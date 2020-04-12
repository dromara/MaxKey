package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.MyProfileMapper;
import org.maxkey.domain.UserInfo;
import org.springframework.stereotype.Service;


/**
 * @author Crystal.Sea
 *
 */
@Service
public class MyProfileService extends JpaBaseService<UserInfo> {
	

	public MyProfileService() {
		super(MyProfileMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public MyProfileMapper getMapper() {
		// TODO Auto-generated method stub
		return (MyProfileMapper)super.getMapper();
	}
	

	public int updateProfile(UserInfo userInfo){
		
		return getMapper().updateProfile(userInfo);
	}
	
}
