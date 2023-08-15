/*
 * Copyright 2006-2011 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.dromara.maxkey.authz.oauth2.provider.token;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dromara.maxkey.authz.oauth2.common.OAuth2AccessToken;
import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidClientException;
import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Request;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2RequestFactory;
import org.dromara.maxkey.authz.oauth2.provider.TokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.TokenRequest;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;

/**
 * @author Dave Syer
 * 
 */
public abstract class AbstractTokenGranter implements TokenGranter {
	
	protected final Log logger = LogFactory.getLog(getClass());

	private final AuthorizationServerTokenServices tokenServices;

	private final ClientDetailsService clientDetailsService;
	
	private final OAuth2RequestFactory requestFactory;
	
	private final String grantType;

	protected AbstractTokenGranter(AuthorizationServerTokenServices tokenServices,
			ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
		this.clientDetailsService = clientDetailsService;
		this.grantType = grantType;
		this.tokenServices = tokenServices;
		this.requestFactory = requestFactory;
	}

	public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {

		if (!this.grantType.equals(grantType)) {
			return null;
		}
		
		String clientId = tokenRequest.getClientId();
		ClientDetails client = clientDetailsService.loadClientByClientId(clientId,true);
		validateGrantType(grantType, client);
		
		logger.debug("Getting access token for: " + clientId);

		return getAccessToken(client, tokenRequest);

	}

	protected OAuth2AccessToken getAccessToken(ClientDetails client, TokenRequest tokenRequest) {
		return tokenServices.createAccessToken(getOAuth2Authentication(client, tokenRequest));
	}

	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		OAuth2Request storedOAuth2Request = requestFactory.createOAuth2Request(client, tokenRequest);
		return new OAuth2Authentication(storedOAuth2Request, null);
	}

	protected void validateGrantType(String grantType, ClientDetails clientDetails) {
		Collection<String> authorizedGrantTypes = clientDetails.getAuthorizedGrantTypes();
		if (authorizedGrantTypes != null && !authorizedGrantTypes.isEmpty()
				&& !authorizedGrantTypes.contains(grantType)) {
			throw new InvalidClientException("Unauthorized grant type: " + grantType);
		}
	}

	protected AuthorizationServerTokenServices getTokenServices() {
		return tokenServices;
	}
	
	protected OAuth2RequestFactory getRequestFactory() {
		return requestFactory;
	}

}
