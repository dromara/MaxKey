/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.persistence.service;


import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.mybatis.jpa.IJpaService;

/**
 * @author Crystal.Sea
 *
 */
public interface UserInfoService extends IJpaService<UserInfo> {
	
    public boolean insert(UserInfo userInfo,boolean passwordEncoder) ;
	
	public boolean delete(UserInfo userInfo) ;
	
    //更新账号状态
    public void accountUpdate(UserInfo userInfo) ;

	public UserInfo findUserRelated(String userId) ;
	
	public boolean updateGridList(String gridList,UserInfo userInfo);
	
	
	public void saveOrUpdate(UserInfo userInfo) ;
	
	public boolean updateProtectedApps(UserInfo userinfo) ;

	public UserInfo findByUsername(String username) ;
	
    public UserInfo findByEmailMobile(String emailMobile) ;
	
	public UserInfo findByAppIdAndUsername(String appId,String username);
	
	public ChangePassword passwordEncoder(UserInfo userInfo) ;
	
	public ChangePassword passwordEncoder(ChangePassword changePassword) ;
	
	/**
	 * 认证密码修改
	 * @param oldPassword
	 * @param newPassword
	 * @param confirmPassword
	 * @param passwordSetType
	 * @return
	 */
	public boolean changePassword(  ChangePassword changePassword) ;
	
	/**
	 * 后台密码修改
	 * @param changeUserInfo
	 * @param passwordPolicy
	 * @return
	 */
    public boolean changePassword(ChangePassword changePassword,boolean passwordPolicy) ;
	
	public String randomPassword() ;
	
	public void changePasswordProvisioning(ChangePassword changePassworded) ;
	
	public boolean updateAppLoginPassword(UserInfo userinfo) ;
	
	
	/**
	 * 锁定用户：islock：1 用户解锁 2 用户锁定
	 * @param userInfo
	 */
	public void locked(UserInfo userInfo) ;

	/**
	 * 用户登录成功后，重置错误密码次数和解锁用户
	 * @param userInfo
	 */
	public void lockout(UserInfo userInfo) ;

	/**
	 * 更新错误密码次数
	 * @param userInfo
	 */
	public void badPasswordCount(UserInfo userInfo) ;
	
	public void badPasswordCountReset(UserInfo userInfo);

	public boolean updateSharedSecret(UserInfo userInfo);
	
	public boolean updatePasswordQuestion(UserInfo userInfo);
	
	public boolean updateAuthnType(UserInfo userInfo);
	
	public boolean updateEmail(UserInfo userInfo);
	
	public boolean updateMobile(UserInfo userInfo);
    
    public int updateProfile(UserInfo userInfo);
    
    public boolean 	updateStatus(UserInfo userInfo);

}
