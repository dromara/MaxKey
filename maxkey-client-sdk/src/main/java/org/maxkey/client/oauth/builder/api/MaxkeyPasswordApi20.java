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
 

package org.maxkey.client.oauth.builder.api;

import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.oauth.extractors.AccessTokenExtractor;
import org.maxkey.client.oauth.extractors.GsonJsonTokenExtractor;
import org.maxkey.client.oauth.model.OAuthConfig;



/**
 * OAuth 2.0 api.
 */
public class MaxkeyPasswordApi20 extends DefaultApi20 {

	private String accessTokenUrl;
	private String grantType = "password";
	private String scope = "&scope=%s";
	private String accessTokenMethod = "POST";

	private static final String AUTHORIZE_PARAM = "&client_id=%s&client_secret=%s&username=%s&password=%s";

	public MaxkeyPasswordApi20(String accessTokenUrl) {
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
