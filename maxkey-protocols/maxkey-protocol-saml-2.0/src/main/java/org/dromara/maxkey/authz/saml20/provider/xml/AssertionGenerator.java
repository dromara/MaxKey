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

import java.util.ArrayList;
import java.util.HashMap;

import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authz.saml.service.IDService;
import org.dromara.maxkey.authz.saml.service.TimeService;
import org.dromara.maxkey.authz.saml20.binding.BindingAdapter;
import org.dromara.maxkey.authz.saml20.xml.IssuerGenerator;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.web.WebContext;
import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.impl.AssertionBuilder;
import org.opensaml.xml.security.BasicSecurityConfiguration;
import org.opensaml.xml.security.credential.BasicCredential;
import org.opensaml.xml.security.keyinfo.KeyInfoGeneratorFactory;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureConstants;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.signature.impl.SignatureBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AssertionGenerator {
	private static final  Logger logger = LoggerFactory.getLogger(AssertionGenerator.class);

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
							AppsSAML20Details saml20Details,
							BindingAdapter bindingAdapter,
							String assertionConsumerURL, 
							String inResponseTo, 
							String audienceUrl,
							int validInSeconds,
							HashMap<String,String>attributeMap,
							UserInfo userInfo
							) {

		Assertion assertion = new AssertionBuilder().buildObject();;
		//Subject
		Subject subject = subjectGenerator.generateSubject(
		                saml20Details,
						assertionConsumerURL,
						inResponseTo,
						validInSeconds,
						userInfo);
		assertion.setSubject(subject);
		//issuer
		Issuer issuer = issuerGenerator.generateIssuer();
		assertion.setIssuer(issuer);
		//AuthnStatements
		DateTime authnInstant = new DateTime(WebContext.getSession().getCreationTime());
		AuthnStatement authnStatement = authnStatementGenerator.generateAuthnStatement(authnInstant);
		assertion.getAuthnStatements().add(authnStatement);
		//AttributeStatements
		ArrayList<GrantedAuthority> grantedAuthoritys = new ArrayList<GrantedAuthority>();
		grantedAuthoritys.add(new SimpleGrantedAuthority("ROLE_USER"));
		for(GrantedAuthority anthGrantedAuthority:  ((UsernamePasswordAuthenticationToken)AuthorizationUtils.getAuthentication()).getAuthorities()){
			grantedAuthoritys.add(anthGrantedAuthority);
		}
		AttributeStatement attributeStatement =
				attributeStatementGenerator.generateAttributeStatement(
									saml20Details, 
									grantedAuthoritys,
									attributeMap,
									userInfo);
		assertion.getAttributeStatements().add(attributeStatement);
		//ID
		assertion.setID(idService.generateID());
		//IssueInstant
		assertion.setIssueInstant(timeService.getCurrentDateTime());
		//Conditions
		Conditions conditions = conditionsGenerator.generateConditions(audienceUrl,validInSeconds);
		assertion.setConditions(conditions);
		//sign Assertion
		try{
		    if(bindingAdapter.getSigningCredential() == null) {
		       throw new Exception("Signing Credential is null..." );
		    }
		    logger.debug("EntityId " + bindingAdapter.getSigningCredential().getEntityId());
	        BasicCredential basicCredential = new BasicCredential();
	        basicCredential.setPrivateKey(bindingAdapter.getSigningCredential().getPrivateKey());
	        
	        Signature signature = new SignatureBuilder().buildObject();
	        signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
	        
	        
	        signature.setSigningCredential(basicCredential);
	        KeyInfoGeneratorFactory keyInfoGeneratorFactory = Configuration
					.getGlobalSecurityConfiguration()
					.getKeyInfoGeneratorManager().getDefaultManager()
					.getFactory(bindingAdapter.getSigningCredential());
	        
	        signature.setKeyInfo(keyInfoGeneratorFactory.newInstance().generate(bindingAdapter.getSigningCredential()));
	        BasicSecurityConfiguration config = (BasicSecurityConfiguration) Configuration.getGlobalSecurityConfiguration();
	        
	        if(saml20Details.getSignature().equalsIgnoreCase("RSAwithSHA1"))  {  
    	        signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
    	        config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
	        }else if(saml20Details.getSignature().equalsIgnoreCase("RSAwithSHA256"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
            }else if(saml20Details.getSignature().equalsIgnoreCase("RSAwithSHA384"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA384);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA384);
            }else if(saml20Details.getSignature().equalsIgnoreCase("RSAwithSHA512"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA512);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA512);
            }else if(saml20Details.getSignature().equalsIgnoreCase("RSAwithMD5"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_NOT_RECOMMENDED_RSA_MD5);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_SIGNATURE_NOT_RECOMMENDED_RSA_MD5);
            }else if(saml20Details.getSignature().equalsIgnoreCase("RSAwithRIPEMD160"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_RIPEMD160);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_SIGNATURE_RSA_RIPEMD160);
            }else if(saml20Details.getSignature().equalsIgnoreCase("DSAwithSHA1"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_ECDSA_SHA1);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_SIGNATURE_ECDSA_SHA1);
            }else if(saml20Details.getSignature().equalsIgnoreCase("ECDSAwithSHA256"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_ECDSA_SHA256);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_SIGNATURE_ECDSA_SHA256);
            }else if(saml20Details.getSignature().equalsIgnoreCase("ECDSAwithSHA384"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_ECDSA_SHA384);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_SIGNATURE_ECDSA_SHA384);
            }else if(saml20Details.getSignature().equalsIgnoreCase("ECDSAwithSHA512"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_ECDSA_SHA512);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_SIGNATURE_ECDSA_SHA512);
            }else if(saml20Details.getSignature().equalsIgnoreCase("HMAC-MD5"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_MAC_HMAC_NOT_RECOMMENDED_MD5);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_MAC_HMAC_NOT_RECOMMENDED_MD5);
            }else if(saml20Details.getSignature().equalsIgnoreCase("HMAC-SHA1"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_MAC_HMAC_SHA1);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
            }else if(saml20Details.getSignature().equalsIgnoreCase("HMAC-SHA256"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_MAC_HMAC_SHA256);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_MAC_HMAC_SHA256);
            }else if(saml20Details.getSignature().equalsIgnoreCase("HMAC-SHA384"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_MAC_HMAC_SHA384);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_MAC_HMAC_SHA384);
            }else if(saml20Details.getSignature().equalsIgnoreCase("HMAC-SHA512"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_MAC_HMAC_SHA512);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_MAC_HMAC_SHA512);
            }else if(saml20Details.getSignature().equalsIgnoreCase("HMAC-RIPEMD160"))  {  
                signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_MAC_HMAC_RIPEMD160);
                config.registerSignatureAlgorithmURI(saml20Details.getSignature(), SignatureConstants.ALGO_ID_MAC_HMAC_RIPEMD160);
            }
	        
            if(saml20Details.getDigestMethod().equalsIgnoreCase("MD5"))  {  
                config.setSignatureReferenceDigestMethod(SignatureConstants.ALGO_ID_DIGEST_NOT_RECOMMENDED_MD5);
            }else if(saml20Details.getDigestMethod().equalsIgnoreCase("SHA1"))  {  
                config.setSignatureReferenceDigestMethod(SignatureConstants.ALGO_ID_DIGEST_SHA1);
            }else if(saml20Details.getDigestMethod().equalsIgnoreCase("SHA256"))  {  
                config.setSignatureReferenceDigestMethod(SignatureConstants.ALGO_ID_DIGEST_SHA256);
            }else if(saml20Details.getDigestMethod().equalsIgnoreCase("SHA384"))  {  
                config.setSignatureReferenceDigestMethod(SignatureConstants.ALGO_ID_DIGEST_SHA384);
            }else if(saml20Details.getDigestMethod().equalsIgnoreCase("SHA512"))  {  
                config.setSignatureReferenceDigestMethod(SignatureConstants.ALGO_ID_DIGEST_SHA512);
            }else if(saml20Details.getDigestMethod().equalsIgnoreCase("RIPEMD-160"))  {  
                config.setSignatureReferenceDigestMethod(SignatureConstants.ALGO_ID_DIGEST_RIPEMD160);
            }
            
			assertion.setSignature(signature);

			Configuration.getMarshallerFactory().getMarshaller(assertion).marshall(assertion);
            Signer.signObject(signature);
            
			logger.debug("assertion.isSigned "+assertion.isSigned());
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("Unable to Signer assertion ",e);
		}

		return assertion;
	}
}
