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


import org.opensaml.Configuration;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EndpointGeneratorTest extends AbstractXMLTest {

	private final static Logger logger = LoggerFactory.getLogger(EndpointGeneratorTest.class);
	
	EndpointGenerator  endpointGenerator;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		endpointGenerator = new EndpointGenerator();
	}

	public void testGenerateEndPoint() throws Exception {
		Endpoint endpoint = endpointGenerator.generateEndpoint(SingleSignOnService.DEFAULT_ELEMENT_NAME,"https://them.com/destination", "https://me.com/reply");

		assertNotNull("Endpoint generator generated a null endpoint", endpoint);

		MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
		Marshaller marshaller = marshallerFactory.getMarshaller(endpoint);

		Element root = marshaller.marshall(endpoint);

		String xml = writer.writeToString(root);
		logger.debug("xml is {}", xml);

		assertNotNull("the DOM could not be serialized into XML", xml);

		assertXpathExists(
				"/saml2md:SingleSignOnService[@Location='https://them.com/destination']",
				xml);
		assertXpathExists(
				"/saml2md:SingleSignOnService[@ResponseLocation]",
				xml);
		assertXpathExists(
				"/saml2md:SingleSignOnService[@ResponseLocation='https://me.com/reply']",
				xml);
	}
	
	public void testGenerateEndPointNoResponseLocation() throws Exception{
		Endpoint endpoint = endpointGenerator.generateEndpoint(SingleSignOnService.DEFAULT_ELEMENT_NAME,"https://them.com/destination", null);

		assertNotNull("Endpoint generator generated a null endpoint", endpoint);

		MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
		Marshaller marshaller = marshallerFactory.getMarshaller(endpoint);

		Element root = marshaller.marshall(endpoint);

		String xml = writer.writeToString(root);
		logger.debug("xml is {}", xml);

		assertNotNull("the DOM could not be serialized into XML", xml);

		assertXpathExists(
				"/saml2md:SingleSignOnService[@Location='https://them.com/destination']",
				xml);
		
		assertXpathNotExists(
				"/saml2md:SingleSignOnService[@ResponseLocation]",
				xml);
	}
	
}
