/**
 * 
 */
package org.maxkey.dao.persistence;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.apps.SAML20Details;

/**
 * @author Crystal.sea
 *
 */
public  interface Saml20DetailsMapper extends IJpaBaseMapper<SAML20Details> {
	
	public  SAML20Details  getSassTemplet(String id);
}
