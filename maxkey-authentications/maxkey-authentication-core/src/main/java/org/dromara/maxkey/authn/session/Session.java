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
 

package org.dromara.maxkey.authn.session;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.dromara.maxkey.web.WebContext;
import org.springframework.security.core.Authentication;

public class Session implements Serializable{
	private static final long   serialVersionUID = 9008067569150338296L;
    
    public static final  int    MAX_EXPIRY_DURATION = 60 * 5; //default 5 minutes.

    public String id;
    
    public LocalDateTime startTimestamp;
    
    public LocalDateTime lastAccessTime;
    
    public LocalDateTime expiredTime;
    
    public Authentication authentication;
    
    Map<String , VisitedDto> visited = new HashMap<>();
    
    public Session() {
        super();
        this.id = WebContext.genId();;
        this.startTimestamp = LocalDateTime.now();
        this.lastAccessTime = LocalDateTime.now();
    }

    public Session(String sessionId) {
        super();
        this.id = sessionId;
        this.startTimestamp = LocalDateTime.now();
        this.lastAccessTime = LocalDateTime.now();
    }
    
    public Session(String sessionId,Authentication authentication) {
        super();
        this.id = sessionId;
        this.authentication = authentication;
        this.startTimestamp = LocalDateTime.now();
        this.lastAccessTime = LocalDateTime.now();
    }
    
    public String getId() {
		return id;
	}

    public void setId(String sessionId) {
        this.id = sessionId;
    }
    

    public LocalDateTime getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(LocalDateTime startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public LocalDateTime getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(LocalDateTime lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public LocalDateTime getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(LocalDateTime expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public void visited(VisitedDto visited) {
        this.visited.put(visited.getAppId(), visited);
    }

    public Map<String, VisitedDto> getVisited() {
		return visited;
	}

	public void setVisited(Map<String, VisitedDto> visited) {
		this.visited = visited;
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
