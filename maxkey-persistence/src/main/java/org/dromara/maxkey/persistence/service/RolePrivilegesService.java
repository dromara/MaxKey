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
 

package org.dromara.maxkey.persistence.service;

import java.util.List;

import org.dromara.maxkey.entity.RolePrivileges;
import org.dromara.maxkey.persistence.mapper.RolePrivilegesMapper;
import org.dromara.mybatis.jpa.JpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RolePrivilegesService  extends JpaService<RolePrivileges>{
    final static Logger _logger = LoggerFactory.getLogger(RolePrivilegesService.class);
   
    
	public RolePrivilegesService() {
		super(RolePrivilegesMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public RolePrivilegesMapper getMapper() {
		return (RolePrivilegesMapper)super.getMapper();
	}
	
	public boolean insertRolePrivileges(List<RolePrivileges> rolePermissionsList) {
	    return getMapper().insertRolePrivileges(rolePermissionsList)>0;
	};
    
	public boolean deleteRolePrivileges(List<RolePrivileges> rolePermissionsList) {
	     return getMapper().deleteRolePrivileges(rolePermissionsList)>=0;
	 }
	
    public List<RolePrivileges> queryRolePrivileges(RolePrivileges rolePermissions){
        return getMapper().queryRolePrivileges(rolePermissions);
    }    

}
