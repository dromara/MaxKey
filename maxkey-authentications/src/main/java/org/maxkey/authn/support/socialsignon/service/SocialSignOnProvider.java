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
	private String accountId;
	private int sortOrder;
	
	
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

    @Override
    public String toString() {
        return "SocialSignOnProvider [provider=" + provider + ", providerName=" + providerName + ", icon=" + icon
                + ", clientId=" + clientId + ", clientSecret=" + clientSecret + ", accountId=" + accountId
                + ", sortOrder=" + sortOrder + ", userBind=" + userBind + "]";
    }
	
	

}
