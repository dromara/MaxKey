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
 

package org.maxkey.authn.web.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authn.jwt.AuthJwtService;
import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.authn.web.AuthorizationUtils;
import org.maxkey.configuration.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
/**
 * 权限Interceptor处理
 * 权限处理需在servlet.xml中配置
 *  mvc:interceptors  permission
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
	OnlineTicketService onlineTicketService;
	
	@Autowired
	AuthJwtService authJwtService ;
	
	/*
	 * 请求前处理
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 _logger.trace("PermissionAdapter preHandle");
		 AuthorizationUtils.authenticate(request, authJwtService, onlineTicketService);
		//判断用户是否登录
		if(AuthorizationUtils.getAuthentication()==null
		        ||AuthorizationUtils.getAuthentication().getAuthorities()==null){//判断用户和角色，判断用户是否登录用户
			_logger.trace("No Authentication ... forward to /auth/entrypoint");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/auth/entrypoint");
		    dispatcher.forward(request, response);
		    return false;
		}
		
		//非管理员用户直接注销
		if (!((SigninPrincipal) AuthorizationUtils.getAuthentication().getPrincipal()).isRoleAdministrators()) {
		    _logger.debug("Not ADMINISTRATORS Authentication .");
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/logout");
		    dispatcher.forward(request, response);
		    return false;
		}
		
		boolean hasAccess=true;
		
		return hasAccess;
	}
}
