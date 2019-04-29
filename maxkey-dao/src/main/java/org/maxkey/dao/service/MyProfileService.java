package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.constants.OPERATEACTION;
import org.maxkey.dao.persistence.MyProfileMapper;
import org.maxkey.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
	

	public int updateBasic(UserInfo userInfo){
		
		return getMapper().updateBasic(userInfo);
	}
	
	public int updateCompany(UserInfo userInfo){
		
		return getMapper().updateCompany(userInfo);
	}
	
	
	public int updateHome(UserInfo userInfo){
	
		return getMapper().updateHome(userInfo);
	}
	
	public int updateExtra(UserInfo userInfo){
		
		return getMapper().updateExtra(userInfo);
	}
	

	
}
