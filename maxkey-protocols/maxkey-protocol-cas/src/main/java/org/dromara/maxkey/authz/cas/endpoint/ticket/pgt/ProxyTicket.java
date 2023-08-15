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

import org.dromara.maxkey.authz.cas.endpoint.ticket.ServiceTicket;

/**
 * The {@link ProxyTicket} represents a CAS proxy ticket. A proxy ticket is an opaque string that a
 * service uses as a credential to obtain access to a back-end service on behalf of a client.
 * Proxy tickets are obtained from CAS upon a service’s
 * presentation of a valid {@link ProxyGrantingTicket}
 * and a service identifier for the back-end service to which it is connecting.
 *
 * @author Misagh Moayyed
 * @since 4.2
 */
public interface ProxyTicket extends ServiceTicket {
    /** Proxy ticket prefix applied to unique ids. */
    String PROXY_TICKET_PREFIX = "PT";
}
