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
 

package org.maxkey.web.contorller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.constants.ConstsPasswordSetType;
import org.maxkey.constants.ConstsTimeInterval;
import org.maxkey.entity.ChangePassword;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.repository.PasswordPolicyValidator;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"/config"})
public class ChangePasswodController {
	final static Logger _logger = LoggerFactory.getLogger(ChangePasswodController.class);
	
	@Autowired
	private UserInfoService userInfoService;
	
	@ResponseBody
	@RequestMapping(value="/changePassword") 
	public Message changePasswod(
			@RequestBody ChangePassword changePassword,
			@CurrentUser UserInfo currentUser) {
		
			changePassword.setUserId(currentUser.getId());
			changePassword.setUsername(currentUser.getUsername());
			changePassword.setInstId(currentUser.getInstId());
			changePassword.setPasswordSetType(ConstsPasswordSetType.PASSWORD_NORMAL);
			if(userInfoService.changePassword(changePassword)) {
				return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			}else {
				return  new Message(
				        WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR)+"<br>"
				        +WebContext.getAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT),
				        MessageType.error);
			}	
	}

}
