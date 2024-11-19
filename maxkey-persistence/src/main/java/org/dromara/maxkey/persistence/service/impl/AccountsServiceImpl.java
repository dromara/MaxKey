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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.AccountsStrategy;
import org.dromara.maxkey.entity.OrganizationsCast;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.mapper.AccountsMapper;
import org.dromara.maxkey.persistence.service.AccountsService;
import org.dromara.maxkey.persistence.service.AccountsStrategyService;
import org.dromara.maxkey.persistence.service.OrganizationsCastService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.provision.ProvisionAct;
import org.dromara.maxkey.provision.ProvisionService;
import org.dromara.maxkey.provision.ProvisionTopic;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Repository
public class AccountsServiceImpl  extends JpaServiceImpl<AccountsMapper,Accounts>  implements AccountsService{

    @Autowired
    ProvisionService provisionService;
    
    @Autowired
    UserInfoService  userInfoService;
    
    @Autowired
    AccountsStrategyService accountsStrategyService;
    
    @Autowired
    OrganizationsCastService organizationsCastService;
    
	@Override
	 public boolean insert(Accounts account) {
	     if (super.insert(account)) {
	            if(provisionService.getApplicationConfig().isProvisionSupport()) {
	                UserInfo loadUserInfo = userInfoService.findUserRelated(account.getUserId());
	                account.setUserInfo(loadUserInfo);
	                OrganizationsCast cast = new OrganizationsCast();
                    cast.setProvider(account.getAppId());
                    cast.setOrgId(loadUserInfo.getDepartmentId());
                    account.setOrgCast(organizationsCastService.query(cast));
                    provisionService.send(
	                        ProvisionTopic.ACCOUNT_TOPIC, 
	                        account,
	                        ProvisionAct.CREATE);
	            }
	            
	            return true;
	        }
	     return false;
	 }
	 
	@Override
	public boolean update(Accounts account) {
         if (super.update(account)) {
        	 if(provisionService.getApplicationConfig().isProvisionSupport()) {
                    UserInfo loadUserInfo = userInfoService.findUserRelated(account.getUserId());
                    account.setUserInfo(loadUserInfo);
                    OrganizationsCast cast = new OrganizationsCast();
                    cast.setProvider(account.getAppId());
                    cast.setOrgId(loadUserInfo.getDepartmentId());
                    account.setOrgCast(organizationsCastService.query(cast));
                    provisionService.send(
                            ProvisionTopic.ACCOUNT_TOPIC, 
                            account,
                            ProvisionAct.UPDATE);
                }
                
                return true;
            }
         return false;
     }
   
   public boolean updateStatus(Accounts accounts) {
	   return this.getMapper().updateStatus(accounts) > 0;
   }
   
