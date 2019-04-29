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


import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Signature;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.Curve;


/**
 * Elliptic Curve Digital Signature Algorithm (ECDSA) functions and utilities.
 *
 * @author Vladimir Dzhuvinov
 * @author Aleksei Doroganov
 * @version 2018-03-28
 */
public class ECDSA {


	/**
	 * Resolves the matching EC DSA algorithm for the specified EC key
	 * (public or private).
	 *
	 * @param ecKey The EC key. Must not be {@code null}.
	 *
	 * @return The matching EC DSA algorithm.
	 *
	 * @throws JOSEException If the elliptic curve of key is not supported.
	 */
	public static JWSAlgorithm resolveAlgorithm(final ECKey ecKey)
		throws JOSEException {

		ECParameterSpec ecParameterSpec = ecKey.getParams();
		return resolveAlgorithm(Curve.forECParameterSpec(ecParameterSpec));
	}


	/**
	 * Resolves the matching EC DSA algorithm for the specified elliptic
	 * curve.
	 *
	 * @param curve The elliptic curve. May be {@code null}.
	 *
	 * @return The matching EC DSA algorithm.
	 *
	 * @throws JOSEException If the elliptic curve of key is not supported.
	 */
	public static JWSAlgorithm resolveAlgorithm(final Curve curve)
		throws JOSEException {

		if (curve == null) {
			throw new JOSEException("The EC key curve is not supported, must be P-256, P-384 or P-521");
		} else if (Curve.P_256.equals(curve)) {
			return JWSAlgorithm.ES256;
		} else if (Curve.P_256K.equals(curve)) {
			return JWSAlgorithm.ES256K;
		} else if (Curve.P_384.equals(curve)) {
			return JWSAlgorithm.ES384;
		} else if (Curve.P_521.equals(curve)) {
			return JWSAlgorithm.ES512;
		} else {
			throw new JOSEException("Unexpected curve: " + curve);
		}
	}


	/**
	 * Creates a new JCA signer / verifier for ECDSA.
	 *
	 * @param alg         The ECDSA JWS algorithm. Must not be
	 *                    {@code null}.
	 * @param jcaProvider The JCA provider, {@code null} if not specified.
	 *
	 * @return The JCA signer / verifier instance.
	 *
	 * @throws JOSEException If a JCA signer / verifier couldn't be
	 *                       created.
	 */
	public static Signature getSignerAndVerifier(final JWSAlgorithm alg,
						     final Provider jcaProvider)
		throws JOSEException {

		String jcaAlg;

		if (alg.equals(JWSAlgorithm.ES256)) {
			jcaAlg = "SHA256withECDSA";
		} else if (alg.equals(JWSAlgorithm.ES256K)) {
			jcaAlg = "SHA256withECDSA";
		} else if (alg.equals(JWSAlgorithm.ES384)) {
			jcaAlg = "SHA384withECDSA";
		} else if (alg.equals(JWSAlgorithm.ES512)) {
			jcaAlg = "SHA512withECDSA";
		} else {
			throw new JOSEException(
				AlgorithmSupportMessage.unsupportedJWSAlgorithm(
					alg,
					ECDSAProvider.SUPPORTED_ALGORITHMS));
		}

		try {
			if (jcaProvider != null) {
				return Signature.getInstance(jcaAlg, jcaProvider);
			} else {
				return Signature.getInstance(jcaAlg);
			}
		} catch (NoSuchAlgorithmException e) {
			throw new JOSEException("Unsupported ECDSA algorithm: " + e.getMessage(), e);
		}
	}


	/**
	 * Returns the expected signature byte array length (R + S parts) for
	 * the specified ECDSA algorithm.
	 *
	 * @param alg The ECDSA algorithm. Must be supported and not
	 *            {@code null}.
	 *
	 * @return The expected byte array length for the signature.
	 *
	 * @throws JOSEException If the algorithm is not supported.
	 */
	public static int getSignatureByteArrayLength(final JWSAlgorithm alg)
		throws JOSEException {

		if (alg.equals(JWSAlgorithm.ES256)) {

			return 64;

		} else if (alg.equals(JWSAlgorithm.ES256K)) {

			return 64;

		} else if (alg.equals(JWSAlgorithm.ES384)) {

			return 96;

		} else if (alg.equals(JWSAlgorithm.ES512)) {

			return 132;

		} else {

			throw new JOSEException(AlgorithmSupportMessage.unsupportedJWSAlgorithm(
				alg,
				ECDSAProvider.SUPPORTED_ALGORITHMS));
		}
	}


