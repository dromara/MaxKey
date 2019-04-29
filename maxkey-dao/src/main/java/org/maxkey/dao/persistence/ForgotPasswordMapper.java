/**
 * 
 */
package org.maxkey.dao.persistence;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.ForgotPassword;
import org.maxkey.domain.UserInfo;

/**
 * @author Crystal.sea
 *
 */

public  interface ForgotPasswordMapper extends IJpaBaseMapper<ForgotPassword> {
	
	public UserInfo queryUserInfoByEmail(String email);
	
}
