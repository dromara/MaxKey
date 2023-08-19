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
 

package org.dromara.maxkey.web.config.contorller;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.SmsProvider;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.service.SmsProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/config/smsprovider"})
public class SmsProviderController {
	static final  Logger logger = LoggerFactory.getLogger(SmsProviderController.class);
	
	@Autowired
	private SmsProviderService smsProviderService;

	@RequestMapping(value={"/get"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@CurrentUser UserInfo currentUser){
		SmsProvider smsProvider = smsProviderService.get(currentUser.getInstId());
		if(smsProvider != null && StringUtils.isNoneBlank(smsProvider.getId())) {
			smsProvider.setAppSecret(PasswordReciprocal.getInstance().decoder(smsProvider.getAppSecret()));
		}
		return new Message<SmsProvider>(smsProvider).buildResponse();
	}

	@RequestMapping(value={"/update"})
	@ResponseBody
	public ResponseEntity<?> update( @RequestBody SmsProvider smsProvider,@CurrentUser UserInfo currentUser,BindingResult result) {
		logger.debug("update smsProvider : {}" ,smsProvider);
		smsProvider.setAppSecret(PasswordReciprocal.getInstance().encode(smsProvider.getAppSecret()));
		smsProvider.setInstId(currentUser.getInstId());
		boolean updateResult = false;
		if(StringUtils.isBlank(smsProvider.getId())) {
			smsProvider.setId(smsProvider.getInstId());
			updateResult = smsProviderService.insert(smsProvider);
		}else {
			updateResult = smsProviderService.update(smsProvider);
		}
		if(updateResult) {
			return new Message<SmsProvider>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<SmsProvider>(Message.FAIL).buildResponse();
		}
	}
}
