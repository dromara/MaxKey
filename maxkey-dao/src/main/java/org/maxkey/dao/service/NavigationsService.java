package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.NavigationsMapper;
import org.maxkey.domain.Navigations;
import org.springframework.stereotype.Service;


@Service
public class NavigationsService  extends JpaBaseService<Navigations>{

	public NavigationsService() {
		super(NavigationsMapper.class);
		
	}
	
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public NavigationsMapper getMapper() {
		// TODO Auto-generated method stub
		return (NavigationsMapper)super.getMapper();
	}

}
