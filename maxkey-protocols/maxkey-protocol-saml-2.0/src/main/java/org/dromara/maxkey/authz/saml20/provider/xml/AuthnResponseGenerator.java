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
 


package org.dromara.maxkey.authz.saml20.provider.xml;

import java.util.HashMap;

import org.dromara.maxkey.authz.saml.common.AuthnRequestInfo;
import org.dromara.maxkey.authz.saml.service.IDService;
import org.dromara.maxkey.authz.saml.service.TimeService;
import org.dromara.maxkey.authz.saml20.binding.BindingAdapter;
import org.dromara.maxkey.authz.saml20.xml.IssuerGenerator;
import org.dromara.maxkey.constants.ConstsBoolean;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.impl.ResponseBuilder;
import org.opensaml.saml2.encryption.Encrypter;
import org.opensaml.saml2.encryption.Encrypter.KeyPlacement;
import org.opensaml.xml.encryption.EncryptionConstants;
import org.opensaml.xml.encryption.EncryptionParameters;
import org.opensaml.xml.encryption.KeyEncryptionParameters;
import org.opensaml.xml.security.keyinfo.KeyInfoGeneratorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthnResponseGenerator {
	private static final  Logger logger = LoggerFactory.getLogger(AuthnResponseGenerator.class);
	private  String issuerName;
	private  IDService idService;
	private  TimeService timeService;
	private  AssertionGenerator assertionGenerator;
	private  IssuerGenerator issuerGenerator;
	private  StatusGenerator statusGenerator;

	public AuthnResponseGenerator(String issuerName, TimeService timeService, IDService idService) {
		this.issuerName = issuerName;
		this.idService = idService;
		this.timeService = timeService;
		issuerGenerator = new IssuerGenerator(this.issuerName);
		assertionGenerator = new AssertionGenerator(issuerName, timeService, idService);
		statusGenerator = new StatusGenerator();
	}


	public Response generateAuthnResponse(  AppsSAML20Details saml20Details,
											AuthnRequestInfo authnRequestInfo,
											HashMap<String,String>attributeMap, 
											BindingAdapter bindingAdapter,
											UserInfo currentUser){
		
		Response authResponse = new ResponseBuilder().buildObject();
		//builder Assertion
		Assertion assertion = assertionGenerator.generateAssertion( 
											saml20Details,
											bindingAdapter,
											saml20Details.getSpAcsUrl(),
											authnRequestInfo.getAuthnRequestID(),
											saml20Details.getAudience(),
											Integer.parseInt(saml20Details.getValidityInterval()), 
											attributeMap,
											currentUser);
		
		//Encrypt 
		if(ConstsBoolean.isYes(saml20Details.getEncrypted())) {
			logger.info("begin to encrypt assertion");
			try {
				// Assume this contains a recipient's RSA public
				EncryptionParameters encryptionParameters = new EncryptionParameters();
				encryptionParameters.setAlgorithm(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128);
				logger.info("encryption assertion Algorithm : "+EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128);
				KeyEncryptionParameters keyEncryptionParameters = new KeyEncryptionParameters();
				keyEncryptionParameters.setEncryptionCredential(bindingAdapter.getSpSigningCredential());
				// kekParams.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSAOAEP);
				keyEncryptionParameters.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSA15);
				logger.info("keyEncryption  Algorithm : "+EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSA15);
				KeyInfoGeneratorFactory keyInfoGeneratorFactory = Configuration
														.getGlobalSecurityConfiguration()
														.getKeyInfoGeneratorManager().getDefaultManager()
														.getFactory(bindingAdapter.getSpSigningCredential());
				keyEncryptionParameters.setKeyInfoGenerator(keyInfoGeneratorFactory.newInstance());
				Encrypter encrypter = new Encrypter(encryptionParameters, keyEncryptionParameters);
				encrypter.setKeyPlacement(KeyPlacement.PEER);
				EncryptedAssertion encryptedAssertion = encrypter.encrypt(assertion);
				authResponse.getEncryptedAssertions().add(encryptedAssertion);
			}catch(Exception e) {
				logger.info("Unable to encrypt assertion .",e);
			}
		}else { 
			authResponse.getAssertions().add(assertion);
		}
		
		authResponse.setIssuer(issuerGenerator.generateIssuer());
		authResponse.setID(idService.generateID());
		authResponse.setIssueInstant(timeService.getCurrentDateTime());
		authResponse.setInResponseTo(authnRequestInfo.getAuthnRequestID());
		authResponse.setDestination(saml20Details.getSpAcsUrl());
		authResponse.setStatus(statusGenerator.generateStatus(StatusCode.SUCCESS_URI));
		logger.debug("authResponse.isSigned "+authResponse.isSigned());
		return authResponse;
	}
	
	
}
