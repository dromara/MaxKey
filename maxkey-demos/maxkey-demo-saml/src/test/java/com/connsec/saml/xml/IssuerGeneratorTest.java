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

package com.connsec.saml.xml;


import org.opensaml.saml2.core.Issuer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IssuerGeneratorTest extends AbstractXMLTest {

	private final static Logger logger = LoggerFactory
			.getLogger(IssuerGeneratorTest.class);

	IssuerGenerator generator;
	String issuerName = "some guy";
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		generator = new IssuerGenerator(issuerName);
	}


	public void testGenerate() throws Exception {
		
		Issuer issuer = generator.generateIssuer();
		assertNotNull(issuer);
		
		String xml =  getAsXMLString(issuer);
		
		logger.debug("AuthnRequest is: \n{}", xml);
		
		assertXpathExists( "/saml2a:Issuer[@Format='urn:oasis:names:tc:SAML:2.0:nameid-format:entity']", xml);
		assertXpathExists("/saml2a:Issuer['"+issuer+"']",xml);
	}
	
}
