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
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Crystal
 *
 */
public class LdapUtils {
    private static  final Logger _logger = LoggerFactory.getLogger(LdapUtils.class);

    public static final  String propertyBaseDN = "baseDN";
    public static final  String propertyDomain = "domain";
    public static final  String propertyTrustStore = "trustStore";
    public static final  String propertyTrustStorePassword = "trustStorePassword";
            
    public static final  String uid = "uid";
    public static final  String userPassword = "userPassword";
    public static final  String cn = "cn";
    public static final  String displayName = "displayName";
    public static final  String givenName = "givenName";
    public static final  String sn = "sn";
    public static final  String mobile = "mobile";
    public static final  String mail = "mail";
    public static final  String employeeNumber = "employeeNumber";
    public static final  String ou = "ou";
    public static final  String manager = "manager";
    public static final  String department = "department";
    public static final  String departmentNumber = "departmentNumber";
    public static final  String title = "title";

    protected DirContext ctx;
    protected String baseDN;
    protected String providerUrl;
    protected String principal;
    protected String credentials;
    protected String referral = "ignore";
    protected String trustStore;
    protected String trustStorePassword;
    protected boolean ssl;
    protected int searchScope;

    /**
     * 
     */
    public LdapUtils() {
        super();
        this.searchScope = SearchControls.SUBTREE_SCOPE;
    }

    public LdapUtils(String providerUrl, String principal, String credentials) {
        this.providerUrl = providerUrl;
        this.principal = principal;
        this.credentials = credentials;
        this.searchScope = SearchControls.SUBTREE_SCOPE;
    }

    public LdapUtils(String providerUrl, String principal, String credentials, String baseDN) {
        this.providerUrl = providerUrl;
        this.principal = principal;
        this.credentials = credentials;
        this.searchScope = SearchControls.SUBTREE_SCOPE;
        this.baseDN = baseDN;
    }

    public LdapUtils(DirContext dirContext) {
        this.ctx = dirContext;
    }

    public void setSearchSubTreeScope() {
        this.searchScope = SearchControls.SUBTREE_SCOPE;
    }

    public void setSearchOneLevelScope() {
        this.searchScope = SearchControls.ONELEVEL_SCOPE;
    }

    protected DirContext InitialDirContext(Properties properties) {
        try {
            ctx = new InitialDirContext(properties);
            _logger.info("connect to ldap " + providerUrl + " seccessful.");
        } catch (NamingException e) {
            _logger.error("connect to ldap " + providerUrl + " fail.");
            e.printStackTrace();
            _logger.error(e.getMessage());
        }
        return ctx;
    }

    // connect to ldap server
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
        props.setProperty(Context.SECURITY_PRINCIPAL, principal);
        props.setProperty(Context.SECURITY_CREDENTIALS, credentials);

        if (ssl && providerUrl.toLowerCase().startsWith("ldaps")) {
            System.setProperty("javax.net.ssl.trustStore", trustStore);
            System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
            props.put(Context.SECURITY_PROTOCOL, "ssl");
            props.put(Context.REFERRAL, "follow");
        }

        return InitialDirContext(props);
    }

    public boolean authenticate() {
        openConnection();
        if (this.ctx != null) {
            close();
            return true;
        } else {
            return false;
        }
    }

    public void close() {
        close(this.ctx);
    }

    public void close(DirContext ctx) {
        if (null != ctx) {
            try {
                ctx.close();
            } catch (Exception e) {
                e.printStackTrace();
                _logger.error(e.getMessage());
            } finally {
                ctx = null;
            }
        }
    }

    public DirContext getCtx() {
        return ctx;
    }

    public DirContext getConnection() {
        if (ctx == null) {
            openConnection();
        }

        return ctx;
    }

    /**
     * @return the baseDN
     */
    public String getBaseDN() {
        return baseDN;
    }

    /**
     * @param baseDN the baseDN to set
     */
    public void setBaseDN(String baseDN) {
        this.baseDN = baseDN;
    }

    /**
     * @return the searchScope
     */
    public int getSearchScope() {
        return searchScope;
    }

    /**
     * @return the providerUrl
     */
    public String getProviderUrl() {
        return providerUrl;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    /**
     * @return the trustStore
     */
    public String getTrustStore() {
        return trustStore;
    }

    /**
     * @param trustStore the trustStore to set
     */
    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }

    /**
     * @return the ssl
     */
    public boolean isSsl() {
        return ssl;
    }

    /**
     * @param ssl the ssl to set
     */
    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    /**
     * @return the referral
     */
    public String getReferral() {
        return referral;
    }

    /**
     * @param referral the referral to set
     */
    public void setReferral(String referral) {
        this.referral = referral;
    }

    /**
     * @return the trustStorePassword
     */
    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    /**
     * @param trustStorePassword the trustStorePassword to set
     */
    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public static String getAttrStringValue(Attributes attrs, String elem) {
        String value = "";
        try {
            if (attrs.get(elem) != null) {
                for (int i = 0; i < attrs.get(elem).size(); i++) {
                    value += "," + attrs.get(elem).get(i).toString();
                }
                value = value.substring(1);
            }
        } catch (NamingException e) {
            e.printStackTrace();
            _logger.error(e.getMessage());
        }
        return value;
    }
}
