package org.maxkey.authz.cas.endpoint.ticket.service;

import org.maxkey.authz.cas.endpoint.ticket.Ticket;
import org.maxkey.authz.oauth2.common.exceptions.InvalidGrantException;

public interface TicketServices {

	/**
	 * Create a authorization code for the specified authentications.
	 * 
	 * @param authentication The authentications to store.
	 * @return The generated code.
	 */
	String createTicket(Ticket ticket);

	/**
	 * Consume a authorization code.
	 * 
	 * @param code The authorization code to consume.
	 * @return The authentications associated with the code.
	 * @throws InvalidGrantException If the authorization code is invalid or expired.
	 */
	Ticket consumeTicket(String ticketId)
			throws InvalidGrantException;

}
