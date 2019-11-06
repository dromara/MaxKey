/**
 * 
 */
package org.maxkey.dao.persistence;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.apps.Apps;

/**
 * @author Crystal.sea
 *
 */
public  interface AppsMapper extends IJpaBaseMapper<Apps> {
	
	public int insertApp(Apps app);
	
	public int updateApp(Apps app);
}
