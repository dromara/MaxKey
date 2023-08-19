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
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Connectors;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.service.ConnectorsService;
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
@RequestMapping(value={"/config/connectors"})
public class ConnectorsController {
	static final  Logger logger = LoggerFactory.getLogger(ConnectorsController.class);
	
	@Autowired
	ConnectorsService connectorsService;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(Connectors connector,@CurrentUser UserInfo currentUser) {
		logger.debug("fetch {}" , connector);
		connector.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Connectors>>(
				connectorsService.fetchPageResults(connector)).buildResponse();
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		Connectors connector = connectorsService.get(id);
		if(StringUtils.isNotBlank(connector.getCredentials())) {
			connector.setCredentials(PasswordReciprocal.getInstance().decoder(connector.getCredentials()));
		}
		return new Message<Connectors>(connector).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> insert(@RequestBody  Connectors connector,@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}" , connector);
		connector.setInstId(currentUser.getInstId());
		if(StringUtils.isNotBlank(connector.getCredentials())) {
			connector.setCredentials(PasswordReciprocal.getInstance().encode(connector.getCredentials()));
		}
		if (connectorsService.insert(connector)) {
			return new Message<Connectors>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Connectors>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(@RequestBody  Connectors connector,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , connector);
		connector.setInstId(currentUser.getInstId());
		connector.setCredentials(PasswordReciprocal.getInstance().encode(connector.getCredentials()));
		if (connectorsService.update(connector)) {
		    return new Message<Connectors>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Connectors>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		if (connectorsService.deleteBatch(ids)) {
			 return new Message<Connectors>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Connectors>(Message.FAIL).buildResponse();
		}
	}

}
