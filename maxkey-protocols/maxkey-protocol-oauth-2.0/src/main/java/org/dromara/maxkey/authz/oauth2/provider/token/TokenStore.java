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
 

package org.dromara.maxkey.authz.oauth2.provider.token;

import java.util.Collection;

import org.dromara.maxkey.authz.oauth2.common.OAuth2AccessToken;
import org.dromara.maxkey.authz.oauth2.common.OAuth2RefreshToken;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;

/**
 * Persistence interface for OAuth2 tokens.
 */
public interface TokenStore {

	/**
	 * Read the authentication stored under the specified token value.
	 * 
	 * @param token The token value under which the authentication is stored.
	 * @return The authentication, or null if none.
	 */
	OAuth2Authentication readAuthentication(OAuth2AccessToken token);

	/**
	 * Read the authentication stored under the specified token value.
	 * 
	 * @param token The token value under which the authentication is stored.
	 * @return The authentication, or null if none.
	 */
	OAuth2Authentication readAuthentication(String token);

	/**
	 * Store an access token.
	 * 
	 * @param token The token to store.
	 * @param authentication The authentication associated with the token.
	 */
	void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication);

	/**
	 * Read an access token from the store.
	 * 
	 * @param tokenValue The token value.
	 * @return The access token to read.
	 */
	OAuth2AccessToken readAccessToken(String tokenValue);

	/**
	 * Remove an access token from the database.
	 * 
	 * @param token The token to remove from the database.
	 */
	void removeAccessToken(OAuth2AccessToken token);

	/**
	 * Store the specified refresh token in the database.
	 * 
	 * @param refreshToken The refresh token to store.
	 * @param authentication The authentication associated with the refresh token.
	 */
	void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication);

	/**
	 * Read a refresh token from the store.
	 * 
	 * @param tokenValue The value of the token to read.
	 * @return The token.
	 */
	OAuth2RefreshToken readRefreshToken(String tokenValue);

	/**
	 * @param token a refresh token
	 * @return the authentication originally used to grant the refresh token
	 */
	OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token);

	/**
	 * Remove a refresh token from the database.
	 * 
	 * @param token The token to remove from the database.
	 */
	void removeRefreshToken(OAuth2RefreshToken token);

	/**
	 * Remove an access token using a refresh token. This functionality is necessary so refresh tokens can't be used to
	 * create an unlimited number of access tokens.
	 * 
	 * @param refreshToken The refresh token.
	 */
	void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken);

	/**
	 * Retrieve an access token stored against the provided authentication key, if it exists.
	 * 
	 * @param authentication the authentication key for the access token
	 * 
	 * @return the access token or null if there was none
	 */
	OAuth2AccessToken getAccessToken(OAuth2Authentication authentication);

	/**
	 * @param clientId the client id to search
	 * @param userName the user name to search
	 * @return a collection of access tokens
	 */
	Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName);

	/**
	 * @param userName the user name to search
	 * @param clientId the client id to search
	 * @return a collection of access tokens
	 */
	Collection<OAuth2AccessToken> findTokensByClientId(String clientId);

}
