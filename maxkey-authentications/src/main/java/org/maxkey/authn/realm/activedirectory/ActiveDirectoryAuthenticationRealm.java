package org.maxkey.authn.realm.activedirectory;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.realm.IAuthenticationServer;
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;


public class ActiveDirectoryAuthenticationRealm extends AbstractAuthenticationRealm{
	private final static Logger _logger = LoggerFactory.getLogger(ActiveDirectoryAuthenticationRealm.class);

	@NotNull
    @Size(min=1)
    private List<IAuthenticationServer> activeDirectoryServers;

	/**
	 * 
	 */
	public ActiveDirectoryAuthenticationRealm() {
		super();
	}

	/**
	 * @param jdbcTemplate
	 */
	public ActiveDirectoryAuthenticationRealm(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}


	@Override
	public boolean passwordMatches(UserInfo userInfo, String password) {
		boolean isAuthenticated=false;
		for (final IAuthenticationServer activeDirectoryServer : this.activeDirectoryServers) {
            _logger.debug("Attempting to authenticate {} at {}", userInfo.getUsername(), activeDirectoryServer);
            isAuthenticated= activeDirectoryServer.authenticate(userInfo.getUsername(), password);
            if (isAuthenticated ) {
            	return true;
            }
		 }
		if(!isAuthenticated){
			 throw new BadCredentialsException(WebContext.getI18nValue("login.error.password"));
		 }
		return false;
	}


	public void setActiveDirectoryServers(
			List<IAuthenticationServer> activeDirectoryServers) {
		this.activeDirectoryServers = activeDirectoryServers;
	}

}
