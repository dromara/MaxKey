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
 

package org.dromara.maxkey.persistence.service.impl;

import org.dromara.maxkey.entity.OrganizationsCast;
import org.dromara.maxkey.persistence.mapper.OrganizationsCastMapper;
import org.dromara.maxkey.persistence.service.OrganizationsCastService;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class OrganizationsCastServiceImpl  extends JpaServiceImpl<OrganizationsCastMapper,OrganizationsCast> implements OrganizationsCastService{
    static final  Logger _logger = LoggerFactory.getLogger(OrganizationsCastServiceImpl.class);


    public boolean updateCast(OrganizationsCast organizationsCast) {
        return getMapper().updateCast(organizationsCast) > 0;
    }

}
