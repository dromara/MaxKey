/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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

import java.sql.Types;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.Institutions;
import org.dromara.maxkey.entity.permissions.Roles;
import org.dromara.maxkey.persistence.mapper.RolesMapper;
import org.dromara.maxkey.persistence.service.InstitutionsService;
import org.dromara.maxkey.persistence.service.RoleMemberService;
import org.dromara.maxkey.persistence.service.RolesService;
import org.dromara.maxkey.util.StrUtils;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolesServiceImpl  extends JpaServiceImpl<RolesMapper,Roles> implements RolesService{
    static final  Logger _logger = LoggerFactory.getLogger(RolesServiceImpl.class);
    
    @Autowired
    RoleMemberService roleMemberService;
    
    @Autowired
    InstitutionsService institutionsService;

	
	public List<Roles> queryDynamicRoles(Roles groups){
	    return this.getMapper().queryDynamicRoles(groups);
	}
	
	public boolean deleteById(String groupId) {
	    this.delete(groupId);
	    roleMemberService.deleteByRoleId(groupId);
	    return true;
	}
	
	public List<Roles> queryRolesByUserId(String userId){
		return this.getMapper().queryRolesByUserId(userId);
	}
	
	public void refreshDynamicRoles(Roles dynamicRole){
	    if(dynamicRole.getCategory().equals(Roles.Category.DYNAMIC)) {
	        
	        if(StringUtils.isNotBlank(dynamicRole.getOrgIdsList())) {
    	    	String []orgIds = dynamicRole.getOrgIdsList().split(",");
    	    	StringBuffer orgIdFilters = new StringBuffer();
    	    	for(String orgId : orgIds) {
    	    		if(StringUtils.isNotBlank(orgId)) {
	    	    		if(orgIdFilters.length() > 0) {
	    	    			orgIdFilters.append(",");
	    	    		}
	    	    		orgIdFilters.append("'").append(orgId).append("'");
    	    		}
    	    	}
    	    	if(orgIdFilters.length() > 0) {
    	    		dynamicRole.setOrgIdsList(orgIdFilters.toString());
    	    	}
    	    }
	        
    	    String filters = dynamicRole.getFilters();
    	    _logger.debug("filters {}" , filters);
    	    if(StringUtils.isNotBlank(filters)) {
	    		if(StrUtils.filtersSQLInjection(filters.toLowerCase())) {  
	    			_logger.info("filters include SQL Injection Attack Risk.");
	    			return;
	    		}
	    		//replace & with AND, | with OR
	    		filters = filters.replace("&", " AND ").replace("\\|", " OR ");
	    	    
	    		_logger.debug("set filters {}" , filters);
	    	    dynamicRole.setFilters(filters);
    	    }
	    
	    	roleMemberService.deleteDynamicRoleMember(dynamicRole);
	    	roleMemberService.addDynamicRoleMember(dynamicRole);
        
	    }
    }
	
	public void refreshAllDynamicRoles(){
		List<Institutions> instList = 
				institutionsService.find("where status = ? ", new Object[]{ConstsStatus.ACTIVE}, new int[]{Types.INTEGER});
		for(Institutions inst : instList) {
			Roles role = new Roles();
			role.setInstId(inst.getId());
		    List<Roles>  rolesList = queryDynamicRoles(role);
	        for(Roles r : rolesList) {
	            _logger.debug("role {}" , r);
	            refreshDynamicRoles(r);
	        }
		}
	}

}
