package org.maxkey.client.oauth.builder.api;

import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.oauth.extractors.AccessTokenExtractor;
import org.maxkey.client.oauth.extractors.GsonJsonTokenExtractor;
import org.maxkey.client.oauth.model.OAuthConfig;



/**
 * OAuth 2.0 api.
 */
public class ConnsecPasswordApi20 extends DefaultApi20 {

	private String accessTokenUrl;
	private String grantType = "password";
	private String scope = "&scope=%s";
	private String accessTokenMethod = "POST";

	private static final String AUTHORIZE_PARAM = "&client_id=%s&client_secret=%s&username=%s&password=%s";

	public ConnsecPasswordApi20(String accessTokenUrl) {
		super();
		this.accessTokenUrl = accessTokenUrl;
	}

	@Override
	public HttpVerb getAccessTokenVerb() {
		if (accessTokenMethod != null
				&& accessTokenMethod.toUpperCase().equals("POST")) {
			return HttpVerb.POST;
		} else {
			return HttpVerb.GET;
		}
	}

	@Override
	public AccessTokenExtractor getAccessTokenExtractor() {
		return new GsonJsonTokenExtractor();
	}

	@Override
	public String getAccessTokenEndpoint() {
		if (accessTokenUrl.indexOf("?") > 0) {
			return accessTokenUrl + "&grant_type=" + grantType+ AUTHORIZE_PARAM;
		} else {
			return accessTokenUrl + "?grant_type=" + grantType+ AUTHORIZE_PARAM;
		}
	}

	public String getAuthorizationUrl(OAuthConfig config,String username,String password) {
			return String.format(getAccessTokenEndpoint(),
					config.getApiKey(),
					config.getApiSecret(),
					username,
					password);
	}

	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}

	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}

	@Override
	public String toString() {
		return "OAuthApi20 [accessTokenUrl=" + accessTokenUrl + ", grantType="
				+ grantType + ", scope=" + scope + "]";
	}

	@Override
	public String getAuthorizationUrl(OAuthConfig config) {
		return "";
	}

	@Override
	public String getGrantType() {
		// TODO Auto-generated method stub
		return "authorization_code";
	}

}
