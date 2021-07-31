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

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.maxkey.constants.ConstantsTimeInterval;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.apps.Apps;
import org.maxkey.persistence.service.AppsService;
import org.maxkey.util.AuthorizationHeaderCredential;
import org.maxkey.util.AuthorizationHeaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
    protected static final UserManagedCache<String, Apps> appsCacheStore = 
            UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, Apps.class)
                .withExpiry(
                    ExpiryPolicyBuilder.timeToLiveExpiration(
                        Duration.ofMinutes(ConstantsTimeInterval.ONE_HOUR)
                    )
                )
                .build(true);
    
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
		 
		AuthorizationHeaderCredential headerCredential = AuthorizationHeaderUtils.resolve(authorization);
		 
		//判断应用的AppId和Secret
		if(headerCredential != null){
			String appId = headerCredential.getUsername();
			String appSecret = headerCredential.getCredential();
		    _logger.trace("appId "+ appId+" , appSecret " + appSecret);
		    Apps app = appsCacheStore.get(appId);
		    if (app == null) {
		    	app = appsService.get(appId);
		    	appsCacheStore.put(appId, app);
		    }
		    
		    _logger.debug("App Info "+ app.getSecret());
		    if(app != null && passwordReciprocal.matches(appSecret, app.getSecret())) {
		        return true;
		    }
		}
		
		
		_logger.trace("No Authentication ... forward to /login");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);
        
		return false;
	}
}
