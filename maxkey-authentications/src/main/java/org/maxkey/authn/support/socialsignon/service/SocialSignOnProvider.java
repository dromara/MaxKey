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
	private String callBack;
	private String authorizeUrl;
	private String accessTokenUrl;
	private String accessTokenMethod;
	private String scope;
	private String verifierCode;
	private String accountUrl;
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
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

	public String getAuthorizeUrl() {
		return authorizeUrl;
	}
	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}
	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}
	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}
	public String getAccessTokenMethod() {
		return accessTokenMethod;
	}
	public void setAccessTokenMethod(String accessTokenMethod) {
		this.accessTokenMethod = accessTokenMethod;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getVerifierCode() {
		return verifierCode;
	}
	public void setVerifierCode(String verifierCode) {
		this.verifierCode = verifierCode;
	}
	public String getAccountUrl() {
		return accountUrl;
	}
	public void setAccountUrl(String accountUrl) {
		this.accountUrl = accountUrl;
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

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public boolean isUserBind() {
		return userBind;
	}

	public void setUserBind(boolean userBind) {
		this.userBind = userBind;
	}

	@Override
	public String toString() {
		return "SocialSignOnProvider [provider=" + provider + ", providerName="
				+ providerName + ", icon=" + icon + ", clientId=" + clientId
				+ ", clientSecret=" + clientSecret + ", authorizeUrl="
				+ authorizeUrl + ", accessTokenUrl=" + accessTokenUrl
				+ ", accessTokenMethod=" + accessTokenMethod + ", scope="
				+ scope + ", verifierCode=" + verifierCode + ", accountUrl="
				+ accountUrl + ", accountId=" + accountId + ", sortOrder="
				+ sortOrder + ", userBind=" + userBind + "]";
	}

}
