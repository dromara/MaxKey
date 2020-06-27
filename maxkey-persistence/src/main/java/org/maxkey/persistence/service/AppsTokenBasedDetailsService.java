package org.maxkey.persistence.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.domain.apps.AppsTokenBasedDetails;
import org.maxkey.persistence.mapper.AppsTokenBasedDetailsMapper;
import org.springframework.stereotype.Service;

@Service
public class AppsTokenBasedDetailsService  extends JpaBaseService<AppsTokenBasedDetails>{

	public AppsTokenBasedDetailsService() {
		super(AppsTokenBasedDetailsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public AppsTokenBasedDetailsMapper getMapper() {
		// TODO Auto-generated method stub
		return (AppsTokenBasedDetailsMapper)super.getMapper();
	}
	
	public  AppsTokenBasedDetails  getAppDetails(String id) {
		return getMapper().getAppDetails(id);
	}
}
