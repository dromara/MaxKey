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
 

package org.maxkey.persistence.ldap;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Crystal
 *
 */
public class ActiveDirectoryUtils extends LdapUtils {
    private final static Logger _logger = LoggerFactory.getLogger(ActiveDirectoryUtils.class);

    public final static String sAMAccountName = "sAMAccountName";
    public final static String unicodePwd = "unicodePwd";
    public final static String CN = "CN";

    public final static String servicePrincipalName = "servicePrincipalName";
    public final static String userPrincipalName = "userPrincipalName";
    public final static String userAccountControl = "userAccountControl";

    protected String domain;

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

    // connect to ActiveDirectory server
    @Override
    public DirContext openConnection() {
        _logger.info("PROVIDER_URL:" + providerUrl);
        _logger.info("SECURITY_PRINCIPAL:" + principal);
        _logger.info("SECURITY_CREDENTIALS:" + credentials);
        // LDAP
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.setProperty(Context.URL_PKG_PREFIXES, "com.sun.jndi.url");
        props.setProperty(Context.REFERRAL, referral);
        props.setProperty(Context.SECURITY_AUTHENTICATION, "simple");

        props.setProperty(Context.PROVIDER_URL, providerUrl);
        if (domain.indexOf(".") > -1) {
            domain = domain.substring(0, domain.indexOf("."));
        }
        _logger.info("PROVIDER_DOMAIN:" + domain);
        String activeDirectoryPrincipal = domain + "\\" + principal;
        _logger.debug("Active Directory SECURITY_PRINCIPAL : " + activeDirectoryPrincipal);
        props.setProperty(Context.SECURITY_PRINCIPAL, activeDirectoryPrincipal);
        props.setProperty(Context.SECURITY_CREDENTIALS, credentials);

        if (ssl && providerUrl.toLowerCase().startsWith("ldaps")) {
            System.setProperty("javax.net.ssl.trustStore", trustStore);
            System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
            props.put(Context.SECURITY_PROTOCOL, "ssl");
            props.put(Context.REFERRAL, "follow");
        }

        return InitialDirContext(props);
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain.toUpperCase();
    }

}
