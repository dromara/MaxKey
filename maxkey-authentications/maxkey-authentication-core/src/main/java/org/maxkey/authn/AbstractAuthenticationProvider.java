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
 

package org.maxkey.authn;

import java.util.ArrayList;
import java.util.HashMap;

import org.maxkey.authn.online.OnlineTicketServices;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
/**
 * login Authentication abstract class.
 * 
 * @author Crystal.Sea
 *
 */
public abstract class AbstractAuthenticationProvider {
    private static final Logger _logger = 
            LoggerFactory.getLogger(AbstractAuthenticationProvider.class);

    public class AuthType{
    	public final static String NORMAL 	= "normal";
    	public final static String TFA 		= "tfa";
    	public final static String MOBILE 	= "mobile";
    }
    protected ApplicationConfig applicationConfig;

    protected AbstractAuthenticationRealm authenticationRealm;

    protected AbstractOtpAuthn tfaOtpAuthn;
    
    protected OtpAuthnService otpAuthnService;

    protected AbstractRemeberMeService remeberMeService;
    
    protected OnlineTicketServices onlineTicketServices;
    
    public static  ArrayList<GrantedAuthority> grantedAdministratorsAuthoritys = new ArrayList<GrantedAuthority>();
    
    static {
        grantedAdministratorsAuthoritys.add(new SimpleGrantedAuthority("ROLE_ADMINISTRATORS"));
    }

    protected abstract String getProviderName();

    protected abstract Authentication doInternalAuthenticate(LoginCredential authentication);
    
    public    abstract Authentication authentication(LoginCredential loginCredential,boolean isTrusted);
    
