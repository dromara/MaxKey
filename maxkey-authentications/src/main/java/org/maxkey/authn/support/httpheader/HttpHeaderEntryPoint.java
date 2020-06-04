package org.maxkey.authn.support.httpheader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.constants.ConstantsLoginType;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class HttpHeaderEntryPoint extends HandlerInterceptorAdapter {
	private static final Logger _logger = LoggerFactory.getLogger(HttpHeaderEntryPoint.class);
	
	String headerName;
    boolean enable;
    
	
	String []skipRequestURI={
			"/oauth/v20/token",
			"/oauth/v10a/request_token",
			"/oauth/v10a/access_token"
	};
	
	 @Override
	 public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 
		 if(!enable){
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
		 String httpHeaderUsername = request.getHeader(headerName);

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

	 public HttpHeaderEntryPoint() {
	        super();
	 }

    public HttpHeaderEntryPoint(String headerName, boolean enable) {
        super();
        this.headerName = headerName;
        this.enable = enable;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
	 
	
}
