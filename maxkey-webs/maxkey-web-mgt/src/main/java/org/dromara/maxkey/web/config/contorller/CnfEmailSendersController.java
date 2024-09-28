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
import org.dromara.maxkey.entity.cnf.CnfEmailSenders;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.CnfEmailSendersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value={"/config/emailsenders"})
public class CnfEmailSendersController {
	static final  Logger logger = LoggerFactory.getLogger(CnfEmailSendersController.class);
	
	@Autowired
	CnfEmailSendersService emailSendersService;

	@GetMapping({"/get"})
	public Message<CnfEmailSenders> get(@CurrentUser UserInfo currentUser){
		CnfEmailSenders emailSenders = emailSendersService.get(currentUser.getInstId());
		if(emailSenders != null && StringUtils.isNotBlank(emailSenders.getCredentials())) {
			emailSenders.setCredentials(PasswordReciprocal.getInstance().decoder(emailSenders.getCredentials()));
		}else {
			emailSenders =new CnfEmailSenders();
			emailSenders.setProtocol("smtp");
			emailSenders.setEncoding("utf-8");
		}
		return new Message<>(emailSenders);	
	}

	@PutMapping({"/update"})
	public Message<CnfEmailSenders> update( @RequestBody  CnfEmailSenders emailSenders,@CurrentUser UserInfo currentUser,BindingResult result) {
		logger.debug("update emailSenders : {}" , emailSenders);
		emailSenders.setInstId(currentUser.getInstId());
		emailSenders.setCredentials(PasswordReciprocal.getInstance().encode(emailSenders.getCredentials()));
		if(StringUtils.isBlank(emailSenders.getId())) {
			emailSenders.setId(emailSenders.getInstId());
			if(emailSendersService.insert(emailSenders)) {
				return new Message<>(Message.SUCCESS);
			}else {
				return new Message<>(Message.ERROR);
			}
		}else {
			if(emailSendersService.update(emailSenders)) {
				return new Message<>(Message.SUCCESS);
			}else {
				return new Message<>(Message.ERROR);
			}
		}
		
	}
}
