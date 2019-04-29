package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.DesktopDetailsMapper;
import org.maxkey.domain.apps.DesktopDetails;
import org.springframework.stereotype.Service;

@Service
public class DesktopDetailsService  extends JpaBaseService<DesktopDetails>{

	public DesktopDetailsService() {
		super(DesktopDetailsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public DesktopDetailsMapper getMapper() {
		// TODO Auto-generated method stub
		return (DesktopDetailsMapper)super.getMapper();
	}
	
}
