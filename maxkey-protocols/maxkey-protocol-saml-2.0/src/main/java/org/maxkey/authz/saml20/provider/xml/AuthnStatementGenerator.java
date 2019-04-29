
package org.maxkey.authz.saml20.provider.xml;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.impl.AuthnContextBuilder;
import org.opensaml.saml2.core.impl.AuthnContextClassRefBuilder;
import org.opensaml.saml2.core.impl.AuthnStatementBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;

public class AuthnStatementGenerator {

	
	private final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

	public AuthnStatement generateAuthnStatement(DateTime authnInstant) {
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
}