	/**
	 * Transcodes the JCA ASN.1/DER-encoded signature into the concatenated
	 * R + S format expected by ECDSA JWS.
	 *
	 * @param derSignature The ASN1./DER-encoded. Must not be {@code null}.
	 * @param outputLength The expected length of the ECDSA JWS signature.
	 *
	 * @return The ECDSA JWS encoded signature.
	 *
	 * @throws JOSEException If the ASN.1/DER signature format is invalid.
	 */
	public static byte[] transcodeSignatureToConcat(final byte[] derSignature, int outputLength)
		throws JOSEException {

		if (derSignature.length < 8 || derSignature[0] != 48) {
			throw new JOSEException("Invalid ECDSA signature format");
		}

		int offset;
		if (derSignature[1] > 0) {
			offset = 2;
		} else if (derSignature[1] == (byte) 0x81) {
			offset = 3;
		} else {
			throw new JOSEException("Invalid ECDSA signature format");
		}

		byte rLength = derSignature[offset + 1];

		int i;
		for (i = rLength; (i > 0) && (derSignature[(offset + 2 + rLength) - i] == 0); i--) {
			// do nothing
		}

		byte sLength = derSignature[offset + 2 + rLength + 1];

		int j;
		for (j = sLength; (j > 0) && (derSignature[(offset + 2 + rLength + 2 + sLength) - j] == 0); j--) {
			// do nothing
		}

		int rawLen = Math.max(i, j);
		rawLen = Math.max(rawLen, outputLength / 2);

		if ((derSignature[offset - 1] & 0xff) != derSignature.length - offset
			|| (derSignature[offset - 1] & 0xff) != 2 + rLength + 2 + sLength
			|| derSignature[offset] != 2
			|| derSignature[offset + 2 + rLength] != 2) {
			throw new JOSEException("Invalid ECDSA signature format");
		}

		final byte[] concatSignature = new byte[2 * rawLen];

		System.arraycopy(derSignature, (offset + 2 + rLength) - i, concatSignature, rawLen - i, i);
		System.arraycopy(derSignature, (offset + 2 + rLength + 2 + sLength) - j, concatSignature, 2 * rawLen - j, j);

		return concatSignature;
	}



	/**
	 * Transcodes the ECDSA JWS signature into ASN.1/DER format for use by
	 * the JCA verifier.
	 *
	 * @param jwsSignature The JWS signature, consisting of the
	 *                     concatenated R and S values. Must not be
	 *                     {@code null}.
	 *
	 * @return The ASN.1/DER encoded signature.
	 *
	 * @throws JOSEException If the ECDSA JWS signature format is invalid.
	 */
	public static byte[] transcodeSignatureToDER(byte[] jwsSignature)
		throws JOSEException {

		// Adapted from org.apache.xml.security.algorithms.implementations.SignatureECDSA

		int rawLen = jwsSignature.length / 2;

		int i;

		for (i = rawLen; (i > 0) && (jwsSignature[rawLen - i] == 0); i--) {
			// do nothing
		}

		int j = i;

		if (jwsSignature[rawLen - i] < 0) {
			j += 1;
		}

		int k;

		for (k = rawLen; (k > 0) && (jwsSignature[2 * rawLen - k] == 0); k--) {
			// do nothing
		}

		int l = k;

		if (jwsSignature[2 * rawLen - k] < 0) {
			l += 1;
		}

		int len = 2 + j + 2 + l;

		if (len > 255) {
			throw new JOSEException("Invalid ECDSA signature format");
		}

		int offset;

		final byte derSignature[];

		if (len < 128) {
			derSignature = new byte[2 + 2 + j + 2 + l];
			offset = 1;
		} else {
			derSignature = new byte[3 + 2 + j + 2 + l];
			derSignature[1] = (byte) 0x81;
			offset = 2;
		}

		derSignature[0] = 48;
		derSignature[offset++] = (byte) len;
		derSignature[offset++] = 2;
		derSignature[offset++] = (byte) j;

		System.arraycopy(jwsSignature, rawLen - i, derSignature, (offset + j) - i, i);

		offset += j;

		derSignature[offset++] = 2;
		derSignature[offset++] = (byte) l;

		System.arraycopy(jwsSignature, 2 * rawLen - k, derSignature, (offset + l) - k, k);

		return derSignature;
	}


	/**
	 * Prevents public instantiation.
	 */
	private ECDSA() {}
}
