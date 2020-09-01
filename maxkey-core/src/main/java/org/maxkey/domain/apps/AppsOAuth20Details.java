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
 

package org.maxkey.domain.apps;

import javax.persistence.Table;

import org.maxkey.domain.apps.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;

@Table(name = "MXK_APPS_OAUTH_CLIENT_DETAILS")
public class AppsOAuth20Details extends Apps {

    /**
     * 
     */
    private static final long serialVersionUID = 6786113671104069370L;

    private String clientId;

    private String clientSecret;

    private String scope;

    private String resourceIds;

    private String authorizedGrantTypes;

    private String registeredRedirectUris;

    private String authorities;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private String approvalPrompt;

    // for OpenID Connect
    private String idTokenSigningAlgorithm;
    private String idTokenEncryptedAlgorithm;
    private String idTokenEncryptionMethod;

    private String userInfoSigningAlgorithm;
    private String userInfoEncryptedAlgorithm;
    private String userInfoEncryptionMethod;

    private String jwksUri;

    /**
     * 
     */
    public AppsOAuth20Details() {
        super();

    }

    /**
     * 
     */
    public AppsOAuth20Details(Apps application, BaseClientDetails baseClientDetails) {
        super();
        this.id = application.getId();
        this.setName(application.getName());
        this.setLoginUrl(application.getLoginUrl());
        this.setCategory(application.getCategory());
        this.setProtocol(application.getProtocol());
        this.setIcon(application.getIcon());
        this.clientId = application.getId();

        this.setSortIndex(application.getSortIndex());
        this.setVendor(application.getVendor());
        this.setVendorUrl(application.getVendorUrl());

        this.clientSecret = baseClientDetails.getClientSecret();
        this.scope = baseClientDetails.getScope().toString();
        this.resourceIds = baseClientDetails.getResourceIds().toString();
        this.authorizedGrantTypes = baseClientDetails.getAuthorizedGrantTypes().toString();
        this.registeredRedirectUris = StringUtils
                .collectionToCommaDelimitedString(baseClientDetails.getRegisteredRedirectUri());
        this.authorities = baseClientDetails.getAuthorities().toString();
        this.accessTokenValiditySeconds = baseClientDetails.getAccessTokenValiditySeconds();
        this.refreshTokenValiditySeconds = baseClientDetails.getRefreshTokenValiditySeconds();
        this.approvalPrompt = baseClientDetails.isAutoApprove("all") + "";

        this.idTokenEncryptedAlgorithm = baseClientDetails.getIdTokenEncryptedAlgorithm();
        this.idTokenEncryptionMethod = baseClientDetails.getIdTokenEncryptionMethod();
        this.idTokenSigningAlgorithm = baseClientDetails.getIdTokenSigningAlgorithm();

        this.userInfoEncryptedAlgorithm = baseClientDetails.getUserInfoEncryptedAlgorithm();
        this.userInfoEncryptionMethod = baseClientDetails.getUserInfoEncryptionMethod();
        this.userInfoSigningAlgorithm = baseClientDetails.getUserInfoSigningAlgorithm();

        this.jwksUri = baseClientDetails.getJwksUri();
        this.approvalPrompt = baseClientDetails.getApprovalPrompt();

    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @return the approvalPrompt
     */
    public String getApprovalPrompt() {
        return approvalPrompt;
    }

    /**
     * @param approvalPrompt the approvalPrompt to set
     */
    public void setApprovalPrompt(String approvalPrompt) {
        this.approvalPrompt = approvalPrompt;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the clientSecret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * @param clientSecret the clientSecret to set
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return the resourceIds
     */
    public String getResourceIds() {
        return resourceIds;
    }

    /**
     * @param resourceIds the resourceIds to set
     */
    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    /**
     * @return the authorizedGrantTypes
     */
    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    /**
     * @param authorizedGrantTypes the authorizedGrantTypes to set
     */
    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    /**
     * @return the registeredRedirectUris
     */
    public String getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    /**
     * @param registeredRedirectUris the registeredRedirectUris to set
     */
    public void setRegisteredRedirectUris(String registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    /**
     * @return the authorities
     */
    public String getAuthorities() {
        return authorities;
    }

    /**
     * @param authorities the authorities to set
     */
    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    /**
     * @return the accessTokenValiditySeconds
     */
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    /**
     * @param accessTokenValiditySeconds the accessTokenValiditySeconds to set
     */
    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    /**
     * @return the refreshTokenValiditySeconds
     */
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    /**
     * @param refreshTokenValiditySeconds the refreshTokenValiditySeconds to set
     */
    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public String getIdTokenSigningAlgorithm() {
        return idTokenSigningAlgorithm;
    }

    public void setIdTokenSigningAlgorithm(String idTokenSigningAlgorithm) {
        this.idTokenSigningAlgorithm = idTokenSigningAlgorithm;
    }

    public String getIdTokenEncryptedAlgorithm() {
        return idTokenEncryptedAlgorithm;
    }

    public void setIdTokenEncryptedAlgorithm(String idTokenEncryptedAlgorithm) {
        this.idTokenEncryptedAlgorithm = idTokenEncryptedAlgorithm;
    }

    public String getIdTokenEncryptionMethod() {
        return idTokenEncryptionMethod;
    }

    public void setIdTokenEncryptionMethod(String idTokenEncryptionMethod) {
        this.idTokenEncryptionMethod = idTokenEncryptionMethod;
    }

    public String getUserInfoSigningAlgorithm() {
        return userInfoSigningAlgorithm;
    }

    public void setUserInfoSigningAlgorithm(String userInfoSigningAlgorithm) {
        this.userInfoSigningAlgorithm = userInfoSigningAlgorithm;
    }

    public String getUserInfoEncryptedAlgorithm() {
        return userInfoEncryptedAlgorithm;
    }

    public void setUserInfoEncryptedAlgorithm(String userInfoEncryptedAlgorithm) {
        this.userInfoEncryptedAlgorithm = userInfoEncryptedAlgorithm;
    }

    public String getUserInfoEncryptionMethod() {
        return userInfoEncryptionMethod;
    }

    public void setUserInfoEncryptionMethod(String userInfoEncryptionMethod) {
        this.userInfoEncryptionMethod = userInfoEncryptionMethod;
    }

    public String getJwksUri() {
        return jwksUri;
    }

    public void setJwksUri(String jwksUri) {
        this.jwksUri = jwksUri;
    }

    public BaseClientDetails clientDetailsRowMapper() {
        BaseClientDetails baseClientDetails = new BaseClientDetails(this.getId(), this.getId(), this.getScope(),
                this.getAuthorizedGrantTypes(), "ROLE_CLIENT, ROLE_TRUSTED_CLIENT", this.getRegisteredRedirectUris());
        baseClientDetails.setAccessTokenValiditySeconds(this.getAccessTokenValiditySeconds());
        baseClientDetails.setRefreshTokenValiditySeconds(this.getRefreshTokenValiditySeconds());
        baseClientDetails.setClientSecret(this.getClientSecret());
        baseClientDetails.setAutoApproveScopes(baseClientDetails.getScope());

        baseClientDetails.setIdTokenEncryptedAlgorithm(this.getIdTokenEncryptedAlgorithm());
        baseClientDetails.setIdTokenEncryptionMethod(this.getIdTokenEncryptionMethod());
        baseClientDetails.setIdTokenSigningAlgorithm(this.getIdTokenSigningAlgorithm());

        baseClientDetails.setUserInfoEncryptedAlgorithm(this.getUserInfoEncryptedAlgorithm());
        baseClientDetails.setUserInfoEncryptionMethod(this.getUserInfoEncryptionMethod());
        baseClientDetails.setUserInfoSigningAlgorithm(this.getUserInfoSigningAlgorithm());

        baseClientDetails.setJwksUri(this.getJwksUri());
        baseClientDetails.setApprovalPrompt(this.getApprovalPrompt());

        return baseClientDetails;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "OAuth20Details [clientId=" + clientId + ", clientSecret=" + clientSecret + ", scope=" + scope
                + ", resourceIds=" + resourceIds + ", authorizedGrantTypes=" + authorizedGrantTypes
                + ", registeredRedirectUris=" + registeredRedirectUris + ", authorities=" + authorities
                + ", accessTokenValiditySeconds=" + accessTokenValiditySeconds + ", refreshTokenValiditySeconds="
                + refreshTokenValiditySeconds + "]";
    }

}
