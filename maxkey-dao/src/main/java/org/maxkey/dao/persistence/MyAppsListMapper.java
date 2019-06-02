/**
 * 
 */
package org.maxkey.dao.persistence;

import java.util.List;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.apps.UserApplications;

/**
 * @author Crystal.sea
 *
 */
public  interface MyAppsListMapper extends IJpaBaseMapper<UserApplications> {
	
	public List<UserApplications> queryMyApps(UserApplications userApplications);
}
