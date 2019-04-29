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

package com.nimbusds.jose;


import com.nimbusds.jose.util.ArrayUtils;
import net.jcip.annotations.Immutable;


/**
 * JSON Web Signature (JWS) algorithm name, represents the {@code alg} header
 * parameter in JWS objects. Also used to represent integrity algorithm 
 * ({@code ia}) header parameters in JWE objects. This class is immutable.
 *
 * <p>Includes constants for the following standard JWS algorithm names:
 *
 * <ul>
 *     <li>{@link #HS256}
 *     <li>{@link #HS384}
 *     <li>{@link #HS512}
 *     <li>{@link #RS256}
 *     <li>{@link #RS384}
 *     <li>{@link #RS512}
 *     <li>{@link #ES256}
 *     <li>{@link #ES384}
 *     <li>{@link #ES512}
 *     <li>{@link #PS256}
 *     <li>{@link #PS384}
 *     <li>{@link #PS512}
 *     <li>{@link #EdDSA}
 *     <li>{@link #ES256K} (non-standard)
 * </ul>
 *
 * <p>Additional JWS algorithm names can be defined using the constructors.
 *
 * @author Vladimir Dzhuvinov
 * @author Aleksei Doroganov
 * @version 2018-03-28
 */
@Immutable
public final class JWSAlgorithm extends Algorithm {


	private static final long serialVersionUID = 1L;


	/**
	 * HMAC using SHA-256 hash algorithm (required).
	 */
	public static final JWSAlgorithm HS256 = new JWSAlgorithm("HS256", Requirement.REQUIRED);


	/**
	 * HMAC using SHA-384 hash algorithm (optional).
	 */
	public static final JWSAlgorithm HS384 = new JWSAlgorithm("HS384", Requirement.OPTIONAL);


	/**
	 * HMAC using SHA-512 hash algorithm (optional).
	 */
	public static final JWSAlgorithm HS512 = new JWSAlgorithm("HS512", Requirement.OPTIONAL);


	/**
	 * RSASSA-PKCS-v1_5 using SHA-256 hash algorithm (recommended).
	 */
	public static final JWSAlgorithm RS256 = new JWSAlgorithm("RS256", Requirement.RECOMMENDED);


	/**
	 * RSASSA-PKCS-v1_5 using SHA-384 hash algorithm (optional).
	 */
	public static final JWSAlgorithm RS384 = new JWSAlgorithm("RS384", Requirement.OPTIONAL);


	/**
	 * RSASSA-PKCS-v1_5 using SHA-512 hash algorithm (optional).
	 */
	public static final JWSAlgorithm RS512 = new JWSAlgorithm("RS512", Requirement.OPTIONAL);


	/**
	 * ECDSA using P-256 (secp256r1) curve and SHA-256 hash algorithm
	 * (recommended).
	 */
	public static final JWSAlgorithm ES256 = new JWSAlgorithm("ES256", Requirement.RECOMMENDED);

	
	/**
	 * ECDSA using P-256K (secp256k1) curve and SHA-256 hash algorithm
	 * (optional).
	 */
	public static final JWSAlgorithm ES256K = new JWSAlgorithm("ES256K", Requirement.OPTIONAL);


	/**
	 * ECDSA using P-384 curve and SHA-384 hash algorithm (optional).
	 */
	public static final JWSAlgorithm ES384 = new JWSAlgorithm("ES384", Requirement.OPTIONAL);


	/**
	 * ECDSA using P-521 curve and SHA-512 hash algorithm (optional).
	 */
	public static final JWSAlgorithm ES512 = new JWSAlgorithm("ES512", Requirement.OPTIONAL);


	/**
	 * RSASSA-PSS using SHA-256 hash algorithm and MGF1 mask generation
	 * function with SHA-256 (optional).
	 */
	public static final JWSAlgorithm PS256 = new JWSAlgorithm("PS256", Requirement.OPTIONAL);


