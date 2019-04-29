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


import java.security.GeneralSecurityException;

import com.google.crypto.tink.subtle.Ed25519Sign;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.impl.EdDSAProvider;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.util.Base64URL;
import net.jcip.annotations.ThreadSafe;


/**
 * Ed25519 signer of {@link com.nimbusds.jose.JWSObject JWS objects}.
 * Expects an {@link OctetKeyPair} with {@code "crv"} Ed25519.
 * Uses the Edwards-curve Digital Signature Algorithm (EdDSA).
 *
 * <p>See <a href="https://tools.ietf.org/html/rfc8037">RFC 8037</a>
 * for more information.
 *
 * <p>This class is thread-safe.
 *
 * <p>Supports the following algorithm:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#EdDSA}
 * </ul>
 *
 * <p>with the following curve:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.jwk.Curve#Ed25519}
 * </ul>
 *
 * @author Tim McLean
 * @version 2018-07-11
 */
@ThreadSafe
public class Ed25519Signer extends EdDSAProvider implements JWSSigner {
	
	
	private final OctetKeyPair privateKey;


	private final Ed25519Sign tinkSigner;


	/**
	 * Creates a new Ed25519 signer.
	 *
	 * @param privateKey The private key. Must be non-{@code null}, and must
	 * be of type Ed25519 ({@code "crv": "Ed25519"}).
	 *
	 * @throws JOSEException If the key subtype is not supported or if the key is not a private key
	 */
	public Ed25519Signer(final OctetKeyPair privateKey)
		throws JOSEException {

		super();

		if (! Curve.Ed25519.equals(privateKey.getCurve())) {
			throw new JOSEException("Ed25519Signer only supports OctetKeyPairs with crv=Ed25519");
		}

		if (! privateKey.isPrivate()) {
			throw new JOSEException("The OctetKeyPair doesn't contain a private part");
		}

		this.privateKey = privateKey;

		try {
			tinkSigner = new Ed25519Sign(privateKey.getDecodedD());

		} catch (GeneralSecurityException e) {
			// If Tink failed to initialize; generally should not happen
			throw new JOSEException(e.getMessage(), e);
		}
	}


	/**
	 * Gets the Ed25519 private key as an {@code OctetKeyPair}.
	 *
	 * @return The private key.
	 */
	public OctetKeyPair getPrivateKey() {
		
		return privateKey;
	}


	@Override
	public Base64URL sign(final JWSHeader header, final byte[] signingInput)
		throws JOSEException {

		// Check alg field in header
		final JWSAlgorithm alg = header.getAlgorithm();
		if (! JWSAlgorithm.EdDSA.equals(alg)) {
			throw new JOSEException("Ed25519Signer requires alg=EdDSA in JWSHeader");
		}

		final byte[] jwsSignature;

		try {
			jwsSignature = tinkSigner.sign(signingInput);

		} catch (GeneralSecurityException e) {

			throw new JOSEException(e.getMessage(), e);
		}

		return Base64URL.encode(jwsSignature);
	}
}
