/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;


import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class WebXssRequestFilter  extends GenericFilterBean {

	final static Logger _logger = LoggerFactory.getLogger(GenericFilterBean.class);	
	
	final static ConcurrentHashMap <String,String> skipUrlMap = new  ConcurrentHashMap <String,String>();
	final static ConcurrentHashMap <String,String> skipParameterName = new  ConcurrentHashMap <String,String>();
	
	static {
		//add or update
		skipUrlMap.put("/notices/add", "/notices/add");
		skipUrlMap.put("/notices/update", "/notices/update");
		skipUrlMap.put("/institutions/update","/institutions/update");
		skipUrlMap.put("/localization/update","/localization/update");
		skipUrlMap.put("/apps/updateExtendAttr","/apps/updateExtendAttr");
		
		//authz
		skipUrlMap.put("/authz/cas", "/authz/cas");
		skipUrlMap.put("/authz/cas/", "/authz/cas/");
		skipUrlMap.put("/authz/cas/login", "/authz/cas/login");
		skipUrlMap.put("/authz/oauth/v20/authorize", "/authz/oauth/v20/authorize");
		//TENCENT_IOA
		skipUrlMap.put("/oauth2/authorize", "/oauth2/authorize");
		
		skipParameterName.put("relatedPassword", "relatedPassword");
		skipParameterName.put("oldPassword", "oldPassword");
		skipParameterName.put("password", "password");
		skipParameterName.put("confirmpassword", "confirmpassword");
		skipParameterName.put("credentials", "credentials");
		skipParameterName.put("clientSecret", "clientSecret");
		skipParameterName.put("appSecret", "appSecret");
		skipParameterName.put("sharedSecret", "sharedSecret");
		skipParameterName.put("secret", "secret");
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		_logger.trace("WebXssRequestFilter");
		boolean isWebXss = false;
		HttpServletRequest request= ((HttpServletRequest)servletRequest);
		if(_logger.isTraceEnabled()) {WebContext.printRequest(request);}
		if(skipUrlMap.containsKey(request.getRequestURI().substring(request.getContextPath().length()))) {
			isWebXss = false;
		}else {
	        Enumeration<String> parameterNames = request.getParameterNames();
	        while (parameterNames.hasMoreElements()) {
	          String key = (String) parameterNames.nextElement();
	          if(skipParameterName.containsKey(key)) {continue;}
	          
	          String value = request.getParameter(key);
	          _logger.trace("parameter name "+key +" , value " + value);
	          String tempValue = value;
	          if(!StringEscapeUtils.escapeHtml4(tempValue).equals(value)
	        		  ||tempValue.toLowerCase().indexOf("script")>-1
	        		  ||tempValue.toLowerCase().replace(" ", "").indexOf("eval(")>-1) {
	        	  isWebXss = true;
	        	  _logger.error("parameter name "+key +" , value " + value 
	        			  		+ ", contains dangerous content ! ");
	        	  break;
	          }
	        }
		}
        if(!isWebXss) {
        	chain.doFilter(request, response);
        }  
	}

}
