package org.maxkey.autoconfigure;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import java.io.IOException;
import javax.sql.DataSource;
import org.maxkey.authn.RealmAuthenticationProvider;
import org.maxkey.authn.SavedRequestAwareAuthenticationSuccessHandler;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@PropertySource("classpath:/application.properties")
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
                new ClassPathResource("/config/applicationConfig.properties");
        ClassPathResource classPathResource2 = new ClassPathResource("/application.properties");

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
    DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
