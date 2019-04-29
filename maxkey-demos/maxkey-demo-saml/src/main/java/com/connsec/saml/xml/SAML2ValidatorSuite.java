package com.connsec.saml.xml;

import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.ValidatorSuite;
import org.springframework.beans.factory.InitializingBean;

public class SAML2ValidatorSuite  {

	public void validate(XMLObject xmlObject) throws ValidationException {
	
		ValidatorSuite schemaValidator = Configuration.getValidatorSuite("saml2-core-schema-validator");
		schemaValidator.validate(xmlObject);
		ValidatorSuite specValidator = Configuration.getValidatorSuite("saml2-core-spec-validator");
		specValidator.validate(xmlObject);

	}
	
	
}
