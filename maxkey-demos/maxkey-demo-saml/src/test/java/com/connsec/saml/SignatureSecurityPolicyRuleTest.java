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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.opensaml.common.binding.security.SAMLProtocolMessageXMLSignatureSecurityPolicyRule;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.security.SAMLSignatureProfileValidator;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityConfiguration;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.impl.ExplicitKeySignatureTrustEngine;
import org.opensaml.xml.validation.ValidationException;

import com.connsec.saml.binding.SignatureSecurityPolicyRule;


public class SignatureSecurityPolicyRuleTest {

	//collabs
	@Mock CredentialResolver credentialResolver;
	@Mock SAMLSignatureProfileValidator samlSignatureProfileValidator;
	@Mock ExplicitKeySignatureTrustEngine trustEngine; 
	
	
	
	//args to class under test
	@Mock MessageContext messageContext;
	@Mock Signature signature;

	@Mock Issuer notSingable;
	@Mock AuthnRequest samlMessage;
	private String messageIssuer = "the issuer";

	//static state
	@Mock SecurityConfiguration securityConfig;
	@Mock  KeyInfoCredentialResolver keyInfoCredentialResolver;

	//class under test
	SignatureSecurityPolicyRule rule;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		rule = new SignatureSecurityPolicyRule(credentialResolver,samlSignatureProfileValidator);
	}
	
	@Test
	public void testAfterPropertiesSet() throws Exception {
		
		Configuration.setGlobalSecurityConfiguration(securityConfig);
		when(securityConfig.getDefaultKeyInfoCredentialResolver()).thenReturn(keyInfoCredentialResolver);
		
		rule.afterPropertiesSet();
		assertNotNull(rule.getTrustEngine());
	}
	

	@Test
	public void testEvaluate() throws Exception {
		rule.setTrustEngine(trustEngine);
		when(messageContext.getInboundMessage()).thenReturn(samlMessage);
		when(samlMessage.isSigned()).thenReturn(Boolean.TRUE);
		when(samlMessage.getSignature()).thenReturn(signature);
		when(messageContext.getInboundMessageIssuer()).thenReturn(messageIssuer );
		when(trustEngine.validate( eq(signature), (CriteriaSet) any())).thenReturn(Boolean.TRUE);
		
		rule.evaluate(messageContext);
		
		verify(samlSignatureProfileValidator).validate(signature);
		
	}
	
	@Test(expected=SecurityPolicyException.class)
	public void testInboundMessageIsNotSignable() throws Exception {
		when(messageContext.getInboundMessage()).thenReturn(notSingable);
		rule.evaluate(messageContext);
	}
	
	@Test(expected=SecurityPolicyException.class)
	public void testInboundMessageIsNotSigned() throws Exception {
		when(messageContext.getInboundMessage()).thenReturn(samlMessage);
		when(samlMessage.isSigned()).thenReturn(Boolean.FALSE);
		rule.evaluate(messageContext);
	}
	
	@Test(expected=SecurityPolicyException.class)
	public void testValidatorThrowsValdiationException() throws Exception {
		rule.setTrustEngine(trustEngine);
		when(messageContext.getInboundMessage()).thenReturn(samlMessage);

		when(samlMessage.isSigned()).thenReturn(Boolean.TRUE);
		when(samlMessage.getSignature()).thenReturn(signature);
		
		doThrow(new ValidationException("ValidationException!")).when(samlSignatureProfileValidator).validate(signature);
		rule.evaluate(messageContext);

	}
	
	@Test(expected=SecurityPolicyException.class)
	public void testInvalidMessageSignature() throws Exception {

		rule.setTrustEngine(trustEngine);
		when(samlMessage.isSigned()).thenReturn(Boolean.TRUE);
		when(samlMessage.getSignature()).thenReturn(signature);
		when(messageContext.getInboundMessage()).thenReturn(samlMessage);

		when(messageContext.getInboundMessageIssuer()).thenReturn(messageIssuer );
		when(trustEngine.validate( eq(signature), (CriteriaSet) any())).thenReturn(Boolean.FALSE);
		
		rule.evaluate(messageContext);
	}

	@Test(expected=SecurityPolicyException.class)
	public void testTruestEngineThrowsException() throws Exception {
		
		rule.setTrustEngine(trustEngine);
		when(samlMessage.isSigned()).thenReturn(Boolean.TRUE);
		when(samlMessage.getSignature()).thenReturn(signature);
		when(messageContext.getInboundMessageIssuer()).thenReturn(messageIssuer );
		when(trustEngine.validate( eq(signature), (CriteriaSet) any())).thenThrow(new org.opensaml.xml.security.SecurityException("SecurityException!"));		
		
		rule.evaluate(messageContext);
	}
	
}
