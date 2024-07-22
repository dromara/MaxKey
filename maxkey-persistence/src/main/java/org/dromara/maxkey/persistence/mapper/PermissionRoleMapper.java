
package org.dromara.maxkey.persistence.mapper;

import java.util.List;

import org.dromara.maxkey.entity.permissions.PermissionRole;
import org.dromara.mybatis.jpa.IJpaMapper;

/**
 * @author Crystal.sea
 *
 */

public  interface PermissionRoleMapper extends IJpaMapper<PermissionRole> {
    
    public int insertPermissionRoles(List<PermissionRole> permissionRolesList);
    
    public int deletePermissionRoles(List<PermissionRole> permissionRolesList);
        
    public List<PermissionRole> queryPermissionRoles(PermissionRole permissionRole);

}
