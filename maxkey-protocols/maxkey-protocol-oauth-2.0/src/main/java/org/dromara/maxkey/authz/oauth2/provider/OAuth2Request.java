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
 

package org.dromara.maxkey.authz.oauth2.provider;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.springframework.security.core.GrantedAuthority;

/**
 * Represents a stored authorization or token request. Used as part of the OAuth2Authentication object to store a
 * request's authentication information. Does not expose public setters so that clients can not mutate state if they
 * respect the declared type of the request.
 * 
 * @author Amanda Anganes
 * @author Dave Syer
 * 
 */
public class OAuth2Request extends BaseRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Resolved resource IDs. This set may change during request processing.
	 */
	private Set<String> resourceIds = new HashSet<String>();

	/**
	 * Resolved granted authorities for this request. May change during request processing.
	 */
	private Collection<? extends GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

	/**
	 * Whether the request has been approved by the end user (or other process). This will be altered by the User
	 * Approval Endpoint and/or the UserApprovalHandler as appropriate.
	 */
	private boolean approved = false;

	/**
	 * Will be non-null if the request is for a token to be refreshed (the original grant type might still be available
	 * via {@link #getGrantType()}).
	 */
	private TokenRequest refresh = null;

	/**
	 * The resolved redirect URI of this request. A URI may be present in the original request, in the
	 * authorizationParameters, or it may not be provided, in which case it will be defaulted (by processing classes) to
	 * the Client's default registered value.
	 */
	private String redirectUri;

	/**
	 * Resolved requested response types initialized (by the OAuth2RequestFactory) with the response types originally
	 * requested.
	 */
	private Set<String> responseTypes = new HashSet<String>();
	
	//support oauth 2.1, PKCE
    /**
     * A challenge derived from the code verifier that is sent in the
     * authorization request, to be verified against later.
     */
    private String codeChallenge;
    
    /**
     * A method that was used to derive code challenge.
     * 
     * plain
     *      code_challenge = code_verifier
     * 
     * S256
     *      code_challenge = BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))
     */
    private String codeChallengeMethod = "S256";

	/**
	 * Extension point for custom processing classes which may wish to store additional information about the OAuth2
	 * request. Since this class is serializable, all members of this map must also be serializable.
	 */
	private Map<String, Serializable> extensions = new HashMap<String, Serializable>();

	public OAuth2Request(Map<String, String> requestParameters, String clientId,
			Collection<? extends GrantedAuthority> authorities, boolean approved, Set<String> scope,
			Set<String> resourceIds, String redirectUri, Set<String> responseTypes,
			String codeChallenge,String codeChallengeMethod,
			Map<String, Serializable> extensionProperties) {
		setClientId(clientId);
		setRequestParameters(requestParameters);
		setScope(scope);
		if (resourceIds != null) {
			this.resourceIds = new HashSet<String>(resourceIds);
		}
		if (authorities != null) {
			this.authorities = new HashSet<GrantedAuthority>(authorities);
		}
		this.approved = approved;
		this.resourceIds = resourceIds;
		if (responseTypes != null) {
			this.responseTypes = new HashSet<String>(responseTypes);
		}
		this.codeChallenge = codeChallenge;
		this.codeChallengeMethod = codeChallengeMethod;
		this.redirectUri = redirectUri;
		if (extensionProperties != null) {
			this.extensions = extensionProperties;
		}
	}

	protected OAuth2Request(OAuth2Request other) {
		this(other.getRequestParameters(), other.getClientId(), other.getAuthorities(), other.isApproved(), other
				.getScope(), other.getResourceIds(), other.getRedirectUri(), other.getResponseTypes(),
				other.getCodeChallenge(),other.getCodeChallengeMethod(),
				other.getExtensions());
	}

	protected OAuth2Request(String clientId) {
		setClientId(clientId);
	}

	protected OAuth2Request() {
		super();
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public Set<String> getResponseTypes() {
		return responseTypes;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public boolean isApproved() {
		return approved;
	}

	public Set<String> getResourceIds() {
		return resourceIds;
	}
	

	public String getCodeChallenge() {
        return codeChallenge;
    }

    public String getCodeChallengeMethod() {
        return codeChallengeMethod;
    }

    public Map<String, Serializable> getExtensions() {
		return extensions;
	}

	/**
	 * Update the request parameters and return a new object with the same properties except the parameters.
	 * @param parameters new parameters replacing the existing ones
	 * @return a new OAuth2Request
	 */
	public OAuth2Request createOAuth2Request(Map<String, String> parameters) {
		return new OAuth2Request(parameters, getClientId(), authorities, approved, getScope(), resourceIds,
				redirectUri, responseTypes,  codeChallenge, codeChallengeMethod,extensions);
	}

	/**
	 * Update the scope and create a new request. All the other properties are the same (including the request
	 * parameters).
	 * 
	 * @param scope the new scope
	 * @return a new request with the narrowed scope
	 */
	public OAuth2Request narrowScope(Set<String> scope) {
		OAuth2Request request = new OAuth2Request(getRequestParameters(), getClientId(), authorities, approved, scope,
				resourceIds, redirectUri, responseTypes, codeChallenge, codeChallengeMethod, extensions);
		request.refresh = this.refresh;
		return request;
	}

	public OAuth2Request refresh(TokenRequest tokenRequest) {
		OAuth2Request request = new OAuth2Request(getRequestParameters(), getClientId(), authorities, approved,
				getScope(), resourceIds, redirectUri, responseTypes,  codeChallenge, codeChallengeMethod,extensions);
		request.refresh = tokenRequest;
		return request;
	}

	/**
	 * @return true if this request is known to be for a token to be refreshed
	 */
	public boolean isRefresh() {
		return refresh != null;
	}

	/**
	 * If this request was for an access token to be refreshed, then the {@link TokenRequest} that led to the refresh
	 * <i>may</i> be available here if it is known.
	 * 
	 * @return the refresh token request (may be null)
	 */
	public TokenRequest getRefreshTokenRequest() {
		return refresh;
	}

	/**
	 * Tries to discover the grant type requested for the token associated with this request.
	 * 
	 * @return the grant type if known, or null otherwise
	 */
	public String getGrantType() {
		if (getRequestParameters().containsKey(OAuth2Constants.PARAMETER.GRANT_TYPE)) {
			return getRequestParameters().get(OAuth2Constants.PARAMETER.GRANT_TYPE);
		}
		if (getRequestParameters().containsKey(OAuth2Constants.PARAMETER.RESPONSE_TYPE)) {
			String response = getRequestParameters().get(OAuth2Constants.PARAMETER.RESPONSE_TYPE);
			if (response.contains("token")) {
				return "implicit";
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (approved ? 1231 : 1237);
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((extensions == null) ? 0 : extensions.hashCode());
		result = prime * result + ((redirectUri == null) ? 0 : redirectUri.hashCode());
		result = prime * result + ((resourceIds == null) ? 0 : resourceIds.hashCode());
		result = prime * result + ((responseTypes == null) ? 0 : responseTypes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OAuth2Request other = (OAuth2Request) obj;
		if (approved != other.approved)
			return false;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		}
		else if (!authorities.equals(other.authorities))
			return false;
		if (extensions == null) {
			if (other.extensions != null)
				return false;
		}
		else if (!extensions.equals(other.extensions))
			return false;
		if (redirectUri == null) {
			if (other.redirectUri != null)
				return false;
		}
		else if (!redirectUri.equals(other.redirectUri))
			return false;
		if (resourceIds == null) {
			if (other.resourceIds != null)
				return false;
		}
		else if (!resourceIds.equals(other.resourceIds))
			return false;
		if (responseTypes == null) {
			if (other.responseTypes != null)
				return false;
		}
		else if (!responseTypes.equals(other.responseTypes))
			return false;
		return true;
	}

}
