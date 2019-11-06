/**
 * 
 */
package org.maxkey.dao.persistence;

import java.util.List;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.apps.UserApps;

/**
 * @author Crystal.sea
 *
 */
public  interface MyAppsListMapper extends IJpaBaseMapper<UserApps> {
	
	public List<UserApps> queryMyApps(UserApps userApplications);
}
