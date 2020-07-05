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

import javax.validation.Valid;

import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.domain.PasswordPolicy;
import org.maxkey.persistence.service.PasswordPolicyService;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"/config/passwordpolicy"})
public class PasswordPolicyController {


		final static Logger _logger = LoggerFactory.getLogger(PasswordPolicyController.class);
		
		@Autowired
		private PasswordPolicyService passwordPolicyService;
		
		/**
		 * 读取
		 * @return
		 */
		@RequestMapping(value={"/forward"})
		public ModelAndView sysConfig(){
			PasswordPolicy passwordPolicy = passwordPolicyService.get("1");
			return new ModelAndView("config/passwordpolicy/passwordpolicy","model",passwordPolicy);
		}
		
		/**
		 * 更新
		 * @param sysConfig
		 * @return
		 */
		@RequestMapping(value={"/update"})
		@ResponseBody
		public Message updatSysConfig(@Valid @ModelAttribute("passwordPolicy") PasswordPolicy passwordPolicy,BindingResult result) {
			_logger.debug("updateRole passwordPolicy : "+passwordPolicy);
			Message message = this.validate(result, passwordPolicy);
			if(message != null) {
				return message;
			}
			if(passwordPolicyService.update(passwordPolicy)) {
				return new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			} else {
				return new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
			}
		}
		
		/**
		 * 验证函数
		 * @param result
		 * @param passwordPolicy
		 * @return
		 */
		private Message validate(BindingResult result,PasswordPolicy passwordPolicy) {
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
			if(passwordPolicy.getDigits() + passwordPolicy.getLowerCase() + passwordPolicy.getUpperCase() + passwordPolicy.getSpecialChar() < passwordPolicy.getMinLength()) {
				FieldError fe = new FieldError("passwordPolicy", "specialChar",
						passwordPolicy.getMinLength(), true,
						new String[]{"ui.passwordpolicy.xe00000003"},//密码包含小写字母、大写字母、数字、特殊字符的个数不能小于密码的最小长度
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
