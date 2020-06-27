package org.maxkey.persistence.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.domain.apps.AppsFormBasedDetails;
import org.maxkey.persistence.mapper.AppsFormBasedDetailsMapper;
import org.springframework.stereotype.Service;

@Service
public class AppsFormBasedDetailsService  extends JpaBaseService<AppsFormBasedDetails>{

	public AppsFormBasedDetailsService() {
		super(AppsFormBasedDetailsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public AppsFormBasedDetailsMapper getMapper() {
		return (AppsFormBasedDetailsMapper)super.getMapper();
	}
	
	public  AppsFormBasedDetails  getAppDetails(String id) {
		return getMapper().getAppDetails(id);
	}
}
