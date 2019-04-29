package org.maxkey.authn.support.kerberos;


public class KerberosToken {

	private String principal;
	
	private String fullPrincipal;
	
	private String notOnOrAfter;
	
	private String userDomain;

	/**
	 * 
	 */
	public KerberosToken() {
		super();
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getFullPrincipal() {
		return fullPrincipal;
	}

	public void setFullPrincipal(String fullPrincipal) {
		this.fullPrincipal = fullPrincipal;
	}
	
	public String getNotOnOrAfter() {
		return notOnOrAfter;
	}

	public void setNotOnOrAfter(String notOnOrAfter) {
		this.notOnOrAfter = notOnOrAfter;
	}

	public String getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}

	@Override
	public String toString() {
		return "KerberosToken [principal=" + principal + ", fullPrincipal="
				+ fullPrincipal + ", notOnOrAfter=" + notOnOrAfter
				+ ", userDomain=" + userDomain + "]";
	}

}
