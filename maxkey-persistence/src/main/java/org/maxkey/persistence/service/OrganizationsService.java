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
 

package org.maxkey.persistence.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.domain.Organizations;
import org.maxkey.identity.kafka.KafkaIdentityAction;
import org.maxkey.identity.kafka.KafkaIdentityTopic;
import org.maxkey.identity.kafka.KafkaProvisioningService;
import org.maxkey.persistence.mapper.OrganizationsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationsService  extends JpaBaseService<Organizations>{

    @Autowired
    KafkaProvisioningService kafkaProvisioningService;
    
	public OrganizationsService() {
		super(OrganizationsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public OrganizationsMapper getMapper() {
		// TODO Auto-generated method stub
		return (OrganizationsMapper)super.getMapper();
	}
	
	 public boolean insert(Organizations organization) {
	     if(super.insert(organization)){
             kafkaProvisioningService.send(
                     KafkaIdentityTopic.ORG_TOPIC, organization, KafkaIdentityAction.CREATE_ACTION);
             return true;
         }
         return false;
	 }
	 
	 public boolean update(Organizations organization) {
	     if(super.update(organization)){
             kafkaProvisioningService.send(
                     KafkaIdentityTopic.ORG_TOPIC, organization, KafkaIdentityAction.UPDATE_ACTION);
             return true;
         }
         return false;
     }
 
	 public boolean delete(Organizations organization) {
	     if(super.delete(organization)){
             kafkaProvisioningService.send(
                     KafkaIdentityTopic.ORG_TOPIC, organization, KafkaIdentityAction.DELETE_ACTION);
             return true;
         }
         return false;
	 }
	
}
