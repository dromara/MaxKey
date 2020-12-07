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
 

package org.maxkey.authz.saml20.xml;

import javax.xml.validation.Schema;

import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.messaging.handler.impl.SchemaValidateXMLMessage;
import org.springframework.core.io.ClassPathResource;

import net.shibboleth.utilities.java.support.xml.SchemaBuilder;

public class SAML2ValidatorSuite  {

	//SchemaValidateXMLMessage schemaValidator = Configuration.getValidatorSuite("saml2-core-schema-validator");
	//SchemaValidateXMLMessage specValidator = Configuration.getValidatorSuite("saml2-core-spec-validator");
	SchemaValidateXMLMessage schemaValidator = null;
	SchemaValidateXMLMessage specValidator = null;
	
	public SAML2ValidatorSuite() {
		try {
			schemaValidator = new SchemaValidateXMLMessage(loadSaml2Schema());
			schemaValidator.initialize();
			
			specValidator = new SchemaValidateXMLMessage(loadSpecSchema());
			specValidator.initialize();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
		
	}
	
	public Schema loadSaml2Schema() throws Exception {
		 ClassPathResource classPathResource = new ClassPathResource("org/opensaml/messaging/handler/impl/schemaValidateXmlMessageTest-schema.xsd");
	    SchemaBuilder schemaBuilder = new SchemaBuilder();
	    schemaBuilder.addSchema(classPathResource.getInputStream());
	    Schema schema = schemaBuilder.buildSchema();
	    return schema;
	}
	
	public Schema loadSpecSchema() throws Exception {
		 ClassPathResource classPathResource = new ClassPathResource("org/opensaml/messaging/handler/impl/schemaValidateXmlMessageTest-schema.xsd");
	    SchemaBuilder schemaBuilder = new SchemaBuilder();
	    schemaBuilder.addSchema(classPathResource.getInputStream());
	    Schema schema = schemaBuilder.buildSchema();
	    return schema;
	}
	
	public void validate(MessageContext messageContext)  {
		try {
			schemaValidator.invoke(messageContext);
			specValidator.invoke(messageContext);
		} catch (MessageHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
}
