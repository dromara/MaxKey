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

import java.time.ZonedDateTime;

import org.springframework.security.core.Authentication;

/**
 * @author Scott Battaglia
 * @since 3.0.0
 */
public interface TicketState {

    /**
     * Returns the number of times a ticket was used.
     *
     * @return the number of times the ticket was used.
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

    /**
     * Get the time the ticket was created.
     *
     * @return the creation time of the ticket.
     */
    ZonedDateTime getCreationTime();

    /**
     * Authentication information from the ticket. This may be null.
     *
     * @return the authentication information.
     */
    Authentication getAuthentication();

    /**
     * Method to retrieve the TicketGrantingTicket that granted this ticket.
     *
     * @return the ticket or null if it has no parent
     */
    TicketGrantingTicket getTicketGrantingTicket();
    
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
}
