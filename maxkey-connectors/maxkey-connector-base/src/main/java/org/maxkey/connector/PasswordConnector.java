package org.maxkey.connector;

import org.maxkey.domain.UserInfo;

public abstract class   PasswordConnector  extends AbstractConnector<UserInfo> {

	public boolean sync(UserInfo userInfo) throws Exception{
		return true;
	}
	

	
}
