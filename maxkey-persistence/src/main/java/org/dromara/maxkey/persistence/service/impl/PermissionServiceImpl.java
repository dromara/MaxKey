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
 

package org.dromara.maxkey.persistence.service.impl;

import java.util.List;

import org.dromara.maxkey.entity.permissions.Permission;
import org.dromara.maxkey.persistence.mapper.PermissionMapper;
import org.dromara.maxkey.persistence.service.PermissionService;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionServiceImpl  extends JpaServiceImpl<PermissionMapper,Permission> implements PermissionService{
	static final  Logger _logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

	public boolean insertGroupPrivileges(List<Permission> rolePermissionsList) {
	    return getMapper().insertGroupPrivileges(rolePermissionsList)>0;
	};
    
	public boolean deleteGroupPrivileges(List<Permission> rolePermissionsList) {
	     return getMapper().deleteGroupPrivileges(rolePermissionsList)>=0;
	 }
	
    public List<Permission> queryGroupPrivileges(Permission rolePermissions){
        return getMapper().queryGroupPrivileges(rolePermissions);
    }    

}
