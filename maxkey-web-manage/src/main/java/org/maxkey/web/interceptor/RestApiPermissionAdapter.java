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
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.domain.apps.Apps;
import org.maxkey.persistence.service.AppsService;
import org.maxkey.util.AuthorizationHeaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * basic认证Interceptor处理.
 * @author Crystal.Sea
 *
 */
@Component
public class RestApiPermissionAdapter extends HandlerInterceptorAdapter {
	private static final Logger _logger = LoggerFactory.getLogger(RestApiPermissionAdapter.class);
	
	@Autowired
    AppsService appsService;
	
	@Autowired
    @Qualifier("passwordReciprocal")
    protected PasswordReciprocal passwordReciprocal;
	
	static  ConcurrentHashMap<String ,String >navigationsMap=null;
	
	/*
	 * 请求前处理
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 _logger.trace("RestApiPermissionAdapter preHandle");
		String  authorization = request.getHeader(AuthorizationHeaderUtils.AUTHORIZATION_HEADERNAME);
		 
		 String [] basicUserPass = AuthorizationHeaderUtils.resolveBasic(authorization);
		 
		//判断应用的AppId和Secret
		if(basicUserPass != null && basicUserPass.length==2){
		    _logger.trace(""+ basicUserPass[0]+":"+basicUserPass[1]);
		    Apps app = appsService.get(basicUserPass[0]);
		    
		    _logger.debug("App Info "+ app.getSecret());
		    if(app != null && passwordReciprocal.encode(basicUserPass[1]).equalsIgnoreCase(app.getSecret())) {
		        return true;
		    }
		}
		
		
		_logger.trace("No Authentication ... forward to /login");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);
        
		return false;
	}
}
