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
 

package org.dromara.maxkey.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.mybatis.jpa.IJpaMapper;


/**
 * @author Crystal.Sea
 *
 */
public interface UserInfoMapper  extends IJpaMapper<UserInfo>{
	
	//login query
	public UserInfo findByAppIdAndUsername(UserInfo userInfo);
	
	@Select("select * from  mxk_userinfo where username = #{value} and status = " + ConstsStatus.ACTIVE)
	public UserInfo findByUsername(String username);
	
	@Select("select * from  mxk_userinfo where ( email = #{value} or mobile= #{value} ) and status = " + ConstsStatus.ACTIVE)
	public UserInfo findByEmailMobile(String emailMobile);
	 
	public List<Organizations> findDeptsByUserId(String userId);
	
	public void updateLocked(UserInfo userInfo);

	public void updateLockout(UserInfo userInfo);

	public void badPasswordCount(UserInfo userInfo);
	
	public void badPasswordCountReset(UserInfo userInfo);
	
	public int 	changePassword(ChangePassword changePassword);
	
	public int 	updateAppLoginPassword(UserInfo userInfo);
	
	public int 	updateProtectedApps(UserInfo userInfo);
	
	public int 	updateSharedSecret(UserInfo userInfo);
	
	public int 	updatePasswordQuestion(UserInfo userInfo);
	
	public int	updateAuthnType(UserInfo userInfo);
	
	public int 	updateEmail(UserInfo userInfo);
	
	public int 	updateMobile(UserInfo userInfo);
	
	public int 	updateProfile(UserInfo userInfo);
	
    @Update("update mxk_userinfo set gridlist =  #{gridList} where id = #{id}")
	public int 	updateGridList(UserInfo userInfo) ;
    
    @Update("update mxk_userinfo set status =  #{status} where id = #{id}")
   	public int 	updateStatus(UserInfo userInfo) ;
}
