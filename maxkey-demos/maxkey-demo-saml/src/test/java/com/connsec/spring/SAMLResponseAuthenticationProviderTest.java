package com.connsec.spring;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.opensaml.saml2.core.Response;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.connsec.saml.AssertionConsumer;
import com.connsec.spring.SAMLAuthenticationToken;
import com.connsec.spring.SAMLResponseAuthenticationProvider;
import com.connsec.spring.User;

public class SAMLResponseAuthenticationProviderTest {

	//args to class under test
	@Mock SAMLAuthenticationToken submitted;
	String credentials ="some b64 encoded message";
	@Mock Response response;
	@Mock Object details;

	//collabs
	@Mock AssertionConsumer assertionConsumer;
	
	//return values of collabs
	@Mock User user;
	Collection<GrantedAuthority> authorities; 
	
	//class under test
	SAMLResponseAuthenticationProvider authenticationProvider;

	
	@Before
	public void before() throws Exception {

		MockitoAnnotations.initMocks(this);
		authenticationProvider = new SAMLResponseAuthenticationProvider(assertionConsumer);
		
		authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
	}
	
	@Test
	public void testAuthenticate() throws Exception {
		
		when(submitted.getCredentials()).thenReturn(credentials);
		when(submitted.getDetails()).thenReturn(details);
		when(submitted.getPrincipal()).thenReturn(response);
		
		when(assertionConsumer.consume(response)).thenReturn(user);
		when(user.getAuthorities()).thenReturn(authorities);
		
		Authentication authentication = authenticationProvider.authenticate(submitted);
		
		assertEquals(user, authentication.getPrincipal());
		assertEquals(credentials, authentication.getCredentials());
		assertEquals(details, authentication.getDetails());
		assertEquals(authorities, authentication.getAuthorities());
		
	}
	
	
	@Test
	public void testSupports() throws Exception {
		assertTrue(authenticationProvider.supports(SAMLAuthenticationToken.class));
	}
	
	@Test
	public void testDoesNotSupport() throws Exception {
		assertFalse(authenticationProvider.supports(UsernamePasswordAuthenticationToken.class));
	}
	
}
