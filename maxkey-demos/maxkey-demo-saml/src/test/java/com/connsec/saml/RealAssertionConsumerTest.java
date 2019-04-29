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

package com.connsec.saml;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.StatusDetail;
import org.opensaml.saml2.core.StatusMessage;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml2.core.impl.AttributeBuilder;
import org.opensaml.saml2.core.impl.AttributeStatementBuilder;
import org.opensaml.saml2.core.impl.AuthnContextBuilder;
import org.opensaml.saml2.core.impl.AuthnContextClassRefBuilder;
import org.opensaml.saml2.core.impl.AuthnStatementBuilder;
import org.opensaml.saml2.core.impl.NameIDBuilder;
import org.opensaml.saml2.core.impl.ResponseImpl;
import org.opensaml.saml2.core.impl.SubjectBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationDataBuilder;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.schema.impl.XSStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.connsec.saml.RealAssertionConsumer;
import com.connsec.spring.IdentityProviderAuthenticationException;
import com.connsec.spring.ServiceProviderAuthenticationException;
import com.connsec.spring.User;

/**
 * More of an integration test than just a unit test.
 * 
 * @author jcox
 *
 */
public class RealAssertionConsumerTest {

	private String responseID= "1";
	private String inResponseTo= "XYZ";
	private String assertionID = "777";
	private String assertionIssuer = "assertion issuer";
	private String responseIssuer = "response issuer";
	private String subjectName = "some guy";
	
	private DateTime assertionIssueInstant = new DateTime("2010-10-26T09:30:01.000Z");
	private DateTime responseIssueInstant  = new DateTime("2010-10-26T09:30:00.000Z");
	private DateTime authnInstant          = new DateTime("2010-10-26T09:15:00.000Z");
	private DateTime validUntil 		   = new DateTime("2010-10-26T09:45:00.000Z");

	
	private String recepientAssertionConsumerURL;
	private String clientAddress;

	List<String> authorities;
	final String role1 = "ROLE_1"; 
	final String role2 = "ROLE_2"; 
	
	static XMLObjectBuilderFactory builderFactory;

	
	//class under test
	RealAssertionConsumer consumer;

	
	@BeforeClass
	public static void beforeClass() throws Exception{
		
		//need to bootstrap SAML for the validators
		DefaultBootstrap.bootstrap();
		
		builderFactory = Configuration.getBuilderFactory();

	}
	
	@Before
	public void before() throws Exception {

		MockitoAnnotations.initMocks(this);
		consumer = new RealAssertionConsumer();
		
		//assertions = new ArrayList<Assertion>();
		authorities = new ArrayList<String>();
		
		authorities.add(role1);
		authorities.add(role2);

	}
	
	@Test(expected=ServiceProviderAuthenticationException.class)
	public void testConsumeAuthnRequestWithNoStatus() {
		
		SAMLObjectBuilder<Response> responseBuilder = (SAMLObjectBuilder<Response>) builderFactory.getBuilder(Response.DEFAULT_ELEMENT_NAME);	
		Response samlResponse = responseBuilder.buildObject();
		samlResponse.setID(responseID);
		samlResponse.setInResponseTo(inResponseTo);
		samlResponse.setIssueInstant(responseIssueInstant);
		
			
		 consumer.consume(samlResponse);
		
	}
	
	@Test(expected=ServiceProviderAuthenticationException.class)
	public void testConsumeAuthnRequestWithNoStatusCode() {
		
		SAMLObjectBuilder<Response> responseBuilder = (SAMLObjectBuilder<Response>) builderFactory.getBuilder(Response.DEFAULT_ELEMENT_NAME);	
		Response samlResponse = responseBuilder.buildObject();
		samlResponse.setID(responseID);
		samlResponse.setInResponseTo(inResponseTo);
		samlResponse.setIssueInstant(responseIssueInstant);
		
		SAMLObjectBuilder<Status> statusBuilder = (SAMLObjectBuilder<Status>) builderFactory.getBuilder(Status.DEFAULT_ELEMENT_NAME);
		Status status = statusBuilder.buildObject();
		samlResponse.setStatus(status);
	
	
		
		consumer.consume(samlResponse);
		
	}
	
	
	@Test(expected=IdentityProviderAuthenticationException.class)
	public void testConsumeRejectedAuthnRequest() {
		
		SAMLObjectBuilder<Response> responseBuilder = (SAMLObjectBuilder<Response>) builderFactory.getBuilder(Response.DEFAULT_ELEMENT_NAME);	
		Response samlResponse = responseBuilder.buildObject();
		samlResponse.setID(responseID);
		samlResponse.setInResponseTo(inResponseTo);
		samlResponse.setIssueInstant(responseIssueInstant);
		
		SAMLObjectBuilder<Status> statusBuilder = (SAMLObjectBuilder<Status>) builderFactory.getBuilder(Status.DEFAULT_ELEMENT_NAME);
		Status status = statusBuilder.buildObject();
		samlResponse.setStatus(status);
	
		SAMLObjectBuilder<StatusCode> statusCodeBuilder = (SAMLObjectBuilder<StatusCode>) builderFactory.getBuilder(StatusCode.DEFAULT_ELEMENT_NAME);
		StatusCode statusCode = statusCodeBuilder.buildObject();
		statusCode.setValue(StatusCode.RESPONDER_URI);
		status.setStatusCode(statusCode);
			
		consumer.consume(samlResponse);
	}
	
