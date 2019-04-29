/*
 * Copyright 2009-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.kerberos.authentication.sun;

import java.security.Principal;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.kerberos.KerberosPrincipal;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.kerberos.authentication.KerberosTicketValidation;
import org.springframework.security.kerberos.authentication.KerberosTicketValidator;
import org.springframework.util.Assert;

/**
 * Implementation of {@link KerberosTicketValidator} which uses the SUN JAAS
 * login module, which is included in the SUN JRE, it will not work with an IBM JRE.
 * The whole configuration is done in this class, no additional JAAS configuration
 * is needed.
 *
 * @author Mike Wiesner
 * @author Jeremy Stone
 * @since 1.0
 */
public class SunJaasKerberosTicketValidator implements KerberosTicketValidator, InitializingBean {

    private String servicePrincipal;
    private Resource keyTabLocation;
    private Subject serviceSubject;
    private boolean holdOnToGSSContext;
    private boolean debug = false;
    private static final Log LOG = LogFactory.getLog(SunJaasKerberosTicketValidator.class);

    @Override
    public KerberosTicketValidation validateTicket(byte[] token) {
        try {
            return Subject.doAs(this.serviceSubject, new KerberosValidateAction(token));
        }
        catch (PrivilegedActionException e) {
            throw new BadCredentialsException("Kerberos validation not succesful", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.servicePrincipal, "servicePrincipal must be specified");
        Assert.notNull(this.keyTabLocation, "keyTab must be specified");
        if (keyTabLocation instanceof ClassPathResource) {
			LOG.warn("Your keytab is in the classpath. This file needs special protection and shouldn't be in the classpath. JAAS may also not be able to load this file from classpath.");
        }
        String keyTabLocationAsString = this.keyTabLocation.getURL().toExternalForm();
        // We need to remove the file prefix (if there is one), as it is not supported in Java 7 anymore.
        // As Java 6 accepts it with and without the prefix, we don't need to check for Java 7
        if (keyTabLocationAsString.startsWith("file:"))
        {
        	keyTabLocationAsString = keyTabLocationAsString.substring(5);
        }
        LoginConfig loginConfig = new LoginConfig(keyTabLocationAsString, this.servicePrincipal,
                this.debug);
        Set<Principal> princ = new HashSet<Principal>(1);
        princ.add(new KerberosPrincipal(this.servicePrincipal));
        Subject sub = new Subject(false, princ, new HashSet<Object>(), new HashSet<Object>());
        LoginContext lc = new LoginContext("", sub, null, loginConfig);
        lc.login();
        this.serviceSubject = lc.getSubject();
    }

    /**
     * The service principal of the application.
     * For web apps this is <code>HTTP/full-qualified-domain-name@DOMAIN</code>.
     * The keytab must contain the key for this principal.
     *
     * @param servicePrincipal service principal to use
     * @see #setKeyTabLocation(Resource)
     */
    public void setServicePrincipal(String servicePrincipal) {
        this.servicePrincipal = servicePrincipal;
    }

    /**
     * <p>The location of the keytab. You can use the normale Spring Resource
     * prefixes like <code>file:</code> or <code>classpath:</code>, but as the
     * file is later on read by JAAS, we cannot guarantee that <code>classpath</code>
     * works in every environment, esp. not in Java EE application servers. You
     * should use <code>file:</code> there.
     *
     * This file also needs special protection, which is another reason to
     * not include it in the classpath but rather use <code>file:/etc/http.keytab</code>
     * for example.
     *
     * @param keyTabLocation The location where the keytab resides
     */
    public void setKeyTabLocation(Resource keyTabLocation) {
        this.keyTabLocation = keyTabLocation;
    }

    /**
     * Enables the debug mode of the JAAS Kerberos login module.
     *
     * @param debug default is false
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Determines whether to hold on to the {@link GSSContext GSS security context} or
     * otherwise {@link GSSContext#dispose() dispose} of it immediately (the default behaviour).
     * <p>Holding on to the GSS context allows decrypt and encrypt operations for subsequent
     * interactions with the principal.
     *
     * @param holdOnToGSSContext true if should hold on to context
     */
    public void setHoldOnToGSSContext(boolean holdOnToGSSContext) {
        this.holdOnToGSSContext = holdOnToGSSContext;
    }

    /**
     * This class is needed, because the validation must run with previously generated JAAS subject
     * which belongs to the service principal and was loaded out of the keytab during startup.
     */
    private class KerberosValidateAction implements PrivilegedExceptionAction<KerberosTicketValidation> {
        byte[] kerberosTicket;

        public KerberosValidateAction(byte[] kerberosTicket) {
            this.kerberosTicket = kerberosTicket;
        }

        @Override
        public KerberosTicketValidation run() throws Exception {
			GSSContext context = GSSManager.getInstance().createContext((GSSCredential) null);
			byte[] responseToken = context.acceptSecContext(kerberosTicket, 0, kerberosTicket.length);
			GSSName gssName = context.getSrcName();
			if (gssName == null) {
				throw new BadCredentialsException("GSSContext name of the context initiator is null");
			}
			if (!holdOnToGSSContext) {
				context.dispose();
			}
			return new KerberosTicketValidation(gssName.toString(), servicePrincipal, responseToken, context);
        }
    }

    /**
     * Normally you need a JAAS config file in order to use the JAAS Kerberos Login Module,
     * with this class it is not needed and you can have different configurations in one JVM.
     */
    private static class LoginConfig extends Configuration {
        private String keyTabLocation;
        private String servicePrincipalName;
        private boolean debug;

        public LoginConfig(String keyTabLocation, String servicePrincipalName, boolean debug) {
            this.keyTabLocation = keyTabLocation;
            this.servicePrincipalName = servicePrincipalName;
            this.debug = debug;
        }

        @Override
        public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("useKeyTab", "true");
            options.put("keyTab", this.keyTabLocation);
            options.put("principal", this.servicePrincipalName);
            options.put("storeKey", "true");
            options.put("doNotPrompt", "true");
            if (this.debug) {
                options.put("debug", "true");
            }
            options.put("isInitiator", "false");

            return new AppConfigurationEntry[] { new AppConfigurationEntry("com.sun.security.auth.module.Krb5LoginModule",
                    AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options), };
        }

    }

}
