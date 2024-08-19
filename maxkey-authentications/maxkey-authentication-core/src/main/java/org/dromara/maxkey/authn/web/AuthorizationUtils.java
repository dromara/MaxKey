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

import java.text.ParseException;


import com.nimbusds.jwt.SignedJWT;
import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.util.AuthorizationHeaderUtils;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class AuthorizationUtils {
	private static final Logger _logger = LoggerFactory.getLogger(AuthorizationUtils.class);

	public static final class BEARERTYPE{

		public static final String CONGRESS 		= "congress";

		public static final String AUTHORIZATION 	= "Authorization";
	}

	public static  void authenticateWithCookie(
			HttpServletRequest request,
			AuthTokenService authTokenService,
			SessionManager sessionManager
			) throws ParseException{
		Cookie authCookie = WebContext.getCookie(request, BEARERTYPE.CONGRESS);
		if(authCookie != null ) {
	    	String  authorization =  authCookie.getValue();
	    	_logger.trace("Try congress authenticate .");
	    	doJwtAuthenticate(BEARERTYPE.CONGRESS,authorization,authTokenService,sessionManager);
		}else {
			_logger.debug("cookie is null , clear authentication .");
			clearAuthentication();
		}
	}

	public static  void authenticate(
			HttpServletRequest request,
			AuthTokenService authTokenService,
			SessionManager sessionManager
			) throws ParseException{
		String  authorization = AuthorizationHeaderUtils.resolveBearer(request);
		if(authorization != null ) {
			_logger.trace("Try Authorization authenticate .");
			doJwtAuthenticate(BEARERTYPE.AUTHORIZATION,authorization,authTokenService,sessionManager);
		}

	}

	public static void doJwtAuthenticate(
			String  bearerType,
			String  authorization,
			AuthTokenService authTokenService,
			SessionManager sessionManager) throws ParseException {
		if(authTokenService.validateJwtToken(authorization)) {
			if(isNotAuthenticated()) {
				String sessionId = authTokenService.resolveJWTID(authorization);
				Session session = sessionManager.get(sessionId);
				if(session != null) {
					setAuthentication(session.getAuthentication());
					_logger.debug("{} Automatic authenticated .",bearerType);
				}else {
					//time out
					_logger.debug("Session timeout .");
					clearAuthentication();
				}
			}
		}else {
			//token invalidate
			_logger.debug("Token invalidate .");
			clearAuthentication();
		}
	}

	public static Session getSession(SessionManager sessionManager, String authorization) throws ParseException {
		_logger.debug("get session by authorization {}", authorization);
		SignedJWT signedJWT = SignedJWT.parse(authorization);
		String sessionId = signedJWT.getJWTClaimsSet().getJWTID();
		_logger.debug("sessionId {}", sessionId);
		return sessionManager.get(sessionId);
	}


    public static Authentication getAuthentication() {
    	Authentication authentication = (Authentication) getAuthentication(WebContext.getRequest());
        return authentication;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
    	Authentication authentication = (Authentication) request.getSession().getAttribute(WebConstants.AUTHENTICATION);
        return authentication;
    }

    //set Authentication to http session
    public static void setAuthentication(Authentication authentication) {
    	WebContext.setAttribute(WebConstants.AUTHENTICATION, authentication);
    }

    public static void clearAuthentication() {
    	WebContext.removeAttribute(WebConstants.AUTHENTICATION);
    }

    public static  boolean isAuthenticated() {
    	return getAuthentication() != null;
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
