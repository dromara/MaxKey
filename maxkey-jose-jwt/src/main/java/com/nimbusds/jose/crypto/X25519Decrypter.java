/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2018, Connect2id Ltd.
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
import java.util.Set;
import javax.crypto.SecretKey;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
import com.nimbusds.jose.crypto.impl.ECDH;
import com.nimbusds.jose.crypto.impl.ECDHCryptoProvider;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.util.Base64URL;


/**
 * Curve25519 Elliptic Curve Diffie-Hellman decrypter of
 * {@link com.nimbusds.jose.JWEObject JWE objects}.
 * Expects a private {@link OctetKeyPair} key with {@code "crv"} X25519.
 *
 * <p>See <a href="https://tools.ietf.org/html/rfc8037">RFC 8037</a>
 * for more information.
 *
 * <p>See also {@link ECDHDecrypter} for ECDH on other curves.
 *
 * <p>This class is thread-safe.
 *
 * <p>Supports the following key management algorithms:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#ECDH_ES}
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#ECDH_ES_A128KW}
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#ECDH_ES_A192KW}
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#ECDH_ES_A256KW}
 * </ul>
 *
 * <p>Supports the following elliptic curve:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.jwk.Curve#X25519} (Curve25519)
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
 * @author Tim McLean
 * @version 2018-07-12
 */
public class X25519Decrypter extends ECDHCryptoProvider implements JWEDecrypter, CriticalHeaderParamsAware {


	/**
	 * The private key.
	 */
	private final OctetKeyPair privateKey;


	/**
	 * The critical header policy.
	 */
	private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();


	/**
	 * Creates a new Curve25519 Elliptic Curve Diffie-Hellman decrypter.
	 *
	 * @param privateKey The private key. Must not be {@code null}.
	 *
	 * @throws JOSEException If the key subtype is not supported.
	 */
	public X25519Decrypter(final OctetKeyPair privateKey)
		throws JOSEException {

		this(privateKey, null);
	}


	/**
	 * Creates a new Curve25519 Elliptic Curve Diffie-Hellman decrypter.
	 *
	 * @param privateKey     The private key. Must not be {@code null}.
	 * @param defCritHeaders The names of the critical header parameters
	 *                       that are deferred to the application for
	 *                       processing, empty set or {@code null} if none.
	 *
	 * @throws JOSEException If the key subtype is not supported.
	 */
	public X25519Decrypter(final OctetKeyPair privateKey, final Set<String> defCritHeaders)
		throws JOSEException {

		super(privateKey.getCurve());

		if (! Curve.X25519.equals(privateKey.getCurve())) {
			throw new JOSEException("X25519Decrypter only supports OctetKeyPairs with crv=X25519");
		}

		if (! privateKey.isPrivate()) {
			throw new JOSEException("The OctetKeyPair doesn't contain a private part");
		}

		this.privateKey = privateKey;

		critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
	}


	@Override
	public Set<Curve> supportedEllipticCurves() {

		return Collections.singleton(Curve.X25519);
	}


	/**
	 * Returns the private key.
	 *
	 * @return The private key.
	 */
	public OctetKeyPair getPrivateKey() {

		return privateKey;
	}


	@Override
	public Set<String> getProcessedCriticalHeaderParams() {

		return critPolicy.getProcessedCriticalHeaderParams();
	}


	@Override
	public Set<String> getDeferredCriticalHeaderParams() {

		return critPolicy.getProcessedCriticalHeaderParams();
	}


	@Override
	public byte[] decrypt(final JWEHeader header,
			      final Base64URL encryptedKey,
			      final Base64URL iv,
			      final Base64URL cipherText,
			      final Base64URL authTag)
		throws JOSEException {

		// Check for unrecognizable "crit" properties
		critPolicy.ensureHeaderPasses(header);

		// Get ephemeral key from header
		OctetKeyPair ephemeralPublicKey = (OctetKeyPair) header.getEphemeralPublicKey();

		if (ephemeralPublicKey == null) {
			throw new JOSEException("Missing ephemeral public key \"epk\" JWE header parameter");
		}

		if (! privateKey.getCurve().equals(ephemeralPublicKey.getCurve())) {
			throw new JOSEException("Curve of ephemeral public key does not match curve of private key");
		}

		// Derive 'Z'
		// Note: X25519 does not require public key validation
		// See https://cr.yp.to/ecdh.html#validate
		SecretKey Z = ECDH.deriveSharedSecret(ephemeralPublicKey, privateKey);

		return decryptWithZ(header, Z, encryptedKey, iv, cipherText, authTag);
	}
}