	@Test(expected=IdentityProviderAuthenticationException.class)
	public void testConsumeRejectedAuthnRequestWithSubStatusCode() {
		
		SAMLObjectBuilder<Response> responseBuilder = (SAMLObjectBuilder<Response>) builderFactory.getBuilder(Response.DEFAULT_ELEMENT_NAME);	
		Response samlResponse = responseBuilder.buildObject();
		samlResponse.setID(responseID);
		samlResponse.setInResponseTo(inResponseTo);
		samlResponse.setIssueInstant(responseIssueInstant);
		
		SAMLObjectBuilder<Status> statusBuilder = (SAMLObjectBuilder<Status>) builderFactory.getBuilder(Status.DEFAULT_ELEMENT_NAME);
		Status status = statusBuilder.buildObject();
		samlResponse.setStatus(status);
	
		SAMLObjectBuilder<StatusCode> statusCodeBuilder = (SAMLObjectBuilder<StatusCode>) builderFactory.getBuilder(StatusCode.DEFAULT_ELEMENT_NAME);
		StatusCode statusCode = statusCodeBuilder.buildObject();
		statusCode.setValue(StatusCode.RESPONDER_URI);
		status.setStatusCode(statusCode);
		
		
		StatusCode subStatusCode = statusCodeBuilder.buildObject();
		subStatusCode.setValue(StatusCode.AUTHN_FAILED_URI);
		statusCode.setStatusCode(subStatusCode);
		
		
		consumer.consume(samlResponse);
	}
	
	@Test(expected=IdentityProviderAuthenticationException.class)
	public void testConsumeRejectedAuthnRequestWithSubStatusCodeAndDetails() {
		
		SAMLObjectBuilder<Response> responseBuilder = (SAMLObjectBuilder<Response>) builderFactory.getBuilder(Response.DEFAULT_ELEMENT_NAME);	
		Response samlResponse = responseBuilder.buildObject();
		samlResponse.setID(responseID);
		samlResponse.setInResponseTo(inResponseTo);
		samlResponse.setIssueInstant(responseIssueInstant);
		
		SAMLObjectBuilder<Status> statusBuilder = (SAMLObjectBuilder<Status>) builderFactory.getBuilder(Status.DEFAULT_ELEMENT_NAME);
		Status status = statusBuilder.buildObject();
		samlResponse.setStatus(status);
	
		SAMLObjectBuilder<StatusCode> statusCodeBuilder = (SAMLObjectBuilder<StatusCode>) builderFactory.getBuilder(StatusCode.DEFAULT_ELEMENT_NAME);
		StatusCode statusCode = statusCodeBuilder.buildObject();
		statusCode.setValue(StatusCode.RESPONDER_URI);
		status.setStatusCode(statusCode);
		
		
		StatusCode subStatusCode = statusCodeBuilder.buildObject();
		subStatusCode.setValue(StatusCode.AUTHN_FAILED_URI);
		statusCode.setStatusCode(subStatusCode);
		
		SAMLObjectBuilder<StatusMessage> statusMessageBuilder = (SAMLObjectBuilder<StatusMessage>) builderFactory.getBuilder(StatusMessage.DEFAULT_ELEMENT_NAME);
		StatusMessage message = statusMessageBuilder.buildObject();
		message.setMessage("some failure message");
		status.setStatusMessage(message);
		
		consumer.consume(samlResponse);
	}
	
