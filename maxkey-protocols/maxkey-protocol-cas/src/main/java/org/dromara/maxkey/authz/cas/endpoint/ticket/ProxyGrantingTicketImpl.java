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

import org.dromara.maxkey.entity.apps.AppsCasDetails;
import org.springframework.security.core.Authentication;

/**
 * The {@link ProxyGrantingTicketImpl} is a concrete implementation of the {@link ProxyTicket}.
 *
 * @author Misagh Moayyed
 * @since 4.2
 */
public class ProxyGrantingTicketImpl extends ServiceTicketImpl implements Ticket {
    private static final long serialVersionUID = -4469960563289285371L;

    /**
     * Instantiates a new Proxy ticket.
     */
    public ProxyGrantingTicketImpl() {
    }

    /**
     * Instantiates a new Proxy ticket.
     *
     * @param id                 the id
     * @param ticket             the ticket
     * @param service            the service
     * @param credentialProvided the credential that prompted this ticket. Could be false.
     * @param policy             the expiration policy
     */
    public ProxyGrantingTicketImpl(final String id,  final Service service,
                           final boolean credentialProvided) {

    }
    
    public ProxyGrantingTicketImpl(Authentication authentication,  AppsCasDetails casDetails) {
    	this.authentication=authentication;
    	this.casDetails=casDetails;
    }
}
