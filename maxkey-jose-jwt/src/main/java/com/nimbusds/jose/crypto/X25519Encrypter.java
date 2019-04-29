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


import java.security.*;
import java.util.Collections;
import java.util.Set;
import javax.crypto.SecretKey;

import com.google.crypto.tink.subtle.X25519;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.impl.ECDH;
import com.nimbusds.jose.crypto.impl.ECDHCryptoProvider;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.util.Base64URL;
import net.jcip.annotations.ThreadSafe;


/**
 * Curve25519 Elliptic Curve Diffie-Hellman encrypter of
 * {@link com.nimbusds.jose.JWEObject JWE objects}.
 * Expects a public {@link OctetKeyPair} key with {@code "crv"} X25519.
 *
 * <p>See <a href="https://tools.ietf.org/html/rfc8037">RFC 8037</a>
 * for more information.
 *
 * <p>See also {@link ECDHEncrypter} for ECDH on other curves.
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
@ThreadSafe
public class X25519Encrypter extends ECDHCryptoProvider implements JWEEncrypter {


	/**
	 * The public key.
	 */
	private final OctetKeyPair publicKey;


	/**
	 * Creates a new Curve25519 Elliptic Curve Diffie-Hellman encrypter.
	 *
	 * @param publicKey The public key. Must not be {@code null}.
	 *
	 * @throws JOSEException If the key subtype is not supported.
	 */
	public X25519Encrypter(final OctetKeyPair publicKey)
		throws JOSEException {

		super(publicKey.getCurve());

		if (! Curve.X25519.equals(publicKey.getCurve())) {
			throw new JOSEException("X25519Encrypter only supports OctetKeyPairs with crv=X25519");
		}

		if (publicKey.isPrivate()) {
			throw new JOSEException("X25519Encrypter requires a public key, use OctetKeyPair.toPublicJWK()");
		}

		this.publicKey = publicKey;
	}


	@Override
	public Set<Curve> supportedEllipticCurves() {

		return Collections.singleton(Curve.X25519);
	}


	/**
	 * Returns the public key.
	 *
	 * @return The public key.
	 */
	public OctetKeyPair getPublicKey() {

		return publicKey;
	}


	@Override
	public JWECryptoParts encrypt(final JWEHeader header, final byte[] clearText)
		throws JOSEException {

		// Generate ephemeral X25519 key pair
		final byte[] ephemeralPrivateKeyBytes = X25519.generatePrivateKey();
		final byte[] ephemeralPublicKeyBytes;
		try {
			ephemeralPublicKeyBytes = X25519.publicFromPrivate(ephemeralPrivateKeyBytes);

		} catch (InvalidKeyException e) {
			// Should never happen since we just generated this private key
			throw new JOSEException(e.getMessage(), e);
		}

		final OctetKeyPair ephemeralPrivateKey =
			new OctetKeyPair.Builder(getCurve(), Base64URL.encode(ephemeralPublicKeyBytes)).
			d(Base64URL.encode(ephemeralPrivateKeyBytes)).
			build();
		final OctetKeyPair ephemeralPublicKey = ephemeralPrivateKey.toPublicJWK();

		// Add the ephemeral public EC key to the header
		JWEHeader updatedHeader = new JWEHeader.Builder(header).
			ephemeralPublicKey(ephemeralPublicKey).
			build();

		// Derive 'Z'
		SecretKey Z = ECDH.deriveSharedSecret(publicKey, ephemeralPrivateKey);

		return encryptWithZ(updatedHeader, Z, clearText);
	}
}
