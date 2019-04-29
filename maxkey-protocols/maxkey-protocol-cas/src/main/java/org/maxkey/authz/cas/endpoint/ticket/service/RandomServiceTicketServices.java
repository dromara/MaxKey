package org.maxkey.authz.cas.endpoint.ticket.service;

import org.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.maxkey.authz.cas.endpoint.ticket.Ticket;
import org.maxkey.authz.cas.endpoint.ticket.generator.DefaultUniqueTicketIdGenerator;
import org.maxkey.authz.oauth2.common.exceptions.InvalidGrantException;


public abstract class RandomServiceTicketServices implements TicketServices {

	//default Random code Generator
	//private RandomValueStringGenerator generator = new RandomValueStringGenerator();
	
	private DefaultUniqueTicketIdGenerator generator=new DefaultUniqueTicketIdGenerator();
	

	protected abstract void store(String ticketId, Ticket ticket);

	protected abstract Ticket remove(String ticket);

	public String createTicket(Ticket ticket) {
		//String code = generator.generate();
		/*
		 * replace with uuid random code
		 * add by Crystal.Sea
		 */
		//String ticket = UUID.randomUUID().toString();
		String ticketId = "";
		if(ticket.getClass().getSimpleName().equalsIgnoreCase("ServiceTicketImpl")){
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.SERVICE_TICKET_PREFIX);
		}else if(ticket.getClass().getSimpleName().equalsIgnoreCase("ProxyTicketImpl")){
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.PROXY_TICKET_PREFIX);
		}else if(ticket.getClass().getSimpleName().equalsIgnoreCase("ProxyTicketImpl")){
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.PROXY_TICKET_PREFIX);
		}else if(ticket.getClass().getSimpleName().equalsIgnoreCase("ProxyTicketImpl")){
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.PROXY_TICKET_PREFIX);
		}
		store(ticketId, ticket);
		return ticketId;
	}

	public Ticket consumeTicket(String ticketId)
			throws InvalidGrantException {
		Ticket  ticket = this.remove(ticketId);
		if (ticket == null) {
			throw new InvalidGrantException("Invalid authorization code: " + ticketId);
		}
		return ticket;
	}

}
