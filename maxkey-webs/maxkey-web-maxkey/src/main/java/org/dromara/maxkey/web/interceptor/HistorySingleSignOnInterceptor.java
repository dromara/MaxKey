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
 

package org.dromara.maxkey.web.interceptor;

import java.util.Date;

import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.history.HistoryLoginApps;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.maxkey.persistence.service.HistoryLoginAppsService;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HistorySingleSignOnInterceptor  implements AsyncHandlerInterceptor  {
    private static final Logger logger = LoggerFactory.getLogger(HistorySingleSignOnInterceptor.class);

    @Autowired
    HistoryLoginAppsService historyLoginAppsService;

    @Autowired
    AppsService appsService;

    /**
     * postHandle .
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(
     *          javax.servlet.http.HttpServletRequest, 
     *          javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,ModelAndView modelAndView) throws Exception {
        logger.debug("postHandle");
       
        final Apps app = (Apps)WebContext.getAttribute(WebConstants.AUTHORIZE_SIGN_ON_APP);
        
        SignPrincipal principal = AuthorizationUtils.getPrincipal();
        if(principal != null && app !=null) {
        	final UserInfo userInfo = principal.getUserInfo();
        	String sessionId = principal.getSessionId();
        	 logger.debug("sessionId : {} , appId {}" , sessionId , app.getId());
             HistoryLoginApps historyLoginApps = new HistoryLoginApps();
             historyLoginApps.setAppId(app.getId());
             historyLoginApps.setSessionId(sessionId);
             historyLoginApps.setAppName(app.getAppName());
             historyLoginApps.setUserId(userInfo.getId());
             historyLoginApps.setUsername(userInfo.getUsername());
             historyLoginApps.setDisplayName(userInfo.getDisplayName());
             historyLoginApps.setInstId(userInfo.getInstId());
             historyLoginApps.setLoginTime(new Date());
             historyLoginAppsService.insert(historyLoginApps);
             WebContext.removeAttribute(WebConstants.CURRENT_SINGLESIGNON_URI);
             WebContext.removeAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID);
        }
       
    }
}
