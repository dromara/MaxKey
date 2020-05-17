package org.maxkey;

import javax.sql.DataSource;
import org.maxkey.authz.oauth2.provider.client.JdbcClientDetailsService;
import org.maxkey.crypto.password.opt.impl.TimeBasedOtpAuthn;
import org.maxkey.authn.realm.jdbc.JdbcAuthenticationRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@PropertySource("classpath:/application.properties")
public class MaxKeyMgtConfig  implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(MaxKeyMgtConfig.class);
    
	@Value("${server.port:8080}")
    private int port;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	@Bean(name = "oauth20JdbcClientDetailsService")
    public JdbcClientDetailsService JdbcClientDetailsService(
                DataSource dataSource,PasswordEncoder passwordReciprocal) {
	    JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
	    clientDetailsService.setPasswordEncoder(passwordReciprocal);
	    _logger.debug("JdbcClientDetailsService inited.");
        return clientDetailsService;
    }
	
	//以下内容可以注释掉后再xml中配置,xml引入在MaxKeyMgtApplication中
	@Bean(name = "authenticationRealm")
    public JdbcAuthenticationRealm JdbcAuthenticationRealm(
                JdbcTemplate jdbcTemplate) {
        JdbcAuthenticationRealm authenticationRealm = new JdbcAuthenticationRealm(jdbcTemplate);
        _logger.debug("JdbcAuthenticationRealm inited.");
        return authenticationRealm;
    }
	
	@Bean(name = "tfaOptAuthn")
    public TimeBasedOtpAuthn tfaOptAuthn() {
	    TimeBasedOtpAuthn tfaOptAuthn = new TimeBasedOtpAuthn();
	    _logger.debug("TimeBasedOtpAuthn inited.");
        return tfaOptAuthn;
    }
	
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }

}
