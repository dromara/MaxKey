package org.maxkey.web;

public class KerberosTokenConfig {
	boolean diagnose;
	String tokenPostLocation;
	Integer validity;
	String userDomain;
	String crypto;
	
	
	/**
	 * 
	 */
	public KerberosTokenConfig() {
		// TODO Auto-generated constructor stub
	}
	public boolean isDiagnose() {
		return diagnose;
	}
	public void setDiagnose(boolean diagnose) {
		this.diagnose = diagnose;
	}
	public String getTokenPostLocation() {
		return tokenPostLocation;
	}
	public void setTokenPostLocation(String tokenPostLocation) {
		this.tokenPostLocation = tokenPostLocation;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public String getUserDomain() {
		return userDomain;
	}
	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}
	public String getCrypto() {
		return crypto;
	}
	public void setCrypto(String crypto) {
		this.crypto = crypto;
	}
	@Override
	public String toString() {
		return "KerberosTokenConfig [diagnose=" + diagnose
				+ ", tokenPostLocation=" + tokenPostLocation + ", validity="
				+ validity + ", userDomain=" + userDomain + ", crypto="
				+ crypto + "]";
	}
	
}

