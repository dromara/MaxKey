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


import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndpointGenerator {

	private final static Logger logger = LoggerFactory.getLogger(EndpointGenerator.class);
	
	private XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
	
	public Endpoint generateEndpoint(QName service, String location, String responseLocation) {
		
		logger.debug("end point service: {}", service);
		logger.debug("end point location: {}", location);
		logger.debug("end point responseLocation: {}", responseLocation);
		
		SAMLObjectBuilder<Endpoint> endpointBuilder = (SAMLObjectBuilder<Endpoint>) builderFactory.getBuilder(service);
		Endpoint samlEndpoint = endpointBuilder.buildObject();
		
        samlEndpoint.setLocation(location);
        
        //this does not have to be set
        if( StringUtils.isNotEmpty(responseLocation))
        	samlEndpoint.setResponseLocation(responseLocation);
        
        return samlEndpoint;
	}

}
