package org.dromara.maxkey.web.permissions.contorller;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.entity.permissions.Permission;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.PermissionService;
import org.dromara.maxkey.util.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value={"/permissions/permission"})
public class PermissionController {
	static final Logger logger = LoggerFactory.getLogger(PermissionController.class);
	
	@Autowired
	PermissionService permissionService;
	
	@Autowired
	HistorySystemLogsService systemLog;
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public  Message<?> update(
			@RequestBody Permission groupPrivileges,
			@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , groupPrivileges);
		//have
		Permission queryGroupPrivileges = 
				new Permission(
						groupPrivileges.getAppId(),
						groupPrivileges.getGroupId(),
						currentUser.getInstId());
		List<Permission> groupPrivilegesList = permissionService.queryGroupPrivileges(queryGroupPrivileges);
		
		HashMap<String,String >privilegeMap =new HashMap<String,String >();
		for(Permission rolePrivilege : groupPrivilegesList) {
			privilegeMap.put(rolePrivilege.getUniqueId(),rolePrivilege.getId());
		}
		//Maybe insert
		ArrayList<Permission> newGroupPrivilegesList =new ArrayList<Permission>();
		List<String>resourceIds = StrUtils.string2List(groupPrivileges.getResourceId(), ",");
		HashMap<String,String >newPrivilegesMap =new HashMap<String,String >();
		for(String resourceId : resourceIds) {
		    Permission newGroupPrivilege=new Permission(
		    		groupPrivileges.getAppId(),
		    		groupPrivileges.getGroupId(),
                    resourceId,
                    currentUser.getInstId());
		    newGroupPrivilege.setId(newGroupPrivilege.generateId());
		    newPrivilegesMap.put(newGroupPrivilege.getUniqueId(), groupPrivileges.getAppId());
		    
		    if(!groupPrivileges.getAppId().equalsIgnoreCase(resourceId) &&
		            !privilegeMap.containsKey(newGroupPrivilege.getUniqueId())) {
		    	newGroupPrivilegesList.add(newGroupPrivilege);
		    }
		}
		
		//delete 
		ArrayList<Permission> deleteGroupPrivilegesList =new ArrayList<Permission>();
		for(Permission rolePrivilege : groupPrivilegesList) {
           if(!newPrivilegesMap.containsKey(rolePrivilege.getUniqueId())) {
        	   rolePrivilege.setInstId(currentUser.getInstId());
        	   deleteGroupPrivilegesList.add(rolePrivilege);
           }
        }
		if (!deleteGroupPrivilegesList.isEmpty()) {
			logger.debug("-remove  : {}" , deleteGroupPrivilegesList);
			permissionService.deleteGroupPrivileges(deleteGroupPrivilegesList);
		}
		
		if (!newGroupPrivilegesList.isEmpty() && permissionService.insertGroupPrivileges(newGroupPrivilegesList)) {
			logger.debug("-insert  : {}" , newGroupPrivilegesList);
			return new Message<Permission>(Message.SUCCESS);
			
		} else {
			return new Message<Permission>(Message.SUCCESS);
		}
		
	}
	
	@ResponseBody
    @RequestMapping(value={"/get"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public  Message<?> get(
    		@ModelAttribute Permission groupPrivileges,
    		@CurrentUser UserInfo currentUser) {
        logger.debug("-get  : {}"  , groupPrivileges);
        //have
        Permission queryGroupPrivilege = 
        		new Permission(
        				groupPrivileges.getAppId(),
        				groupPrivileges.getGroupId(),
        				currentUser.getInstId());
        List<Permission> rolePrivilegeList = permissionService.queryGroupPrivileges(queryGroupPrivilege);
        
        return new Message<List<Permission>>(
        		rolePrivilegeList);
	}

	
}
