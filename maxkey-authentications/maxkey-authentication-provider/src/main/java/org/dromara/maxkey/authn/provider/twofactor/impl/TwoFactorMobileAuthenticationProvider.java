
 

package org.dromara.maxkey.authn.provider.twofactor.impl;

import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.sms.SmsOtpAuthnService;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


/**
 * TwoFactorMobile Authentication provider.二次认证手机认证提供者
 * 
 * @author Crystal.Sea
 *
 */
public class TwoFactorMobileAuthenticationProvider extends AbstractAuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(TwoFactorMobileAuthenticationProvider.class);

    SmsOtpAuthnService smsOtpAuthnService;
    
    public String getProviderName() {
        return "twoFactorMobile" + PROVIDER_SUFFIX;
    }
 
    public TwoFactorMobileAuthenticationProvider(SmsOtpAuthnService smsOtpAuthnService) {
        this.smsOtpAuthnService = smsOtpAuthnService;
    }

    @Override
    public Authentication doAuthenticate(LoginCredential credential) {
        return null;
    }
    
    @Override
    public Authentication doTwoFactorAuthenticate(LoginCredential credential,UserInfo user) {
        UsernamePasswordAuthenticationToken authenticationToken = null;
        logger.debug("loginCredential {}" , credential);
        try {
            //短信验证码校验
            matches(credential.getOtpCaptcha(),user);
            
            authenticationToken = new UsernamePasswordAuthenticationToken(credential.getUsername(),"mobile");
        
        } catch (AuthenticationException e) {
            logger.error("Failed to authenticate user {} via {}: {}",credential.getPrincipal(),
                                    getProviderName(),
                                    e.getMessage() );
            WebContext.setAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE, e.getMessage());
        } catch (Exception e) {
            logger.error("Login error Unexpected exception in {} authentication:\n{}" ,getProviderName(), e.getMessage());
        }
       
        return  authenticationToken;
    }
    
    
    /**
     * mobile validate.手机验证码校验
     * 
     * @param otpCaptcha String
     * @param authType   String
     * @param userInfo   UserInfo
     */
    protected void matches(String captcha, UserInfo userInfo) {
        // for mobile password
        UserInfo validUserInfo = new UserInfo();
        validUserInfo.setUsername(userInfo.getUsername());
        validUserInfo.setId(userInfo.getId());
        AbstractOtpAuthn smsOtpAuthn = smsOtpAuthnService.getByInstId(userInfo.getInstId());
        if (captcha == null || !smsOtpAuthn.validate(validUserInfo, captcha)) {
            String message = WebContext.getI18nValue("login.error.captcha");
            logger.debug("login captcha valid error.");
            throw new BadCredentialsException(message);
        }
    }
  
}
