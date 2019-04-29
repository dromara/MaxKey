
package org.maxkey.authz.saml20.provider.xml;

import org.opensaml.Configuration;

import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.StatusMessage;
import org.opensaml.saml2.core.impl.StatusBuilder;
import org.opensaml.saml2.core.impl.StatusCodeBuilder;
import org.opensaml.saml2.core.impl.StatusMessageBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;

public class StatusGenerator {

	private final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
	
	
	public Status generateStatus( String value ) {
		Status status =  builderStatus();
		
		StatusCode statusCode =  builderStatusCode(value);
		
		status.setStatusCode(statusCode);
		
		return status;
	}
	
	public Status generateStatus( String value, String subStatus, String message ) {
		Status status =  builderStatus();
		
		StatusCode statusCode =  builderStatusCode(value);
		
		StatusCode subStatusCode =builderStatusCode(value);
		
		statusCode.setStatusCode(subStatusCode);
		
		status.setStatusCode(statusCode);
		
		StatusMessage statusMessage = builderStatusMessage(message);
		
		status.setStatusMessage(statusMessage);
		
		return status;
	}
	
	public Status builderStatus(){
		StatusBuilder builder = (StatusBuilder) builderFactory.getBuilder(Status.DEFAULT_ELEMENT_NAME);
		Status status =  (Status) builder.buildObject();
		return status;
	}
	
	public StatusCode builderStatusCode(String value){
		StatusCodeBuilder codeBuilder = (StatusCodeBuilder) builderFactory.getBuilder(StatusCode.DEFAULT_ELEMENT_NAME);
		
		StatusCode statusCode = (StatusCode) codeBuilder.buildObject();
		statusCode.setValue(value);
		
		return statusCode;
	}
	
	public StatusMessage builderStatusMessage(String message){
		StatusMessageBuilder statusMessageBuilder = (StatusMessageBuilder) builderFactory.getBuilder(StatusMessage.DEFAULT_ELEMENT_NAME);
		StatusMessage statusMessage = statusMessageBuilder.buildObject();
		
		statusMessage.setMessage(message);
		
		return statusMessage;
	}
	
}
