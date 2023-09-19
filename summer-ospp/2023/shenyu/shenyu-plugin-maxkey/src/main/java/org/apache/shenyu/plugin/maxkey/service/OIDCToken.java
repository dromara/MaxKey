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

import com.google.gson.annotations.SerializedName;

public class OIDCToken {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("scope")
    private String scope;

    @SerializedName("id_token")
    private String idToken;

    public OIDCToken() {
    }

    public OIDCToken(final String accessToken,
                     final String tokenType,
                     final String refreshToken,
                     final int expiresIn,
                     final String scope,
                     final String idToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.idToken = idToken;
    }

    /**
     * Gets accessToken.
     *
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets accessToken.
     *
     * @param accessToken the accessToken
     */
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets tokenType.
     *
     * @return the tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Sets tokenType.
     *
     * @param tokenType the tokenType
     */
    public void setTokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * Gets refreshToken.
     *
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Sets refreshToken.
     *
     * @param refreshToken the refreshToken
     */
    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Gets expiresIn.
     *
     * @return the expiresIn
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * Sets expiresIn.
     *
     * @param expiresIn the expiresIn
     */
    public void setExpiresIn(final int expiresIn) {
        this.expiresIn = expiresIn;
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
     * Gets idToken.
     *
     * @return the idToken
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * Sets idToken.
     *
     * @param idToken the idToken
     */
    public void setIdToken(final String idToken) {
        this.idToken = idToken;
    }

    @Override
    public String toString() {
        return "OIDCToken{"
                + "accessToken='" + accessToken + '\''
                + ", tokenType='" + tokenType + '\''
                + ", refreshToken='" + refreshToken + '\''
                + ", expiresIn=" + expiresIn
                + ", scope='" + scope + '\''
                + ", idToken='" + idToken + '\''
                + '}';
    }
}
