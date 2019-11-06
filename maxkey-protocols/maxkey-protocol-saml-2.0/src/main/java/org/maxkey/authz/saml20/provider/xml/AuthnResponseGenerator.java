
package org.maxkey.authz.saml20.provider.xml;


import java.util.Collection;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.maxkey.authz.saml.common.AuthnRequestInfo;
import org.maxkey.authz.saml.service.IDService;
import org.maxkey.authz.saml.service.TimeService;
import org.maxkey.authz.saml20.xml.IssuerGenerator;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.domain.apps.AppsSAML20Details;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.impl.ResponseBuilder;
import org.opensaml.saml2.encryption.Encrypter;
import org.opensaml.saml2.encryption.Encrypter.KeyPlacement;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.encryption.EncryptionConstants;
import org.opensaml.xml.encryption.EncryptionException;
import org.opensaml.xml.encryption.EncryptionParameters;
import org.opensaml.xml.encryption.KeyEncryptionParameters;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.keyinfo.KeyInfoGeneratorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

public class AuthnResponseGenerator {
	private final static Logger logger = LoggerFactory.getLogger(AuthnResponseGenerator.class);
	private final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

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
		issuerGenerator = new IssuerGenerator(issuerName);
		assertionGenerator = new AssertionGenerator(issuerName, timeService, idService);
		statusGenerator = new StatusGenerator();
		
	}


	public Response generateAuthnResponse(  AppsSAML20Details saml20Details,
											AuthnRequestInfo authnRequestInfo,
											String nameIdValue,
											String clientAddress,
											DateTime authnInstant,
											Collection<GrantedAuthority> authorities, 
											HashMap<String,String>attributeMap, 
											Credential signingCredential,
											Credential spSigningCredential){
		
		ResponseBuilder responseBuilder = (ResponseBuilder) builderFactory.getBuilder(Response.DEFAULT_ELEMENT_NAME);
		Response authResponse = responseBuilder.buildObject();
		
		int validInSeconds=Integer.parseInt(saml20Details.getValidityInterval());
		String audienceUrl=saml20Details.getAudience();
		String assertionConsumerURL=saml20Details.getSpAcsUrl();
		String inResponseTo=authnRequestInfo.getAuthnRequestID();
		
		Issuer responseIssuer = issuerGenerator.generateIssuer();
		
		Assertion assertion = assertionGenerator.generateAssertion( 
											assertionConsumerURL,
											nameIdValue,
											inResponseTo,
											audienceUrl,
											validInSeconds, 
											authorities,
											attributeMap, 
											clientAddress, 
											authnInstant);
		
		try{
			if(BOOLEAN.isTrue(saml20Details.getEncrypted())) {
				// Assume this contains a recipient's RSA public
				logger.info("begin to encrypt assertion");
				EncryptionParameters encryptionParameters = new EncryptionParameters();
				encryptionParameters.setAlgorithm(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128);
				logger.info("encryption assertion Algorithm : "+EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128);
				KeyEncryptionParameters keyEncryptionParameters = new KeyEncryptionParameters();
				keyEncryptionParameters.setEncryptionCredential(spSigningCredential);
				// kekParams.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSAOAEP);
				keyEncryptionParameters.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSA15);
				logger.info("keyEncryption  Algorithm : "+EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSA15);
				KeyInfoGeneratorFactory keyInfoGeneratorFactory = Configuration
														.getGlobalSecurityConfiguration()
														.getKeyInfoGeneratorManager().getDefaultManager()
														.getFactory(spSigningCredential);
				keyEncryptionParameters.setKeyInfoGenerator(keyInfoGeneratorFactory.newInstance());
				Encrypter encrypter = new Encrypter(encryptionParameters, keyEncryptionParameters);
				encrypter.setKeyPlacement(KeyPlacement.PEER);
				EncryptedAssertion encryptedAssertion = encrypter.encrypt(assertion);
				authResponse.getEncryptedAssertions().add(encryptedAssertion);
			} else {
				authResponse.getAssertions().add(assertion);
			}
		}
		catch (EncryptionException e) {
			logger.info("Unable to encrypt assertion .");
			e.printStackTrace();
		}
	
		authResponse.setIssuer(responseIssuer);
		authResponse.setID(idService.generateID());
		authResponse.setIssueInstant(timeService.getCurrentDateTime());
		authResponse.setInResponseTo(inResponseTo);
		authResponse.getAssertions().add(assertion);
		authResponse.setDestination(assertionConsumerURL);
		authResponse.setStatus(statusGenerator.generateStatus(StatusCode.SUCCESS_URI));
		return authResponse;
	}
	
	
}