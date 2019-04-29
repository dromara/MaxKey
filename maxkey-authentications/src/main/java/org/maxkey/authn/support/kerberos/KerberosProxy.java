package org.maxkey.authn.support.kerberos;

public class KerberosProxy {
	
	String userdomain;
	
	String fullUserdomain;
	
	String crypto;
	
	String redirectUri;

	
	/**
	 * 
	 */
	public KerberosProxy() {
		super();
	}

	public String getUserdomain() {
		return userdomain;
	}

	public void setUserdomain(String userdomain) {
		this.userdomain = userdomain.toUpperCase();
	}

	public String getFullUserdomain() {
		return fullUserdomain;
	}

	public void setFullUserdomain(String fullUserdomain) {
		this.fullUserdomain = fullUserdomain.toUpperCase();
	}



	public String getCrypto() {
		return crypto;
	}

	public void setCrypto(String crypto) {
		this.crypto = crypto;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	@Override
	public String toString() {
		return "KerberosProxy [userdomain=" + userdomain + ", fullUserdomain="
				+ fullUserdomain + ", crypto=" + crypto
				+ ", redirectUri=" + redirectUri + "]";
	}
	
	
}
