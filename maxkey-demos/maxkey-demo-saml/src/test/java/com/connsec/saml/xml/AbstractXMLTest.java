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

import java.util.HashMap;

import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObject;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public abstract class AbstractXMLTest extends XMLTestCase {

	private final static Logger logger = LoggerFactory.getLogger(AbstractXMLTest.class);
	
//	used to serialize the DOM to a string for inspection
	protected LSSerializer writer;
	protected DOMImplementationLS domImpl;
	
	private boolean initialized;

	public AbstractXMLTest() {
		super();
		setUpOnce();
	}

	public AbstractXMLTest(String name) {
		super();
		setUpOnce();
	}
	
	//note that we are extending XMLTestCase, can't put this in a @BeforeClass
	public void setUpOnce() {
		try {
			// init open saml
			DefaultBootstrap.bootstrap();

			// init xml handling
			DOMImplementationRegistry registry = DOMImplementationRegistry
					.newInstance();

			domImpl = (DOMImplementationLS) registry.getDOMImplementation("LS");
			writer = domImpl.createLSSerializer();
			initialized = true;

		} catch (Exception ex) {
			logger.error("Could not initialize test env", ex);
			initialized = false;
		}
	}
	
	@Override
	protected void setUp() throws Exception {
		
		assertTrue("Failure initializing open saml ",initialized);
		super.setUp();
		
		//tell XMLUnit about the expected name spaces
		HashMap<String,String> m = new HashMap<String,String>();  
		m.put("saml2a", "urn:oasis:names:tc:SAML:2.0:assertion");  
		m.put("saml2p", "urn:oasis:names:tc:SAML:2.0:protocol");
		m.put("saml2md", "urn:oasis:names:tc:SAML:2.0:metadata");
		//m.put("saml2p", "urn:oasis:names:tc:SAML:2.0:protocol");
		SimpleNamespaceContext ctx = new SimpleNamespaceContext(m);  
		XMLUnit.setXpathNamespaceContext(ctx);  
	}

	protected String getAsXMLString(SAMLObject response) throws MarshallingException {
		MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
		Marshaller marshaller = marshallerFactory.getMarshaller(response);
	
		Element root = marshaller.marshall(response);
		
		return writer.writeToString(root);
	}
	
	
}
