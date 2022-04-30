/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

import java.time.LocalTime;
import java.util.List;

import org.maxkey.entity.HistoryLogin;

public interface SessionManager {

	public  void create(String sessionId, Session session);

    public  Session remove(String sessionId);
    
    public  Session get(String sessionId);
    
    public Session refresh(String sessionId ,LocalTime refreshTime);
    
    public Session refresh(String sessionId);

    public void setValiditySeconds(int validitySeconds);
    
    public List<HistoryLogin> querySessions();
    
    public void terminate(String sessionId,String userId,String username);
}
