/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2018, Connect2id Ltd and contributors.
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

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.crypto.tink.subtle.Ed25519Sign;
import com.google.crypto.tink.subtle.X25519;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.util.Base64URL;


/**
 * Octet Key Pair (OKP) JSON Web Key (JWK) generator.
 *
 * <p>Supported curves:
 *
 * <ul>
 *     <li>{@link Curve#X25519 X25519}
 *     <li>{@link Curve#Ed25519 Ed25519}
 * </ul>
 *
 * @author Tim McLean
 * @version 2018-07-18
 */
public class OctetKeyPairGenerator extends JWKGenerator<OctetKeyPair> {


	/**
	 * The curve.
	 */
	private final Curve crv;


	/**
	 * The supported values for the "crv" property.
	 */
	public static final Set<Curve> SUPPORTED_CURVES;


	static {
		Set<Curve> curves = new LinkedHashSet<>();
		curves.add(Curve.X25519);
		curves.add(Curve.Ed25519);
		SUPPORTED_CURVES = Collections.unmodifiableSet(curves);
	}


	/**
	 * Creates a new OctetKeyPair JWK generator.
	 *
	 * @param crv The curve. Must not be {@code null}.
	 */
	public OctetKeyPairGenerator(final Curve crv) {

		if (crv == null) {
			throw new IllegalArgumentException("The curve must not be null");
		}

		if (! SUPPORTED_CURVES.contains(crv)) {
			throw new IllegalArgumentException("Curve not supported for OKP generation");
		}

		this.crv = crv;
	}
	
	
	@Override
	public OctetKeyPair generate()
		throws JOSEException {

		final Base64URL privateKey;
		final Base64URL publicKey;

		if (this.crv.equals(Curve.X25519)) {

			final byte[] privateKeyBytes;
			final byte[] publicKeyBytes;

			try {
				privateKeyBytes = X25519.generatePrivateKey();
				publicKeyBytes = X25519.publicFromPrivate(privateKeyBytes);

			} catch (InvalidKeyException e) {
				// internal Tink error, should not happen
				throw new JOSEException(e.getMessage(), e);
			}

			privateKey = Base64URL.encode(privateKeyBytes);
			publicKey = Base64URL.encode(publicKeyBytes);

		} else if (this.crv.equals(Curve.Ed25519)) {

			final Ed25519Sign.KeyPair tinkKeyPair;

			try {
				tinkKeyPair = Ed25519Sign.KeyPair.newKeyPair();

			} catch (GeneralSecurityException e) {
				// internal Tink error, should not happen
				throw new JOSEException(e.getMessage(), e);
			}

			privateKey = Base64URL.encode(tinkKeyPair.getPrivateKey());
			publicKey = Base64URL.encode(tinkKeyPair.getPublicKey());

		} else {

			throw new JOSEException("Curve not supported");
		}

		OctetKeyPair.Builder builder = new OctetKeyPair.Builder(crv, publicKey)
			.d(privateKey)
			.keyUse(use)
			.keyOperations(ops)
			.algorithm(alg);

		if (x5tKid) {
			builder.keyIDFromThumbprint();
		} else {
			builder.keyID(kid);
		}

		return builder.build();
	}
}
