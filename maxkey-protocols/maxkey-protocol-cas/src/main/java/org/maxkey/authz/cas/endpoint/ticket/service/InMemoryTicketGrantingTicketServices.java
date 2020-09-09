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
 

package org.maxkey.authz.cas.endpoint.ticket.service;

import java.time.Duration;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.maxkey.authz.cas.endpoint.ticket.Ticket;


public class InMemoryTicketGrantingTicketServices extends RandomServiceTicketServices {

	protected final static  UserManagedCache<String, Ticket> casTicketGrantingTicketStore = 
			UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, Ticket.class)
				.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofDays(2)))
				.build(true);

	
	@Override
	public void store(String ticketId, Ticket ticket) {
	    casTicketGrantingTicketStore.put(ticketId, ticket);
	}

	@Override
	public Ticket remove(String ticketId) {
		Ticket ticket=casTicketGrantingTicketStore.get(ticketId);	
		casTicketGrantingTicketStore.remove(ticketId);
		return ticket;
	}

    @Override
    public Ticket get(String ticketId) {
        Ticket ticket=casTicketGrantingTicketStore.get(ticketId);   
        return ticket;
    }

}
