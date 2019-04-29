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


import java.security.Provider;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import net.jcip.annotations.Immutable;


/**
 * Pseudo-Random Function (PRF) parameters, intended for use in the Password-
 * Based Key Derivation Function 2 (PBKDF2).
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-05-26
 */
@Immutable
public final class PRFParams {


	/**
	 * The JCA MAC algorithm name.
	 */
	private final String jcaMacAlg;


	/**
	 * The JCA MAC provider, {@code null} to use the default one.
	 */
	private final Provider macProvider;


	/**
	 * The byte length of the key to derive.
	 */
	private final int dkLen;


	/**
	 * Creates a new pseudo-random function parameters instance.
	 *
	 * @param jcaMacAlg   The JCA MAC algorithm name. Must not be
	 *                    {@code null}.
	 * @param macProvider The JCA MAC provider, {@code null} to use the
	 *                    default one.
	 * @param dkLen       The byte length of the key to derive.

	 */
	public PRFParams(String jcaMacAlg, Provider macProvider, int dkLen) {
		this.jcaMacAlg = jcaMacAlg;
		this.macProvider = macProvider;
		this.dkLen = dkLen;
	}


	/**
	 * Returns the JCA MAC algorithm name.
	 *
	 * @return The JCA MAC algorithm name.
	 */
	public String getMACAlgorithm() {

		return jcaMacAlg;
	}


	/**
	 * Returns the JCA MAC provider.
	 *
	 * @return The JCA MAC provider, {@code null} to use the default one.
	 */
	public Provider getMacProvider() {

		return macProvider;
	}


	/**
	 * Returns the byte length of the key to derive.
	 *
	 * @return The byte length of the key to derive.
	 */
	public int getDerivedKeyByteLength() {

		return dkLen;
	}


	/**
	 * Resolves the Pseudo-Random Function (PRF) parameters for the
	 * specified PBES2 JWE algorithm.
	 *
	 * @param alg         The JWE algorithm. Must be supported and not
	 *                    {@code null}.
	 * @param macProvider The specific MAC JCA provider, {@code null} to
	 *                    use the default one.
	 *
	 * @return The PRF parameters.
	 *
	 * @throws JOSEException If the JWE algorithm is not supported.
	 */
	public static PRFParams resolve(final JWEAlgorithm alg,
					   final Provider macProvider)
		throws JOSEException {

		final String jcaMagAlg;
		final int dkLen;

		if (JWEAlgorithm.PBES2_HS256_A128KW.equals(alg)) {
			jcaMagAlg = "HmacSHA256";
			dkLen = 16;
		} else if (JWEAlgorithm.PBES2_HS384_A192KW.equals(alg)) {
			jcaMagAlg = "HmacSHA384";
			dkLen = 24;
		} else if (JWEAlgorithm.PBES2_HS512_A256KW.equals(alg)) {
			jcaMagAlg = "HmacSHA512";
			dkLen = 32;
		} else {
			throw new JOSEException(AlgorithmSupportMessage.unsupportedJWEAlgorithm(
				alg,
				PasswordBasedCryptoProvider.SUPPORTED_ALGORITHMS));
		}

		return new PRFParams(jcaMagAlg, macProvider, dkLen);
	}
}
