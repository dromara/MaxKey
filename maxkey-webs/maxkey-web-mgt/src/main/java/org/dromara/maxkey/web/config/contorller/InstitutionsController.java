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
import org.dromara.maxkey.entity.Institutions;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.InstitutionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/config/institutions"})
public class InstitutionsController {
		static final  Logger logger = LoggerFactory.getLogger(InstitutionsController.class);
		
		@Autowired
		InstitutionsService institutionsService;
		
		@RequestMapping(value={"/get"}, produces = {MediaType.APPLICATION_JSON_VALUE})
		public Message<?> get(@CurrentUser UserInfo currentUser){
			Institutions institutions = institutionsService.get(currentUser.getInstId());
			return new Message<Institutions>(Message.SUCCESS,institutions);
		}
		
		@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
		public Message<?> update(
				@RequestBody  Institutions institutions,
				@CurrentUser UserInfo currentUser,
				BindingResult result) {
			logger.debug("updateRole institutions : {}" , institutions);
			if(institutionsService.update(institutions)) {
				return new Message<Institutions>(Message.SUCCESS);
			} else {
				return new Message<Institutions>(Message.FAIL);
			}
		}
}
