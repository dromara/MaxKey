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
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


public class InMemorySessionManager extends AbstractSessionManager{
    private static final Logger _logger = LoggerFactory.getLogger(InMemorySessionManager.class);

	protected  static  Cache<String, Session> sessionStore = 
        	        Caffeine.newBuilder()
        	            .expireAfterWrite(30, TimeUnit.MINUTES)
        	            .maximumSize(200000)
        	            .build();
	
	public InMemorySessionManager(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
	public void create(String sessionId, Session session) {
    	sessionStore.put(sessionId, session);
	}

	@Override
	public Session remove(String sessionId) {
	    Session session = sessionStore.getIfPresent(sessionId);	
	    sessionStore.invalidate(sessionId);
		return session;
	}

    @Override
    public Session get(String sessionId) {
        Session session = sessionStore.getIfPresent(sessionId); 
        return session;
    }

    @Override
    public void setValiditySeconds(int validitySeconds) {
    	sessionStore = 
                Caffeine.newBuilder()
                    .expireAfterWrite(validitySeconds/60, TimeUnit.MINUTES)
                    .maximumSize(200000)
                    .build();
        
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
        
        _logger.trace("OnlineTicket duration " + duration.getSeconds());
        
        if(duration.getSeconds() > Session.MAX_EXPIRY_DURATION) {
        	session.setLastAccessTime(currentTime);
            refresh(sessionId,currentTime);
        }
    }

}
