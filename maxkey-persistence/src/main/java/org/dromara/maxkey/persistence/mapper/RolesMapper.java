package org.dromara.maxkey.persistence.mapper;

import java.util.List;

import org.dromara.maxkey.entity.permissions.Roles;
import org.dromara.mybatis.jpa.IJpaMapper;

/**
 * @author Crystal.sea
 *
 */

public  interface RolesMapper extends IJpaMapper<Roles> {

    public List<Roles> queryDynamicRoles(Roles groups);
    
    public List<Roles> queryRolesByUserId(String userId);
}
