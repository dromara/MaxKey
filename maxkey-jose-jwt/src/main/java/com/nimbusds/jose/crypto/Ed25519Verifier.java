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
import java.util.Set;

import com.google.crypto.tink.subtle.Ed25519Verify;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
import com.nimbusds.jose.crypto.impl.EdDSAProvider;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.util.Base64URL;
import net.jcip.annotations.ThreadSafe;


/**
 * Ed25519 verifier of {@link com.nimbusds.jose.JWSObject JWS objects}.
 * Expects a public {@link OctetKeyPair} with {@code "crv"} Ed25519.
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
public class Ed25519Verifier extends EdDSAProvider implements JWSVerifier, CriticalHeaderParamsAware {


	private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();


	private final OctetKeyPair publicKey;


	private final Ed25519Verify tinkVerifier;


	/**
	 * Creates a new Ed25519 verifier.
	 *
	 * @param publicKey The public Ed25519 key. Must not be {@code null}.
	 *
	 * @throws JOSEException If the key subtype is not supported
	 */
	public Ed25519Verifier(final OctetKeyPair publicKey)
		throws JOSEException {

		this(publicKey, null);
	}


	/**
	 * Creates a Ed25519 verifier.
	 *
	 * @param publicKey      The public Ed25519 key. Must not be {@code null}.
	 * @param defCritHeaders The names of the critical header parameters
	 *                       that are deferred to the application for
	 *                       processing, empty set or {@code null} if none.
	 *
	 * @throws JOSEException If the key subtype is not supported.
	 */
	public Ed25519Verifier(final OctetKeyPair publicKey, final Set<String> defCritHeaders)
		throws JOSEException {

		super();

		if (! Curve.Ed25519.equals(publicKey.getCurve())) {
			throw new JOSEException("Ed25519Verifier only supports OctetKeyPairs with crv=Ed25519");
		}

		if (publicKey.isPrivate()) {
			throw new JOSEException("Ed25519Verifier requires a public key, use OctetKeyPair.toPublicJWK()");
		}

		this.publicKey = publicKey;
		tinkVerifier = new Ed25519Verify(publicKey.getDecodedX());
		critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
	}


	/**
	 * Returns the public key.
	 *
	 * @return An OctetKeyPair without the private part
	 */
	public OctetKeyPair getPublicKey() {

		return publicKey;
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
	public boolean verify(final JWSHeader header,
		              final byte[] signedContent, 
		              final Base64URL signature)
		throws JOSEException {

		// Check alg field in header
		final JWSAlgorithm alg = header.getAlgorithm();
		if (! JWSAlgorithm.EdDSA.equals(alg)) {
			throw new JOSEException("Ed25519Verifier requires alg=EdDSA in JWSHeader");
		}

		// Check for unrecognized "crit" properties
		if (! critPolicy.headerPasses(header)) {
			return false;
		}

		final byte[] jwsSignature = signature.decode();

		try {
			tinkVerifier.verify(jwsSignature, signedContent);
			return true;

		} catch (GeneralSecurityException e) {
			return false;
		}
	}
}
