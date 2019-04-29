/**
 * 
 */
package org.maxkey.dao.persistence;

import java.util.List;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.Navigations;
import org.maxkey.domain.RoleNav;


/**
 * @author Crystal.Sea
 *
 */

public  interface RoleNavMapper extends IJpaBaseMapper<RoleNav> {
	
	public List<Navigations> queryNavs(String roleId);
	public int deleteRoleNav(String roleId);
	public int insertRoleNav(List<RoleNav> listRoleNav);
	
}
