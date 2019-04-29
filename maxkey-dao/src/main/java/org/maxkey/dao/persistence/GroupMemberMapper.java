/**
 * 
 */
package org.maxkey.dao.persistence;

import java.util.List;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.GroupMember;
import org.maxkey.domain.Groups;
import org.maxkey.domain.UserInfo;

/**
 * @author Crystal.sea
 *
 */

public  interface GroupMemberMapper extends IJpaBaseMapper<GroupMember> {
	
	public List<UserInfo> gridUserMemberInGroup(GroupMember entity);
	
	public Integer  countUserMemberInGroup(GroupMember entity);
	
	public List<UserInfo> gridAllUserMemberInGroup(GroupMember entity);
	
	public Integer  countAllUserMemberInGroup(GroupMember entity);
	
	public List<UserInfo> gridUserMemberNotInGroup(GroupMember entity);
	
	public Integer  countUserMemberNotInGroup(GroupMember entity);	
	
	public List<Groups> gridGroupMemberInGroup(GroupMember entity);
	
	public Integer  countGroupMemberInGroup(GroupMember entity);
	
	

}
