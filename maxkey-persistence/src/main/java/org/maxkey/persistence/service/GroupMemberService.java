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
import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.entity.GroupMember;
import org.maxkey.entity.Groups;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.mapper.GroupMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GroupMemberService  extends JpaBaseService<GroupMember>{
	final static Logger _logger = LoggerFactory.getLogger(GroupMemberService.class);
	
	public GroupMemberService() {
		super(GroupMemberMapper.class);
	}
	
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public GroupMemberMapper getMapper() {
		return (GroupMemberMapper)super.getMapper();
	}
	
	public int addDynamicGroupMember(Groups dynamicGroup) {
	    return getMapper().addDynamicGroupMember(dynamicGroup);
	}
	
	public int deleteDynamicGroupMember(Groups dynamicGroup) {
	    return getMapper().deleteDynamicGroupMember(dynamicGroup);
	}
	
	public int deleteByGroupId(String groupId) {
        return getMapper().deleteByGroupId(groupId);
    }
	
	public List<UserInfo> queryMemberByGroupId(String groupId){
		return getMapper().queryMemberByGroupId(groupId);
	}
	
	
	public JpaPageResults<Groups> groupsNoMember(GroupMember entity) {
		entity.setPageResultSelectUUID(entity.generateId());
		entity.setStartRow(calculateStartRow(entity.getPageNumber() ,entity.getPageSize()));
		
		entity.setPageable(true);
		List<Groups> resultslist = null;
		try {
			resultslist = getMapper().groupsNoMember(entity);
		} catch (Exception e) {
			_logger.error("queryPageResults Exception " , e);
		}
		entity.setPageable(false);
		Integer totalPage = resultslist.size();
		
		Integer totalCount = 0;
		if(entity.getPageNumber() == 1 && totalPage < entity.getPageSize()) {
			totalCount = totalPage;
		}else {
			totalCount = parseCount(getMapper().queryPageResultsCount(entity));
		}
		
		return new JpaPageResults<Groups>(entity.getPageNumber(),entity.getPageSize(),totalPage,totalCount,resultslist);
	}
	
}
