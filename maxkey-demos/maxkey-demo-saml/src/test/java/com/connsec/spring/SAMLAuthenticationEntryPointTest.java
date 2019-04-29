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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;

import com.connsec.saml.AuthnRequestGenerator;
import com.connsec.saml.binding.BindingAdapter;
import com.connsec.saml.util.IDService;
import com.connsec.saml.util.TimeService;
import com.connsec.saml.xml.EndpointGenerator;
import com.connsec.spring.SAMLAuthenticationEntryPoint;

public class SAMLAuthenticationEntryPointTest {

	private final static Logger logger = LoggerFactory.getLogger(SAMLAuthenticationEntryPointTest.class);

	//args to class under test 
	@Mock private HttpServletRequest request;
	@Mock private HttpServletResponse response;
	@Mock AuthenticationException authException;
	

	//collaborators
	final String singleSignOnServiceURL = "https://idp.com/ssoEntryPoint";
	final String assertionConsumerServiceURL = "https://sp.com/assertionConsumerService";
	final String issuer ="https://sp.com";
	@Mock EndpointGenerator endpointGenerator;
	@Mock BindingAdapter bindingAdapter;
	@Mock AuthnRequestGenerator authnRequestGenerator;
	@Mock TimeService timeService;
	@Mock IDService idService;
	@Mock CredentialResolver credentialResolver;
	
	//return values of collabs
	@Mock Endpoint endpoint;
	@Mock AuthnRequest authnReqeust;
	@Mock Credential signingCredential;
	
	//class under test
	@InjectMocks SAMLAuthenticationEntryPoint authEntryPoint;

	@Before
	public void before() throws Exception {
		
		authEntryPoint = new SAMLAuthenticationEntryPoint(timeService, idService, assertionConsumerServiceURL);
		authEntryPoint.setAssertionConsumerServiceURL(assertionConsumerServiceURL);
		authEntryPoint.setSingleSignOnServiceURL(singleSignOnServiceURL);
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCommence() throws Exception {
		
		when(endpointGenerator.generateEndpoint(SingleSignOnService.DEFAULT_ELEMENT_NAME, singleSignOnServiceURL, assertionConsumerServiceURL)).thenReturn(endpoint);
		when(authnRequestGenerator.generateAuthnRequest(singleSignOnServiceURL, assertionConsumerServiceURL)).thenReturn(authnReqeust);
		
		authEntryPoint.commence(request, response, authException);

		verify(bindingAdapter).sendSAMLMessage(authnReqeust, endpoint, signingCredential, response);
		
	}
	
	@Test
	public void testBindingAdapterThrowsMessageEncodingException() throws Exception {
		
		when(endpointGenerator.generateEndpoint(SingleSignOnService.DEFAULT_ELEMENT_NAME, singleSignOnServiceURL, assertionConsumerServiceURL)).thenReturn(endpoint);
		when(authnRequestGenerator.generateAuthnRequest(singleSignOnServiceURL, assertionConsumerServiceURL)).thenReturn(authnReqeust);
		
		doThrow(new MessageEncodingException("MessageEncodingException!")).when(bindingAdapter).sendSAMLMessage(authnReqeust, endpoint, signingCredential, response);
		
		authEntryPoint.commence(request, response, authException);

		verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	@Test
	public void testAfterPropertiesSet() throws Exception {
		
		when(credentialResolver.resolveSingle((CriteriaSet) any())).thenReturn(signingCredential);
		
		authEntryPoint.authnRequestGenerator = null;
		authEntryPoint.endpointGenerator = null;
		
		authEntryPoint.afterPropertiesSet();
		
		assertTrue(authEntryPoint.endpointGenerator != null);
		assertTrue(authEntryPoint.authnRequestGenerator != null);
		
	}
	
}
