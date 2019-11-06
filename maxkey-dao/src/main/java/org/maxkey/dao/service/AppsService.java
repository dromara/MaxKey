package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.AppsMapper;
import org.maxkey.domain.apps.Apps;
import org.springframework.stereotype.Service;

@Service
public class AppsService  extends JpaBaseService<Apps>{

	public AppsService() {
		super(AppsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public AppsMapper getMapper() {
		// TODO Auto-generated method stub
		return (AppsMapper)super.getMapper();
	}
	
	public boolean insertApp(Apps app) {
		return ((AppsMapper)super.getMapper()).insertApp(app)>0;
	};
	public boolean updateApp(Apps app) {
		return ((AppsMapper)super.getMapper()).updateApp(app)>0;
	};
	
}
