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
 

package org.dromara.maxkey.authn.support.httpheader;

import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.constants.ConstsLoginType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class HttpHeaderEntryPoint implements AsyncHandlerInterceptor {
	private static final Logger _logger = LoggerFactory.getLogger(HttpHeaderEntryPoint.class);
	
	String headerName;
    boolean enable;
    
    @Autowired
    @Qualifier("authenticationProvider")
    AbstractAuthenticationProvider authenticationProvider ;
	
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
		 _logger.trace("HttpHeader Login Start ...");
		 _logger.trace("Request url : "+ request.getRequestURL());
		 _logger.trace("Request URI : "+ request.getRequestURI());
		 _logger.trace("Request ContextPath : "+ request.getContextPath());
		 _logger.trace("Request ServletPath : "+ request.getServletPath());
		 _logger.trace("RequestSessionId : "+ request.getRequestedSessionId());
		 _logger.trace("isRequestedSessionIdValid : "+ request.isRequestedSessionIdValid());
		 _logger.trace("getSession : "+ request.getSession(false));
		 
		 for(int i=0;i<skipRequestURI.length;i++){
			 if(skipRequestURI[i].indexOf(requestPath)>-1){
				 _logger.trace("skip uri : "+ requestPath);
				 return true;
			 }
		 }
		
		
		 
		// session not exists，session timeout，recreate new session
		 if(request.getSession(false) == null) {
		    _logger.trace("recreate new session .");
			request.getSession(true);
		 }
		 
		 _logger.trace("getSession.getId : "+ request.getSession().getId());
		 String httpHeaderUsername = request.getHeader(headerName);

		 _logger.trace("HttpHeader username : " + httpHeaderUsername);
		
		
		 if(httpHeaderUsername==null||httpHeaderUsername.equals("")){
			 _logger.info("Authentication fail HttpHeader is null . ");
			 return false;
		 }
		 
		 boolean isAuthenticated=false;
		 
		 if(SecurityContextHolder.getContext().getAuthentication() == null) {
			 _logger.info("Security Authentication  is  null .");
			 isAuthenticated=false;
		 }else {
			 _logger.info("Security Authentication   not null . ");
			 UsernamePasswordAuthenticationToken authenticationToken = 
					 (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
			 String lastSessionUserName = authenticationToken.getPrincipal().toString();
			 _logger.info("Authentication Principal : " + lastSessionUserName);
			 if (lastSessionUserName != null && !lastSessionUserName.equals(httpHeaderUsername)) {
				isAuthenticated=false;
			 }else{
				isAuthenticated=true;
			 }
		 }
		 
		 if(!isAuthenticated){
			LoginCredential loginCredential =new LoginCredential(httpHeaderUsername,"",ConstsLoginType.HTTPHEADER);
            authenticationProvider.authenticate(loginCredential,true);
			_logger.info("Authentication  "+httpHeaderUsername+" successful .");
		 }
		
		 return true;
	}

	 public HttpHeaderEntryPoint() {
	        super();
	 }

    public HttpHeaderEntryPoint(String headerName, boolean enable) {
        super();
        this.headerName = headerName;
        this.enable = enable;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
	 
	
}
