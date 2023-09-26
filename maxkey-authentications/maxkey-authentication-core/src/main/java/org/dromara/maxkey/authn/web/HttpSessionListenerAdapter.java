/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
package org.dromara.maxkey.authn.web;

import java.util.Date;


import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.util.DateUtils;
import org.dromara.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class HttpSessionListenerAdapter implements HttpSessionListener {
    private static final Logger _logger = LoggerFactory.getLogger(HttpSessionListenerAdapter.class);
    
    public HttpSessionListenerAdapter() {
        super();
        _logger.debug("SessionListenerAdapter inited . ");
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
        Object principal  = authentication == null ? null : authentication.getPrincipal();
        _logger.trace("principal {}",principal);
        if(principal != null ) {
        	if(principal instanceof SignPrincipal && ((SignPrincipal)principal).getUserInfo()!=null) {
        		SignPrincipal signPrincipal = (SignPrincipal)principal;
        		_logger.trace("{} HttpSession Id  {} for userId  {} , username {} @Ticket {} Destroyed" ,
        			DateUtils.formatDateTime(new Date()),
        			session.getId(), 
        			signPrincipal.getUserInfo().getId(),
        			signPrincipal.getUserInfo().getUsername(),
        			signPrincipal.getSessionId());
        	}else if(principal instanceof User) {
        		User user = (User)principal;
        		_logger.trace("{} HttpSession Id  {} for username {} password {} Destroyed" ,
        			DateUtils.formatDateTime(new Date()),
        			session.getId(), 
        			user.getUsername(),
        			user.getPassword());
        	}else{
        		_logger.trace("{} HttpSession Id  {} for principal {} Destroyed" ,
        			DateUtils.formatDateTime(new Date()),
        			session.getId(), 
        			principal);
        	}
        }else {
        	_logger.trace("{} HttpSession Id  {} Destroyed" ,
        			DateUtils.formatDateTime(new Date()),
        			session.getId());
        }
    }

}
