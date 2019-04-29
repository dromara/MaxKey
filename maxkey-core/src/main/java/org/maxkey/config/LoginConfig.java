package org.maxkey.config;

public class LoginConfig {
	
	boolean captcha;
	
	boolean oneTimePwd;
	
	boolean socialSignOn;
	
	
	boolean kerberos;
	
	boolean remeberMe;
	
	boolean wsFederation;
	
	String defaultUri;

	
	/**
	 * 
	 */
	public LoginConfig() {
		// TODO Auto-generated constructor stub
	}

	public boolean isCaptcha() {
		return captcha;
	}

	public void setCaptcha(boolean captcha) {
		this.captcha = captcha;
	}



	public boolean isOneTimePwd() {
		return oneTimePwd;
	}

	public void setOneTimePwd(boolean oneTimePwd) {
		this.oneTimePwd = oneTimePwd;
	}

	public boolean isSocialSignOn() {
		return socialSignOn;
	}

	public void setSocialSignOn(boolean socialSignOn) {
		this.socialSignOn = socialSignOn;
	}

	public boolean isKerberos() {
		return kerberos;
	}

	public void setKerberos(boolean kerberos) {
		this.kerberos = kerberos;
	}

	public String getDefaultUri() {
		return defaultUri;
	}

	public void setDefaultUri(String defaultUri) {
		this.defaultUri = defaultUri;
	}

	public boolean isRemeberMe() {
		return remeberMe;
	}

	public void setRemeberMe(boolean remeberMe) {
		this.remeberMe = remeberMe;
	}
	
	public boolean isWsFederation() {
		return wsFederation;
	}

	public void setWsFederation(boolean wsFederation) {
		this.wsFederation = wsFederation;
	}

	@Override
	public String toString() {
		return "LoginConfig [captcha=" + captcha + ", oneTimePwd=" + oneTimePwd
				+ ", socialSignOn=" + socialSignOn + ", kerberos=" + kerberos
				+ ", remeberMe=" + remeberMe + ", wsFederation=" + wsFederation
				+ ", defaultUri=" + defaultUri + "]";
	}
}
