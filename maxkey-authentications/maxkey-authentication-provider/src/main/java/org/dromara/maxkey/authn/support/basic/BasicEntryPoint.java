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
 

package org.dromara.maxkey.authn.support.basic;


import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.constants.ConstsLoginType;
import org.dromara.maxkey.util.AuthorizationHeader;
import org.dromara.maxkey.util.AuthorizationHeaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BasicEntryPoint implements   AsyncHandlerInterceptor {
	private static final Logger _logger = LoggerFactory.getLogger(BasicEntryPoint.class);
	
	boolean enable;
	
	@Autowired
    @Qualifier("authenticationProvider")
	AbstractAuthenticationProvider authenticationProvider ;
	
	public BasicEntryPoint() {
	    
    }
	
	public BasicEntryPoint(boolean enable) {
        super();
        this.enable = enable;
    }

    String []skipRequestURI={
			"/oauth/v20/token",
			"/oauth/v10a/request_token",
			"/oauth/v10a/access_token"
	};
	
	 @Override
	 public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 
		 if(!enable){
			 return true;
		 }
		 String requestPath=request.getServletPath();
		 _logger.debug("HttpHeader Login Start ...");
		 _logger.info("Request url : "+ request.getRequestURL());
		 _logger.info("Request URI : "+ request.getRequestURI());
		 _logger.info("Request ContextPath : "+ request.getContextPath());
		 _logger.info("Request ServletPath : "+ request.getServletPath());
		 _logger.debug("RequestSessionId : "+ request.getRequestedSessionId());
		 _logger.debug("isRequestedSessionIdValid : "+ request.isRequestedSessionIdValid());
		 _logger.debug("getSession : "+ request.getSession(false));
		 
		 for(int i=0;i<skipRequestURI.length;i++){
			 if(skipRequestURI[i].indexOf(requestPath)>-1){
				 _logger.info("skip uri : "+ requestPath);
				 return true;
			 }
		 }
		
		// session not exists，session timeout，recreate new session
		 if(request.getSession(false) == null) {
		    _logger.info("recreate new session .");
			request.getSession(true);
		 }
		 String basicCredential =request.getHeader(AuthorizationHeaderUtils.HEADER_Authorization);
		 _logger.info("getSession.getId : "+ request.getSession().getId());
		 
		 _logger.info("Authorization : " + basicCredential);
		
		
		 if(basicCredential==null||basicCredential.equals("")){
			 _logger.info("Authentication fail header Authorization is null . ");
			 return false;
		 }
		 
		 AuthorizationHeader headerCredential = null;
		 
		 if(AuthorizationHeaderUtils.isBasic(basicCredential)){
			 headerCredential=AuthorizationHeaderUtils.resolve(basicCredential);
		 }else{
			 return false;
		 }
		 if(headerCredential.getUsername()==null||headerCredential.getUsername().equals("")){
			 _logger.info("Authentication fail username is null . ");
			 return false;
		 }
		 if(headerCredential.getCredential()==null||headerCredential.getCredential().equals("")){
			 _logger.info("Authentication fail password is null . ");
			 return false;
		 }
		 
		 boolean isAuthenticated=false;
		 
		 if(SecurityContextHolder.getContext().getAuthentication() == null) {
			 _logger.info("Security Authentication  is  null .");
			 isAuthenticated=false;
		 }else {
			 _logger.info("Security Authentication   not null . ");
			 UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
			 String lastSessionUserName = authenticationToken.getPrincipal().toString();
			 _logger.info("Authentication Principal : " + lastSessionUserName);
			 if (lastSessionUserName != null && !lastSessionUserName.equals(headerCredential.getUsername())) {
				isAuthenticated=false;
			 }else{
				isAuthenticated=true;
			 }
		 }
		 
		 if(!isAuthenticated){
		     LoginCredential loginCredential =new LoginCredential(headerCredential.getUsername(),"",ConstsLoginType.BASIC);
			 authenticationProvider.authenticate(loginCredential,true);
			 _logger.info("Authentication  "+headerCredential.getUsername()+" successful .");
		 }
		
		 return true;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	
}
