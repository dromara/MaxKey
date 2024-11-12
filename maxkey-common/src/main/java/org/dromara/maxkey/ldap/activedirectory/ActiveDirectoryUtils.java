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
 

package org.dromara.maxkey.ldap.activedirectory;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.dromara.maxkey.ldap.LdapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Crystal
 *
 */
public class ActiveDirectoryUtils extends LdapUtils {
    private static final Logger _logger = LoggerFactory.getLogger(ActiveDirectoryUtils.class);

    protected String domain;
    
    String activeDirectoryDomain;
    /**
     * 
     */
    public ActiveDirectoryUtils() {
        super();
    }

    public ActiveDirectoryUtils(String providerUrl, String principal, String credentials, String baseDN,
            String domain) {
        this.providerUrl = providerUrl;
        this.principal = principal;
        this.credentials = credentials;
        this.searchScope = SearchControls.SUBTREE_SCOPE;
        this.baseDN = baseDN;
        this.domain = domain.toUpperCase();
    }

    public ActiveDirectoryUtils(String providerUrl, String principal, String credentials, String domain) {
        this.providerUrl = providerUrl;
        this.principal = principal;
        this.credentials = credentials;
        this.searchScope = SearchControls.SUBTREE_SCOPE;
        this.domain = domain.toUpperCase();
    }

    public ActiveDirectoryUtils(DirContext dirContext) {
        this.ctx = dirContext;
    }

    @Override
    protected void initEnvironment() {
    	 if(props == null) {
    		_logger.debug("PROVIDER_URL {}" , providerUrl);
            _logger.debug("SECURITY_PRINCIPAL {}" , principal);
            //no log credentials 
            //_logger.trace("SECURITY_CREDENTIALS {}" , credentials);
 	        // LDAP
 	        props = new Properties();
 	        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
 	        props.setProperty(Context.URL_PKG_PREFIXES, "com.sun.jndi.url");
 	        props.setProperty(Context.REFERRAL, referral);
 	        props.setProperty(Context.SECURITY_AUTHENTICATION, "simple");
 	
 	        props.setProperty(Context.PROVIDER_URL, providerUrl);
 	        
 	        if (domain.indexOf(".") > -1) {
 	        	activeDirectoryDomain = domain.substring(0, domain.indexOf("."));
 	        }else {
 	        	activeDirectoryDomain = domain;
 	        }
 	        
 	        _logger.info("PROVIDER_DOMAIN : {} for {}" ,activeDirectoryDomain, domain);
 	        String activeDirectoryPrincipal = activeDirectoryDomain + "\\" + principal;
 	        _logger.debug("Active Directory SECURITY_PRINCIPAL : {}" , activeDirectoryPrincipal);
 	        props.setProperty(Context.SECURITY_PRINCIPAL, activeDirectoryPrincipal);
 	        props.setProperty(Context.SECURITY_CREDENTIALS, credentials);
 	
 	        if (ssl && providerUrl.toLowerCase().startsWith("ldaps")) {
 	        	_logger.info("ldaps security protocol.");
 	            System.setProperty("javax.net.ssl.trustStore", trustStore);
 	            System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
 	            props.put(Context.SECURITY_PROTOCOL, "ssl");
 	        }
 	        props.put(Context.REFERRAL, "follow");
     	 }
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain.toUpperCase();
    }

}
