package org.maxkey.domain;

import java.io.Serializable;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;


/**
 * @author Crystal.Sea
 *
 */
public class LoginHistory  extends JpaBaseDomain  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1321470643357719383L;
	
	String sessionId;
	String uid;
	String username;
	String displayName;
	
	String loginType;
	String message;
	
	String code;
	
	String provider;
	
	String sourceIp;
	String browser;
	String platform;
	String application;
	String loginUrl;
	
	String loginTime;
	String logoutTime;
	
	
	public LoginHistory() {
		super();
	}


	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}


	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}


	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	/**
	 * @return the sourceIp
	 */
	public String getSourceIp() {
		return sourceIp;
	}


	/**
	 * @param sourceIp the sourceIp to set
	 */
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}


	/**
	 * @return the loginType
	 */
	public String getLoginType() {
		return loginType;
	}


	/**
	 * @param loginType the loginType to set
	 */
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}


	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * @return the loginTime
	 */
	public String getLoginTime() {
		return loginTime;
	}


	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}


	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}


	/**
	 * @return the logoutTime
	 */
	public String getLogoutTime() {
		return logoutTime;
	}


	/**
	 * @param logoutTime the logoutTime to set
	 */
	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}


	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}


	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	/**
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}


	/**
	 * @param browser the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}


	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}


	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}


	/**
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}


	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}


	/**
	 * @return the loginUrl
	 */
	public String getLoginUrl() {
		return loginUrl;
	}


	/**
	 * @param loginUrl the loginUrl to set
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginHistory [sessionId=" + sessionId + ", uid=" + uid
				+ ", username=" + username + ", loginType=" + loginType
				+ ", message=" + message + ", code=" + code + ", provider="
				+ provider +  ", sourceIp="
				+ sourceIp + ", browser=" + browser + ", platform=" + platform
				+ ", application=" + application + ", loginUrl=" + loginUrl
				+ ", loginTime=" + loginTime + ", logoutTime=" + logoutTime
				+ "]";
	}

	
	


}
