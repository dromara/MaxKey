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
 

package org.dromara.maxkey.authn.web.interceptor;


import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 权限Interceptor处理
 * @author Crystal.Sea
 *
 */
@Component
public class PermissionInterceptor  implements AsyncHandlerInterceptor  {
	private static final Logger _logger = LoggerFactory.getLogger(PermissionInterceptor.class);
	//无需Interceptor url
	@Autowired
	ApplicationConfig applicationConfig;
	
	@Autowired
	SessionManager sessionManager;
	
	@Autowired
	AuthTokenService authTokenService ;
	
	boolean mgmt = false;
	
	/*
	 * 请求前处理
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 _logger.trace("Permission Interceptor .");
		 AuthorizationUtils.authenticate(request, authTokenService, sessionManager);
		 SignPrincipal principal = AuthorizationUtils.getPrincipal();
		//判断用户是否登录,判断用户是否登录用户
		if(principal == null){
			_logger.debug("No Authentication ... forward to /auth/entrypoint , request URI {}" , request.getRequestURI());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/auth/entrypoint");
		    dispatcher.forward(request, response);
		    return false;
		}
		
		//管理端必须使用管理员登录,非管理员用户直接注销
		if (this.mgmt && !principal.isRoleAdministrators()) {
		    _logger.debug("Not ADMINISTRATORS Authentication .");
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/auth/entrypoint");
		    dispatcher.forward(request, response);
		    return false;
		}
		
		return true;
	}

	public void setMgmt(boolean mgmt) {
		this.mgmt = mgmt;
		_logger.debug("Permission for ADMINISTRATORS {}", this.mgmt);
	}
	
}
