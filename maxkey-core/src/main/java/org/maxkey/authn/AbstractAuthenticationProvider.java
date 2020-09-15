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

import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstantsLoginType;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.crypto.password.opt.AbstractOptAuthn;
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * login Authentication abstract class.
 * 
 * @author Crystal.Sea
 *
 */
public abstract class AbstractAuthenticationProvider {
    private static final Logger _logger = 
            LoggerFactory.getLogger(AbstractAuthenticationProvider.class);

    @Autowired
    @Qualifier("applicationConfig")
    protected ApplicationConfig applicationConfig;

    @Autowired
    @Qualifier("authenticationRealm")
    protected AbstractAuthenticationRealm authenticationRealm;

    @Autowired
    @Qualifier("tfaOptAuthn")
    protected AbstractOptAuthn tfaOptAuthn;

    @Autowired
    @Qualifier("remeberMeService")
    protected AbstractRemeberMeService remeberMeService;

    protected abstract String getProviderName();

    protected abstract Authentication doInternalAuthenticate(Authentication authentication);
    
    public abstract Authentication basicAuthenticate(Authentication authentication) ;

    public abstract Authentication trustAuthentication(
                                    String username, 
                                    String type, 
                                    String provider, 
                                    String code,
                                    String message);
    
    @SuppressWarnings("rawtypes")
    public boolean supports(Class authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    /**
     * authenticate .
     * 
     */
    public Authentication authenticate(Authentication authentication) 
            throws AuthenticationException {
        _logger.debug("Trying to authenticate user '{}' via {}", 
                authentication.getPrincipal(), getProviderName());

        try {
            authentication = doInternalAuthenticate(authentication);
        } catch (AuthenticationException e) {
            _logger.error("Failed to authenticate user {} via {}: {}",
                    new Object[] { 
                            authentication.getPrincipal(), getProviderName(), e.getMessage() });
            WebContext.setAttribute(
                    WebConstants.LOGIN_ERROR_SESSION_MESSAGE, e.getMessage());
        } catch (Exception e) {
            String message = "Unexpected exception in " + getProviderName() + " authentication:";
            _logger.error("Login error " + message, e);
        }
        if (!authentication.isAuthenticated()) {
            return authentication;
        }

        // user authenticated
        _logger.debug("'{}' authenticated successfully by {}.", 
                authentication.getPrincipal(), getProviderName());

        final UserInfo userInfo = WebContext.getUserInfo();
        final Object passwordSetType = WebContext.getSession()
                .getAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE);
        // 登录完成后切换SESSION
        _logger.debug("Login  Session {}.", WebContext.getSession().getId());
        
        final Object firstSavedRequest =
                WebContext.getAttribute(WebConstants.FIRST_SAVED_REQUEST_PARAMETER);
        
        WebContext.getSession().invalidate();
        WebContext.setAttribute(
                WebConstants.CURRENT_USER_SESSION_ID, WebContext.getSession().getId());
        _logger.debug("Login Success Session {}.", WebContext.getSession().getId());

        authenticationRealm.insertLoginHistory(
                userInfo, ConstantsLoginType.LOCAL, "", "xe00000004", "success");

        WebContext.setAttribute(WebConstants.FIRST_SAVED_REQUEST_PARAMETER,firstSavedRequest);
        // 认证设置
        WebContext.setAuthentication(authentication);
        WebContext.setUserInfo(userInfo);
        WebContext.getSession().setAttribute(
                WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE, passwordSetType);

        // create new authentication response containing the user and it's authorities
        UsernamePasswordAuthenticationToken simpleUserAuthentication = 
                new UsernamePasswordAuthenticationToken(
                        userInfo.getUsername(), 
                        authentication.getCredentials(), 
                        authentication.getAuthorities()
                );
        return simpleUserAuthentication;
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
                authType.equalsIgnoreCase("basic") 
                || authType.equalsIgnoreCase("tfa"))
            ) {
            return;
        }
        
        final   String message = WebContext.getI18nValue("login.error.authtype");
        _logger.debug("Login AuthN type must eq basic or tfa ， Error message is " + message);
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
        if (applicationConfig.getLoginConfig().isCaptcha() && authType.equalsIgnoreCase("basic")) {
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
        if (applicationConfig.getLoginConfig().isMfa() && authType.equalsIgnoreCase("tfa")) {
            UserInfo validUserInfo = new UserInfo();
            validUserInfo.setUsername(userInfo.getUsername());
            String sharedSecret = 
                    PasswordReciprocal.getInstance().decoder(userInfo.getSharedSecret());
            validUserInfo.setSharedSecret(sharedSecret);
            validUserInfo.setSharedCounter(userInfo.getSharedCounter());
            validUserInfo.setId(userInfo.getId());
            if (otpCaptcha == null || !tfaOptAuthn.validate(validUserInfo, otpCaptcha)) {
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

    protected boolean userinfoValid(UserInfo userInfo, String username) {
        if (null == userInfo) {
            String message = WebContext.getI18nValue("login.error.username");
            _logger.debug("login user  " + username + " not in this System ." + message);
            UserInfo loginUser = new UserInfo(username);
            loginUser.setId(loginUser.generateId());
            loginUser.setDisplayName("not exist");
            loginUser.setLoginCount(0);
            authenticationRealm.insertLoginHistory(loginUser, ConstantsLoginType.LOCAL, "",
                    WebContext.getI18nValue("login.error.username"), "user not exist");
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.username"));
        }
        return true;
    }

}
