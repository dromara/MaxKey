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
 

package org.maxkey.client.oauth.oauth;

import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.http.Response;
import org.maxkey.client.oauth.builder.ServiceBuilder;
import org.maxkey.client.oauth.builder.api.MaxkeyPasswordApi20;
import org.maxkey.client.oauth.model.OAuthConfig;
import org.maxkey.client.oauth.model.OAuthRequest;
import org.maxkey.client.oauth.model.Token;


/**
 * OAuth 2.0 api.
 */
public class OAuthPasswordService {

	private OAuthConfig config;
	
	private MaxkeyPasswordApi20 passwordApi20;

	public OAuthPasswordService() {
		super();
	}

	public OAuthPasswordService(OAuthConfig config,MaxkeyPasswordApi20 passwordApi20) {
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

	public MaxkeyPasswordApi20 getPasswordApi20() {
		return passwordApi20;
	}

	public void setPasswordApi20(MaxkeyPasswordApi20 passwordApi20) {
		this.passwordApi20 = passwordApi20;
	}

}
