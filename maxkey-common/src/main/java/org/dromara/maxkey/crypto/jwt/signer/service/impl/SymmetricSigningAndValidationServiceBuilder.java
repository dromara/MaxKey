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
 

/**
 * 
 */
package org.dromara.maxkey.crypto.jwt.signer.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import org.dromara.maxkey.crypto.jwt.signer.service.JwtSigningAndValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;

/**
 * @author Crystal.Sea
 * Builder Symmetric Signing Service
 */
public class SymmetricSigningAndValidationServiceBuilder {
	static final  Logger _logger = LoggerFactory.getLogger(SymmetricSigningAndValidationServiceBuilder.class);
	public static final String SYMMETRIC_KEY = "SYMMETRIC-KEY";
	/**
	 * 
	 */
	public SymmetricSigningAndValidationServiceBuilder() {
		
	}
	
	public JwtSigningAndValidationService serviceBuilder(String sharedSecret){
		_logger.debug("shared Secret : {}" , sharedSecret);
		_logger.debug("Symmetric Id : {}" , SYMMETRIC_KEY);
		if (sharedSecret == null) {
			_logger.error("Couldn't create symmetric SigningAndValidation");
			return null;
		}

		/**
		 * Builder Symmetric Signing And Validation Service
		 */
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
