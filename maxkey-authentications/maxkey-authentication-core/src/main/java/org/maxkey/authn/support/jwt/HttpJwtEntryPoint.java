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
 

package org.maxkey.authn.support.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.LoginCredential;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstsLoginType;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import com.nimbusds.jwt.SignedJWT;


public class HttpJwtEntryPoint implements AsyncHandlerInterceptor {
	private static final Logger _logger = LoggerFactory.getLogger(HttpJwtEntryPoint.class);
	
    boolean enable;
    
  	ApplicationConfig applicationConfig;
    
    AbstractAuthenticationProvider authenticationProvider ;
    
	JwtLoginService jwtLoginService;
	
	 @Override
	 public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 boolean isAuthenticated= WebContext.isAuthenticated();
		 String jwt = request.getParameter(WebConstants.JWT_TOKEN_PARAMETER);
		 
		 if(!enable 
				 || isAuthenticated 
				 || jwt == null){
			 return true;
		 }
		 
		 _logger.debug("JWT Login Start ...");
		 _logger.trace("Request url : "+ request.getRequestURL());
		 _logger.trace("Request URI : "+ request.getRequestURI());
		 _logger.trace("Request ContextPath : "+ request.getContextPath());
		 _logger.trace("Request ServletPath : "+ request.getServletPath());
		 _logger.trace("RequestSessionId : "+ request.getRequestedSessionId());
		 _logger.trace("isRequestedSessionIdValid : "+ request.isRequestedSessionIdValid());
		 _logger.trace("getSession : "+ request.getSession(false));
		 
		// session not exists，session timeout，recreate new session
		 if(request.getSession(false) == null) {
		    _logger.trace("recreate new session .");
			request.getSession(true);
		 }
		 
		 _logger.trace("getSession.getId : "+ request.getSession().getId());

		//for jwt Login
		 _logger.debug("jwt : " + jwt);

		 SignedJWT signedJWT = jwtLoginService.jwtTokenValidation(jwt);
		 if(signedJWT != null) {
			 String username =signedJWT.getJWTClaimsSet().getSubject();
			 LoginCredential loginCredential =new LoginCredential(username,"",ConstsLoginType.JWT);
			 authenticationProvider.authentication(loginCredential,true);
			 _logger.debug("JWT Logined in , username " + username);
		 }
		
		return true;
	}

	 public HttpJwtEntryPoint() {
	        super();
	 }

    public HttpJwtEntryPoint (boolean enable) {
        super();
        this.enable = enable;
    }

    public HttpJwtEntryPoint(AbstractAuthenticationProvider authenticationProvider, JwtLoginService jwtLoginService,
			ApplicationConfig applicationConfig, boolean enable) {
		super();
		this.authenticationProvider = authenticationProvider;
		this.jwtLoginService = jwtLoginService;
		this.applicationConfig = applicationConfig;
		this.enable = enable;
	}

	public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public void setAuthenticationProvider(AbstractAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	public void setJwtLoginService(JwtLoginService jwtLoginService) {
		this.jwtLoginService = jwtLoginService;
	}
	
}
