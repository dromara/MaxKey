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

package com.nimbusds.jose.crypto.factories;


import java.security.Key;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.crypto.SecretKey;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jca.JCAContext;
import com.nimbusds.jose.proc.JWSVerifierFactory;
import net.jcip.annotations.ThreadSafe;


/**
 * Default JSON Web Signature (JWS) verifier factory.
 *
 * <p>Supports all standard JWS algorithms implemented in the
 * {@link com.nimbusds.jose.crypto} package.
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-11-16
 */
@ThreadSafe
public class DefaultJWSVerifierFactory implements JWSVerifierFactory {


	/**
	 * The supported JWS algorithms.
	 */
	public static final Set<JWSAlgorithm> SUPPORTED_ALGORITHMS;


	static {
		Set<JWSAlgorithm> algs = new LinkedHashSet<>();
		algs.addAll(MACVerifier.SUPPORTED_ALGORITHMS);
		algs.addAll(RSASSAVerifier.SUPPORTED_ALGORITHMS);
		algs.addAll(ECDSAVerifier.SUPPORTED_ALGORITHMS);
		SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
	}


	/**
	 * The JCA context.
	 */
	private final JCAContext jcaContext = new JCAContext();


	@Override
	public Set<JWSAlgorithm> supportedJWSAlgorithms() {

		return SUPPORTED_ALGORITHMS;
	}


	@Override
	public JCAContext getJCAContext() {

		return jcaContext;
	}


	@Override
	public JWSVerifier createJWSVerifier(final JWSHeader header, final Key key)
		throws JOSEException {

		JWSVerifier verifier;

		if (MACVerifier.SUPPORTED_ALGORITHMS.contains(header.getAlgorithm())) {

			if (!(key instanceof SecretKey)) {
				throw new KeyTypeException(SecretKey.class);
			}

			SecretKey macKey = (SecretKey)key;

			verifier = new MACVerifier(macKey);

		} else if (RSASSAVerifier.SUPPORTED_ALGORITHMS.contains(header.getAlgorithm())) {

			if (!(key instanceof RSAPublicKey)) {
				throw new KeyTypeException(RSAPublicKey.class);
			}

			RSAPublicKey rsaPublicKey = (RSAPublicKey)key;

			verifier = new RSASSAVerifier(rsaPublicKey);

		} else if (ECDSAVerifier.SUPPORTED_ALGORITHMS.contains(header.getAlgorithm())) {

			if (!(key instanceof ECPublicKey)) {
				throw new KeyTypeException(ECPublicKey.class);
			}

			ECPublicKey ecPublicKey = (ECPublicKey)key;

			verifier = new ECDSAVerifier(ecPublicKey);

		} else {

			throw new JOSEException("Unsupported JWS algorithm: " + header.getAlgorithm());
		}

		// Apply JCA context
		verifier.getJCAContext().setSecureRandom(jcaContext.getSecureRandom());
		verifier.getJCAContext().setProvider(jcaContext.getProvider());

		return verifier;
	}
}
