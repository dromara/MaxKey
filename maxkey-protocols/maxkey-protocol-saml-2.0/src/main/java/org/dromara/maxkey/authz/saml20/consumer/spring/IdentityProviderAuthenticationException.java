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
 

package org.dromara.maxkey.authz.saml20.consumer.spring;

import org.springframework.security.core.AuthenticationException;

/**
 * Indicates that the Identity Provider has failed the user's attempt to log in.
 * This could be because the user has entered invalid credentials, the account is locked, etc...
 * 
 * The user may be able to re-auththenticate given a second chance, or at the very least,
 * be able to see in the IDP's ui as to why the authentication was not successful. 
 * 
 * 
 * 
 * @author jcox
 *
 */
public class IdentityProviderAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1106622672393663684L;


	public IdentityProviderAuthenticationException(String msg, Object extraInformation) {
		super(msg, (Throwable) extraInformation);
	}


	public IdentityProviderAuthenticationException(String msg) {
		super(msg);
	}

}
