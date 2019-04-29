package org.maxkey.authn.realm.ldap;

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

public class LdapAuthenticationRealm  extends AbstractAuthenticationRealm{
	private final static Logger _logger = LoggerFactory.getLogger(LdapAuthenticationRealm.class);
	
	@NotNull
    @Size(min=1)
    private List<IAuthenticationServer> ldapServers;
	
	/**
	 * 
	 */
	public LdapAuthenticationRealm() {
		
	}

	/**
	 * @param jdbcTemplate
	 */
	public LdapAuthenticationRealm(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}
	
	
	@Override
	public boolean passwordMatches(UserInfo userInfo, String password) {
		 boolean isAuthenticated=false;
		 for (final IAuthenticationServer ldapServer : this.ldapServers) {
            _logger.debug("Attempting to authenticate {} at {}", userInfo.getUsername(), ldapServer);
            isAuthenticated= ldapServer.authenticate(userInfo.getUsername(), password);
            if (isAuthenticated ) {
            	return true;
            }
		 }
		 if(!isAuthenticated){
			 throw new BadCredentialsException(WebContext.getI18nValue("login.error.password"));
		 }
		return false;
	}

	public void setLdapServers(List<IAuthenticationServer> ldapServers) {
		this.ldapServers = ldapServers;
	}


}