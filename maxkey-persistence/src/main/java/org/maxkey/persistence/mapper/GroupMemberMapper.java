/**
 * 
 */
package org.maxkey.persistence.mapper;

import java.util.List;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.GroupMember;

/**
 * @author Crystal.sea
 *
 */

public  interface GroupMemberMapper extends IJpaBaseMapper<GroupMember> {
	
	public List<GroupMember> allMemberInGroup(GroupMember entity);
	public List<GroupMember> memberInGroup(GroupMember entity);
	public List<GroupMember> memberNotInGroup(GroupMember entity);
	public List<GroupMember> groupMemberInGroup(GroupMember entity);
}
