/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.maxkey.crypto.password.opt.token;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.maxkey.constants.ConstantsStatus;
import org.maxkey.crypto.password.opt.OneTimePassword;
import org.maxkey.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcOptTokenStore extends AbstractOptTokenStore {
    private static final  Logger logger = LoggerFactory.getLogger(JdbcOptTokenStore.class);
    
    private static final String DEFAULT_DEFAULT_INSERT_STATEMENT = 
            "INSERT INTO ONE_TIME_PASSWORD(ID ,OPTTYPE,USERNAME,TOKEN,RECEIVER,CREATETIME,STATUS)" 
                    + " VALUES(?,?,?,?,?,?," + ConstantsStatus.ACTIVE + ")";

    private static final String DEFAULT_DEFAULT_SELECT_STATEMENT = 
            "SELECT ID ,OPTTYPE,USERNAME,TOKEN,RECEIVER,CREATETIME FROM ONE_TIME_PASSWORD"
            +   " WHERE STATUS =" + ConstantsStatus.ACTIVE 
            +   " AND  USERNAME = ? AND TOKEN = ? AND OPTTYPE = ?";

    private static final String DEFAULT_DEFAULT_DELETE_STATEMENT = 
            "UPDATE ONE_TIME_PASSWORD SET  STATUS ="
            + ConstantsStatus.DELETE + " WHERE USERNAME = ? AND TOKEN = ? AND OPTTYPE = ?";
    
    private final JdbcTemplate jdbcTemplate;
    
    public JdbcOptTokenStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
 
    /**
     *store.
     */
    public void store(UserInfo userInfo, String token, String receiver, String type) {
        jdbcTemplate.update(DEFAULT_DEFAULT_INSERT_STATEMENT,
                new Object[] { 
                        java.util.UUID.randomUUID(), 
                        type, 
                        userInfo.getUsername(),
                        token, 
                        receiver, 
                        new Date() 
                },
                new int[] { Types.VARCHAR, Types.VARCHAR, 
                        Types.VARCHAR, Types.VARCHAR, 
                        Types.VARCHAR,Types.TIMESTAMP 
                }
        );
    }

    /**
     * validate.
     * @param userInfo UserInfo
     * @param token String
     * @param type int
     * @return
     */
    public boolean validate(UserInfo userInfo, String token, String type,int interval) {
        OneTimePassword oneTimePassword = jdbcTemplate.queryForObject(
                DEFAULT_DEFAULT_SELECT_STATEMENT,
                new OneTimePasswordRowMapper(), userInfo.getUsername(), token, type);

        if (oneTimePassword != null) {

            jdbcTemplate.update(
                    DEFAULT_DEFAULT_DELETE_STATEMENT, 
                    new Object[] { userInfo.getUsername(), token, type },
                    new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER }
            );
            DateTime currentdateTime = new DateTime();
            DateTime oneTimePwdData = DateTime.parse(oneTimePassword.getCreateTime(),
                    DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            Duration duration = new Duration(oneTimePwdData, currentdateTime);
            int intDuration = Integer.parseInt(duration.getStandardSeconds() + "");
            logger.debug("validate duration " + intDuration);
            logger.debug("validate result " + (intDuration <= interval));
            if (intDuration <= interval) {
                return true;
            }
        }
        return false;

    }
    
    public class OneTimePasswordRowMapper implements RowMapper<OneTimePassword> {

        /**
         *ResultSet.
         */
        public OneTimePassword mapRow(ResultSet rs, int rowNum) throws SQLException {
            OneTimePassword oneTimePassword = new OneTimePassword();
            oneTimePassword.setId(rs.getString("ID"));
            oneTimePassword.setType(rs.getString("OPTTYPE"));
            oneTimePassword.setUsername(rs.getString("USERNAME"));
            oneTimePassword.setToken(rs.getString("TOKEN"));
            oneTimePassword.setUsername(rs.getString("USERNAME"));
            oneTimePassword.setReceiver(rs.getString("RECEIVER"));
            oneTimePassword.setCreateTime(rs.getString("CREATETIME"));
            return oneTimePassword;
        }
    }

}
