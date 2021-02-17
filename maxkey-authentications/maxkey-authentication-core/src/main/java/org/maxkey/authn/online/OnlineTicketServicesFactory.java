package org.maxkey.authn.online;

import org.maxkey.constants.ConstantsPersistence;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class OnlineTicketServicesFactory {
	private static final  Logger _logger = 
            LoggerFactory.getLogger(OnlineTicketServicesFactory.class);
	
	 public OnlineTicketServices getService(
			 	int persistence,
			 	JdbcTemplate jdbcTemplate,
	            RedisConnectionFactory redisConnFactory){
		 
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
}
