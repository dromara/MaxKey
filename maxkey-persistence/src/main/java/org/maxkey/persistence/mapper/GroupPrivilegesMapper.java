/**
 * 
 */
package org.maxkey.persistence.mapper;

import java.util.List;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.GroupPrivileges;

/**
 * @author Crystal.sea
 *
 */

public  interface GroupPrivilegesMapper extends IJpaBaseMapper<GroupPrivileges> {
	
	public List<GroupPrivileges>appsInGroup(GroupPrivileges entity);
	
	
	public List<GroupPrivileges> appsNotInGroup(GroupPrivileges entity);
	

}
