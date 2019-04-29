/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2019, Connect2id Ltd.
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

package com.nimbusds.jose.proc;


import java.security.Key;
import java.security.PublicKey;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.crypto.SecretKey;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.source.JWKSource;
import net.jcip.annotations.ThreadSafe;


/**
 * Key selector for verifying JWS objects, where the key candidates are
 * retrieved from a {@link JWKSource JSON Web Key (JWK) source}.
 *
 * @author Vladimir Dzhuvinov
 * @version 2016-06-21
 */
@ThreadSafe
public class JWSVerificationKeySelector<C extends SecurityContext> extends AbstractJWKSelectorWithSource<C> implements JWSKeySelector<C> {


	/**
	 * The expected JWS algorithm.
	 */
	private final JWSAlgorithm jwsAlg;


	/**
	 * Creates a new JWS verification key selector.
	 *
	 * @param jwsAlg    The expected JWS algorithm for the objects to be
	 *                  verified. Must not be {@code null}.
	 * @param jwkSource The JWK source. Must not be {@code null}.
	 */
	public JWSVerificationKeySelector(final JWSAlgorithm jwsAlg, final JWKSource<C> jwkSource) {
		super(jwkSource);
		if (jwsAlg == null) {
			throw new IllegalArgumentException("The JWS algorithm must not be null");
		}
		this.jwsAlg = jwsAlg;
	}


	/**
	 * Returns the expected JWS algorithm.
	 *
	 * @return The expected JWS algorithm.
	 */
	public JWSAlgorithm getExpectedJWSAlgorithm() {

		return jwsAlg;
	}


	/**
	 * Creates a JWK matcher for the expected JWS algorithm and the
	 * specified JWS header.
	 *
	 * @param jwsHeader The JWS header. Must not be {@code null}.
	 *
	 * @return The JWK matcher, {@code null} if none could be created.
	 */
	protected JWKMatcher createJWKMatcher(final JWSHeader jwsHeader) {

		if (! getExpectedJWSAlgorithm().equals(jwsHeader.getAlgorithm())) {
			// Unexpected JWS alg
			return null;
		} else {
			return JWKMatcher.forJWSHeader(jwsHeader);
		}
	}


	@Override
	public List<Key> selectJWSKeys(final JWSHeader jwsHeader, final C context)
		throws KeySourceException {

		if (! jwsAlg.equals(jwsHeader.getAlgorithm())) {
			// Unexpected JWS alg
			return Collections.emptyList();
		}

		JWKMatcher jwkMatcher = createJWKMatcher(jwsHeader);
		if (jwkMatcher == null) {
			return Collections.emptyList();
		}

		List<JWK> jwkMatches = getJWKSource().get(new JWKSelector(jwkMatcher), context);

		List<Key> sanitizedKeyList = new LinkedList<>();

		for (Key key: KeyConverter.toJavaKeys(jwkMatches)) {
			if (key instanceof PublicKey || key instanceof SecretKey) {
				sanitizedKeyList.add(key);
			} // skip asymmetric private keys
		}

		return sanitizedKeyList;
	}
}
