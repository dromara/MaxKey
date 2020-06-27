package org.maxkey.persistence.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.domain.apps.AppsDesktopDetails;
import org.maxkey.persistence.mapper.AppsDesktopDetailsMapper;
import org.springframework.stereotype.Service;

@Service
public class AppsDesktopDetailsService  extends JpaBaseService<AppsDesktopDetails>{

	public AppsDesktopDetailsService() {
		super(AppsDesktopDetailsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public AppsDesktopDetailsMapper getMapper() {
		// TODO Auto-generated method stub
		return (AppsDesktopDetailsMapper)super.getMapper();
	}
	public  AppsDesktopDetails  getAppDetails(String id) {
		return getMapper().getAppDetails(id);
	}
}
