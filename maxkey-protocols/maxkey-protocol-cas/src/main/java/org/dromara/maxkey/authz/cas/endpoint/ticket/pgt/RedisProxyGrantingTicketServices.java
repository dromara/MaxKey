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
 

package org.dromara.maxkey.authz.cas.endpoint.ticket.pgt;

import org.dromara.maxkey.authz.cas.endpoint.ticket.RandomServiceTicketServices;
import org.dromara.maxkey.authz.cas.endpoint.ticket.Ticket;
import org.dromara.maxkey.persistence.redis.RedisConnection;
import org.dromara.maxkey.persistence.redis.RedisConnectionFactory;


public class RedisProxyGrantingTicketServices extends RandomServiceTicketServices {

	
	protected int serviceTicketValiditySeconds = 60 * 60; //default 60 minutes.
	
	RedisConnectionFactory connectionFactory;
	
	public static String PREFIX = "MXK_CAS_TICKET_PGT_";
	/**
	 * @param connectionFactory
	 */
	public RedisProxyGrantingTicketServices(RedisConnectionFactory connectionFactory) {
		super();
		this.connectionFactory = connectionFactory;
	}
	
	/**
	 * 
	 */
	public RedisProxyGrantingTicketServices() {
		
	}

	public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public void store(String ticketId, Ticket ticket) {
		store(ticketId,ticket,serviceTicketValiditySeconds);
	}

	@Override
	public void store(String ticketId, Ticket ticket, int validitySeconds) {
		RedisConnection conn=connectionFactory.getConnection();
		conn.setexObject(PREFIX+ticketId, validitySeconds, ticket);
		conn.close();
	}

	@Override
	public Ticket remove(String ticketId) {
		RedisConnection conn=connectionFactory.getConnection();
		Ticket ticket = conn.getObject(PREFIX+ticketId);
		conn.delete(PREFIX+ticketId);
		conn.close();
		return ticket;
	}

    @Override
    public Ticket get(String ticketId) {
        RedisConnection conn=connectionFactory.getConnection();
        Ticket ticket = conn.getObject(PREFIX+ticketId);
        conn.close();
        return ticket;
    }

	
}
