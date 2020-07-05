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
 

package org.maxkey.web.contorller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.domain.RolePermissions;
import org.maxkey.persistence.service.RolesService;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/permissions"})
public class PermissionsController {
	final static Logger _logger = LoggerFactory.getLogger(PermissionsController.class);
	
	@Autowired
    @Qualifier("rolesService")
    RolesService rolesService;

	
	
	@RequestMapping(value={"/list"})
	public ModelAndView resourcesList(){
		return new ModelAndView("permissions/permissionsList");
	}

	
	@ResponseBody
	@RequestMapping(value={"/savepermissions"})
	public Message insert(@ModelAttribute("rolePermissions") RolePermissions rolePermissions) {
		_logger.debug("-save  :" + rolePermissions);
		//have
		List<RolePermissions> rolePermissionsedList = rolesService.queryRolePermissions(
		                new RolePermissions(rolePermissions.getAppId(),rolePermissions.getRoleId()));
		
		HashMap<String,String >permedMap =new HashMap<String,String >();
		for(RolePermissions rolePerms : rolePermissionsedList) {
		    permedMap.put(rolePerms.getUniqueId(),rolePerms.getId());
		}
		//Maybe insert
		ArrayList<RolePermissions> rolePermissionsList =new ArrayList<RolePermissions>();
		List<String>resourceIds = StringUtils.string2List(rolePermissions.getResourceId(), ",");
		HashMap<String,String >newPermsMap =new HashMap<String,String >();
		for(String resourceId : resourceIds) {
		    
		    RolePermissions newRolePermissions=new RolePermissions(
                    rolePermissions.getAppId(),
                    rolePermissions.getRoleId(),
                    resourceId);
		    
		    newPermsMap.put(newRolePermissions.getUniqueId(), rolePermissions.getAppId());
		    
		    if(!rolePermissions.getAppId().equalsIgnoreCase(resourceId) &&
		            !permedMap.containsKey(newRolePermissions.getUniqueId())) {
    		    rolePermissionsList.add(newRolePermissions);
		    }
		}
		
		//delete 
		ArrayList<RolePermissions> deleteRolePermissionsList =new ArrayList<RolePermissions>();
		for(RolePermissions rolePerms : rolePermissionsedList) {
           if(!newPermsMap.containsKey(rolePerms.getUniqueId())) {
               deleteRolePermissionsList.add(rolePerms);
           }
        }
		if (!deleteRolePermissionsList.isEmpty()) {
		    rolesService.logisticDeleteRolePermissions(deleteRolePermissionsList);
		}
		
		if (!rolePermissionsList.isEmpty() && rolesService.insertRolePermissions(rolePermissionsList)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		
	}
	
	@ResponseBody
    @RequestMapping(value={"/querypermissions"})
    public List<RolePermissions> querypermissions(@ModelAttribute("rolePermissions") RolePermissions rolePermissions) {
        _logger.debug("-querypermissions  :" + rolePermissions);
        //have
        List<RolePermissions> rolePermissionsedList = rolesService.queryRolePermissions(
                        new RolePermissions(rolePermissions.getAppId(),rolePermissions.getRoleId()));
        return rolePermissionsedList;
	}

	
}
