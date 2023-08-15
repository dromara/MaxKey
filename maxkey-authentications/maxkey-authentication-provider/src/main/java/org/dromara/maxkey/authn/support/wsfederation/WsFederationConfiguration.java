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
 


package org.dromara.maxkey.authn.support.wsfederation;

import org.opensaml.xml.security.x509.BasicX509Credential;
import org.springframework.core.io.Resource;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This class gathers configuration information for the WS Federation Identity Provider.
 * 
 */
public final class WsFederationConfiguration {
	
    @NotNull
    private String identifier;

    @NotNull
    private String url;

    @NotNull
    private String principal;
    
    @NotNull
    private String relyingParty;
    
    private String upnSuffix;
    @NotNull
    private List<Resource> signingCertificates;

    private int tolerance = 10000;

    private List<BasicX509Credential> signingWallet;

    private WsFederationAttributeMutator attributeMutator;

    private String logoutUrl;
 
    public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getRelyingParty() {
		return relyingParty;
	}

	public void setRelyingParty(String relyingParty) {
		this.relyingParty = relyingParty;
	}

	public List<BasicX509Credential> getSigningWallet() {
		return signingWallet;
	}

	public void setSigningWallet(List<BasicX509Credential> signingWallet) {
		this.signingWallet = signingWallet;
	}

    /**
     * gets the signing certificates.
     *
     * @return X509credentials of the signing certs
     */
    public List<BasicX509Credential> getSigningCertificates() {
        return this.signingWallet;
    }
    
	/**
     * sets the signing certs.
     *
     * @param signingCertificateFiles a list of certificate files to read in.
     */
    public void setSigningCertificates(final List<Resource> signingCertificateFiles) {
        this.signingCertificates = signingCertificateFiles;

        final List<BasicX509Credential> signingCerts = new ArrayList<BasicX509Credential>();

        for (Resource file : signingCertificateFiles) {
            signingCerts.add(WsFederationUtils.getSigningCredential(file));
        }

        this.signingWallet = signingCerts;
    }

    /**
     * gets the tolerance.
     *
     * @return the tolerance in milliseconds
     */
    public int getTolerance() {
        return tolerance;
    }

    /**
     * sets the tolerance of the validity of the timestamp token.
     *
     * @param tolerance the tolerance in milliseconds
     */
    public void setTolerance(final int tolerance) {
        this.tolerance = tolerance;
    }

    /**
     * gets the attributeMutator.
     *
     * @return an attributeMutator
     */
    public WsFederationAttributeMutator getAttributeMutator() {
        return attributeMutator;
    }

    /**
     * sets the attributeMutator.
     *
     * @param attributeMutator an attributeMutator
     */
    public void setAttributeMutator(final WsFederationAttributeMutator attributeMutator) {
        this.attributeMutator = attributeMutator;
    }

	public String getUpnSuffix() {
		return upnSuffix;
	}

	public void setUpnSuffix(String upnSuffix) {
		this.upnSuffix = upnSuffix;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

}
