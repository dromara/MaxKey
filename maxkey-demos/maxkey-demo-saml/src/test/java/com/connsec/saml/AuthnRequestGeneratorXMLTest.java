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

import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.opensaml.saml2.core.AuthnRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connsec.saml.AuthnRequestGenerator;
import com.connsec.saml.util.IDService;
import com.connsec.saml.util.TimeService;
import com.connsec.saml.xml.AbstractXMLTest;

public class AuthnRequestGeneratorXMLTest extends AbstractXMLTest{

	private final static Logger logger = LoggerFactory.getLogger(AuthnRequestGeneratorXMLTest.class);

	AuthnRequestGenerator authnRequestGenerator;
	private final String singleSignonService = "https://idp.com/authresponsder";
	private final String assertionConsumerURL="https://sp.com/assertionConsumer";
	private final String issuerName = "the sp";
	private final String authnRequestID = "1234321";
	
	@Mock TimeService timeService;
	@Mock IDService idService;
	
	public AuthnRequestGeneratorXMLTest() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
		
		MockitoAnnotations.initMocks(this);

		authnRequestGenerator = new AuthnRequestGenerator(issuerName, timeService, idService);
	}
	
	public void testGenerateAuthnRequest()  throws Exception {
		
		when(timeService.getCurrentDateTime()).thenReturn(new DateTime("2010-10-26T09:30:00.000Z"));
		when(idService.generateID()).thenReturn(authnRequestID);
		
		AuthnRequest authnRequest = authnRequestGenerator.generateAuthnRequest(singleSignonService,assertionConsumerURL);
		
		assertNotNull(authnRequest);
		
		String xml =  getAsXMLString(authnRequest);
		
		logger.debug("AuthnRequest is: \n{}", xml);
		
		//saml-core-2.0-os section 3.2.1 Required attributes on RequestAbstract Type
		assertXpathExists("/saml2p:AuthnRequest",xml);
		assertXpathExists("/saml2p:AuthnRequest[@AssertionConsumerServiceURL='"+assertionConsumerURL+"']",xml);
		assertXpathExists("/saml2p:AuthnRequest[@ID='"+authnRequestID+"']",xml);
		//optional
		assertXpathExists("/saml2p:AuthnRequest[@Destination='"+singleSignonService+"']",xml);

		assertXpathExists("/saml2p:AuthnRequest[@AssertionConsumerServiceURL='"+assertionConsumerURL+"']",xml);
		
		
		//saml-profiles-2.0-os 4.1.4.2
		//lines 515-518
		assertXpathExists("/saml2p:AuthnRequest[saml2a:Issuer='"+issuerName+"']",xml);
		assertXpathExists( "/saml2p:AuthnRequest/saml2a:Issuer[@Format='urn:oasis:names:tc:SAML:2.0:nameid-format:entity']", xml);

	}
	
}
