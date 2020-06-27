package org.maxkey.persistence.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.domain.apps.AppsCasDetails;
import org.maxkey.persistence.mapper.AppsCasDetailsMapper;
import org.springframework.stereotype.Service;

@Service
public class AppsCasDetailsService  extends JpaBaseService<AppsCasDetails>{

	public AppsCasDetailsService() {
		super(AppsCasDetailsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public AppsCasDetailsMapper getMapper() {
		// TODO Auto-generated method stub
		return (AppsCasDetailsMapper)super.getMapper();
	}
	
	public  AppsCasDetails  getAppDetails(String id) {
		return getMapper().getAppDetails(id);
	}
}
