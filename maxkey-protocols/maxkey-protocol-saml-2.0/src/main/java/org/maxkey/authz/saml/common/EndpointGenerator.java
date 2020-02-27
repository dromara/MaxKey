
package org.maxkey.authz.saml.common;


import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.impl.AssertionConsumerServiceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndpointGenerator {
	private final static Logger logger = LoggerFactory.getLogger(EndpointGenerator.class);
	
	public Endpoint generateEndpoint( String location) {
		logger.debug("end point location: {}", location);
		Endpoint samlEndpoint = new AssertionConsumerServiceBuilder().buildObject();
		
        samlEndpoint.setLocation(location);
        
        return samlEndpoint;
	}
	
	public Endpoint generateEndpoint( String location,String responseLocation, QName service) {
		logger.debug("end point service: {}", service);
		logger.debug("end point location: {}", location);
		logger.debug("end point responseLocation: {}", responseLocation);
		Endpoint samlEndpoint;
		if(null==service){
			service =  AssertionConsumerService.DEFAULT_ELEMENT_NAME;
		}
		samlEndpoint = new AssertionConsumerServiceBuilder().buildObject(service);
		
        samlEndpoint.setLocation(location);
        
        //this does not have to be set
        if( StringUtils.isNotEmpty(responseLocation)){
        	samlEndpoint.setResponseLocation(responseLocation);
        }
        
        return samlEndpoint;
	}

}
