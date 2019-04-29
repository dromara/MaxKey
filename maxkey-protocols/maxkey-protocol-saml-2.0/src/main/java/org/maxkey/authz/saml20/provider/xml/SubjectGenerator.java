
package org.maxkey.authz.saml20.provider.xml;


import org.maxkey.authz.saml.service.TimeService;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml2.core.impl.NameIDBuilder;
import org.opensaml.saml2.core.impl.SubjectBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationDataBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;

public class SubjectGenerator {

	private final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
	private final TimeService timeService;
		
	public SubjectGenerator(TimeService timeService) {
		super();
		this.timeService = timeService;
	}

	public Subject generateSubject( 
							String assertionConsumerURL, 
							String nameIdValue,
							String inResponseTo, 
							int validInSeconds, 
							String clientAddress) {
		
		
		NameID nameID =builderNameID(nameIdValue,assertionConsumerURL);
		Subject subject =builderSubject(nameID);
		SubjectConfirmation subjectConfirmation =builderSubjectConfirmation(
								assertionConsumerURL,
								inResponseTo,
								validInSeconds,
								clientAddress);

		subject.getSubjectConfirmations().add(subjectConfirmation);
		
		return subject;
	}
	
	public NameID builderNameID(String value,String strSPNameQualifier){
		//Response/Assertion/Subject/NameID
		NameIDBuilder nameIDBuilder = (NameIDBuilder) builderFactory.getBuilder(NameID.DEFAULT_ELEMENT_NAME);
		NameID nameID = nameIDBuilder.buildObject();
		nameID.setValue(value);
		nameID.setFormat(NameIDType.PERSISTENT);
		nameID.setSPNameQualifier(strSPNameQualifier);
		
		return nameID;
	}
	
	public Subject builderSubject (NameID nameID){
		//Response/Assertion/Subject
		SubjectBuilder subjectBuilder =  (SubjectBuilder)builderFactory.getBuilder(Subject.DEFAULT_ELEMENT_NAME);
		Subject subject = subjectBuilder.buildObject();
		subject.setNameID(nameID);
		return subject;
	}
	
	public SubjectConfirmation builderSubjectConfirmation(String recipient,String inResponseTo,int validInSeconds,String clientAddress){
		SubjectConfirmationBuilder subjectConfirmationBuilder = (SubjectConfirmationBuilder)builderFactory.getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
		SubjectConfirmation subjectConfirmation = subjectConfirmationBuilder.buildObject();
		subjectConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);
		
		SubjectConfirmationDataBuilder subjectConfirmationDataBuilder = (SubjectConfirmationDataBuilder)builderFactory.getBuilder(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
		SubjectConfirmationData subjectConfirmationData = subjectConfirmationDataBuilder.buildObject();
		
		subjectConfirmationData.setRecipient(recipient);
		//if idp-init not need inResponseTo
		if(null!=inResponseTo){
			subjectConfirmationData.setInResponseTo(inResponseTo);
		}
		subjectConfirmationData.setNotOnOrAfter(timeService.getCurrentDateTime().plusSeconds(validInSeconds));
		subjectConfirmationData.setAddress(clientAddress);
		
		subjectConfirmation.setSubjectConfirmationData(subjectConfirmationData);
		
		return subjectConfirmation;
	}
	
}
