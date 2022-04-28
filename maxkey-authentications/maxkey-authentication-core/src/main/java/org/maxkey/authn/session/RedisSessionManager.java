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
 

package org.maxkey.authn.session;

import java.time.Duration;
import java.time.LocalTime;

import org.maxkey.persistence.redis.RedisConnection;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;


public class RedisSessionManager extends AbstractSessionManager {
    private static final Logger _logger = LoggerFactory.getLogger(RedisSessionManager.class);
	
	protected int serviceTicketValiditySeconds = 60 * 30; //default 30 minutes.
	
	RedisConnectionFactory connectionFactory;
	
	public static String PREFIX="REDIS_SESSION_";
	/**
	 * @param connectionFactory
	 */
	public RedisSessionManager(
			RedisConnectionFactory connectionFactory,
			JdbcTemplate jdbcTemplate) {
		super();
		this.connectionFactory = connectionFactory;
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 
	 */
	public RedisSessionManager() {
		
	}

	public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public void create(String sessionId, Session ticket) {
		RedisConnection conn = connectionFactory.getConnection();
		conn.setexObject(PREFIX + sessionId, serviceTicketValiditySeconds, ticket);
		conn.close();
	}

	@Override
	public Session remove(String sessionId) {
		RedisConnection conn=connectionFactory.getConnection();
		Session ticket = conn.getObject(PREFIX+sessionId);
		conn.delete(PREFIX+sessionId);
		conn.close();
		return ticket;
	}

    @Override
    public Session get(String sessionId) {
        RedisConnection conn=connectionFactory.getConnection();
        Session session = conn.getObject(PREFIX+sessionId);
        conn.close();
        return session;
    }

    @Override
    public void setValiditySeconds(int validitySeconds) {
       this.serviceTicketValiditySeconds = validitySeconds;
        
    }

    @Override
    public void refresh(String sessionId,LocalTime refreshTime) {
        Session session = get(sessionId);
        session.setLastAccessTime(refreshTime);
        create(sessionId , session);
    }
    
    @Override
    public void refresh(String sessionId) {
        Session session = get(sessionId);
        
        LocalTime currentTime = LocalTime.now();
        Duration duration = Duration.between(currentTime, session.getLastAccessTime());
        
        _logger.trace("Session duration " + duration.getSeconds());
        
        if(duration.getSeconds() > Session.MAX_EXPIRY_DURATION) {
        	session.setLastAccessTime(currentTime);
            refresh(sessionId,currentTime);
        }
    }

	
}
