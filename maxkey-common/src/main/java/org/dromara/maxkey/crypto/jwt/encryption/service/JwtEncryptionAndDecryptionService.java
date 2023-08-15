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
package org.dromara.maxkey.crypto.jwt.encryption.service;

import java.util.Collection;
import java.util.Map;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.jwk.JWK;

/**
 * @author wkim
 *
 */
public interface JwtEncryptionAndDecryptionService {

	/**
	 * Encrypts the JWT in place with the default encrypter.
	 * If an arbitrary payload is used, then pass in a JWEObject.
	 * Otherwise, if JWT claims are the payload, then use the JWEObject subclass EncryptedJWT instead.
	 * @param jwt
	 */
	public void encryptJwt(JWEObject jwt);

	/**
	 * Decrypts the JWT in place with the default decrypter.
	 *  If an arbitrary payload is used, then pass in a JWEObject.
	 *  Otherwise, if JWT claims are the payload, then use the JWEObject subclass EncryptedJWT instead.
	 * @param jwt
	 */
	public void decryptJwt(JWEObject jwt);

	/**
	 * Get all public keys for this service, mapped by their Key ID
	 */
	public Map<String, JWK> getAllPublicKeys();

	/**
	 * Get the list of all encryption algorithms supported by this service.
	 * @return
	 */
	public Collection<JWEAlgorithm> getAllEncryptionAlgsSupported();

	/**
	 * Get the list of all encryption methods supported by this service.
	 * @return
	 */
	public Collection<EncryptionMethod> getAllEncryptionEncsSupported();

	/**
	 *  add functionality for encrypting and decrypting using a specified key id.
	 * Example: public void encryptJwt(EncryptedJWT jwt, String kid);
	 */
}
