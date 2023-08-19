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
 

package org.dromara.maxkey.web.config.contorller;


import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.PasswordPolicy;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.service.PasswordPolicyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value={"/config/passwordpolicy"})
public class PasswordPolicyController {
	static final  Logger logger = LoggerFactory.getLogger(PasswordPolicyController.class);
		
	@Autowired
	private PasswordPolicyService passwordPolicyService;
	
	@RequestMapping(value={"/get"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@CurrentUser UserInfo currentUser){
		PasswordPolicy passwordPolicy = passwordPolicyService.get(currentUser.getInstId());
		return new Message<PasswordPolicy>(passwordPolicy).buildResponse();
	}

	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(@Valid @RequestBody PasswordPolicy passwordPolicy,@CurrentUser UserInfo currentUser,BindingResult result) {
		logger.debug("updateRole passwordPolicy : {}" ,passwordPolicy);
		//Message message = this.validate(result, passwordPolicy);
		
		if(passwordPolicyService.update(passwordPolicy)) {
			return new Message<PasswordPolicy>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<PasswordPolicy>(Message.ERROR).buildResponse();
		}
	}
	
	public Message validate(BindingResult result,PasswordPolicy passwordPolicy) {
		if (result.hasErrors()) {
			return new Message(result);
		}
		if(passwordPolicy.getMinLength() < 3) {
			FieldError fe = new FieldError("passwordPolicy", "minLength",
					passwordPolicy.getMinLength(), true,
					new String[]{"ui.passwordpolicy.xe00000001"},//密码最小长度不能小于3位字符
					null, null);
			result.addError(fe);
			return new Message(result);
		}
		if(passwordPolicy.getMinLength() > passwordPolicy.getMaxLength()) {
			FieldError fe = new FieldError("passwordPolicy", "maxLength",
					passwordPolicy.getMinLength(), true,
					new String[]{"ui.passwordpolicy.xe00000002"},//密码最大长度不能小于最小长度
					null, null);
			result.addError(fe);
			return new Message(result);
		}
		
		if(passwordPolicy.getDigits() + passwordPolicy.getLowerCase() + passwordPolicy.getUpperCase() + passwordPolicy.getSpecialChar() < 2) {
			FieldError fe = new FieldError("passwordPolicy", "specialChar",
					2, true,
					new String[]{"ui.passwordpolicy.xe00000003"},//密码包含小写字母、大写字母、数字、特殊字符的个数不能小于2
					null, null);
			result.addError(fe);
			return new Message(result);
		}
		
		if(passwordPolicy.getDigits() + passwordPolicy.getLowerCase() + passwordPolicy.getUpperCase() + passwordPolicy.getSpecialChar() > passwordPolicy.getMaxLength()) {
			FieldError fe = new FieldError("passwordPolicy", "specialChar",
					passwordPolicy.getMinLength(), true,
					new String[]{"ui.passwordpolicy.xe00000004"},//密码包含小写字母、大写字母、数字、特殊字符的个数不能大于密码的最大长度
					null, null);
			result.addError(fe);
			return new Message(result);
		}
		return null;
	}
}
