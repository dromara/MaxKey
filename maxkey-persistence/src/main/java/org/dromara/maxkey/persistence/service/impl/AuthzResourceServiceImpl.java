/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 



package org.dromara.maxkey.persistence.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.authz.QueryAppResourceDto;
import org.dromara.maxkey.entity.authz.QueryGroupMembersDto;
import org.dromara.maxkey.entity.authz.QueryRoleMembersDto;
import org.dromara.maxkey.entity.idm.Groups;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.entity.permissions.Resources;
import org.dromara.maxkey.entity.permissions.Roles;
import org.dromara.maxkey.persistence.mapper.AuthzResourceMapper;
import org.dromara.maxkey.persistence.service.AuthzResourceService;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AuthzResourceServiceImpl   extends JpaServiceImpl<AuthzResourceMapper,UserInfo> implements AuthzResourceService{
	private static final Logger logger = LoggerFactory.getLogger(AuthzResourceServiceImpl.class);

	/**
	 * 根据主体获取用户对应得应用资源清单
	 * @param user
	 * @param app 
	 * @return 资源清单列表
	 */
    public Set<Resources> getResourcesBySubject(UserInfo user,Apps  app){
    	logger.debug("user {} , app {}",user,app);
    	Set<Resources> resourcesList = new HashSet<>();
    	
    	QueryAppResourceDto dto = new QueryAppResourceDto(user.getId(),app.getId());
    	
    	//查询用户的所属用户组
    	QueryGroupMembersDto queryGroupMembersDto = new QueryGroupMembersDto();
    	queryGroupMembersDto.add(user.getId());
    	List<Groups> listGroup = getMapper().queryGroupsByMembers(queryGroupMembersDto);
    	for(Groups group : listGroup) {
    		dto.getGroupIds().add(group.getId());
    	}
    	
    	//根据用户组获取应用资源
    	List<Resources> groupResourcesList = queryResourcesByGroupId(dto);
    	resourcesList.addAll(groupResourcesList);
    	
    	//查询用户的所属应用角色组
    	QueryRoleMembersDto queryRoleMembersDto = new QueryRoleMembersDto();
    	queryRoleMembersDto.setAppId(app.getId());
    	queryRoleMembersDto.add(user.getId());
    	List<Roles> listRoles = getMapper().queryRolesByMembers(queryRoleMembersDto);
    	for(Roles role : listRoles) {
    		dto.getRoleIds().add(role.getId());
    	}
    	//根据角色获取应用资源
    	List<Resources> roleResourcesList = queryResourcesByRoleId(dto);
    	resourcesList.addAll(roleResourcesList);

    	return resourcesList;
    }
 
	/**
	 * 根据组列表获取资源清单
	 * @param dto
	 * @return
	 */
	public List<Resources> queryResourcesByGroupId(QueryAppResourceDto dto) {
		return getMapper().queryResourcesByGroupId(dto);
	}

	/**
	 * 根据角色列表获取资源清单
	 * @param dto
	 * @return
	 */
	public List<Resources> queryResourcesByRoleId(QueryAppResourceDto dto) {
		return getMapper().queryResourcesByRoleId(dto);
	}
}
