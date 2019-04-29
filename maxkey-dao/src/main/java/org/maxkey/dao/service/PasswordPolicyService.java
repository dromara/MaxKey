package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.PasswordPolicyMapper;
import org.maxkey.domain.PasswordPolicy;
import org.springframework.stereotype.Service;

@Service
public class PasswordPolicyService  extends JpaBaseService<PasswordPolicy>{

	public PasswordPolicyService() {
		super(PasswordPolicyMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public PasswordPolicyMapper getMapper() {
		// TODO Auto-generated method stub
		return (PasswordPolicyMapper)super.getMapper();
	}
	
}
