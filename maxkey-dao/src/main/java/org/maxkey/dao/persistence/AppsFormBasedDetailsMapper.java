/**
 * 
 */
package org.maxkey.dao.persistence;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.apps.AppsFormBasedDetails;

/**
 * @author Crystal.sea
 *
 */
public  interface AppsFormBasedDetailsMapper extends IJpaBaseMapper<AppsFormBasedDetails> {
	
	public  AppsFormBasedDetails  getAppDetails(String id) ;
}
