package org.maxkey.authn.online;

import java.io.Serializable;
import java.util.HashMap;

import org.maxkey.domain.apps.Apps;
import org.springframework.security.core.Authentication;

public class OnlineTicket implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 9008067569150338296L;

    public String ticketId;
    
    public Authentication authentication;
    
    private HashMap<String , Apps> authorizedApps;
    

    public OnlineTicket(String ticketId) {
        super();
        this.ticketId = ticketId;
    }
    
    public OnlineTicket(String ticketId,Authentication authentication) {
        super();
        this.ticketId = ticketId;
        this.authentication = authentication;
    }
    
    

    public String getTicketId() {
        return ticketId;
    }



    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    
    public Authentication getAuthentication() {
        return authentication;
    }



    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

  

    public HashMap<String, Apps> getAuthorizedApps() {
        return authorizedApps;
    }



    public void setAuthorizedApps(HashMap<String, Apps> authorizedApps) {
        this.authorizedApps = authorizedApps;
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OnlineTicket [ticketId=");
        builder.append(ticketId);
        builder.append("]");
        return builder.toString();
    }
    
    
}
