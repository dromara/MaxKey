package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.ForgotPasswordMapper;
import org.maxkey.domain.ForgotPassword;
import org.maxkey.domain.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService  extends JpaBaseService<ForgotPassword>{

	public ForgotPasswordService() {
		super(ForgotPasswordMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public ForgotPasswordMapper getMapper() {

		return (ForgotPasswordMapper)super.getMapper();
	}

	 
	public UserInfo queryUserInfoByEmail(String email){
		return getMapper().queryUserInfoByEmail(email);
	}
	
}
