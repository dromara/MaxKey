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
import org.maxkey.entity.EmailSenders;
import org.maxkey.persistence.service.EmailSendersService;
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
@RequestMapping(value={"/emailsenders"})
public class EmailSendersController {


		final static Logger _logger = LoggerFactory.getLogger(EmailSendersController.class);
		
		@Autowired
		private EmailSendersService emailSendersService;
		
		/**
		 * 读取
		 * @return
		 */
		@RequestMapping(value={"/forward"})
		public ModelAndView forward(){
			EmailSenders emailSenders = emailSendersService.get(WebContext.getUserInfo().getInstId());
			if(emailSenders != null && StringUtils.isNotBlank(emailSenders.getCredentials())) {
				emailSenders.setCredentials(PasswordReciprocal.getInstance().decoder(emailSenders.getCredentials()));
			}else {
				emailSenders =new EmailSenders();
				emailSenders.setProtocol("smtp");
				emailSenders.setEncoding("utf-8");
			}
			return new ModelAndView("emailsenders/updateEmailSenders","model",emailSenders);	
		}
		
		/**
		 * 更新
		 * @param emailSenders
		 * @return
		 */
		@RequestMapping(value={"/update"})
		@ResponseBody
		public Message update(@ModelAttribute("emailSenders") EmailSenders emailSenders,BindingResult result) {
			_logger.debug("update emailSenders : "+emailSenders);
			emailSenders.setInstId(WebContext.getUserInfo().getInstId());
			emailSenders.setCredentials(PasswordReciprocal.getInstance().encode(emailSenders.getCredentials()));
			boolean updateResult = false;
			if(StringUtils.isBlank(emailSenders.getId())) {
				emailSenders.setId(emailSenders.getInstId());
				updateResult = emailSendersService.insert(emailSenders);
			}else {
				updateResult = emailSendersService.update(emailSenders);
			}
			
			if(updateResult) {
				return new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			} else {
				return new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
			}
		}
		

}
