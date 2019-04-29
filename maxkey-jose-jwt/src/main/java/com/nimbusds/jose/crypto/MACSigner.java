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

package com.nimbusds.jose.crypto;


import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.crypto.SecretKey;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.impl.AlgorithmSupportMessage;
import com.nimbusds.jose.crypto.impl.HMAC;
import com.nimbusds.jose.crypto.impl.MACProvider;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.ByteUtils;
import com.nimbusds.jose.util.StandardCharset;
import net.jcip.annotations.ThreadSafe;



/**
 * Message Authentication Code (MAC) signer of 
 * {@link com.nimbusds.jose.JWSObject JWS objects}. Expects a secret key.
 *
 * <p>See RFC 7518
 * <a href="https://tools.ietf.org/html/rfc7518#section-3.2">section 3.2</a>
 * for more information.
 *
 * <p>This class is thread-safe.
 *
 * <p>Supports the following algorithms:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#HS256}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#HS384}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#HS512}
 * </ul>
 * 
 * @author Vladimir Dzhuvinov
 * @version 2016-07-27
 */
@ThreadSafe
public class MACSigner extends MACProvider implements JWSSigner {


	/**
	 * Returns the minimal required secret length for the specified HMAC
	 * JWS algorithm.
	 *
	 * @param alg The HMAC JWS algorithm. Must be
	 *            {@link #SUPPORTED_ALGORITHMS supported} and not
	 *            {@code null}.
	 *
	 * @return The minimal required secret length, in bits.
	 *
	 * @throws JOSEException If the algorithm is not supported.
	 */
	public static int getMinRequiredSecretLength(final JWSAlgorithm alg)
		throws JOSEException {

		if (JWSAlgorithm.HS256.equals(alg)) {
			return 256;
		} else if (JWSAlgorithm.HS384.equals(alg)) {
			return 384;
		} else if (JWSAlgorithm.HS512.equals(alg)) {
			return 512;
		} else {
			throw new JOSEException(AlgorithmSupportMessage.unsupportedJWSAlgorithm(
				alg,
				SUPPORTED_ALGORITHMS));
		}
	}


	/**
	 * Returns the compatible JWS HMAC algorithms for the specified secret
	 * length.
	 *
	 * @param secretLength The secret length in bits. Must not be negative.
	 *
	 * @return The compatible HMAC algorithms, empty set if the secret
	 *         length is too short for any algorithm.
	 */
	public static Set<JWSAlgorithm> getCompatibleAlgorithms(final int secretLength) {

		Set<JWSAlgorithm> hmacAlgs = new LinkedHashSet<>();

		if (secretLength >= 256)
			hmacAlgs.add(JWSAlgorithm.HS256);

		if (secretLength >= 384)
			hmacAlgs.add(JWSAlgorithm.HS384);

		if (secretLength >= 512)
			hmacAlgs.add(JWSAlgorithm.HS512);

		return Collections.unmodifiableSet(hmacAlgs);
	}


	/**
	 * Creates a new Message Authentication (MAC) signer.
	 *
	 * @param secret The secret. Must be at least 256 bits long and not
	 *               {@code null}.
	 *
	 * @throws KeyLengthException If the secret length is shorter than the
	 *                            minimum 256-bit requirement.
	 */
	public MACSigner(final byte[] secret)
		throws KeyLengthException {

		super(secret, getCompatibleAlgorithms(ByteUtils.bitLength(secret.length)));
	}


	/**
	 * Creates a new Message Authentication (MAC) signer.
	 *
	 * @param secretString The secret as a UTF-8 encoded string. Must be at
	 *                     least 256 bits long and not {@code null}.
	 *
	 * @throws KeyLengthException If the secret length is shorter than the
	 *                            minimum 256-bit requirement.
	 */
	public MACSigner(final String secretString)
		throws KeyLengthException {

		this(secretString.getBytes(StandardCharset.UTF_8));
	}


	/**
	 * Creates a new Message Authentication (MAC) signer.
	 *
	 * @param secretKey The secret key. Must be at least 256 bits long and
	 *                  not {@code null}.
	 *
	 * @throws KeyLengthException If the secret length is shorter than the
	 *                            minimum 256-bit requirement.
	 */
	public MACSigner(final SecretKey secretKey)
		throws KeyLengthException {

		this(secretKey.getEncoded());
	}


	/**
	 * Creates a new Message Authentication (MAC) signer.
	 *
	 * @param jwk The secret as a JWK. Must be at least 256 bits long and
	 *            not {@code null}.
	 *
	 * @throws KeyLengthException If the secret length is shorter than the
	 *                            minimum 256-bit requirement.
	 */
	public MACSigner(final OctetSequenceKey jwk)
		throws KeyLengthException {

		this(jwk.toByteArray());
	}


	@Override
	public Base64URL sign(final JWSHeader header, final byte[] signingInput)
		throws JOSEException {

		final int minRequiredLength = getMinRequiredSecretLength(header.getAlgorithm());

		if (getSecret().length < ByteUtils.byteLength(minRequiredLength)) {
			throw new KeyLengthException("The secret length for " + header.getAlgorithm() + " must be at least " + minRequiredLength + " bits");
		}

		String jcaAlg = getJCAAlgorithmName(header.getAlgorithm());
		byte[] hmac = HMAC.compute(jcaAlg, getSecret(), signingInput, getJCAContext().getProvider());
		return Base64URL.encode(hmac);
	}
}
