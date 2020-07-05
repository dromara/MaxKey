/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

/**
 * 
 */
package org.maxkey.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.UserApps;

/**
 * @author Crystal.sea
 *
 */
public  interface AppsMapper extends IJpaBaseMapper<Apps> {
	
	public int insertApp(Apps app);
	
	public int updateApp(Apps app);
	
	@Update("UPDATE APPS SET ISEXTENDATTR=#{isExtendAttr},	EXTENDATTR=#{extendAttr} WHERE id = #{id}")
	public int updateExtendAttr(Apps app);  
	

    public List<UserApps> queryMyApps(UserApps userApplications);
}
