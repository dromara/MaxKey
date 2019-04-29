/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2016, Connect2id Ltd and contributors.
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

package com.nimbusds.jose.jwk.gen;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;


/**
 * RSA JSON Web Key (JWK) generator.
 *
 * @author Vladimir Dzhuvinov
 * @version 2018-07-20
 */
public class RSAKeyGenerator extends JWKGenerator<RSAKey> {
	
	
	/**
	 * The minimum size of generated keys.
	 */
	public static final int MIN_KEY_SIZE_BITS = 2048;
	
	
	/**
	 * The RSA key size, in bits.
	 */
	private final int size;
	
	
	/**
	 * Creates a new RSA JWK generator.
	 *
	 * @param size The RSA key size, in bits. Must be at least 2048 bits
	 *             long for sufficient strength.
	 */
	public RSAKeyGenerator(final int size) {
		
		this(size, false);
	}
	
	
	/**
	 * Creates a new RSA JWK generator.
	 *
	 * @param size          The RSA key size, in bits. Must be at least
	 *                      2048 bits long for sufficient strength.
	 * @param allowWeakKeys {@code true} to allow generation of keys
	 *                      shorter than 2048 bits.
	 */
	public RSAKeyGenerator(final int size, final boolean allowWeakKeys) {
		
		if (! allowWeakKeys && size < MIN_KEY_SIZE_BITS) {
			throw new IllegalArgumentException("The key size must be at least " + MIN_KEY_SIZE_BITS + " bits");
		}
		this.size = size;
	}
	
	
	@Override
	public RSAKey generate()
		throws JOSEException {
		
		KeyPairGenerator generator;
		try {
			if (keyStore != null) {
				// For PKCS#11
				generator = KeyPairGenerator.getInstance("RSA", keyStore.getProvider());
			} else {
				generator = KeyPairGenerator.getInstance("RSA");
			}
			generator.initialize(size);
		} catch (NoSuchAlgorithmException e) {
			throw new JOSEException(e.getMessage(), e);
		}
		
		KeyPair kp = generator.generateKeyPair();
		
		RSAPublicKey pub = (RSAPublicKey) kp.getPublic();
		RSAPrivateKey priv = (RSAPrivateKey) kp.getPrivate();
		
		RSAKey.Builder builder = new RSAKey.Builder(pub)
			.privateKey(priv)
			.keyUse(use)
			.keyOperations(ops)
			.algorithm(alg)
			.keyStore(keyStore);
		
		if (x5tKid) {
			builder.keyIDFromThumbprint();
		} else {
			builder.keyID(kid);
		}
		
		return builder.build();
	}
}
