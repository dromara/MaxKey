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

import java.util.List;

import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.entity.permissions.RoleMember;
import org.dromara.maxkey.entity.permissions.Roles;
import org.dromara.maxkey.persistence.mapper.RoleMemberMapper;
import org.dromara.maxkey.persistence.service.RoleMemberService;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RoleMemberServiceImpl  extends JpaServiceImpl<RoleMemberMapper,RoleMember> implements RoleMemberService{
	static final  Logger _logger = LoggerFactory.getLogger(RoleMemberServiceImpl.class);

	public int addDynamicRoleMember(Roles dynamicGroup) {
	    return getMapper().addDynamicRoleMember(dynamicGroup);
	}
	
	public int deleteDynamicRoleMember(Roles dynamicGroup) {
	    return getMapper().deleteDynamicRoleMember(dynamicGroup);
	}
	
	public int deleteByRoleId(String groupId) {
        return getMapper().deleteByRoleId(groupId);
    }
	
	public List<UserInfo> queryMemberByRoleId(String groupId){
		return getMapper().queryMemberByRoleId(groupId);
	}
	
	
	public JpaPageResults<Roles> rolesNoMember(RoleMember entity) {
		beforePageResults(entity);
		List<Roles> resultslist = null;
		try {
			resultslist = getMapper().rolesNoMember(entity);
		} catch (Exception e) {
			_logger.error("fetchPageResults Exception " , e);
		}
		//当前页记录数
		Integer records = parseRecords(resultslist);
		//总页数
		Integer totalCount =fetchCount(entity, resultslist);
		return new JpaPageResults<Roles>(entity.getPageNumber(),entity.getPageSize(),records,totalCount,resultslist);
	}
	
}
