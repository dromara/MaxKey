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
import org.maxkey.entity.Accounts;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.kafka.KafkaIdentityAction;
import org.maxkey.persistence.kafka.KafkaIdentityTopic;
import org.maxkey.persistence.kafka.KafkaPersistService;
import org.maxkey.persistence.mapper.AccountsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsService  extends JpaBaseService<Accounts>{

    @Autowired
    KafkaPersistService kafkaPersistService;
    
    @Autowired
    UserInfoService  userInfoService;
    
	public AccountsService() {
		super(AccountsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public AccountsMapper getMapper() {
		// TODO Auto-generated method stub
		return (AccountsMapper)super.getMapper();
	}
	
	
	 public boolean insert(Accounts account) {
	     if (super.insert(account)) {
	            if(kafkaPersistService.getApplicationConfig().isKafkaSupport()) {
	                UserInfo loadUserInfo = userInfoService.loadUserRelated(account.getUid());
	                account.setUserInfo(loadUserInfo);
	                kafkaPersistService.send(
	                        KafkaIdentityTopic.ACCOUNT_TOPIC, 
	                        account,
	                        KafkaIdentityAction.CREATE_ACTION);
	            }
	            
	            return true;
	        }
	     return false;
	 }
	 
   public boolean update(Accounts account) {
         if (super.update(account)) {
                if(kafkaPersistService.getApplicationConfig().isKafkaSupport()) {
                    UserInfo loadUserInfo = userInfoService.loadUserRelated(account.getUid());
                    account.setUserInfo(loadUserInfo);
                    kafkaPersistService.send(
                            KafkaIdentityTopic.ACCOUNT_TOPIC, 
                            account,
                            KafkaIdentityAction.UPDATE_ACTION);
                }
                
                return true;
            }
         return false;
     }
   
   public boolean remove(String id) {
       Accounts account = this.get(id);
       if (super.remove(id)) {
              UserInfo loadUserInfo = null;
              if(kafkaPersistService.getApplicationConfig().isKafkaSupport()) {
                  loadUserInfo = userInfoService.loadUserRelated(account.getUid());
                  account.setUserInfo(loadUserInfo);
                  kafkaPersistService.send(
                          KafkaIdentityTopic.ACCOUNT_TOPIC, 
                          account,
                          KafkaIdentityAction.DELETE_ACTION);
              }
              
              return true;
          }
       return false;
   }
	
}
