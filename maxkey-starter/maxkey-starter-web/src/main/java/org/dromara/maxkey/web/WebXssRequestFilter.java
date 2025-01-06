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
import java.util.regex.Pattern;

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

	static final  Logger _logger = LoggerFactory.getLogger(WebXssRequestFilter.class);	
	
	static final  ConcurrentHashMap <String,String> skipUrlMap = new  ConcurrentHashMap <>();
	static final  ConcurrentHashMap <String,String> skipParameterName = new  ConcurrentHashMap <>();
	
	/**
	 * 特殊字符 ' -- #
	 */
	public final static Pattern specialCharacterRegex = Pattern.compile(".*((\\%27)|(')|(\\')|(--)|(\\-\\-)|(\\%23)|(#)).*", Pattern.CASE_INSENSITIVE);
	
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
		String  requestURL =request.getRequestURI().substring(request.getContextPath().length());
		if(skipUrlMap.containsKey(requestURL)) {
			_logger.trace("skip URL {}",requestURL);
		}else {
	        Enumeration<String> parameterNames = request.getParameterNames();
	        while (parameterNames.hasMoreElements()) {
	          String key = parameterNames.nextElement();
	          if(!skipParameterName.containsKey(key)) {
		          String value = request.getParameter(key);
		          _logger.trace("parameter name {} , value {}" , key, value);
		          String tempValue = value;
		          String  lowerCaseTempValue = tempValue.toLowerCase();
		          /**
		           * StringEscapeUtils.escapeHtml4
		           * " 转义为 &quot;
		           * & 转义为 &amp;
		           * < 转义为 &lt;
		           * > 转义为 &gt;
		           * 
		           * 以下符号过滤
		           * ' 
		           * --
		           * #
		           * 
		           * script
		           * eval
		           * 
		           */
		          if(!StringEscapeUtils.escapeHtml4(tempValue).equals(value)
		        		  ||specialCharacterRegex.matcher(value).matches()
		        		  ||lowerCaseTempValue.indexOf("script")>-1
		        		  ||lowerCaseTempValue.replace(" ", "").indexOf("eval(")>-1) {
		        	  isWebXss = true;
		        	  _logger.error("dangerous ! parameter {} , value {}",key,value);
		        	  break;
		          }
	          }
	        }
		}
        if(!isWebXss) {
        	chain.doFilter(request, response);
        }  
	}

}
