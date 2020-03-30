package org.maxkey.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.maxkey.dao.service.AppsService;
import org.maxkey.dao.service.HistoryLoginAppsService;
import org.maxkey.domain.HistoryLoginApps;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Apps;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class HistoryLoginAppAdapter extends HandlerInterceptorAdapter {
    private static final Logger _logger = LoggerFactory.getLogger(HistoryLoginAppAdapter.class);

    @Autowired
    HistoryLoginAppsService historyLoginAppsService;

    @Autowired
    @Qualifier("appsService")
    protected AppsService appsService;
    
    /**
     * postHandle .
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(
     *          javax.servlet.http.HttpServletRequest, 
     *          javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,ModelAndView modelAndView) throws Exception {
        _logger.debug("postHandle");
        String appId = null;
        if (WebContext.getAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID) != null) {
            appId = WebContext.getAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID).toString();
        }
        if (appId == null) {
            PreLoginAppAdapter.getAppIdFromRequestUrl(request);
        }
        //Applications  app=applicationsService.get(appId);
        final Apps app = (Apps)WebContext.getAttribute(AuthorizeBaseEndpoint.class.getName());
        String sessionId = (String)WebContext.getAttribute(WebConstants.CURRENT_USER_SESSION_ID);
        final UserInfo userInfo = WebContext.getUserInfo();
        _logger.debug("sessionId : " + sessionId + " ,appId : " + appId);
        HistoryLoginApps historyLoginApps = new HistoryLoginApps();
        historyLoginApps.setId(historyLoginApps.generateId());
        historyLoginApps.setAppId(appId);
        historyLoginApps.setSessionId(sessionId);
        historyLoginApps.setAppName(app.getName());
        historyLoginApps.setUid(userInfo.getId());
        historyLoginApps.setUsername(userInfo.getUsername());
        historyLoginApps.setDisplayName(userInfo.getDisplayName());
        historyLoginAppsService.insert(historyLoginApps);
        WebContext.removeAttribute(WebConstants.CURRENT_SINGLESIGNON_URI);
        WebContext.removeAttribute(WebConstants.SINGLE_SIGN_ON_APP_ID);
    }
}
