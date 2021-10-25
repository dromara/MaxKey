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

import java.util.List;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.constants.ConstantsStatus;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.entity.Accounts;
import org.maxkey.entity.AccountsStrategy;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.kafka.KafkaIdentityAction;
import org.maxkey.persistence.kafka.KafkaIdentityTopic;
import org.maxkey.persistence.kafka.KafkaPersistService;
import org.maxkey.persistence.mapper.AccountsMapper;
import org.maxkey.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsService  extends JpaBaseService<Accounts>{

    @Autowired
    KafkaPersistService kafkaPersistService;
    
    @Autowired
    UserInfoService  userInfoService;
    
    @Autowired
    AccountsStrategyService accountsStrategyService;
    
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
	                UserInfo loadUserInfo = userInfoService.loadUserRelated(account.getUserId());
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
                    UserInfo loadUserInfo = userInfoService.loadUserRelated(account.getUserId());
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
                  loadUserInfo = userInfoService.loadUserRelated(account.getUserId());
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
   
   public void refreshByStrategy(AccountsStrategy strategy) {
       if(StringUtils.isNotBlank(strategy.getOrgIdsList())) {
           strategy.setOrgIdsList("'"+strategy.getOrgIdsList().replace(",", "','")+"'");
       }
       List<UserInfo>  userList = queryUserNotInStrategy(strategy);
       for(UserInfo user : userList) {
           Accounts account = new Accounts();
           account.setAppId(strategy.getAppId());
           account.setAppName(strategy.getAppName());
           
           account.setUserId(user.getId());
           account.setUsername(user.getUsername());
           account.setDisplayName(user.getDisplayName());
           
           if(strategy.getMapping().equalsIgnoreCase("username")) {
               account.setRelatedUsername(user.getUsername());
           }else if(strategy.getMapping().equalsIgnoreCase("mobile")) {
               account.setRelatedUsername(user.getMobile());
           }else if(strategy.getMapping().equalsIgnoreCase("email")) {
               account.setRelatedUsername(user.getEmail());
           }else if(strategy.getMapping().equalsIgnoreCase("employeeNumber")) {
               account.setRelatedUsername(user.getEmployeeNumber());
           }else if(strategy.getMapping().equalsIgnoreCase("windowsAccount")) {
               account.setRelatedUsername(user.getWindowsAccount());
           }else if(strategy.getMapping().equalsIgnoreCase("idCardNo")) {
               account.setRelatedUsername(user.getIdCardNo());
           }else {
               account.setRelatedUsername(user.getUsername());
           }
           account.setRelatedPassword(ReciprocalUtils.encode(userInfoService.randomPassword()));
           
           account.setCreateType("automatic");
           account.setStatus(ConstantsStatus.ACTIVE);
           account.setStrategyId(strategy.getId());
           
           insert(account);
       }
       deleteByStrategy(strategy);
   }
   public void refreshAllByStrategy() {
       for( AccountsStrategy strategy : accountsStrategyService.query(null)) {
           refreshByStrategy(strategy);
       }
   }
   
   
   public List<UserInfo> queryUserNotInStrategy(AccountsStrategy strategy){
       return getMapper().queryUserNotInStrategy(strategy);
   }
   
   public long deleteByStrategy(AccountsStrategy strategy) {
       return getMapper().deleteByStrategy(strategy);
   }
	
}
