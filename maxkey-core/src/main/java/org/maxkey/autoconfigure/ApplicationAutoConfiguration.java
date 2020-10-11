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

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.authn.RealmAuthenticationProvider;
import org.maxkey.authn.SavedRequestAwareAuthenticationSuccessHandler;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.authn.support.rememberme.InMemoryRemeberMeService;
import org.maxkey.authn.support.rememberme.RedisRemeberMeService;
import org.maxkey.constants.ConstantsPersistence;
import org.maxkey.constants.ConstantsProperties;
import org.maxkey.crypto.keystore.KeyStoreLoader;
import org.maxkey.crypto.password.LdapShaPasswordEncoder;
import org.maxkey.crypto.password.Md4PasswordEncoder;
import org.maxkey.crypto.password.NoOpPasswordEncoder;
import org.maxkey.crypto.password.MessageDigestPasswordEncoder;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.crypto.password.SM3PasswordEncoder;
import org.maxkey.crypto.password.StandardPasswordEncoder;
import org.maxkey.persistence.db.PasswordPolicyValidator;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
public class ApplicationAutoConfiguration  implements InitializingBean {
    private static final  Logger _logger = 
            LoggerFactory.getLogger(ApplicationAutoConfiguration.class);
    
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    
    /**
     * propertySourcesPlaceholderConfigurer .
     * @return propertySourcesPlaceholderConfigurer
     * @throws IOException  null
     */
    @Bean (name = "propertySourcesPlaceholderConfigurer")
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
            throws IOException {
        ClassPathResource classPathResource1 = 
                new ClassPathResource(ConstantsProperties.classPathResource(
                        ConstantsProperties.applicationPropertySource));
        ClassPathResource classPathResource2 = 
                new ClassPathResource(ConstantsProperties.classPathResource(
                        ConstantsProperties.maxKeyPropertySource));

        PropertySourcesPlaceholderConfigurer configurer = 
                new PropertySourcesPlaceholderConfigurer();
        configurer.setLocations(
                classPathResource1,
                classPathResource2
        );
        configurer.setIgnoreUnresolvablePlaceholders(true);
        _logger.debug("PropertySourcesPlaceholderConfigurer init");
        return configurer;
    }
    
    @Bean(name = "passwordReciprocal")
    public PasswordReciprocal passwordReciprocal() {
        return new PasswordReciprocal();
    }
    
    @Bean(name = "savedRequestSuccessHandler")
    public SavedRequestAwareAuthenticationSuccessHandler 
            savedRequestAwareAuthenticationSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }
    
    @Bean(name = "authenticationProvider")
    public AbstractAuthenticationProvider authenticationProvider() {
        return new RealmAuthenticationProvider();
    }
    
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
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
    
    /**
     * keyStoreLoader .
     * @return
     */
    @Bean(name = "keyStoreLoader")
    public KeyStoreLoader keyStoreLoader(
            @Value("${config.saml.v20.idp.issuing.entity.id}") String entityName,
            @Value("${config.saml.v20.idp.keystore.password}") String keystorePassword,
            @Value("${config.saml.v20.idp.keystore}") Resource keystoreFile) {
        KeyStoreLoader keyStoreLoader = new KeyStoreLoader();
        keyStoreLoader.setEntityName(entityName);
        keyStoreLoader.setKeystorePassword(keystorePassword);
        keyStoreLoader.setKeystoreFile(keystoreFile);
        return keyStoreLoader;
    }
    
    /**
     * spKeyStoreLoader .
     * @return
     */
    @Bean(name = "spKeyStoreLoader")
    public KeyStoreLoader spKeyStoreLoader(
            @Value("${config.saml.v20.sp.issuing.entity.id}") String entityName,
            @Value("${config.saml.v20.sp.keystore.password}") String keystorePassword,
            @Value("${config.saml.v20.sp.keystore}") Resource keystoreFile) {
        KeyStoreLoader keyStoreLoader = new KeyStoreLoader();
        keyStoreLoader.setEntityName(entityName);
        keyStoreLoader.setKeystorePassword(keystorePassword);
        keyStoreLoader.setKeystoreFile(keystoreFile);
        return keyStoreLoader;
    }
    
    /**
     * spKeyStoreLoader .
     * @return
     */
    @Bean(name = "spIssuingEntityName")
    public String spIssuingEntityName(
            @Value("${config.saml.v20.sp.issuing.entity.id}") String spIssuingEntityName) {
        return spIssuingEntityName;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
