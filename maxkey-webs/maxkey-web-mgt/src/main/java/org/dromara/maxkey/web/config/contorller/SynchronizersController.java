/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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

import java.util.List;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.service.SynchronizersService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.util.StringUtils;
import org.dromara.maxkey.web.WebContext;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/config/synchronizers"})
public class SynchronizersController {
	static final Logger logger = LoggerFactory.getLogger(SynchronizersController.class);
	
	@Autowired
	SynchronizersService synchronizersService;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(Synchronizers synchronizers,@CurrentUser UserInfo currentUser) {
		logger.debug("fetch {}" , synchronizers);
		synchronizers.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Synchronizers>>(
				synchronizersService.fetchPageResults(synchronizers)).buildResponse();
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		Synchronizers synchronizers=synchronizersService.get(id);
		synchronizers.setCredentials(PasswordReciprocal.getInstance().decoder(synchronizers.getCredentials()));
		return new Message<Synchronizers>(synchronizers).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(@RequestBody  Synchronizers synchronizers,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , synchronizers);
		synchronizers.setInstId(currentUser.getInstId());
		synchronizers.setCredentials(PasswordReciprocal.getInstance().encode(synchronizers.getCredentials()));
		if (synchronizersService.update(synchronizers)) {
		    return new Message<Synchronizers>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Synchronizers>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/synchr"})  
	public ResponseEntity<?> synchr(@RequestParam("id") String id) {
		logger.debug("-sync ids : {}" , id);
		
		List<String> ids = StringUtils.string2List(id, ",");
		try {
			for(String sysId : ids) {
				Synchronizers  synchronizer  = synchronizersService.get(sysId);
				synchronizer.setCredentials(PasswordReciprocal.getInstance().decoder(synchronizer.getCredentials()));
				logger.debug("synchronizer {}" , synchronizer);
				ISynchronizerService synchronizerService = WebContext.getBean(synchronizer.getService(),ISynchronizerService.class);
				if(synchronizerService != null) {
					synchronizerService.setSynchronizer(synchronizer);
					synchronizerService.sync();
				}else {
					logger.info("synchronizer {} not exist .",synchronizer.getService());
				}
			}
		}catch(Exception e) {
			logger.error("synchronizer Exception " , e);
			return new Message<Synchronizers>(Message.FAIL).buildResponse();
			
		}
		return new Message<Synchronizers>(Message.SUCCESS).buildResponse();
	}

}
