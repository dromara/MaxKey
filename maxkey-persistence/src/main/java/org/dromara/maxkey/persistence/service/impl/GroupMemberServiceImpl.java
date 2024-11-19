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

import org.dromara.maxkey.entity.idm.GroupMember;
import org.dromara.maxkey.entity.idm.Groups;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.mapper.GroupMemberMapper;
import org.dromara.maxkey.persistence.service.GroupMemberService;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GroupMemberServiceImpl  extends JpaServiceImpl<GroupMemberMapper,GroupMember> implements GroupMemberService{
	static final  Logger _logger = LoggerFactory.getLogger(GroupMemberServiceImpl.class);

	public int addDynamicMember(Groups dynamicGroup) {
	    return getMapper().addDynamicMember(dynamicGroup);
	}
	
	public int deleteDynamicMember(Groups dynamicGroup) {
	    return getMapper().deleteDynamicMember(dynamicGroup);
	}
	
	public int deleteByGroupId(String groupId) {
        return getMapper().deleteByGroupId(groupId);
    }
	
	public List<UserInfo> queryMemberByGroupId(String groupId){
		return getMapper().queryMemberByGroupId(groupId);
	}
	
	
	public JpaPageResults<Groups> noMember(GroupMember entity) {
		beforePageResults(entity);
		List<Groups> resultslist = null;
		try {
			resultslist = getMapper().noMember(entity);
		} catch (Exception e) {
			_logger.error("queryPageResults Exception " , e);
		}
		//当前页记录数
		Integer records = parseRecords(resultslist);
		//总页数
		Integer totalCount =fetchCount(entity, resultslist);
		return new JpaPageResults<Groups>(entity.getPageNumber(),entity.getPageSize(),records,totalCount,resultslist);
	}
	
}
