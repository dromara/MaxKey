package org.maxkey.client.oauth.builder.api;

import org.maxkey.client.oauth.model.*;

public class OAuthApi10a extends DefaultApi10a
{
	//private static final String REQUEST_TOKEN_URL = "http://api.t.sina.com.cn/oauth/request_token";
	//private static final String ACCESS_TOKEN_URL = "http://api.t.sina.com.cn/oauth/access_token";
	//private static final String AUTHORIZE_URL = "http://api.t.sina.com.cn/oauth/authorize?oauth_token=%s";
	private String requestTokenUrl;
	private String accessTokenUrl;
	private String authorizeUrl;

	public OAuthApi10a(String requestTokenUrl,String authorizeUrl, String accessTokenUrl) {
		super();
		this.requestTokenUrl = requestTokenUrl;
		this.authorizeUrl = authorizeUrl;
		this.accessTokenUrl = accessTokenUrl;
	}

	@Override
	public String getRequestTokenEndpoint()
	{
		return requestTokenUrl;
	}

	@Override
	public String getAccessTokenEndpoint()
	{
		return accessTokenUrl;
	}

	@Override
	public String getAuthorizationUrl(Token requestToken)
	{
		return String.format(authorizeUrl, requestToken.getToken());
	}

	public String getRequestTokenUrl() {
		return requestTokenUrl;
	}

	public void setRequestTokenUrl(String requestTokenUrl) {
		this.requestTokenUrl = requestTokenUrl;
	}

	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}

	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}

	public String getAuthorizeUrl() {
		return authorizeUrl;
	}

	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}

	@Override
	public String toString() {
		return "OAuthApi10a [requestTokenUrl=" + requestTokenUrl
				+ ", accessTokenUrl=" + accessTokenUrl + ", authorizeUrl="
				+ authorizeUrl + "]";
	}
}
