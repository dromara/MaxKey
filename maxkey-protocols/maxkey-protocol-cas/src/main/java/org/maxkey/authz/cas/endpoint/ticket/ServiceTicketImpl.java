package org.maxkey.authz.cas.endpoint.ticket;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.maxkey.domain.apps.AppsCasDetails;
import org.springframework.security.core.Authentication;

import javax.persistence.Column;

/**
 * Domain object representing a Service Ticket. A service ticket grants specific
 * access to a particular service. It will only work for a particular service.
 * Generally, it is a one time use Ticket, but the specific expiration policy
 * can be anything.
 *
 * @author Scott Battaglia
 * @since 3.0.0
 */

public class ServiceTicketImpl extends AbstractTicket {
    
    private static final long serialVersionUID = -4223319704861765405L;


    /**
     * The service this ticket is valid for.
     */

    @Column(name = "SERVICE", nullable = false, length = Integer.MAX_VALUE)
    private Service service;

    /**
     * Is this service ticket the result of a new login.
     */
    @Column(name = "FROM_NEW_LOGIN", nullable = false)
    private boolean fromNewLogin;

    @Column(name = "TICKET_ALREADY_GRANTED", nullable = false)
    private Boolean grantedTicketAlready = Boolean.FALSE;

    /**
     * Instantiates a new service ticket impl.
     */
    public ServiceTicketImpl() {
        // exists for JPA purposes
    }

    /**
     * Instantiates a new service ticket impl.
     */
    public ServiceTicketImpl(Authentication authentication) {
        // exists for JPA purposes
    	this.authentication=authentication;
    }
    
    /**
     * Instantiates a new service ticket impl.
     */
    public ServiceTicketImpl(Authentication authentication,AppsCasDetails casDetails) {
        // exists for JPA purposes
    	this.authentication=authentication;
    	this.casDetails=casDetails;
    }

    /**
     * {@inheritDoc}
     * <p>The state of the ticket is affected by this operation and the
     * ticket will be considered used regardless of the match result.
     * The state update subsequently may impact the ticket expiration
     * policy in that, depending on the policy configuration, the ticket
     * may be considered expired.
     */

    public boolean isValidFor(final Service serviceToValidate) {
        update();
        return serviceToValidate.matches(this.service);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
       

        final Ticket ticket = (Ticket) object;

        return new EqualsBuilder()
                .append(ticket.getId(), this.getId())
                .isEquals();
    }

}
