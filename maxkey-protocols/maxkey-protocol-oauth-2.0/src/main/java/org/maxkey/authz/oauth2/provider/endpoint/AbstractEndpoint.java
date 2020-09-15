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
package org.maxkey.authz.oauth2.provider.endpoint;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.maxkey.authz.oauth2.provider.CompositeTokenGranter;
import org.maxkey.authz.oauth2.provider.OAuth2RequestFactory;
import org.maxkey.authz.oauth2.provider.TokenGranter;
import org.maxkey.authz.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.maxkey.authz.oauth2.provider.code.AuthorizationCodeServices;
import org.maxkey.authz.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.maxkey.authz.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.maxkey.authz.oauth2.provider.implicit.ImplicitTokenGranter;
import org.maxkey.authz.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.maxkey.authz.oauth2.provider.refresh.RefreshTokenGranter;
import org.maxkey.authz.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.maxkey.authz.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.Assert;

/**
 * @author Dave Syer
 * 
 */
public class AbstractEndpoint implements InitializingBean {

	protected final Log logger = LogFactory.getLog(getClass());

	private TokenGranter tokenGranter;
	
	@Autowired
  	@Qualifier("oauth20AuthorizationCodeServices")
	protected AuthorizationCodeServices authorizationCodeServices = new InMemoryAuthorizationCodeServices();
	
	@Autowired
  	@Qualifier("oauth20TokenServices")
	AuthorizationServerTokenServices tokenServices ;
	
	@Autowired
  	@Qualifier("oauth20JdbcClientDetailsService")
	private ClientDetailsService clientDetailsService;
	@Autowired
  	@Qualifier("oAuth2RequestFactory")
	private OAuth2RequestFactory oAuth2RequestFactory;
	
	@Autowired
  	@Qualifier("oAuth2RequestFactory")
	private OAuth2RequestFactory defaultOAuth2RequestFactory;

	@Autowired
    @Qualifier("oauth20UserAuthenticationManager")
	AuthenticationManager authenticationManager;
	
	
	public void afterPropertiesSet() throws Exception {
		if (tokenGranter == null) {
			//ClientDetailsService clientDetails = clientDetailsService();
			//AuthorizationServerTokenServices tokenServices = tokenServices();
			//AuthorizationCodeServices authorizationCodeServices = authorizationCodeServices();
			//OAuth2RequestFactory requestFactory = requestFactory();

			List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
			tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices,
					clientDetailsService, oAuth2RequestFactory));
			tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, oAuth2RequestFactory));
			ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetailsService, oAuth2RequestFactory);
			tokenGranters.add(implicit);
			tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, oAuth2RequestFactory));
			if (authenticationManager != null) {
				tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices,
				        clientDetailsService, oAuth2RequestFactory));
			}
			tokenGranter = new CompositeTokenGranter(tokenGranters);
		}
		Assert.state(tokenGranter != null, "TokenGranter must be provided");
		Assert.state(clientDetailsService != null, "ClientDetailsService must be provided");
		defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(getClientDetailsService());
		if (oAuth2RequestFactory == null) {
			oAuth2RequestFactory = defaultOAuth2RequestFactory;
		}
	}



	public void setTokenGranter(TokenGranter tokenGranter) {
		this.tokenGranter = tokenGranter;
	}

	protected TokenGranter getTokenGranter() {
		return tokenGranter;
	}



	protected OAuth2RequestFactory getOAuth2RequestFactory() {
		return oAuth2RequestFactory;
	}

	protected OAuth2RequestFactory getDefaultOAuth2RequestFactory() {
		return defaultOAuth2RequestFactory;
	}

	public void setOAuth2RequestFactory(OAuth2RequestFactory oAuth2RequestFactory) {
		this.oAuth2RequestFactory = oAuth2RequestFactory;
	}

	protected ClientDetailsService getClientDetailsService() {
		return clientDetailsService;
	}

	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}

}