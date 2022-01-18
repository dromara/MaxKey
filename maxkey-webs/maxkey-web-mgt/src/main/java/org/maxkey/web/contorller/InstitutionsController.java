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

import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.entity.Institutions;
import org.maxkey.persistence.service.InstitutionsService;
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
@RequestMapping(value={"/institutions"})
public class InstitutionsController {


		final static Logger _logger = LoggerFactory.getLogger(InstitutionsController.class);
		
		@Autowired
		private InstitutionsService institutionsService;
		
		/**
		 * 读取
		 * @return
		 */
		@RequestMapping(value={"/forward"})
		public ModelAndView forward(){
			Institutions institutions = institutionsService.get(WebContext.getUserInfo().getInstId());
			return new ModelAndView("institutions/updateInstitutions","model",institutions);
		}
		
		/**
		 * 更新
		 * @param sysConfig
		 * @return
		 */
		@RequestMapping(value={"/update"})
		@ResponseBody
		public Message updat(@ModelAttribute("institutions") Institutions institutions,BindingResult result) {
			_logger.debug("updateRole institutions : "+institutions);
			if(institutionsService.update(institutions)) {
				return new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			} else {
				return new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
			}
		}
		

}
