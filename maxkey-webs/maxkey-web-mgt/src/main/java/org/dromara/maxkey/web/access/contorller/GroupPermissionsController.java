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
 
package org.dromara.maxkey.web.access.contorller;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.GroupPermissions;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.GroupPermissionssService;
import org.dromara.maxkey.web.WebContext;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
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
@RequestMapping(value={"/access/permissions"})
public class GroupPermissionsController {
	static final Logger logger = LoggerFactory.getLogger(GroupPermissionsController.class);
	
	@Autowired
	GroupPermissionssService groupPermissionssService;

	@Autowired
	HistorySystemLogsService systemLog;
	
	@RequestMapping(value = { "/appsInGroup" })
	@ResponseBody
	public ResponseEntity<?> appsInRole(
			@ModelAttribute GroupPermissions groupPermission,
			@CurrentUser UserInfo currentUser) {
		JpaPageResults<GroupPermissions> groupPermissions;
		groupPermission.setInstId(currentUser.getInstId());
		groupPermissions= groupPermissionssService.fetchPageResults("appsInGroup",groupPermission);

		if(groupPermissions!=null&&groupPermissions.getRows()!=null){
			for (Apps app : groupPermissions.getRows()){
				app.transIconBase64();
			}
		}
		return new Message<JpaPageResults<GroupPermissions>>(Message.FAIL,groupPermissions).buildResponse();
	}
	
	@RequestMapping(value = { "/appsNotInGroup" })
	@ResponseBody
	public ResponseEntity<?> appsNotInRole(
				@ModelAttribute GroupPermissions groupPermission,
				@CurrentUser UserInfo currentUser) {
		JpaPageResults<GroupPermissions> groupPermissions;
		groupPermission.setInstId(currentUser.getInstId());
		groupPermissions= groupPermissionssService.fetchPageResults("appsNotInGroup",groupPermission);

		if(groupPermissions!=null&&groupPermissions.getRows()!=null){
			for (Apps app : groupPermissions.getRows()){
				app.transIconBase64();
			}
		}
		return new Message<JpaPageResults<GroupPermissions>>(Message.FAIL,groupPermissions).buildResponse();
	}

	@RequestMapping(value = {"/add"})
	@ResponseBody
	public ResponseEntity<?> insertPermission(
				@RequestBody GroupPermissions groupPermission,
				@CurrentUser UserInfo currentUser) {
		if (groupPermission == null || groupPermission.getGroupId() == null) {
			return new Message<GroupPermissions>(Message.FAIL).buildResponse();
		}
		String roleId = groupPermission.getGroupId();
		
		boolean result = true;
		String appIds = groupPermission.getAppId();
		if (appIds != null) {
			String[] arrAppIds = appIds.split(",");
			for (int i = 0; i < arrAppIds.length; i++) {
				if(StringUtils.isNotBlank(arrAppIds[i])) {
					GroupPermissions newgroupPermissions = 
							new GroupPermissions(roleId, arrAppIds[i],currentUser.getInstId());
					newgroupPermissions.setId(WebContext.genId());
					result = groupPermissionssService.insert(newgroupPermissions);
				}
			}
			if(result) {
				return new Message<GroupPermissions>(Message.SUCCESS).buildResponse();
			}
		}
		return new Message<GroupPermissions>(Message.FAIL).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete ids : {}" , ids);
		if (groupPermissionssService.deleteBatch(ids)) {
			 return new Message<GroupPermissions>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<GroupPermissions>(Message.FAIL).buildResponse();
		}
	}

}
