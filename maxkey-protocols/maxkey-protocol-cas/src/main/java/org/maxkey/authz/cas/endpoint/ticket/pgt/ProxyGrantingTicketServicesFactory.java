package org.maxkey.authz.cas.endpoint.ticket.pgt;

import org.maxkey.authz.cas.endpoint.ticket.TicketServices;
import org.maxkey.constants.ConstantsPersistence;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProxyGrantingTicketServicesFactory {
	private static final  Logger _logger = LoggerFactory.getLogger(ProxyGrantingTicketServicesFactory.class);
	
    public TicketServices getService(
            int persistence,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory) {
    	TicketServices casTicketServices = null;
        if (persistence == ConstantsPersistence.INMEMORY) {
            casTicketServices = new InMemoryProxyGrantingTicketServices();
            _logger.debug("InMemoryTicketServices");
        } else if (persistence == ConstantsPersistence.JDBC) {
            //casTicketServices = new JdbcTicketServices(jdbcTemplate);
            _logger.debug("JdbcTicketServices not support ");
        } else if (persistence == ConstantsPersistence.REDIS) {
            casTicketServices = new RedisProxyGrantingTicketServices(redisConnFactory);
            _logger.debug("RedisTicketServices");
        }
        return casTicketServices;
    }
}
