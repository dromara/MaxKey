package org.maxkey.authn;

import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.constants.LOGINTYPE;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.crypto.password.opt.AbstractOTPAuthn;
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * login Authentication abstract class
 * 
 * @author Crystal.Sea
 *
 */
public abstract class AbstractAuthenticationProvider {

    private static final Logger _logger = LoggerFactory.getLogger(AbstractAuthenticationProvider.class);

    @Autowired
    @Qualifier("applicationConfig")
    protected ApplicationConfig applicationConfig;

    @Autowired
    @Qualifier("authenticationRealm")
    protected AbstractAuthenticationRealm authenticationRealm;

    @Autowired
    @Qualifier("tfaOTPAuthn")
    protected AbstractOTPAuthn tfaOTPAuthn;

    @Autowired
    @Qualifier("remeberMeService")
    protected AbstractRemeberMeService remeberMeService;

    protected abstract String getProviderName();

    protected abstract Authentication doInternalAuthenticate(Authentication authentication);

    @SuppressWarnings("rawtypes")
    public boolean supports(Class authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    /*
     * authenticate (non-Javadoc)
     * 
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * authenticate(org.springframework.security.core.Authentication)
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        _logger.debug("Trying to authenticate user '{}' via {}", authentication.getPrincipal(), getProviderName());

        try {
            authentication = doInternalAuthenticate(authentication);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            _logger.error("Failed to authenticate user {} via {}: {}",
                    new Object[] { authentication.getPrincipal(), getProviderName(), e.getMessage() });
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            String message = "Unexpected exception in " + getProviderName() + " authentication:";
            _logger.error(message, e);
            throw new AuthenticationServiceException(message, e);
        }
        if (!authentication.isAuthenticated()) {
            return authentication;
        }

        // user authenticated
        _logger.debug("'{}' authenticated successfully by {}.", authentication.getPrincipal(), getProviderName());

        UserInfo userInfo = WebContext.getUserInfo();
        Object password_set_type = WebContext.getSession()
                .getAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE);
        // 登录完成后切换SESSION
        _logger.debug("Login  Session {}.", WebContext.getSession().getId());
        WebContext.getSession().invalidate();
        WebContext.setAttribute(WebConstants.CURRENT_USER_SESSION_ID, WebContext.getSession().getId());
        _logger.debug("Login Success Session {}.", WebContext.getSession().getId());

        authenticationRealm.insertLoginHistory(userInfo, LOGINTYPE.LOCAL, "", "xe00000004", "success");

        // 认证设置
        WebContext.setAuthentication(authentication);
        WebContext.setUserInfo(userInfo);
        WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE, password_set_type);

        // create new authentication response containing the user and it's authorities
        UsernamePasswordAuthenticationToken simpleUserAuthentication = new UsernamePasswordAuthenticationToken(
                userInfo.getUsername(), authentication.getCredentials(), authentication.getAuthorities());
        return simpleUserAuthentication;
    }

    /**
     * session validate
     * 
     * @param j_username
     * @param j_cname
     * @param sessionId
     */
    protected void sessionValid(String j_sessionId) {
        if (j_sessionId == null || !j_sessionId.equals(WebContext.getSession().getId())) {
            String message = WebContext.getI18nValue("login.error.session");
            _logger.debug("login session valid error.");
            throw new BadCredentialsException(message);
        }
    }

    /**
     * session validate
     * 
     * @param j_username
     * @param j_cname
     * @param sessionId
     */
    protected void jwtTokenValid(String j_jwtToken) {
        /*
         * if(j_jwtToken!=null && ! j_jwtToken.equals("")){
         * if(jwtLoginService.jwtTokenValidation(j_jwtToken)){ return; } }
         */
        String message = WebContext.getI18nValue("login.error.session");
        _logger.debug("login session valid error.");
        throw new BadCredentialsException(message);
    }

    protected void authTypeValid(String j_auth_type) {
        if (j_auth_type == null) {
            String message = WebContext.getI18nValue("login.error.authtype");
            _logger.debug("login AuthN type can not been null .");
            throw new BadCredentialsException(message);
        }
    }

    /**
     * captcha validate
     * 
     * @param j_username
     * @param j_cname
     * @param captcha
     */
    protected void captchaValid(String j_captcha, String j_auth_type) {
        if (applicationConfig.getLoginConfig().isCaptcha()) {// for basic
            if (j_auth_type.equalsIgnoreCase("common")) {
                _logger.info("captcha : "
                        + WebContext.getSession().getAttribute(WebConstants.KAPTCHA_SESSION_KEY).toString());
                if (j_captcha == null || !j_captcha
                        .equals(WebContext.getSession().getAttribute(WebConstants.KAPTCHA_SESSION_KEY).toString())) {
                    String message = WebContext.getI18nValue("login.error.captcha");
                    _logger.debug("login captcha valid error.");
                    throw new BadCredentialsException(message);
                }
            }
        }
    }

    /**
     * captcha validate
     * 
     * @param j_username
     * @param j_cname
     * @param j_otp_captcha
     */
    protected void tftcaptchaValid(String j_otp_captcha, String j_auth_type, UserInfo userInfo) {
        if (applicationConfig.getLoginConfig().isOneTimePwd()) {// for one time password 2 factor
            if (j_auth_type.equalsIgnoreCase("tfa")) {
                UserInfo validUserInfo = new UserInfo();
                validUserInfo.setUsername(userInfo.getUsername());
                String sharedSecret = PasswordReciprocal.getInstance().decoder(userInfo.getSharedSecret());
                validUserInfo.setSharedSecret(sharedSecret);
                validUserInfo.setSharedCounter(userInfo.getSharedCounter());
                validUserInfo.setId(userInfo.getId());
                if (j_otp_captcha == null || !tfaOTPAuthn.validate(validUserInfo, j_otp_captcha)) {
                    String message = WebContext.getI18nValue("login.error.captcha");
                    _logger.debug("login captcha valid error.");
                    throw new BadCredentialsException(message);
                }
            }

        }
    }

    /**
     * login user by j_username and j_cname first query user by j_cname if first
     * step userinfo is null,query user from system
     * 
     * @param j_username
     * @param j_cname
     * @return
     */
    protected UserInfo loadUserInfo(String j_username, String j_password) {
        UserInfo userInfo = authenticationRealm.loadUserInfo(j_username, j_password);

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
     * check input password empty
     * 
     * @param password
     * @return
     */
    protected boolean emptyPasswordValid(String j_password) {
        if (null == j_password || "".equals(j_password)) {
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.password.null"));
        }
        return true;
    }

    /**
     * check input username or password empty
     * 
     * @param j_username
     * @param password
     * @return
     */
    protected boolean emptyEmailValid(String j_email) {
        if (null == j_email || "".equals(j_email)) {
            throw new BadCredentialsException("login.error.email.null");
        }
        return true;
    }

    /**
     * check input username empty
     * 
     * @param j_username
     * @return
     */
    protected boolean emptyUsernameValid(String j_username) {
        if (null == j_username || "".equals(j_username)) {
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.username.null"));
        }
        return true;
    }

    protected boolean userinfoValid(UserInfo userInfo, String j_username) {
        if (null == userInfo) {
            String message = WebContext.getI18nValue("login.error.username");
            _logger.debug("login user  " + j_username + " not in this System ." + message);
            UserInfo loginUser = new UserInfo(j_username);
            loginUser.setId(loginUser.generateId());
            loginUser.setDisplayName("not exist");
            loginUser.setLoginCount(0);
            authenticationRealm.insertLoginHistory(loginUser, LOGINTYPE.LOCAL, "",
                    WebContext.getI18nValue("login.error.username"), "user not exist");
            throw new BadCredentialsException(WebContext.getI18nValue("login.error.username"));
        }
        return true;
    }

}