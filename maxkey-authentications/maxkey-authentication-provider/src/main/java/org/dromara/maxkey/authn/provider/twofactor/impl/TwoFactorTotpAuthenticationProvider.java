

package org.dromara.maxkey.authn.provider.twofactor.impl;

import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


/**
 * TwoFactorTotp Authentication provider.二次认证TOTP认证提供者
 * 
 * @author Crystal.Sea
 *
 */
public class TwoFactorTotpAuthenticationProvider extends AbstractAuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(TwoFactorTotpAuthenticationProvider.class);
    
    public String getProviderName() {
        return "twoFactorTotp" + PROVIDER_SUFFIX;
    }
 
    public TwoFactorTotpAuthenticationProvider(AbstractAuthenticationRealm authenticationRealm,AbstractOtpAuthn tfaOtpAuthn) {
    	this.authenticationRealm = authenticationRealm;
		this.tfaOtpAuthn = tfaOtpAuthn;
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
	        //验证码校验
    		UserInfo userTotp = authenticationRealm.loadUserInfoById(user.getId());
    		
	        matches(credential.getOtpCaptcha(),userTotp.getSharedSecret());
	        
	        authenticationToken = new UsernamePasswordAuthenticationToken(credential.getUsername(),"TOTP");
	        
        } catch (AuthenticationException e) {
            logger.error("Failed to authenticate user {} via {}: {}",credential.getPrincipal(),
                                    getProviderName(),
                                    e.getMessage() );
            WebContext.setAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE, e.getMessage());
        } catch (Exception e) {
            logger.error("Login error Unexpected exception in {} authentication:\n{}" , getProviderName(), e.getMessage());
        }
       
        return  authenticationToken;
    }
    
    
    /**
     * 双因素验证.
     * 
     * @param otpCaptcha String
     * @param authType   String
     * @param userInfo   UserInfo
     */
    protected void matches(String captcha, String sharedSecret) {
        // for one time password 2 factor
        if (captcha == null || !tfaOtpAuthn.validate(sharedSecret, captcha)) {
            String message = WebContext.getI18nValue("login.error.captcha");
            logger.debug("login captcha valid error.");
            throw new BadCredentialsException(message);
        }
    }
  
}
