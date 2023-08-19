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


package org.dromara.maxkey.web.contorller;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsEntryType;
import org.dromara.maxkey.constants.ConstsOperateAction;
import org.dromara.maxkey.constants.ConstsOperateResult;
import org.dromara.maxkey.constants.ConstsPasswordSetType;
import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.PasswordPolicy;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.repository.PasswordPolicyValidator;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.PasswordPolicyService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/config"})
public class ChangePasswodController {
	static final Logger logger = LoggerFactory.getLogger(ChangePasswodController.class);

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	HistorySystemLogsService systemLog;

	@Autowired
	private PasswordPolicyService passwordPolicyService;

	@RequestMapping(value={"/passwordpolicy"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> passwordpolicy(@CurrentUser UserInfo currentUser){
		PasswordPolicy passwordPolicy = passwordPolicyService.get(currentUser.getInstId());
		//构建密码强度说明
		passwordPolicy.buildMessage();
		return new Message<PasswordPolicy>(passwordPolicy).buildResponse();
	}


	@ResponseBody
	@RequestMapping(value = { "/changePassword" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> changePasswod(
			@RequestBody ChangePassword changePassword,
			@CurrentUser UserInfo currentUser) {

		changePassword.setUserId(currentUser.getId());
		changePassword.setUsername(currentUser.getUsername());
		changePassword.setInstId(currentUser.getInstId());
		changePassword.setPasswordSetType(ConstsPasswordSetType.PASSWORD_NORMAL);
		if(userInfoService.changePassword(changePassword)) {
			systemLog.insert(
					ConstsEntryType.USERINFO,
					changePassword,
					ConstsOperateAction.CHANGE_PASSWORD,
					ConstsOperateResult.SUCCESS,
					currentUser);
			return new Message<ChangePassword>().buildResponse();
		}else {
			String message = (String) WebContext.getAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT);
			logger.info("-message:",message);
			return new Message<ChangePassword>(Message.ERROR,message).buildResponse();
		}
	}

}
