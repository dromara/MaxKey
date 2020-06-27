package org.maxkey.persistence.service;

import java.util.List;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.domain.RolePermissions;
import org.maxkey.domain.Roles;
import org.maxkey.persistence.mapper.RolesMapper;
import org.springframework.stereotype.Service;

@Service
public class RolesService  extends JpaBaseService<Roles>{
	
	public RolesService() {
		super(RolesMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public RolesMapper getMapper() {
		return (RolesMapper)super.getMapper();
	}
	
	public boolean insertRolePermissions(List<RolePermissions> rolePermissionsList) {
	    return getMapper().insertRolePermissions(rolePermissionsList)>0;
	};
    
	public boolean logisticDeleteRolePermissions(List<RolePermissions> rolePermissionsList) {
	     return getMapper().logisticDeleteRolePermissions(rolePermissionsList)>=0;
	 }
	
    public List<RolePermissions> queryRolePermissions(RolePermissions rolePermissions){
        return getMapper().queryRolePermissions(rolePermissions);
    }
}