    @SuppressWarnings("rawtypes")
    public boolean supports(Class authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    /**
     * authenticate .
     * 
     */
    public Authentication authenticate(LoginCredential loginCredential) 
            throws AuthenticationException {
        _logger.debug("Trying to authenticate user '{}' via {}", 
                loginCredential.getPrincipal(), getProviderName());
        // 登录SESSION
        _logger.debug("Login  Session {}.", WebContext.getSession().getId());
        Authentication authentication = null;
        try {
            authentication = doInternalAuthenticate(loginCredential);
        } catch (AuthenticationException e) {
            _logger.error("Failed to authenticate user {} via {}: {}",
                    new Object[] {  loginCredential.getPrincipal(),
                                    getProviderName(),
                                    e.getMessage() });
            WebContext.setAttribute(
                    WebConstants.LOGIN_ERROR_SESSION_MESSAGE, e.getMessage());
        } catch (Exception e) {
            _logger.error("Login error Unexpected exception in {} authentication:\n{}" ,
                            getProviderName(), e.getMessage());
        }
        
        if (authentication== null || !authentication.isAuthenticated()) {
            return authentication;
        }

        // user authenticated
        _logger.debug("'{}' authenticated successfully by {}.", 
                authentication.getPrincipal(), getProviderName());
        
        changeSession(authentication);
        
        authenticationRealm.insertLoginHistory( WebContext.getUserInfo(), 
						        				ConstsLoginType.LOCAL, 
								                "", 
								                "xe00000004", 
								                WebConstants.LOGIN_RESULT.SUCCESS);
        
        return authentication;
    }
    
    protected void changeSession(Authentication authentication) {
        
        HashMap<String,Object> sessionAttributeMap = new HashMap<String,Object>();
        for(String attributeName : WebContext.sessionAttributeNameList) {
            sessionAttributeMap.put(attributeName, WebContext.getAttribute(attributeName));
            WebContext.removeAttribute(attributeName);
        }
        
        //new Session        
        WebContext.getSession().invalidate();
        
        for(String attributeName : WebContext.sessionAttributeNameList) {
            WebContext.setAttribute(attributeName, sessionAttributeMap.get(attributeName));
        }
        
        _logger.debug("Login Success Session {} Mapping to user Session {}.",
                        WebContext.getSession().getId(),
                        WebContext.getAttribute(WebConstants.CURRENT_USER_SESSION_ID));
    }
   

    /**
     * session validate.
     * 
     * @param sessionId String
     */
    protected void sessionValid(String sessionId) {
        if (sessionId == null || !sessionId.equals(WebContext.getSession().getId())) {
            _logger.debug("login session valid error.");
            _logger.debug("login session sessionId " + sessionId);
            _logger.debug("login getSession sessionId " + WebContext.getSession().getId());
            
            String message = WebContext.getI18nValue("login.error.session");
            throw new BadCredentialsException(message);
        }
    }

    /**
     * session validate.
     * 
     * @param jwtToken String
     */
    protected void jwtTokenValid(String jwtToken) {
        /*
         * if(jwtToken!=null && ! jwtToken.equals("")){
         * if(jwtLoginService.jwtTokenValidation(j_jwtToken)){ return; } }
         */
        String message = WebContext.getI18nValue("login.error.session");
        _logger.debug("login session valid error.");
        throw new BadCredentialsException(message);
    }

    protected void authTypeValid(String authType) {
        _logger.debug("Login AuthN Type  " + authType);
        if (authType != null && (
                authType.equalsIgnoreCase(AuthType.NORMAL) 
                || authType.equalsIgnoreCase(AuthType.TFA)
                || authType.equalsIgnoreCase(AuthType.MOBILE)
        		)
            ) {
            return;
        }
        
        final   String message = WebContext.getI18nValue("login.error.authtype");
        _logger.debug("Login AuthN type must eq basic or tfa ， Error message is {}" , message);
        throw new BadCredentialsException(message);
    }

    /**
     * captcha validate .
     * 
     * @param authType String
     * @param captcha String
     */
    protected void captchaValid(String captcha, String authType) {
        // for basic
        if (authType.equalsIgnoreCase(AuthType.NORMAL)) {
            _logger.info("captcha : "
                    + WebContext.getSession().getAttribute(
                            WebConstants.KAPTCHA_SESSION_KEY).toString());
            if (captcha == null || !captcha
                    .equals(WebContext.getSession().getAttribute(
                                    WebConstants.KAPTCHA_SESSION_KEY).toString())) {
                String message = WebContext.getI18nValue("login.error.captcha");
                _logger.debug("login captcha valid error.");
                throw new BadCredentialsException(message);
            }
        }
    }

    /**
     * captcha validate.
     * 
     * @param otpCaptcha String
     * @param authType   String
     * @param userInfo   UserInfo
     */
    protected void tftcaptchaValid(String otpCaptcha, String authType, UserInfo userInfo) {
        // for one time password 2 factor
        if (applicationConfig.getLoginConfig().isMfa() 
        		&& authType.equalsIgnoreCase(AuthType.TFA)) {
            UserInfo validUserInfo = new UserInfo();
            validUserInfo.setUsername(userInfo.getUsername());
            validUserInfo.setSharedSecret(userInfo.getSharedSecret());
            validUserInfo.setSharedCounter(userInfo.getSharedCounter());
            validUserInfo.setId(userInfo.getId());
            if (otpCaptcha == null || !tfaOtpAuthn.validate(validUserInfo, otpCaptcha)) {
                String message = WebContext.getI18nValue("login.error.captcha");
                _logger.debug("login captcha valid error.");
                throw new BadCredentialsException(message);
            }
        }
    }
    
    /**
     * mobile validate.
     * 
     * @param otpCaptcha String
     * @param authType   String
     * @param userInfo   UserInfo
     */
    protected void mobilecaptchaValid(String password, String authType, UserInfo userInfo) {
        // for mobile password
        if (applicationConfig.getLoginConfig().isMfa() 
        		&& authType.equalsIgnoreCase(AuthType.MOBILE)) {
            UserInfo validUserInfo = new UserInfo();
            validUserInfo.setUsername(userInfo.getUsername());
            validUserInfo.setId(userInfo.getId());
            AbstractOtpAuthn smsOtpAuthn = otpAuthnService.getByInstId(userInfo.getInstId());
            if (password == null || !smsOtpAuthn.validate(validUserInfo, password)) {
                String message = WebContext.getI18nValue("login.error.captcha");
                _logger.debug("login captcha valid error.");
                throw new BadCredentialsException(message);
            }
        }
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

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public void setAuthenticationRealm(AbstractAuthenticationRealm authenticationRealm) {
        this.authenticationRealm = authenticationRealm;
    }

    public void setTfaOtpAuthn(AbstractOtpAuthn tfaOtpAuthn) {
        this.tfaOtpAuthn = tfaOtpAuthn;
    }

    public void setRemeberMeService(AbstractRemeberMeService remeberMeService) {
        this.remeberMeService = remeberMeService;
    }

    public void setOnlineTicketServices(OnlineTicketServices onlineTicketServices) {
        this.onlineTicketServices = onlineTicketServices;
    }

	public void setOtpAuthnService(OtpAuthnService otpAuthnService) {
		this.otpAuthnService = otpAuthnService;
	}

}
