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
 

package org.dromara.maxkey.authz.oauth2.provider.code;

import java.util.UUID;

import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidGrantException;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;

/**
 * Base implementation for authorization code services that generates a random-value authorization code.
 * 
 * @author Ryan Heaton
 * @author Dave Syer
 */
public abstract class RandomValueAuthorizationCodeServices implements AuthorizationCodeServices {

	//default Random code Generator
	//private RandomValueStringGenerator generator = new RandomValueStringGenerator();

	protected abstract void store(String code, OAuth2Authentication authentication);

	protected abstract OAuth2Authentication remove(String code);

	public String createAuthorizationCode(OAuth2Authentication authentication) {
		//String code = generator.generate();
		/*
		 * replace with uuid random code
		 * add by Crystal.Sea
		 */
		String code = UUID.randomUUID().toString();
		store(code, authentication);
		return code;
	}

	public OAuth2Authentication consumeAuthorizationCode(String code)
			throws InvalidGrantException {
		OAuth2Authentication auth = this.remove(code);
		if (auth == null) {
			throw new InvalidGrantException("Invalid authorization code: " + code);
		}
		return auth;
	}

}
