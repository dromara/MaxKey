package org.maxkey.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;

/**
 * @author Crystal.Sea
 *
 */

@Table(name = "LOGIN_APPS_HISTORY")  
public class LoginAppsHistory extends JpaBaseDomain {
	
	private static final long serialVersionUID = 5085201575292304749L;
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO,generator="uuid")
	String id;
	@Column
	private String sessionId;
	@Column
	private String appId;
	@Column
	private String appName;
	@Column
	private String uid;
	@Column
	private String username;
	@Column
	private String displayName;
	@Column
	private String loginTime;
	
	String startDate;
	String endDate;
	
	/**
	 * 
	 */
	public LoginAppsHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @param sessionId
	 * @param appId
	 */
	public LoginAppsHistory(String sessionId, String appId) {
		super();
		this.sessionId = sessionId;
		this.appId = appId;
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
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
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


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginAppsHistory [sessionId=" + sessionId + ", appId=" + appId
				+ ", appName=" + appName + ", uid=" + uid + ", username="
				+ username + ", displayName=" + displayName + ", loginTime="
				+ loginTime + "]";
	}
}
