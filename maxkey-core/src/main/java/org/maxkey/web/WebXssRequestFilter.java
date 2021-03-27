package org.maxkey.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

public class WebXssRequestFilter  extends GenericFilterBean {

	final static Logger _logger = LoggerFactory.getLogger(GenericFilterBean.class);	
	
	final static ConcurrentHashMap <String,String> skipUrlMap = new  ConcurrentHashMap <String,String>();
	
	static {
		skipUrlMap.put("/notices/add", "");
		skipUrlMap.put("/notices/update", "");
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		_logger.trace("WebXssRequestFilter");
		
		boolean isWebXss = false;
		HttpServletRequest request= ((HttpServletRequest)servletRequest);
		String requestURI=request.getRequestURI();
		_logger.trace("getContextPath " +request.getContextPath());
		_logger.trace("getRequestURL " + ((HttpServletRequest)request).getRequestURI());
		_logger.trace("URL " +requestURI.substring(request.getContextPath().length()));
		
		if(skipUrlMap.containsKey(requestURI.substring(request.getContextPath().length()))) {
			isWebXss = false;
		}else {
	        Enumeration<String> parameterNames = request.getParameterNames();
	        while (parameterNames.hasMoreElements()) {
	          String key = (String) parameterNames.nextElement();
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
