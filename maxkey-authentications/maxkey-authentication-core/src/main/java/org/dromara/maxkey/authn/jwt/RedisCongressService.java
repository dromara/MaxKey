/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authn.jwt;

import org.dromara.maxkey.persistence.redis.RedisConnection;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RedisCongressService implements CongressService {
    private static final Logger logger = LoggerFactory.getLogger(RedisCongressService.class);
	
	protected int validitySeconds = 60 * 3; //default 3 minutes.
	
	RedisConnectionFactory connectionFactory;
	
	public static final String PREFIX = "REDIS:CONGRESS:";
	/**
	 * @param connectionFactory
	 */
	public RedisCongressService(
			RedisConnectionFactory connectionFactory) {
		super();
		this.connectionFactory = connectionFactory;
	}
	
	/**
	 * 
	 */
	public RedisCongressService() {
		
	}

	public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public void store(String congress, AuthJwt authJwt) {
		RedisConnection conn = connectionFactory.getConnection();
		conn.setexObject(PREFIX + congress, validitySeconds, authJwt);
		conn.close();
	}

	@Override
	public AuthJwt remove(String congress) {
		RedisConnection conn=connectionFactory.getConnection();
		AuthJwt authJwt = conn.getObject(PREFIX + congress);
		conn.delete(PREFIX+congress);
		conn.close();
		return authJwt;
	}

    @Override
    public AuthJwt get(String congress) {
        RedisConnection conn = connectionFactory.getConnection();
        AuthJwt authJwt = conn.getObject(PREFIX + congress);
        conn.close();
        return authJwt;
    }

	@Override
	public AuthJwt consume(String congress) {
		RedisConnection conn=connectionFactory.getConnection();
		AuthJwt authJwt = conn.getObject(PREFIX + congress);
		conn.delete(PREFIX+congress);
		conn.close();
		return authJwt;
	}

	
}
