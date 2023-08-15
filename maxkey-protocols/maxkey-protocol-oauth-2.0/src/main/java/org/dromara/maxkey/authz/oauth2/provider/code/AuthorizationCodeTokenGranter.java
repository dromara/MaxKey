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

package org.dromara.maxkey.authz.oauth2.provider.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants.CODE_CHALLENGE_METHOD_TYPE;
import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidClientException;
import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidGrantException;
import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidRequestException;
import org.dromara.maxkey.authz.oauth2.common.exceptions.OAuth2Exception;
import org.dromara.maxkey.authz.oauth2.common.exceptions.RedirectMismatchException;
import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Request;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2RequestFactory;
import org.dromara.maxkey.authz.oauth2.provider.TokenRequest;
import org.dromara.maxkey.authz.oauth2.provider.token.AbstractTokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.token.AuthorizationServerTokenServices;
import org.dromara.maxkey.constants.ConstsProtocols;
import org.dromara.maxkey.crypto.DigestUtils;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
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
		String authorizationCode = parameters.get(OAuth2Constants.PARAMETER.CODE);
		String redirectUri = parameters.get(OAuth2Constants.PARAMETER.REDIRECT_URI);
		String codeVerifier = parameters.get(OAuth2Constants.PARAMETER.CODE_VERIFIER);

		if (authorizationCode == null) {
			throw new InvalidRequestException("An authorization code must be supplied.");
		}
		//consume AuthorizationCode
		logger.trace("consume AuthorizationCode...");
		OAuth2Authentication storedAuth = authorizationCodeServices.consumeAuthorizationCode(authorizationCode);
		if (storedAuth == null) {
			throw new InvalidGrantException("Invalid authorization code: " + authorizationCode);
		}

		OAuth2Request pendingOAuth2Request = storedAuth.getOAuth2Request();
		// https://jira.springsource.org/browse/SECOAUTH-333
		// This might be null, if the authorization was done without the redirect_uri parameter
		String redirectUriApprovalParameter = 
		        pendingOAuth2Request.getRequestParameters().get(
		                OAuth2Constants.PARAMETER.REDIRECT_URI);

		String pendingClientId = pendingOAuth2Request.getClientId();
		String clientId = tokenRequest.getClientId();
		
		/*
		 * 
		 * add for RedirectUri
		 * add by Crystal.Sea
		 */
		Set<String> redirectUris = client.getRegisteredRedirectUri();
		boolean redirectMismatch=false;
		//match the stored RedirectUri with request redirectUri parameter
		for(String storedRedirectUri : redirectUris){
			if(redirectUri.startsWith(storedRedirectUri)){
				redirectMismatch=true;
			}
		}
		
		if ((redirectUri != null || redirectUriApprovalParameter != null)
				&& !redirectMismatch) {
			logger.info("storedAuth redirectUri "+pendingOAuth2Request.getRedirectUri());
			logger.info("redirectUri parameter "+ redirectUri);
			logger.info("stored RedirectUri "+ redirectUris);
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
		
		//OAuth 2.1 and PKCE Support
		logger.debug("client Protocol "+client.getProtocol()+", PKCE Support "+
		        (client.getPkce().equalsIgnoreCase(OAuth2Constants.PKCE_TYPE.PKCE_TYPE_YES)));
		if(client.getProtocol().equalsIgnoreCase(ConstsProtocols.OAUTH21)
		        || client.getPkce().equalsIgnoreCase(OAuth2Constants.PKCE_TYPE.PKCE_TYPE_YES)) {
    		logger.trace("stored CodeChallengeMethod "+ pendingOAuth2Request.getCodeChallengeMethod());
    		logger.trace("stored CodeChallenge "+ pendingOAuth2Request.getCodeChallenge());
    		logger.trace("stored codeVerifier "+ codeVerifier);
    		if(StringUtils.isBlank(codeVerifier)) {
    		    throw new OAuth2Exception("code_verifier can not null.");
    		}
    		
    		if(StringUtils.isBlank(pendingOAuth2Request.getCodeChallenge())) {
                throw new OAuth2Exception("code_challenge can not null.");
            }
    		
    		if(CODE_CHALLENGE_METHOD_TYPE.S256.equalsIgnoreCase(pendingOAuth2Request.getCodeChallengeMethod())) {
    		    codeVerifier = DigestUtils.digestBase64Url(codeVerifier,DigestUtils.Algorithm.SHA256);
    		}
    		
    		if(!codeVerifier.equals(pendingOAuth2Request.getCodeChallenge())) {
                throw new OAuth2Exception("code_verifier not match.");
            }
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
