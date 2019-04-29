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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.connsec.saml.xml.SAML2ValidatorSuite;
import com.connsec.spring.IdentityProviderAuthenticationException;
import com.connsec.spring.ServiceProviderAuthenticationException;
import com.connsec.spring.User;

public class RealAssertionConsumer implements AssertionConsumer {

	private final static Logger logger = LoggerFactory
			.getLogger(RealAssertionConsumer.class);
	
	
	SAML2ValidatorSuite validatorSuite = new SAML2ValidatorSuite();
	
	@Override
	public User consume(Response samlResponse) throws AuthenticationException {
	
		
		try {
			validatorSuite.validate(samlResponse);
		} catch (ValidationException ve) {
			logger.warn("Response Message failed Validation", ve);
			throw new ServiceProviderAuthenticationException("Invalid SAML REsponse Message", ve);
		}

		
		checkResponseStatus(samlResponse);

		Assertion assertion = samlResponse.getAssertions().get(0);
		
		logger.debug("authenticationResponseIssuingEntityName {}" ,samlResponse.getIssuer().getValue()); 
		
		logger.debug("assertion.getID() {}" ,assertion.getID());
		logger.debug("assertion.getSubject().getNameID().getValue() {}", assertion.getSubject().getNameID().getValue());
		
		AuthnStatement authnStatement = assertion.getAuthnStatements().get(0);
		
		logger.debug("authnStatement.getAuthnInstant() {}",authnStatement.getAuthnInstant());

		Set<GrantedAuthority> authorities = extractAuthorities(assertion.getAttributeStatements());
		logger.debug("Granted Authorities will be {}" , authorities);
		
	
		logger.debug("assertion.getID() {}", assertion.getAuthnStatements());

		return new User(assertion.getSubject().getNameID().getValue(), 
						samlResponse.getIssuer().getValue(), 
						assertion.getIssuer().getValue(), 
						samlResponse.getID(), 
						assertion.getID(), 
						samlResponse.getIssueInstant(), 
						assertion.getIssueInstant(), 
						authnStatement.getAuthnInstant(), 
						authorities);
		
	}

	private Set<GrantedAuthority> extractAuthorities(
			List<AttributeStatement> attributeStatements) {
		

		for (AttributeStatement attributeStatement : attributeStatements) {
			for (Attribute attribute : attributeStatement.getAttributes()) {
				if(  GrantedAuthority.class.getName().equalsIgnoreCase(attribute.getName())) {
					logger.debug("found Granted Authorities.");
					Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
					for (XMLObject xmlObj : attribute.getAttributeValues()) {
						if(xmlObj instanceof XSString)
						authorities.add(new GrantedAuthorityImpl(((XSString)xmlObj).getValue()));
					}
					return authorities;
				}
			}
		}
		throw new ServiceProviderAuthenticationException("There were no GrantedAuthority.class.getName() AttributeStatements");

	}

	private void checkResponseStatus(Response samlResponse) {

		
		if(StatusCode.SUCCESS_URI.equals( StringUtils.trim(samlResponse.getStatus().getStatusCode().getValue()))) {
			
			additionalValidationChecksOnSuccessfulResponse(samlResponse);
			
		}
		
		
		else {
			
			StringBuilder extraInformation = extractExtraInformation(samlResponse);
			
			if(extraInformation.length() > 0) {
				logger.warn("Extra information extracted from authentication failure was {}", extraInformation.toString());
				
				throw new IdentityProviderAuthenticationException("Identity Provider has failed the authentication.", extraInformation.toString());
			}
			
			else {
				throw new IdentityProviderAuthenticationException("Identity Provider has failed the authentication.");
			}
			
		}
	}

	private void additionalValidationChecksOnSuccessfulResponse(
			Response samlResponse) {
		//saml validator suite does not check for assertions on successful auths
		if(samlResponse.getAssertions().isEmpty()){
			throw new ServiceProviderAuthenticationException("Successful Response did not contain any assertions");
		}
		
		//nor authnStatements
		else if(samlResponse.getAssertions().get(0).getAuthnStatements().isEmpty()){
			throw new ServiceProviderAuthenticationException("Successful Response did not contain an assertions with an AuthnStatement");
		}

		//we require at attribute statements
		else if(samlResponse.getAssertions().get(0).getAttributeStatements().isEmpty()){
			throw new ServiceProviderAuthenticationException("Successful Response did not contain an assertions with an AttributeStatements");

		}
		//we will require an issuer
		else if(samlResponse.getIssuer() == null) {
			throw new ServiceProviderAuthenticationException("Successful Response did not contain any Issuer");

		}
	}

	private StringBuilder extractExtraInformation(Response samlResponse) {
		StringBuilder extraInformation = new StringBuilder();
		
		if( samlResponse.getStatus().getStatusCode().getStatusCode() !=null ) {
		
			extraInformation.append(samlResponse.getStatus().getStatusCode().getStatusCode().getValue());
		}
		
		if(samlResponse.getStatus().getStatusMessage() != null) {
			if(extraInformation.length() > 0) {
				extraInformation.append("  -  ");
			}
			extraInformation.append(samlResponse.getStatus().getStatusMessage());
		}
		return extraInformation;
	}

}
