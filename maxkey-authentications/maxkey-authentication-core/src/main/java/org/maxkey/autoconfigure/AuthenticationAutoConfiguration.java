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

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.RealmAuthenticationProvider;
import org.maxkey.authn.SavedRequestAwareAuthenticationSuccessHandler;
import org.maxkey.authn.online.InMemoryOnlineTicketServices;
import org.maxkey.authn.online.OnlineTicketServices;
import org.maxkey.authn.online.RedisOnlineTicketServices;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.authn.support.rememberme.InMemoryRemeberMeService;
import org.maxkey.authn.support.rememberme.RedisRemeberMeService;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstantsPersistence;
import org.maxkey.constants.ConstantsProperties;
import org.maxkey.crypto.password.LdapShaPasswordEncoder;
import org.maxkey.crypto.password.Md4PasswordEncoder;
import org.maxkey.crypto.password.NoOpPasswordEncoder;
import org.maxkey.crypto.password.MessageDigestPasswordEncoder;
import org.maxkey.crypto.password.SM3PasswordEncoder;
import org.maxkey.crypto.password.StandardPasswordEncoder;
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
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.maxkey.persistence.db.LoginService;
import org.maxkey.persistence.db.LoginHistoryService;


@Configuration
@PropertySource(ConstantsProperties.applicationPropertySource)
@PropertySource(ConstantsProperties.maxKeyPropertySource)
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
    	    AbstractOtpAuthn tfaOptAuthn,
    	    AbstractRemeberMeService remeberMeService,
    	    OnlineTicketServices onlineTicketServices
    		) {
    	
        return new RealmAuthenticationProvider(
        		authenticationRealm,
        		applicationConfig,
        		tfaOptAuthn,
        		remeberMeService,
        		onlineTicketServices
        		);
        
    }
    
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
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
     * Authentication Password Encoder .
     * @return
     */
    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map<String ,PasswordEncoder > encoders = new HashMap<String ,PasswordEncoder>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("plain", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        //md
        encoders.put("md4", new Md4PasswordEncoder());
        encoders.put("md5", new MessageDigestPasswordEncoder("MD5"));
        //sha
        encoders.put("sha1", new StandardPasswordEncoder("SHA-1",""));
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("sha384", new StandardPasswordEncoder("SHA-384",""));
        encoders.put("sha512", new StandardPasswordEncoder("SHA-512",""));
        
        encoders.put("sm3", new SM3PasswordEncoder());
        
        encoders.put("ldap", new LdapShaPasswordEncoder());
        
        //idForEncode is default for encoder
        PasswordEncoder passwordEncoder =
            new DelegatingPasswordEncoder(idForEncode, encoders);
        
        return passwordEncoder;
    }
    
    /**
     * remeberMeService .
     * @return
     */
    @Bean(name = "remeberMeService")
    public AbstractRemeberMeService remeberMeService(
            @Value("${config.server.persistence}") int persistence,
            @Value("${config.login.remeberme.validity}") int validity,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory) {
        AbstractRemeberMeService remeberMeService = null;
        if (persistence == ConstantsPersistence.INMEMORY) {
            remeberMeService = new InMemoryRemeberMeService();
            _logger.debug("InMemoryRemeberMeService");
        } else if (persistence == ConstantsPersistence.JDBC) {
            //remeberMeService = new JdbcRemeberMeService(jdbcTemplate);
            _logger.debug("JdbcRemeberMeService not support "); 
        } else if (persistence == ConstantsPersistence.REDIS) {
            remeberMeService = new RedisRemeberMeService(redisConnFactory);
            _logger.debug("RedisRemeberMeService");
        }
        return remeberMeService;
    }
    
    @Bean(name = "onlineTicketServices")
    public OnlineTicketServices onlineTicketServices(
            @Value("${config.server.persistence}") int persistence,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory) {
        OnlineTicketServices onlineTicketServices = null;
        if (persistence == ConstantsPersistence.INMEMORY) {
            onlineTicketServices = new InMemoryOnlineTicketServices();
            _logger.debug("InMemoryOnlineTicketServices");
        } else if (persistence == ConstantsPersistence.JDBC) {
            _logger.debug("OnlineTicketServices not support "); 
        } else if (persistence == ConstantsPersistence.REDIS) {
            onlineTicketServices = new RedisOnlineTicketServices(redisConnFactory);
            _logger.debug("RedisOnlineTicketServices");
        }
        return onlineTicketServices;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
