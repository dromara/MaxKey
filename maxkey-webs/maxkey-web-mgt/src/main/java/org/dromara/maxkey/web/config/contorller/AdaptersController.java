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
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.entity.apps.AppsAdapters;
import org.dromara.maxkey.persistence.service.AppsAdaptersService;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/config/adapters"})
public class AdaptersController {
	static final  Logger logger = LoggerFactory.getLogger(AdaptersController.class);
	
	@Autowired
	AppsAdaptersService appsAdaptersService;	
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(@ModelAttribute AppsAdapters appsAdapter) {
		logger.debug("fetch {}",appsAdapter);
		return new Message<JpaPageResults<AppsAdapters>>(
				appsAdaptersService.fetchPageResults(appsAdapter)).buildResponse();
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> query(@ModelAttribute AppsAdapters appsAdapter,@CurrentUser UserInfo currentUser) {
		logger.debug("-query  : {}" , appsAdapter);
		if (appsAdaptersService.query(appsAdapter)!=null) {
			 return new Message<AppsAdapters>(Message.SUCCESS).buildResponse();
		} else {
			 return new Message<AppsAdapters>(Message.SUCCESS).buildResponse();
		}
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		AppsAdapters appsAdapter=appsAdaptersService.get(id);
		return new Message<AppsAdapters>(appsAdapter).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> insert(@RequestBody  AppsAdapters appsAdapter,@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}" , appsAdapter);
		
		if (appsAdaptersService.insert(appsAdapter)) {
			return new Message<AppsAdapters>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<AppsAdapters>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(@RequestBody  AppsAdapters appsAdapter,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , appsAdapter);
		if (appsAdaptersService.update(appsAdapter)) {
		    return new Message<AppsAdapters>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<AppsAdapters>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		if (appsAdaptersService.deleteBatch(ids)) {
			 return new Message<AppsAdapters>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<AppsAdapters>(Message.FAIL).buildResponse();
		}
	}
}
