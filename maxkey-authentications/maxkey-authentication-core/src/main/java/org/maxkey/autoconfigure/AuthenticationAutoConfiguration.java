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
 

package org.maxkey.autoconfigure;

import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.RealmAuthenticationProvider;
import org.maxkey.authn.SavedRequestAwareAuthenticationSuccessHandler;
import org.maxkey.authn.online.OnlineTicketServices;
import org.maxkey.authn.online.OnlineTicketServicesFactory;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.authn.support.rememberme.RemeberMeServiceFactory;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.maxkey.persistence.db.PasswordPolicyValidator;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.maxkey.persistence.db.LoginService;
import org.maxkey.persistence.db.LoginHistoryService;


@Configuration
public class AuthenticationAutoConfiguration  implements InitializingBean {
    private static final  Logger _logger = 
            LoggerFactory.getLogger(AuthenticationAutoConfiguration.class);
    
    
    @Bean(name = "savedRequestSuccessHandler")
    public SavedRequestAwareAuthenticationSuccessHandler 
            savedRequestAwareAuthenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }
    
    @Bean(name = "authenticationProvider")
    public AbstractAuthenticationProvider authenticationProvider(
    		AbstractAuthenticationRealm authenticationRealm,
    		ApplicationConfig applicationConfig,
    	    AbstractOtpAuthn tfaOtpAuthn,
    	    AbstractOtpAuthn smsOtpAuthn,
    	    AbstractRemeberMeService remeberMeService,
    	    OnlineTicketServices onlineTicketServices
    		) {
       
    	_logger.debug("init authentication Provider .");
        return new RealmAuthenticationProvider(
        		authenticationRealm,
        		applicationConfig,
        		tfaOtpAuthn,
        		smsOtpAuthn,
        		remeberMeService,
        		onlineTicketServices
        		);
        
    }
    
    @Bean(name = "passwordPolicyValidator")
    public PasswordPolicyValidator passwordPolicyValidator(JdbcTemplate jdbcTemplate,MessageSource messageSource) {
        return new PasswordPolicyValidator(jdbcTemplate,messageSource);
    }
    
    @Bean(name = "loginService")
    public LoginService LoginService(JdbcTemplate jdbcTemplate) {
        return new LoginService(jdbcTemplate);
    }
    @Bean(name = "loginHistoryService")
    public LoginHistoryService loginHistoryService(JdbcTemplate jdbcTemplate) {
        return new LoginHistoryService(jdbcTemplate);
    }
    
    /**
     * remeberMeService .
     * @return
     */
    @Bean(name = "remeberMeService")
    public AbstractRemeberMeService remeberMeService(
            @Value("${maxkey.server.persistence}") int persistence,
            @Value("${maxkey.login.remeberme.validity}") int validity,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory) {
        return new RemeberMeServiceFactory().getService(persistence, jdbcTemplate, redisConnFactory);
    }
    
    @Bean(name = "onlineTicketServices")
    public OnlineTicketServices onlineTicketServices(
            @Value("${maxkey.server.persistence}") int persistence,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory,
            @Value("${server.servlet.session.timeout:1800}") int timeout
            ) {
        OnlineTicketServices  onlineTicketServices  = 
                new OnlineTicketServicesFactory().getService(persistence, jdbcTemplate, redisConnFactory);
        onlineTicketServices.setValiditySeconds(timeout);
        _logger.trace("onlineTicket timeout " + timeout);
        return onlineTicketServices;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
}
