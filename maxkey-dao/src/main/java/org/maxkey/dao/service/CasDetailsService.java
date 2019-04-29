package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.CasDetailsMapper;
import org.maxkey.domain.apps.CasDetails;
import org.springframework.stereotype.Service;

@Service
public class CasDetailsService  extends JpaBaseService<CasDetails>{

	public CasDetailsService() {
		super(CasDetailsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public CasDetailsMapper getMapper() {
		// TODO Auto-generated method stub
		return (CasDetailsMapper)super.getMapper();
	}
	
}
