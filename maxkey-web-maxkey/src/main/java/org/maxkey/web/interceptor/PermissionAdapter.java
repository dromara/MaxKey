package org.maxkey.web.interceptor;

import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.SavedRequestAwareAuthenticationSuccessHandler;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 权限Interceptor处理 权限处理需在servlet.xml中配置 mvc:interceptors permission
 * 
 * @author Crystal.Sea
 *
 */
@Component
public class PermissionAdapter extends HandlerInterceptorAdapter {
    private static final Logger _logger = LoggerFactory.getLogger(PermissionAdapter.class);
    // 无需Interceptor url
    @Autowired
    @Qualifier("applicationConfig")
    private ApplicationConfig applicationConfig;


    @Autowired
    @Qualifier("savedRequestSuccessHandler")
    SavedRequestAwareAuthenticationSuccessHandler savedRequestSuccessHandler;
    
    static ConcurrentHashMap<String, String> navigationsMap = null;

    /*
     * 请求前处理 (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, 
            HttpServletResponse response, Object handler)
            throws Exception {
        _logger.trace("PermissionAdapter preHandle");
        //save  first protected url 
        SavedRequest  firstSavedRequest = (SavedRequest)WebContext.getAttribute(WebConstants.FIRST_SAVED_REQUEST_PARAMETER);
        // 判断用户是否登录, 判断用户和角色，判断用户是否登录用户
        if  (WebContext.getAuthentication() == null 
                || WebContext.getAuthentication().getAuthorities() == null) {
            //保存未认证的请求信息
            if(firstSavedRequest==null){
                RequestCache requestCache = new HttpSessionRequestCache();
                requestCache.saveRequest(request, response);
                SavedRequest  savedRequest =requestCache.getRequest(request, response);
                if(savedRequest!=null){
                    _logger.debug("first request parameter  savedRequest "+savedRequest.getRedirectUrl());
                    WebContext.setAttribute(WebConstants.FIRST_SAVED_REQUEST_PARAMETER, savedRequest);
                    savedRequestSuccessHandler.setRequestCache(requestCache);
                }
            }
            
            _logger.trace("No Authentication ... forward to /login");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
            dispatcher.forward(request, response);
            return false;
        }
        
        //认证完成，跳转到未认证请求
        if(firstSavedRequest!=null) {
            savedRequestSuccessHandler.onAuthenticationSuccess(request, response, WebContext.getAuthentication());
            WebContext.removeAttribute(WebConstants.FIRST_SAVED_REQUEST_PARAMETER);
        }

        boolean hasAccess = true;

        /*
         * boolean preHandler = super.preHandle(request, response, handler);
         * 
         * if(preHandler) { preHandler = false;
         * 
         * 
         * if(!preHandler){//无权限转向
         * log.debug("You do not have permission to access "+accessUrl);
         * RequestDispatcher dispatcher = request.getRequestDispatcher("/accessdeny");
         * dispatcher.forward(request, response); return false; } }
         */
        return hasAccess;
    }
}
