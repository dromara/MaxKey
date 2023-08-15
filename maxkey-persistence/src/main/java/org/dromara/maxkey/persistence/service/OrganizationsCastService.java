/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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

import org.dromara.maxkey.entity.OrganizationsCast;
import org.dromara.maxkey.persistence.mapper.OrganizationsCastMapper;
import org.dromara.mybatis.jpa.JpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class OrganizationsCastService  extends JpaService<OrganizationsCast>{

	final static Logger _logger = LoggerFactory.getLogger(OrganizationsCastService.class);

    
	public OrganizationsCastService() {
		super(OrganizationsCastMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public OrganizationsCastMapper getMapper() {
		return (OrganizationsCastMapper)super.getMapper();
	}

	public boolean updateCast(OrganizationsCast organizationsCast) {
		return getMapper().updateCast(organizationsCast) > 0;
	}

}
