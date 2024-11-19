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
package org.dromara.maxkey.authz.oauth2.provider.endpoint;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.CompositeTokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2RequestFactory;
import org.dromara.maxkey.authz.oauth2.provider.TokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.code.AuthorizationCodeServices;
import org.dromara.maxkey.authz.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.dromara.maxkey.authz.oauth2.provider.implicit.ImplicitTokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.refresh.RefreshTokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.dromara.maxkey.authz.oauth2.provider.token.AuthorizationServerTokenServices;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.persistence.cache.MomentaryService;
import org.dromara.maxkey.persistence.service.AppsService;
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
	protected AuthorizationServerTokenServices tokenServices ;
	
	@Autowired
  	@Qualifier("oauth20JdbcClientDetailsService")
	protected ClientDetailsService clientDetailsService;
	
	@Autowired
  	@Qualifier("oAuth2RequestFactory")
	protected OAuth2RequestFactory oAuth2RequestFactory;
	
	@Autowired
  	@Qualifier("oAuth2RequestFactory")
	protected OAuth2RequestFactory defaultOAuth2RequestFactory;

	@Autowired
    @Qualifier("oauth20UserAuthenticationManager")
	AuthenticationManager authenticationManager;
	
	@Autowired
    protected AppsService appsService;
	
	@Autowired 
    @Qualifier("applicationConfig")
    protected ApplicationConfig applicationConfig;
	
	@Autowired 
	protected MomentaryService momentaryService;
	
	
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