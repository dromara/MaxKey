package org.maxkey.authz.cas.endpoint.ticket.service;

import java.util.concurrent.ConcurrentHashMap;

import org.maxkey.authz.cas.endpoint.ticket.Ticket;


public class InMemoryTicketServices extends RandomServiceTicketServices {

	protected final static ConcurrentHashMap<String, Ticket> authorizationTicketStore = new ConcurrentHashMap<String, Ticket>();

	@Override
	protected void store(String ticketId, Ticket ticket) {
		this.authorizationTicketStore.put(ticketId, ticket);
	}

	@Override
	public Ticket remove(String ticketId) {
		Ticket ticket = this.authorizationTicketStore.remove(ticketId);
		return ticket;
	}

}
