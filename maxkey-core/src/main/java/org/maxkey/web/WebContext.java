/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.maxkey.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.LogFactory;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.DateUtils;
import org.maxkey.util.StringGenerator;
import org.maxkey.web.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Application is common class for Web Application Context.
 * 
 * @author Crystal.Sea
 * @since 1.5
 */
public final class WebContext {
    
    final static Logger _logger = LoggerFactory.getLogger(WebContext.class);
    
    public static Properties properties;
     
    /**
     * set Current login user to session.
     * 
     * @see WebConstants.CURRENT_USER
     */
    public static void setUserInfo(UserInfo userInfo) {
        setAttribute(WebConstants.CURRENT_USER, userInfo);
    }

    /**
     * get Current login user from session.
     * 
     * @see WebConstants.CURRENT_USER
     * @return UserInfo
     */
    public static UserInfo getUserInfo() {
        return ((UserInfo) getAttribute(WebConstants.CURRENT_USER));
    }

    /**
     * set Message to session,session id is Constants.MESSAGE
     * 
     * @see WebConstants.MESSAGE
     * @param message Message
     */
    public static void setMessage(Message message) {
        setAttribute(WebConstants.CURRENT_MESSAGE, message);
    }

    /**
     * get message from session,session id is Constants.MESSAGE
     * 
     * @see WebConstants.MESSAGE
     * @return Message
     */
    public static Message getMessage() {
        return ((Message) getAttribute(WebConstants.CURRENT_MESSAGE));
    }

    /**
     * clear session Message ,session id is Constants.MESSAGE
     * 
     * @see WebConstants.MESSAGE
     */
    public static void clearMessage() {
        removeAttribute(WebConstants.CURRENT_MESSAGE);
    }

    public static void setAuthentication(Authentication authentication) {
        setAttribute(WebConstants.AUTHENTICATION, authentication);
    }

    public static Authentication getAuthentication() {
        Authentication authentication = (Authentication) getAttribute(WebConstants.AUTHENTICATION);
        return authentication;
    }

    /**
     * isAuthenticated.
     * @return isAuthenticated
     */
    public static boolean isAuthenticated() {
        if (getUserInfo() != null) {
            return true;
        }
        return false;
    }

    /**
     * get ApplicationContext from web ServletContext configuration.
     * 
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return WebApplicationContextUtils.getWebApplicationContext(
                    getSession().getServletContext());
    }

    /**
     * get bean from spring configuration by bean id.
     * 
     * @param id String
     * @return Object
     */
    public static Object getBean(String id) {
        return getApplicationContext().getBean(id);
    }

    // below method is common HttpServlet method
    /**
     * get Spring HttpServletRequest.
     * 
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) 
                    RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * get Http Context full Path.
     * 
     * @return String HttpContextPath
     */
    public static String getHttpContextPath() {
        HttpServletRequest httpServletRequest = WebContext.getRequest();
        return getHttpContextPath(httpServletRequest);
    }
    
    /**
     * get Http Context full Path,if port equals 80 or 443 is omitted.
     * 
     * @return String eg:http://192.168.1.20:9080/webcontext or
     *         http://www.website.com/webcontext
     */
    public static String getHttpContextPath(HttpServletRequest httpServletRequest) {
        ApplicationConfig applicationConfig = (
                ApplicationConfig) WebContext.getBean("applicationConfig");
        
        _logger.trace("Config ServerPrefix " + applicationConfig.getServerPrefix());
        _logger.trace("Config DomainName " + applicationConfig.getDomainName());
        _logger.trace("ServerName " + httpServletRequest.getServerName());
        
        String scheme = httpServletRequest.getScheme().toLowerCase();
        String httpContextPath = scheme + "://"+httpServletRequest.getServerName();
        int port = httpServletRequest.getServerPort();
        if(!(port==80 || port==443)){
            httpContextPath    +=  ":"+port;
        }
        httpContextPath += httpServletRequest.getContextPath() + "";
        
        _logger.trace("httpContextPath " + httpContextPath);
        return httpContextPath;

    }

    /**
     * get current Session.
     * 
     * @return HttpSession
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * get current Session,if no session ,new Session created.
     * 
     * @return HttpSession
     */
    public static HttpSession getSession(boolean create) {
        System.out.println("new Session created");
        return getRequest().getSession(create);
    }

    /**
     * set Attribute to session ,Attribute name is name,value is value.
     * 
     * @param name String
     * @param value String
     */
    public static void setAttribute(String name, Object value) {
        getSession().setAttribute(name, value);
    }

    /**
     * get Attribute from session by name.
     * 
     * @param name String
     * @return
     */
    public static Object getAttribute(String name) {
        return getSession().getAttribute(name);
    }

    /**
     * remove Attribute from session by name.
     * 
     * @param name String
     */
    public static void removeAttribute(String name) {
        getSession().removeAttribute(name);
    }

