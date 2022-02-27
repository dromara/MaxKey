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
 

package org.maxkey.web.interceptor;

import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.maxkey.authz.oauth2.provider.token.DefaultTokenServices;
import org.maxkey.util.AuthorizationHeaderCredential;
import org.maxkey.util.AuthorizationHeaderUtils;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * basic认证Interceptor处理.
 * @author Crystal.Sea
 *
 */
@Component
public class RestApiPermissionAdapter  implements AsyncHandlerInterceptor  {
	private static final Logger _logger = LoggerFactory.getLogger(RestApiPermissionAdapter.class);

	@Autowired
	@Qualifier("oauth20TokenServices")
	DefaultTokenServices oauth20TokenServices;

	@Autowired
    @Qualifier("oauth20ClientAuthenticationManager")
	ProviderManager authenticationManager;
	
	static  ConcurrentHashMap<String ,String >navigationsMap=null;
	
	/*
	 * 请求前处理
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 _logger.trace("RestApiPermissionAdapter preHandle");
		String  authorization = request.getHeader(AuthorizationHeaderUtils.HEADER_Authorization);
		AuthorizationHeaderCredential headerCredential = AuthorizationHeaderUtils.resolve(authorization);
		 
		//判断应用的AppId和Secret
		if(headerCredential != null){
			UsernamePasswordAuthenticationToken authenticationToken = null;
			if(headerCredential.getCredentialType().equals(AuthorizationHeaderCredential.Credential.BASIC)) {
			    if(StringUtils.isNotBlank(headerCredential.getUsername())&&
			    		StringUtils.isNotBlank(headerCredential.getCredential())
			    		) {
			    	UsernamePasswordAuthenticationToken authRequest = 
							new UsernamePasswordAuthenticationToken(
									headerCredential.getUsername(),
									headerCredential.getCredential());
			    	authenticationToken= (UsernamePasswordAuthenticationToken)authenticationManager.authenticate(authRequest);
			    }
			}else {
				_logger.trace("Authentication bearer " + headerCredential.getCredential());
				OAuth2Authentication oauth2Authentication = 
						oauth20TokenServices.loadAuthentication(headerCredential.getCredential());
				
				if(oauth2Authentication != null) {
					_logger.trace("Authentication token " + oauth2Authentication.getPrincipal().toString());
					authenticationToken= new UsernamePasswordAuthenticationToken(
			    			new User(
			    					oauth2Authentication.getPrincipal().toString(), 
			    					"CLIENT_SECRET", 
			    					oauth2Authentication.getAuthorities()), 
	                        "PASSWORD", 
	                        oauth2Authentication.getAuthorities()
	                );
				}else {
					_logger.trace("Authentication token is null ");
				}
			}
			
			if(authenticationToken !=null && authenticationToken.isAuthenticated()) {
				WebContext.setAuthentication(authenticationToken);
				return true;
			}
		}
		
		_logger.trace("No Authentication ... forward to /login");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);
        
		return false;
	}
}
