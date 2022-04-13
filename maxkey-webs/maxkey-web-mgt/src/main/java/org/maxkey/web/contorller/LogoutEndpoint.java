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

import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.authn.online.OnlineTicketService;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutEndpoint {
	
	@Autowired
    protected OnlineTicketService onlineTicketService;
	
 	@RequestMapping(value={"/logout"}, produces = {MediaType.APPLICATION_JSON_VALUE})
 	public  ResponseEntity<?> logout(@CurrentUser UserInfo currentUser){
 		onlineTicketService.terminate(
 				currentUser.getOnlineTicket(), 
 				currentUser.getId(),
 				currentUser.getUsername());
 		return new Message<String>().buildResponse();
 	}
 	
}
