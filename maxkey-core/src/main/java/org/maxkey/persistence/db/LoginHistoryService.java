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
 

package org.maxkey.persistence.db;

import java.sql.Types;

import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoginHistoryService {
    private static Logger _logger = LoggerFactory.getLogger(LoginHistoryService.class);
    
    private static final String HISTORY_LOGIN_INSERT_STATEMENT = "INSERT INTO MXK_HISTORY_LOGIN (ID , SESSIONID , UID , USERNAME , DISPLAYNAME , LOGINTYPE , MESSAGE , CODE , PROVIDER , SOURCEIP , BROWSER , PLATFORM , APPLICATION , LOGINURL )VALUES( ? , ? , ? , ? , ?, ? , ? , ?, ? , ? , ?, ? , ? , ?)";

    private static final String HISTORY_LOGOUT_UPDATE_STATEMENT = "UPDATE MXK_HISTORY_LOGIN SET LOGOUTTIME = ?  WHERE  SESSIONID = ?";

    protected JdbcTemplate jdbcTemplate;
    
    public LoginHistoryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public void login(UserInfo userInfo,String sessionId,
            String type, String message, String code, String provider,String browser, String platform) {
        jdbcTemplate.update(HISTORY_LOGIN_INSERT_STATEMENT,
                new Object[] { WebContext.genId(), sessionId, userInfo.getId(), userInfo.getUsername(),
                        userInfo.getDisplayName(), type, message, code, provider, userInfo.getLastLoginIp(), browser, platform,
                        "Browser", userInfo.getLastLoginTime() },
                new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.TIMESTAMP });
        

    }
    
    public void logoff(String lastLogoffTime,String sessionId) {
        _logger.debug(" sessionId " +sessionId +" , lastlogofftime " + lastLogoffTime);
        jdbcTemplate.update(HISTORY_LOGOUT_UPDATE_STATEMENT,
                new Object[] { lastLogoffTime, sessionId },                           
                new int[] { Types.TIMESTAMP, Types.VARCHAR });
    }
}
