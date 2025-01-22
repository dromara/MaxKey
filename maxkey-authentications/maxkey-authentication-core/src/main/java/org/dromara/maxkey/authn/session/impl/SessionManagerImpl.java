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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.authn.session.VisitedDto;
import org.dromara.maxkey.constants.ConstsPersistence;
import org.dromara.maxkey.entity.history.HistoryLogin;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;
import org.dromara.maxkey.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * SessionManager
 * Level 1 in memory,store in Caffeine
 * Level 2 in Redis 
 * user session status in database
 * @author shimh
 *
 */
public class SessionManagerImpl implements SessionManager{
	private static final  Logger _logger = LoggerFactory.getLogger(SessionManagerImpl.class);
	
	private static final String DEFAULT_DEFAULT_SELECT_STATEMENT = 
			"select id,sessionid,userId,username,displayname,logintime from mxk_history_login where sessionstatus = 1";
	
    private static final String LOGOUT_USERINFO_UPDATE_STATEMENT = 
    		"update mxk_userinfo set lastlogofftime = ? , online = " + UserInfo.ONLINE.OFFLINE + "  where id = ?";
	
    private static final String HISTORY_LOGOUT_UPDATE_STATEMENT = 
    		"update mxk_history_login set logouttime = ? ,sessionstatus = 7 where  sessionid = ?";
    
    private static final String NO_SESSION_UPDATE_STATEMENT = 
    		"update mxk_history_login set sessionstatus = 7 where sessionstatus = 1 and (sessionid is null or sessionid = '')";

    private JdbcTemplate jdbcTemplate;
    
	private InMemorySessionManager 	inMemorySessionManager;
	
	private RedisSessionManager 	redisSessionManager;
	
	private boolean isRedis = false;	
	
	private int validitySeconds ;
	
	public SessionManagerImpl(int persistence,
			 	JdbcTemplate jdbcTemplate,
	            RedisConnectionFactory redisConnFactory,
	            int validitySeconds) {
		this.validitySeconds = validitySeconds;
		 this.jdbcTemplate = jdbcTemplate;
		 this.inMemorySessionManager = 
				new InMemorySessionManager(validitySeconds);
		 _logger.debug("InMemorySessionManager");
		 if (persistence == ConstsPersistence.REDIS) {
			isRedis = true;
			this.redisSessionManager = 
					new RedisSessionManager(redisConnFactory,validitySeconds);
			_logger.debug("RedisSessionManager");
		 }
	}

	@Override
	public void create(String sessionId, Session session) {
		inMemorySessionManager.create(sessionId, session);
		if(isRedis) {
			redisSessionManager.create(sessionId, session);
		}
	}

	@Override
	public Session remove(String sessionId) {
		Session session = inMemorySessionManager.remove(sessionId);
		if(isRedis) {
			session = redisSessionManager.remove(sessionId);
		}
		return session;
	}

	@Override
	public Session get(String sessionId) {
		Session session = inMemorySessionManager.get(sessionId);
		if(session == null && isRedis) {
			session = redisSessionManager.get(sessionId);
		}
		return session;
	}

	@Override
	public Session refresh(String sessionId, LocalDateTime refreshTime) {
		Session session = null;
		if(isRedis) {
			session = redisSessionManager.refresh(sessionId,refreshTime);
			//renew one in Memory
			inMemorySessionManager.create(sessionId, session);
		}else {
			session = inMemorySessionManager.refresh(sessionId,refreshTime);
		}
		return session;
	}

	@Override
	public Session refresh(String sessionId) {
		Session session = null;
		if(isRedis) {
			session = redisSessionManager.refresh(sessionId);
			//renew one
			inMemorySessionManager.remove(sessionId);
			inMemorySessionManager.create(sessionId, session);
		}else {
			session = inMemorySessionManager.refresh(sessionId);
		}
		
		return session;
	}

	@Override
	public List<HistoryLogin> querySessions(Integer category) {
		//clear session id is null
		jdbcTemplate.execute(NO_SESSION_UPDATE_STATEMENT);
		String sessionSql =  DEFAULT_DEFAULT_SELECT_STATEMENT;
		if(!isRedis) {
			sessionSql = sessionSql + " and category = " + category;
		}
		 _logger.trace("sessionSql {} " ,sessionSql);
		//query on line session
		List<HistoryLogin> listSessions = jdbcTemplate.query(
				sessionSql, 
				new OnlineTicketRowMapper());
		return listSessions;
	}

    private void profileLastLogoffTime(String userId,String lastLogoffTime) {
        _logger.trace("userId {} , lastlogofftime {}" ,userId, lastLogoffTime);
        jdbcTemplate.update(	LOGOUT_USERINFO_UPDATE_STATEMENT, 
        		new Object[] { lastLogoffTime, userId },
                new int[] { 	Types.TIMESTAMP, Types.VARCHAR });
    }
    
    private void sessionLogoff(String sessionId,String lastLogoffTime) {
        _logger.trace("sessionId {} , lastlogofftime {}" ,sessionId, lastLogoffTime);
        jdbcTemplate.update(HISTORY_LOGOUT_UPDATE_STATEMENT,
                new Object[] { lastLogoffTime, sessionId },                           
                new int[] { Types.VARCHAR, Types.VARCHAR });
    }
    
    @Override
	public void terminate(String sessionId, String userId, String username) {
		String lastLogoffTime = DateUtils.formatDateTime(new Date());
	   	 _logger.trace("{} user {} terminate session {} ." ,lastLogoffTime,username, sessionId);
	   	this.profileLastLogoffTime(userId, lastLogoffTime);
	   	this.sessionLogoff(sessionId, lastLogoffTime);
	   	this.remove(sessionId);
	}
	
    @Override
	public int getValiditySeconds() {
		return validitySeconds;
	}
	
	private final class OnlineTicketRowMapper  implements RowMapper<HistoryLogin> {
		@Override
		public HistoryLogin mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			HistoryLogin history=new HistoryLogin();
			history.setId(rs.getString(1));
			history.setSessionId(rs.getString(2));
			history.setUserId(rs.getString(3));
			history.setUsername(rs.getString(4));
			history.setDisplayName(rs.getString(5));
			history.setLoginTime(rs.getTimestamp(6));
			return history;
		}
	}

	@Override
	public void visited(String sessionId, VisitedDto visited) {
        inMemorySessionManager.visited(sessionId,visited);
		if(isRedis) {
			redisSessionManager.visited(sessionId,visited);
		}
	}
}
