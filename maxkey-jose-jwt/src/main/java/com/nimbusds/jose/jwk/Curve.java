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

package com.nimbusds.jose.jwk;


import java.io.Serializable;
import java.security.spec.ECParameterSpec;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.nimbusds.jose.JWSAlgorithm;
import net.jcip.annotations.Immutable;


/**
 * Cryptographic curve. This class is immutable.
 *
 * <p>Includes constants for the following standard cryptographic curves:
 *
 * <ul>
 *     <li>{@link #P_256}
 *     <li>{@link #P_256K}
 *     <li>{@link #P_384}
 *     <li>{@link #P_521}
 *     <li>{@link #Ed25519}
 *     <li>{@link #Ed448}
 *     <li>{@link #X25519}
 *     <li>{@link #X448}
 * </ul>
 *
 * <p>See
 *
 * <ul>
 *     <li>"Digital Signature Standard (DSS)", FIPS PUB 186-3, June 2009,
 *         National Institute of Standards and Technology (NIST).
 *     <li>CFRG Elliptic Curve Diffie-Hellman (ECDH) and Signatures in JSON
 *         Object Signing and Encryption (JOSE) (RFC 8037).
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 * @author Aleksei Doroganov
 * @version 2013-03-28
 */
@Immutable
public final class Curve implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * P-256 curve (secp256r1, also called prime256v1, OID =
	 * 1.2.840.10045.3.1.7).
	 */
	public static final Curve P_256 = new Curve("P-256", "secp256r1", "1.2.840.10045.3.1.7");
	
	
	/**
	 * P-256K curve (secp256k1, OID = 1.3.132.0.10).
	 */
	public static final Curve P_256K = new Curve("P-256K", "secp256k1", "1.3.132.0.10");
	
	
	/**
	 * P-384 curve (secp384r1, OID = 1.3.132.0.34).
	 */
	public static final Curve P_384 = new Curve("P-384", "secp384r1", "1.3.132.0.34");
	
	
	/**
	 * P-521 curve (secp521r1).
	 */
	public static final Curve P_521 = new Curve("P-521", "secp521r1", "1.3.132.0.35");
	
	
	/**
	 * Ed25519 signature algorithm key pairs.
	 */
	public static final Curve Ed25519 = new Curve("Ed25519", "Ed25519", null);
	
	
	/**
	 * Ed448 signature algorithm key pairs.
	 */
	public static final Curve Ed448 = new Curve("Ed448", "Ed448", null);
	
	
	/**
	 * X25519 function key pairs.
	 */
	public static final Curve X25519 = new Curve("X25519", "X25519", null);
	
	
	/**
	 * X448 function key pairs.
	 */
	public static final Curve X448 = new Curve("X448", "X448", null);
	
	
	/**
	 * The JOSE curve name.
	 */
	private final String name;
	
	
	/**
	 * The standard curve name, {@code null} if not specified.
	 */
	private final String stdName;
	
	
	/**
	 * The standard object identifier for the curve, {@code null}
	 * if not specified.
	 */
	private final String oid;
	
	
	/**
	 * Creates a new cryptographic curve with the specified JOSE name. A
	 * standard curve name and object identifier (OID) are not unspecified.
	 *
	 * @param name The JOSE name of the cryptographic curve. Must not be
	 *             {@code null}.
	 */
	public Curve(final String name) {
		
		this(name, null, null);
	}
	
	
	/**
	 * Creates a new cryptographic curve with the specified JOSE name,
	 * standard name and object identifier (OID).
	 *
	 * @param name    The JOSE name of the cryptographic curve. Must not
	 *                be {@code null}.
	 * @param stdName The standard name of the cryptographic curve,
	 *                {@code null} if not specified.
	 * @param oid     The object identifier (OID) of the cryptographic
	 *                curve, {@code null} if not specified.
	 */
	public Curve(final String name, final String stdName, final String oid) {
		
		if (name == null) {
			throw new IllegalArgumentException("The JOSE cryptographic curve name must not be null");
		}
		
		this.name = name;
		
		this.stdName = stdName;
		
		this.oid = oid;
	}
	
	
	/**
	 * Returns the JOSE name of this cryptographic curve.
	 *
	 * @return The JOSE name.
	 */
	public String getName() {
		
		return name;
	}
	
	
	/**
	 * Returns the standard name of this cryptographic curve.
	 *
	 * @return The standard name, {@code null} if not specified.
	 */
	public String getStdName() {
		
		return stdName;
	}
	
	
	/**
	 * Returns the standard object identifier (OID) of this cryptographic
	 * curve.
	 *
	 * @return The OID, {@code null} if not specified.
	 */
	public String getOID() {
		
		return oid;
	}
	
	
	/**
	 * Returns the parameter specification for this cryptographic curve.
	 *
	 * @return The EC parameter specification, {@code null} if it cannot be
	 *         determined.
	 */
	public ECParameterSpec toECParameterSpec() {
		
		return ECParameterTable.get(this);
	}
	
	
	/**
	 * @see #getName
	 */
	@Override
	public String toString() {
		
		return getName();
	}
	
	
	@Override
	public boolean equals(final Object object) {
		
		return object instanceof Curve &&
			this.toString().equals(object.toString());
	}
	
	
	/**
	 * Parses a cryptographic curve from the specified string.
	 *
	 * @param s The string to parse. Must not be {@code null} or empty.
	 *
	 * @return The cryptographic curve.
	 */
	public static Curve parse(final String s) {
		
		if (s == null || s.trim().isEmpty()) {
			throw new IllegalArgumentException("The cryptographic curve string must not be null or empty");
		}
		
		if (s.equals(P_256.getName())) {
			return P_256;
		} else if (s.equals(P_256K.getName())) {
			return P_256K;
		} else if (s.equals(P_384.getName())) {
			return P_384;
		} else if (s.equals(P_521.getName())) {
			return P_521;
		} else if (s.equals(Ed25519.getName())) {
			return Ed25519;
		} else if (s.equals(Ed448.getName())) {
			return Ed448;
		} else if (s.equals(X25519.getName())) {
			return X25519;
		} else if (s.equals(X448.getName())) {
			return X448;
		} else {
			return new Curve(s);
		}
	}
	
	
	/**
	 * Gets the cryptographic curve for the specified standard
	 * name.
	 *
	 * @param stdName The standard curve name. May be {@code null}.
	 *
	 * @return The curve, {@code null} if it cannot be determined.
	 */
	public static Curve forStdName(final String stdName) {
		if( "secp256r1".equals(stdName) || "prime256v1".equals(stdName)) {
			return P_256;
		} else if("secp256k1".equals(stdName)) {
			return P_256K;
		} else if("secp384r1".equals(stdName)) {
			return P_384;
		} else if("secp521r1".equals(stdName)) {
			return P_521;
		} else if (Ed25519.getStdName().equals(stdName)) {
			return Ed25519;
		} else if (Ed448.getStdName().equals(stdName)) {
			return Ed448;
		} else if (X25519.getStdName().equals(stdName)) {
			return X25519;
		} else if (X448.getStdName().equals(stdName)) {
			return X448;
		} else {
			return null;
		}
	}
	
	
	/**
	 * Gets the cryptographic curve for the specified object identifier
	 * (OID).
	 *
	 * @param oid The object OID. May be {@code null}.
	 *
	 * @return The curve, {@code null} if it cannot be determined.
	 */
	public static Curve forOID(final String oid) {
		
		if (P_256.getOID().equals(oid)) {
			return P_256;
		} else if (P_256K.getOID().equals(oid)) {
			return P_256K;
		} else if (P_384.getOID().equals(oid)) {
			return P_384;
		} else if (P_521.getOID().equals(oid)) {
			return P_521;
		} else {
			return null;
		}
	}
	
	
	/**
	 * Gets the cryptographic curve(s) for the specified JWS algorithm.
	 *
	 * @param alg The JWS algorithm. May be {@code null}.
	 *
	 * @return The curve(s), {@code null} if the JWS algorithm is not curve
	 *         based, or the JWS algorithm is not supported.
	 */
	public static Set<Curve> forJWSAlgorithm(final JWSAlgorithm alg) {
		
		if (JWSAlgorithm.ES256.equals(alg)) {
			return Collections.singleton(P_256);
		} else if (JWSAlgorithm.ES256K.equals(alg)) {
            return Collections.singleton(P_256K);
		} else if (JWSAlgorithm.ES384.equals(alg)) {
			return Collections.singleton(P_384);
		} else if (JWSAlgorithm.ES512.equals(alg)) {
			return Collections.singleton(P_521);
		} else if (JWSAlgorithm.EdDSA.equals(alg)) {
			return Collections.unmodifiableSet(
				new HashSet<>(Arrays.asList(
					Ed25519,
					Ed448
				))
			);
		} else {
			return null;
		}
	}
	
	
	/**
	 * Gets the cryptographic curve for the specified parameter
	 * specification.
	 *
	 * @param spec The EC parameter spec. May be {@code null}.
	 *
	 * @return The curve, {@code null} if it cannot be determined.
	 */
	public static Curve forECParameterSpec(final ECParameterSpec spec) {
		
		return ECParameterTable.get(spec);
	}
}