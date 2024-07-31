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

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.dromara.maxkey.authn.realm.IAuthenticationServer;
import org.dromara.maxkey.ldap.LdapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Crystal.Sea
 *
 */
public final class StandardLdapServer implements IAuthenticationServer {
	private static final  Logger _logger = LoggerFactory.getLogger(StandardLdapServer.class);
	
	LdapUtils ldapUtils;
	
	String filterAttribute;
	
	boolean mapping;
	
	/* (non-Javadoc)
	 * @see com.connsec.web.authentication.realm.IAuthenticationServer#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticate(String username, String password) {
		String queryFilter = String.format(filterAttribute, username);
		_logger.info(" filter : " + queryFilter);
		String dn="";
		SearchControls constraints = new SearchControls();
		constraints.setSearchScope(ldapUtils.getSearchScope());
		try {
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), queryFilter, constraints);
			
			if (results == null || !results.hasMore()) {
				_logger.error("Ldap user "+username +" not found . ");
				return false;
			}else{
				while (results != null && results.hasMore()) {
					SearchResult sr = (SearchResult) results.next();
					//String rdn = sr.getName();
					dn = sr.getNameInNamespace();
					_logger.debug("Directory user dn is "+dn+" .");
				}
			}
		} catch (NamingException e) {
			_logger.error("query throw NamingException:" + e.getMessage());
		} finally {
			//ldapUtils.close();
		}
		
		LdapUtils ldapPassWordValid=new LdapUtils(ldapUtils.getProviderUrl(),dn,password);
		ldapPassWordValid.openConnection();
		if(ldapPassWordValid.getCtx()!=null){
			_logger.debug("Directory user " + username + "  is validate .");
			ldapPassWordValid.close();
			return true;
		}
		return false;
	}
	
	public LdapUtils getLdapUtils() {
		return ldapUtils;
	}
	public void setLdapUtils(LdapUtils ldapUtils) {
		this.ldapUtils = ldapUtils;
	}
	public String getFilterAttribute() {
		return filterAttribute;
	}
	public void setFilterAttribute(String filterAttribute) {
		this.filterAttribute = filterAttribute;
	}

	@Override
	public boolean isMapping() {
		return mapping;
	}

	public void setMapping(boolean mapping) {
		this.mapping = mapping;
	}

}
