package org.maxkey.dao.service;

import java.util.List;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.MyAppsListMapper;
import org.maxkey.domain.apps.UserApplications;
import org.springframework.stereotype.Service;

@Service
public class MyAppsListService  extends JpaBaseService<UserApplications>{

	public MyAppsListService() {
		super(MyAppsListMapper.class);
	}

	public List<UserApplications> queryMyApps(UserApplications userApplications){
		return getMapper().queryMyApps(userApplications);
	}
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public MyAppsListMapper getMapper() {
		// TODO Auto-generated method stub
		return (MyAppsListMapper)super.getMapper();
	}
	
}
