/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
package org.maxkey.web;

import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.mybatis.jpa.util.WebContext;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.repository.LoginHistoryRepository;
import org.maxkey.persistence.repository.LoginRepository;
import org.maxkey.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class SessionListenerAdapter implements HttpSessionListener {

    private static final Logger _logger = LoggerFactory.getLogger(SessionListenerAdapter.class);
    
    LoginRepository loginRepository;
    
    LoginHistoryRepository loginHistoryRepository;
    
    public SessionListenerAdapter() {
        super();
        _logger.debug("SessionListenerAdapter inited . ");
    }

    public SessionListenerAdapter(LoginRepository loginRepository, LoginHistoryRepository loginHistoryRepository) {
        super();
        this.loginRepository = loginRepository;
        this.loginHistoryRepository = loginHistoryRepository;
        _logger.debug("SessionListenerAdapter inited . ");
    }

    public void init() {
        if(loginRepository == null ) {
        	loginRepository = (LoginRepository)WebContext.getBean("loginRepository");
        	loginHistoryRepository = (LoginHistoryRepository)WebContext.getBean("loginHistoryRepository");
            _logger.debug("SessionListenerAdapter function inited . ");
        }
    }
    /**
     * session Created
     */
    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        _logger.trace("new session Created :" + sessionEvent.getSession().getId());
    }

    /**
     * session Destroyed
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        Object sessionIdAttribute = session.getAttribute(WebConstants.CURRENT_USER_SESSION_ID);
        _logger.trace("session Id : " + session.getId());
        if(sessionIdAttribute != null) {
            init();
            UserInfo userInfo = (UserInfo)session.getAttribute(WebConstants.CURRENT_USER);
            userInfo.setLastLogoffTime(DateUtils.formatDateTime(new Date()));
            loginRepository.updateLastLogoff(userInfo);
            loginHistoryRepository.logoff(userInfo.getLastLogoffTime(), sessionIdAttribute.toString());
            
            _logger.debug(
                    "session {} Destroyed as {} userId : {} , username : {}" ,
                    sessionIdAttribute,
                    userInfo.getLastLogoffTime(),
                    userInfo.getId(),
                    userInfo.getUsername());
        }
        
    }

	public void setLoginRepository(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	public void setLoginHistoryRepository(LoginHistoryRepository loginHistoryRepository) {
		this.loginHistoryRepository = loginHistoryRepository;
	}

}
