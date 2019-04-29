/**
 * 
 */
package org.maxkey.dao.persistence;

import java.util.List;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.GroupPrivileges;
import org.maxkey.domain.apps.Applications;

/**
 * @author Crystal.sea
 *
 */

public  interface GroupPrivilegesMapper extends IJpaBaseMapper<GroupPrivileges> {
	
	public List<Applications> gridAppsInGroup(GroupPrivileges entity);
	
	public Integer  countAppsInGroup(GroupPrivileges entity);
	
	public List<Applications> gridAppsNotInGroup(GroupPrivileges entity);
	
	public Integer  countAppsNotInGroup(GroupPrivileges entity);

}
