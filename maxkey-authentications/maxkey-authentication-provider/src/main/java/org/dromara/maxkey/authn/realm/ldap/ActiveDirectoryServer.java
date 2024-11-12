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
 

/**
 * 
 */
package org.dromara.maxkey.authn.realm.ldap;

import org.dromara.maxkey.authn.realm.IAuthenticationServer;
import org.dromara.maxkey.ldap.activedirectory.ActiveDirectoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Crystal.Sea
 *
 */
public final class ActiveDirectoryServer implements IAuthenticationServer {
	private static final  Logger _logger = LoggerFactory.getLogger(ActiveDirectoryServer.class);

	ActiveDirectoryUtils activeDirectoryUtils;

	String filter;
	
	boolean mapping;
	
	/* (non-Javadoc)
	 * @see com.connsec.web.authentication.realm.IAuthenticationServer#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticate(String username, String password) {
		ActiveDirectoryUtils ldapPassWordValid = 
    		        new ActiveDirectoryUtils(
    		                activeDirectoryUtils.getProviderUrl(),
    		                username, 
    		                password,
    		                activeDirectoryUtils.getDomain()
    		         );
		ldapPassWordValid.openConnection();
		if(ldapPassWordValid.getCtx()!=null){
			_logger.debug("Active Directory user " + username + "  is validate .");
			ldapPassWordValid.close();
			return true;
		}
		
		ldapPassWordValid.close();
		return false;
	}

	public ActiveDirectoryUtils getActiveDirectoryUtils() {
		return activeDirectoryUtils;
	}

	public void setActiveDirectoryUtils(ActiveDirectoryUtils activeDirectoryUtils) {
		this.activeDirectoryUtils = activeDirectoryUtils;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	@Override
	public boolean isMapping() {
		return mapping;
	}

	public void setMapping(boolean mapping) {
		this.mapping = mapping;
	}
}
