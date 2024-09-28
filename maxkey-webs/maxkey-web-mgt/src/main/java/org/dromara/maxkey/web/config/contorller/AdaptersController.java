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

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.apps.AppsAdapters;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.AppsAdaptersService;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value={"/config/adapters"})
public class AdaptersController {
	static final  Logger logger = LoggerFactory.getLogger(AdaptersController.class);
	
	@Autowired
	AppsAdaptersService appsAdaptersService;	
	
	@GetMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<JpaPageResults<AppsAdapters>> fetch(@ModelAttribute AppsAdapters appsAdapter) {
		logger.debug("fetch {}",appsAdapter);
		return new Message<>(
				appsAdaptersService.fetchPageResults(appsAdapter));
	}

	@GetMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<AppsAdapters> query(@ModelAttribute AppsAdapters appsAdapter,@CurrentUser UserInfo currentUser) {
		logger.debug("-query  : {}" , appsAdapter);
		if (CollectionUtils.isNotEmpty(appsAdaptersService.query(appsAdapter))) {
			 return new Message<>(Message.SUCCESS);
		} else {
			 return new Message<>(Message.FAIL);
		}
	}
	
	@GetMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<AppsAdapters> get(@PathVariable("id") String id) {
		AppsAdapters appsAdapter=appsAdaptersService.get(id);
		return new Message<>(appsAdapter);
	}
	
	@PostMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<AppsAdapters> insert(@RequestBody  AppsAdapters appsAdapter,@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}" , appsAdapter);
		
		if (appsAdaptersService.insert(appsAdapter)) {
			return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}
	
	@PutMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<AppsAdapters> update(@RequestBody  AppsAdapters appsAdapter,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , appsAdapter);
		if (appsAdaptersService.update(appsAdapter)) {
		    return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}
	
	@DeleteMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<AppsAdapters> delete(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		if (appsAdaptersService.deleteBatch(ids)) {
			 return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}
}
