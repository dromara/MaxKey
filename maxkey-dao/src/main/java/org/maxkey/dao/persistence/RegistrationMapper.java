/**
 * 
 */
package org.maxkey.dao.persistence;

import java.util.List;

import org.apache.mybatis.jpa.persistence.IJpaBaseMapper;
import org.maxkey.domain.Registration;
import org.maxkey.domain.UserInfo;

/**
 * @author Crystal.sea
 *
 */

public  interface RegistrationMapper extends IJpaBaseMapper<Registration> {
	

	public List<UserInfo> queryUserInfoByEmail(String email);

}
