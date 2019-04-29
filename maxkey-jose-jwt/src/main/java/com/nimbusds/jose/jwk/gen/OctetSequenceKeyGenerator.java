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


import java.security.SecureRandom;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.util.Base64URL;


/**
 * Octet sequence JSON Web Key (JWK) generator.
 *
 * @author Vladimir Dzhuvinov
 * @version 2018-07-20
 */
public class OctetSequenceKeyGenerator extends JWKGenerator<OctetSequenceKey> {
	
	
	/**
	 * The minimum size of generated keys.
	 */
	public static final int MIN_KEY_SIZE_BITS = 112;
	
	
	/**
	 * The key size, in bits.
	 */
	private final int size;
	
	
	/**
	 * The secure random generator to use, {@code null} to use the default
	 * one.
	 */
	private SecureRandom secureRandom;
	
	
	/**
	 * Creates a new octet sequence JWK generator.
	 *
	 * @param size The key size, in bits. Must be at least 112 bits long
	 *             for sufficient entropy.
	 */
	public OctetSequenceKeyGenerator(final int size) {
		if (size < MIN_KEY_SIZE_BITS) {
			throw new IllegalArgumentException("The key size must be at least " + MIN_KEY_SIZE_BITS + " bits");
		}
		if (size % 8 != 0) {
			throw new IllegalArgumentException("The key size in bits must be divisible by 8");
		}
		this.size = size;
	}
	
	
	/**
	 * Sets the secure random generator to use.
	 *
	 * @param secureRandom The secure random generator to use, {@code null}
	 *                     to use the default one.
	 *
	 * @return This generator.
	 */
	public OctetSequenceKeyGenerator secureRandom(final SecureRandom secureRandom) {
		
		this.secureRandom = secureRandom;
		return this;
	}
	
	
	@Override
	public OctetSequenceKey generate()
		throws JOSEException {
		
		byte[] keyMaterial = new byte[size / 8];
		
		if (secureRandom != null) {
			secureRandom.nextBytes(keyMaterial);
		} else {
			// The default random gen
			new SecureRandom().nextBytes(keyMaterial);
		}
		
		OctetSequenceKey.Builder builder = new OctetSequenceKey.Builder(Base64URL.encode(keyMaterial))
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
