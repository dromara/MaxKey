/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authn.session;

import java.io.Serializable;

import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.apps.AppsCasDetails;

public class VisitedDto  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6694914707659511202L;

	/**
	 * appId or client id
	 */
	String appId;
	/**
	 * protocol
	 */
	String protocol;
	/**
	 * ticket
	 */
	String ticket;
	/**
	 * token
	 */
	String token;
	
	/**
	 * refreshToken
	 */
	String refreshToken;
	/**
	 * logoutType
	 */
	int logoutType;
	/**
	 * logoutUrl
	 */
	String logoutUrl;
	
	
	public VisitedDto(AppsCasDetails app,String ticket ) {
		this.appId = app.getId();
		this.protocol = app.getProtocol();
		this.logoutType = app.getLogoutType();
		this.logoutUrl = app.getLogoutUrl();
		this.ticket = ticket;
	}
	
	public VisitedDto(Apps app,String ticket ) {
		this.appId = app.getId();
		this.protocol = app.getProtocol();
		this.logoutType = app.getLogoutType();
		this.logoutUrl = app.getLogoutUrl();
		this.ticket = ticket;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public int getLogoutType() {
		return logoutType;
	}

	public void setLogoutType(int logoutType) {
		this.logoutType = logoutType;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VisitedDto [appId=");
		builder.append(appId);
		builder.append(", protocol=");
		builder.append(protocol);
		builder.append(", ticket=");
		builder.append(ticket);
		builder.append(", token=");
		builder.append(token);
		builder.append(", refreshToken=");
		builder.append(refreshToken);
		builder.append(", logoutType=");
		builder.append(logoutType);
		builder.append(", logoutUrl=");
		builder.append(logoutUrl);
		builder.append("]");
		return builder.toString();
	}
	
}
