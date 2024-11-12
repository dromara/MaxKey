/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
package org.dromara.maxkey.authn.realm.ldap;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.authn.realm.IAuthenticationServer;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.cnf.CnfLdapContext;
import org.dromara.maxkey.ldap.LdapUtils;
import org.dromara.maxkey.ldap.activedirectory.ActiveDirectoryUtils;
import org.dromara.maxkey.persistence.service.CnfLdapContextService;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class LdapAuthenticationRealmService {
    protected static final Cache<String, LdapAuthenticationRealm> ldapRealmStore = 
            Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build();
    
    CnfLdapContextService ldapContextService;
    
    
    public LdapAuthenticationRealmService(CnfLdapContextService ldapContextService) {
		this.ldapContextService = ldapContextService;
	}

	public LdapAuthenticationRealm getByInstId(String instId) {
		LdapAuthenticationRealm authenticationRealm = ldapRealmStore.getIfPresent(instId);
		if(authenticationRealm == null) {
			List<CnfLdapContext> ldapContexts = 
					ldapContextService.find("where instid = ? and status = 1 ", new Object[]{instId}, new int[]{Types.VARCHAR});
			authenticationRealm = new LdapAuthenticationRealm(false);
			if(ldapContexts != null && ldapContexts.size()>0) {
				authenticationRealm.setLdapSupport(true);
				List<IAuthenticationServer> ldapAuthenticationServers = new ArrayList<IAuthenticationServer>();
				for(CnfLdapContext ldapContext : ldapContexts) { 
					if(ldapContext.getProduct().equalsIgnoreCase("ActiveDirectory")) {
						ActiveDirectoryServer ldapServer = new ActiveDirectoryServer();
			            ActiveDirectoryUtils  ldapUtils  = new ActiveDirectoryUtils(
			            								ldapContext.getProviderUrl(),
			            								ldapContext.getPrincipal(),
			            								PasswordReciprocal.getInstance().decoder(
			            										ldapContext.getCredentials()),
			            								ldapContext.getMsadDomain());
			            ldapServer.setActiveDirectoryUtils(ldapUtils);
			            if(ldapContext.getAccountMapping().equalsIgnoreCase("YES")) {
			            	ldapServer.setMapping(true);
			            }
			            ldapAuthenticationServers.add(ldapServer);
						
					}else {
						StandardLdapServer standardLdapServer=new StandardLdapServer();
						LdapUtils ldapUtils = new LdapUtils(
													ldapContext.getProviderUrl(),
													ldapContext.getPrincipal(),
													PasswordReciprocal.getInstance().decoder(
		            										ldapContext.getCredentials()),
													ldapContext.getBasedn());
						standardLdapServer.setLdapUtils(ldapUtils);
						standardLdapServer.setFilterAttribute(ldapContext.getFilters());
						if(ldapContext.getAccountMapping().equalsIgnoreCase("YES")) {
							standardLdapServer.setMapping(true);
			            }
						ldapAuthenticationServers.add(standardLdapServer);
					}
				}
				authenticationRealm.setLdapServers(ldapAuthenticationServers);
			}
			ldapRealmStore.put(instId, authenticationRealm);
		}
    	return authenticationRealm;
    	
    }
}
