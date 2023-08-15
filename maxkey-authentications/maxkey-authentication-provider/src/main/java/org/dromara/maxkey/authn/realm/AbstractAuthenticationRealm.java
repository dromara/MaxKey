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
 

package org.dromara.maxkey.authn.realm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authn.realm.ldap.LdapAuthenticationRealmService;
import org.dromara.maxkey.entity.HistoryLogin;
import org.dromara.maxkey.entity.Roles;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.repository.LoginHistoryRepository;
import org.dromara.maxkey.persistence.repository.LoginRepository;
import org.dromara.maxkey.persistence.repository.PasswordPolicyValidator;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.util.DateUtils;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * AbstractAuthenticationRealm.
 * @author Crystal.Sea
 *
 */
public abstract class AbstractAuthenticationRealm {
    private static Logger _logger = LoggerFactory.getLogger(AbstractAuthenticationRealm.class);

    protected JdbcTemplate jdbcTemplate;
    
    protected PasswordPolicyValidator passwordPolicyValidator;
    
    protected LoginRepository loginRepository;

    protected LoginHistoryRepository loginHistoryRepository;
    
    protected UserInfoService userInfoService;
    
    protected LdapAuthenticationRealmService ldapAuthenticationRealmService;
   

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

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public UserInfo loadUserInfo(String username, String password) {
        return loginRepository.find(username, password);
    }

    public abstract boolean passwordMatches(UserInfo userInfo, String password);
    
    public List<Roles> queryGroups(UserInfo userInfo) {
       return loginRepository.queryRoles(userInfo);
    }

    /**
     * grant Authority by userinfo
     * 
     * @param userInfo
     * @return ArrayList<GrantedAuthority>
     */
    public ArrayList<GrantedAuthority> grantAuthority(UserInfo userInfo) {
        return loginRepository.grantAuthority(userInfo);
    }
    
    /**
     * grant Authority by grantedAuthoritys
     * 
     * @param grantedAuthoritys
     * @return ArrayList<GrantedAuthority Apps>
     */
    public ArrayList<GrantedAuthority> queryAuthorizedApps(ArrayList<GrantedAuthority> grantedAuthoritys) {
        return loginRepository.queryAuthorizedApps(grantedAuthoritys);
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
        HistoryLogin historyLogin = new HistoryLogin();
        historyLogin.setSessionId(WebContext.genId());
        historyLogin.setSessionStatus(7);
        Authentication  authentication  = (Authentication ) WebContext.getAttribute(WebConstants.AUTHENTICATION);
        if(authentication != null 
        		&& authentication.getPrincipal() instanceof SignPrincipal) {
        	  historyLogin.setSessionStatus(1);
              historyLogin.setSessionId(userInfo.getSessionId());
        }
        
        _logger.debug("user session id is {} . ",historyLogin.getSessionId());
        
        userInfo.setLastLoginTime(DateUtils.formatDateTime(new Date()));
        userInfo.setLastLoginIp(WebContext.getRequestIpAddress());
        
        Browser browser = resolveBrowser();
        historyLogin.setBrowser(browser.getName());
        historyLogin.setPlatform(browser.getPlatform());
        historyLogin.setSourceIp(userInfo.getLastLoginIp());
        historyLogin.setProvider(provider);
        historyLogin.setCode(code);
        historyLogin.setLoginType(type);
        historyLogin.setMessage(message);
        historyLogin.setUserId(userInfo.getId());
        historyLogin.setUsername(userInfo.getUsername());
        historyLogin.setDisplayName(userInfo.getDisplayName());
        historyLogin.setInstId(userInfo.getInstId());
        
        loginHistoryRepository.login(historyLogin);
        
        loginRepository.updateLastLogin(userInfo);

        return true;
    }
    
    public Browser  resolveBrowser() {
        Browser browser =new Browser();
        String userAgent = WebContext.getRequest().getHeader("User-Agent");
        String[] arrayUserAgent = null;
        if (userAgent.indexOf("MSIE") > 0) {
            arrayUserAgent = userAgent.split(";");
            browser.setName(arrayUserAgent[1].trim());
            browser.setPlatform(arrayUserAgent[2].trim());
        } else if (userAgent.indexOf("Trident") > 0) {
            arrayUserAgent = userAgent.split(";");
            browser.setName( "MSIE/" + arrayUserAgent[3].split("\\)")[0]);

            browser.setPlatform( arrayUserAgent[0].split("\\(")[1]);
        } else if (userAgent.indexOf("Chrome") > 0) {
            arrayUserAgent = userAgent.split(" ");
            // browser=arrayUserAgent[8].trim();
            for (int i = 0; i < arrayUserAgent.length; i++) {
                if (arrayUserAgent[i].contains("Chrome")) {
                    browser.setName( arrayUserAgent[i].trim());
                    browser.setName( browser.getName().substring(0, browser.getName().indexOf('.')));
                }
            }
            browser.setPlatform( (arrayUserAgent[1].substring(1) + " " + arrayUserAgent[2] + " "
                    + arrayUserAgent[3].substring(0, arrayUserAgent[3].length() - 1)).trim());
        } else if (userAgent.indexOf("Firefox") > 0) {
            arrayUserAgent = userAgent.split(" ");
            for (int i = 0; i < arrayUserAgent.length; i++) {
                if (arrayUserAgent[i].contains("Firefox")) {
                    browser.setName( arrayUserAgent[i].trim());
                    browser.setName(browser.getName().substring(0, browser.getName().indexOf('.')));
                }
            }
            browser.setPlatform( (arrayUserAgent[1].substring(1) + " " + arrayUserAgent[2] + " "
                    + arrayUserAgent[3].substring(0, arrayUserAgent[3].length() - 1)).trim());

        }
        
        return browser;
    }
    
    
    public class Browser{
        
        private  String platform;
        
        private  String name;
        
        public String getPlatform() {
            return platform;
        }
        public void setPlatform(String platform) {
            this.platform = platform;
        }
        public String getName() {
            return name;
        }
        public void setName(String browser) {
            this.name = browser;
        }
        
        
    }
    
}
