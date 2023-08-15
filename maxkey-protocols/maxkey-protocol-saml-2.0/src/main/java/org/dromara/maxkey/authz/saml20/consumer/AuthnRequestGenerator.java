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
 


package org.dromara.maxkey.authz.saml20.consumer;


import org.dromara.maxkey.authz.saml.service.IDService;
import org.dromara.maxkey.authz.saml.service.TimeService;
import org.dromara.maxkey.authz.saml20.xml.IssuerGenerator;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;


public class AuthnRequestGenerator {
	
	private final String issuingEntityName;
	private final TimeService timeService; 
	private final IDService idService;
	private IssuerGenerator issuerGenerator;
		
	public AuthnRequestGenerator(String issuingEntityName, TimeService timeService, IDService idService) {
		super();
		this.issuingEntityName = issuingEntityName;
		this.timeService = timeService;
		this.idService = idService;
		
		issuerGenerator = new IssuerGenerator(this.issuingEntityName);
	}

	public AuthnRequest generateAuthnRequest(String destination, String responseLocation) {
		AuthnRequest authnRequest = new AuthnRequestBuilder().buildObject();
		
		authnRequest.setAssertionConsumerServiceURL(responseLocation);
		authnRequest.setID(idService.generateID());
		authnRequest.setIssueInstant(timeService.getCurrentDateTime());
		authnRequest.setDestination(destination);
		
		authnRequest.setIssuer(issuerGenerator.generateIssuer());
		
		return authnRequest;
	}


	
	
}
