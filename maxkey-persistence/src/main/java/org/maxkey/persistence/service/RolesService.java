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
 

package org.maxkey.persistence.service;

import java.util.List;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.entity.RolePermissions;
import org.maxkey.entity.Roles;
import org.maxkey.persistence.mapper.RolesMapper;
import org.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class RolesService  extends JpaBaseService<Roles>{
    final static Logger _logger = LoggerFactory.getLogger(RolesService.class);
    
    @Autowired
    @Qualifier("roleMemberService")
    RoleMemberService roleMemberService;
    
	public RolesService() {
		super(RolesMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public RolesMapper getMapper() {
		return (RolesMapper)super.getMapper();
	}
	
	public boolean insertRolePermissions(List<RolePermissions> rolePermissionsList) {
	    return getMapper().insertRolePermissions(rolePermissionsList)>0;
	};
    
	public boolean logisticDeleteRolePermissions(List<RolePermissions> rolePermissionsList) {
	     return getMapper().logisticDeleteRolePermissions(rolePermissionsList)>=0;
	 }
	
    public List<RolePermissions> queryRolePermissions(RolePermissions rolePermissions){
        return getMapper().queryRolePermissions(rolePermissions);
    }
    
    public List<Roles> queryDynamicRoles(Roles dynamicRole){
        return this.getMapper().queryDynamicRoles(dynamicRole);
    }
    
    public boolean deleteById(String roleId) {
        this.remove(roleId);
        roleMemberService.deleteByRoleId(roleId);
        return true;
    }
    
    public void refreshDynamicRoles(Roles dynamicRole){
        if(dynamicRole.getDynamic().equals("1")) {
            if(dynamicRole.getOrgIdsList()!=null && !dynamicRole.getOrgIdsList().equals("")) {
                dynamicRole.setOrgIdsList("'"+dynamicRole.getOrgIdsList().replace(",", "','")+"'");
            }
            
            String filters = dynamicRole.getFilters();
            if(StringUtils.filtersSQLInjection(filters.toLowerCase())) {  
                _logger.info("filters include SQL Injection Attack Risk.");
                return;
            }
            
            filters = filters.replace("&", " AND ");
            filters = filters.replace("|", " OR ");
            
            dynamicRole.setFilters(filters);
            
            roleMemberService.deleteDynamicRoleMember(dynamicRole);
            roleMemberService.addDynamicRoleMember(dynamicRole);
        }
    }
}
