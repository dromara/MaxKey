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
 

package org.maxkey.web.permissions.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.entity.Message;
import org.maxkey.entity.Roles;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.RolesService;
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
@RequestMapping(value={"/permissions/roles"})
public class RolesController {
	final static Logger _logger = LoggerFactory.getLogger(RolesController.class);
	
	@Autowired
	RolesService rolesService;

	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(@ModelAttribute Roles roles,@CurrentUser UserInfo currentUser) {
		_logger.debug(""+roles);
		roles.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Roles>>(
				rolesService.queryPageResults(roles)).buildResponse();
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> query(@ModelAttribute Roles role,@CurrentUser UserInfo currentUser) {
		_logger.debug("-query  :" + role);
		role.setInstId(currentUser.getInstId());
		if (rolesService.load(role)!=null) {
			 return new Message<Roles>(Message.SUCCESS).buildResponse();
		} else {
			 return new Message<Roles>(Message.SUCCESS).buildResponse();
		}
		
	}

	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		Roles role=rolesService.get(id);
		return new Message<Roles>(role).buildResponse();
	}

	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> insert(@RequestBody  Roles role,@CurrentUser UserInfo currentUser) {
		_logger.debug("-Add  :" + role);
		role.setInstId(currentUser.getInstId());
		if (rolesService.insert(role)) {
		    rolesService.refreshDynamicRoles(role);
		    return new Message<Roles>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Roles>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(@RequestBody  Roles role,@CurrentUser UserInfo currentUser) {
		_logger.debug("-update  :" + role);
		role.setInstId(currentUser.getInstId());
		if (rolesService.update(role)) {
		    rolesService.refreshDynamicRoles(role);
		    return new Message<Roles>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Roles>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		_logger.debug("-delete ids : {} " , ids);
		
		if (rolesService.deleteBatch(ids)) {
			 return new Message<Roles>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Roles>(Message.FAIL).buildResponse();
		}
		
	}
}
