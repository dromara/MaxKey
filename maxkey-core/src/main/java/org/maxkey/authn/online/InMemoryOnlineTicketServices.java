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

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;


public class InMemoryOnlineTicketServices implements OnlineTicketServices{

	protected final static  UserManagedCache<String, OnlineTicket> onlineTicketStore = 
			UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, OnlineTicket.class)
				.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(30)))
				.build(true);

	
	@Override
	public void store(String ticketId, OnlineTicket ticket) {
	    onlineTicketStore.put(ticketId, ticket);
	}

	@Override
	public OnlineTicket remove(String ticketId) {
	    OnlineTicket ticket=onlineTicketStore.get(ticketId);	
	    onlineTicketStore.remove(ticketId);
		return ticket;
	}

    @Override
    public OnlineTicket get(String ticketId) {
        OnlineTicket ticket=onlineTicketStore.get(ticketId); 
        return ticket;
    }

}
