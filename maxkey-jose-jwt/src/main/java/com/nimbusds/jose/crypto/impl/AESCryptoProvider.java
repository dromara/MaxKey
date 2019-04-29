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

package com.nimbusds.jose.crypto.impl;


import java.util.*;
import javax.crypto.SecretKey;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.util.ByteUtils;


/**
 * The base abstract class for AES and AES GCM key wrap encrypters and
 * decrypters of {@link com.nimbusds.jose.JWEObject JWE objects}.
 *
 * <p>Supports the following key management algorithms:
 *
 * <ul>
 *      <li>{@link com.nimbusds.jose.JWEAlgorithm#A128KW}
 *      <li>{@link com.nimbusds.jose.JWEAlgorithm#A192KW}
 *      <li>{@link com.nimbusds.jose.JWEAlgorithm#A256KW}
 *      <li>{@link com.nimbusds.jose.JWEAlgorithm#A128GCMKW}
 *      <li>{@link com.nimbusds.jose.JWEAlgorithm#A192GCMKW}
 *      <li>{@link com.nimbusds.jose.JWEAlgorithm#A256GCMKW}
 * </ul>
 *
 * <p>Supports the following content encryption algorithms:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A128CBC_HS256}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A192CBC_HS384}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A256CBC_HS512}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A128GCM}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A192GCM}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A256GCM}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A128CBC_HS256_DEPRECATED}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A256CBC_HS512_DEPRECATED}
 * </ul>
 *
 * @author Melisa Halsband
 * @author Vladimir Dzhuvinov
 * @version 2015-06-29
 */
public abstract class AESCryptoProvider extends BaseJWEProvider {


	/**
	 * The supported JWE algorithms by the AES crypto provider class.
	 */
	public static final Set<JWEAlgorithm> SUPPORTED_ALGORITHMS;


	/**
	 * The supported encryption methods by the AES crypto provider class.
	 */
	public static final Set<EncryptionMethod> SUPPORTED_ENCRYPTION_METHODS = ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS;


	/**
	 * The JWE algorithms compatible with each key size in bits.
	 */
	public static final Map<Integer,Set<JWEAlgorithm>> COMPATIBLE_ALGORITHMS;


	static {
		Set<JWEAlgorithm> algs = new LinkedHashSet<>();
		algs.add(JWEAlgorithm.A128KW);
		algs.add(JWEAlgorithm.A192KW);
		algs.add(JWEAlgorithm.A256KW);
		algs.add(JWEAlgorithm.A128GCMKW);
		algs.add(JWEAlgorithm.A192GCMKW);
		algs.add(JWEAlgorithm.A256GCMKW);
		SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);

		Map<Integer,Set<JWEAlgorithm>> algsMap = new HashMap<>();
		Set<JWEAlgorithm> bit128Algs = new HashSet<>();
		Set<JWEAlgorithm> bit192Algs = new HashSet<>();
		Set<JWEAlgorithm> bit256Algs = new HashSet<>();
		bit128Algs.add(JWEAlgorithm.A128GCMKW);
		bit128Algs.add(JWEAlgorithm.A128KW);
		bit192Algs.add(JWEAlgorithm.A192GCMKW);
		bit192Algs.add(JWEAlgorithm.A192KW);
		bit256Algs.add(JWEAlgorithm.A256GCMKW);
		bit256Algs.add(JWEAlgorithm.A256KW);
		algsMap.put(128,Collections.unmodifiableSet(bit128Algs));
		algsMap.put(192,Collections.unmodifiableSet(bit192Algs));
		algsMap.put(256,Collections.unmodifiableSet(bit256Algs));
		COMPATIBLE_ALGORITHMS = Collections.unmodifiableMap(algsMap);
	}


	/**
	 * The Key Encryption Key (KEK).
	 */
	private final SecretKey kek;


	/**
	 * Returns the compatible JWE algorithms for the specified Key
	 * Encryption Key (CEK) length.
	 *
	 * @param kekLength The KEK length in bits.
	 *
	 * @return The compatible JWE algorithms.
	 *
	 * @throws KeyLengthException If the KEK length is not compatible.
	 */
	private static Set<JWEAlgorithm> getCompatibleJWEAlgorithms(final int kekLength)
		throws KeyLengthException {

		Set<JWEAlgorithm> algs = COMPATIBLE_ALGORITHMS.get(kekLength);

		if (algs == null) {
			throw new KeyLengthException("The Key Encryption Key length must be 128 bits (16 bytes), 192 bits (24 bytes) or 256 bits (32 bytes)");
		}

		return algs;
	}


	/**
	 * Creates a new AES encryption / decryption provider.
	 *
	 *  @param kek The Key Encryption Key. Must be 128 bits (16 bytes), 192
	 *             bits (24 bytes) or 256 bits (32 bytes). Must not be
	 *             {@code null}.
	 *
	 * @throws KeyLengthException If the KEK length is invalid.
	 */
	protected AESCryptoProvider(final SecretKey kek)
		throws KeyLengthException {

		super(getCompatibleJWEAlgorithms(ByteUtils.bitLength(kek.getEncoded())), ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS);

		this.kek = kek;
	}


	/**
	 * Gets the Key Encryption Key (KEK).
	 *
	 * @return The Key Encryption Key.
	 */
	public SecretKey getKey() {

		return kek;
	}
}
