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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class PreLoginAppAdapter extends HandlerInterceptorAdapter {

    private static final Logger _logger = LoggerFactory.getLogger(PreLoginAppAdapter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, 
            HttpServletResponse response, Object handler)
            throws Exception {
        _logger.debug("preHandle");
        UserInfo userInfo = WebContext.getUserInfo();
        String redirect_uri = request.getRequestURL().toString();
        String appId = getAppIdFromRequestUrl(request);
        _logger.debug("preHandle app Id " + appId);
        Object singlesignon_uri = WebContext.getAttribute(WebConstants.CURRENT_SINGLESIGNON_URI);
        if (singlesignon_uri != null && singlesignon_uri.equals(redirect_uri)) {
            return true;
        }
        /*
         * if(userInfo.getProtectedAppsMap().get(appId)!=null){
         * 
         * request.setAttribute("redirect_uri",redirect_uri);
         * _logger.debug(""+redirect_uri); RequestDispatcher dispatcher =
         * request.getRequestDispatcher("/authorize/protected/forward");
         * dispatcher.forward(request, response); return false; }
         */

        return true;
    }

    /**
     * Request URL .
     * @param request http
     * @return .
     */
    public static String getAppIdFromRequestUrl(HttpServletRequest request) {
        String[] uri = request.getRequestURI().split("/");
        String appId = uri[uri.length - 1];
        return appId;
    }
}
