package org.maxkey.web.interceptor;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.maxkey.authz.oauth2.provider.token.DefaultTokenServices;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.util.AuthorizationHeaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * OAuth v2.0 accessToken认证Interceptor处理.
 * @author Crystal.Sea
 *
 */
@Component
public class Oauth20ApiPermissionAdapter extends HandlerInterceptorAdapter {
	private static final Logger _logger = LoggerFactory.getLogger(Oauth20ApiPermissionAdapter.class);
	
	@Autowired
    @Qualifier("passwordReciprocal")
    protected PasswordReciprocal passwordReciprocal;
	
	@Autowired
    @Qualifier("oauth20TokenServices")
    private DefaultTokenServices oauth20tokenServices;
	
	static  ConcurrentHashMap<String ,String >navigationsMap=null;
	
	/*
	 * 请求前处理
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		 _logger.trace("Oauth20ApiPermissionAdapter preHandle");
		String  authorization = request.getHeader(AuthorizationHeaderUtils.AUTHORIZATION_HEADERNAME);
		 
		 String accessToken = AuthorizationHeaderUtils.resolveBearer(authorization);
		 OAuth2Authentication authentication = oauth20tokenServices.loadAuthentication(accessToken);
		 
		//判断应用的accessToken信息
		if(authentication != null ){
		    _logger.trace("authentication "+ authentication);
		    return true;
		}
		
		_logger.trace("No Authentication ... forward to /login");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);
        
		return false;
	}
}
