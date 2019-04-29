package org.maxkey.authz.cas.endpoint.ticket.service;

import org.maxkey.authz.cas.endpoint.ticket.Ticket;
import org.maxkey.persistence.redis.RedisConnection;
import org.maxkey.persistence.redis.RedisConnectionFactory;


public class RedisTicketServices extends RandomServiceTicketServices {

	protected int serviceTicketValiditySeconds = 60 * 10; //default 10 minutes.
	
	RedisConnectionFactory connectionFactory;
	
	public static String PREFIX="REDIS_CAS_SERVICE_TICKET_";
	/**
	 * @param connectionFactory
	 */
	public RedisTicketServices(RedisConnectionFactory connectionFactory) {
		super();
		this.connectionFactory = connectionFactory;
	}
	
	/**
	 * 
	 */
	public RedisTicketServices() {
		
	}

	public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	protected void store(String ticketId, Ticket ticket) {
		RedisConnection conn=connectionFactory.getConnection();
		conn.setexObject(PREFIX+ticketId, serviceTicketValiditySeconds, ticket);
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

	
}
