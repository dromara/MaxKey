/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.dromara.maxkey.authz.saml20.xml;

import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.ValidatorSuite;

public class SAML2ValidatorSuite  {

	public void validate(XMLObject xmlObject) throws ValidationException {
	
		ValidatorSuite schemaValidator = Configuration.getValidatorSuite("saml2-core-schema-validator");
		schemaValidator.validate(xmlObject);
		ValidatorSuite specValidator = Configuration.getValidatorSuite("saml2-core-spec-validator");
		specValidator.validate(xmlObject);

	}
	
	
}
