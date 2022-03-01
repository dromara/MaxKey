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
 

package org.maxkey.persistence.repository;

import java.sql.Types;

import org.maxkey.entity.HistoryLogin;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoginHistoryRepository {
    private static Logger _logger = LoggerFactory.getLogger(LoginHistoryRepository.class);
    
    private static final String HISTORY_LOGIN_INSERT_STATEMENT = "insert into mxk_history_login (id , sessionid , userid , username , displayname , logintype , message , code , provider , sourceip , ipregion , iplocation, browser , platform , application , loginurl , sessionstatus ,instid)values( ? , ? , ? , ? , ? , ? , ? , ? , ?, ? , ? , ?, ? , ? , ?, ? , ? , ?)";

    private static final String HISTORY_LOGOUT_UPDATE_STATEMENT = "update mxk_history_login set logouttime = ? ,sessionstatus = 7 where  sessionid = ?";

    protected JdbcTemplate jdbcTemplate;
    
    public LoginHistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
        
    public void login(HistoryLogin historyLogin) {
        historyLogin.setId(WebContext.genId());
        historyLogin.setLoginUrl(WebContext.getRequest().getRequestURI());
        _logger.debug(" historyLogin " + historyLogin);
        jdbcTemplate.update(HISTORY_LOGIN_INSERT_STATEMENT,
                new Object[] { 
                        historyLogin.getId(), historyLogin.getSessionId(), historyLogin.getUserId(), historyLogin.getUsername(),
                        historyLogin.getDisplayName(), historyLogin.getLoginType(), historyLogin.getMessage(), historyLogin.getCode(), 
                        historyLogin.getProvider(), historyLogin.getSourceIp(),historyLogin.getIpRegion(),historyLogin.getIpLocation(),
                        historyLogin.getBrowser(), historyLogin.getPlatform(),"Browser", historyLogin.getLoginUrl() , 
                        historyLogin.getSessionStatus(),historyLogin.getInstId()
                        },
                new int[] { 
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
                        Types.VARCHAR ,Types.INTEGER, Types.VARCHAR
                        });
    }
    
    
    
    public void logoff(String lastLogoffTime,String sessionId) {
        _logger.debug(" sessionId {} , lastlogofftime {}" ,sessionId, lastLogoffTime);
        jdbcTemplate.update(HISTORY_LOGOUT_UPDATE_STATEMENT,
                new Object[] { lastLogoffTime, sessionId },                           
                new int[] { Types.VARCHAR, Types.VARCHAR });
    }
    
    public void logoff(HistoryLogin historyLogin) {
        _logger.debug(" sessionId {} , LogoutTime {}" ,
                    historyLogin.getSessionId(), historyLogin.getLogoutTime()
                );
        jdbcTemplate.update(HISTORY_LOGOUT_UPDATE_STATEMENT,
                new Object[] { historyLogin.getLogoutTime(), historyLogin.getSessionId() },                           
                new int[] { Types.VARCHAR, Types.VARCHAR });
    }
}
