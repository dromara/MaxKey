package org.maxkey.authz.oauth2.provider.code;

import org.maxkey.constants.ConstantsPersistence;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class AuthorizationCodeServicesFactory {
	private static final  Logger _logger = LoggerFactory.getLogger(AuthorizationCodeServicesFactory.class);
	
	 public AuthorizationCodeServices getService(
	            int persistence,
	            JdbcTemplate jdbcTemplate,
	            RedisConnectionFactory redisConnFactory) {
	        AuthorizationCodeServices authorizationCodeServices = null;
	        if (persistence == ConstantsPersistence.INMEMORY) {
	            authorizationCodeServices = new InMemoryAuthorizationCodeServices();
	            _logger.debug("InMemoryAuthorizationCodeServices");
	        } else if (persistence == ConstantsPersistence.JDBC) {
	            //authorizationCodeServices = new JdbcAuthorizationCodeServices(jdbcTemplate);
	            _logger.debug("JdbcAuthorizationCodeServices not support "); 
	        } else if (persistence == ConstantsPersistence.REDIS) {
	            authorizationCodeServices = new RedisAuthorizationCodeServices(redisConnFactory);
	            _logger.debug("RedisAuthorizationCodeServices");
	        }
	        return authorizationCodeServices;
	    }
}