    /**
     * get Request Parameter by name.
     * 
     * @param name String
     * @return String
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * encoding encodingString by ApplicationConfig.
     * 
     * @param encodingString String
     * @return encoded String
     */
    public static String encoding(String encodingString) {
        ApplicationConfig applicationConfig = (ApplicationConfig) getBean("applicationConfig");
        return applicationConfig.getCharacterEncodingConfig().encoding(encodingString);
    }

    /**
     * get locale from Spring Resolver,if locale is null,get locale from Spring.
     * SessionLocaleResolver this is from internationalization
     * 
     * @return Locale
     */
    public static Locale getLocale() {
        Locale locale = null;
        try {
            CookieLocaleResolver cookieLocaleResolver = 
                    (CookieLocaleResolver) getBean("localeResolver");
            locale = cookieLocaleResolver.resolveLocale(getRequest());

        } catch (Exception e) {
            LogFactory.getLog(WebContext.class).debug("getLocale() error . ");
            e.printStackTrace();
            locale = RequestContextUtils.getLocale(getRequest());
        }

        return locale;
    }
    
    public static Map<String, String> getRequestParameterMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> parameters = request.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            map.put(key, values != null && values.length > 0 ? values[0] : null);
        }
        return map;
    }

    /**
     * 根据名字获取cookie.
     *
     * @param request HttpServletRequest
     * @param name  cookie名字
     * @return Cookie
     */
    public static Cookie readCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieAll(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }
 
    /**
     * 将cookie封装到Map里面.
     *
     * @param request HttpServletRequest
     * @return Map 
     */
    private static Map<String, Cookie> readCookieAll(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
 
    /**
     * 保存Cookies.
     *
     * @param response response响应
     * @param name cookie的名字
     * @param value cookie的值
     * @param time cookie的存在时间
     */
    public static HttpServletResponse setCookie(
            HttpServletResponse response, String name, String value, int time) {
        // new一个Cookie对象,键值对为参数
        Cookie cookie = new Cookie(name, value);
        // tomcat下多应用共享
        cookie.setPath("/");
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        try {
            URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 单位：秒
        cookie.setMaxAge(time);
        // 将Cookie添加到Response中,使之生效
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        return response;
    }

    /**
     * get Current Date,eg 2012-07-10.
     * 
     * @return String
     */
    public static String getCurrentDate() {
        return DateUtils.getCurrentDateAsString(DateUtils.FORMAT_DATE_YYYY_MM_DD);
    }

    /**
     * get System Menu RootId,root id is constant.
     * 
     * @return String
     */
    public static String getSystemNavRootId() {
        return "100000000000";
    }

    /**
     * get Request IpAddress,for current Request.
     * 
     * @return String,100.167.216.100
     */
    public static final String getRequestIpAddress() {
        return getRequestIpAddress(getRequest());
    }

    /**
     * get Request IpAddress by request.
     * 
     * @param request HttpServletRequest
     * @return String
     */
    public static final String getRequestIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        LogFactory.getLog(WebContext.class).debug(
                "getRequestIpAddress() RequestIpAddress:" + ipAddress);
        return ipAddress;
    }

    /**
     * captchaValid.
     * @param captcha String
     * @return
     */
    public static boolean captchaValid(String captcha) {
        if (captcha == null || !captcha
                .equals(WebContext.getSession().getAttribute(
                        WebConstants.KAPTCHA_SESSION_KEY).toString())) {
            return false;
        }
        return true;
    }

    //TODO:
    /**
     * getI18nValue.
     *  @param code String
     * @return
     */
    public static String getI18nValue(String code) {
        String message = code;
        try {
            message = getApplicationContext().getMessage(
                code.toString(), 
                null,
                getLocale());
        } catch (Exception e) {
            //
            e.printStackTrace();
        }
        return message;
    }

    /**
     * getI18nValue.
     * @param code String
     * @param filedValues Object
     * @return
     */
    public static String getI18nValue(String code, Object[] filedValues) {
        String message = code;
        try { 
            message = getApplicationContext().getMessage(
                code.toString(), 
                filedValues,
                getLocale());
        } catch (Exception e) {
            //
            e.printStackTrace();
        }
        return message;
    }
    
    //TODO:
    /**
     * getRequestLocale.
     * @return
     */
    public static String getRequestLocale() {
        return "";
    }

    /**
     * generate random Universally Unique Identifier,delete -.
     * 
     * @return String
     */
    public static String genId() {
        return (new StringGenerator()).uuidGenerate();
    }

    public static ModelAndView redirect(String redirectUrl) {
        return new ModelAndView("redirect:" + redirectUrl);
    }

    public static ModelAndView forward(String forwardUrl) {
        return new ModelAndView("forward:" + forwardUrl);
    }
}
