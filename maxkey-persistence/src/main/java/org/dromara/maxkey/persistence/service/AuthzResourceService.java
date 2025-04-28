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
 



package org.dromara.maxkey.persistence.service;

import java.util.List;
import java.util.Set;

import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.authz.QueryAppResourceDto;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.entity.permissions.Resources;
import org.dromara.mybatis.jpa.IJpaService;

public interface AuthzResourceService   extends IJpaService<UserInfo>{
	
	/**
	 * 根据主体获取用户对应得应用资源清单
	 * @param user
	 * @param app 
	 * @return 资源清单列表
	 */
    public Set<Resources> getResourcesBySubject(UserInfo user,Apps  app);
   
	/**
	 * 根据组列表获取资源清单
	 * @param dto
	 * @return
	 */
	public List<Resources> queryResourcesByGroupId(QueryAppResourceDto dto) ;

	/**
	 * 根据角色列表获取资源清单
	 * @param dto
	 * @return
	 */
	public List<Resources> queryResourcesByRoleId(QueryAppResourceDto dto) ;
}
