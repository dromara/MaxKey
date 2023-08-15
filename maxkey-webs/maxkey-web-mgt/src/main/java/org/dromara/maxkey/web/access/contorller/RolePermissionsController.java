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

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.RolePermissions;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.RolePermissionssService;
import org.dromara.maxkey.web.WebContext;
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
public class RolePermissionsController {
	final static Logger _logger = LoggerFactory.getLogger(RolePermissionsController.class);
	
	@Autowired
	RolePermissionssService rolePermissionssService;

	@Autowired
	HistorySystemLogsService systemLog;
	
	@RequestMapping(value = { "/appsInRole" })
	@ResponseBody
	public ResponseEntity<?> appsInRole(
			@ModelAttribute RolePermissions rolePermission,
			@CurrentUser UserInfo currentUser) {
		JpaPageResults<RolePermissions> rolePermissions;
		rolePermission.setInstId(currentUser.getInstId());
		rolePermissions= rolePermissionssService.queryPageResults("appsInRole",rolePermission);

		if(rolePermissions!=null&&rolePermissions.getRows()!=null){
			for (Apps app : rolePermissions.getRows()){
				app.transIconBase64();
			}
		}
		return new Message<JpaPageResults<RolePermissions>>(Message.FAIL,rolePermissions).buildResponse();
	}
	
	@RequestMapping(value = { "/appsNotInRole" })
	@ResponseBody
	public ResponseEntity<?> appsNotInRole(
				@ModelAttribute RolePermissions rolePermission,
				@CurrentUser UserInfo currentUser) {
		JpaPageResults<RolePermissions> rolePermissions;
		rolePermission.setInstId(currentUser.getInstId());
		rolePermissions= rolePermissionssService.queryPageResults("appsNotInRole",rolePermission);

		if(rolePermissions!=null&&rolePermissions.getRows()!=null){
			for (Apps app : rolePermissions.getRows()){
				app.transIconBase64();
			}
		}
		return new Message<JpaPageResults<RolePermissions>>(Message.FAIL,rolePermissions).buildResponse();
	}

	@RequestMapping(value = {"/add"})
	@ResponseBody
	public ResponseEntity<?> insertPermission(
				@RequestBody RolePermissions rolePermission,
				@CurrentUser UserInfo currentUser) {
		if (rolePermission == null || rolePermission.getRoleId() == null) {
			return new Message<RolePermissions>(Message.FAIL).buildResponse();
		}
		String roleId = rolePermission.getRoleId();
		
		boolean result = true;
		String appIds = rolePermission.getAppId();
		if (appIds != null) {
			String[] arrAppIds = appIds.split(",");
			for (int i = 0; i < arrAppIds.length; i++) {
				RolePermissions newRolePermissions = 
						new RolePermissions(roleId, arrAppIds[i],currentUser.getInstId());
				newRolePermissions.setId(WebContext.genId());
				result = rolePermissionssService.insert(newRolePermissions);
			}
			if(result) {
				return new Message<RolePermissions>(Message.SUCCESS).buildResponse();
			}
		}
		return new Message<RolePermissions>(Message.FAIL).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		_logger.debug("-delete ids : {}" , ids);
		if (rolePermissionssService.deleteBatch(ids)) {
			 return new Message<RolePermissions>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<RolePermissions>(Message.FAIL).buildResponse();
		}
	}

}
