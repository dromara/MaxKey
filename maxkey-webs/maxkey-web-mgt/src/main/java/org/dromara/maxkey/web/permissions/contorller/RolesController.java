/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
 



package org.dromara.maxkey.web.permissions.contorller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsAct;
import org.dromara.maxkey.constants.ConstsActResult;
import org.dromara.maxkey.constants.ConstsEntryType;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.entity.permissions.Roles;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.RolesService;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value={"/permissions/roles"})
public class RolesController {
	static final Logger _logger = LoggerFactory.getLogger(RolesController.class);
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	HistorySystemLogsService systemLog;
	
	@GetMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<JpaPageResults<Roles>> fetch(
			@ModelAttribute Roles role,
			@CurrentUser UserInfo currentUser) {
		_logger.debug("fetch {}",role);
		role.setInstId(currentUser.getInstId());
		return new Message<>(rolesService.fetchPageResults(role));
	}

	@GetMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<Roles> query(@ModelAttribute Roles role,@CurrentUser UserInfo currentUser) {
		_logger.debug("-query  : {}" , role);
		role.setInstId(currentUser.getInstId());
		if (!rolesService.query(role).isEmpty()) {
			 return new Message<>(Message.SUCCESS);
		} else {
			 return new Message<>(Message.FAIL);
		}
		
	}
	
	@GetMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<Roles> get(@PathVariable("id") String id,@CurrentUser UserInfo currentUser) {
		Roles role=rolesService.get(id);
		return new Message<>(role);
	}
	
	@PostMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<Roles> insert(@RequestBody Roles role,@CurrentUser UserInfo currentUser) {
		_logger.debug("-Add  : {}" , role);
		role.setCreatedBy(currentUser.getId());
		role.setInstId(currentUser.getInstId());
		role.setId(role.generateId());
		if(StringUtils.isBlank(role.getRoleCode())) {
			role.setRoleCode(role.getId());
		}
		if (rolesService.insert(role)) {
			rolesService.refreshDynamicRoles(role);
		    systemLog.insert(
					ConstsEntryType.ROLE, 
					role, 
					ConstsAct.CREATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
		    return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}

	@PutMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<Roles> update(@RequestBody Roles role,@CurrentUser UserInfo currentUser) {
		_logger.debug("-update  group : {}" , role);
		if(role.getId().equalsIgnoreCase("ROLE_ALL_USER")) {
			role.setDefaultAllUser();
		}
		role.setModifiedBy(currentUser.getId());
		role.setInstId(currentUser.getInstId());
		if (rolesService.update(role)) {
			rolesService.refreshDynamicRoles(role);
		    systemLog.insert(
					ConstsEntryType.ROLE, 
					role, 
					ConstsAct.UPDATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
		    return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}

	@DeleteMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<Roles> delete(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
		_logger.debug("-delete ids : {}" , ids);
		ids.removeAll(Arrays.asList("ROLE_ALL_USER","ROLE_ADMINISTRATORS","-1"));
		if (rolesService.deleteBatch(ids)) {
			systemLog.insert(
					ConstsEntryType.ROLE, 
					ids, 
					ConstsAct.DELETE, 
					ConstsActResult.SUCCESS, 
					currentUser);
			 return new Message<>(Message.SUCCESS);
		} else {
			return new Message<>(Message.FAIL);
		}
	}
}
