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
 

package org.maxkey.autoconfigure;

import org.maxkey.authn.jwt.AuthTokenService;
import org.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.maxkey.authn.provider.AuthenticationProviderFactory;
import org.maxkey.authn.provider.impl.MobileAuthenticationProvider;
import org.maxkey.authn.provider.impl.NormalAuthenticationProvider;
import org.maxkey.authn.provider.impl.TrustedAuthenticationProvider;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.session.SessionManager;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeManager;
import org.maxkey.authn.support.rememberme.JdbcRemeberMeManager;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstsPersistence;
import org.maxkey.password.onetimepwd.OtpAuthnService;
import org.maxkey.password.onetimepwd.token.RedisOtpTokenStore;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.maxkey.persistence.repository.LoginHistoryRepository;
import org.maxkey.persistence.repository.LoginRepository;
import org.maxkey.persistence.repository.PasswordPolicyValidator;
import org.maxkey.persistence.service.EmailSendersService;
import org.maxkey.persistence.service.SmsProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;


@AutoConfiguration
public class AuthnProviderAutoConfiguration  implements InitializingBean {
    private static final  Logger _logger = 
            LoggerFactory.getLogger(AuthnProviderAutoConfiguration.class);
    
    @Bean
    public AbstractAuthenticationProvider authenticationProvider(
    		AbstractAuthenticationProvider normalAuthenticationProvider,
    		AbstractAuthenticationProvider mobileAuthenticationProvider,
    		AbstractAuthenticationProvider trustedAuthenticationProvider
    		) {
    	AuthenticationProviderFactory authenticationProvider = new AuthenticationProviderFactory();
    	authenticationProvider.addAuthenticationProvider(normalAuthenticationProvider);
    	authenticationProvider.addAuthenticationProvider(mobileAuthenticationProvider);
    	authenticationProvider.addAuthenticationProvider(trustedAuthenticationProvider);
    	
    	return authenticationProvider;
    }
    		
    @Bean
    public AbstractAuthenticationProvider normalAuthenticationProvider(
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
    public AbstractAuthenticationProvider mobileAuthenticationProvider(
    		AbstractAuthenticationRealm authenticationRealm,
    		ApplicationConfig applicationConfig,
    	    OtpAuthnService otpAuthnService,
    	    SessionManager sessionManager
    		) {
    	_logger.debug("init Mobile authentication Provider .");
    	return new MobileAuthenticationProvider(
        		authenticationRealm,
        		applicationConfig,
        		otpAuthnService,
        		sessionManager
        	);
    }

    @Bean
    public AbstractAuthenticationProvider trustedAuthenticationProvider(
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
    public PasswordPolicyValidator passwordPolicyValidator(JdbcTemplate jdbcTemplate,MessageSource messageSource) {
        return new PasswordPolicyValidator(jdbcTemplate,messageSource);
    }
    
    @Bean
    public LoginRepository loginRepository(JdbcTemplate jdbcTemplate) {
        return new LoginRepository(jdbcTemplate);
    }
    
    @Bean
    public LoginHistoryRepository loginHistoryRepository(JdbcTemplate jdbcTemplate) {
        return new LoginHistoryRepository(jdbcTemplate);
    }
    
    /**
     * remeberMeService .
     * @return
     */
    @Bean
    public AbstractRemeberMeManager remeberMeManager(
            @Value("${maxkey.server.persistence}") int persistence,
            @Value("${maxkey.login.remeberme.validity}") int validity,
            ApplicationConfig applicationConfig,
            AuthTokenService authTokenService,
            JdbcTemplate jdbcTemplate) {
    	_logger.trace("init RemeberMeManager , validity {}." , validity);
        return new  JdbcRemeberMeManager(
        		jdbcTemplate,applicationConfig,authTokenService,validity);
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
}
