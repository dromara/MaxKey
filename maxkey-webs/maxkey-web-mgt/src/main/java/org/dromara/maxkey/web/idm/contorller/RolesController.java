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

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsEntryType;
import org.dromara.maxkey.constants.ConstsOperateAction;
import org.dromara.maxkey.constants.ConstsOperateResult;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.Roles;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.RolesService;
import org.dromara.maxkey.util.StringUtils;
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
@RequestMapping(value={"/access/roles"})
public class RolesController {
	final static Logger logger = LoggerFactory.getLogger(RolesController.class);
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	HistorySystemLogsService systemLog;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(
			@ModelAttribute Roles role,
			@CurrentUser UserInfo currentUser) {
		logger.debug("role {}" , role);
		role.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Roles>>(
				rolesService.fetchPageResults(role)).buildResponse();
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> query(@ModelAttribute Roles role,@CurrentUser UserInfo currentUser) {
		logger.debug("-query  : {}" , role);
		role.setInstId(currentUser.getInstId());
		if (rolesService.query(role)!=null) {
			 return new Message<Roles>(Message.SUCCESS).buildResponse();
		} else {
			 return new Message<Roles>(Message.FAIL).buildResponse();
		}
		
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@PathVariable("id") String id,@CurrentUser UserInfo currentUser) {
		Roles role=rolesService.get(id);
		return new Message<Roles>(role).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> insert(@RequestBody Roles role,@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}" , role);
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
					ConstsOperateAction.CREATE, 
					ConstsOperateResult.SUCCESS, 
					currentUser);
		    return new Message<Roles>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Roles>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(@RequestBody Roles role,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  group : {}" , role);
		if(role.getId().equalsIgnoreCase("ROLE_ALL_USER")) {
			role.setDefaultAllUser();
		}
		role.setInstId(currentUser.getInstId());
		if (rolesService.update(role)) {
			rolesService.refreshDynamicRoles(role);
		    systemLog.insert(
					ConstsEntryType.ROLE, 
					role, 
					ConstsOperateAction.UPDATE, 
					ConstsOperateResult.SUCCESS, 
					currentUser);
		    return new Message<Roles>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Roles>(Message.FAIL).buildResponse();
		}
	}

	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete ids : {}" , ids);
		ids = ids.replaceAll("ROLE_ALL_USER", "-1").replaceAll("ROLE_ADMINISTRATORS", "-1");
		if (rolesService.deleteBatch(ids)) {
			systemLog.insert(
					ConstsEntryType.ROLE, 
					ids, 
					ConstsOperateAction.DELETE, 
					ConstsOperateResult.SUCCESS, 
					currentUser);
			 return new Message<Roles>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Roles>(Message.FAIL).buildResponse();
		}
	}
}
