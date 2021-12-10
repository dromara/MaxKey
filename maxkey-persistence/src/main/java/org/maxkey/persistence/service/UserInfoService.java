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
import org.maxkey.constants.ConstantsStatus;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.Accounts;
import org.maxkey.entity.ChangePassword;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.db.PasswordPolicyValidator;
import org.maxkey.persistence.kafka.KafkaIdentityAction;
import org.maxkey.persistence.kafka.KafkaIdentityTopic;
import org.maxkey.persistence.kafka.KafkaPersistService;
import org.maxkey.persistence.mapper.UserInfoMapper;
import org.maxkey.util.DateUtils;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


/**
 * @author Crystal.Sea
 *
 */
@Repository
public class UserInfoService extends JpaBaseService<UserInfo> {
	final static Logger _logger = LoggerFactory.getLogger(UserInfoService.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	PasswordPolicyValidator passwordPolicyValidator;
	
	@Autowired
	KafkaPersistService kafkaPersistService;
	
	 @Autowired
	 protected JdbcTemplate jdbcTemplate;
	 
	 AccountsService accountsService;
	
	public UserInfoService() {
		super(UserInfoMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public UserInfoMapper getMapper() {
		return (UserInfoMapper)super.getMapper();
	}
	
    public boolean insert(UserInfo userInfo) {
        userInfo = passwordEncoder(userInfo);
        if (super.insert(userInfo)) {
            if(kafkaPersistService.getApplicationConfig().isKafkaSupport()) {
                UserInfo loadUserInfo = loadUserRelated(userInfo.getId());
                kafkaPersistService.send(
                        KafkaIdentityTopic.USERINFO_TOPIC, 
                        loadUserInfo,
                        KafkaIdentityAction.CREATE_ACTION);
            }
            
            return true;
        }

        return false;
    }
	
    public boolean update(UserInfo userInfo) {
        userInfo = passwordEncoder(userInfo);
        if (super.update(userInfo)) {
            if(kafkaPersistService.getApplicationConfig().isKafkaSupport()) {
                UserInfo loadUserInfo = loadUserRelated(userInfo.getId());
                accountUpdate(loadUserInfo);
                kafkaPersistService.send(
                        KafkaIdentityTopic.USERINFO_TOPIC, 
                        loadUserInfo,
                        KafkaIdentityAction.UPDATE_ACTION);
            }
            
            changePasswordProvisioning(userInfo);
            return true;
        }
        return false;
    }
	
	public boolean delete(UserInfo userInfo) {
	    UserInfo loadUserInfo = null;
	    if(kafkaPersistService.getApplicationConfig().isKafkaSupport()) {
	        loadUserInfo = loadUserRelated(userInfo.getId());
	    }
	    
		if( super.delete(userInfo)){
			kafkaPersistService.send(
		            KafkaIdentityTopic.USERINFO_TOPIC, 
		            loadUserInfo, 
		            KafkaIdentityAction.DELETE_ACTION);
			accountUpdate(loadUserInfo);
			 return true;
		}
		return false;
	}
	
    //更新账号状态
    public void accountUpdate(UserInfo userInfo) {
        if(userInfo.getStatus() != ConstantsStatus.ACTIVE) {
            if(accountsService==null) {
                accountsService = 
                       WebContext.getBean("accountsService",AccountsService.class); 
            }
            Accounts queryAcount =new Accounts();
            queryAcount.setUserId(userInfo.getId());
            for (Accounts acount : accountsService.query(queryAcount)) {
                acount.setStatus(ConstantsStatus.INACTIVE);
                accountsService.update(acount);
            }
        }
    }

	public UserInfo loadUserRelated(String userId) {
	    UserInfo loadUserInfo =this.get(userId);
	    loadUserInfo.setDepts(getMapper().loadDeptsByUserId(userId));
	    loadUserInfo.setAdjoints(getMapper().loadAdjointsByUserId(userId));
	    return loadUserInfo;
	}
	
	public boolean updateGridList(String gridList) {
	    try {
    	    if (gridList != null && !gridList.equals("")) {
                WebContext.getUserInfo().setGridList(Integer.parseInt(gridList));
                getMapper().updateGridList(WebContext.getUserInfo());
            }
	    }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
	    return true;
	}
	
	
	public boolean updateProtectedApps(UserInfo userinfo) {
		try {
			if(WebContext.getUserInfo() != null) {
				userinfo.setModifiedBy(WebContext.getUserInfo().getId());
			}
			userinfo.setModifiedDate(DateUtils.getCurrentDateTimeAsString());
			return getMapper().updateProtectedApps(userinfo) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public UserInfo loadByUsername(String username) {
		return getMapper().loadByUsername(username);
	}
	
	public UserInfo loadByAppIdAndUsername(String appId,String username){
		try {
			UserInfo userinfo = new UserInfo();
			userinfo.setUsername(username);
			return getMapper().loadByAppIdAndUsername(userinfo) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public void logisticDeleteAllByCid(String cid){
		try {
			 getMapper().logisticDeleteAllByCid(cid);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserInfo passwordEncoder(UserInfo userInfo) {
	    //密码不为空，则需要进行加密处理
	    if(userInfo.getPassword()!=null && !userInfo.getPassword().equals("")) {
    	    String password = passwordEncoder.encode(userInfo.getPassword());
            userInfo.setDecipherable(PasswordReciprocal.getInstance().encode(userInfo.getPassword()));
            _logger.debug("decipherable : "+userInfo.getDecipherable());
            userInfo.setPassword(password);
            userInfo.setPasswordLastSetTime(DateUtils.getCurrentDateTimeAsString());
            
            userInfo.setModifiedDate(DateUtils.getCurrentDateTimeAsString());
	    }
        return userInfo;
	}
	
	
	public boolean changePassword(  String oldPassword,
                                    String newPassword,
                                    String confirmPassword,
                                    int passwordSetType) {
		try {
		    WebContext.setAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT, "");
	        UserInfo userInfo = WebContext.getUserInfo();
	        UserInfo changeUserInfo = new UserInfo();
	        changeUserInfo.setUsername(userInfo.getUsername());
	        changeUserInfo.setPassword(newPassword);
	        changeUserInfo.setId(userInfo.getId());
	        changeUserInfo.setDecipherable(userInfo.getDecipherable());
	        changeUserInfo.setPasswordSetType(passwordSetType);
	        
	        if(newPassword.equals(confirmPassword)){
	            if(oldPassword==null || 
	                    passwordEncoder.matches(oldPassword, userInfo.getPassword())){
	                if(changePassword(changeUserInfo,true) ){
	                    userInfo.setPassword(changeUserInfo.getPassword());
                        userInfo.setDecipherable(changeUserInfo.getDecipherable());
	                    return true;
	                }
	                return false;	               
	            }else {
	                if(oldPassword!=null &&
	                        passwordEncoder.matches(newPassword, userInfo.getPassword())) {
	                    WebContext.setAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT, 
	                            WebContext.getI18nValue("PasswordPolicy.OLD_PASSWORD_MATCH"));
	                }else {
	                    WebContext.setAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT, 
	                        WebContext.getI18nValue("PasswordPolicy.OLD_PASSWORD_NOT_MATCH"));
	                }
	            }
	        }else {
	            WebContext.setAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT, 
	                    WebContext.getI18nValue("PasswordPolicy.CONFIRMPASSWORD_NOT_MATCH"));
	        }
		 } catch (Exception e) {
             e.printStackTrace();
         }    
		    
		return false;
	}
	
    public boolean changePassword(UserInfo changeUserInfo,boolean passwordPolicy) {
        try {
            _logger.debug("decipherable old : " + changeUserInfo.getDecipherable());
            _logger.debug("decipherable new : " + PasswordReciprocal.getInstance().encode(changeUserInfo.getPassword()));

            if (passwordPolicy && passwordPolicyValidator.validator(changeUserInfo) == false) {
                return false;
            }

            if (WebContext.getUserInfo() != null) {
                changeUserInfo.setModifiedBy(WebContext.getUserInfo().getId());
            }

            changeUserInfo = passwordEncoder(changeUserInfo);

            if (getMapper().changePassword(changeUserInfo) > 0) {
                changePasswordProvisioning(changeUserInfo);
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
	
	public String randomPassword() {
	    return passwordPolicyValidator.generateRandomPassword();
	}
	
	public void changePasswordProvisioning(UserInfo userInfo) {
	    if(userInfo.getPassword()!=null && !userInfo.getPassword().equals("")) {
    	    ChangePassword changePassword=new ChangePassword();
            changePassword.setId(userInfo.getId());
            changePassword.setUserId(userInfo.getId());
            changePassword.setUsername(userInfo.getUsername());
            changePassword.setDecipherable(userInfo.getDecipherable());
            changePassword.setPassword(userInfo.getPassword());
            kafkaPersistService.send(
                    KafkaIdentityTopic.PASSWORD_TOPIC, 
                    changePassword, 
                    KafkaIdentityAction.PASSWORD_ACTION);
	    }
	}
	
	public boolean changeAppLoginPassword(UserInfo userinfo) {
		try {
			if(WebContext.getUserInfo() != null) {
				userinfo.setModifiedBy(WebContext.getUserInfo().getId());
			}
			userinfo.setModifiedDate(DateUtils.getCurrentDateTimeAsString());
			return getMapper().changeAppLoginPassword(userinfo) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 锁定用户：islock：1 用户解锁 2 用户锁定
	 * @param userInfo
	 */
	public void locked(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				userInfo.setIsLocked(ConstantsStatus.STOP);
				getMapper().locked(userInfo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户登录成功后，重置错误密码次数和解锁用户
	 * @param userInfo
	 */
	public void unlock(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				userInfo.setIsLocked(ConstantsStatus.START);
				userInfo.setBadPasswordCount(0);
				getMapper().unlock(userInfo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新错误密码次数
	 * @param userInfo
	 */
	public void updateBadPasswordCount(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				int updateBadPWDCount = userInfo.getBadPasswordCount() + 1;
				userInfo.setBadPasswordCount(updateBadPWDCount);
				getMapper().updateBadPWDCount(userInfo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

 
	
	public boolean changeSharedSecret(UserInfo userInfo){
		return getMapper().changeSharedSecret(userInfo)>0;
	}
	
	public boolean changePasswordQuestion(UserInfo userInfo){
		return getMapper().changePasswordQuestion(userInfo)>0;
	}
	
	public boolean changeAuthnType(UserInfo userInfo){
		return getMapper().changeAuthnType(userInfo)>0;
	}
	
	public boolean changeEmail(UserInfo userInfo){
		return getMapper().changeEmail(userInfo)>0;
	}
	
	public boolean changeMobile(UserInfo userInfo){
		return getMapper().changeMobile(userInfo)>0;
	}
	
    public UserInfo queryUserInfoByEmailMobile(String emailMobile) {
        return getMapper().queryUserInfoByEmailMobile(emailMobile);
    }
    
    public int updateProfile(UserInfo userInfo){
        
        return getMapper().updateProfile(userInfo);
    }

    public void setPasswordPolicyValidator(PasswordPolicyValidator passwordPolicyValidator) {
        this.passwordPolicyValidator = passwordPolicyValidator;
    }

}
