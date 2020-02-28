package org.maxkey.authz.saml20.provider.xml;

import java.util.Collection;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.maxkey.authz.saml.service.IDService;
import org.maxkey.authz.saml.service.TimeService;
import org.maxkey.authz.saml20.xml.IssuerGenerator;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.impl.AssertionBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.springframework.security.core.GrantedAuthority;

public class AssertionGenerator {

	private final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

	private final IssuerGenerator issuerGenerator;
	private final SubjectGenerator subjectGenerator;
	private final IDService idService;
	private final TimeService timeService;
	private final AuthnStatementGenerator authnStatementGenerator ;
	private final AttributeStatementGenerator attributeStatementGenerator;
	private final ConditionsGenerator conditionsGenerator;

	public AssertionGenerator(
							String issuerName,
							TimeService timeService, 
							IDService idService) {
		this.timeService = timeService;
		this.idService = idService;
		issuerGenerator = new IssuerGenerator(issuerName);
		subjectGenerator = new SubjectGenerator(timeService);
		authnStatementGenerator = new AuthnStatementGenerator();
		attributeStatementGenerator = new AttributeStatementGenerator();
		conditionsGenerator = new ConditionsGenerator();
	}

	public Assertion generateAssertion(
							String assertionConsumerURL, 
							String nameIdValue,
							String inResponseTo, 
							String audienceUrl,
							int validInSeconds,
							Collection<GrantedAuthority> authorities, 
							HashMap<String,String>attributeMap,
							String clientAddress,
							DateTime authnInstant) {


		AssertionBuilder assertionBuilder = (AssertionBuilder) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
		Assertion assertion = assertionBuilder.buildObject();
		
		Subject subject = subjectGenerator.generateSubject(
						assertionConsumerURL,
						nameIdValue,
						inResponseTo,
						validInSeconds, 
						clientAddress);
		
		assertion.setSubject(subject);
		
		Issuer issuer = issuerGenerator.generateIssuer();
		assertion.setIssuer(issuer);
		
		AuthnStatement authnStatement = authnStatementGenerator.generateAuthnStatement(authnInstant);
		assertion.getAuthnStatements().add(authnStatement);
		
		AttributeStatement attributeStatement =attributeStatementGenerator.generateAttributeStatement(authorities, attributeMap);
		assertion.getAttributeStatements().add(attributeStatement);
		assertion.setID(idService.generateID());
		assertion.setIssueInstant(timeService.getCurrentDateTime());

		Conditions conditions = conditionsGenerator.generateConditions(audienceUrl,validInSeconds);
		assertion.setConditions(conditions);

		return assertion;
	}
}
