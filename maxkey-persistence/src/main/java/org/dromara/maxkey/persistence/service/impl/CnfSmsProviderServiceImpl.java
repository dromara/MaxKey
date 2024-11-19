/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

import org.dromara.maxkey.entity.cnf.CnfSmsProvider;
import org.dromara.maxkey.persistence.mapper.CnfSmsProviderMapper;
import org.dromara.maxkey.persistence.service.CnfSmsProviderService;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.springframework.stereotype.Repository;


@Repository
public class CnfSmsProviderServiceImpl  extends JpaServiceImpl<CnfSmsProviderMapper,CnfSmsProvider> implements CnfSmsProviderService{
 
}
