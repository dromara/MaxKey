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
import org.dromara.maxkey.authz.cas.endpoint.ticket.pgt.InMemoryProxyGrantingTicketServices;
import org.dromara.maxkey.authz.cas.endpoint.ticket.pgt.RedisProxyGrantingTicketServices;
import org.dromara.maxkey.authz.cas.endpoint.ticket.st.InMemoryTicketServices;
import org.dromara.maxkey.authz.cas.endpoint.ticket.st.RedisTicketServices;
import org.dromara.maxkey.authz.cas.endpoint.ticket.tgt.InMemoryTicketGrantingTicketServices;
import org.dromara.maxkey.authz.cas.endpoint.ticket.tgt.RedisTicketGrantingTicketServices;
import org.dromara.maxkey.constants.ConstsPersistence;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

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
    TicketServices casTicketServices(
            @Value("${maxkey.server.persistence}") int persistence,
            RedisConnectionFactory redisConnFactory) {
    	_logger.debug("init casTicketServices.");
    	TicketServices casTicketServices = null;
        if (persistence == ConstsPersistence.REDIS) {
            casTicketServices = new RedisTicketServices(redisConnFactory);
            _logger.debug("RedisTicketServices");
        }else {
        	casTicketServices = new InMemoryTicketServices();
            _logger.debug("InMemoryTicketServices");
        }
        return casTicketServices;
    }

    /**
     * TicketServices. 
     * @param persistence int
     * @param validity int
     * @return casTicketServices
     */
    @Bean(name = "casTicketGrantingTicketServices")
    TicketServices casTicketGrantingTicketServices(
            @Value("${maxkey.server.persistence}") int persistence,
            RedisConnectionFactory redisConnFactory) {
    	_logger.debug("init casTicketGrantingTicketServices.");
    	TicketServices casTicketServices = null;
        if (persistence == ConstsPersistence.REDIS) {
            casTicketServices = new RedisTicketGrantingTicketServices(redisConnFactory);
            _logger.debug("RedisTicketGrantingTicketServices");
        }else {
        	casTicketServices = new InMemoryTicketGrantingTicketServices();
            _logger.debug("InMemoryTicketGrantingTicketServices");
        }
        return casTicketServices;
    }

    @Bean(name = "casProxyGrantingTicketServices")
    TicketServices casProxyGrantingTicketServices(
            @Value("${maxkey.server.persistence}") int persistence,
            RedisConnectionFactory redisConnFactory) {
    	_logger.debug("init casTicketGrantingTicketServices.");
    	TicketServices casTicketServices = null;
        if (persistence == ConstsPersistence.REDIS) {
            casTicketServices = new RedisProxyGrantingTicketServices(redisConnFactory);
            _logger.debug("RedisProxyGrantingTicketServices");
        }else {
        	casTicketServices = new InMemoryProxyGrantingTicketServices();
            _logger.debug("InMemoryProxyGrantingTicketServices");
        }
        return casTicketServices;
    }
    
    
    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
}
