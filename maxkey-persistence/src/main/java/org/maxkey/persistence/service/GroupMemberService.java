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
import org.maxkey.entity.GroupMember;
import org.maxkey.entity.Groups;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.mapper.GroupMemberMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GroupMemberService  extends JpaBaseService<GroupMember>{
	
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
	
}
