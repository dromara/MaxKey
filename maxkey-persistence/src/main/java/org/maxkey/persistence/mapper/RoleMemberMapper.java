/**
 * 
 */
package org.maxkey.persistence.mapper;

import java.util.List;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.RoleMember;

/**
 * @author Crystal.sea
 *
 */

public  interface RoleMemberMapper extends IJpaBaseMapper<RoleMember> {
	
	public List<RoleMember> allMemberInRole(RoleMember entity);
	public List<RoleMember> memberInRole(RoleMember entity);
	public List<RoleMember> memberNotInRole(RoleMember entity);
	public List<RoleMember> roleMemberInRole(RoleMember entity);
}
