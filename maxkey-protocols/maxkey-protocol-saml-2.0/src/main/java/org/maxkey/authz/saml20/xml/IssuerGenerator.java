
package org.maxkey.authz.saml20.xml;

import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.impl.IssuerBuilder;

public class IssuerGenerator {
	
	private final String issuerName;
	
	public IssuerGenerator(String issuerName) {
		this.issuerName = issuerName;
	}

	public Issuer generateIssuer() {
		///Issuer
		Issuer issuer = new IssuerBuilder().buildObject();
	
		issuer.setValue(issuerName);
		issuer.setFormat(NameIDType.ENTITY);
		
		return issuer;
	}
	
}
