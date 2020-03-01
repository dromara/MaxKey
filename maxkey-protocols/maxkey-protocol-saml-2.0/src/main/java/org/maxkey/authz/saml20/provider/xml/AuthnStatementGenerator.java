
package org.maxkey.authz.saml20.provider.xml;

import org.joda.time.DateTime;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.impl.AuthnContextBuilder;
import org.opensaml.saml2.core.impl.AuthnContextClassRefBuilder;
import org.opensaml.saml2.core.impl.AuthnStatementBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthnStatementGenerator {
	private final static Logger logger = LoggerFactory.getLogger(AuthnStatementGenerator.class);
	
	public AuthnStatement generateAuthnStatement(DateTime authnInstant) {
		//Response/Assertion/AuthnStatement/AuthContext/AuthContextClassRef
		AuthnContextClassRef authnContextClassRef = new AuthnContextClassRefBuilder().buildObject();
		//urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport
		authnContextClassRef.setAuthnContextClassRef(AuthnContext.PPT_AUTHN_CTX);

		//Response/Assertion/AuthnStatement/AuthContext
		AuthnContext authnContext = new AuthnContextBuilder().buildObject();
		authnContext.setAuthnContextClassRef(authnContextClassRef);

		//Response/Assertion/AuthnStatement
		AuthnStatement authnStatement = new AuthnStatementBuilder().buildObject();
		authnStatement.setAuthnContext(authnContext);
		authnStatement.setAuthnInstant(authnInstant);
		logger.debug("generateAuthnStatement authnInstant "+authnInstant);
		return authnStatement;

	}
}
