package org.maxkey.connector;

import org.maxkey.domain.UserInfo;

public abstract class   UserInfoConnector  extends AbstractConnector<UserInfo> {
	
	@Override
	public boolean create(UserInfo userInfo)  throws Exception {
		return true;
	}
	
	@Override
	public boolean update(UserInfo userInfo) throws Exception{
		return true;
	}
	
	@Override
	public boolean delete(UserInfo userInfo) throws Exception{
		return true;
	}

	
}
