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
 

package org.maxkey.authn.session;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;

import org.maxkey.entity.apps.Apps;
import org.maxkey.web.WebContext;
import org.springframework.security.core.Authentication;

public class Session implements Serializable{
	private static final long   serialVersionUID = 9008067569150338296L;
	
	public static final  String SESSION_PREFIX = "OT";
    
    public static final  int    MAX_EXPIRY_DURATION = 60 * 10; //default 10 minutes.
    
    public String id;
    
    public LocalTime startTimestamp;
    
    public LocalTime lastAccessTime;
    
    public Authentication authentication;
    
    private HashMap<String , Apps> authorizedApps = new HashMap<String , Apps>();
    
    public Session() {
        super();
        this.id = WebContext.genId();;
        this.startTimestamp = LocalTime.now();
        this.lastAccessTime = LocalTime.now();
    }

    public Session(String sessionId) {
        super();
        this.id = sessionId;
        this.startTimestamp = LocalTime.now();
        this.lastAccessTime = LocalTime.now();
    }
    
    public Session(String sessionId,Authentication authentication) {
        super();
        this.id = sessionId;
        this.authentication = authentication;
        this.startTimestamp = LocalTime.now();
        this.lastAccessTime = LocalTime.now();
    }
    
    public String getId() {
		return id;
	}

	public String getFormattedId() {
        return SESSION_PREFIX + id;
    }

    public void setId(String sessionId) {
        this.id = sessionId;
    }
    

    public LocalTime getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(LocalTime startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public LocalTime getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(LocalTime lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
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
		builder.append("Session [id=");
		builder.append(id);
		builder.append(", startTimestamp=");
		builder.append(startTimestamp);
		builder.append(", lastAccessTime=");
		builder.append(lastAccessTime);
		builder.append("]");
		return builder.toString();
	}
    
    
}
