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
 

package org.dromara.maxkey.authz.oauth2.provider.implicit;

import org.dromara.maxkey.authz.oauth2.provider.OAuth2Request;
import org.dromara.maxkey.authz.oauth2.provider.TokenRequest;

/**
 * Service to associate & store an incoming AuthorizationRequest with the TokenRequest that is passed
 * to the ImplicitTokenGranter during the Implicit flow. This mimics the AuthorizationCodeServices
 * functionality from the Authorization Code flow, allowing the ImplicitTokenGranter to reference the original 
 * AuthorizationRequest, while still allowing the ImplicitTokenGranter to adhere to the TokenGranter interface. 
 * 
 * @author Amanda Anganes
 * 
 * @deprecated with no replacement (it shouldn't be necessary to use this strategy since 2.0.2)
 *
 */
@Deprecated
public interface ImplicitGrantService {

	/**
	 * Save an association between an OAuth2Request and a TokenRequest.
	 * 
	 * @param originalRequest
	 * @param tokenRequest
	 */
	public void store(OAuth2Request originalRequest, TokenRequest tokenRequest);
	
	/**
	 * Look up and return the OAuth2Request associated with the given TokenRequest.
	 * 
	 * @param tokenRequest
	 * @return
	 */
	public OAuth2Request remove(TokenRequest tokenRequest);
	
}
