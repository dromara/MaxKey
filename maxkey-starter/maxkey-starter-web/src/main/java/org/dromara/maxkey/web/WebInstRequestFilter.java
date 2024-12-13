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
 

package org.dromara.maxkey.web;

import java.io.IOException;


import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.entity.Institutions;
import org.dromara.maxkey.persistence.service.InstitutionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class WebInstRequestFilter  extends GenericFilterBean {
	static final  Logger _logger = LoggerFactory.getLogger(WebInstRequestFilter.class);	
	
	public static final  String  HEADER_HOST 		= "host";
	
	public static final  String  HEADER_HOSTNAME 	= "hostname";
	
	public static final  String  HEADER_ORIGIN		= "Origin";	
	
	InstitutionsService institutionsService;
	
	ApplicationConfig applicationConfig;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		_logger.trace("WebInstRequestFilter");
		HttpServletRequest request= ((HttpServletRequest)servletRequest);
		
		if(request.getSession().getAttribute(WebConstants.CURRENT_INST) == null) {
			if(_logger.isTraceEnabled()) {WebContext.printRequest(request);}
			String host = request.getHeader(HEADER_HOSTNAME);
			_logger.trace("hostname {}",host);
			if(StringUtils.isEmpty(host)) {
				host = request.getHeader(HEADER_HOST);
				_logger.trace("host {}",host);
			}
			if(StringUtils.isEmpty(host)) {
				host = applicationConfig.getDomainName();
				_logger.trace("config domain {}",host);
			}
			if(host.indexOf(":")> -1 ) {
				host = host.split(":")[0];
				_logger.trace("domain split {}",host);
			}
			_logger.trace("host {}",host);
			Institutions institution = institutionsService.get(host);
			_logger.trace("{}" ,institution);
			request.getSession().setAttribute(WebConstants.CURRENT_INST, institution);
			
			String origin = request.getHeader(HEADER_ORIGIN);
			if(StringUtils.isEmpty(origin)) {
				origin = applicationConfig.getFrontendUri();
			}
			_logger.trace("origin {}" ,origin);
		}
        chain.doFilter(servletRequest, servletResponse);
	}

	public WebInstRequestFilter(InstitutionsService institutionsService,ApplicationConfig applicationConfig) {
		super();
		this.institutionsService = institutionsService;
		this.applicationConfig = applicationConfig;
	}

}
