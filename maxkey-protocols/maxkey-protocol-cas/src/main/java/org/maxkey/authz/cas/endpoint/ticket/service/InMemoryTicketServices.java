package org.maxkey.authz.cas.endpoint.ticket.service;

import java.time.Duration;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.maxkey.authz.cas.endpoint.ticket.Ticket;


public class InMemoryTicketServices extends RandomServiceTicketServices {

	protected final static  UserManagedCache<String, Ticket> casTicketStore = 
			UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, Ticket.class)
				.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(60)))
				.build(true);

	
	@Override
	protected void store(String ticketId, Ticket ticket) {
		casTicketStore.put(ticketId, ticket);
	}

	@Override
	public Ticket remove(String ticketId) {
		Ticket ticket=casTicketStore.get(ticketId);	
		casTicketStore.remove(ticketId);
		return ticket;
	}

}
