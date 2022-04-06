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
import org.maxkey.entity.GroupPrivileges;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.maxkey.entity.apps.Apps;
import org.maxkey.persistence.service.GroupPrivilegesService;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/access/privileges"})
public class GroupPrivilegesController {
	final static Logger _logger = LoggerFactory.getLogger(GroupPrivilegesController.class);
	
	@Autowired
	GroupPrivilegesService groupPrivilegesService;

	@RequestMapping(value = { "/appsInGroup" })
	@ResponseBody
	public ResponseEntity<?> appsInGroup(
			@ModelAttribute GroupPrivileges groupPrivilege,
			@CurrentUser UserInfo currentUser) {
		JpaPageResults<GroupPrivileges> groupPrivileges;
		groupPrivilege.setInstId(currentUser.getInstId());
		groupPrivileges= groupPrivilegesService.queryPageResults("appsInGroup",groupPrivilege);

		if(groupPrivileges!=null&&groupPrivileges.getRows()!=null){
			for (Apps app : groupPrivileges.getRows()){
				app.transIconBase64();
			}
		}
		return new Message<JpaPageResults<GroupPrivileges>>(Message.FAIL,groupPrivileges).buildResponse();
	}
	
	@RequestMapping(value = { "/appsNotInGroup" })
	@ResponseBody
	public ResponseEntity<?> appsNotInGroup(
				@ModelAttribute GroupPrivileges groupPrivilege,
				@CurrentUser UserInfo currentUser) {
		JpaPageResults<GroupPrivileges> groupPrivileges;
		groupPrivilege.setInstId(currentUser.getInstId());
		groupPrivileges= groupPrivilegesService.queryPageResults("appsNotInGroup",groupPrivilege);

		if(groupPrivileges!=null&&groupPrivileges.getRows()!=null){
			for (Apps app : groupPrivileges.getRows()){
				app.transIconBase64();
			}
		}
		return new Message<JpaPageResults<GroupPrivileges>>(Message.FAIL,groupPrivileges).buildResponse();
	}

	@RequestMapping(value = {"/add"})
	@ResponseBody
	public ResponseEntity<?> insertGroupApp(
				@RequestBody GroupPrivileges groupPrivileges,
				@CurrentUser UserInfo currentUser) {
		if (groupPrivileges == null || groupPrivileges.getGroupId() == null) {
			return new Message<GroupPrivileges>(Message.FAIL).buildResponse();
		}
		String groupId = groupPrivileges.getGroupId();
		
		boolean result = true;
		String appIds = groupPrivileges.getAppId();
		if (appIds != null) {
			String[] arrAppIds = appIds.split(",");
			for (int i = 0; i < arrAppIds.length; i++) {
				GroupPrivileges newGroupPrivilege = 
						new GroupPrivileges(groupId, arrAppIds[i],currentUser.getInstId());
				newGroupPrivilege.setId(WebContext.genId());
				result = groupPrivilegesService.insert(newGroupPrivilege);
			}
			if(result) {
				return new Message<GroupPrivileges>(Message.SUCCESS).buildResponse();
			}
		}
		return new Message<GroupPrivileges>(Message.FAIL).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		_logger.debug("-delete ids : {}" , ids);
		if (groupPrivilegesService.deleteBatch(ids)) {
			 return new Message<GroupPrivileges>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<GroupPrivileges>(Message.FAIL).buildResponse();
		}
	}

}
