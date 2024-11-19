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
 

package org.dromara.maxkey.persistence.service.impl;

import java.sql.Types;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.Institutions;
import org.dromara.maxkey.entity.idm.Groups;
import org.dromara.maxkey.entity.permissions.Roles;
import org.dromara.maxkey.persistence.mapper.GroupsMapper;
import org.dromara.maxkey.persistence.service.GroupMemberService;
import org.dromara.maxkey.persistence.service.GroupsService;
import org.dromara.maxkey.persistence.service.InstitutionsService;
import org.dromara.maxkey.util.StrUtils;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GroupsServiceImpl  extends JpaServiceImpl<GroupsMapper,Groups> implements GroupsService{
    static final  Logger _logger = LoggerFactory.getLogger(GroupsServiceImpl.class);

    @Autowired
    GroupMemberService groupMemberService;
    
    @Autowired
    InstitutionsService institutionsService;
    
	public List<Groups> queryDynamicGroups(Groups groups){
	    return this.getMapper().queryDynamic(groups);
	}
	
	public boolean deleteById(String groupId) {
	    this.delete(groupId);
	    groupMemberService.deleteByGroupId(groupId);
	    return true;
	}
	
	public List<Groups> queryByUserId(String userId){
		return this.getMapper().queryByUserId(userId);
	}
	
	public void refreshDynamicGroups(Groups dynamicGroup){
	    if(dynamicGroup.getCategory().equals(Roles.Category.DYNAMIC)) {
	        
	        if(StringUtils.isNotBlank(dynamicGroup.getOrgIdsList())) {
    	    	String []orgIds = dynamicGroup.getOrgIdsList().split(",");
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
    	    		dynamicGroup.setOrgIdsList(orgIdFilters.toString());
    	    	}
    	    }
	        
    	    String filters = dynamicGroup.getFilters();
    	    if(StringUtils.isNotBlank(filters)) {
	    		if(StrUtils.filtersSQLInjection(filters.toLowerCase())) {  
	    			_logger.info("filters include SQL Injection Attack Risk.");
	    			return;
	    		}
	    		//replace & with AND, | with OR
	    		filters = filters.replace("&", " AND ").replace("|", " OR ");
	    	    
	    	    dynamicGroup.setFilters(filters);
    	    }
	    
	    	groupMemberService.deleteDynamicMember(dynamicGroup);
	    	groupMemberService.addDynamicMember(dynamicGroup);
            
	    }
    }
	
	public void refreshAllDynamicGroups(){
		List<Institutions> instList = 
				institutionsService.find("where status = ? ", new Object[]{ConstsStatus.ACTIVE}, new int[]{Types.INTEGER});
		for(Institutions inst : instList) {
			Groups group = new Groups();
			group.setInstId(inst.getId());
		    List<Groups>  groupsList = queryDynamicGroups(group);
	        for(Groups g : groupsList) {
	            _logger.debug("role {}" , g);
	            refreshDynamicGroups(g);
	        }
		}
	}

}
