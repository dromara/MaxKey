/**
 * 
 */
package org.maxkey.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.UserApps;

/**
 * @author Crystal.sea
 *
 */
public  interface AppsMapper extends IJpaBaseMapper<Apps> {
	
	public int insertApp(Apps app);
	
	public int updateApp(Apps app);
	
	@Update("UPDATE APPS SET ISEXTENDATTR=#{isExtendAttr},	EXTENDATTR=#{extendAttr} WHERE id = #{id}")
	public int updateExtendAttr(Apps app);  
	

    public List<UserApps> queryMyApps(UserApps userApplications);
}
