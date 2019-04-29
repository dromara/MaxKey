package org.maxkey.authz.saml.common;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AuthnRequestInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9115030955516039900L;
	private final String assertionConumerURL;
	private final String authnRequestID;
	
	public AuthnRequestInfo(String assertionConumerURL, String authnRequestID) {
		super();
		this.assertionConumerURL = assertionConumerURL;
		this.authnRequestID = authnRequestID;
	}
	
	public AuthnRequestInfo() {
		super();
		this.assertionConumerURL = "";
		this.authnRequestID = null;
	}
	
	public String getAssertionConumerURL() {
		return assertionConumerURL;
	}
	
	
	public String getAuthnRequestID() {
		return authnRequestID;
	}

	@Override
	 public String toString() {
	     return new ToStringBuilder(this).
	       append("assertionConumerURL", assertionConumerURL).
	       append("authnRequestID",authnRequestID).
	       toString();
	}
	
	
}
