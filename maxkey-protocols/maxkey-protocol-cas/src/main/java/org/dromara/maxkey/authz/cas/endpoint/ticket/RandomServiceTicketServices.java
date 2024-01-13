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
 

package org.dromara.maxkey.authz.cas.endpoint.ticket;

import org.dromara.maxkey.authz.cas.endpoint.ticket.generator.DefaultUniqueTicketIdGenerator;


public abstract class RandomServiceTicketServices implements TicketServices {

	//default Random code Generator
	//private RandomValueStringGenerator generator = new RandomValueStringGenerator();
	
	private DefaultUniqueTicketIdGenerator generator=new DefaultUniqueTicketIdGenerator();

	@Override
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
		}else if(ticket.getClass().getSimpleName().equalsIgnoreCase("TicketGrantingTicketImpl")){
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.TICKET_GRANTING_TICKET_PREFIX);
		}else if(ticket.getClass().getSimpleName().equalsIgnoreCase("ProxyGrantingTicketImpl")){
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.PROXY_GRANTING_TICKET_PREFIX);
		}else if(ticket.getClass().getSimpleName().equalsIgnoreCase("ProxyGrantingTicketIOUImpl")){
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.PROXY_GRANTING_TICKET_IOU_PREFIX);
			return ticketId;
		}else {
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.SERVICE_TICKET_PREFIX);
		}
		
		store(ticketId, ticket);
		return ticketId;
	}

	@Override
	public String createTicket(Ticket ticket, int validitySeconds) {
		
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
		}else if(ticket.getClass().getSimpleName().equalsIgnoreCase("TicketGrantingTicketImpl")){
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.TICKET_GRANTING_TICKET_PREFIX);
		}else if(ticket.getClass().getSimpleName().equalsIgnoreCase("ProxyGrantingTicketImpl")){
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.PROXY_GRANTING_TICKET_PREFIX);
		}else if(ticket.getClass().getSimpleName().equalsIgnoreCase("ProxyGrantingTicketIOUImpl")){
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.PROXY_GRANTING_TICKET_IOU_PREFIX);
			return ticketId;
		}else {
			ticketId = generator.getNewTicketId(CasConstants.PREFIX.SERVICE_TICKET_PREFIX);
		}
		
		store(ticketId, ticket,validitySeconds);
		return ticketId;
	}

	@Override
	public Ticket consumeTicket(String ticketId) throws Exception{
		Ticket  ticket = this.remove(ticketId);
		if (ticket == null) {
			throw new Exception("Invalid authorization code: " + ticketId);
		}
		return ticket;
	}

}
