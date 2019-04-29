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


import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.crypto.SecretKey;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.util.ByteUtils;


/**
 * The base abstract class for direct encrypters and decrypters of
 * {@link com.nimbusds.jose.JWEObject JWE objects} with a shared symmetric key.
 *
 * <p>Supports the following key management algorithms:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#DIR}
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
 * @author Vladimir Dzhuvinov
 * @version 2015-06-29
 */
public abstract class DirectCryptoProvider extends BaseJWEProvider {


	/**
	 * The supported JWE algorithms by the direct crypto provider class.
	 */
	public static final Set<JWEAlgorithm> SUPPORTED_ALGORITHMS;


	/**
	 * The supported encryption methods by the direct crypto provider
	 * class.
	 */
	public static final Set<EncryptionMethod> SUPPORTED_ENCRYPTION_METHODS = ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS;


	static {
		Set<JWEAlgorithm> algs = new LinkedHashSet<>();
		algs.add(JWEAlgorithm.DIR);
		SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
	}


	/**
	 * Returns the compatible encryption methods for the specified Content
	 * Encryption Key (CEK) length.
	 *
	 * @param cekLength The CEK length in bits.
	 *
	 * @return The compatible encryption methods.
	 *
	 * @throws KeyLengthException If the CEK length is not compatible.
	 */
	private static Set<EncryptionMethod> getCompatibleEncryptionMethods(final int cekLength)
		throws KeyLengthException {

		Set<EncryptionMethod> encs = ContentCryptoProvider.COMPATIBLE_ENCRYPTION_METHODS.get(cekLength);

		if (encs == null) {
			throw new KeyLengthException("The Content Encryption Key length must be 128 bits (16 bytes), 192 bits (24 bytes), 256 bits (32 bytes), 384 bits (48 bytes) or 512 bites (64 bytes)");
		}

		return encs;
	}


	/**
	 * The Content Encryption Key (CEK).
	 */
	private final SecretKey cek;


	/**
	 * Creates a new direct encryption / decryption provider.
	 *
	 * @param cek The Content Encryption Key (CEK). Must be 128 bits (16
	 *            bytes), 192 bits (24 bytes), 256 bits (32 bytes), 384
	 *            bits (48 bytes) or 512 bits (64 bytes) long. Must not be
	 *            {@code null}.
	 *
	 * @throws KeyLengthException If the CEK length is not compatible.
	 */
	protected DirectCryptoProvider(final SecretKey cek)
		throws KeyLengthException {

		super(SUPPORTED_ALGORITHMS, getCompatibleEncryptionMethods(ByteUtils.bitLength(cek.getEncoded())));

		this.cek = cek;
	}


	/**
	 * Gets the Content Encryption Key (CEK).
	 *
	 * @return The key.
	 */
	public SecretKey getKey() {

		return cek;
	}
}
