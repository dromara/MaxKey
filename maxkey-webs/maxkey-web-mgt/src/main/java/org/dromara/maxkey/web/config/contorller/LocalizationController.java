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
import org.dromara.maxkey.entity.Localization;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.entity.UserInfoAdjoint;
import org.dromara.maxkey.persistence.repository.LocalizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"/localization"})
public class LocalizationController {


		static final  Logger logger = LoggerFactory.getLogger(LocalizationController.class);
		
		@Autowired
		private LocalizationRepository localizationRepository;
		
		/**
		 * 读取
		 * @return
		 */
		@RequestMapping(value={"/forward/{property}"})
		public ModelAndView forward(@PathVariable("property") String property,@CurrentUser UserInfo currentUser){
			Localization localization = localizationRepository.get(property,currentUser.getInstId());
			if(localization == null )localization = new Localization();
			localization.setProperty(property);
			localization.setInstId(currentUser.getInstId());
			return new ModelAndView("localization/updateLocalization","model",localization);
		}
		
		/**
		 * 更新
		 * @param sysConfig
		 * @return
		 */
		@RequestMapping(value={"/update"})
		@ResponseBody
		public ResponseEntity<?> update(@ModelAttribute("localization") Localization localization,@CurrentUser UserInfo currentUser,BindingResult result) {
			logger.debug("update  localization : {}" ,localization);
			localization.setInstId(currentUser.getInstId());
			if(StringUtils.isBlank(localization.getId())){
				localization.setId(localization.generateId());
				if(localizationRepository.insert(localization)) {
					return new Message<UserInfoAdjoint>(Message.SUCCESS).buildResponse();
				} else {
					return new Message<UserInfoAdjoint>(Message.FAIL).buildResponse();
				}
			}else {
				if(localizationRepository.update(localization)) {
					return new Message<UserInfoAdjoint>(Message.SUCCESS).buildResponse();
				} else {
					return new Message<UserInfoAdjoint>(Message.FAIL).buildResponse();
				}
			}
		}
		

}