	/**
	 * RSASSA-PSS using SHA-384 hash algorithm and MGF1 mask generation
	 * function with SHA-384 (optional).
	 */
	public static final JWSAlgorithm PS384 = new JWSAlgorithm("PS384", Requirement.OPTIONAL);


	/**
	 * RSASSA-PSS using SHA-512 hash algorithm and MGF1 mask generation
	 * function with SHA-512 (optional).
	 */
	public static final JWSAlgorithm PS512 = new JWSAlgorithm("PS512", Requirement.OPTIONAL);
	
	
	/**
	 * EdDSA signature algorithms (optional).
	 */
	public static final JWSAlgorithm EdDSA = new JWSAlgorithm("EdDSA", Requirement.OPTIONAL);


	/**
	 * JWS algorithm family.
	 */
	public static final class Family extends AlgorithmFamily<JWSAlgorithm> {


		private static final long serialVersionUID = 1L;


		/**
		 * HMAC using a SHA-2 hash.
		 */
		public static final Family HMAC_SHA = new Family(HS256, HS384, HS512);


		/**
		 * RSA signature (RSASSA-PKCS-v1_5 or RSASSA-PSS) using a SHA-2
		 * hash.
		 */
		public static final Family RSA = new Family(RS256, RS384, RS512, PS256, PS384, PS512);


		/**
		 * Elliptic Curve signature (ECDSA) using a SHA-2 hash.
		 */
		public static final Family EC = new Family(ES256, ES256K, ES384, ES512);
		
		
		/**
		 * Edwards Curve signature (EdDSA).
		 */
		public static final Family ED = new Family(EdDSA);
		
		
		/**
		 * Super family of all digital signature based JWS algorithms.
		 */
		public static final Family SIGNATURE = new Family(ArrayUtils
			.concat(
				RSA.toArray(new JWSAlgorithm[]{}),
				EC.toArray(new JWSAlgorithm[]{}),
				ED.toArray(new JWSAlgorithm[]{})
			)
		);


		/***
		 * Creates a new JWS algorithm family.
		 *
		 * @param algs The JWS algorithms of the family. Must not be
		 *             {@code null}.
		 */
		public Family(final JWSAlgorithm ... algs) {
			super(algs);
		}
	}


	/**
	 * Creates a new JSON Web Signature (JWS) algorithm name.
	 *
	 * @param name The algorithm name. Must not be {@code null}.
	 * @param req  The implementation requirement, {@code null} if not 
	 *             known.
	 */
	public JWSAlgorithm(final String name, final Requirement req) {

		super(name, req);
	}


	/**
	 * Creates a new JSON Web Signature (JWS) algorithm name.
	 *
	 * @param name The algorithm name. Must not be {@code null}.
	 */
	public JWSAlgorithm(final String name) {

		super(name, null);
	}


	/**
	 * Parses a JWS algorithm from the specified string.
	 *
	 * @param s The string to parse. Must not be {@code null}.
	 *
	 * @return The JWS algorithm (matching standard algorithm constant, else
	 *         a newly created algorithm).
	 */
	public static JWSAlgorithm parse(final String s) {

		if (s.equals(HS256.getName())) {
			return HS256;
		} else if (s.equals(HS384.getName())) {
			return HS384;
		} else if (s.equals(HS512.getName())) {
			return HS512;
		} else if (s.equals(RS256.getName())) {
			return RS256;
		} else if (s.equals(RS384.getName())) {
			return RS384;
		} else if (s.equals(RS512.getName())) {
			return RS512;
		} else if (s.equals(ES256.getName())) {
			return ES256;
		} else if (s.equals(ES256K.getName())) {
			return ES256K;
		} else if (s.equals(ES384.getName())) {
			return ES384;
		} else if (s.equals(ES512.getName())) {
			return ES512;
		} else if (s.equals(PS256.getName())) {
			return PS256;
		} else if (s.equals(PS384.getName())) {
			return PS384;
		} else if (s.equals(PS512.getName())) {
			return PS512;
		} else if (s.equals(EdDSA.getName())) {
			return EdDSA;
		} else {
			return new JWSAlgorithm(s);
		}
	}
}
