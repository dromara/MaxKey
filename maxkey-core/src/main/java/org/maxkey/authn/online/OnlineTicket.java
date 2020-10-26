package org.maxkey.authn.online;

import java.io.Serializable;

import org.maxkey.domain.apps.Apps;

public class OnlineTicket implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 9008067569150338296L;

    public String id;
    
    private Apps authorizeApps;
    

    public OnlineTicket(String id) {
        super();
        this.id = id;
    }
    
    

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OnlineTicket [id=");
        builder.append(id);
        builder.append("]");
        return builder.toString();
    }
    
    
}
