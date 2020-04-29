/**
 * 
 */
package org.maxkey.dao.persistence;

import java.util.List;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.RolePermissions;
import org.maxkey.domain.Roles;

/**
 * @author Crystal.sea
 *
 */

public  interface RolesMapper extends IJpaBaseMapper<Roles> {
    
    public int insertRolePermissions(List<RolePermissions> rolePermissionsList);
    
    public int logisticDeleteRolePermissions(List<RolePermissions> rolePermissionsList);
        
    public List<RolePermissions> queryRolePermissions(RolePermissions rolePermissions);
}
