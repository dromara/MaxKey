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
 

package org.dromara.maxkey.persistence.service;

import java.io.Serializable;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.Institutions;
import org.dromara.maxkey.entity.Roles;
import org.dromara.maxkey.persistence.mapper.RolesMapper;
import org.dromara.maxkey.util.StringUtils;
import org.dromara.mybatis.jpa.JpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Repository
public class RolesService  extends JpaService<Roles> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4156671926199393550L;
    
    final static Logger _logger = LoggerFactory.getLogger(RolesService.class);
    @JsonIgnore
    @Autowired
    RoleMemberService roleMemberService;
    
    @Autowired
    InstitutionsService institutionsService;
    
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
	
	
	public List<Roles> queryDynamicRoles(Roles groups){
	    return this.getMapper().queryDynamicRoles(groups);
	}
	
	public boolean deleteById(String groupId) {
	    this.remove(groupId);
	    roleMemberService.deleteByRoleId(groupId);
	    return true;
	}
	
	public List<Roles> queryRolesByUserId(String userId){
		return this.getMapper().queryRolesByUserId(userId);
	}
	
	public void refreshDynamicRoles(Roles dynamicRole){
	    if(dynamicRole.getCategory().equals(Roles.Category.DYNAMIC)) {
	        boolean isDynamicTimeSupport = false;
	        boolean isBetweenEffectiveTime = false;
	        if(StringUtils.isNotBlank(dynamicRole.getResumeTime())
	                &&StringUtils.isNotBlank(dynamicRole.getSuspendTime())
	                &&!dynamicRole.getSuspendTime().equals("00:00")) {
	            LocalTime currentTime = LocalDateTime.now().toLocalTime();
	            LocalTime resumeTime = LocalTime.parse(dynamicRole.getResumeTime());
	            LocalTime suspendTime = LocalTime.parse(dynamicRole.getSuspendTime());
	            
	            _logger.info("currentTime: " + currentTime 
                        + " , resumeTime : " + resumeTime 
                        + " , suspendTime: " + suspendTime);
	            isDynamicTimeSupport = true;
	            
	            if(resumeTime.isBefore(currentTime) && currentTime.isBefore(suspendTime)) {
	                isBetweenEffectiveTime = true;
	            }
	            
	        }
	        
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
    	    if(StringUtils.isNotBlank(filters)) {
	    		if(StringUtils.filtersSQLInjection(filters.toLowerCase())) {  
	    			_logger.info("filters include SQL Injection Attack Risk.");
	    			return;
	    		}
	    		filters = filters.replace("&", " AND ");
	    	    filters = filters.replace("|", " OR ");
	    	    
	    	    dynamicRole.setFilters(filters);
    	    }
    	    
    	    if(isDynamicTimeSupport) {
    	        if(isBetweenEffectiveTime) {
    	        	roleMemberService.deleteDynamicRoleMember(dynamicRole);
    	        	roleMemberService.addDynamicRoleMember(dynamicRole);
    	        }else {
    	        	roleMemberService.deleteDynamicRoleMember(dynamicRole);
    	        }
    	    }else{
    	    	roleMemberService.deleteDynamicRoleMember(dynamicRole);
    	    	roleMemberService.addDynamicRoleMember(dynamicRole);
            }
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
	            _logger.debug("role " + rolesList);
	            refreshDynamicRoles(r);
	        }
		}
	}

}
