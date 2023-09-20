/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.plugin.maxkey.service;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shenyu.common.utils.GsonUtils;
import org.apache.shenyu.plugin.maxkey.config.MaxkeyConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

public class MaxkeyService {

    private static final int REDIRECT_STATE_CODE = 302;

    private final MaxkeyConfig maxkeyConfig;

    public MaxkeyService(final MaxkeyConfig maxkeyConfig) {
        this.maxkeyConfig = maxkeyConfig;
    }

    /**
     * redirect unauthenticated requests to the IdP service.
     *
     * @param exchange exchange
     * @param state state
     * @return void
     */
    public Mono<Void> redirect(final ServerWebExchange exchange, final String state) {
        ServerHttpResponse response = exchange.getResponse();
        String redirectUrl = UriComponentsBuilder.fromUriString(maxkeyConfig.getAuthorizationEndpoint())
                .queryParam("response_type", maxkeyConfig.getResponseType())
                .queryParam("client_id", maxkeyConfig.getClientId())
                .queryParam("redirect_uri", maxkeyConfig.getRedirectUrl())
                .queryParam("scope", maxkeyConfig.getScope())
                .queryParam("state", state)
                .build()
                .toUriString();

        response.setRawStatusCode(REDIRECT_STATE_CODE);
        response.getHeaders().add(HttpHeaders.LOCATION, redirectUrl);
        return response.setComplete();
    }

    /**
     * getAccessToken from maxkey service.
     *
     * @param code code
     * @return String
     */
    public String getOAuthToken(final String code) {
        try {
            OAuthClientRequest oAuthClientRequest = OAuthClientRequest
                    .tokenLocation(maxkeyConfig.getTokenEndpoint())
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(maxkeyConfig.getClientId())
                    .setClientSecret(maxkeyConfig.getClientSecret())
                    .setRedirectURI(String.format("%s", maxkeyConfig.getRedirectUrl()))
                    .setCode(code)
                    .buildQueryMessage();
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(oAuthClientRequest, OAuth.HttpMethod.POST);
            return oAuthResponse.getAccessToken();
        } catch (OAuthSystemException | OAuthProblemException e) {
            throw new RuntimeException("Code error, cannot get OAuth token from maxkey server.", e);
        }
    }

    /**
     * getOidcToken from maxkey service.
     *
     * @param code code
     * @param state state
     * @return OIDCToken
     */
    public OIDCToken getOidcToken(final String code, final String state) {
        String url = maxkeyConfig.getTokenEndpoint();
        String responseType = maxkeyConfig.getResponseType();
        String redirectUri = maxkeyConfig.getRedirectUrl();
        String scope = maxkeyConfig.getScope();
        String clientId = maxkeyConfig.getClientId();
        String clientSecret = maxkeyConfig.getClientSecret();
        String grantType = maxkeyConfig.getGrantType();

        String requestUrl = String.format("%s?response_type=%s&code=%s&redirect_uri=%s&scope=%s&client_id=%s&client_secret=%s&grant_type=%s&state=%s",
                url, responseType, code, redirectUri, scope, clientId, clientSecret, grantType, state);
        HttpResponse response = HttpUtil.createGet(requestUrl).execute();
        OIDCToken oidcToken;
        try {
            oidcToken = GsonUtils.getInstance().fromJson(response.body(), OIDCToken.class);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Code error, cannot get OAuth token from maxkey server.", e);
        }
        return oidcToken;
    }

    /**
     * introspect AccessToken via maxkey authentication server.
     *
     * @param token access token
     * @return boolean
     */
    public boolean introspectAccessToken(final String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        String url = maxkeyConfig.getIntrospectionEndpoint();
        String requestUrl = String.format("%s?access_token=%s", url, token);
        HttpResponse response = HttpUtil
                .createGet(requestUrl)
                .execute();
        Introspection introspection = GsonUtils.getInstance().fromJson(response.body(), Introspection.class);
        return introspection.isActive();
    }

    /**
     * get maxkey user by access token.
     *
     * @param token access token
     * @return MaxkeyUser
     */
    public MaxkeyUser getMaxkeyUser(final String token) {
        String fullUserInfoJson = getUserInfo(token);
        return GsonUtils.getInstance().fromJson(fullUserInfoJson, MaxkeyUser.class);
    }

    /**
     * get user info by access token.
     *
     * @param token access token
     * @return String
     */
    public String getUserInfo(final String token) {
        String url = maxkeyConfig.getUserInfoEndpoint();
        String requestUrl = String.format("%s?access_token=%s", url, token);
        HttpResponse response = HttpUtil
                .createGet(requestUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .execute();
        return response.body();
    }

    /**
     * get maxkey config.
     *
     * @return MaxkeyConfig
     */
    public MaxkeyConfig getMaxkeyConfig() {
        return this.maxkeyConfig;
    }
}

