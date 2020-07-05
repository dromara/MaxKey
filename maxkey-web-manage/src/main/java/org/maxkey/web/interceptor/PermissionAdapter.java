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

import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 权限Interceptor处理
 * 权限处理需在servlet.xml中配置
 *  mvc:interceptors  permission
 * @author Crystal.Sea
 *
 */
@Component
public class PermissionAdapter extends HandlerInterceptorAdapter {
	private static final Logger _logger = LoggerFactory.getLogger(PermissionAdapter.class);
	//无需Interceptor url
	@Autowired
	@Qualifier("applicationConfig")
	private ApplicationConfig applicationConfig;
	
	static  ConcurrentHashMap<String ,String >navigationsMap=null;
	/*
	 * 请求前处理
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 _logger.trace("PermissionAdapter preHandle");
		
		//判断用户是否登录
		if(WebContext.getAuthentication()==null||WebContext.getAuthentication().getAuthorities()==null){//判断用户和角色，判断用户是否登录用户
			_logger.trace("No Authentication ... forward to /login");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
			dispatcher.forward(request, response);
			return false;
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
