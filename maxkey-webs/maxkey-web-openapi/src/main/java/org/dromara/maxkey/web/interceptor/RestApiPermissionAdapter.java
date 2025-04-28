/*
 * Copyright [2023] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.web.interceptor;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.authz.oauth2.provider.token.DefaultTokenServices;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.maxkey.util.AuthorizationHeader;
import org.dromara.maxkey.util.AuthorizationHeaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * basic认证Interceptor处理.
 * @author Crystal.Sea
 *
 */
@Component
public class RestApiPermissionAdapter  implements AsyncHandlerInterceptor  {
	private static final Logger logger = LoggerFactory.getLogger(RestApiPermissionAdapter.class);

	static final String PASSWORD = "password";
	
	@Autowired
	DefaultTokenServices oauth20TokenServices;

	@Autowired
	AppsService appsService;
	
	/*
	 * 请求前处理
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		logger.trace("Rest API Permission Adapter pre handle");
		 AuthorizationHeader headerCredential = AuthorizationHeaderUtils.resolve(request);
		 
		//判断应用的AppId和Secret
		if(headerCredential != null){
			UsernamePasswordAuthenticationToken authenticationToken = null;
			if(headerCredential.isBasic()) {
			    if(StringUtils.isNotBlank(headerCredential.getUsername())&&
			    		StringUtils.isNotBlank(headerCredential.getCredential())
			    		) {
			    	String appId = headerCredential.getUsername();
			    	String credential = headerCredential.getCredential();
			    	Apps app = appsService.get(appId, true);
			    	if(app != null ) {
			    		if(	PasswordReciprocal.getInstance().matches(credential, app.getSecret())) {
			    			ArrayList<SimpleGrantedAuthority> grantedAuthoritys = new ArrayList<>();
			    			grantedAuthoritys.add(new SimpleGrantedAuthority("ROLE_USER"));
			    			User user = new User(appId, PASSWORD, grantedAuthoritys);
			    			authenticationToken= new UsernamePasswordAuthenticationToken(user, PASSWORD, grantedAuthoritys);
			    		}else {
			    			logger.trace("app {} secret not matches . ",appId);
			    		}
				    }else {
				    	logger.trace("app {} not exists . ",appId);
				    }
			    }
			}else if(StringUtils.isNotBlank(headerCredential.getCredential())){
				logger.trace("Authentication bearer {}" , headerCredential.getCredential());
				OAuth2Authentication oauth2Authentication = 
						oauth20TokenServices.loadAuthentication(headerCredential.getCredential());
				
				if(oauth2Authentication != null) {
					logger.trace("Authentication token {}" , oauth2Authentication.getPrincipal().toString());
					authenticationToken= new UsernamePasswordAuthenticationToken(
			    			new User(
			    					oauth2Authentication.getPrincipal().toString(), 
			    					"CLIENT_SECRET", 
			    					oauth2Authentication.getAuthorities()), 
	                        "PASSWORD", 
	                        oauth2Authentication.getAuthorities()
	                );
				}else {
					logger.trace("Authentication token is null ");
				}
			}
			
			if(authenticationToken !=null && authenticationToken.isAuthenticated()) {
				AuthorizationUtils.setAuthentication(authenticationToken);
				return true;
			}
		}
		
		logger.trace("No Authentication ... forward to /login");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);
        
		return false;
	}
}