	private Response getSuccessfulResponse() {
		SAMLObjectBuilder<Response> responseBuilder = (SAMLObjectBuilder<Response>) builderFactory.getBuilder(Response.DEFAULT_ELEMENT_NAME);	
		Response samlResponse = responseBuilder.buildObject();
		samlResponse.setID(responseID);
		samlResponse.setInResponseTo(inResponseTo);
		samlResponse.setIssueInstant(responseIssueInstant);
		samlResponse.setIssuer(generateIssuer(responseIssuer));
		
		SAMLObjectBuilder<Status> statusBuilder = (SAMLObjectBuilder<Status>) builderFactory.getBuilder(Status.DEFAULT_ELEMENT_NAME);
		Status status = statusBuilder.buildObject();
		samlResponse.setStatus(status);
	
		SAMLObjectBuilder<StatusCode> statusCodeBuilder = (SAMLObjectBuilder<StatusCode>) builderFactory.getBuilder(StatusCode.DEFAULT_ELEMENT_NAME);
		StatusCode statusCode = statusCodeBuilder.buildObject();
		statusCode.setValue(StatusCode.SUCCESS_URI);
		status.setStatusCode(statusCode);
		
		return samlResponse;
	}
	
	
	@Test(expected=ServiceProviderAuthenticationException.class)
	public void testConsumerAuthnResponseWithEmptyAssertions() {
		
		Response samlResponse = getSuccessfulResponse();
		
		consumer.consume(samlResponse);
	}
	
	@Test(expected=ServiceProviderAuthenticationException.class)
	public void testConsumerAuthnResponseWithAssertionWithNoID() {
		
		Response samlResponse = getSuccessfulResponse();
		SAMLObjectBuilder<Assertion> assertionBuilder = (SAMLObjectBuilder<Assertion>) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
		Assertion assertion = assertionBuilder.buildObject();
		
		assertion.setIssuer(generateIssuer(assertionIssuer));
		assertion.getAuthnStatements().add(generateAuthnStatement(authnInstant));
		assertion.setSubject(generateSubject(subjectName));
		assertion.setIssueInstant(assertionIssueInstant);
		assertion.getAttributeStatements().add( generateAttributeStatement(GrantedAuthority.class.getName(), authorities));
		//assertion.setID(assertionID);

		samlResponse.getAssertions().add(assertion);

		consumer.consume(samlResponse);
	}


	
	@Test(expected=ServiceProviderAuthenticationException.class)
	public void testConsumerAuthnResponseWithAssertionWithNoIssuer() {
		
		Response samlResponse = getSuccessfulResponse();
		SAMLObjectBuilder<Assertion> assertionBuilder = (SAMLObjectBuilder<Assertion>) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
		Assertion assertion = assertionBuilder.buildObject();
		
		//assertion.setIssuer(generateIssuer(assertionIssuer));
		assertion.getAuthnStatements().add(generateAuthnStatement(authnInstant));
		assertion.setSubject(generateSubject(subjectName));
		assertion.setIssueInstant(assertionIssueInstant);
		assertion.getAttributeStatements().add( generateAttributeStatement(GrantedAuthority.class.getName(), authorities));
		assertion.setID(assertionID);
		
		
		samlResponse.getAssertions().add(assertion);
		
		
		
		consumer.consume(samlResponse);
	}
	
	@Test(expected=ServiceProviderAuthenticationException.class)
	public void testConsumerNoAuthnStatements() throws Exception {
		Response samlResponse = getSuccessfulResponse();
		SAMLObjectBuilder<Assertion> assertionBuilder = (SAMLObjectBuilder<Assertion>) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
		Assertion assertion = assertionBuilder.buildObject();
		
		assertion.setIssuer(generateIssuer(assertionIssuer));
		//assertion.getAuthnStatements().add(generateAuthnStatement(authnInstant));
		assertion.setSubject(generateSubject(subjectName));
		assertion.setIssueInstant(assertionIssueInstant);
		assertion.getAttributeStatements().add( generateAttributeStatement(GrantedAuthority.class.getName(), authorities));
		assertion.setID(assertionID);
		
		samlResponse.getAssertions().add(assertion);
		
		consumer.consume(samlResponse);
	}


	
	@Test(expected=ServiceProviderAuthenticationException.class)
	public void testConsumeNoSubject() throws Exception {
		Response samlResponse = getSuccessfulResponse();
		SAMLObjectBuilder<Assertion> assertionBuilder = (SAMLObjectBuilder<Assertion>) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
		Assertion assertion = assertionBuilder.buildObject();
		
		assertion.setIssuer(generateIssuer(assertionIssuer));
		assertion.getAuthnStatements().add(generateAuthnStatement(authnInstant));
		//assertion.setSubject(generateSubject(subjectName));
		assertion.setIssueInstant(assertionIssueInstant);
		assertion.getAttributeStatements().add( generateAttributeStatement(GrantedAuthority.class.getName(), authorities));
		assertion.setID(assertionID);
		
		
		samlResponse.getAssertions().add(assertion);
		
		consumer.consume(samlResponse);
	}
	
