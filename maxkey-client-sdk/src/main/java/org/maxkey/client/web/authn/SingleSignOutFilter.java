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
