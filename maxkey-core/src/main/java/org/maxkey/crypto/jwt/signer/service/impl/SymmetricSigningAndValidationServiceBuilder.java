/**
 * 
 */
package org.maxkey.crypto.jwt.signer.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.maxkey.crypto.jwt.signer.service.JwtSigningAndValidationService;

import com.google.common.collect.ImmutableMap;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.util.Base64URL;

/**
 * @author Crystal.Sea
 * Builder Symmetric Signing Service
 */
public class SymmetricSigningAndValidationServiceBuilder {
	final static Logger logger = Logger.getLogger(SymmetricSigningAndValidationServiceBuilder.class);
	public static final String SYMMETRIC_KEY = "SYMMETRIC-KEY";
	/**
	 * 
	 */
	public SymmetricSigningAndValidationServiceBuilder() {
		
	}
	
	public JwtSigningAndValidationService serviceBuilder(String sharedSecret){
		logger.debug("shared Secret : "+sharedSecret);
		logger.debug("Symmetric Id : "+SYMMETRIC_KEY);
		if (sharedSecret == null) {
			logger.error("Couldn't create symmetric SigningAndValidation");
			return null;
		}

		/**
		 * Builder Symmetric Signing And Validation Service
		 */
		//TODO:
		JWK jwk = null;
		//JWK jwk = new OctetSequenceKey(Base64URL.encode(sharedSecret), KeyUse.SIGNATURE, null, null, SYMMETRIC_KEY, null, null, null);
		Map<String, JWK> keys = ImmutableMap.of(SYMMETRIC_KEY, jwk);
		try {
			JwtSigningAndValidationService  symmetricSigningAndValidationService = new DefaultJwtSigningAndValidationService(keys);
			return symmetricSigningAndValidationService;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (JOSEException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
