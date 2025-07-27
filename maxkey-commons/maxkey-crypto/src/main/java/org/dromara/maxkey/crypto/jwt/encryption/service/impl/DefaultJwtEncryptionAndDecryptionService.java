/*******************************************************************************
 * Copyright 2014 The MITRE Corporation
 *   and the MIT Kerberos and Internet Trust Consortium
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.dromara.maxkey.crypto.jwt.encryption.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import org.dromara.maxkey.crypto.jose.keystore.JWKSetKeyStore;
import org.dromara.maxkey.crypto.jwt.encryption.service.JwtEncryptionAndDecryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;

import jakarta.annotation.PostConstruct;

/**
 * @author wkim
 *
 */
public class DefaultJwtEncryptionAndDecryptionService implements JwtEncryptionAndDecryptionService {

	private static Logger _logger = LoggerFactory.getLogger(DefaultJwtEncryptionAndDecryptionService.class);

	// map of identifier to encrypter
	private Map<String, JWEEncrypter> encrypters = new HashMap<String, JWEEncrypter>();

	// map of identifier to decrypter
	private Map<String, JWEDecrypter> decrypters = new HashMap<String, JWEDecrypter>();

	private String defaultEncryptionKeyId;

	private String defaultDecryptionKeyId;

	private JWEAlgorithm defaultAlgorithm;

	// map of identifier to key
	private Map<String, JWK> keys = new HashMap<String, JWK>();

