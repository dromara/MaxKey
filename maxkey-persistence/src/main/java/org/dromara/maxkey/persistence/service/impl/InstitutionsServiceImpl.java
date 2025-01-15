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
 

package org.dromara.maxkey.persistence.service.impl;

import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.entity.Institutions;
import org.dromara.maxkey.persistence.mapper.InstitutionsMapper;
import org.dromara.maxkey.persistence.service.InstitutionsService;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


@Repository
public class InstitutionsServiceImpl  extends JpaServiceImpl<InstitutionsMapper,Institutions> implements InstitutionsService{
	 static final Logger _logger = LoggerFactory.getLogger(InstitutionsServiceImpl.class);
	 
    private static final String DEFAULT_INSTID = "1";

    protected static final Cache<String, Institutions> institutionsStore =
            Caffeine.newBuilder()
                	.expireAfterWrite(60, TimeUnit.MINUTES)
                	.build();
    
	 public Institutions findByDomain(String domain) {
		 return getMapper().findByDomain(domain);
	 }
	 
	 public Institutions get(String instIdOrDomain) {
	        _logger.trace(" instId {}" , instIdOrDomain);
	        Institutions inst = getByDomain(instIdOrDomain);
	        if(inst == null) {//use default inst
	        	inst = getByDomain(DEFAULT_INSTID);
	        	institutionsStore.put(instIdOrDomain, inst);
	        }
	        return inst;
	    }

	    private Institutions getByDomain(String instIdOrDomain) {
	        _logger.trace(" instId {}" , instIdOrDomain);
	        Institutions inst = institutionsStore.getIfPresent(instIdOrDomain);
	        if(inst == null) {
		        Institutions institution = findByDomain(instIdOrDomain);
		        if(institution != null ) {
		        	inst = institution;
			        institutionsStore.put(inst.getDomain(), inst);
			        institutionsStore.put(inst.getConsoleDomain(), inst);
			        institutionsStore.put(inst.getId(), inst);
		        }
	        }

	        return inst;
	    }
	 
}
