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

package com.nimbusds.jose.jwk.gen;


import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;


/**
 * Elliptic Curve (EC) JSON Web Key (JWK) generator.
 *
 * <p>Supported curves:
 *
 * <ul>
 *     <li>{@link Curve#P_256 P-256}
 *     <li>{@link Curve#P_256K P-256K}
 *     <li>{@link Curve#P_384 P-384}
 *     <li>{@link Curve#P_521 P-512}
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 * @version 2018-07-15
 */
public class ECKeyGenerator extends JWKGenerator<ECKey> {
	
	
	/**
	 * The curve.
	 */
	private final Curve crv;
	
	
	/**
	 * Creates a new EC JWK generator.
	 *
	 * @param crv The curve. Must not be {@code null}.
	 */
	public ECKeyGenerator(final Curve crv) {
	
		if (crv == null) {
			throw new IllegalArgumentException("The curve must not be null");
		}
		this.crv = crv;
	}
	
	
	@Override
	public ECKey generate()
		throws JOSEException  {
		
		ECParameterSpec ecSpec = crv.toECParameterSpec();
		
		KeyPairGenerator generator;
		try {
			if (keyStore != null) {
				// For PKCS#11
				generator = KeyPairGenerator.getInstance("EC", keyStore.getProvider());
			} else {
				generator = KeyPairGenerator.getInstance("EC");
			}
			generator.initialize(ecSpec);
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			throw new JOSEException(e.getMessage(), e);
		}
		
		KeyPair kp = generator.generateKeyPair();
		
		ECPublicKey pub = (ECPublicKey) kp.getPublic();
		ECPrivateKey priv = (ECPrivateKey) kp.getPrivate();
		
		ECKey.Builder builder = new ECKey.Builder(crv, pub)
			.privateKey(priv)
			.keyUse(use)
			.keyOperations(ops)
			.algorithm(alg)
			.keyStore(keyStore);
		
		if (x5tKid) {
			builder.keyIDFromThumbprint();
		} else {
			builder.keyID(kid);
		}
		
		return builder.build();
	}
}
