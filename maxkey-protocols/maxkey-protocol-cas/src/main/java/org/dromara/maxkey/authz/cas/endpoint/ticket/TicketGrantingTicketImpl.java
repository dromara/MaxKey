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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.dromara.maxkey.entity.apps.AppsCasDetails;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of a TicketGrantingTicket. A TicketGrantingTicket is
 * the global identifier of a principal into the system. It grants the Principal
 * single-sign on access to any service that opts into single-sign on.
 * Expiration of a TicketGrantingTicket is controlled by the ExpirationPolicy
 * specified as object creation.
 *
 * @author Scott Battaglia
 * @since 3.0.0
 */
@Entity
@Table(name = "TICKETGRANTINGTICKET")
@DiscriminatorColumn(name = "TYPE")
@DiscriminatorValue(TicketGrantingTicket.PREFIX)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)

public class TicketGrantingTicketImpl extends AbstractTicket implements TicketGrantingTicket {

    /**
     * Unique Id for serialization.
     */
    private static final long serialVersionUID = -8608149809180911599L;
    
    /**
     * Service that produced a proxy-granting ticket.
     */
    @Lob
    @Column(name = "PROXIED_BY", length = Integer.MAX_VALUE)
    private Service proxiedBy;

    /**
     * The services associated to this ticket.
     */
    @Lob
    @Column(name = "SERVICES_GRANTED_ACCESS_TO", nullable = false, length = Integer.MAX_VALUE)
    private HashMap<String, Service> services = new HashMap<>();

    /**
     * The {@link TicketGrantingTicket} this is associated with.
     */
    @ManyToOne(targetEntity = TicketGrantingTicketImpl.class)
    private TicketGrantingTicket ticketGrantingTicket;

    /**
     * The PGTs associated to this ticket.
     */
    @Lob
    @Column(name = "PROXY_GRANTING_TICKETS", nullable = false, length = Integer.MAX_VALUE)
    private HashMap<String, Service> proxyGrantingTickets = new HashMap<>();

    /**
     * The ticket ids which are tied to this ticket.
     */
    @Lob
    @Column(name = "DESCENDANT_TICKETS", nullable = false, length = Integer.MAX_VALUE)
    private HashSet<String> descendantTickets = new HashSet<>();

    /**
     * Constructs a new TicketGrantingTicket.
     * May throw an {@link IllegalArgumentException} if the Authentication object is null.
     *
     * @param id                         the id of the Ticket
     * @param proxiedBy                  Service that produced this proxy ticket.
     * @param parentTicketGrantingTicket the parent ticket
     * @param authentication             the Authentication request for this ticket
     * @param policy                     the expiration policy for this ticket.
     */
    @JsonCreator
    public TicketGrantingTicketImpl(@JsonProperty("id") final String id, @JsonProperty("proxiedBy") final Service proxiedBy,
                                    @JsonProperty("ticketGrantingTicket") final TicketGrantingTicket parentTicketGrantingTicket,
                                    @NonNull @JsonProperty("authentication") final Authentication authentication, @JsonProperty("expirationPolicy") final ExpirationPolicy policy) {
        if (parentTicketGrantingTicket != null && proxiedBy == null) {
            throw new IllegalArgumentException("Must specify proxiedBy when providing parent TGT");
        }
        this.ticketGrantingTicket = parentTicketGrantingTicket;
        this.authentication = authentication;
        this.proxiedBy = proxiedBy;
    }

    /**
     * Constructs a new TicketGrantingTicket without a parent
     * TicketGrantingTicket.
     *
     * @param id             the id of the Ticket
     * @param authentication the Authentication request for this ticket
     * @param policy         the expiration policy for this ticket.
     */
    public TicketGrantingTicketImpl(final String id, final Authentication authentication, final ExpirationPolicy policy) {
        this(id, null, null, authentication, policy);
    }
    
    @Override
    public synchronized ServiceTicket grantServiceTicket(final String id, final Service service, AppsCasDetails casDetails,final ExpirationPolicy expirationPolicy,
                                                         final boolean credentialProvided, final boolean onlyTrackMostRecentSession) {
        final ServiceTicket serviceTicket = new ServiceTicketImpl(authentication,casDetails);
        return serviceTicket;
    }

    /**
     * Normalize the path of a service by removing the query string and everything after a semi-colon.
     *
     * @param service the service to normalize
     * @return the normalized path
     
    private static String normalizePath(final Service service) {
        String path = service.getId();
        path = StringUtils.substringBefore(path, "?");
        path = StringUtils.substringBefore(path, ";");
        path = StringUtils.substringBefore(path, "#");
        return path;
    }
*/
    /**
     * Remove all services of the TGT (at logout).
     */
    @Override
    public void removeAllServices() {
        this.services.clear();
    }


    public String getPrefix() {
        return TicketGrantingTicket.PREFIX;
    }

    @Override
    public Map<String, Service> getServices() {
        return null;
    }

    @Override
    public Map<String, Service> getProxyGrantingTickets() {
        return null;
    }

    @Override
    public Service getProxiedBy() {
        return null;
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    @Override
    public TicketGrantingTicket getRoot() {
        return null;
    }

    @Override
    public List<Authentication> getChainedAuthentications() {
        return null;
    }


}
