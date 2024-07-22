package org.dromara.maxkey.persistence.mapper;

import java.util.List;

import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.entity.permissions.RoleMember;
import org.dromara.maxkey.entity.permissions.Roles;
import org.dromara.mybatis.jpa.IJpaMapper;

/**
 * @author Crystal.sea
 *
 */

public  interface RoleMemberMapper extends IJpaMapper<RoleMember> {
	
	public List<RoleMember> memberInRole(RoleMember entity);
	
	public List<RoleMember> memberNotInRole(RoleMember entity);
	
	public List<RoleMember> memberPostNotInRole(RoleMember entity);
	
	public List<Roles> rolesNoMember(RoleMember entity);
	
	public int addDynamicRoleMember(Roles dynamicRole);
	
	public int deleteDynamicRoleMember(Roles dynamicRole);
	
	public int deleteByRoleId(String roleId);
	
	public List<UserInfo> queryMemberByRoleId(String roleId);
	
	
	
}
