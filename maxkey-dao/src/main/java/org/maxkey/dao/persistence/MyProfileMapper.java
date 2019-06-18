package org.maxkey.dao.persistence;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.UserInfo;


/**
 * @author Crystal.Sea
 *
 */
public interface MyProfileMapper  extends IJpaBaseMapper<UserInfo>{
	
	public int updateProfile(UserInfo userInfo);
	
}
