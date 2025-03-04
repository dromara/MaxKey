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
 

package org.dromara.maxkey.authn.session.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.authn.session.VisitedDto;
import org.dromara.maxkey.entity.history.HistoryLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


public class InMemorySessionManager implements SessionManager{
    private static final Logger _logger = LoggerFactory.getLogger(InMemorySessionManager.class);

    static final 	long 	CACHE_MAXIMUM_SIZE 	= 2000000;
    protected 		int 	validitySeconds 	= 60 * 30; //default 30 minutes.
    
	protected  static  Cache<String, Session> sessionStore = 
        	        Caffeine.newBuilder()
        	            .expireAfterWrite(10, TimeUnit.MINUTES)
        	            .maximumSize(CACHE_MAXIMUM_SIZE)
        	            .build();
	
	public InMemorySessionManager(int validitySeconds) {
        super();
        this.validitySeconds = validitySeconds;
        sessionStore = 
                Caffeine.newBuilder()
                    .expireAfterWrite(validitySeconds, TimeUnit.SECONDS)
                    .maximumSize(CACHE_MAXIMUM_SIZE)
                    .build();
        
    }

    @Override
	public void create(String sessionId, Session session) {
    	session.setExpiredTime(session.getLastAccessTime().plusSeconds(validitySeconds));
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
    public Session refresh(String sessionId,LocalDateTime refreshTime) {
        Session session = get(sessionId);
        if(session != null) {
        	_logger.debug("refresh session Id {} at refreshTime {}",sessionId,refreshTime);
	        session.setLastAccessTime(refreshTime);
	        //put new session
	        create(sessionId , session);
        }
        return session;
    }

    @Override
    public Session refresh(String sessionId) {
        Session session = get(sessionId);
        
        if(session != null) {
        	LocalDateTime currentTime = LocalDateTime.now();
        	_logger.debug("refresh session Id {} at time {}",sessionId,currentTime);
        	session.setLastAccessTime(currentTime);
        	//sessionId then renew one
	        create(sessionId , session);
        }
        return session;
    }

    @Override
	public int getValiditySeconds() {
		return validitySeconds;
	}

	@Override
	public List<HistoryLogin> querySessions(Integer category) {
		// not need implement
		return null;
	}

	@Override
	public void terminate(String sessionId, String userId, String username) {
		// not need implement
	}

	@Override
	public void visited(String sessionId, VisitedDto visited) {
		Session session  = this.get(sessionId);
		if(session != null) {
		    //set token or ticket to Visited , bind user session
			session.visited(visited);
			//override the session
		    this.create(sessionId, session);
		    _logger.debug("session {} store visited  {} ." , sessionId , visited);
		}
	}

}
