package org.maxkey.dao.service;

import java.util.List;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.RoleNavMapper;
import org.maxkey.domain.Navigations;
import org.maxkey.domain.RoleNav;
import org.springframework.stereotype.Service;

@Service
public class RoleNavService extends JpaBaseService<RoleNav> {

	public RoleNavService() {
		super(RoleNavMapper.class);

	}

	
	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public RoleNavMapper getMapper() {
		// TODO Auto-generated method stub
		return (RoleNavMapper)super.getMapper();
	}

	
	public List<Navigations> queryNavs(String roleId){
		return getMapper().queryNavs(roleId);
	}
	
	
	public boolean delete(String roleId) {
		return  getMapper().deleteRoleNav(roleId) > 0;
	}

	public boolean insert(List<RoleNav> listRoleNav) {
		return getMapper().insertRoleNav(listRoleNav)>0;
	}
}
