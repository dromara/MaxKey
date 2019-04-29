
package org.maxkey.authz.saml20.xml;

import org.opensaml.Configuration;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;

public class IssuerGenerator {

	private final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
	
	private final String issuerName;
	
	public IssuerGenerator(String issuerName) {
		this.issuerName = issuerName;
	}

	public Issuer generateIssuer() {
		///Issuer
		IssuerBuilder issuerBuilder = (IssuerBuilder) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
		Issuer issuer = issuerBuilder.buildObject();
	
		issuer.setValue(issuerName);
		issuer.setFormat(NameIDType.ENTITY);
		
		return issuer;
	
	}
	
}
