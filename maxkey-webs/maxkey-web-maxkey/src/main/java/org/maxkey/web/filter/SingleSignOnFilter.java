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
 

package org.maxkey.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  SingleSignOnFilter.
 * @author Crystal.Sea
 */

public class SingleSignOnFilter implements Filter {
    private static final Logger _logger = LoggerFactory.getLogger(SingleSignOnFilter.class);

    /**
     *doFilter.
     */
    public void doFilter(ServletRequest request, 
            ServletResponse response, FilterChain chain)throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        // 浠巗ession涓幏鍙栧瓨鏀剧殑appid
        String appId = (String) session.getAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID);
        // 鑾峰彇鏈�鍚庝竴涓�"/"鐨勬暟鎹綔涓篴ppid锛屼繚瀛樺湪session涓�
        if (StringUtils.isNullOrBlank(appId)) {
            String uir = httpServletRequest.getRequestURI();
            session.setAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID, 
                                uir.substring(uir.lastIndexOf("/") + 1));
            session.setAttribute("protocol", "formbase");
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
        _logger.debug(" destroy.");
    }

    public void init(FilterConfig config) throws ServletException {
        _logger.debug(" init.");
    }
}
