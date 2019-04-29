/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2016, Connect2id Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.nimbusds.jose.jwk;


import java.security.Key;
import java.security.KeyPair;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nimbusds.jose.JOSEException;


/**
 * Key converter.
 */
public class KeyConverter {
	

	/**
	 * Converts the specified list of JSON Web Keys (JWK) their standard
	 * Java class representation. Asymmetric {@link RSAKey RSA} and
	 * {@link ECKey EC key} pairs are converted to
	 * {@link java.security.PublicKey} and {@link java.security.PrivateKey}
	 * (if specified) objects. {@link OctetSequenceKey secret JWKs} are
	 * converted to {@link javax.crypto.SecretKey} objects. Key conversion
	 * exceptions are silently ignored.
	 *
	 * @param jwkList The JWK list. May be {@code null}.
	 *
	 * @return The converted keys, empty set if none or {@code null}.
	 */
	public static List<Key> toJavaKeys(final List<JWK> jwkList) {

		if (jwkList == null) {
			return Collections.emptyList();
		}

		List<Key> out = new LinkedList<>();
		for (JWK jwk: jwkList) {
			try {
				if (jwk instanceof AsymmetricJWK) {
					KeyPair keyPair = ((AsymmetricJWK)jwk).toKeyPair();
					out.add(keyPair.getPublic()); // add public
					if (keyPair.getPrivate() != null) {
						out.add(keyPair.getPrivate()); // add private if present
					}
				} else if (jwk instanceof SecretJWK) {
					out.add(((SecretJWK)jwk).toSecretKey());
				}
			} catch (JOSEException e) {
				// ignore and continue
			}
		}
		return out;
	}
}
