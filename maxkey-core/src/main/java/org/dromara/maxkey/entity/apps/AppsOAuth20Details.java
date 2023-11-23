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
 

package org.dromara.maxkey.entity.apps;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

import org.dromara.maxkey.entity.apps.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "MXK_APPS_OAUTH_CLIENT_DETAILS")
public class AppsOAuth20Details extends Apps  implements Serializable {

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

	private String issuer;
	
	private String audience;
	
	private String algorithm;
	
	private String algorithmKey;
	
	private String encryptionMethod;
	
	private String signature;
	
	private String signatureKey;
	
	private String subject;
	
	private String userInfoResponse;
    
    private String pkce;

	private String instId;

	private String instName;
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
        this.setAppName(application.getAppName());
        this.setLoginUrl(application.getLoginUrl());
        this.setLogoutUrl(application.getLogoutUrl());
        this.setLogoutType(application.getLogoutType());
        this.setCategory(application.getCategory());
        this.setProtocol(application.getProtocol());
        this.setIcon(application.getIcon());
        this.clientId = application.getId();

        this.setSortIndex(application.getSortIndex());
        this.setVendor(application.getVendor());
        this.setVendorUrl(application.getVendorUrl());
        this.setVisible(application.getVisible());
        
        this.setIsAdapter(application.getIsAdapter());
        this.setAdapter(application.getAdapter());
        this.setAdapterId(application.getAdapterId());
        this.setAdapterName(application.getAdapterName());
        this.setFrequently(application.getFrequently());
        this.setStatus(application.getStatus());
        this.setInducer(application.getInducer());
        this.setExtendAttr(application.getExtendAttr());
        this.setIsExtendAttr(application.getIsExtendAttr());
        
        this.clientSecret = baseClientDetails.getClientSecret();
        this.scope = StringUtils
                .collectionToCommaDelimitedString(baseClientDetails.getScope());
        this.resourceIds = StringUtils
                .collectionToCommaDelimitedString(baseClientDetails.getResourceIds());
        this.authorizedGrantTypes = StringUtils
                .collectionToCommaDelimitedString(baseClientDetails.getAuthorizedGrantTypes());
        this.registeredRedirectUris = StringUtils
                .collectionToCommaDelimitedString(baseClientDetails.getRegisteredRedirectUri());
        this.authorities = StringUtils
                .collectionToCommaDelimitedString(baseClientDetails.getAuthorities());
        this.accessTokenValiditySeconds = baseClientDetails.getAccessTokenValiditySeconds();
        this.refreshTokenValiditySeconds = baseClientDetails.getRefreshTokenValiditySeconds();
        this.approvalPrompt = baseClientDetails.isAutoApprove("all") + "";

        this.audience = baseClientDetails.getAudience();
        this.issuer = baseClientDetails.getIssuer();
        
        this.algorithm = baseClientDetails.getAlgorithm();
        this.algorithmKey = baseClientDetails.getAlgorithmKey();
        this.encryptionMethod = baseClientDetails.getEncryptionMethod();
        
        this.signature = baseClientDetails.getSignature();
        this.signatureKey = baseClientDetails.getSignatureKey();
        
        this.approvalPrompt = baseClientDetails.getApprovalPrompt();
        
        this.pkce = baseClientDetails.getPkce();
        this.instId = baseClientDetails.getInstId();
        this.subject = baseClientDetails.getSubject();
        this.userInfoResponse = baseClientDetails.getUserInfoResponse();
        

    }

    public String getPkce() {
        return pkce;
    }

    public void setPkce(String pkce) {
        this.pkce = pkce;
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

    public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUserInfoResponse() {
		return userInfoResponse;
	}

	public void setUserInfoResponse(String userInfoResponse) {
		this.userInfoResponse = userInfoResponse;
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

  
    public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getAudience() {
		return audience;
	}

	public void setAudience(String audience) {
		this.audience = audience;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getAlgorithmKey() {
		return algorithmKey;
	}

	public void setAlgorithmKey(String algorithmKey) {
		this.algorithmKey = algorithmKey;
	}

	public String getEncryptionMethod() {
		return encryptionMethod;
	}

	public void setEncryptionMethod(String encryptionMethod) {
		this.encryptionMethod = encryptionMethod;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignatureKey() {
		return signatureKey;
	}

	public void setSignatureKey(String signatureKey) {
		this.signatureKey = signatureKey;
	}



    public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public BaseClientDetails clientDetailsRowMapper() {
        BaseClientDetails baseClientDetails = new BaseClientDetails(this.getId(), this.getId(), this.getScope(),
                this.getAuthorizedGrantTypes(), "ROLE_CLIENT, ROLE_TRUSTED_CLIENT", this.getRegisteredRedirectUris());
        baseClientDetails.setAccessTokenValiditySeconds(this.getAccessTokenValiditySeconds());
        baseClientDetails.setRefreshTokenValiditySeconds(this.getRefreshTokenValiditySeconds());
        baseClientDetails.setClientSecret(this.getClientSecret());
        baseClientDetails.setAutoApproveScopes(baseClientDetails.getScope());

        baseClientDetails.setAudience(this.getAudience());
        baseClientDetails.setIssuer(this.getIssuer());
        
        baseClientDetails.setAlgorithm(this.getAlgorithm());
        baseClientDetails.setAlgorithmKey(this.getAlgorithmKey());
        baseClientDetails.setEncryptionMethod(this.getEncryptionMethod());
        
        baseClientDetails.setSignature(this.getSignature());
        baseClientDetails.setSignatureKey(this.getSignatureKey());
        
        baseClientDetails.setSubject(this.getSubject());
        baseClientDetails.setUserInfoResponse(this.userInfoResponse);
        
        baseClientDetails.setApprovalPrompt(this.getApprovalPrompt());
        baseClientDetails.setPkce(this.getPkce());
        baseClientDetails.setProtocol(this.getProtocol());
        baseClientDetails.setInstId(instId);
        return baseClientDetails;
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppsOAuth20Details [clientId=");
		builder.append(clientId);
		builder.append(", clientSecret=");
		builder.append(clientSecret);
		builder.append(", scope=");
		builder.append(scope);
		builder.append(", resourceIds=");
		builder.append(resourceIds);
		builder.append(", authorizedGrantTypes=");
		builder.append(authorizedGrantTypes);
		builder.append(", registeredRedirectUris=");
		builder.append(registeredRedirectUris);
		builder.append(", authorities=");
		builder.append(authorities);
		builder.append(", accessTokenValiditySeconds=");
		builder.append(accessTokenValiditySeconds);
		builder.append(", refreshTokenValiditySeconds=");
		builder.append(refreshTokenValiditySeconds);
		builder.append(", approvalPrompt=");
		builder.append(approvalPrompt);
		builder.append(", issuer=");
		builder.append(issuer);
		builder.append(", audience=");
		builder.append(audience);
		builder.append(", algorithm=");
		builder.append(algorithm);
		builder.append(", algorithmKey=");
		builder.append(algorithmKey);
		builder.append(", encryptionMethod=");
		builder.append(encryptionMethod);
		builder.append(", signature=");
		builder.append(signature);
		builder.append(", signatureKey=");
		builder.append(signatureKey);
		builder.append(", pkce=");
		builder.append(pkce);
		builder.append(", instId=");
		builder.append(instId);
		builder.append(", instName=");
		builder.append(instName);
		builder.append("]");
		return builder.toString();
	}

}
