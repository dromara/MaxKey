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

import org.dromara.maxkey.entity.GroupPrivileges;
import org.dromara.maxkey.persistence.mapper.GroupPrivilegesMapper;
import org.dromara.mybatis.jpa.JpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GroupPrivilegesService  extends JpaService<GroupPrivileges>{
    final static Logger _logger = LoggerFactory.getLogger(GroupPrivilegesService.class);
   
    
	public GroupPrivilegesService() {
		super(GroupPrivilegesMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public GroupPrivilegesMapper getMapper() {
		return (GroupPrivilegesMapper)super.getMapper();
	}
	
	public boolean insertGroupPrivileges(List<GroupPrivileges> rolePermissionsList) {
	    return getMapper().insertGroupPrivileges(rolePermissionsList)>0;
	};
    
	public boolean deleteGroupPrivileges(List<GroupPrivileges> rolePermissionsList) {
	     return getMapper().deleteGroupPrivileges(rolePermissionsList)>=0;
	 }
	
    public List<GroupPrivileges> queryGroupPrivileges(GroupPrivileges rolePermissions){
        return getMapper().queryGroupPrivileges(rolePermissions);
    }    

}
