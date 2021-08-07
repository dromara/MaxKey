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
 

package org.maxkey.authn.support.socialsignon.service;

/**
 * @author Crystal.Sea
 *
 */
public class SocialSignOnProvider {
	
	private String provider;
	private String providerName;
	private String icon;
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String agentId;
	private String accountId;
	private String bindTime;
	private String unBindTime;
	private String lastLoginTime;
	private String state;
	private int sortOrder;
	private boolean hidden;
	
	private boolean userBind;
	
	/**
	 * 
	 */
	public SocialSignOnProvider() {

	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isUserBind() {
		return userBind;
	}

	public void setUserBind(boolean userBind) {
		this.userBind = userBind;
	}

	
    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    public String getUnBindTime() {
        return unBindTime;
    }

    public void setUnBindTime(String unBindTime) {
        this.unBindTime = unBindTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SocialSignOnProvider [provider=");
		builder.append(provider);
		builder.append(", providerName=");
		builder.append(providerName);
		builder.append(", clientId=");
		builder.append(clientId);
		builder.append(", clientSecret=");
		builder.append(clientSecret);
		builder.append(", agentId=");
		builder.append(agentId);
		builder.append(", accountId=");
		builder.append(accountId);
		builder.append(", bindTime=");
		builder.append(bindTime);
		builder.append(", unBindTime=");
		builder.append(unBindTime);
		builder.append(", lastLoginTime=");
		builder.append(lastLoginTime);
		builder.append(", sortOrder=");
		builder.append(sortOrder);
		builder.append(", userBind=");
		builder.append(userBind);
		builder.append("]");
		return builder.toString();
	}
	
	

}
