package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.IpAddrFilterMapper;
import org.maxkey.domain.IpAddrFilter;
import org.springframework.stereotype.Service;

@Service
public class IpAddrFilterService  extends JpaBaseService<IpAddrFilter>{

	public IpAddrFilterService() {
		super(IpAddrFilterMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public IpAddrFilterMapper getMapper() {
		// TODO Auto-generated method stub
		return (IpAddrFilterMapper)super.getMapper();
	}
	
}
