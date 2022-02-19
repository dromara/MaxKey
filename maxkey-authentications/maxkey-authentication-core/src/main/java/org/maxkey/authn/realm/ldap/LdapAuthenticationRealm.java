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
 

package org.maxkey.authn.realm.ldap;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.realm.IAuthenticationServer;
import org.maxkey.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class LdapAuthenticationRealm  extends AbstractAuthenticationRealm{
	private final static Logger _logger = LoggerFactory.getLogger(LdapAuthenticationRealm.class);
	
	@NotNull
    @Size(min=1)
    private List<IAuthenticationServer> ldapServers;
	
	private boolean ldapSupport;
	
	/**
	 * 
	 */
	public LdapAuthenticationRealm() {
		
	}

	public LdapAuthenticationRealm(boolean ldapSupport) {
		this.ldapSupport = ldapSupport;
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
			 String username = userInfo.getUsername();
			 if(ldapServer.isMapping()) {//if ldap Context accountMapping equals YES 
				 username = userInfo.getWindowsAccount();
			 }
            _logger.debug("Attempting to authenticate {} at {}", username, ldapServer);
            isAuthenticated= ldapServer.authenticate(username, password);
            if (isAuthenticated ) {
            	return true;
            }
		 }
		return false;
	}

	public void setLdapServers(List<IAuthenticationServer> ldapServers) {
		this.ldapServers = ldapServers;
	}

	public boolean isLdapSupport() {
		return ldapSupport;
	}

	public void setLdapSupport(boolean ldapSupport) {
		this.ldapSupport = ldapSupport;
	}


}
