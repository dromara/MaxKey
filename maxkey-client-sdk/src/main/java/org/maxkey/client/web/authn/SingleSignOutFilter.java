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
 

package org.maxkey.client.web.authn;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author Crystal.Sea
 */
public class SingleSignOutFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(SingleSignOutFilter.class);
	private String singleSignOutEndpoint;
	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		HttpSession session = httpServletRequest.getSession();
		session.removeAttribute(AuthenticationFilter.CONST_CONNSEC_USERINFO);
		session.invalidate();
		
		httpServletResponse.sendRedirect(singleSignOutEndpoint);
		
		chain.doFilter(request, response);
	}
		
	public void destroy() {
		this.destroy();
	}

	public void init(FilterConfig config) throws ServletException {
		this.singleSignOutEndpoint=config.getInitParameter("singleSignOutEndpoint");
		log.debug(" init.");
	}
}
