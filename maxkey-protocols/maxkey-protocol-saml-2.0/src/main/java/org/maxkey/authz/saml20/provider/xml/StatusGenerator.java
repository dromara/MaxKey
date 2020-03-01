
package org.maxkey.authz.saml20.provider.xml;

import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.StatusMessage;
import org.opensaml.saml2.core.impl.StatusBuilder;
import org.opensaml.saml2.core.impl.StatusCodeBuilder;
import org.opensaml.saml2.core.impl.StatusMessageBuilder;

public class StatusGenerator {

	
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
		Status status =  (Status) new StatusBuilder().buildObject();
		return status;
	}
	
	public StatusCode builderStatusCode(String value){
		StatusCode statusCode = (StatusCode) new StatusCodeBuilder().buildObject();
		statusCode.setValue(value);
		
		return statusCode;
	}
	
	public StatusMessage builderStatusMessage(String message){
		StatusMessage statusMessage = new StatusMessageBuilder().buildObject();
		
		statusMessage.setMessage(message);
		
		return statusMessage;
	}
	
}
