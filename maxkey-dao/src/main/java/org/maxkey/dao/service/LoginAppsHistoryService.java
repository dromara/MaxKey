package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.LoginAppsHistoryMapper;
import org.maxkey.domain.LoginAppsHistory;
import org.springframework.stereotype.Service;

@Service
public class LoginAppsHistoryService  extends JpaBaseService<LoginAppsHistory>{

	public LoginAppsHistoryService() {
		super(LoginAppsHistoryMapper.class);
	}


	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public LoginAppsHistoryMapper getMapper() {
		// TODO Auto-generated method stub
		return (LoginAppsHistoryMapper)super.getMapper();
	}
	
	public boolean  insert(LoginAppsHistory loginAppsHistory){
		return getMapper().insert(loginAppsHistory)> 0;
	}
}