	/**
	 * Build this service based on the keys given. All public keys will be used to make encrypters,
	 * all private keys will be used to make decrypters.
	 * 
	 * @param keys
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws JOSEException
	 */
	public DefaultJwtEncryptionAndDecryptionService(Map<String, JWK> keys) throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
		this.keys = keys;
		buildEncryptersAndDecrypters();
	}

	/**
	 * Build this service based on the given keystore. All keys must have a key
	 * id ({@code kid}) field in order to be used.
	 * 
	 * @param keyStore
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws JOSEException
	 */
	public DefaultJwtEncryptionAndDecryptionService(JWKSetKeyStore keyStore) throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {

		// convert all keys in the keystore to a map based on key id
		for (JWK key : keyStore.getKeys()) {
			if (!Strings.isNullOrEmpty(key.getKeyID())) {
				this.keys.put(key.getKeyID(), key);
			} else {
				throw new IllegalArgumentException("Tried to load a key from a keystore without a 'kid' field: " + key);
			}
		}

		buildEncryptersAndDecrypters();

	}
	
	public DefaultJwtEncryptionAndDecryptionService(String jwkSetString, String defaultEncryptionKeyId,String defaultAlgorithm) throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
		JWKSetKeyStore keyStore = new JWKSetKeyStore("{\"keys\": [" + jwkSetString + "]}");
		this.defaultEncryptionKeyId = defaultEncryptionKeyId;
		this.defaultAlgorithm = JWEAlgorithm.parse(defaultAlgorithm);
		_logger.trace(" encryptAlgorithm {}" , defaultAlgorithm);
		
		// convert all keys in the keystore to a map based on key id
		for (JWK key : keyStore.getKeys()) {
			if (!Strings.isNullOrEmpty(key.getKeyID())) {
				this.keys.put(key.getKeyID(), key);
			} else {
				throw new IllegalArgumentException("Tried to load a key from a keystore without a 'kid' field: " + key);
			}
		}

		buildEncryptersAndDecrypters();

	}


	@PostConstruct
	public void afterPropertiesSet() {

		if (keys == null) {
			throw new IllegalArgumentException("Encryption and decryption service must have at least one key configured.");
		}
		try {
			buildEncryptersAndDecrypters();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Encryption and decryption service could not find given algorithm.");
		} catch (InvalidKeySpecException e) {
			throw new IllegalArgumentException("Encryption and decryption service saw an invalid key specification.");
		} catch (JOSEException e) {
			throw new IllegalArgumentException("Encryption and decryption service was unable to process JOSE object.");
		}
	}

	public String getDefaultEncryptionKeyId() {
		if (defaultEncryptionKeyId != null) {
			return defaultEncryptionKeyId;
		} else if (keys.size() == 1) {
			// if there's only one key in the map, it's the default
			return keys.keySet().iterator().next();
		} else {
			return null;
		}
	}

	public void setDefaultEncryptionKeyId(String defaultEncryptionKeyId) {
		this.defaultEncryptionKeyId = defaultEncryptionKeyId;
	}

	public String getDefaultDecryptionKeyId() {
		if (defaultDecryptionKeyId != null) {
			return defaultDecryptionKeyId;
		} else if (keys.size() == 1) {
			// if there's only one key in the map, it's the default
			return keys.keySet().iterator().next();
		} else {
			return null;
		}
	}

	public void setDefaultDecryptionKeyId(String defaultDecryptionKeyId) {
		this.defaultDecryptionKeyId = defaultDecryptionKeyId;
	}

	public JWEAlgorithm getDefaultAlgorithm() {
		return defaultAlgorithm;
	}
	
	public JWEAlgorithm getDefaultAlgorithm(String algorithm) {
		if(algorithm.startsWith("RSA")) {
			return defaultAlgorithm;
		}else {
			return JWEAlgorithm.DIR;
		}
	}

	public void setDefaultAlgorithm(String algorithm) {
		defaultAlgorithm = JWEAlgorithm.parse(algorithm);
	}
	
	public void setDefaultAlgorithm(JWEAlgorithm defaultAlgorithm) {
		this.defaultAlgorithm = defaultAlgorithm;
	}

	/* (non-Javadoc)
	 * @see org.mitre.jwt.encryption.service.JwtEncryptionAndDecryptionService#encryptJwt(com.nimbusds.jwt.EncryptedJWT)
	 */
	@Override
	public void encryptJwt(JWEObject jwt) {
		if (getDefaultEncryptionKeyId() == null) {
			throw new IllegalStateException("Tried to call default encryption with no default encrypter ID set");
		}

		JWEEncrypter encrypter = encrypters.get(getDefaultEncryptionKeyId());

		try {
			jwt.encrypt(encrypter);
		} catch (JOSEException e) {

			_logger.error("Failed to encrypt JWT, error was: ", e);
		}

	}

	/* (non-Javadoc)
	 * @see org.mitre.jwt.encryption.service.JwtEncryptionAndDecryptionService#decryptJwt(com.nimbusds.jwt.EncryptedJWT)
	 */
	@Override
	public void decryptJwt(JWEObject jwt) {
		if (getDefaultDecryptionKeyId() == null) {
			throw new IllegalStateException("Tried to call default decryption with no default decrypter ID set");
		}

		JWEDecrypter decrypter = decrypters.get(getDefaultDecryptionKeyId());

		try {
			jwt.decrypt(decrypter);
		} catch (JOSEException e) {

			_logger.error("Failed to decrypt JWT, error was: ", e);
		}

	}

	/**
	 * Builds all the encrypters and decrypters for this service based on the key map.
	 * @throws
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws JOSEException
	 */
	private void buildEncryptersAndDecrypters() throws NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {

		for (Map.Entry<String, JWK> jwkEntry : keys.entrySet()) {

			String id = jwkEntry.getKey();
			JWK jwk = jwkEntry.getValue();

			if (jwk instanceof RSAKey) {
				// build RSA encrypters and decrypters

				RSAEncrypter encrypter = new RSAEncrypter(((RSAKey) jwk).toRSAPublicKey()); // there should always at least be the public key
				encrypters.put(id, encrypter);

				if (jwk.isPrivate()) { // we can decrypt!
					RSADecrypter decrypter = new RSADecrypter(((RSAKey) jwk).toRSAPrivateKey());
					decrypters.put(id, decrypter);
				} else {
					_logger.warn("No private key for key #" + jwk.getKeyID());
				}

				//  add support for EC keys

			} else if (jwk instanceof OctetSequenceKey) {
				// build symmetric encrypters and decrypters

				DirectEncrypter encrypter = new DirectEncrypter(((OctetSequenceKey) jwk).toByteArray());
				DirectDecrypter decrypter = new DirectDecrypter(((OctetSequenceKey) jwk).toByteArray());

				encrypters.put(id, encrypter);
				decrypters.put(id, decrypter);

			} else {
				_logger.warn("Unknown key type: " + jwk);
			}

		}
	}

	@Override
	public Map<String, JWK> getAllPublicKeys() {
		Map<String, JWK> pubKeys = new HashMap<String, JWK>();

		// pull out all public keys
		for (String keyId : keys.keySet()) {
			JWK key = keys.get(keyId);
			JWK pub = key.toPublicJWK();
			if (pub != null) {
				pubKeys.put(keyId, pub);
			}
		}

		return pubKeys;
	}

	@Override
	public Collection<JWEAlgorithm> getAllEncryptionAlgsSupported() {
		Set<JWEAlgorithm> algs = new HashSet<JWEAlgorithm>();

		for (JWEEncrypter encrypter : encrypters.values()) {
			algs.addAll(encrypter.supportedJWEAlgorithms());
		}

		for (JWEDecrypter decrypter : decrypters.values()) {
			algs.addAll(decrypter.supportedJWEAlgorithms());
		}

		return algs;
	}

	/* (non-Javadoc)
	 * @see org.mitre.jwt.encryption.service.JwtEncryptionAndDecryptionService#getAllEncryptionEncsSupported()
	 */
	@Override
	public Collection<EncryptionMethod> getAllEncryptionEncsSupported() {
		Set<EncryptionMethod> encs = new HashSet<EncryptionMethod>();

		for (JWEEncrypter encrypter : encrypters.values()) {
			encs.addAll(encrypter.supportedEncryptionMethods());
		}

		for (JWEDecrypter decrypter : decrypters.values()) {
			encs.addAll(decrypter.supportedEncryptionMethods());
		}

		return encs;
	}

	public EncryptionMethod parseEncryptionMethod(String encryptionMethodName) {
		EncryptionMethod encryptionMethod = null;
		if(encryptionMethodName.equalsIgnoreCase("A128GCM")) {
			encryptionMethod = EncryptionMethod.A128GCM;
		}else if(encryptionMethodName.equalsIgnoreCase("A192GCM")) {
			encryptionMethod = EncryptionMethod.A192GCM;
		}else if(encryptionMethodName.equalsIgnoreCase("A256GCM")) {
			encryptionMethod = EncryptionMethod.A256GCM;
		}else if(encryptionMethodName.equalsIgnoreCase("A128CBC_HS256")) {
			encryptionMethod = EncryptionMethod.A128CBC_HS256;
		}else if(encryptionMethodName.equalsIgnoreCase("A192CBC_HS384")) {
			encryptionMethod = EncryptionMethod.A192CBC_HS384;
		}else if(encryptionMethodName.equalsIgnoreCase("A256CBC_HS512")) {
			encryptionMethod = EncryptionMethod.A256CBC_HS512;
		}else if(encryptionMethodName.equalsIgnoreCase("XC20P")) {
			encryptionMethod = EncryptionMethod.XC20P;
		}
		return encryptionMethod;
	}
}
