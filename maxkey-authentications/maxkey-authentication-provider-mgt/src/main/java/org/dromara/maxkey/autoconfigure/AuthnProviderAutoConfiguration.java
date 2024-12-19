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


package org.dromara.maxkey.autoconfigure;

import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.provider.AuthenticationProviderFactory;
import org.dromara.maxkey.authn.provider.impl.*;
import org.dromara.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.password.sms.SmsOtpAuthnService;
import org.dromara.maxkey.persistence.service.CnfPasswordPolicyService;
import org.dromara.maxkey.persistence.service.PasswordPolicyValidatorService;
import org.dromara.maxkey.persistence.service.impl.PasswordPolicyValidatorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
public class AuthnProviderAutoConfiguration {
    static final  Logger _logger = LoggerFactory.getLogger(AuthnProviderAutoConfiguration.class);

    @Bean
     AbstractAuthenticationProvider authenticationProvider(
    		NormalAuthenticationProvider normalAuthenticationProvider,
    		MobileAuthenticationProvider mobileAuthenticationProvider,
    		TrustedAuthenticationProvider trustedAuthenticationProvider
    		) {
    	AuthenticationProviderFactory authenticationProvider = new AuthenticationProviderFactory();
    	authenticationProvider.addAuthenticationProvider(normalAuthenticationProvider);
    	authenticationProvider.addAuthenticationProvider(mobileAuthenticationProvider);
    	authenticationProvider.addAuthenticationProvider(trustedAuthenticationProvider);
    	return authenticationProvider;
    }

    @Bean
    NormalAuthenticationProvider normalAuthenticationProvider(
    		AbstractAuthenticationRealm authenticationRealm,
    		ApplicationConfig applicationConfig,
    	    SessionManager sessionManager,
    	    AuthTokenService authTokenService
    		) {
    	_logger.debug("init authentication Provider .");
    	return new NormalAuthenticationProvider(
        		authenticationRealm,
        		applicationConfig,
        		sessionManager,
        		authTokenService
        	);
    }

    @Bean
    MobileAuthenticationProvider mobileAuthenticationProvider(
    		AbstractAuthenticationRealm authenticationRealm,
    		ApplicationConfig applicationConfig,
    	    SmsOtpAuthnService smsAuthnService,
    	    SessionManager sessionManager
    		) {
    	_logger.debug("init Mobile authentication Provider .");
    	return new MobileAuthenticationProvider(
        		authenticationRealm,
        		applicationConfig,
        		smsAuthnService,
        		sessionManager
        	);
    }
    
    @Bean
    TrustedAuthenticationProvider trustedAuthenticationProvider(
    		AbstractAuthenticationRealm authenticationRealm,
    		ApplicationConfig applicationConfig,
    	    SessionManager sessionManager
    		) {
    	_logger.debug("init Mobile authentication Provider .");
    	return new TrustedAuthenticationProvider(
        		authenticationRealm,
        		applicationConfig,
        		sessionManager
        	);
    }

    @Bean
    PasswordPolicyValidatorService passwordPolicyValidatorService(
    		CnfPasswordPolicyService cnfPasswordPolicyService,
    		MessageSource messageSource) {
        return new PasswordPolicyValidatorServiceImpl(cnfPasswordPolicyService,messageSource);
    }

}
