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


import java.sql.Types;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.mapper.UserInfoMapper;
import org.dromara.maxkey.persistence.service.AccountsService;
import org.dromara.maxkey.persistence.service.PasswordPolicyValidatorService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.provision.ProvisionAct;
import org.dromara.maxkey.provision.ProvisionService;
import org.dromara.maxkey.provision.ProvisionTopic;
import org.dromara.maxkey.web.WebContext;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


/**
 * @author Crystal.Sea
 *
 */
@Repository
public class UserInfoServiceImpl extends JpaServiceImpl<UserInfoMapper,UserInfo> implements UserInfoService{
	static final  Logger _logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	PasswordPolicyValidatorService passwordPolicyValidatorService;
	
	@Autowired
	ProvisionService provisionService;

	AccountsService accountsService;
	
	
	@Override
    public boolean insert(UserInfo userInfo) {
    	this.passwordEncoder(userInfo);
        if (super.insert(userInfo)) {
        	if(provisionService.getApplicationConfig().isProvisionSupport()) {
                UserInfo loadUserInfo = findUserRelated(userInfo.getId());
                provisionService.send(
                        ProvisionTopic.USERINFO_TOPIC, 
                        loadUserInfo,
                        ProvisionAct.CREATE);
            }
            
            return true;
        }

        return false;
    }
    
    public boolean insert(UserInfo userInfo,boolean passwordEncoder) {
    	if(passwordEncoder) {
    		this.passwordEncoder(userInfo);
    	}
        if (super.insert(userInfo)) {
        	if(provisionService.getApplicationConfig().isProvisionSupport()) {
                UserInfo loadUserInfo = findUserRelated(userInfo.getId());
                provisionService.send(
                        ProvisionTopic.USERINFO_TOPIC, 
                        loadUserInfo,
                        ProvisionAct.CREATE);
            }
            
            return true;
        }

        return false;
    }
	
    @Override
    public boolean update(UserInfo userInfo) {
    	ChangePassword changePassword = this.passwordEncoder(userInfo);
        if (super.update(userInfo)) {
        	if(provisionService.getApplicationConfig().isProvisionSupport()) {
                UserInfo loadUserInfo = findUserRelated(userInfo.getId());
                accountUpdate(loadUserInfo);
                provisionService.send(
                        ProvisionTopic.USERINFO_TOPIC, 
                        loadUserInfo,
                        ProvisionAct.UPDATE);
            }
            if(userInfo.getPassword() != null) {
            	changePasswordProvisioning(changePassword);
            }
            return true;
        }
        return false;
    }
	
	public boolean delete(UserInfo userInfo) {
	    UserInfo loadUserInfo = null;
	    if(provisionService.getApplicationConfig().isProvisionSupport()) {
	        loadUserInfo = findUserRelated(userInfo.getId());
	    }
	    
		if( super.delete(userInfo.getId())){
			provisionService.send(
		            ProvisionTopic.USERINFO_TOPIC, 
		            loadUserInfo, 
		            ProvisionAct.DELETE);
			accountUpdate(loadUserInfo);
			 return true;
		}
		return false;
	}
	
    //更新账号状态
    public void accountUpdate(UserInfo userInfo) {
        if(userInfo.getStatus() != ConstsStatus.ACTIVE) {
            if(accountsService==null) {
                accountsService = 
                       WebContext.getBean("accountsService",AccountsService.class); 
            }
            Accounts queryAcount =new Accounts();
            queryAcount.setUserId(userInfo.getId());
            for (Accounts acount : accountsService.query(queryAcount)) {
                acount.setStatus(ConstsStatus.INACTIVE);
                accountsService.update(acount);
            }
        }
    }

	public UserInfo findUserRelated(String userId) {
	    UserInfo loadUserInfo =this.get(userId);
	    loadUserInfo.setDepts(getMapper().findDeptsByUserId(userId));
	    return loadUserInfo;
	}
	
