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
 

package org.dromara.maxkey.authn.session.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.authn.session.VisitedDto;
import org.dromara.maxkey.entity.history.HistoryLogin;
import org.dromara.maxkey.persistence.redis.RedisConnection;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RedisSessionManager implements SessionManager {
    private static final Logger _logger = LoggerFactory.getLogger(RedisSessionManager.class);
	
    protected int validitySeconds = 60 * 30; //default 30 minutes.
    
	RedisConnectionFactory connectionFactory;
	
	public static String PREFIX="MXK_SESSION_";
	
	public String getKey(String sessionId) {
		return PREFIX + sessionId;
	}
	
	/**
	 * @param connectionFactory
	 */
	public RedisSessionManager(
			RedisConnectionFactory connectionFactory,
			int validitySeconds) {
		super();
		this.connectionFactory = connectionFactory;
		this.validitySeconds = validitySeconds;
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
	public void create(String sessionId, Session session) {
		_logger.debug("store session key {} .",sessionId);
		session.setExpiredTime(session.getLastAccessTime().plusSeconds(validitySeconds));
		RedisConnection conn = connectionFactory.getConnection();
		_logger.trace("store session {} ...",sessionId);
		conn.setexObject( getKey(sessionId), validitySeconds, session);
		_logger.debug("store session {} successful .",sessionId);
		_logger.trace("close conn ...");
		conn.close();
		_logger.trace("close conn successful .");
	}

	@Override
	public Session remove(String sessionId) {
		RedisConnection conn=connectionFactory.getConnection();
		Session ticket = conn.getObject(getKey(sessionId));
		conn.delete(getKey(sessionId));
		conn.close();
		return ticket;
	}

    @Override
    public Session get(String sessionId) {
        RedisConnection conn=connectionFactory.getConnection();
        Session session = conn.getObject(getKey(sessionId));
        conn.close();
        return session;
    }

    @Override
    public int getValiditySeconds() {
		return validitySeconds;
	}

	public void setValiditySeconds(int validitySeconds) {
		this.validitySeconds = validitySeconds;
	}

	@Override
    public Session refresh(String sessionId,LocalDateTime refreshTime) {
        Session session = get(sessionId);
        if(session != null) {
        	_logger.debug("refresh session Id {} at {}",sessionId,refreshTime);
	        session.setLastAccessTime(refreshTime);
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
        	create(sessionId , session);
        }
        return session;
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
