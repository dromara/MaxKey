package org.maxkey.web;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

public class WebXssRequestFilter  extends GenericFilterBean {

	final static Logger _logger = LoggerFactory.getLogger(GenericFilterBean.class);	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		_logger.trace("WebXssRequestFilter");
		boolean isWebXss = false;
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
        if(!isWebXss) {
        	chain.doFilter(request, response);
        }  
	}

}
