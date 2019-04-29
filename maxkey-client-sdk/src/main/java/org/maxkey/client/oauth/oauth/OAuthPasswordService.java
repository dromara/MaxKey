package org.maxkey.client.oauth.oauth;

import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.http.Response;
import org.maxkey.client.oauth.builder.ServiceBuilder;
import org.maxkey.client.oauth.builder.api.ConnsecPasswordApi20;
import org.maxkey.client.oauth.model.OAuthConfig;
import org.maxkey.client.oauth.model.OAuthRequest;
import org.maxkey.client.oauth.model.Token;


/**
 * OAuth 2.0 api.
 */
public class OAuthPasswordService {

	private OAuthConfig config;
	
	private ConnsecPasswordApi20 passwordApi20;

	public OAuthPasswordService() {
		super();
	}

	public OAuthPasswordService(OAuthConfig config,ConnsecPasswordApi20 passwordApi20) {
		super();
		this.passwordApi20=passwordApi20;
		this.config = config;
	}

	public Token getAccessToken(String username, String password) {
		try {
			String accessTokenUrl=passwordApi20.getAuthorizationUrl(config, username, password);
			System.out.println(accessTokenUrl);
			OAuthRequest oauthRequest = new OAuthRequest(HttpVerb.GET,accessTokenUrl);
			Response response = oauthRequest.send();
			return passwordApi20.getAccessTokenExtractor().extract(response.getBody());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Response sendRequest(Token accessToken,HttpVerb requestMethod,String requestUrl) {
		OAuthRequest oauthRequest = new OAuthRequest(requestMethod, requestUrl);
		ServiceBuilder builder = new ServiceBuilder().provider(passwordApi20)
	    .apiKey(config.getApiKey())
	    .apiSecret(config.getApiSecret())
	    .callback(config.getCallback());
		OAuthService oAuthService=builder.build();
		oAuthService.signRequest(accessToken, oauthRequest);
		return oauthRequest.send();
	}
	public OAuthConfig getConfig() {
		return config;
	}

	public void setConfig(OAuthConfig config) {
		this.config = config;
	}

	public ConnsecPasswordApi20 getPasswordApi20() {
		return passwordApi20;
	}

	public void setPasswordApi20(ConnsecPasswordApi20 passwordApi20) {
		this.passwordApi20 = passwordApi20;
	}

}
