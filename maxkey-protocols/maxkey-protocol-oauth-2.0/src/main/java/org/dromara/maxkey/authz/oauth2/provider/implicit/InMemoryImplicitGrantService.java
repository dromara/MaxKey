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

import java.util.concurrent.ConcurrentHashMap;

import org.dromara.maxkey.authz.oauth2.provider.OAuth2Request;
import org.dromara.maxkey.authz.oauth2.provider.TokenRequest;

/**
 * In-memory implementation of the ImplicitGrantService.
 * 
 * @author Amanda Anganes
 *
 */
@SuppressWarnings("deprecation")
public class InMemoryImplicitGrantService implements ImplicitGrantService {

	protected final ConcurrentHashMap<TokenRequest, OAuth2Request> requestStore = new ConcurrentHashMap<TokenRequest, OAuth2Request>();
	
	public void store(OAuth2Request originalRequest, TokenRequest tokenRequest) {
		this.requestStore.put(tokenRequest, originalRequest);
	}

	public OAuth2Request remove(TokenRequest tokenRequest) {
		OAuth2Request request = this.requestStore.remove(tokenRequest);
		return request;
	}

}
