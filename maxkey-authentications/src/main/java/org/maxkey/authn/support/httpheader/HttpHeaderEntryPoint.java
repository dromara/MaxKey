package org.maxkey.authn.support.httpheader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.constants.ConstantsLoginType;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class HttpHeaderEntryPoint extends HandlerInterceptorAdapter {
	private static final Logger _logger = LoggerFactory.getLogger(HttpHeaderEntryPoint.class);
	
	@Autowired 
  	@Qualifier("httpHeaderSupport")
	HttpHeaderConfig httpHeaderSupport;
	
	String []skipRequestURI={
			"/oauth/v20/token",
			"/oauth/v10a/request_token",
			"/oauth/v10a/access_token"
	};
	
	 @Override
	 public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 
		 if(!httpHeaderSupport.isEnable()){
			 return true;
		 }
		 String requestPath=request.getServletPath();
		 _logger.debug("HttpHeader Login Start ...");
		 _logger.info("Request url : "+ request.getRequestURL());
		 _logger.info("Request URI : "+ request.getRequestURI());
		 _logger.info("Request ContextPath : "+ request.getContextPath());
		 _logger.info("Request ServletPath : "+ request.getServletPath());
		 _logger.debug("RequestSessionId : "+ request.getRequestedSessionId());
		 _logger.debug("isRequestedSessionIdValid : "+ request.isRequestedSessionIdValid());
		 _logger.debug("getSession : "+ request.getSession(false));
		 
		 for(int i=0;i<skipRequestURI.length;i++){
			 if(skipRequestURI[i].indexOf(requestPath)>-1){
				 _logger.info("skip uri : "+ requestPath);
				 return true;
			 }
		 }
		
		
		 
		// session not exists，session timeout，recreate new session
		 if(request.getSession(false) == null) {
			request.getSession(true);
		 }
		 
		 _logger.info("getSession.getId : "+ request.getSession().getId());
		 String httpHeaderUsername = request.getHeader(httpHeaderSupport.getHeaderName());

		 _logger.info("HttpHeader username : " + httpHeaderUsername);
		
		
		 if(httpHeaderUsername==null||httpHeaderUsername.equals("")){
			 _logger.info("Authentication fail HttpHeader is null . ");
			 return false;
		 }
		 
		 boolean isAuthenticated=false;
		 
		 if(SecurityContextHolder.getContext().getAuthentication() == null) {
			 _logger.info("Security Authentication  is  null .");
			 isAuthenticated=false;
		 }else {
			 _logger.info("Security Authentication   not null . ");
			 UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
			 String lastSessionUserName = authenticationToken.getPrincipal().toString();
			 _logger.info("Authentication Principal : " + lastSessionUserName);
			 if (lastSessionUserName != null && !lastSessionUserName.equals(httpHeaderUsername)) {
				isAuthenticated=false;
			 }else{
				isAuthenticated=true;
			 }
		 }
		 
		 if(!isAuthenticated){
			if(WebContext.setAuthentication(httpHeaderUsername,ConstantsLoginType.HTTPHEADER,"","","success")){
				_logger.info("Authentication  "+httpHeaderUsername+" successful .");
			}
		 }
		
		 return true;
	}

	public void setHttpHeaderSupport(HttpHeaderConfig httpHeaderSupport) {
		this.httpHeaderSupport = httpHeaderSupport;
	}
	
}
