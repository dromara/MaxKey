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
 

package org.maxkey.web.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.jwt.AuthJwtService;
import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.authn.web.AuthorizationUtils;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.crypto.Base64Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

@Component
public class SingleSignOnInterceptor  implements AsyncHandlerInterceptor {
    private static final Logger _logger = LoggerFactory.getLogger(SingleSignOnInterceptor.class);
    
    @Autowired
    ApplicationConfig applicationConfig;
    
    @Autowired
	OnlineTicketService onlineTicketService;
    
    @Autowired
	AuthJwtService authJwtService ;
    
    @Override
    public boolean preHandle(HttpServletRequest request, 
            HttpServletResponse response, Object handler)
            throws Exception {
    	_logger.trace("Single Sign On Interceptor");
       
    	AuthorizationUtils.authenticateWithCookie(
    				request,authJwtService,onlineTicketService);

        if(AuthorizationUtils.isNotAuthenticated()){
        	String loginUrl = applicationConfig.getFrontendUri() + "/#/passport/login?redirect_uri=%s";
        	String redirect_uri = UrlUtils.buildFullRequestUrl(request);
        	String base64RequestUrl = Base64Utils.base64UrlEncode(redirect_uri.getBytes());
        	_logger.debug("No Authentication ... Redirect to /passport/login , redirect_uri {}",redirect_uri);
        	response.sendRedirect(String.format(loginUrl,base64RequestUrl));
        }
        return true;
    }

}
