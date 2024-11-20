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
 

package org.dromara.maxkey.authz.singlelogout;

import java.util.HashMap;
import java.util.UUID;

import org.dromara.maxkey.authn.session.VisitedDto;
import org.dromara.maxkey.util.DateUtils;
import org.springframework.security.core.Authentication;

public class DefaultSingleLogout extends SingleLogout{

    @Override
    public void sendRequest(Authentication authentication,VisitedDto visited) {
        HashMap<String,Object> logoutParameters  = new HashMap<String,Object>();
        logoutParameters.put("id",  UUID.randomUUID().toString());
        logoutParameters.put("principal", authentication.getName());
        logoutParameters.put("request",  "logoutRequest");
        logoutParameters.put("issueInstant", DateUtils.getCurrentDateAsString(DateUtils.FORMAT_DATE_ISO_TIMESTAMP));
        logoutParameters.put("ticket",   visited.getTicket());
        postMessage(visited.getLogoutUrl(),logoutParameters);
        
    }
    
}
