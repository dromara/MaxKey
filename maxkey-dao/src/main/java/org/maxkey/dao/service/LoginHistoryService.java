package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.LoginHistoryMapper;
import org.maxkey.domain.LoginHistory;
import org.springframework.stereotype.Service;

@Service
public class LoginHistoryService  extends JpaBaseService<LoginHistory>{

	public LoginHistoryService() {
		super(LoginHistoryMapper.class);
	}
	
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public LoginHistoryMapper getMapper() {
		// TODO Auto-generated method stub
		return (LoginHistoryMapper)super.getMapper();
	}
}
