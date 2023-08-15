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
package org.dromara.maxkey.crypto.jwt.signer.service;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Map;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jwt.SignedJWT;

public interface JwtSigningAndValidationService {

	/**
	 * Get all public keys for this service, mapped by their Key ID
	 */
	public Map<String, JWK> getAllPublicKeys();

	/**
	 * Checks the signature of the given JWT against all configured signers,
	 * returns true if at least one of the signers validates it.
	 * 
	 * @param jwtString
	 *            the string representation of the JWT as sent on the wire
	 * @return true if the signature is valid, false if not
	 * @throws NoSuchAlgorithmException
	 */
	public boolean validateSignature(SignedJWT jwtString);

	/**
	 * Called to sign a jwt in place for a client that hasn't registered a preferred signing algorithm.
	 * Use the default algorithm to sign.
	 * 
	 * @param jwt the jwt to sign
	 * @return the signed jwt
	 * @throws NoSuchAlgorithmException
	 */
	public void signJwt(SignedJWT jwt);

	/**
	 * Get the default signing algorithm for use when nothing else has been specified.
	 * @return
	 */
	public JWSAlgorithm getDefaultSigningAlgorithm();

	/**
	 * Get the list of all signing algorithms supported by this service.
	 * @return
	 */
	public Collection<JWSAlgorithm> getAllSigningAlgsSupported();

	/**
	 * Sign a jwt using the selected algorithm. The algorithm is selected using the String parameter values specified
	 * in the JWT spec, section 6. I.E., "HS256" means HMAC with SHA-256 and corresponds to our HmacSigner class.
	 * 
	 * @param jwt the jwt to sign
	 * @param alg the name of the algorithm to use, as specified in JWS s.6
	 * @return the signed jwt
	 */
	public void signJwt(SignedJWT jwt, JWSAlgorithm alg);

	public String getDefaultSignerKeyId();

	/**
	 *  method to sign a jwt using a specified algorithm and a key id
	 */
}
