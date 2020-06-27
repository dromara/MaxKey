package org.maxkey.persistence.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.UserInfo;


/**
 * @author Crystal.Sea
 *
 */
public interface UserInfoMapper  extends IJpaBaseMapper<UserInfo>{
	
	//login query
	
	public UserInfo loadByAppIdAndUsername(UserInfo userInfo);
	
	public int logisticDeleteAllByCid(String cid);
	
	public UserInfo loadByUsername(String username);
	
	public void locked(UserInfo userInfo);

	public void unlock(UserInfo userInfo);

	public void updateBadPWDCount(UserInfo userInfo);
	
	public int changePassword(UserInfo userInfo);
	
	public int changeAppLoginPassword(UserInfo userInfo);
	
	public int updateProtectedApps(UserInfo userInfo);
	
	public int changeSharedSecret(UserInfo userInfo);
	
	public int changePasswordQuestion(UserInfo userInfo);
	
	public int changeAuthnType(UserInfo userInfo);
	
	public int changeEmail(UserInfo userInfo);
	
	public int changeMobile(UserInfo userInfo);
	
	public int updateProfile(UserInfo userInfo);
	   
    @Select("SELECT * FROM  USERINFO WHERE   EMAIL = #{value} OR MOBILE= #{value}")
    public UserInfo queryUserInfoByEmailMobile(String emailMobile);
	
}
