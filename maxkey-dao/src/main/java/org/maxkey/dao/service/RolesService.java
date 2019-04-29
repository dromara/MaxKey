package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.RolesMapper;
import org.maxkey.domain.Roles;
import org.springframework.stereotype.Service;

@Service
public class RolesService extends JpaBaseService<Roles> {

	public RolesService() {
		super(RolesMapper.class);

	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public RolesMapper getMapper() {
		// TODO Auto-generated method stub
		return (RolesMapper)super.getMapper();
	}
	


}
