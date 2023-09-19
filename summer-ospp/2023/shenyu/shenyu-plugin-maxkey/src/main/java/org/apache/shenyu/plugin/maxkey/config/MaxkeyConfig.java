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

package org.apache.shenyu.plugin.maxkey.config;

public class MaxkeyConfig {

    private String clientId;

    private String clientSecret;

    private String authorizationEndpoint;

    private String scope;

    private String responseType;

    private String redirectUrl;

    private String realm;

    private String grantType;

    private String tokenEndpoint;

    private boolean bearerOnly;

    private String introspectionEndpoint;

    private boolean setUserInfoHeader;

    private String userInfoEndpoint;

    private String introspectionEndpointAuthMethodsSupported;

    private String discovery;

    public MaxkeyConfig() {
    }

    public MaxkeyConfig(final String clientId,
                        final String clientSecret,
                        final String authorizationEndpoint,
                        final String scope,
                        final String responseType,
                        final String redirectUrl,
                        final String realm,
                        final String grantType,
                        final String tokenEndpoint,
                        final boolean bearerOnly,
                        final String introspectionEndpoint,
                        final boolean setUserInfoHeader,
                        final String userInfoEndpoint,
                        final String introspectionEndpointAuthMethodsSupported,
                        final String discovery) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authorizationEndpoint = authorizationEndpoint;
        this.scope = scope;
        this.responseType = responseType;
        this.redirectUrl = redirectUrl;
        this.realm = realm;
        this.grantType = grantType;
        this.tokenEndpoint = tokenEndpoint;
        this.bearerOnly = bearerOnly;
        this.introspectionEndpoint = introspectionEndpoint;
        this.setUserInfoHeader = setUserInfoHeader;
        this.userInfoEndpoint = userInfoEndpoint;
        this.introspectionEndpointAuthMethodsSupported = introspectionEndpointAuthMethodsSupported;
        this.discovery = discovery;
    }

    /**
     * Gets clientId.
     *
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets clientId.
     *
     * @param clientId the clientId
     */
    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets clientSecret.
     *
     * @return the clientSecret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Sets clientSecret.
     *
     * @param clientSecret the clientSecret
     */
    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * Gets authorizationEndpoint.
     *
     * @return the authorizationEndpoint
     */
    public String getAuthorizationEndpoint() {
        return authorizationEndpoint;
    }

    /**
     * Sets authorizationEndpoint.
     *
     * @param authorizationEndpoint the authorizationEndpoint
     */
    public void setAuthorizationEndpoint(final String authorizationEndpoint) {
        this.authorizationEndpoint = authorizationEndpoint;
    }

    /**
     * Gets scope.
     *
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * Sets scope.
     *
     * @param scope the scope
     */
    public void setScope(final String scope) {
        this.scope = scope;
    }

    /**
     * Gets responseType.
     *
     * @return the responseType
     */
    public String getResponseType() {
        return responseType;
    }

    /**
     * Sets responseType.
     *
     * @param responseType the responseType
     */
    public void setResponseType(final String responseType) {
        this.responseType = responseType;
    }

    /**
     * Gets redirectUrl.
     *
     * @return the redirectUrl
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Sets redirectUrl.
     *
     * @param redirectUrl the redirectUrl
     */
    public void setRedirectUrl(final String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    /**
     * Gets realm.
     *
     * @return the realm
     */
    public String getRealm() {
        return realm;
    }

    /**
     * Sets realm.
     *
     * @param realm the realm
     */
    public void setRealm(final String realm) {
        this.realm = realm;
    }

    /**
     * Gets tokenType.
     *
     * @return the tokenType
     */
    public String getGrantType() {
        return grantType;
    }

    /**
     * Sets grantType.
     *
     * @param grantType the grantType
     */
    public void setGrantType(final String grantType) {
        this.grantType = grantType;
    }

    /**
     * Gets tokenEndpoint.
     *
     * @return the tokenEndpoint
     */
    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    /**
     * Sets tokenEndpoint.
     *
     * @param tokenEndpoint the tokenEndpoint
     */
    public void setTokenEndpoint(final String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    /**
     * Is bearerOnly.
     *
     * @return is bearerOnly
     */
    public boolean isBearerOnly() {
        return bearerOnly;
    }

    /**
     * Sets bearerOnly.
     *
     * @param bearerOnly the bearerOnly
     */
    public void setBearerOnly(final boolean bearerOnly) {
        this.bearerOnly = bearerOnly;
    }

    /**
     * Gets introspectionEndpoint.
     *
     * @return the introspectionEndpoint
     */
    public String getIntrospectionEndpoint() {
        return introspectionEndpoint;
    }

    /**
     * Sets introspectionEndpoint.
     *
     * @param introspectionEndpoint the introspectionEndpoint
     */
    public void setIntrospectionEndpoint(final String introspectionEndpoint) {
        this.introspectionEndpoint = introspectionEndpoint;
    }

    /**
     * Is setUserInfoHeader.
     *
     * @return is setUserInfoHeader
     */
    public boolean isSetUserInfoHeader() {
        return setUserInfoHeader;
    }

    /**
     * Sets setUserInfoHeader.
     *
     * @param setUserInfoHeader the setUserInfoHeader
     */
    public void setSetUserInfoHeader(final boolean setUserInfoHeader) {
        this.setUserInfoHeader = setUserInfoHeader;
    }

    /**
     * Gets userInfoEndpoint.
     *
     * @return the userInfoEndpoint
     */
    public String getUserInfoEndpoint() {
        return userInfoEndpoint;
    }

    /**
     * Sets userInfoEndpoint.
     *
     * @param userInfoEndpoint the userInfoEndpoint
     */
    public void setUserInfoEndpoint(final String userInfoEndpoint) {
        this.userInfoEndpoint = userInfoEndpoint;
    }

    /**
     * Gets introspectionEndpointAuthMethodsSupported.
     *
     * @return the introspectionEndpointAuthMethodsSupported
     */
    public String getIntrospectionEndpointAuthMethodsSupported() {
        return introspectionEndpointAuthMethodsSupported;
    }

    /**
     * Sets introspectionEndpointAuthMethodsSupported.
     *
     * @param introspectionEndpointAuthMethodsSupported the accessToken
     */
    public void setIntrospectionEndpointAuthMethodsSupported(final String introspectionEndpointAuthMethodsSupported) {
        this.introspectionEndpointAuthMethodsSupported = introspectionEndpointAuthMethodsSupported;
    }

    /**
     * Gets discovery.
     *
     * @return the discovery
     */
    public String getDiscovery() {
        return discovery;
    }

    /**
     * Sets discovery.
     *
     * @param discovery the discovery
     */
    public void setDiscovery(final String discovery) {
        this.discovery = discovery;
    }

    @Override
    public String toString() {
        return "MaxkeyConfig{"
                + "clientId='" + clientId + '\''
                + ", clientSecret='" + clientSecret + '\''
                + ", authorizationEndpoint='" + authorizationEndpoint + '\''
                + ", scope='" + scope + '\''
                + ", responseType='" + responseType + '\''
                + ", redirectUrl='" + redirectUrl + '\''
                + ", realm='" + realm + '\''
                + ", grantType='" + grantType + '\''
                + ", tokenEndpoint='" + tokenEndpoint + '\''
                + ", bearerOnly=" + bearerOnly
                + ", introspectionEndpoint='" + introspectionEndpoint + '\''
                + ", setUserInfoHeader=" + setUserInfoHeader
                + ", userInfoEndpoint='" + userInfoEndpoint + '\''
                + ", introspectionEndpointAuthMethodsSupported='" + introspectionEndpointAuthMethodsSupported + '\''
                + ", discovery='" + discovery + '\''
                + '}';
    }
}
