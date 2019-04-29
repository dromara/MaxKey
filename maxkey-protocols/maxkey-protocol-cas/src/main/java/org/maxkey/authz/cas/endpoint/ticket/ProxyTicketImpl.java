package org.maxkey.authz.cas.endpoint.ticket;

/**
 * The {@link ProxyTicketImpl} is a concrete implementation of the {@link ProxyTicket}.
 *
 * @author Misagh Moayyed
 * @since 4.2
 */
public class ProxyTicketImpl extends ServiceTicketImpl implements Ticket {
    private static final long serialVersionUID = -4469960563289285371L;

    /**
     * Instantiates a new Proxy ticket.
     */
    public ProxyTicketImpl() {
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
    public ProxyTicketImpl(final String id,  final Service service,
                           final boolean credentialProvided) {

    }
}
