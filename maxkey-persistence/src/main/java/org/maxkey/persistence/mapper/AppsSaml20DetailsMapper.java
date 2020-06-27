/**
 * 
 */
package org.maxkey.persistence.mapper;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.apps.AppsSAML20Details;

/**
 * @author Crystal.sea
 *
 */
public  interface AppsSaml20DetailsMapper extends IJpaBaseMapper<AppsSAML20Details> {
	
	public  AppsSAML20Details  getAppDetails(String id);
}