	public boolean updateGridList(String gridList,UserInfo userInfo) {
	    try {
    	    if (gridList != null && !gridList.equals("")) {
    	    	userInfo.setGridList(Integer.parseInt(gridList));
                getMapper().updateGridList(userInfo);
            }
	    }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
	    return true;
	}
	
	
	public void saveOrUpdate(UserInfo userInfo) {
		UserInfo loadUserInfo = findOne(" username = ? and instid = ?",
				new Object[] { userInfo.getUsername(),userInfo.getInstId() },
                new int[] { Types.VARCHAR,Types.VARCHAR});
		if(loadUserInfo == null) {
			insert(userInfo);
		}else {
			userInfo.setId(loadUserInfo.getId());
			userInfo.setPassword(null);
			update(userInfo);
		}
	}
	
	public boolean updateProtectedApps(UserInfo userinfo) {
		try {
			userinfo.setModifiedDate(new Date());
			return getMapper().updateProtectedApps(userinfo) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public UserInfo findByUsername(String username) {
		return getMapper().findByUsername(username);
	}
	
    public UserInfo findByEmailMobile(String emailMobile) {
        return getMapper().findByEmailMobile(emailMobile);
    }
	
	public UserInfo findByAppIdAndUsername(String appId,String username){
		try {
			UserInfo userinfo = new UserInfo();
			userinfo.setUsername(username);
			return getMapper().findByAppIdAndUsername(userinfo) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ChangePassword passwordEncoder(UserInfo userInfo) {
		ChangePassword changePassword = null;
		if(StringUtils.isNotBlank(userInfo.getPassword())) {
    		changePassword = new ChangePassword(userInfo);
    		passwordEncoder(changePassword);
    		userInfo.setPassword(changePassword.getPassword());
    		userInfo.setDecipherable(changePassword.getDecipherable());
    		userInfo.setPasswordLastSetTime(changePassword.getPasswordLastSetTime());
    	}else {
    		userInfo.setPassword(null);
    		userInfo.setDecipherable(null);
    	}
		return changePassword;
	}
	
	public ChangePassword passwordEncoder(ChangePassword changePassword) {
	    //密码不为空，则需要进行加密处理
	    if(StringUtils.isNotBlank(changePassword.getPassword())) {
    	    String password = passwordEncoder.encode(changePassword.getPassword());
    	    changePassword.setDecipherable(PasswordReciprocal.getInstance().encode(changePassword.getPassword()));
            _logger.debug("decipherable : {}",changePassword.getDecipherable());
            changePassword.setPassword(password);
            changePassword.setPasswordLastSetTime(new Date());
            
	    }else {
	    	changePassword.setPassword(null);
	    	changePassword.setDecipherable(null);
	    }
        return changePassword;
	}
	
	/**
	 * 认证密码修改
	 * @param oldPassword
	 * @param newPassword
	 * @param confirmPassword
	 * @param passwordSetType
	 * @return
	 */
	public boolean changePassword(  ChangePassword changePassword) {
		try {
		    WebContext.setAttribute(PasswordPolicyValidatorServiceImpl.PASSWORD_POLICY_VALIDATE_RESULT, "");
		    UserInfo userInfo = this.findByUsername(changePassword.getUsername());
	        if(changePassword.getPassword().equals(changePassword.getConfirmPassword())){
	            if(StringUtils.isNotBlank(changePassword.getOldPassword()) &&
	                    passwordEncoder.matches(changePassword.getOldPassword(), userInfo.getPassword())){
	                if(changePassword(changePassword,true) ){
	                    return true;
	                }
	                return false;	               
	            }else {
	                if(StringUtils.isNotBlank(changePassword.getOldPassword())&&
	                        passwordEncoder.matches(changePassword.getPassword(), userInfo.getPassword())) {
	                    WebContext.setAttribute(PasswordPolicyValidatorServiceImpl.PASSWORD_POLICY_VALIDATE_RESULT, 
	                            WebContext.getI18nValue("PasswordPolicy.OLD_PASSWORD_MATCH"));
	                }else {
	                    WebContext.setAttribute(PasswordPolicyValidatorServiceImpl.PASSWORD_POLICY_VALIDATE_RESULT, 
	                        WebContext.getI18nValue("PasswordPolicy.OLD_PASSWORD_NOT_MATCH"));
	                }
	            }
	        }else {
	            WebContext.setAttribute(PasswordPolicyValidatorServiceImpl.PASSWORD_POLICY_VALIDATE_RESULT, 
	                    WebContext.getI18nValue("PasswordPolicy.CONFIRMPASSWORD_NOT_MATCH"));
	        }
		 } catch (Exception e) {
             e.printStackTrace();
         }    
		    
		return false;
	}
	
	/**
	 * 后台密码修改
	 * @param changeUserInfo
	 * @param passwordPolicy
	 * @return
	 */
    public boolean changePassword(ChangePassword changePassword,boolean passwordPolicy) {
        try {
            _logger.debug("decipherable old : {}" , changePassword.getDecipherable());
            _logger.debug("decipherable new : {}" , PasswordReciprocal.getInstance().encode(changePassword.getDecipherable()));

            if (passwordPolicy && !passwordPolicyValidatorService.validator(changePassword)) {
                return false;
            }

            changePassword = passwordEncoder(changePassword);

            if (getMapper().changePassword(changePassword) > 0) {
                changePasswordProvisioning(changePassword);
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
	
	public String randomPassword() {
	    return passwordPolicyValidatorService.generateRandomPassword();
	}
	
	public void changePasswordProvisioning(ChangePassword changePassworded) {
	    if(changePassworded !=null && StringUtils.isNotBlank(changePassworded.getPassword())) {
	    	UserInfo loadUserInfo = findByUsername(changePassworded.getUsername());
    	    ChangePassword changePassword = new ChangePassword(loadUserInfo);
    	    provisionService.send(ProvisionTopic.PASSWORD_TOPIC, changePassword, ProvisionAct.PASSWORD);
	    }
	}
	
	public boolean updateAppLoginPassword(UserInfo userinfo) {
		try {
			userinfo.setModifiedDate(new Date());
			return getMapper().updateAppLoginPassword(userinfo) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 锁定用户：islock：1 解锁 5 锁定
	 * @param userInfo
	 */
	public void locked(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				userInfo.setIsLocked(ConstsStatus.LOCK);
				getMapper().updateLocked(userInfo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户登录成功后，重置错误密码次数和解锁用户
	 * @param userInfo
	 */
	public void lockout(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				userInfo.setIsLocked(ConstsStatus.ACTIVE);
				userInfo.setBadPasswordCount(0);
				getMapper().updateLockout(userInfo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新错误密码次数
	 * @param userInfo
	 */
	public void badPasswordCount(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				int updateBadPWDCount = userInfo.getBadPasswordCount() + 1;
				userInfo.setBadPasswordCount(updateBadPWDCount);
				getMapper().badPasswordCount(userInfo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重置错误密码次数
	 * @param userInfo
	 */
	public void badPasswordCountReset(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				getMapper().badPasswordCountReset(userInfo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public boolean updateSharedSecret(UserInfo userInfo){
		return getMapper().updateSharedSecret(userInfo)>0;
	}
	
	public boolean updatePasswordQuestion(UserInfo userInfo){
		return getMapper().updatePasswordQuestion(userInfo)>0;
	}
	
	public boolean updateAuthnType(UserInfo userInfo){
		return getMapper().updateAuthnType(userInfo)>0;
	}
	
	public boolean updateEmail(UserInfo userInfo){
		return getMapper().updateEmail(userInfo)>0;
	}
	
	public boolean updateMobile(UserInfo userInfo){
		return getMapper().updateMobile(userInfo)>0;
	}
    
    public int updateProfile(UserInfo userInfo){
        return getMapper().updateProfile(userInfo);
    }
    
    public boolean 	updateStatus(UserInfo userInfo) {
    	return getMapper().updateStatus(userInfo) > 0;
    }

}
