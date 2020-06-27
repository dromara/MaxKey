package org.maxkey.persistence.service;

import java.util.List;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.domain.Registration;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.mapper.RegistrationMapper;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService  extends JpaBaseService<Registration>{

	public RegistrationService() {
		super(RegistrationMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public RegistrationMapper getMapper() {
		// TODO Auto-generated method stub
		return (RegistrationMapper)super.getMapper();
	}
	
	
	public UserInfo queryUserInfoByEmail(String email){
		List<UserInfo> listUserInfo=getMapper().queryUserInfoByEmail(email);
		return listUserInfo.size()>0?listUserInfo.get(0):null;
	}
	
}
