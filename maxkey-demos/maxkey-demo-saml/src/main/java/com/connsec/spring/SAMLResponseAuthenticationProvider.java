/*
*   Copyright 2010 James Cox <james.s.cox@gmail.com>
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

package com.connsec.spring;

import org.opensaml.saml2.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.connsec.saml.AssertionConsumer;

public class SAMLResponseAuthenticationProvider implements
		AuthenticationProvider {

	private final static Logger logger = LoggerFactory
			.getLogger(SAMLResponseAuthenticationProvider.class);
	
	private final AssertionConsumer assertionConsumer;

	public SAMLResponseAuthenticationProvider(AssertionConsumer assertionConsumer) {
		super();
		this.assertionConsumer = assertionConsumer;
	}

	@Override
	public Authentication authenticate(Authentication submitted)
			throws AuthenticationException {
		
		logger.debug("attempting to authenticate: {}", submitted);
		
		User user = assertionConsumer.consume((Response)submitted.getPrincipal());
		
		logger.debug("getPrincipal: {}", submitted.getPrincipal());
		SAMLAuthenticationToken authenticated = new SAMLAuthenticationToken(user, (String)submitted.getCredentials(), user.getAuthorities());
		
		authenticated.setDetails(submitted.getDetails());
		
		logger.debug("Returning with authentication token of {}", authenticated);
		
		return authenticated;
		
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (SAMLAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
