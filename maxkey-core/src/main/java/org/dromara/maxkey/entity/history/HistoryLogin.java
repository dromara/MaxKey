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
@Table(name = "MXK_HISTORY_LOGIN")  
public class HistoryLogin  extends JpaEntity  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1321470643357719383L;
	@Id
	@Column
	@GeneratedValue
	String id;
	@Column
	String sessionId;
	@Column
	int category;
	@Column
	String userId;
	@Column
	String username;
	@Column
	String displayName;
	@Column
	String loginType;
	@Column
	String message;
	@Column
	String code;
	@Column
	String provider;
	@Column
	String sourceIp;
	@Column
	String country;
	@Column
	String province;
	@Column
	String city;
	@Column
	String location;
	@Column
	String browser;
	@Column
	String platform;
	@Column
	String application;
	@Column
	Date loginTime;
	@Column
	Date logoutTime;
	@Column
	int sessionStatus;
	@Column
	private String instId;

	private String instName;
	
	String startDate;
	String endDate;
	
	public HistoryLogin() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
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

	public int getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(int sessionStatus) {
        this.sessionStatus = sessionStatus;
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
		builder.append("HistoryLogin [id=");
		builder.append(id);
		builder.append(", sessionId=");
		builder.append(sessionId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", username=");
		builder.append(username);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append(", loginType=");
		builder.append(loginType);
		builder.append(", message=");
		builder.append(message);
		builder.append(", code=");
		builder.append(code);
		builder.append(", provider=");
		builder.append(provider);
		builder.append(", sourceIp=");
		builder.append(sourceIp);
		builder.append(", country=");
		builder.append(country);
		builder.append(", province=");
		builder.append(province);
		builder.append(", city=");
		builder.append(city);
		builder.append(", location=");
		builder.append(location);
		builder.append(", browser=");
		builder.append(browser);
		builder.append(", platform=");
		builder.append(platform);
		builder.append(", application=");
		builder.append(application);
		builder.append(", loginTime=");
		builder.append(loginTime);
		builder.append(", logoutTime=");
		builder.append(logoutTime);
		builder.append(", instId=");
		builder.append(instId);
		builder.append(", instName=");
		builder.append(instName);
		builder.append(", sessionStatus=");
		builder.append(sessionStatus);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append("]");
		return builder.toString();
	}
}
