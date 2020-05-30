package org.maxkey.autoconfigure;

import org.maxkey.authz.cas.endpoint.ticket.service.InMemoryTicketServices;
import org.maxkey.authz.cas.endpoint.ticket.service.JdbcTicketServices;
import org.maxkey.authz.cas.endpoint.ticket.service.RedisTicketServices;
import org.maxkey.authz.cas.endpoint.ticket.service.TicketServices;
import org.maxkey.constants.ConstantsProperties;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan(basePackages = {
        "org.maxkey.authz.cas.endpoint"
})
@PropertySource(ConstantsProperties.maxKeyPropertySource)
public class CasAutoConfiguration implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(CasAutoConfiguration.class);
    
    /**
     * TicketServices. 
     * @param persistence int
     * @param validity int
     * @return casTicketServices
     */
    @Bean(name = "casTicketServices")
    public TicketServices casTicketServices(
            @Value("${config.server.persistence}") int persistence,
            @Value("${config.login.remeberme.validity}") int validity,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory jedisConnectionFactory) {
        TicketServices casTicketServices = null;
        if (persistence == 0) {
            casTicketServices = new InMemoryTicketServices();
            _logger.debug("InMemoryTicketServices");
        } else if (persistence == 1) {
            casTicketServices = new JdbcTicketServices(jdbcTemplate);
            _logger.debug("JdbcTicketServices");
        } else if (persistence == 2) {
            casTicketServices = new RedisTicketServices(jedisConnectionFactory);
            _logger.debug("RedisTicketServices");
        }
        return casTicketServices;
    }
   

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
