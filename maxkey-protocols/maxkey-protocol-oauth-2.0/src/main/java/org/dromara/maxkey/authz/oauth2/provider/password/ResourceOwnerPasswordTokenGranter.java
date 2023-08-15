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

package org.dromara.maxkey.authz.oauth2.provider.password;

import java.util.LinkedHashMap;
import java.util.Map;

import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidGrantException;
import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Request;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2RequestFactory;
import org.dromara.maxkey.authz.oauth2.provider.TokenRequest;
import org.dromara.maxkey.authz.oauth2.provider.token.AbstractTokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.token.AuthorizationServerTokenServices;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * @author Dave Syer
 * 
 */
public class ResourceOwnerPasswordTokenGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "password";

	private final AuthenticationManager authenticationManager;

	public ResourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager,
			AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

		Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
		String username = parameters.get("username");
		String password = parameters.get("password");
		// Protect from downstream leaks of password
		parameters.remove("password");

		Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
		try {
			userAuth = authenticationManager.authenticate(userAuth);
		}
		catch (AccountStatusException ase) {
			//covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
			throw new InvalidGrantException(ase.getMessage());
		}
		catch (BadCredentialsException e) {
			// If the username/password are wrong the spec says we should send 400/invlid grant
			throw new InvalidGrantException(e.getMessage());
		}
		if (userAuth == null || !userAuth.isAuthenticated()) {
			throw new InvalidGrantException("Could not authenticate user: " + username);
		}
		
		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);		
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}
}