	@Test(expected=ServiceProviderAuthenticationException.class)
	public void testConsumerNoIssuerInstant() throws Exception {
		Response samlResponse = getSuccessfulResponse();
		SAMLObjectBuilder<Assertion> assertionBuilder = (SAMLObjectBuilder<Assertion>) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
		Assertion assertion = assertionBuilder.buildObject();
		
		assertion.setIssuer(generateIssuer(assertionIssuer));
		assertion.getAuthnStatements().add(generateAuthnStatement(authnInstant));
		assertion.setSubject(generateSubject(subjectName));
		//assertion.setIssueInstant(assertionIssueInstant);
		assertion.getAttributeStatements().add( generateAttributeStatement(GrantedAuthority.class.getName(), authorities));
		assertion.setID(assertionID);
		
		
		samlResponse.getAssertions().add(assertion);
		
		
		consumer.consume(samlResponse);
	}
	
	@Test(expected=ServiceProviderAuthenticationException.class)
	public void testConsumeNoAttributeStatements() throws Exception {
		
		Response samlResponse = getSuccessfulResponse();
		SAMLObjectBuilder<Assertion> assertionBuilder = (SAMLObjectBuilder<Assertion>) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
		Assertion assertion = assertionBuilder.buildObject();
		
		assertion.setIssuer(generateIssuer(assertionIssuer));
		assertion.getAuthnStatements().add(generateAuthnStatement(authnInstant));
		assertion.setSubject(generateSubject(subjectName));
		assertion.setIssueInstant(assertionIssueInstant);
		//assertion.getAttributeStatements().add( generateAttributeStatement(GrantedAuthority.class.getName(), authorities));
		assertion.setID(assertionID);
		
		samlResponse.getAssertions().add(assertion);
		
		consumer.consume(samlResponse);
	}
	
	@Test(expected=ServiceProviderAuthenticationException.class)
	public void testConsumeUnknownAttributeStatements() throws Exception {
		Response samlResponse = getSuccessfulResponse();
		SAMLObjectBuilder<Assertion> assertionBuilder = (SAMLObjectBuilder<Assertion>) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
		Assertion assertion = assertionBuilder.buildObject();
		
		assertion.setIssuer(generateIssuer(assertionIssuer));
		assertion.getAuthnStatements().add(generateAuthnStatement(authnInstant));
		assertion.setSubject(generateSubject(subjectName));
		assertion.setIssueInstant(assertionIssueInstant);
		assertion.getAttributeStatements().add( generateAttributeStatement("foo", authorities));
		assertion.setID(assertionID);
		
		samlResponse.getAssertions().add(assertion);
		
		consumer.consume(samlResponse);
	}
	
	@Test
	public void testConsume() throws Exception {
		
		Response samlResponse = getSuccessfulResponse();
		SAMLObjectBuilder<Assertion> assertionBuilder = (SAMLObjectBuilder<Assertion>) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
		Assertion assertion = assertionBuilder.buildObject();
		
		assertion.setIssuer(generateIssuer(assertionIssuer));
		assertion.getAuthnStatements().add(generateAuthnStatement(authnInstant));
		assertion.setSubject(generateSubject(subjectName));
		assertion.setIssueInstant(assertionIssueInstant);
		assertion.getAttributeStatements().add( generateAttributeStatement(GrantedAuthority.class.getName(), authorities));
		assertion.setID(assertionID);
		
		samlResponse.getAssertions().add(assertion);
		
		User user = consumer.consume(samlResponse);
		
		assertEquals(subjectName, user.getName());
		assertEquals(responseID,user.getAuthenticationResponseID());
		assertEquals(assertionID,user.getAuthenticationAssertionID());
		assertEquals(assertionIssuer,user.getAuthenticationAssertionIssuingEntityName());
		assertEquals(responseIssuer,user.getAuthenticationResponseIssuingEntityName());
		assertEquals(responseIssueInstant.getMillis(), user.getAuthenticationResponseIssueInstant().getMillis());
		assertEquals(assertionIssueInstant.getMillis(), user.getAuthenticationAssertionIssueInstant().getMillis());
		assertEquals(authnInstant.getMillis(), user.getAuthenticationIssueInstant().getMillis());
		
		
	}
	
