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

import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidScopeException;
import org.dromara.maxkey.authz.oauth2.provider.endpoint.TokenEndpoint;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;

/**
 * Validation interface for OAuth2 requests to the {@link AuthorizationEndpoint} and {@link TokenEndpoint}.
 * 
 * @author Amanda Anganes
 *
 */
public interface OAuth2RequestValidator {

	/**
	 * Ensure that the client has requested a valid set of scopes.
	 * 
	 * @param authorizationRequest the AuthorizationRequest to be validated
	 * @param client the client that is making the request
	 * @throws InvalidScopeException if a requested scope is invalid
	 */
	public void validateScope(AuthorizationRequest authorizationRequest, ClientDetails client) throws InvalidScopeException;
	
	/**
	 * Ensure that the client has requested a valid set of scopes.
	 * 
	 * @param tokenRequest the TokenRequest to be validated
	 * @param client the client that is making the request
	 * @throws InvalidScopeException if a requested scope is invalid
	 */
	public void validateScope(TokenRequest tokenRequest, ClientDetails client) throws InvalidScopeException;
	
}
