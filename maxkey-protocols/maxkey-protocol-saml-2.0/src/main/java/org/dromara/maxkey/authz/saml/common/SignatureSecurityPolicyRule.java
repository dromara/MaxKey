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
 


package org.dromara.maxkey.authz.saml.common;

import org.opensaml.Configuration;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.security.SAMLSignatureProfileValidator;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.signature.impl.ExplicitKeySignatureTrustEngine;
import org.opensaml.xml.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
* Rule to check that the message has been signed by an issuer that has credentials
* in the keystore.
* 
* We could use a SAMLProtocolMessageXMLSignatureSecurityPolicyRule, but, that
* relies on role info to be set (which we will not be using).  Also, we will insist
* that the message be signed and not rely on an additional rule to check the isAuthenticated
* flag on the message context.
*/
public class SignatureSecurityPolicyRule  implements InitializingBean, SecurityPolicyRule {

	private static final  Logger logger = LoggerFactory.getLogger(SignatureSecurityPolicyRule.class);
	
	private final CredentialResolver credentialResolver;	
	private final SAMLSignatureProfileValidator samlSignatureProfileValidator;
	ExplicitKeySignatureTrustEngine trustEngine; 
	
	public SignatureSecurityPolicyRule(CredentialResolver credentialResolver, SAMLSignatureProfileValidator samlSignatureProfileValidator) {
		super();
		this.credentialResolver = credentialResolver;
		this.samlSignatureProfileValidator = samlSignatureProfileValidator;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		KeyInfoCredentialResolver keyInfoCredResolver =
		Configuration.getGlobalSecurityConfiguration().getDefaultKeyInfoCredentialResolver();

		trustEngine = new ExplicitKeySignatureTrustEngine(credentialResolver,keyInfoCredResolver);		
	}

	public void loadTrustEngine(){
		KeyInfoCredentialResolver keyInfoCredResolver =
				Configuration.getGlobalSecurityConfiguration().getDefaultKeyInfoCredentialResolver();

				trustEngine = new ExplicitKeySignatureTrustEngine(credentialResolver,keyInfoCredResolver);	
	}
	
	@Override
	public void evaluate(MessageContext messageContext) throws SecurityPolicyException {
		
		logger.debug("evaluating signature of {}", messageContext);
		
		if(!( messageContext.getInboundMessage() instanceof SignableSAMLObject)) {
			throw new SecurityPolicyException("Inbound Message is not a SignableSAMLObject");
		}
		
		SignableSAMLObject samlMessage = (SignableSAMLObject) messageContext.getInboundMessage();
		//POST NEED Signed,but some is not
		if( !samlMessage.isSigned()) {
			logger.debug("evaluating signature POST NEED Signed,but some is not.");
			throw new SecurityPolicyException("InboundMessage was not signed.");
		}
				
		checkSignatureProfile(samlMessage);

		checkMessageSignature(messageContext, samlMessage);
	
	}

	private void checkMessageSignature(MessageContext messageContext,SignableSAMLObject samlMessage) throws SecurityPolicyException {
		CriteriaSet criteriaSet = new CriteriaSet();
		logger.debug("Inbound issuer is {}", messageContext.getInboundMessageIssuer());
		//https://localhost-dev-ed.my.salesforce.com
		criteriaSet.add( new EntityIDCriteria(messageContext.getInboundMessageIssuer()));	
		//criteriaSet.add( new EntityIDCriteria("https://localhost-dev-ed.my.salesforce.com"));
		criteriaSet.add( new UsageCriteria(UsageType.SIGNING) );

		try {
			if (!trustEngine.validate( samlMessage.getSignature(), criteriaSet)) {
				throw new SecurityPolicyException("Signature was either invalid or signing key could not be established as trusted");
			}
		} catch (SecurityException se) {
			// System.out.println("Error evaluating the signature"+se.toString());
			throw new SecurityPolicyException("Error evaluating the signature",se);
		}
	}

	private void checkSignatureProfile(SignableSAMLObject samlMessage)throws SecurityPolicyException {
		try {
			samlSignatureProfileValidator.validate(samlMessage.getSignature());
		} catch (ValidationException ve) {
		   
			throw new SecurityPolicyException("Signature did not conform to SAML Signature profile",ve);
		}
	}
}
