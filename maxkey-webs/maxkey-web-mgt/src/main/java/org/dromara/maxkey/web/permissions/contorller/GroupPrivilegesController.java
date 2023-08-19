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
import org.dromara.maxkey.entity.GroupPrivileges;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.GroupPrivilegesService;
import org.dromara.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/permissions/privileges"})
public class GroupPrivilegesController {
	static final Logger logger = LoggerFactory.getLogger(GroupPrivilegesController.class);
	
	@Autowired
	GroupPrivilegesService groupPrivilegesService;
	
	@Autowired
	HistorySystemLogsService systemLog;
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public  ResponseEntity<?> update(
			@RequestBody GroupPrivileges groupPrivileges,
			@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , groupPrivileges);
		//have
		GroupPrivileges queryGroupPrivileges = 
				new GroupPrivileges(
						groupPrivileges.getAppId(),
						groupPrivileges.getGroupId(),
						currentUser.getInstId());
		List<GroupPrivileges> groupPrivilegesList = groupPrivilegesService.queryGroupPrivileges(queryGroupPrivileges);
		
		HashMap<String,String >privilegeMap =new HashMap<String,String >();
		for(GroupPrivileges rolePrivilege : groupPrivilegesList) {
			privilegeMap.put(rolePrivilege.getUniqueId(),rolePrivilege.getId());
		}
		//Maybe insert
		ArrayList<GroupPrivileges> newGroupPrivilegesList =new ArrayList<GroupPrivileges>();
		List<String>resourceIds = StringUtils.string2List(groupPrivileges.getResourceId(), ",");
		HashMap<String,String >newPrivilegesMap =new HashMap<String,String >();
		for(String resourceId : resourceIds) {
		    GroupPrivileges newGroupPrivilege=new GroupPrivileges(
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
		ArrayList<GroupPrivileges> deleteGroupPrivilegesList =new ArrayList<GroupPrivileges>();
		for(GroupPrivileges rolePrivilege : groupPrivilegesList) {
           if(!newPrivilegesMap.containsKey(rolePrivilege.getUniqueId())) {
        	   rolePrivilege.setInstId(currentUser.getInstId());
        	   deleteGroupPrivilegesList.add(rolePrivilege);
           }
        }
		if (!deleteGroupPrivilegesList.isEmpty()) {
			logger.debug("-remove  : {}" , deleteGroupPrivilegesList);
			groupPrivilegesService.deleteGroupPrivileges(deleteGroupPrivilegesList);
		}
		
		if (!newGroupPrivilegesList.isEmpty() && groupPrivilegesService.insertGroupPrivileges(newGroupPrivilegesList)) {
			logger.debug("-insert  : {}" , newGroupPrivilegesList);
			return new Message<GroupPrivileges>(Message.SUCCESS).buildResponse();
			
		} else {
			return new Message<GroupPrivileges>(Message.SUCCESS).buildResponse();
		}
		
	}
	
	@ResponseBody
    @RequestMapping(value={"/get"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public  ResponseEntity<?> get(
    		@ModelAttribute GroupPrivileges groupPrivileges,
    		@CurrentUser UserInfo currentUser) {
        logger.debug("-get  : {}"  , groupPrivileges);
        //have
        GroupPrivileges queryGroupPrivilege = 
        		new GroupPrivileges(
        				groupPrivileges.getAppId(),
        				groupPrivileges.getGroupId(),
        				currentUser.getInstId());
        List<GroupPrivileges> rolePrivilegeList = groupPrivilegesService.queryGroupPrivileges(queryGroupPrivilege);
        
        return new Message<List<GroupPrivileges>>(
        		rolePrivilegeList).buildResponse();
	}

	
}
