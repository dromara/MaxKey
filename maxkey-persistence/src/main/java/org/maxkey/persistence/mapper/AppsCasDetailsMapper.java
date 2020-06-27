/**
 * 
 */
package org.maxkey.persistence.mapper;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.apps.AppsCasDetails;

/**
 * @author Crystal.sea
 *
 */
public  interface AppsCasDetailsMapper extends IJpaBaseMapper<AppsCasDetails> {
	
	public  AppsCasDetails  getAppDetails(String id) ;
}
