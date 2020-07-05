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
 

package org.maxkey.authz.oauth2.provider.token.store;

import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import org.maxkey.authz.oauth2.common.ExpiringOAuth2RefreshToken;
import org.maxkey.authz.oauth2.common.OAuth2AccessToken;
import org.maxkey.authz.oauth2.common.OAuth2RefreshToken;
import org.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.maxkey.authz.oauth2.provider.token.AuthenticationKeyGenerator;
import org.maxkey.authz.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.maxkey.authz.oauth2.provider.token.TokenStore;
import org.maxkey.persistence.redis.RedisConnection;
import org.maxkey.persistence.redis.RedisConnectionFactory;
import org.maxkey.util.ObjectTransformer;

import java.util.Date;

/**
 * @author efenderbosch
 */
public class RedisTokenStore implements TokenStore {

	private static final String ACCESS = "REDIS_OAUTH_V20_access:";
	private static final String AUTH_TO_ACCESS = "REDIS_OAUTH_V20_auth_to_access:";
	private static final String AUTH = "REDIS_OAUTH_V20_auth:";
	private static final String REFRESH_AUTH = "REDIS_OAUTH_V20_refresh_auth:";
	private static final String ACCESS_TO_REFRESH = "REDIS_OAUTH_V20_access_to_refresh:";
	private static final String REFRESH = "REDIS_OAUTH_V20_refresh:";
	private static final String REFRESH_TO_ACCESS = "REDIS_OAUTH_V20_refresh_to_access:";
	private static final String CLIENT_ID_TO_ACCESS = "REDIS_OAUTH_V20_client_id_to_access:";
	private static final String UNAME_TO_ACCESS = "REDIS_OAUTH_V20_uname_to_access:";

	private final RedisConnectionFactory connectionFactory;
	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
	
	private String prefix = "";