	private  AuthnStatement generateAuthnStatement(DateTime authnInstant) {
		

		//Response/Assertion/AuthnStatement/AuthContext/AuthContextClassRef
		AuthnContextClassRefBuilder authnContextClassRefBuilder = (AuthnContextClassRefBuilder) builderFactory.getBuilder(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
		AuthnContextClassRef authnContextClassRef = authnContextClassRefBuilder.buildObject();
		//urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport
		authnContextClassRef.setAuthnContextClassRef(AuthnContext.PASSWORD_AUTHN_CTX);

		//Response/Assertion/AuthnStatement/AuthContext
		AuthnContextBuilder authnContextBuilder = (AuthnContextBuilder)builderFactory.getBuilder(AuthnContext.DEFAULT_ELEMENT_NAME);
		AuthnContext authnContext = authnContextBuilder.buildObject();
		authnContext.setAuthnContextClassRef(authnContextClassRef);

		//Response/Assertion/AuthnStatement
		AuthnStatementBuilder authnStatementBuilder = (AuthnStatementBuilder) builderFactory.getBuilder(AuthnStatement.DEFAULT_ELEMENT_NAME);
		AuthnStatement authnStatement = authnStatementBuilder.buildObject();
		authnStatement.setAuthnContext(authnContext);
		
		authnStatement.setAuthnInstant(authnInstant);
		
		return authnStatement;

	}
	
	private Issuer generateIssuer(String issuerValue) {
		SAMLObjectBuilder<Issuer> issuerBuilder = (SAMLObjectBuilder<Issuer>) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
		Issuer issuer = issuerBuilder.buildObject();
		issuer.setValue(issuerValue);
		return issuer;
	}

	
	private  Subject generateSubject( String subjectName) {
		
		//Response/Assertion/Subject/NameID
		NameIDBuilder nameIDBuilder = (NameIDBuilder) builderFactory.getBuilder(NameID.DEFAULT_ELEMENT_NAME);
		NameID nameID = nameIDBuilder.buildObject();
		nameID.setValue(subjectName);
		nameID.setFormat(NameIDType.UNSPECIFIED);

		//Response/Assertion/Subject
		SubjectBuilder subjectBuilder =  (SubjectBuilder)builderFactory.getBuilder(Subject.DEFAULT_ELEMENT_NAME);
		Subject subject = subjectBuilder.buildObject();
		
		subject.setNameID(nameID);
		
		SubjectConfirmationBuilder subjectConfirmationBuilder = (SubjectConfirmationBuilder)builderFactory.getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
		SubjectConfirmation subjectConfirmation = subjectConfirmationBuilder.buildObject();
		subjectConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);
		
		SubjectConfirmationDataBuilder subjectConfirmationDataBuilder = (SubjectConfirmationDataBuilder)builderFactory.getBuilder(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
		SubjectConfirmationData subjectConfirmationData = subjectConfirmationDataBuilder.buildObject();
		
		subjectConfirmationData.setRecipient(recepientAssertionConsumerURL);
		subjectConfirmationData.setInResponseTo(inResponseTo);
		subjectConfirmationData.setNotOnOrAfter(validUntil);
		subjectConfirmationData.setAddress(clientAddress);
		
		subjectConfirmation.setSubjectConfirmationData(subjectConfirmationData);
		
		subject.getSubjectConfirmations().add(subjectConfirmation);
		
		return subject;
	}

	private AttributeStatement generateAttributeStatement(String attrName, List<String> values) {
		
		AttributeStatementBuilder attributeStatementBuilder = (AttributeStatementBuilder) builderFactory.getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
		AttributeStatement attributeStatement = attributeStatementBuilder.buildObject();

		//Response/Assertion/AttributeStatement/Attribute
		AttributeBuilder attributeBuilder = (AttributeBuilder)  builderFactory.getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
		Attribute attribute = attributeBuilder.buildObject();
		attribute.setName(attrName);
		
		//urn:oasis:names:tc:SAML:2.0:attrname-format:basic
		attribute.setNameFormat(Attribute.BASIC);
		
		
		for (String value : values) {
			//Response/Assertion/AttributeStatement/Attribute/AttributeValue
			XSStringBuilder stringBuilder = (XSStringBuilder) Configuration.getBuilderFactory().getBuilder(XSString.TYPE_NAME);
			XSString stringValue = stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
			stringValue.setValue(value);
			attribute.getAttributeValues().add(stringValue);
			
		}
		
		attributeStatement.getAttributes().add(attribute);
		
		return attributeStatement;
	}
	
}
