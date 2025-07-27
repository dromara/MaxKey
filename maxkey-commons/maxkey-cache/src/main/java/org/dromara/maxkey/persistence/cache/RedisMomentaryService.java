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
 

package org.dromara.maxkey.persistence.cache;

import org.dromara.maxkey.persistence.redis.RedisConnection;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RedisMomentaryService implements MomentaryService {
    private static final Logger _logger = LoggerFactory.getLogger(RedisMomentaryService.class);
	
	protected int validitySeconds = 60 * 5; //default 5 minutes.
	
	RedisConnectionFactory connectionFactory;
	
	public static String PREFIX="MXK_MOMENTARY_";
	/**
	 * @param connectionFactory
	 */
	public RedisMomentaryService(
			RedisConnectionFactory connectionFactory) {
		super();
		this.connectionFactory = connectionFactory;
	}
	
	/**
	 * 
	 */
	public RedisMomentaryService() {
		
	}

	public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public  void put(String sessionId , String name, Object value){
		RedisConnection conn = connectionFactory.getConnection();
		conn.setexObject(getSessionKey(sessionId , name), validitySeconds, value);
		_logger.trace("key {}, validitySeconds {}, value {}",getSessionKey(sessionId , name),validitySeconds,value);
		conn.close();
	}

    @Override
    public Object get(String sessionId , String name) {
        RedisConnection conn = connectionFactory.getConnection();
        Object value = conn.getObject(getSessionKey(sessionId , name));
        _logger.trace("key {}, value {}",getSessionKey(sessionId , name),value);
        conn.close();
        return value;
    }

	@Override
	public Object remove(String sessionId, String name) {
		RedisConnection conn = connectionFactory.getConnection();
        Object value = conn.getObject(getSessionKey(sessionId , name));
        conn.delete(getSessionKey(sessionId , name));
        conn.close();
        _logger.trace("key {}, value {}",getSessionKey(sessionId , name),value);
        return value;
	}
	

    private String getSessionKey(String sessionId , String name) {
    	return PREFIX + sessionId + name;
    }



	
}
