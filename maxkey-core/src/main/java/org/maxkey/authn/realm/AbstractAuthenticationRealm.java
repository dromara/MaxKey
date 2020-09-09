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
 

package org.maxkey.authn.realm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.domain.Groups;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.db.LoginHistoryService;
import org.maxkey.persistence.db.PasswordPolicyValidator;
import org.maxkey.persistence.db.LoginService;
import org.maxkey.util.DateUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;

/**
 * AbstractAuthenticationRealm.
 * @author Crystal.Sea
 *
 */
public abstract class AbstractAuthenticationRealm {
    private static Logger _logger = LoggerFactory.getLogger(AbstractAuthenticationRealm.class);

    protected JdbcTemplate jdbcTemplate;
    
    protected boolean provisioning;
    
    @Autowired
    protected PasswordPolicyValidator passwordPolicyValidator;
    
    @Autowired
    protected LoginService loginService;
    
    @Autowired
    protected LoginHistoryService loginHistoryService;

    @Autowired
    @Qualifier("remeberMeService")
    protected AbstractRemeberMeService remeberMeService;

    /**
     * 
     */
    public AbstractAuthenticationRealm() {

    }

    public AbstractAuthenticationRealm(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PasswordPolicyValidator getPasswordPolicyValidator() {
        return passwordPolicyValidator;
    }

    public LoginService getUserInfoLoginService() {
        return loginService;
    }

    public UserInfo loadUserInfo(String username, String password) {
        return loginService.loadUserInfo(username, password);
    }

    public abstract boolean passwordMatches(UserInfo userInfo, String password);
    

    public static boolean isAuthenticated() {
        if (WebContext.getUserInfo() != null) {
            return true;
        } else {
            return false;
        }
    }


    public List<Groups> queryGroups(UserInfo userInfo) {
       return loginService.queryGroups(userInfo);
    }

    /**
     * grant Authority by userinfo
     * 
     * @param userInfo
     * @return ArrayList<GrantedAuthority>
     */
    public ArrayList<GrantedAuthority> grantAuthority(UserInfo userInfo) {
        return loginService.grantAuthority(userInfo);
    }

    /**
     * login log write to log db
     * 
     * @param uid
     * @param j_username
     * @param type
     * @param code
     * @param message
     */
    public boolean insertLoginHistory(UserInfo userInfo, String type, String provider, String code, String message) {
        String sessionId = WebContext.genId();
        WebContext.setAttribute(WebConstants.CURRENT_USER_SESSION_ID, sessionId);
        userInfo.setLastLoginTime(DateUtils.formatDateTime(new Date()));
        userInfo.setLastLoginIp(WebContext.getRequestIpAddress());
        String platform = "";
        String browser = "";
        String userAgent = WebContext.getRequest().getHeader("User-Agent");
        String[] arrayUserAgent = null;
        if (userAgent.indexOf("MSIE") > 0) {
            arrayUserAgent = userAgent.split(";");
            browser = arrayUserAgent[1].trim();
            platform = arrayUserAgent[2].trim();
        } else if (userAgent.indexOf("Trident") > 0) {
            arrayUserAgent = userAgent.split(";");
            browser = "MSIE/" + arrayUserAgent[3].split("\\)")[0];
            ;
            platform = arrayUserAgent[0].split("\\(")[1];
        } else if (userAgent.indexOf("Chrome") > 0) {
            arrayUserAgent = userAgent.split(" ");
            // browser=arrayUserAgent[8].trim();
            for (int i = 0; i < arrayUserAgent.length; i++) {
                if (arrayUserAgent[i].contains("Chrome")) {
                    browser = arrayUserAgent[i].trim();
                    browser = browser.substring(0, browser.indexOf('.'));
                }
            }
            platform = (arrayUserAgent[1].substring(1) + " " + arrayUserAgent[2] + " "
                    + arrayUserAgent[3].substring(0, arrayUserAgent[3].length() - 1)).trim();
        } else if (userAgent.indexOf("Firefox") > 0) {
            arrayUserAgent = userAgent.split(" ");
            for (int i = 0; i < arrayUserAgent.length; i++) {
                if (arrayUserAgent[i].contains("Firefox")) {
                    browser = arrayUserAgent[i].trim();
                    browser = browser.substring(0, browser.indexOf('.'));
                }
            }
            platform = (arrayUserAgent[1].substring(1) + " " + arrayUserAgent[2] + " "
                    + arrayUserAgent[3].substring(0, arrayUserAgent[3].length() - 1)).trim();

        }

        loginHistoryService.login(userInfo,sessionId, type, message, code, provider, browser, platform);
        
        loginService.setLastLoginInfo(userInfo);

        return true;
    }

    /**
     * logout user and remove RemeberMe token 
     * @param response
     * @return
     */
    public boolean logout(HttpServletResponse response) {
        if (isAuthenticated()) {
            Object sessionIdAttribute = WebContext.getAttribute(WebConstants.CURRENT_USER_SESSION_ID);
            UserInfo userInfo = WebContext.getUserInfo();
            userInfo.setLastLogoffTime(DateUtils.formatDateTime(new Date()));
            
            if (sessionIdAttribute != null) {
                remeberMeService.removeRemeberMe(response);

                loginHistoryService.logoff(userInfo.getLastLogoffTime(), sessionIdAttribute.toString());
            }
            
            loginService.setLastLogoffInfo(userInfo);
            
            _logger.debug("Session " + WebContext.getAttribute(WebConstants.CURRENT_USER_SESSION_ID) + ", user "
                    + userInfo.getUsername() + " Logout, datetime " + userInfo.getLastLogoffTime() + " .");
        }
        return true;

    }
    
    
}
