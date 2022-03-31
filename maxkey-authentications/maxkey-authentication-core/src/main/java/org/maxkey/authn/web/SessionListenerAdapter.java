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
package org.maxkey.authn.web;

import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.mybatis.jpa.util.WebContext;
import org.maxkey.authn.SigninPrincipal;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.repository.LoginHistoryRepository;
import org.maxkey.persistence.repository.LoginRepository;
import org.maxkey.util.DateUtils;
import org.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
        _logger.info("SecurityContextHolder StrategyName " + SessionSecurityContextHolderStrategy.class.getCanonicalName());
        SecurityContextHolder.setStrategyName(SessionSecurityContextHolderStrategy.class.getCanonicalName());
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
        Authentication  authentication  = (Authentication ) session.getAttribute(WebConstants.AUTHENTICATION);
        if(authentication != null && authentication.getPrincipal() instanceof SigninPrincipal) {
        	SigninPrincipal signinPrincipal = ((SigninPrincipal) authentication.getPrincipal());
        	_logger.trace("session Id : " + session.getId());
        	init();
        	UserInfo userInfo = signinPrincipal.getUserInfo();
        	userInfo.setLastLogoffTime(DateUtils.formatDateTime(new Date()));
        	loginRepository.updateLastLogoff(userInfo);
        	loginHistoryRepository.logoff(userInfo.getLastLogoffTime(), userInfo.getOnlineTicket());
          
        	_logger.debug(
                  "session {} Destroyed as {} userId : {} , username : {}" ,
                  userInfo.getOnlineTicket(),
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
