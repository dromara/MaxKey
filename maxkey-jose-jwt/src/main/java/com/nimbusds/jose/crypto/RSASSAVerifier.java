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


import java.security.InvalidKeyException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;

import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
import com.nimbusds.jose.crypto.impl.RSASSA;
import com.nimbusds.jose.crypto.impl.RSASSAProvider;
import net.jcip.annotations.ThreadSafe;

import com.nimbusds.jose.*;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64URL;


/**
 * RSA Signature-Scheme-with-Appendix (RSASSA) verifier of 
 * {@link com.nimbusds.jose.JWSObject JWS objects}. Expects a public RSA key.
 *
 * <p>See RFC 7518, sections
 * <a href="https://tools.ietf.org/html/rfc7518#section-3.3">3.3</a> and
 * <a href="https://tools.ietf.org/html/rfc7518#section-3.5">3.5</a> for more
 * information.
 *
 * <p>This class is thread-safe.
 *
 * <p>Supports the following algorithms:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#RS256}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#RS384}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#RS512}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#PS256}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#PS384}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#PS512}
 * </ul>
 * 
 * @author Vladimir Dzhuvinov
 * @version 2015-06-02
 */
@ThreadSafe
public class RSASSAVerifier extends RSASSAProvider implements JWSVerifier, CriticalHeaderParamsAware {


	/**
	 * The critical header policy.
	 */
	private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();


	/**
	 * The public RSA key.
	 */
	private final RSAPublicKey publicKey;


	/**
	 * Creates a new RSA Signature-Scheme-with-Appendix (RSASSA) verifier.
	 *
	 * @param publicKey The public RSA key. Must not be {@code null}.
	 */
	public RSASSAVerifier(final RSAPublicKey publicKey) {

		this(publicKey, null);
	}


	/**
	 * Creates a new RSA Signature-Scheme-with-Appendix (RSASSA) verifier.
	 *
	 * @param rsaJWK The RSA JSON Web Key (JWK). Must not be {@code null}.
	 *
	 * @throws JOSEException If the RSA JWK extraction failed.
	 */
	public RSASSAVerifier(final RSAKey rsaJWK)
		throws JOSEException {

		this(rsaJWK.toRSAPublicKey(), null);
	}


	/**
	 * Creates a new RSA Signature-Scheme-with-Appendix (RSASSA) verifier.
	 *
	 * @param publicKey      The public RSA key. Must not be {@code null}.
	 * @param defCritHeaders The names of the critical header parameters
	 *                       that are deferred to the application for
	 *                       processing, empty set or {@code null} if none.
	 */
	public RSASSAVerifier(final RSAPublicKey publicKey,
			      final Set<String> defCritHeaders) {

		if (publicKey == null) {
			throw new IllegalArgumentException("The public RSA key must not be null");
		}

		this.publicKey = publicKey;

		critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
	}


	/**
	 * Gets the public RSA key.
	 *
	 * @return The public RSA key.
	 */
	public RSAPublicKey getPublicKey() {

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

		if (! critPolicy.headerPasses(header)) {
			return false;
		}

		final Signature verifier = RSASSA.getSignerAndVerifier(header.getAlgorithm(), getJCAContext().getProvider());

		try {
			verifier.initVerify(publicKey);

		} catch (InvalidKeyException e) {
			throw new JOSEException("Invalid public RSA key: " + e.getMessage(), e);
		}

		try {
			verifier.update(signedContent);
			return verifier.verify(signature.decode());

		} catch (SignatureException e) {
			return false;
		}
	}
}
