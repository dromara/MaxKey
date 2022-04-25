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

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authn.jwt.AuthJwtService;
import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketService;
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
			OnlineTicketService onlineTicketService
			) throws ParseException{
		 if(getAuthentication() == null) {
			Cookie authCookie = WebContext.getCookie(request, Authorization_Cookie);
			if(authCookie != null ) {
		    	String  authorization =  authCookie.getValue();
		    	doJwtAuthenticate(authorization,authJwtService,onlineTicketService);
		    	_logger.debug("congress automatic authenticated .");
			}
		 }
	}
	
	public static  void authenticate(
			HttpServletRequest request,
			AuthJwtService authJwtService,
			OnlineTicketService onlineTicketService
			) throws ParseException{
		 if(getAuthentication() == null) {
			 String  authorization = AuthorizationHeaderUtils.resolveBearer(request);
			if(authorization != null ) {
				doJwtAuthenticate(authorization,authJwtService,onlineTicketService);
				_logger.debug("Authorization automatic authenticated .");
			}
		 }
	}
	
	public static void doJwtAuthenticate(
			String  authorization,
			AuthJwtService authJwtService,
			OnlineTicketService onlineTicketService) throws ParseException {
		if(authJwtService.validateJwtToken(authorization)) {
			String ticket = authJwtService.resolveJWTID(authorization);
			OnlineTicket onlineTicket = onlineTicketService.get(ticket);
			if(onlineTicket != null) {
				setAuthentication(onlineTicket.getAuthentication());
			}
		}
	}
	
    public static void setAuthentication(Authentication authentication) {
    	WebContext.setAttribute(WebConstants.AUTHENTICATION, authentication);
    }

    public static Authentication getAuthentication() {
        Authentication authentication = (Authentication) getAuthentication(WebContext.getRequest());
        return authentication;
    }
    
    public static Authentication getAuthentication(HttpServletRequest request) {
        Authentication authentication = (Authentication) request.getSession().getAttribute(WebConstants.AUTHENTICATION);
        return authentication;
    }
    
    public static  boolean isAuthenticated() {
    	return getAuthentication() != null;
    }
    
    public static  boolean isNotAuthenticated() {
    	return ! isAuthenticated();
    }
    
    public static SigninPrincipal getPrincipal() {
    	 Authentication authentication =  getAuthentication();
    	return getPrincipal(authentication);
    }
    
    public static SigninPrincipal getPrincipal(Authentication authentication) {
    	return authentication == null ? null : (SigninPrincipal) authentication.getPrincipal();
   }
    
    public static UserInfo getUserInfo(Authentication authentication) {
    	UserInfo userInfo = null;
    	SigninPrincipal principal = getPrincipal(authentication);
    	if(principal != null ) {
        	userInfo = principal.getUserInfo();
        }
    	return userInfo;
    }
    
    public static UserInfo getUserInfo() {
    	return getUserInfo(getAuthentication());
    }
	
}
