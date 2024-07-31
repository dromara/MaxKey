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
 

package org.dromara.maxkey.web.idm.contorller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsEntryType;
import org.dromara.maxkey.constants.ConstsAct;
import org.dromara.maxkey.constants.ConstsActResult;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.Groups;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.entity.permissions.Roles;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.GroupsService;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value={"/access/groups"})
public class GroupsController {
	static final Logger logger = LoggerFactory.getLogger(GroupsController.class);
	
	@Autowired
	GroupsService service;
	
	@Autowired
	HistorySystemLogsService systemLog;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Message<?> fetch(
			@ModelAttribute Groups group,
			@CurrentUser UserInfo currentUser) {
		logger.debug("group {}" , group);
		group.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Groups>>(
				service.fetchPageResults(group));
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> query(@ModelAttribute Groups group,@CurrentUser UserInfo currentUser) {
		logger.debug("-query  : {}" , group);
		group.setInstId(currentUser.getInstId());
		if (service.query(group)!=null) {
			 return new Message<Groups>(Message.SUCCESS);
		} else {
			 return new Message<Groups>(Message.FAIL);
		}
		
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> get(@PathVariable("id") String id,@CurrentUser UserInfo currentUser) {
		Groups group =service.get(id);
		return new Message<Groups>(group);
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> insert(@RequestBody Groups group,@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}" , group);
		group.setInstId(currentUser.getInstId());
		group.setId(group.generateId());
		if(StringUtils.isBlank(group.getGroupCode())) {
			group.setGroupCode(group.getId());
		}
		if (service.insert(group)) {
			service.refreshDynamicGroups(group);
		    systemLog.insert(
					ConstsEntryType.ROLE, 
					group, 
					ConstsAct.CREATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
		    return new Message<Groups>(Message.SUCCESS);
		} else {
			return new Message<Groups>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> update(@RequestBody Groups group,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  group : {}" , group);
		if(group.getId().equalsIgnoreCase("ROLE_ALL_USER")) {
			group.setDefaultAllUser();
		}
		group.setInstId(currentUser.getInstId());
		if (service.update(group)) {
			service.refreshDynamicGroups(group);
		    systemLog.insert(
					ConstsEntryType.ROLE, 
					group, 
					ConstsAct.UPDATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
		    return new Message<Roles>(Message.SUCCESS);
		} else {
			return new Message<Roles>(Message.FAIL);
		}
	}

	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> delete(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete ids : {}" , ids);
		ids.removeAll(Arrays.asList("ROLE_ALL_USER","ROLE_ADMINISTRATORS","-1"));
		if (service.deleteBatch(ids)) {
			systemLog.insert(
					ConstsEntryType.ROLE, 
					ids, 
					ConstsAct.DELETE, 
					ConstsActResult.SUCCESS, 
					currentUser);
			 return new Message<Roles>(Message.SUCCESS);
		} else {
			return new Message<Roles>(Message.FAIL);
		}
	}
}
