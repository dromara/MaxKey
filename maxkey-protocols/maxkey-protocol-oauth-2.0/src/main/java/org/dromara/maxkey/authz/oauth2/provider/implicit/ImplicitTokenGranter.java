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

package org.dromara.maxkey.authz.oauth2.provider.implicit;


import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Request;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2RequestFactory;
import org.dromara.maxkey.authz.oauth2.provider.TokenRequest;
import org.dromara.maxkey.authz.oauth2.provider.token.AbstractTokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.token.AuthorizationServerTokenServices;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * @author Dave Syer
 * 
 */
public class ImplicitTokenGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "implicit";

	public ImplicitTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest clientToken) {

		Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
		if (userAuth==null || !userAuth.isAuthenticated()) {
			throw new InsufficientAuthenticationException("There is no currently logged in user");
		}
		Assert.state(clientToken instanceof ImplicitTokenRequest, "An ImplicitTokenRequest is required here. Caller needs to wrap the TokenRequest.");
		
		OAuth2Request requestForStorage = ((ImplicitTokenRequest)clientToken).getOAuth2Request();
		
		return new OAuth2Authentication(requestForStorage, userAuth);

	}
	
	@SuppressWarnings("deprecation")
	public void setImplicitGrantService(ImplicitGrantService service) {
	}

}
