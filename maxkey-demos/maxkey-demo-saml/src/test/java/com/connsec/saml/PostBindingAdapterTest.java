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

package com.connsec.saml;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.common.binding.encoding.SAMLMessageEncoder;
import org.opensaml.saml2.binding.encoding.HTTPPostSimpleSignEncoder;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.security.credential.Credential;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import com.connsec.saml.binding.PostBindingAdapter;

public class PostBindingAdapterTest {

	//args to class under test
	@Mock Endpoint endpoint;
	@Mock HttpServletResponse response;
	@Mock HttpServletRequest request;
	@Mock SignableSAMLObject samlMessage;
	private String b64EncodedMessage = "some b64EncodedMessage";
	
	
	//collaborators
	@Mock SAMLMessageEncoder encoder;
	@Mock VelocityEngineFactoryBean velocityEngineFactory;
	@Mock SAMLMessageDecoder decoder;
	@Mock Credential signingCredential;
	private final String issuingEntityName = "https:/us.com";
	private final String inboundIssuingEntityName = "https://them.com";
	@Mock SecurityPolicyResolver securityPolicyResolver;

	
	
	@Mock VelocityEngine velocityEngine;
	
	//return values of collabs
	
	@Mock Response authnResponse;
	@Mock Issuer issuer;
	@Mock Credential cred;
	@Mock Endpoint endPoint;	
	
	//class under test
	@InjectMocks PostBindingAdapter postBindingAdapter;
	

	@Before
	public void before() {
		
		postBindingAdapter = new PostBindingAdapter(decoder,issuingEntityName,securityPolicyResolver);
		MockitoAnnotations.initMocks(this);
	}
	
	//@Test
	public void testExtractSAMLMessageContext() throws Exception {
		
		expectMessageIsDecoded();
		
		SAMLMessageContext messageContext = postBindingAdapter.extractSAMLMessageContext(request);
		
		assertEquals (authnResponse, messageContext.getInboundSAMLMessage());
		
	}
	
	private void expectMessageIsDecoded() throws Exception {
		  
		doAnswer(new Answer() {
		      public Object answer(InvocationOnMock invocation) {
		          Object[] args = invocation.getArguments();
		          SAMLMessageContext messageContext =  (SAMLMessageContext) args[0];
		          messageContext.setInboundMessage(authnResponse);
		          messageContext.setInboundSAMLMessage(authnResponse);
		          messageContext.setInboundMessageIssuer(inboundIssuingEntityName);
		          messageContext.setSecurityPolicyResolver(securityPolicyResolver);
		          return null;
		      }})
		  .when(decoder).decode((MessageContext) any());
	}

	
	@Test
	public void testSendSAMLMessage() throws Exception {
		
		
		postBindingAdapter.sendSAMLMessage(samlMessage, endpoint, signingCredential,response);
		
		ArgumentCaptor<BasicSAMLMessageContext> argument = ArgumentCaptor.forClass(BasicSAMLMessageContext.class);

		verify(encoder).encode(argument.capture());
		
		BasicSAMLMessageContext actualMC = argument.getValue();
		assertEquals(samlMessage, actualMC.getOutboundSAMLMessage());
		assertEquals(issuingEntityName, actualMC.getOutboundMessageIssuer());
		assertEquals(endpoint, actualMC.getPeerEntityEndpoint());
		assertEquals(signingCredential,actualMC.getOuboundSAMLMessageSigningCredential());
		assertEquals(HttpServletResponseAdapter.class, actualMC.getOutboundMessageTransport().getClass());
		
	}
	
	@Test
	public void testExtractSAMLRequestMessage() throws Exception {
		when(request.getParameter(PostBindingAdapter.SAML_REQUEST_POST_PARAM_NAME)).thenReturn(b64EncodedMessage);
		assertEquals(b64EncodedMessage, postBindingAdapter.extractSAMLMessage(request));
	}
	
	@Test
	public void testExtractSAMLResponseMessage() throws Exception {
		when(request.getParameter(PostBindingAdapter.SAML_RESPONSE_POST_PARAM_NAME)).thenReturn(b64EncodedMessage);
		assertEquals(b64EncodedMessage, postBindingAdapter.extractSAMLMessage(request));
	}
	
	@Test
	public void testAfterPropertiesSet() throws Exception {
		when(velocityEngineFactory.getObject()).thenReturn(velocityEngine);
		
		postBindingAdapter.afterPropertiesSet();
		
		assertTrue (postBindingAdapter.encoder instanceof HTTPPostSimpleSignEncoder);
		
	}
	
}
