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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.constants.ConstsStatus;
import org.maxkey.entity.Groups;
import org.maxkey.persistence.mapper.GroupsMapper;
import org.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Repository
public class GroupsService  extends JpaBaseService<Groups> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4156671926199393550L;
    
    final static Logger _logger = LoggerFactory.getLogger(GroupsService.class);
    @JsonIgnore
    @Autowired
    @Qualifier("groupMemberService")
    GroupMemberService groupMemberService;
    
	public GroupsService() {
		super(GroupsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public GroupsMapper getMapper() {
		return (GroupsMapper)super.getMapper();
	}
	
	
	public List<Groups> queryDynamicGroups(Groups groups){
	    return this.getMapper().queryDynamicGroups(groups);
	}
	
	public boolean deleteById(String groupId) {
	    this.remove(groupId);
	    groupMemberService.deleteByGroupId(groupId);
	    return true;
	}
	
	public List<Groups> queryGroupByUserId(String userId){
		return this.getMapper().queryGroupByUserId(userId);
	}
	
	public void refreshDynamicGroups(Groups dynamicGroup){
	    if(dynamicGroup.getDynamic().equals(ConstsStatus.ACTIVE+"")) {
	        boolean isDynamicTimeSupport = false;
	        boolean isBetweenEffectiveTime = false;
	        if(StringUtils.isNotBlank(dynamicGroup.getResumeTime())
	                &&StringUtils.isNotBlank(dynamicGroup.getSuspendTime())) {
	            LocalTime currentTime = LocalDateTime.now().toLocalTime();
	            LocalTime resumeTime = LocalTime.parse(dynamicGroup.getResumeTime());
	            LocalTime suspendTime = LocalTime.parse(dynamicGroup.getSuspendTime());
	            
	            _logger.info("currentTime: " + currentTime 
                        + " , resumeTime : " + resumeTime 
                        + " , suspendTime: " + suspendTime);
	            isDynamicTimeSupport = true;
	            
	            if(resumeTime.isBefore(currentTime) && currentTime.isBefore(suspendTime)) {
	                isBetweenEffectiveTime = true;
	            }
	            
	        }
	        
    	    if(StringUtils.isNotBlank(dynamicGroup.getOrgIdsList())) {
    	        dynamicGroup.setOrgIdsList("'"+dynamicGroup.getOrgIdsList().replace(",", "','")+"'");
    	    }
    	    String filters = dynamicGroup.getFilters();
    	    if(StringUtils.filtersSQLInjection(filters.toLowerCase())) {  
    	        _logger.info("filters include SQL Injection Attack Risk.");
    	        return;
    	    }
    	    
    	    filters = filters.replace("&", " AND ");
    	    filters = filters.replace("|", " OR ");
    	    
    	    dynamicGroup.setFilters(filters);
    	    
    	    if(isDynamicTimeSupport) {
    	        if(isBetweenEffectiveTime) {
    	            groupMemberService.deleteDynamicGroupMember(dynamicGroup);
                    groupMemberService.addDynamicGroupMember(dynamicGroup);
    	        }else {
    	            groupMemberService.deleteDynamicGroupMember(dynamicGroup);
    	        }
    	    }else{
                groupMemberService.deleteDynamicGroupMember(dynamicGroup);
                groupMemberService.addDynamicGroupMember(dynamicGroup);
            }
	    }
    }
	
	public void refreshAllDynamicGroups(){
	    List<Groups>  groupsList = queryDynamicGroups(null);
        for(Groups group : groupsList) {
            _logger.debug("group " + group);
            refreshDynamicGroups(group);
        }
	}

    public GroupMemberService getGroupMemberService() {
        return groupMemberService;
    }

    public void setGroupMemberService(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }
	

	
}
