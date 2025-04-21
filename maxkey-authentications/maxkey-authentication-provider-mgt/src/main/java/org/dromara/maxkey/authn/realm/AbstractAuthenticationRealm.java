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

import java.util.Date;
import java.util.List;

import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authn.realm.ldap.LdapAuthenticationRealmService;
import org.dromara.maxkey.authn.session.SessionCategory;
import org.dromara.maxkey.entity.history.HistoryLogin;
import org.dromara.maxkey.entity.idm.Groups;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.ip2location.IpLocationParser;
import org.dromara.maxkey.ip2location.Region;
import org.dromara.maxkey.persistence.service.HistoryLoginService;
import org.dromara.maxkey.persistence.service.LoginService;
import org.dromara.maxkey.persistence.service.PasswordPolicyValidatorService;
import org.dromara.maxkey.persistence.service.UserInfoService;
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
    private static final Logger _logger = LoggerFactory.getLogger(AbstractAuthenticationRealm.class);

    protected JdbcTemplate jdbcTemplate;
    
    protected PasswordPolicyValidatorService passwordPolicyValidatorService;
    
    protected LoginService loginService;

    protected HistoryLoginService historyLoginService;
    
    protected UserInfoService userInfoService;
    
    protected LdapAuthenticationRealmService ldapAuthenticationRealmService;
    
    protected IpLocationParser ipLocationParser;
   

    /**
     * 
     */
    public AbstractAuthenticationRealm() {

    }

    public AbstractAuthenticationRealm(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PasswordPolicyValidatorService getPasswordPolicyValidatorService() {
        return passwordPolicyValidatorService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public UserInfo loadUserInfo(String username, String password) {
        return loginService.find(username, password);
    }

    public abstract boolean passwordMatches(UserInfo userInfo, String password);
    
    public List<Groups> queryGroups(UserInfo userInfo) {
       return loginService.queryGroups(userInfo);
    }

    /**
     * grant Authority by userinfo
     * 
     * @param userInfo
     * @return ArrayList<GrantedAuthority>
     */
    public List<GrantedAuthority> grantAuthority(UserInfo userInfo) {
        return loginService.grantAuthority(userInfo);
    }
    
    /**
     * grant Authority by grantedAuthoritys
     * 
     * @param grantedAuthoritys
     * @return ArrayList<GrantedAuthority Apps>
     */
    public List<GrantedAuthority> queryAuthorizedApps(List<GrantedAuthority> grantedAuthoritys) {
        return loginService.queryAuthorizedApps(grantedAuthoritys);
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
        
        userInfo.setLastLoginTime(new Date());
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
        historyLogin.setCategory(SessionCategory.MGMT);
        
        Region ipRegion =ipLocationParser.region(userInfo.getLastLoginIp());
        if(ipRegion != null) {
        	historyLogin.setCountry(ipRegion.getCountry());
        	historyLogin.setProvince(ipRegion.getProvince());
        	historyLogin.setCity(ipRegion.getCity());
        	historyLogin.setLocation(ipRegion.getAddr());
        }
        historyLoginService.login(historyLogin);
        
        loginService.updateLastLogin(userInfo);

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
