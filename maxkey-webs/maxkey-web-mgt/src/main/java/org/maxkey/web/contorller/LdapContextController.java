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

import org.apache.commons.lang3.StringUtils;
import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.LdapContext;
import org.maxkey.persistence.service.LdapContextService;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"/ldapcontext"})
public class LdapContextController {


		final static Logger _logger = LoggerFactory.getLogger(LdapContextController.class);
		
		@Autowired
		private LdapContextService ldapContextService;
		
		/**
		 * 读取
		 * @return
		 */
		@RequestMapping(value={"/forward"})
		public ModelAndView forward(){
			LdapContext ldapContext = ldapContextService.get(WebContext.getUserInfo().getInstId());
			if(ldapContext != null && StringUtils.isNoneBlank(ldapContext.getCredentials())) {
				ldapContext.setCredentials(PasswordReciprocal.getInstance().decoder(ldapContext.getCredentials()));
			}
			return new ModelAndView("ldapcontext/updateLdapContext","model",ldapContext);
		}
		
		/**
		 * 更新
		 * @param sysConfig
		 * @return
		 */
		@RequestMapping(value={"/update"})
		@ResponseBody
		public Message update(@ModelAttribute("ldapContext") LdapContext ldapContext,BindingResult result) {
			_logger.debug("update ldapContext : "+ldapContext);
			ldapContext.setCredentials(PasswordReciprocal.getInstance().encode(ldapContext.getCredentials()));
			ldapContext.setInstId(WebContext.getUserInfo().getInstId());
			boolean updateResult = false;
			if(StringUtils.isBlank(ldapContext.getId())) {
				ldapContext.setId(ldapContext.getInstId());
				updateResult = ldapContextService.insert(ldapContext);
			}else {
				updateResult = ldapContextService.update(ldapContext);
			}
			if(updateResult) {
				return new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			} else {
				return new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
			}
		}
		

}
