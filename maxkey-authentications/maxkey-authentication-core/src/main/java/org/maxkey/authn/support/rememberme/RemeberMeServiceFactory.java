package org.maxkey.authn.support.rememberme;

import org.maxkey.constants.ConstantsPersistence;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class RemeberMeServiceFactory {
	private static final  Logger _logger = 
            LoggerFactory.getLogger(RemeberMeServiceFactory.class);
	
	 public AbstractRemeberMeService getService(
			 	int persistence,
			 	JdbcTemplate jdbcTemplate,
	            RedisConnectionFactory redisConnFactory){
		 
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
}
