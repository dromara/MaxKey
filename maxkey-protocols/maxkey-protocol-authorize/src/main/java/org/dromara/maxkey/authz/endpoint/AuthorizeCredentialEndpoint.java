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
 

/**
 * 
 */
package org.dromara.maxkey.authz.endpoint;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Crystal.Sea
 *
 */
@Controller
@RequestMapping(value = { "/authz/credential" })
public class AuthorizeCredentialEndpoint extends AuthorizeBaseEndpoint{

	@RequestMapping("/get/{appId}")
	@ResponseBody
	public ResponseEntity<?>  get(
			@PathVariable("appId") String appId,
			@CurrentUser UserInfo currentUser){
		Apps app = getApp(appId);
		Accounts account = getAccounts(app,currentUser);
		if(account == null) {
			account =new Accounts ();
			account.setId(account.generateId());
			
			account.setUserId(currentUser.getId());
			account.setUsername(currentUser.getUsername());
			account.setDisplayName(currentUser.getDisplayName());
			
			account.setAppId(appId);
			account.setAppName(app.getAppName());
			account.setInstId(currentUser.getInstId());
			account.setCreateType("manual");
			account.setStatus(ConstsStatus.ACTIVE);
		}
		return new Message<Accounts>(account).buildResponse();
	}
	
	@RequestMapping("/update")
	public ResponseEntity<?>  update(
			@RequestBody  Accounts account,
			@CurrentUser UserInfo currentUser){
		if(StringUtils.isNotEmpty(account.getRelatedPassword())
				&&StringUtils.isNotEmpty(account.getRelatedPassword())){
			account.setInstId(currentUser.getInstId());
			account.setRelatedPassword(
					PasswordReciprocal.getInstance().encode(account.getRelatedPassword()));
			if(accountsService.get(account.getId()) == null) {
				if(accountsService.insert(account)){
					return new Message<Accounts>().buildResponse();
				}
			}else {
				if(accountsService.update(account)){
					return new Message<Accounts>().buildResponse();
				}
			}
		}
		
		return new Message<Accounts>(Message.FAIL).buildResponse();
	}
			
}
