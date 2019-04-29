package org.maxkey.web.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.dao.service.ApplicationsService;
import org.maxkey.dao.service.LoginAppsHistoryService;
import org.maxkey.domain.LoginAppsHistory;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Applications;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginAppHistoryAdapter extends HandlerInterceptorAdapter {
	private static final Logger _logger = LoggerFactory.getLogger(LoginAppHistoryAdapter.class);

	@Autowired
	LoginAppsHistoryService loginAppsHistoryService;
	
	@Autowired
	@Qualifier("applicationsService")
	protected ApplicationsService applicationsService;
	/*
	 *  (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		_logger.debug("postHandle");
		String appId=null;
		if(WebContext.getAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID)!=null){
			appId=WebContext.getAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID).toString();
		}
		if(appId==null){
			PreLoginAppAdapter.getAppIdFromRequestURI(request);
		}
		//Applications  app=applicationsService.get(appId);
		Applications  app=(Applications)WebContext.getAttribute(AuthorizeBaseEndpoint.class.getName());
		String sessionId=(String)WebContext.getAttribute(WebConstants.CURRENT_USER_SESSION_ID);
		UserInfo userInfo =WebContext.getUserInfo();
		_logger.debug("sessionId : "+sessionId+" ,appId : "+appId);
		LoginAppsHistory loginAppsHistory=new LoginAppsHistory();
		loginAppsHistory.setId(loginAppsHistory.generateId());
		loginAppsHistory.setAppId(appId);
		loginAppsHistory.setSessionId(sessionId);
		loginAppsHistory.setAppName(app.getName());
		loginAppsHistory.setUid(userInfo.getId());
		loginAppsHistory.setUsername(userInfo.getUsername());
		loginAppsHistory.setDisplayName(userInfo.getDisplayName());
		loginAppsHistoryService.insert(loginAppsHistory);
		WebContext.removeAttribute(WebConstants.CURRENT_SINGLESIGNON_URI);
		WebContext.removeAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID);
	}
}
