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
import org.dromara.maxkey.entity.cnf.CnfSmsProvider;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.CnfSmsProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value={"/config/smsprovider"})
public class CnfSmsProviderController {
	static final  Logger logger = LoggerFactory.getLogger(CnfSmsProviderController.class);
	
	@Autowired
	CnfSmsProviderService smsProviderService;

	@GetMapping(value={"/get"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<CnfSmsProvider> get(@CurrentUser UserInfo currentUser){
		CnfSmsProvider smsProvider = smsProviderService.get(currentUser.getInstId());
		if(smsProvider != null && StringUtils.isNoneBlank(smsProvider.getId())) {
			smsProvider.setAppSecret(PasswordReciprocal.getInstance().decoder(smsProvider.getAppSecret()));
		}
		return new Message<>(smsProvider);
	}

	@PutMapping({"/update"})
	public Message<CnfSmsProvider> update( @RequestBody CnfSmsProvider smsProvider,@CurrentUser UserInfo currentUser,BindingResult result) {
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
			return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}
}
