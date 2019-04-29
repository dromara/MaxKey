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

import org.opensaml.Configuration;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;

import com.connsec.saml.util.IDService;
import com.connsec.saml.util.TimeService;
import com.connsec.saml.xml.IssuerGenerator;


public class AuthnRequestGenerator {

	private XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
	
	private final String issuingEntityName;
	private final TimeService timeService; 
	private final IDService idService;
	private IssuerGenerator issuerGenerator;
		
	public AuthnRequestGenerator(String issuingEntityName, TimeService timeService, IDService idService) {
		super();
		this.issuingEntityName = issuingEntityName;
		this.timeService = timeService;
		this.idService = idService;
		
		issuerGenerator = new IssuerGenerator(issuingEntityName);
	}

	public AuthnRequest generateAuthnRequest(String destination, String responseLocation) {
		
		AuthnRequestBuilder authnRequestBuilder = (AuthnRequestBuilder) builderFactory.getBuilder(AuthnRequest.DEFAULT_ELEMENT_NAME);
		
		AuthnRequest authnRequest = authnRequestBuilder.buildObject();
		
		authnRequest.setAssertionConsumerServiceURL(responseLocation);
		authnRequest.setID(idService.generateID());
		authnRequest.setIssueInstant(timeService.getCurrentDateTime());
		authnRequest.setDestination(destination);
		
		authnRequest.setIssuer(issuerGenerator.generateIssuer());
		
		return authnRequest;
	}


	
	
}
