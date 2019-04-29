package org.maxkey.web;

public class KerberosToken {

	String principal;
	String fullPrincipal;
	String notOnOrAfter;
	String userDomain;

	/**
	 * 
	 */
	public KerberosToken() {
		// TODO Auto-generated constructor stub
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
