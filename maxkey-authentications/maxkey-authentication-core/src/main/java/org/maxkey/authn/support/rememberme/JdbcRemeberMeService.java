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
 

package org.maxkey.authn.support.rememberme;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcRemeberMeService extends AbstractRemeberMeService {
    private static final Logger _logger = LoggerFactory.getLogger(JdbcRemeberMeService.class);

    private static final String DEFAULT_DEFAULT_INSERT_STATEMENT = 
            "insert into  mxk_remember_me(id, username,authkey,lastlogin)values( ? , ? , ? , ?)";

    private static final String DEFAULT_DEFAULT_SELECT_STATEMENT = 
            "select id, username,authkey,lastlogin  from mxk_remember_me " 
                    + " where id = ?  and username = ? and authkey = ?";

    private static final String DEFAULT_DEFAULT_DELETE_STATEMENT = 
            "delete from  mxk_remember_me where  username = ?";

    private static final String DEFAULT_DEFAULT_UPDATE_STATEMENT = 
            "update mxk_remember_me  set authkey  = ? , lastlogin = ?  where id = ?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcRemeberMeService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(RemeberMe remeberMe) {
        jdbcTemplate.update(DEFAULT_DEFAULT_INSERT_STATEMENT,
                new Object[] { remeberMe.getId(), remeberMe.getUsername(), remeberMe.getAuthKey(),
                        remeberMe.getLastLogin() },
                new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP });
    }

    @Override
    public void update(RemeberMe remeberMe) {
        jdbcTemplate.update(DEFAULT_DEFAULT_UPDATE_STATEMENT,
                new Object[] { 
                        remeberMe.getAuthKey(), 
                        remeberMe.getLastLogin(), 
                        remeberMe.getId() 
                });
    }

    @Override
    public RemeberMe read(RemeberMe remeberMe) {
        List<RemeberMe> listRemeberMe = jdbcTemplate.query(DEFAULT_DEFAULT_SELECT_STATEMENT,
                new RowMapper<RemeberMe>() {
                    public RemeberMe mapRow(ResultSet rs, int rowNum) throws SQLException {
                        RemeberMe remeberMe = new RemeberMe();
                        remeberMe.setId(rs.getString(1));
                        remeberMe.setUsername(rs.getString(2));
                        remeberMe.setAuthKey(rs.getString(3));
                        remeberMe.setLastLogin(rs.getDate(4));
                        return remeberMe;
                    }
                }, remeberMe.getId(), remeberMe.getUsername(), remeberMe.getAuthKey());
        _logger.debug("listRemeberMe " + listRemeberMe);
        return (listRemeberMe.size() > 0) ? listRemeberMe.get(0) : null;
    }

    @Override
    public void remove(String username) {
        jdbcTemplate.update(DEFAULT_DEFAULT_DELETE_STATEMENT, username);
    }

}
