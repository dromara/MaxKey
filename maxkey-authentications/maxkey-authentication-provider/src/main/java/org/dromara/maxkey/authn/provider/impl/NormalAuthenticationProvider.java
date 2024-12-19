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
 

package org.dromara.maxkey.authn.provider.impl;

import java.text.ParseException;

import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.constants.ConstsLoginType;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * database Authentication provider.
 * @author Crystal.Sea
 *
 */
public class NormalAuthenticationProvider extends AbstractAuthenticationProvider {
    private static final Logger _logger = LoggerFactory.getLogger(NormalAuthenticationProvider.class);

    @Override
    public String getProviderName() {
        return "normal" + PROVIDER_SUFFIX;
    }
    

    public NormalAuthenticationProvider() {
		super();
	}

    public NormalAuthenticationProvider(
    		AbstractAuthenticationRealm authenticationRealm,
    		ApplicationConfig applicationConfig,
    	    SessionManager sessionManager,
    	    AuthTokenService authTokenService) {
		this.authenticationRealm = authenticationRealm;
		this.applicationConfig = applicationConfig;
		this.sessionManager = sessionManager;
		this.authTokenService = authTokenService;
	}

    @Override
	public Authentication doAuthenticate(LoginCredential loginCredential) {
		UsernamePasswordAuthenticationToken authenticationToken = null;
		_logger.debug("Trying to authenticate user '{}' via {}", 
                loginCredential.getPrincipal(), getProviderName());
        try {
        	
	        _logger.debug("authentication {}" , loginCredential);
	        
	        if(this.applicationConfig.getLoginConfig().isCaptcha()) {
	        	captchaValid(loginCredential.getState(),loginCredential.getCaptcha());
	        }
	
	        emptyPasswordValid(loginCredential.getPassword());
	
	        emptyUsernameValid(loginCredential.getUsername());
	
	        UserInfo userInfo =  loadUserInfo(loginCredential.getUsername(),loginCredential.getPassword());
	
	        isUserExist(loginCredential , userInfo);
	        
	        //Validate PasswordPolicy
	        authenticationRealm.getLoginService().passwordPolicyValid(userInfo);
	        
	        statusValid(loginCredential , userInfo);
	        
	        //Match password 
	        authenticationRealm.passwordMatches(userInfo, loginCredential.getPassword());

	        //apply PasswordSetType and resetBadPasswordCount
	        authenticationRealm.getLoginService().applyPasswordPolicy(userInfo);
	        
	        authenticationToken = createOnlineTicket(loginCredential,userInfo);
	        // user authenticated
	        _logger.debug("'{}' authenticated successfully by {}.", 
	        		loginCredential.getPrincipal(), getProviderName());
	        
	        authenticationRealm.insertLoginHistory(userInfo, 
							        				ConstsLoginType.LOCAL, 
									                "", 
									                "xe00000004", 
									                WebConstants.LOGIN_RESULT.SUCCESS);
        } catch (AuthenticationException e) {
            _logger.error("Failed to authenticate user {} via {}: {}",
                    				loginCredential.getPrincipal(),
                                    getProviderName(),
                                    e.getMessage() );
            WebContext.setAttribute(
                    WebConstants.LOGIN_ERROR_SESSION_MESSAGE, e.getMessage());
        } catch (Exception e) {
            _logger.error("Login error Unexpected exception in {} authentication:\n{}" ,
                            getProviderName(), e.getMessage());
        }
       
        return  authenticationToken;
    }
    
    /**
     * captcha validate .
     * 
     * @param authType String
     * @param captcha String
     * @throws ParseException 
     */
    protected void captchaValid(String state ,String captcha) {
        // for basic
    	if(!authTokenService.validateCaptcha(state,captcha)) {
    		throw new BadCredentialsException(WebContext.getI18nValue("login.error.captcha"));
    	}        
    }
}
