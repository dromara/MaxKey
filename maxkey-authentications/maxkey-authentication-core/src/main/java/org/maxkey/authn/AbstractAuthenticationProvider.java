/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.authn;

import java.util.ArrayList;

import org.maxkey.authn.jwt.AuthJwtService;
import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.web.AuthorizationUtils;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstsLoginType;
import org.maxkey.constants.ConstsStatus;
import org.maxkey.entity.UserInfo;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.password.onetimepwd.OtpAuthnService;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
/**
 * login Authentication abstract class.
 * 
 * @author Crystal.Sea
 *
 */
public abstract class AbstractAuthenticationProvider {
    private static final Logger _logger = 
            LoggerFactory.getLogger(AbstractAuthenticationProvider.class);

    public static String PROVIDER_SUFFIX = "AuthenticationProvider";
    
    public class AuthType{
    	public final static String NORMAL 	= "normal";
    	public final static String TFA 		= "tfa";
    	public final static String MOBILE 	= "mobile";
    	public final static String TRUSTED 	= "trusted";
    }
    
    protected ApplicationConfig applicationConfig;

    protected AbstractAuthenticationRealm authenticationRealm;

    protected AbstractOtpAuthn tfaOtpAuthn;
    
    protected OtpAuthnService otpAuthnService;

    protected OnlineTicketService onlineTicketServices;
    
    protected AuthJwtService authJwtService;
    
    public static  ArrayList<GrantedAuthority> grantedAdministratorsAuthoritys = new ArrayList<GrantedAuthority>();
    
    static {
        grantedAdministratorsAuthoritys.add(new SimpleGrantedAuthority("ROLE_ADMINISTRATORS"));
    }

    public abstract String getProviderName();

    public abstract Authentication doAuthenticate(LoginCredential authentication);
    
    @SuppressWarnings("rawtypes")
    public boolean supports(Class authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public Authentication authenticate(LoginCredential authentication){
    	return null;
    }
    
    public Authentication authenticate(LoginCredential authentication,boolean trusted) {
    	return null;
    }
    
    /**
     * createOnlineSession 
     * @param credential
     * @param userInfo
     * @return
     */
    public UsernamePasswordAuthenticationToken createOnlineTicket(LoginCredential credential,UserInfo userInfo) {
        //Online Tickit
        OnlineTicket onlineTicket = new OnlineTicket();

        userInfo.setOnlineTicket(onlineTicket.getTicketId());
        
        SigninPrincipal principal = new SigninPrincipal(userInfo);
        //set OnlineTicket
        principal.setOnlineTicket(onlineTicket);
        ArrayList<GrantedAuthority> grantedAuthoritys = authenticationRealm.grantAuthority(userInfo);
        principal.setAuthenticated(true);
        
        for(GrantedAuthority administratorsAuthority : grantedAdministratorsAuthoritys) {
            if(grantedAuthoritys.contains(administratorsAuthority)) {
            	principal.setRoleAdministrators(true);
                _logger.trace("ROLE ADMINISTRATORS Authentication .");
            }
        }
        _logger.debug("Granted Authority {}" , grantedAuthoritys);
        
        principal.setGrantedAuthorityApps(authenticationRealm.queryAuthorizedApps(grantedAuthoritys));
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                		principal, 
                        "PASSWORD", 
                        grantedAuthoritys
                );
        
        authenticationToken.setDetails(
                new WebAuthenticationDetails(WebContext.getRequest()));
        
        onlineTicket.setAuthentication(authenticationToken);
        
        //store onlineTicket
        this.onlineTicketServices.store(onlineTicket.getTicketId(), onlineTicket);
        
        /*
         *  put Authentication to current session context
         */
        AuthorizationUtils.setAuthentication(authenticationToken);
     
        return authenticationToken;
    }
    
    /**
     * login user by j_username and j_cname first query user by j_cname if first
     * step userinfo is null,query user from system.
     * 
     * @param username String
     * @param password String
     * @return
     */
    public UserInfo loadUserInfo(String username, String password) {
        UserInfo userInfo = authenticationRealm.loadUserInfo(username, password);

        if (userInfo != null) {
            if (userInfo.getUserType() == "SYSTEM") {
                _logger.debug("SYSTEM User Login. ");
            } else {
                _logger.debug("User Login. ");
            }
            
        }

        return userInfo;
    }

    /**
     * check input password empty.
     * 
     * @param password String
     * @return
     */
    protected boolean emptyPasswordValid(String password) {
        if (null == password || "".equals(password)) {
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.password.null"));
        }
        return true;
    }

    /**
     * check input username or password empty.
     * 
     * @param email String
     * @return
     */
    protected boolean emptyEmailValid(String email) {
        if (null == email || "".equals(email)) {
            throw new BadCredentialsException("login.error.email.null");
        }
        return true;
    }

    /**
     * check input username empty.
     * 
     * @param username String
     * @return
     */
    protected boolean emptyUsernameValid(String username) {
        if (null == username || "".equals(username)) {
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.username.null"));
        }
        return true;
    }

    protected boolean statusValid(LoginCredential loginCredential , UserInfo userInfo) {
        if (null == userInfo) {
            String i18nMessage = WebContext.getI18nValue("login.error.username");
            _logger.debug("login user  " + loginCredential.getUsername() + " not in this System ." + i18nMessage);
            UserInfo loginUser = new UserInfo(loginCredential.getUsername());
            loginUser.setId(loginUser.generateId());
            loginUser.setUsername(loginCredential.getUsername());
            loginUser.setDisplayName("not exist");
            loginUser.setLoginCount(0);
            authenticationRealm.insertLoginHistory(
            			loginUser, 
            			ConstsLoginType.LOCAL, 
            			"",
            			i18nMessage,
            			WebConstants.LOGIN_RESULT.USER_NOT_EXIST);
            throw new BadCredentialsException(i18nMessage);
        }else {
        	if(userInfo.getIsLocked()==ConstsStatus.LOCK) {
        		authenticationRealm.insertLoginHistory( 
        				userInfo, 
                        loginCredential.getAuthType(), 
                        loginCredential.getProvider(), 
                        loginCredential.getCode(), 
                        WebConstants.LOGIN_RESULT.USER_LOCKED
                    );
        	}else if(userInfo.getStatus()!=ConstsStatus.ACTIVE) {
        		authenticationRealm.insertLoginHistory( 
        				userInfo, 
                        loginCredential.getAuthType(), 
                        loginCredential.getProvider(), 
                        loginCredential.getCode(), 
                        WebConstants.LOGIN_RESULT.USER_INACTIVE
                    );
        	}
        }
        return true;
    }

}
