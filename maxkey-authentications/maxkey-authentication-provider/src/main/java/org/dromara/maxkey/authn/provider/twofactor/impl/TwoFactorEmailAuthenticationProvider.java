/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
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
 


 

package org.dromara.maxkey.authn.provider.twofactor.impl;

import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.MailOtpAuthnService;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


/**
 * TwoFactor Authentication provider.二次认证邮件认证提供者
 * 
 * @author Crystal.Sea
 *
 */
public class TwoFactorEmailAuthenticationProvider extends AbstractAuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(TwoFactorEmailAuthenticationProvider.class);

    MailOtpAuthnService mailOtpAuthnService;
    
    public String getProviderName() {
        return "twoFactorEmail" + PROVIDER_SUFFIX;
    }
 
    public TwoFactorEmailAuthenticationProvider(MailOtpAuthnService mailOtpAuthnService) {
        this.mailOtpAuthnService = mailOtpAuthnService;
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
            
            authenticationToken = new UsernamePasswordAuthenticationToken(credential.getUsername(),"email");
            
        } catch (AuthenticationException e) {
            logger.error("Failed to authenticate user {} via {}: {}",credential.getPrincipal(),
                                    getProviderName(),
                                    e.getMessage() );
            WebContext.setAttribute(
                    WebConstants.LOGIN_ERROR_SESSION_MESSAGE, e.getMessage());
        } catch (Exception e) {
            logger.error("Login error Unexpected exception in {} authentication:\n{}" ,
                            getProviderName(), e.getMessage());
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
        AbstractOtpAuthn smsOtpAuthn = mailOtpAuthnService.getMailOtpAuthn(userInfo.getInstId());
        if (captcha == null || !smsOtpAuthn.validate(validUserInfo, captcha)) {
            String message = WebContext.getI18nValue("login.error.captcha");
            logger.debug("login captcha valid error.");
            throw new BadCredentialsException(message);
        }
    }
  
}