   public boolean remove(String id) {
       Accounts account = this.get(id);
       if (super.delete(id)) {
              UserInfo loadUserInfo = null;
              if(provisionService.getApplicationConfig().isProvisionSupport()) {
                  loadUserInfo = userInfoService.findUserRelated(account.getUserId());
                  account.setUserInfo(loadUserInfo);
                  provisionService.send(
                          ProvisionTopic.ACCOUNT_TOPIC, 
                          account,
                          ProvisionAct.DELETE);
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
           account.setRelatedUsername(generateAccount(user,strategy));
           account.setRelatedPassword(PasswordReciprocal.getInstance().encode(userInfoService.randomPassword()));
           
           account.setInstId(strategy.getInstId());
           account.setCreateType("automatic");
           account.setStatus(ConstsStatus.ACTIVE);
           account.setStrategyId(strategy.getId());
           
           insert(account);
       }
       deleteByStrategy(strategy);
   }
   public void refreshAllByStrategy() {
	   AccountsStrategy queryStrategy = new AccountsStrategy();
	   queryStrategy.setCreateType("automatic");
       for( AccountsStrategy strategy : accountsStrategyService.query(queryStrategy)) {
           refreshByStrategy(strategy);
       }
   }
   
   
   public List<UserInfo> queryUserNotInStrategy(AccountsStrategy strategy){
       return getMapper().queryUserNotInStrategy(strategy);
   }
   
   public long deleteByStrategy(AccountsStrategy strategy) {
       return getMapper().deleteByStrategy(strategy);
   }
	
	
   public List<Accounts> queryByAppIdAndDate(Accounts account) {
       return getMapper().queryByAppIdAndDate(account);
   }
   
   public List<Accounts> queryByAppIdAndAccount(String appId,String relatedUsername){
	   return getMapper().queryByAppIdAndAccount(appId,relatedUsername);
   }
   
   
   public String generateAccount(UserInfo  userInfo,AccountsStrategy accountsStrategy) {
   	String shortAccount = generateAccount(userInfo,accountsStrategy,true);
   	String account = generateAccount(userInfo,accountsStrategy,false);
   	String accountResult = shortAccount;
   	List<Accounts> accountsList =getMapper().queryByAppIdAndAccount(accountsStrategy.getAppId(),shortAccount +accountsStrategy.getSuffixes());
   	if(!accountsList.isEmpty()) {
   		if(accountsStrategy.getMapping().equalsIgnoreCase("email")) {
   			accountResult = account;
   			accountsList =getMapper().queryByAppIdAndAccount(accountsStrategy.getAppId(),account + accountsStrategy.getSuffixes());
   		}
   		if(!accountsList.isEmpty()) {
	    		for(int i =1 ;i < 100 ;i++) {
	    			accountResult = account + i;
	    			accountsList =getMapper().queryByAppIdAndAccount(accountsStrategy.getAppId(),accountResult + accountsStrategy.getSuffixes());
	    			if(accountsList.isEmpty()) {
	    				break;
	    			}
	    		}
   		}
   	}
   	if(StringUtils.isNotBlank(accountsStrategy.getSuffixes())){
   		accountResult = accountResult + accountsStrategy.getSuffixes();
   	}
       return accountResult;
   }
   
   
	private String generateAccount(UserInfo  userInfo,AccountsStrategy strategy,boolean isShort) {
		String account = "";
    	if(strategy.getMapping().equalsIgnoreCase("username")) {
    		account = userInfo.getUsername();
    	}else if(strategy.getMapping().equalsIgnoreCase("mobile")) {
    		account = userInfo.getMobile();
    	}else if(strategy.getMapping().equalsIgnoreCase("email")) {
    		try {
	    		if(isShort) {
	    			account = getPinYinShortName(userInfo.getDisplayName());
	    		}else {
	    			account = getPinYinName(userInfo.getDisplayName());
	    		}
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}else if(strategy.getMapping().equalsIgnoreCase("employeeNumber")) {
    		account = userInfo.getEmployeeNumber();
    	}else if(strategy.getMapping().equalsIgnoreCase("windowsAccount")) {
    		account = userInfo.getWindowsAccount();
    	}else if(strategy.getMapping().equalsIgnoreCase("idCardNo")) {
    		account = userInfo.getIdCardNo();
    	}else {
    		account = userInfo.getUsername();
    	}
    	
        return account;
	}
	
	public static String getPinYinName(String name) throws BadHanyuPinyinOutputFormatCombination {
        HanyuPinyinOutputFormat pinyinFormat = new        HanyuPinyinOutputFormat();
        pinyinFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pinyinFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        return PinyinHelper.toHanYuPinyinString(name, pinyinFormat, "",false);
    }
	
	public static String getPinYinShortName(String name) throws BadHanyuPinyinOutputFormatCombination {
		char[] strs = name.toCharArray();
		String pinyinName = "";
		for(int i=0;i<strs.length;i++) {
			if(i == 0) {
				pinyinName += getPinYinName(strs[i]+"");
			}else {
				pinyinName += getPinYinName(strs[i]+"").charAt(0);
			}
		}
		return pinyinName;
    }
	
}
