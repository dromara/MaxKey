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
 

package org.maxkey.persistence;

import org.maxkey.persistence.redis.RedisConnection;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RedisMomentaryService implements MomentaryService {
    private static final Logger _logger = LoggerFactory.getLogger(RedisMomentaryService.class);
	
	protected int validitySeconds = 60 * 5; //default 5 minutes.
	
	RedisConnectionFactory connectionFactory;
	
	public static String PREFIX="REDIS_MOMENTARY_";
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
	public  void put(String ticket , String name, Object value){
		RedisConnection conn = connectionFactory.getConnection();
		conn.setexObject(getKey(ticket , name), validitySeconds, value);
		conn.close();
	}

    @Override
    public Object get(String ticket , String name) {
        RedisConnection conn = connectionFactory.getConnection();
        Object value = conn.getObject(getKey(ticket , name));
        conn.close();
        return value;
    }

	@Override
	public Object remove(String ticket, String name) {
		RedisConnection conn = connectionFactory.getConnection();
        Object value = conn.getObject(getKey(ticket , name));
        conn.delete(getKey(ticket , name));
        conn.close();
        return value;
	}
	

    private String getKey(String ticket , String name) {
    	return PREFIX + ticket + name;
    }



	
}
