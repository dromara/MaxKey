/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authz.cas.endpoint.ticket.pgt;

import org.dromara.maxkey.authz.cas.endpoint.ticket.TicketServices;
import org.dromara.maxkey.constants.ConstsPersistence;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
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
        if (persistence == ConstsPersistence.INMEMORY) {
            casTicketServices = new InMemoryProxyGrantingTicketServices();
            _logger.debug("InMemoryTicketServices");
        } else if (persistence == ConstsPersistence.JDBC) {
            //casTicketServices = new JdbcTicketServices(jdbcTemplate);
            _logger.debug("JdbcTicketServices not support ");
        } else if (persistence == ConstsPersistence.REDIS) {
            casTicketServices = new RedisProxyGrantingTicketServices(redisConnFactory);
            _logger.debug("RedisTicketServices");
        }
        return casTicketServices;
    }
}
