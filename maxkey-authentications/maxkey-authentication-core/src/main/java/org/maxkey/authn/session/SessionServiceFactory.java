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
 

package org.maxkey.authn.session;

import org.maxkey.constants.ConstsPersistence;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class SessionServiceFactory {
	private static final  Logger _logger = 
            LoggerFactory.getLogger(SessionServiceFactory.class);
	
	 public SessionService getService(
			 	int persistence,
			 	JdbcTemplate jdbcTemplate,
	            RedisConnectionFactory redisConnFactory){
		 
		 SessionService sessionService = null;
		if (persistence == ConstsPersistence.INMEMORY) {
			sessionService = new InMemorySessionService(jdbcTemplate);
		    _logger.debug("InMemorySessionService");
		} else if (persistence == ConstsPersistence.JDBC) {
		    _logger.debug("JdbcSessionService not support "); 
		} else if (persistence == ConstsPersistence.REDIS) {
			sessionService = new RedisSessionService(redisConnFactory,jdbcTemplate);
		    _logger.debug("RedisSessionService");
		}
		
		return sessionService;
	}
}
