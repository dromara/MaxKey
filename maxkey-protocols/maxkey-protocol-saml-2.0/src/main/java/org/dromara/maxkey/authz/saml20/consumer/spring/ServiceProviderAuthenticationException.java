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
 * Indicates that the Service Provider was the entity that failed the authentication.
 * This could be because of an invalid Signature, invalid Message structure....
 * 
 * The user 'may' be authenticated with the Identity Provider.
 * 
 * Further attempts to authenticate the user with the IDP would most probably
 * be fruitless;
 * 
 * 
 * @author jcox
 *
 */
public class ServiceProviderAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8817095932085915398L;

	public ServiceProviderAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

	public ServiceProviderAuthenticationException(String msg) {
		super(msg);
	}

}
