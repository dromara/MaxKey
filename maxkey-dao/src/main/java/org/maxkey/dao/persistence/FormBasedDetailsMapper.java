/**
 * 
 */
package org.maxkey.dao.persistence;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.apps.FormBasedDetails;

/**
 * @author Crystal.sea
 *
 */
public  interface FormBasedDetailsMapper extends IJpaBaseMapper<FormBasedDetails> {
	
	public FormBasedDetails getSassTemplet(String id);
}
