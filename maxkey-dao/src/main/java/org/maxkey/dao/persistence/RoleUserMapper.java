/**
 * 
 */
package org.maxkey.dao.persistence;

import java.util.List;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.RoleUser;
import org.maxkey.domain.UserInfo;


/**
 * @author Crystal.Sea
 *
 */

public  interface RoleUserMapper extends IJpaBaseMapper<RoleUser> {
	
	public List<UserInfo> gridUserInfoInRole(RoleUser roleUser);
	
	public Integer countUserInfoInRole(RoleUser roleUser);
	
	public List<UserInfo> gridAllUserInfoInRole(RoleUser roleUser);
	
	public Integer countAllUserInfoInRole(RoleUser roleUser);
	
	public List<UserInfo> gridUserInfoNotInRole(RoleUser roleUser);
	
	public Integer countUserInfoNotInRole(RoleUser roleUser);
}
