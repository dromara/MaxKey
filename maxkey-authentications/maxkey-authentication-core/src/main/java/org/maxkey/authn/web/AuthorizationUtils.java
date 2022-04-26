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

import java.text.ParseException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.maxkey.authn.SignPrincipal;
import org.maxkey.authn.jwt.AuthJwtService;
import org.maxkey.authn.session.Session;
import org.maxkey.authn.session.SessionService;
import org.maxkey.entity.UserInfo;
import org.maxkey.util.AuthorizationHeaderUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

public class AuthorizationUtils {
	private static final Logger _logger = LoggerFactory.getLogger(AuthorizationUtils.class);
	
	public static final String Authorization_Cookie = "congress";
	
	public static  void authenticateWithCookie(
			HttpServletRequest request,
			AuthJwtService authJwtService,
			SessionService sessionService
			) throws ParseException{
		 if(getSession() == null) {
			Cookie authCookie = WebContext.getCookie(request, Authorization_Cookie);
			if(authCookie != null ) {
		    	String  authorization =  authCookie.getValue();
		    	doJwtAuthenticate(authorization,authJwtService,sessionService);
		    	_logger.debug("congress automatic authenticated .");
			}
		 }
	}
	
	public static  void authenticate(
			HttpServletRequest request,
			AuthJwtService authJwtService,
			SessionService sessionService
			) throws ParseException{
		 if(getSession() == null) {
			 String  authorization = AuthorizationHeaderUtils.resolveBearer(request);
			if(authorization != null ) {
				doJwtAuthenticate(authorization,authJwtService,sessionService);
				_logger.debug("Authorization automatic authenticated .");
			}
		 }
	}
	
	public static void doJwtAuthenticate(
			String  authorization,
			AuthJwtService authJwtService,
			SessionService sessionService) throws ParseException {
		if(authJwtService.validateJwtToken(authorization)) {
			String sessionId = authJwtService.resolveJWTID(authorization);
			Session session = sessionService.get(sessionId);
			if(session != null) {
				setSession(session);
				setAuthentication(session.getAuthentication());
			}
		}
	}
	
    public static void setSession(Session session) {
    	WebContext.setAttribute(WebConstants.SESSION, session);
    }

    public static Session getSession() {
    	Session session = getSession(WebContext.getRequest());
        return session;
    }
    
    public static Session getSession(HttpServletRequest request) {
    	Session session = (Session) request.getSession().getAttribute(WebConstants.SESSION);
        return session;
    }

    public static Authentication getAuthentication() {
    	Authentication authentication = (Authentication) getAuthentication(WebContext.getRequest());
        return authentication;
    }
    
    public static Authentication getAuthentication(HttpServletRequest request) {
    	Authentication authentication = (Authentication) request.getSession().getAttribute(WebConstants.AUTHENTICATION);
        return authentication;
    }
    
    public static void setAuthentication(Authentication authentication) {
    	WebContext.setAttribute(WebConstants.AUTHENTICATION, authentication);
    }

    public static  boolean isAuthenticated() {
    	return getSession() != null;
    }
    
    public static  boolean isNotAuthenticated() {
    	return ! isAuthenticated();
    }
    
    public static SignPrincipal getPrincipal() {
    	 Authentication authentication =  getAuthentication();
    	return getPrincipal(authentication);
    }
    
    public static SignPrincipal getPrincipal(Authentication authentication) {
    	return authentication == null ? null : (SignPrincipal) authentication.getPrincipal();
   }
    
    public static UserInfo getUserInfo(Authentication authentication) {
    	UserInfo userInfo = null;
    	SignPrincipal principal = getPrincipal(authentication);
    	if(principal != null ) {
        	userInfo = principal.getUserInfo();
        }
    	return userInfo;
    }
    
    public static UserInfo getUserInfo() {
    	return getUserInfo(getAuthentication());
    }
	
}
