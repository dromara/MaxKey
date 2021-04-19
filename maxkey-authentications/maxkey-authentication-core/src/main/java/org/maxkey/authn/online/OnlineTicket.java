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
 

package org.maxkey.authn.online;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;

import org.maxkey.entity.apps.Apps;
import org.springframework.security.core.Authentication;

public class OnlineTicket implements Serializable{

    /**
     * 
     */
    
    public static final  int    MAX_EXPIRY_DURATION = 60 * 10; //default 10 minutes.
    
    private static final long   serialVersionUID = 9008067569150338296L;

    public String ticketId;
    
    public LocalTime ticketTime;
    
    public Authentication authentication;
    
    private HashMap<String , Apps> authorizedApps = new HashMap<String , Apps>();
    

    public OnlineTicket(String ticketId) {
        super();
        this.ticketId = ticketId;
        this.ticketTime = LocalTime.now();
    }
    
    public OnlineTicket(String ticketId,Authentication authentication) {
        super();
        this.ticketId = ticketId;
        this.authentication = authentication;
        this.ticketTime = LocalTime.now();
    }
    
    

    public String getTicketId() {
        return ticketId;
    }



    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    
    public LocalTime getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(LocalTime ticketTime) {
        this.ticketTime = ticketTime;
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
    
    public void setAuthorizedApp(Apps authorizedApp) {
        this.authorizedApps.put(authorizedApp.getId(), authorizedApp);
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
