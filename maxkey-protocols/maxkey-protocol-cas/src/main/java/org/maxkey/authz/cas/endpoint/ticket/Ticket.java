package org.maxkey.authz.cas.endpoint.ticket;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.maxkey.domain.apps.AppsCasDetails;
import org.springframework.security.core.Authentication;

/**
 * Interface for the generic concept of a ticket.
 *
 * @author Scott Battaglia
 * @since 3.0.0
 */
public interface Ticket extends Serializable, Comparable<Ticket> {

    /**
     * Method to retrieve the id.
     *
     * @return the id
     */
    String getId();

    /**
     * Determines if the ticket is expired. Most common implementations might
     * collaborate with <i>ExpirationPolicy </i> strategy.
     *
     * @return true, if the ticket is expired
     * @see ExpirationPolicy
     */
    boolean isExpired();
    
    /**
     * Authentication information from the ticket. This may be null.
     *
     * @return the authentication information.
     */
    Authentication getAuthentication();

    /**
     * Records the <i>previous</i> last time this ticket was used as well as
     * the last usage time. The ticket usage count is also incremented.
     * <p>Tickets themselves are solely responsible to maintain their state. The
     * determination of  ticket usage is left up to the implementation and
     * the specific ticket type.
     *
     * @see ExpirationPolicy
     * @since 5.0.0
     */
    void update();

    /**
     * Method to return the time the Ticket was created.
     *
     * @return the time the ticket was created.
     */
    ZonedDateTime getCreationTime();

    /**
     * Gets count of uses.
     *
     * @return the number of times this ticket was used.
     */
    int getCountOfUses();
    
    /**
     * Returns the last time the ticket was used.
     *
     * @return the last time the ticket was used.
     */
    ZonedDateTime getLastTimeUsed();

    /**
     * Get the second to last time used.
     *
     * @return the previous time used.
     */

    ZonedDateTime getPreviousTimeUsed();
    
    AppsCasDetails getCasDetails(); 

    
}
