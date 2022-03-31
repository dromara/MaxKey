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
import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.util.AuthorizationHeaderUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class PermissionAdapter  implements AsyncHandlerInterceptor  {
	private static final Logger _logger = LoggerFactory.getLogger(PermissionAdapter.class);
	//无需Interceptor url
	@Autowired
	@Qualifier("applicationConfig")
	private ApplicationConfig applicationConfig;
	
	@Autowired
	@Qualifier("onlineTicketService")
	OnlineTicketService onlineTicketService;
	
	@Autowired
	@Qualifier("authJwtService")
	AuthJwtService authJwtService ;
	
	/*
	 * 请求前处理
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 _logger.trace("PermissionAdapter preHandle");
		 String  authorization = AuthorizationHeaderUtils.resolveBearer(request);
		
		 if(authJwtService.validateJwtToken(authorization)) {
			 String ticket = authJwtService.resolveTicket(authorization);
			 if(WebContext.getAuthentication()==null) {
				 OnlineTicket onlineTicket = onlineTicketService.get(ticket);
				 if(onlineTicket != null) {
					 WebContext.setAuthentication(onlineTicket.getAuthentication());
				 }
			 }
			//判断用户是否登录
	        if(WebContext.getAuthentication()==null
	                ||WebContext.getAuthentication().getAuthorities()==null){//判断用户和角色，判断用户是否登录用户
	            _logger.trace("No Authentication ... forward to /login");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
	            dispatcher.forward(request, response);
	            return false;
	        }
	        
	        //非管理员用户直接注销
	        if (!((SigninPrincipal) WebContext.getAuthentication().getPrincipal()).isRoleAdministrators()) {
	            _logger.debug("Not ADMINISTRATORS Authentication .");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/logout");
	            dispatcher.forward(request, response);
	            return false;
	        }
		 }
		
		boolean hasAccess=true;
		
		
		/*	
		boolean preHandler = super.preHandle(request, response, handler);
		
		if(preHandler) {
			preHandler = false;
			
			
			if(!preHandler){//无权限转向
				log.debug("You do not have permission to access "+accessUrl);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/accessdeny");
				dispatcher.forward(request, response);
				return false;
			}
		}*/
		return hasAccess;
	}
}
