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


import java.sql.Types;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.constants.ConstsStatus;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.Accounts;
import org.maxkey.entity.ChangePassword;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.mapper.UserInfoMapper;
import org.maxkey.persistence.mq.MqIdentityAction;
import org.maxkey.persistence.mq.MqIdentityTopic;
import org.maxkey.persistence.mq.MqPersistService;
import org.maxkey.persistence.repository.PasswordPolicyValidator;
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
	MqPersistService mqPersistService;
	
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
        	if(mqPersistService.getApplicationConfig().isMessageQueueSupport()) {
                UserInfo loadUserInfo = findUserRelated(userInfo.getId());
                mqPersistService.send(
                        MqIdentityTopic.USERINFO_TOPIC, 
                        loadUserInfo,
                        MqIdentityAction.CREATE_ACTION);
            }
            
            return true;
        }

        return false;
    }
	
    public boolean update(UserInfo userInfo) {
        userInfo = passwordEncoder(userInfo);
        if (super.update(userInfo)) {
        	if(mqPersistService.getApplicationConfig().isMessageQueueSupport()) {
                UserInfo loadUserInfo = findUserRelated(userInfo.getId());
                accountUpdate(loadUserInfo);
                mqPersistService.send(
                        MqIdentityTopic.USERINFO_TOPIC, 
                        loadUserInfo,
                        MqIdentityAction.UPDATE_ACTION);
            }
            
            changePasswordProvisioning(userInfo);
            return true;
        }
        return false;
    }
	
	public boolean delete(UserInfo userInfo) {
	    UserInfo loadUserInfo = null;
	    if(mqPersistService.getApplicationConfig().isMessageQueueSupport()) {
	        loadUserInfo = findUserRelated(userInfo.getId());
	    }
	    
		if( super.delete(userInfo)){
			mqPersistService.send(
		            MqIdentityTopic.USERINFO_TOPIC, 
		            loadUserInfo, 
		            MqIdentityAction.DELETE_ACTION);
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
	    loadUserInfo.setAdjoints(getMapper().findAdjointsByUserId(userId));
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
	
	/**
	 * 认证密码修改
	 * @param oldPassword
	 * @param newPassword
	 * @param confirmPassword
	 * @param passwordSetType
	 * @return
	 */
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
	
	/**
	 * 后台密码修改
	 * @param changeUserInfo
	 * @param passwordPolicy
	 * @return
	 */
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

            if (getMapper().updatePassword(changeUserInfo) > 0) {
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
	    if(StringUtils.isNotBlank(userInfo.getPassword())) {
	    	UserInfo loadUserInfo = findByUsername(userInfo.getUsername());
    	    ChangePassword changePassword=new ChangePassword();
            changePassword.setId(loadUserInfo.getId());
            changePassword.setUserId(loadUserInfo.getId());
            changePassword.setUsername(loadUserInfo.getUsername());
            changePassword.setWindowsAccount(loadUserInfo.getWindowsAccount());
            changePassword.setMobile(loadUserInfo.getMobile());
            changePassword.setEmail(loadUserInfo.getEmail());
            changePassword.setEmployeeNumber(loadUserInfo.getEmployeeNumber());
            changePassword.setDecipherable(loadUserInfo.getDecipherable());
            changePassword.setPassword(loadUserInfo.getPassword());
            changePassword.setInstId(loadUserInfo.getInstId());
            mqPersistService.send(
                    MqIdentityTopic.PASSWORD_TOPIC, 
                    changePassword, 
                    MqIdentityAction.PASSWORD_ACTION);
	    }
	}
	
	public boolean updateAppLoginPassword(UserInfo userinfo) {
		try {
			if(WebContext.getUserInfo() != null) {
				userinfo.setModifiedBy(WebContext.getUserInfo().getId());
			}
			userinfo.setModifiedDate(DateUtils.getCurrentDateTimeAsString());
			return getMapper().updateAppLoginPassword(userinfo) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 锁定用户：islock：1 用户解锁 2 用户锁定
	 * @param userInfo
	 */
	public void updateLocked(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				userInfo.setIsLocked(ConstsStatus.STOP);
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
	public void updateLockout(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				userInfo.setIsLocked(ConstsStatus.START);
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

    public void setPasswordPolicyValidator(PasswordPolicyValidator passwordPolicyValidator) {
        this.passwordPolicyValidator = passwordPolicyValidator;
    }

}
