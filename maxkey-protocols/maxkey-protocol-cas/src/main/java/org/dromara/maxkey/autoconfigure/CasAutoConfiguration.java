/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.dromara.maxkey.autoconfigure;

import org.dromara.maxkey.authz.cas.endpoint.ticket.TicketServices;
import org.dromara.maxkey.authz.cas.endpoint.ticket.pgt.ProxyGrantingTicketServicesFactory;
import org.dromara.maxkey.authz.cas.endpoint.ticket.st.TicketServicesFactory;
import org.dromara.maxkey.authz.cas.endpoint.ticket.tgt.TicketGrantingTicketServicesFactory;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

@AutoConfiguration
@ComponentScan(basePackages = {
        "org.maxkey.authz.cas.endpoint"
})
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
            @Value("${maxkey.server.persistence}") int persistence,
            @Value("${maxkey.login.remeberme.validity}") int validity,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory) {
    	_logger.debug("init casTicketServices.");
        return new TicketServicesFactory().getService(persistence, jdbcTemplate, redisConnFactory);
    }
   
    /**
     * TicketServices. 
     * @param persistence int
     * @param validity int
     * @return casTicketServices
     */
    @Bean(name = "casTicketGrantingTicketServices")
    public TicketServices casTicketGrantingTicketServices(
            @Value("${maxkey.server.persistence}") int persistence,
            @Value("${maxkey.login.remeberme.validity}") int validity,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory) {
    	_logger.debug("init casTicketGrantingTicketServices.");
        return new TicketGrantingTicketServicesFactory().getService(persistence, jdbcTemplate, redisConnFactory);
    }
    
    @Bean(name = "casProxyGrantingTicketServices")
    public TicketServices casProxyGrantingTicketServices(
            @Value("${maxkey.server.persistence}") int persistence,
            @Value("${maxkey.login.remeberme.validity}") int validity,
            JdbcTemplate jdbcTemplate,
            RedisConnectionFactory redisConnFactory) {
    	_logger.debug("init casTicketGrantingTicketServices.");
        return new ProxyGrantingTicketServicesFactory().getService(persistence, jdbcTemplate, redisConnFactory);
    }
    
    
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
}
