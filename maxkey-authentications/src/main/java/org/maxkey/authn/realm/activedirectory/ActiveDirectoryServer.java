/**
 * 
 */
package org.maxkey.authn.realm.activedirectory;

import org.maxkey.authn.realm.IAuthenticationServer;
import org.maxkey.persistence.ldap.ActiveDirectoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Crystal.Sea
 *
 */
public final class ActiveDirectoryServer implements IAuthenticationServer {
	private final static Logger _logger = LoggerFactory.getLogger(ActiveDirectoryServer.class);

	ActiveDirectoryUtils activeDirectoryUtils;

	String filter;
	
	/* (non-Javadoc)
	 * @see com.connsec.web.authentication.realm.IAuthenticationServer#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticate(String username, String password) {
		ActiveDirectoryUtils ldapPassWordValid = new ActiveDirectoryUtils(activeDirectoryUtils.getProviderUrl(),
				activeDirectoryUtils.getDomain()+"\\" + username, password,activeDirectoryUtils.getDomain());
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

}
