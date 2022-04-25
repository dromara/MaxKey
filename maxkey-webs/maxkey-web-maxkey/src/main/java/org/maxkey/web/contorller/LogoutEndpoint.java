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
 

package org.maxkey.web.contorller;

import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.authz.singlelogout.SamlSingleLogout;
import org.maxkey.authz.singlelogout.DefaultSingleLogout;
import org.maxkey.authz.singlelogout.LogoutType;
import org.maxkey.authz.singlelogout.SingleLogout;
import org.maxkey.constants.ConstsProtocols;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.maxkey.entity.apps.Apps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "1-3-单点注销接口文档模块")
@Controller
public class LogoutEndpoint {
	private static Logger _logger = LoggerFactory.getLogger(LogoutEndpoint.class);

	@Autowired
    protected OnlineTicketService onlineTicketService;
	
	@Operation(summary = "单点注销接口", description = "reLoginUrl跳转地址",method="GET")
	@RequestMapping(value={"/logout"}, produces = {MediaType.APPLICATION_JSON_VALUE})
 	public  ResponseEntity<?> logout(@CurrentUser UserInfo currentUser){
		//if logined in have onlineTicket ,need remove or logout back
		String onlineTicketId = currentUser.getOnlineTicket();
 		OnlineTicket onlineTicket = onlineTicketService.get(onlineTicketId);
 		if(onlineTicket != null) {
	 		Set<Entry<String, Apps>> entrySet = onlineTicket.getAuthorizedApps().entrySet();
	 
	        Iterator<Entry<String, Apps>> iterator = entrySet.iterator();
	        while (iterator.hasNext()) {
	            Entry<String, Apps> mapEntry = iterator.next();
	            _logger.debug("App Id : "+ mapEntry.getKey()+ " , " +mapEntry.getValue());
	            if( mapEntry.getValue().getLogoutType() == LogoutType.BACK_CHANNEL){
	                SingleLogout singleLogout;
	                if(mapEntry.getValue().getProtocol().equalsIgnoreCase(ConstsProtocols.CAS)) {
	                    singleLogout =new SamlSingleLogout();
	                }else {
	                    singleLogout = new DefaultSingleLogout();
	                }
	                singleLogout.sendRequest(onlineTicket.getAuthentication(), mapEntry.getValue());
	            }
	        }
	        
	        onlineTicketService.terminate(
	        		onlineTicketId, 
	        		currentUser.getId(),
	        		currentUser.getUsername());
 		}
 		return new Message<String>().buildResponse();
 	}
}
