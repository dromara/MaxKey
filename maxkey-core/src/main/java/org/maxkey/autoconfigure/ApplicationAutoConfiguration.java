package org.maxkey.autoconfigure;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import java.io.IOException;
import javax.sql.DataSource;
import org.maxkey.authn.RealmAuthenticationProvider;
import org.maxkey.authn.SavedRequestAwareAuthenticationSuccessHandler;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.authn.support.rememberme.InMemoryRemeberMeService;
import org.maxkey.authn.support.rememberme.JdbcRemeberMeService;
import org.maxkey.authn.support.rememberme.RedisRemeberMeService;
import org.maxkey.constants.ConstantsProperties;
import org.maxkey.crypto.keystore.KeyStoreLoader;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
import org.springframework.security.crypto.password.PasswordEncoder;


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
    public RealmAuthenticationProvider authenticationProvider() {
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
    
    /**
     * Authentication Password Encoder .
     * @return
     */
    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
            RedisConnectionFactory jedisConnectionFactory) {
        AbstractRemeberMeService remeberMeService = null;
        if (persistence == 0) {
            remeberMeService = new InMemoryRemeberMeService();
            _logger.debug("InMemoryRemeberMeService");
        } else if (persistence == 1) {
            remeberMeService = new JdbcRemeberMeService(jdbcTemplate);
            _logger.debug("JdbcRemeberMeService");
        } else if (persistence == 2) {
            remeberMeService = new RedisRemeberMeService(jedisConnectionFactory);
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
