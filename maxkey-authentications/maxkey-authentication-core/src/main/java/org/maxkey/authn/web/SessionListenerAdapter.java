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
package org.maxkey.authn.web;

import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.util.DateUtils;
import org.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

@WebListener
public class SessionListenerAdapter implements HttpSessionListener {
    private static final Logger _logger = LoggerFactory.getLogger(SessionListenerAdapter.class);
    
    public SessionListenerAdapter() {
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
        SigninPrincipal principal = AuthorizationUtils.getPrincipal(authentication);
        if(principal != null ) {
        	_logger.trace("{} HttpSession Id  {} for userId  {} , username  {} @Ticket {} Destroyed" ,
        			DateUtils.formatDateTime(new Date()),
        			session.getId(), 
        			principal.getUserInfo().getId(),
        			principal.getUserInfo().getUsername(),
        			principal.getOnlineTicket().getTicketId());
        }
        
    }

}
