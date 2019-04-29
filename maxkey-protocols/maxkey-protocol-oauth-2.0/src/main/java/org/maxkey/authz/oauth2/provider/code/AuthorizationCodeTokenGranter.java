/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.maxkey.authz.oauth2.provider.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.maxkey.authz.oauth2.common.exceptions.InvalidClientException;
import org.maxkey.authz.oauth2.common.exceptions.InvalidGrantException;
import org.maxkey.authz.oauth2.common.exceptions.InvalidRequestException;
import org.maxkey.authz.oauth2.common.exceptions.RedirectMismatchException;
import org.maxkey.authz.oauth2.common.util.OAuth2Utils;
import org.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.maxkey.authz.oauth2.provider.OAuth2Request;
import org.maxkey.authz.oauth2.provider.OAuth2RequestFactory;
import org.maxkey.authz.oauth2.provider.TokenRequest;
import org.maxkey.authz.oauth2.provider.token.AbstractTokenGranter;
import org.maxkey.authz.oauth2.provider.token.AuthorizationServerTokenServices;
import org.maxkey.domain.apps.oauth2.provider.ClientDetails;
import org.springframework.security.core.Authentication;

/**
 * Token granter for the authorization code grant type.
 * 
 * @author Dave Syer
 * 
 */
public class AuthorizationCodeTokenGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "authorization_code";

	private final AuthorizationCodeServices authorizationCodeServices;

	public AuthorizationCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
			AuthorizationCodeServices authorizationCodeServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.authorizationCodeServices = authorizationCodeServices;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

		Map<String, String> parameters = tokenRequest.getRequestParameters();
		String authorizationCode = parameters.get("code");
		String redirectUri = parameters.get(OAuth2Utils.REDIRECT_URI);

		if (authorizationCode == null) {
			throw new InvalidRequestException("An authorization code must be supplied.");
		}

		OAuth2Authentication storedAuth = authorizationCodeServices.consumeAuthorizationCode(authorizationCode);
		if (storedAuth == null) {
			throw new InvalidGrantException("Invalid authorization code: " + authorizationCode);
		}

		OAuth2Request pendingOAuth2Request = storedAuth.getOAuth2Request();
		// https://jira.springsource.org/browse/SECOAUTH-333
		// This might be null, if the authorization was done without the redirect_uri parameter
		String redirectUriApprovalParameter = pendingOAuth2Request.getRequestParameters().get(
				OAuth2Utils.REDIRECT_URI);

		String pendingClientId = pendingOAuth2Request.getClientId();
		String clientId = tokenRequest.getClientId();
		
		/*
		 * 
		 * add for RedirectUri
		 * add by Crystal.Sea
		 */
		Set<String> redirectUris = client.getRegisteredRedirectUri();
		boolean redirectMismatch=false;
		
		for(String storedRedirectUri : redirectUris){
			if(redirectUri.startsWith(storedRedirectUri)){
				redirectMismatch=true;
			}
		}
		
		if ((redirectUri != null || redirectUriApprovalParameter != null)
				&& !redirectMismatch) {
			logger.info("storedAuth redirectUri "+pendingOAuth2Request.getRedirectUri());
			logger.info("redirectUri "+ redirectUri);
			logger.info("storedRedirectUri "+ redirectUris);
			throw new RedirectMismatchException("Redirect URI mismatch.");
		}
		/*
		if ((redirectUri != null || redirectUriApprovalParameter != null)
				&& !pendingOAuth2Request.getRedirectUri().equals(redirectUri)) {
			logger.info("storedAuth redirectUri "+pendingOAuth2Request.getRedirectUri());
			logger.info("redirectUri "+ redirectUri);
			throw new RedirectMismatchException("Redirect URI mismatch.");
		}*/

		
		if (clientId != null && !clientId.equals(pendingClientId)) {
			// just a sanity check.
			throw new InvalidClientException("Client ID mismatch");
		}

		// Secret is not required in the authorization request, so it won't be available
		// in the pendingAuthorizationRequest. We do want to check that a secret is provided
		// in the token request, but that happens elsewhere.

		Map<String, String> combinedParameters = new HashMap<String, String>(pendingOAuth2Request
				.getRequestParameters());
		// Combine the parameters adding the new ones last so they override if there are any clashes
		combinedParameters.putAll(parameters);
		
		// Make a new stored request with the combined parameters
		OAuth2Request finalStoredOAuth2Request = pendingOAuth2Request.createOAuth2Request(combinedParameters);
		
		Authentication userAuth = storedAuth.getUserAuthentication();
		
		return new OAuth2Authentication(finalStoredOAuth2Request, userAuth);

	}

}
