package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.ApplicationsMapper;
import org.maxkey.domain.apps.Applications;
import org.springframework.stereotype.Service;

@Service
public class ApplicationsService  extends JpaBaseService<Applications>{

	public ApplicationsService() {
		super(ApplicationsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public ApplicationsMapper getMapper() {
		// TODO Auto-generated method stub
		return (ApplicationsMapper)super.getMapper();
	}
	
}
