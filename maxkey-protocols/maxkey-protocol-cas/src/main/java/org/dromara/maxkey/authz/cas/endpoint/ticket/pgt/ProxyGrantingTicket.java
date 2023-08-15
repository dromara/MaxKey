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

import org.dromara.maxkey.authz.cas.endpoint.ticket.ExpirationPolicy;
import org.dromara.maxkey.authz.cas.endpoint.ticket.Service;
import org.dromara.maxkey.authz.cas.endpoint.ticket.TicketGrantingTicket;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Interface for a proxy granting ticket. A proxy-granting ticket is an opaque string that is
 * used by a service to obtain proxy tickets for obtaining access to a back-end service on behalf of a client.
 * Proxy-granting tickets are obtained from CAS upon validation of a service ticket or a proxy ticket.
 *
 * @author Misagh Moayyed
 * @since 4.2
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface ProxyGrantingTicket extends TicketGrantingTicket {

    /** The prefix to use when generating an id for a Proxy Granting Ticket. */
    String PROXY_GRANTING_TICKET_PREFIX = "PGT";

    /** The prefix to use when generating an id for a Proxy Granting Ticket IOU. */
    String PROXY_GRANTING_TICKET_IOU_PREFIX = "PGTIOU";

    /**
     * Grant a proxy ticket for a specific service.
     *
     * @param id The unique identifier for this ticket.
     * @param service The service for which we are granting a ticket
     * @param expirationPolicy the expiration policy.
     * @param onlyTrackMostRecentSession track the most recent session by keeping the latest service ticket
     * @return the service ticket granted to a specific service for the
     * principal of the TicketGrantingTicket
     */
    ProxyTicket grantProxyTicket(String id, Service service,
                                 ExpirationPolicy expirationPolicy,
                                 boolean onlyTrackMostRecentSession);

}

