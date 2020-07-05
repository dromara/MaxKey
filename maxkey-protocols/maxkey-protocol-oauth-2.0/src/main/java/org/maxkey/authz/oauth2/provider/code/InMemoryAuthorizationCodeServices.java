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
 

package org.maxkey.authz.oauth2.provider.code;

import java.time.Duration;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.maxkey.authz.oauth2.provider.OAuth2Authentication;

/**
 * Implementation of authorization code services that stores the codes and authentication in memory.
 * 
 * @author Ryan Heaton
 * @author Dave Syer
 */
public class InMemoryAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
			protected final static  UserManagedCache<String, OAuth2Authentication> authorizationCodeStore = 
					UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, OAuth2Authentication.class)
						.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(60)))
						.build(true);
	@Override
	protected void store(String code, OAuth2Authentication authentication) {
		authorizationCodeStore.put(code, authentication);
	}

	@Override
	public OAuth2Authentication remove(String code) {
		OAuth2Authentication auth = authorizationCodeStore.get(code);
		authorizationCodeStore.remove(code);
		return auth;
	}

}
