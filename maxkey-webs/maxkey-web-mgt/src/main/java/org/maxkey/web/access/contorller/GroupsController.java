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
 

package org.maxkey.web.access.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.entity.Groups;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.GroupsService;
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
@RequestMapping(value={"/access/groups"})
public class GroupsController {
	final static Logger _logger = LoggerFactory.getLogger(GroupsController.class);
	
	@Autowired
	GroupsService groupsService;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(
			@ModelAttribute Groups groups,
			@CurrentUser UserInfo currentUser) {
		_logger.debug(""+groups);
		groups.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Groups>>(
				groupsService.queryPageResults(groups)).buildResponse();
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> query(@ModelAttribute Groups group,@CurrentUser UserInfo currentUser) {
		_logger.debug("-query  :" + group);
		group.setInstId(currentUser.getInstId());
		if (groupsService.load(group)!=null) {
			 return new Message<Groups>(Message.SUCCESS).buildResponse();
		} else {
			 return new Message<Groups>(Message.FAIL).buildResponse();
		}
		
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@PathVariable("id") String id,@CurrentUser UserInfo currentUser) {
		Groups group=groupsService.get(id);
		return new Message<Groups>(group).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> insert(@RequestBody Groups group,@CurrentUser UserInfo currentUser) {
		_logger.debug("-Add  :" + group);
		group.setInstId(currentUser.getInstId());
		if (groupsService.insert(group)) {
		    groupsService.refreshDynamicGroups(group);
		    return new Message<Groups>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Groups>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(@RequestBody Groups group,@CurrentUser UserInfo currentUser) {
		_logger.debug("-update  group :" + group);
		group.setInstId(currentUser.getInstId());
		if (groupsService.update(group)) {
		    groupsService.refreshDynamicGroups(group);
		    return new Message<Groups>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Groups>(Message.FAIL).buildResponse();
		}
	}

	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		_logger.debug("-delete ids : {}" , ids);
		
		if (groupsService.deleteBatch(ids)) {
			 return new Message<Groups>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Groups>(Message.FAIL).buildResponse();
		}
	}
}
