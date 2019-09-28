package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.OrganizationsMapper;
import org.maxkey.domain.Organizations;
import org.springframework.stereotype.Service;

@Service
public class OrganizationsService  extends JpaBaseService<Organizations>{

	public OrganizationsService() {
		super(OrganizationsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public OrganizationsMapper getMapper() {
		// TODO Auto-generated method stub
		return (OrganizationsMapper)super.getMapper();
	}
	
}
