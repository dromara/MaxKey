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
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
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
       
        final Apps app = (Apps)WebContext.getAttribute(AuthorizeBaseEndpoint.class.getName());
        String sessionId = (String)WebContext.getAttribute(WebConstants.CURRENT_USER_SESSION_ID);
        final UserInfo userInfo = WebContext.getUserInfo();
        _logger.debug("sessionId : " + sessionId + " ,appId : " + app.getId());
        HistoryLoginApps historyLoginApps = new HistoryLoginApps();
        historyLoginApps.setId(historyLoginApps.generateId());
        historyLoginApps.setAppId(app.getId());
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
