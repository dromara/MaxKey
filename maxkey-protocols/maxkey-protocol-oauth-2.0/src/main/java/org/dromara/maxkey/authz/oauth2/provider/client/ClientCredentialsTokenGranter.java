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

package org.dromara.maxkey.authz.oauth2.provider.client;

import org.dromara.maxkey.authz.oauth2.common.DefaultOAuth2AccessToken;
import org.dromara.maxkey.authz.oauth2.common.OAuth2AccessToken;
import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2RequestFactory;
import org.dromara.maxkey.authz.oauth2.provider.TokenRequest;
import org.dromara.maxkey.authz.oauth2.provider.token.AbstractTokenGranter;
import org.dromara.maxkey.authz.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * @author Dave Syer
 * 
 */
public class ClientCredentialsTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "client_credentials";
    private boolean allowRefresh = false;

    public ClientCredentialsTokenGranter(AuthorizationServerTokenServices tokenServices,
            ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }
    
    public void setAllowRefresh(boolean allowRefresh) {
        this.allowRefresh = allowRefresh;
    }

    @Override
    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
        OAuth2AccessToken token = super.grant(grantType, tokenRequest);
        if (token != null) {
            DefaultOAuth2AccessToken norefresh = new DefaultOAuth2AccessToken(token);
            // The spec says that client credentials should not be allowed to get a refresh token
            if (!allowRefresh) {
                norefresh.setRefreshToken(null);
            }
            token = norefresh;
        }
        return token;
    }

}