	public RedisTokenStore(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
		this.authenticationKeyGenerator = authenticationKeyGenerator;
	}

	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	private RedisConnection getConnection() {
		return connectionFactory.getConnection();
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		String key = authenticationKeyGenerator.extractKey(authentication);
		String serializedKey = (AUTH_TO_ACCESS + key);
		RedisConnection conn = getConnection();
		OAuth2AccessToken accessToken =conn.getObject(serializedKey);
		if (accessToken != null
				&& !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
			// Keep the stores consistent (maybe the same user is
			// represented by this authentication but the details have
			// changed)
			storeAccessToken(accessToken, authentication);
		}
		return accessToken;
	}

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		RedisConnection conn = getConnection();
		OAuth2Authentication auth = conn.getObject(AUTH + token);
		return auth;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return readAuthenticationForRefreshToken(token.getValue());
	}

	public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
		RedisConnection conn = getConnection();
		try {
			OAuth2Authentication auth = conn.getObject(REFRESH_AUTH + token);
			return auth;
		} finally {
			conn.close();
		}
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		String accessKey = (ACCESS + token.getValue());
		String authKey = (AUTH + token.getValue());
		String authToAccessKey = (AUTH_TO_ACCESS + authenticationKeyGenerator.extractKey(authentication));
		String approvalKey = (UNAME_TO_ACCESS + getApprovalKey(authentication));
		String clientId = (CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId());

		RedisConnection conn = getConnection();
		try {
			conn.openPipeline();
			conn.setObject(accessKey, token);
			conn.setObject(authKey, authentication);
			conn.setObject(authToAccessKey, token);
			if (!authentication.isClientOnly()) {
				conn.rPush(approvalKey, token);
			}
			conn.rPush(clientId, token);
			if (token.getExpiration() != null) {
				int seconds = token.getExpiresIn();
				conn.expire(accessKey, seconds);
				conn.expire(authKey, seconds);
				conn.expire(authToAccessKey, seconds);
				conn.expire(clientId, seconds);
				conn.expire(approvalKey, seconds);
			}
			OAuth2RefreshToken refreshToken = token.getRefreshToken();
			if (refreshToken != null && refreshToken.getValue() != null) {
				String refresh = (token.getRefreshToken().getValue());
				String auth = (token.getValue());
				String refreshToAccessKey = (REFRESH_TO_ACCESS + token.getRefreshToken().getValue());
				conn.set(refreshToAccessKey, auth);
				String accessToRefreshKey = (ACCESS_TO_REFRESH + token.getValue());
				conn.set(accessToRefreshKey, refresh);
				if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
					ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
					Date expiration = expiringRefreshToken.getExpiration();
					if (expiration != null) {
						int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
								.intValue();
						conn.expire(refreshToAccessKey, seconds);
						conn.expire(accessToRefreshKey, seconds);
					}
				}
			}
			conn.closePipeline();
		} finally {
			conn.close();
		}
	}

	private static String getApprovalKey(OAuth2Authentication authentication) {
		String userName = authentication.getUserAuthentication() == null ? ""
				: authentication.getUserAuthentication().getName();
		return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
	}

	private static String getApprovalKey(String clientId, String userName) {
		return clientId + (userName == null ? "" : ":" + userName);
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken accessToken) {
		removeAccessToken(accessToken.getValue());
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		RedisConnection conn = getConnection();
		String key = (ACCESS + tokenValue);
		OAuth2AccessToken accessToken = conn.getObject(key);
		return accessToken;
	}

	public void removeAccessToken(String tokenValue) {
		String accessKey = (ACCESS + tokenValue);
		String authKey = (AUTH + tokenValue);
		String accessToRefreshKey = (ACCESS_TO_REFRESH + tokenValue);
		RedisConnection conn = getConnection();
		try {
			conn.openPipeline();
			conn.delete(accessKey);
			conn.delete(accessToRefreshKey);
			// Don't remove the refresh token - it's up to the caller to do that
			conn.delete(authKey);
			List<Object> results = conn.closePipeline();
			String access = (String) results.get(0);
			String auth = (String) results.get(1);

			OAuth2Authentication authentication = ObjectTransformer.deserialize(auth);
			if (authentication != null) {
				String key = authenticationKeyGenerator.extractKey(authentication);
				String authToAccessKey = (AUTH_TO_ACCESS + key);
				String unameKey = (UNAME_TO_ACCESS + getApprovalKey(authentication));
				String clientId = (CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId());
				conn.openPipeline();
				conn.delete(authToAccessKey);
				conn.lRem(unameKey, 1, access);
				conn.lRem(clientId, 1, access);
				conn.delete(ACCESS + key);
				conn.closePipeline();
			}
		} finally {
			conn.close();
		}
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		String refreshKey = (REFRESH + refreshToken.getValue());
		String refreshAuthKey = (REFRESH_AUTH + refreshToken.getValue());
		RedisConnection conn = getConnection();
		try {
			conn.openPipeline();
			conn.setObject(refreshKey, refreshToken);
			conn.setObject(refreshAuthKey, authentication);
			if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
				ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
				Date expiration = expiringRefreshToken.getExpiration();
				if (expiration != null) {
					int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
							.intValue();
					conn.expire(refreshKey, seconds);
					conn.expire(refreshAuthKey, seconds);
				}
			}
			conn.closePipeline();
		} finally {
			conn.close();
		}
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		String key = (REFRESH + tokenValue);
		RedisConnection conn = getConnection();
		OAuth2RefreshToken refreshToken = conn.getObject(key);
		conn.close();
		return refreshToken;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
		removeRefreshToken(refreshToken.getValue());
	}

	public void removeRefreshToken(String tokenValue) {
		String refreshKey = (REFRESH + tokenValue);
		String refreshAuthKey = (REFRESH_AUTH + tokenValue);
		String refresh2AccessKey = (REFRESH_TO_ACCESS + tokenValue);
		String access2RefreshKey = (ACCESS_TO_REFRESH + tokenValue);
		RedisConnection conn = getConnection();
		try {
			conn.openPipeline();
			conn.delete(refreshKey);
			conn.delete(refreshAuthKey);
			conn.delete(refresh2AccessKey);
			conn.delete(access2RefreshKey);
			conn.closePipeline();
		} finally {
			conn.close();
		}
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		removeAccessTokenUsingRefreshToken(refreshToken.getValue());
	}

	private void removeAccessTokenUsingRefreshToken(String refreshToken) {
		String key = (REFRESH_TO_ACCESS + refreshToken);
		List<Object> results = null;
		RedisConnection conn = getConnection();
		try {
			conn.openPipeline();
			conn.get(key);
			conn.delete(key);
			results = conn.closePipeline();
		} finally {
			conn.close();
		}
		if (results == null) {
			return;
		}
		String  bytes = (String) results.get(0);
		String accessToken = ObjectTransformer.deserialize(bytes);
		if (accessToken != null) {
			removeAccessToken(accessToken);
		}
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		String approvalKey = (UNAME_TO_ACCESS + getApprovalKey(clientId, userName));
		List<String> stringList = null;
		RedisConnection conn = getConnection();
		try {
			stringList = conn.lRange(approvalKey, 0, -1);
		} finally {
			conn.close();
		}
		if (stringList == null || stringList.size() == 0) {
			return Collections.<OAuth2AccessToken> emptySet();
		}
		List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>(stringList.size());
		for (String str : stringList) {
			OAuth2AccessToken accessToken = conn.getObject(str);
			accessTokens.add(accessToken);
		}
		return Collections.<OAuth2AccessToken> unmodifiableCollection(accessTokens);
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		String key = (CLIENT_ID_TO_ACCESS + clientId);
		List<String> stringList = null;
		RedisConnection conn = getConnection();
		try {
			stringList = conn.lRange(key, 0, -1);
		} finally {
			conn.close();
		}
		if (stringList == null || stringList.size() == 0) {
			return Collections.<OAuth2AccessToken> emptySet();
		}
		List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>(stringList.size());
		for (String str : stringList) {
			OAuth2AccessToken accessToken = conn.getObject(str);
			accessTokens.add(accessToken);
		}
		return Collections.<OAuth2AccessToken> unmodifiableCollection(accessTokens);
	}

}
