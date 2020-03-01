/**
 * 
 */
package org.maxkey.dao.persistence;

import org.apache.ibatis.annotations.Update;
import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.apps.Apps;

/**
 * @author Crystal.sea
 *
 */
public  interface AppsMapper extends IJpaBaseMapper<Apps> {
	
	public int insertApp(Apps app);
	
	public int updateApp(Apps app);
	
	@Update("UPDATE APPS SET ISEXTENDATTR=#{isExtendAttr},	EXTENDATTR=#{extendAttr} WHERE id = #{id}")
	public int updateExtendAttr(Apps app);  
}
