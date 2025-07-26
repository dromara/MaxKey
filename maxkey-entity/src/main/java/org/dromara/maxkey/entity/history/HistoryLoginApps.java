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
 

package org.dromara.maxkey.entity.history;

import java.io.Serializable;
import java.util.Date;

import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Crystal.Sea
 *
 */
@Entity
@Table(name = "MXK_HISTORY_LOGIN_APPS")  
public class HistoryLoginApps extends JpaEntity  implements Serializable {
	
	private static final long serialVersionUID = 5085201575292304749L;
	@Id
	@Column
	@GeneratedValue
	String id;
	@Column
	private String sessionId;
	@Column
	private String appId;
	@Column
	private String appName;
	@Column
	private String userId;
	@Column
	private String username;
	@Column
	private String displayName;
	@Column
	private Date loginTime;
	@Column
	private String instId;

	private String instName;
	String startDate;
	String endDate;
	
	/**
	 * 
	 */
	public HistoryLoginApps() {
		super();
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
	public HistoryLoginApps(String sessionId, String appId) {
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
	public Date getLoginTime() {
		return loginTime;
	}
	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * @param uid the uid to set
	 */
	public void setUserId(String uid) {
		this.userId = uid;
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


	public String getInstId() {
		return instId;
	}


	public void setInstId(String instId) {
		this.instId = instId;
	}


	public String getInstName() {
		return instName;
	}


	public void setInstName(String instName) {
		this.instName = instName;
	}


	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("HistoryLoginApps [id=");
        builder.append(id);
        builder.append(", sessionId=");
        builder.append(sessionId);
        builder.append(", appId=");
        builder.append(appId);
        builder.append(", appName=");
        builder.append(appName);
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", username=");
        builder.append(username);
        builder.append(", displayName=");
        builder.append(displayName);
        builder.append(", loginTime=");
        builder.append(loginTime);
        builder.append(", startDate=");
        builder.append(startDate);
        builder.append(", endDate=");
        builder.append(endDate);
        builder.append("]");
        return builder.toString();
    }
}
