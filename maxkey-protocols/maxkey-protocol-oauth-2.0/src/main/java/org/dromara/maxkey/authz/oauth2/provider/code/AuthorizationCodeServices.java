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

import org.dromara.maxkey.authz.oauth2.common.exceptions.InvalidGrantException;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;

/**
 * Services for issuing and storing authorization codes.
 * 
 * @author Ryan Heaton
 */
public interface AuthorizationCodeServices {

	/**
	 * Create a authorization code for the specified authentications.
	 * 
	 * @param authentication The authentications to store.
	 * @return The generated code.
	 */
	String createAuthorizationCode(OAuth2Authentication authentication);

	/**
	 * Consume a authorization code.
	 * 
	 * @param code The authorization code to consume.
	 * @return The authentications associated with the code.
	 * @throws InvalidGrantException If the authorization code is invalid or expired.
	 */
	OAuth2Authentication consumeAuthorizationCode(String code)
			throws InvalidGrantException;

}
