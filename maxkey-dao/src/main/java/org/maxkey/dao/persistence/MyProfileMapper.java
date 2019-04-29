package org.maxkey.dao.persistence;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.UserInfo;


/**
 * @author Crystal.Sea
 *
 */
public interface MyProfileMapper  extends IJpaBaseMapper<UserInfo>{
	
	public int updateBasic(UserInfo userInfo);
	
	public int updateHome(UserInfo userInfo);
	
	public int updateExtra(UserInfo userInfo);
	
	public int updateCompany(UserInfo userInfo);
	
}
