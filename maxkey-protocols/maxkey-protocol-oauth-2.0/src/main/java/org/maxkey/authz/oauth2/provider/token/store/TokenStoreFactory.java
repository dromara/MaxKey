package org.maxkey.authz.oauth2.provider.token.store;

import org.maxkey.authz.oauth2.provider.token.TokenStore;
import org.maxkey.constants.ConstantsPersistence;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class TokenStoreFactory {
	private static final  Logger _logger = LoggerFactory.getLogger(TokenStoreFactory.class);
	
	 public TokenStore getTokenStore(
	            int persistence,
	            JdbcTemplate jdbcTemplate,
	            RedisConnectionFactory redisConnFactory) {
	        TokenStore tokenStore = null;
	        if (persistence == ConstantsPersistence.INMEMORY) {
	            tokenStore = new InMemoryTokenStore();
	            _logger.debug("InMemoryTokenStore");
	        } else if (persistence == ConstantsPersistence.JDBC) {
	            //tokenStore = new JdbcTokenStore(jdbcTemplate);
	            _logger.debug("JdbcTokenStore not support "); 
	        } else if (persistence == ConstantsPersistence.REDIS) {
	            tokenStore = new RedisTokenStore(redisConnFactory);
	            _logger.debug("RedisTokenStore");
	        }
	        return tokenStore;
	    }
}
