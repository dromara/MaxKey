package org.maxkey.authz.cas.endpoint.ticket.service;

import org.maxkey.constants.ConstantsPersistence;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class TicketGrantingTicketServicesFactory {
	private static final  Logger _logger = LoggerFactory.getLogger(TicketGrantingTicketServicesFactory.class);
	
    public TicketServices getService(
            int persistence,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory) {
        TicketServices casTicketServices = null;
        if (persistence == ConstantsPersistence.INMEMORY) {
            casTicketServices = new InMemoryTicketGrantingTicketServices();
            _logger.debug("InMemoryTicketGrantingTicketServices");
        } else if (persistence == ConstantsPersistence.JDBC) {
            //
            //casTicketServices = new JdbcTicketGrantingTicketServices(jdbcTemplate);
            _logger.debug("JdbcTicketGrantingTicketServices not support ");
        } else if (persistence == ConstantsPersistence.REDIS) {
            casTicketServices = new RedisTicketGrantingTicketServices(redisConnFactory);
            _logger.debug("RedisTicketServices");
        }
        return casTicketServices;
    }
}
