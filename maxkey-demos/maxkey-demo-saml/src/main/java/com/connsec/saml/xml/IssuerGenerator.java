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

package com.connsec.saml.xml;

import org.opensaml.Configuration;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;

public class IssuerGenerator {

	private final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
	
	private final String issuingEntityName;
	
	
	public IssuerGenerator(String issuingEntityName) {
		super();
		this.issuingEntityName = issuingEntityName;
	}


	public Issuer generateIssuer() {
	
		///Issuer
		IssuerBuilder issuerBuilder = (IssuerBuilder) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
		Issuer issuer = issuerBuilder.buildObject();
		

		issuer.setValue(issuingEntityName);
		issuer.setFormat(NameIDType.ENTITY);
		
		return issuer;
	
	}
	
}
