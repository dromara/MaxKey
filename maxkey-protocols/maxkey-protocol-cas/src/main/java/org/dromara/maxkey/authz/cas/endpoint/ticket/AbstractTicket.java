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

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.dromara.maxkey.entity.apps.AppsCasDetails;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Abstract implementation of a ticket that handles all ticket state for
 * policies. Also incorporates properties common among all tickets. As this is
 * an abstract class, it cannnot be instanciated. It is recommended that
 * implementations of the Ticket interface extend the AbstractTicket as it
 * handles common functionality amongst different ticket types (such as state
 * updating).
 *
 * AbstractTicket does not provide a logger instance to
 * avoid instantiating many such Loggers at runtime (there will be many instances
 * of subclasses of AbstractTicket in a typical running CAS server).  Instead
 * subclasses should use static Logger instances.
 *
 * @author Scott Battaglia
 * @since 3.0.0
 */
public abstract class AbstractTicket implements Ticket {

    private static final long serialVersionUID = -8506442397878267555L;



    /** The unique identifier for this ticket. */
    @Id
    @Column(name="ID", nullable=false)
    protected String id;

    /** The last time this ticket was used. */
    @Column(name="LAST_TIME_USED")
    protected ZonedDateTime lastTimeUsed;

    /** The previous last time this ticket was used. */
    @Column(name="PREVIOUS_LAST_TIME_USED")
    protected ZonedDateTime previousLastTimeUsed;

    /** The time the ticket was created. */
    @Column(name="CREATION_TIME")
    protected ZonedDateTime creationTime;

    /** The number of times this was used. */
    @Column(name="NUMBER_OF_TIMES_USED")
    protected int countOfUses;

    protected Authentication authentication;
    
    protected AppsCasDetails casDetails;
    /**
     * Instantiates a new abstract ticket.
     */
    protected AbstractTicket() {
        // nothing to do
    }

    /**
     * Constructs a new Ticket with a unique id, a possible parent Ticket (can
     * be null) and a specified Expiration Policy.
     *
     * @param id the unique identifier for the ticket
     * @param expirationPolicy the expiration policy for the ticket.
     * @throws IllegalArgumentException if the id or expiration policy is null.
     */
    public AbstractTicket(final String id) {
        Assert.notNull(id, "id cannot be null");

        this.id = id;
        this.creationTime = ZonedDateTime.now(ZoneOffset.UTC);
        this.lastTimeUsed = ZonedDateTime.now(ZoneOffset.UTC);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void update() {
        this.previousLastTimeUsed = this.lastTimeUsed;
        this.lastTimeUsed = ZonedDateTime.now(ZoneOffset.UTC);
        this.countOfUses++;
    }

    @Override
    public int getCountOfUses() {
        return this.countOfUses;
    }

    @Override
    public ZonedDateTime getCreationTime() {
        return this.creationTime;
    }

    @Override
    public ZonedDateTime getLastTimeUsed() {
        return this.lastTimeUsed;
    }

    @Override
    public ZonedDateTime getPreviousTimeUsed() {
        return this.previousLastTimeUsed;
    }

    @Override
    public boolean isExpired() {
        return  isExpiredInternal();
    }

    protected boolean isExpiredInternal() {
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 133).append(this.getId()).toHashCode();
    }

    @Override
    public String toString() {
        return this.getId();
    }

	@Override
	public AppsCasDetails getCasDetails() {
		return this.casDetails;
	}
	
	@Override
	public Authentication getAuthentication() {
		return this.authentication;
	}
	
    @Override
    public int compareTo(final Ticket o) {
        return getId().compareTo(o.getId());
    }
}
