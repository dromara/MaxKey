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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.domain.Groups;
import org.maxkey.persistence.mapper.GroupsMapper;
import org.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GroupsService  extends JpaBaseService<Groups>{
    final static Logger _logger = LoggerFactory.getLogger(GroupsService.class);
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
		// TODO Auto-generated method stub
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
	
	public void refreshDynamicGroups(Groups dynamicGroup){
	    if(dynamicGroup.getDynamic().equals("1")) {
	        boolean isDynamicTimeSupport = false;
	        boolean isBetweenEffectiveTime = false;
	        if(dynamicGroup.getResumeTime()!=null&&dynamicGroup.getResumeTime().equals("")
	                &&dynamicGroup.getSuspendTime()!=null&&dynamicGroup.getSuspendTime().equals("")) {
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
	        
    	    if(dynamicGroup.getOrgIdsList()!=null && !dynamicGroup.getOrgIdsList().equals("")) {
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
	

	
}
