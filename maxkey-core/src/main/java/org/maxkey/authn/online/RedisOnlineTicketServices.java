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
 

package org.maxkey.authn.online;

import java.time.Duration;
import java.time.LocalTime;

import org.maxkey.persistence.redis.RedisConnection;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RedisOnlineTicketServices implements OnlineTicketServices {
    private static final Logger _logger = LoggerFactory.getLogger(RedisOnlineTicketServices.class);
	
	protected int serviceTicketValiditySeconds = 60 * 30; //default 30 minutes.
	
	RedisConnectionFactory connectionFactory;
	
	public static String PREFIX="REDIS_ONLINE_TICKET_";
	/**
	 * @param connectionFactory
	 */
	public RedisOnlineTicketServices(RedisConnectionFactory connectionFactory) {
		super();
		this.connectionFactory = connectionFactory;
	}
	
	/**
	 * 
	 */
	public RedisOnlineTicketServices() {
		
	}

	public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public void store(String ticketId, OnlineTicket ticket) {
		RedisConnection conn=connectionFactory.getConnection();
		conn.setexObject(PREFIX+ticketId, serviceTicketValiditySeconds, ticket);
		conn.close();
	}

	@Override
	public OnlineTicket remove(String ticketId) {
		RedisConnection conn=connectionFactory.getConnection();
		OnlineTicket ticket = conn.getObject(PREFIX+ticketId);
		conn.delete(PREFIX+ticketId);
		conn.close();
		return ticket;
	}

    @Override
    public OnlineTicket get(String ticketId) {
        RedisConnection conn=connectionFactory.getConnection();
        OnlineTicket ticket = conn.getObject(PREFIX+ticketId);
        conn.close();
        return ticket;
    }

    @Override
    public void setValiditySeconds(int validitySeconds) {
       this.serviceTicketValiditySeconds = validitySeconds;
        
    }

    @Override
    public void refresh(String ticketId,LocalTime refreshTime) {
        OnlineTicket onlineTicket = get(ticketId);
        onlineTicket.setTicketTime(refreshTime);
        store(ticketId , onlineTicket);
    }
    
    @Override
    public void refresh(String ticketId) {
        OnlineTicket onlineTicket = get(ticketId);
        
        LocalTime currentTime = LocalTime.now();
        Duration duration = Duration.between(currentTime, onlineTicket.getTicketTime());
        
        _logger.trace("OnlineTicket duration " + duration.getSeconds());
        
        if(duration.getSeconds() > OnlineTicket.MAX_EXPIRY_DURATION) {
            onlineTicket.setTicketTime(currentTime);
            refresh(ticketId,currentTime);
        }
    }

	
}
