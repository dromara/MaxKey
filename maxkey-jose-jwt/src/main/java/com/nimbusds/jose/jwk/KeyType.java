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

package com.nimbusds.jose.jwk;


import java.io.Serializable;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.Requirement;
import net.jcip.annotations.Immutable;
import net.minidev.json.JSONAware;
import net.minidev.json.JSONObject;


/**
 * Key type. Represents the {@code kty} parameter in a JSON Web Key (JWK). 
 * This class is immutable.
 *
 * <p>Includes constants for the following standard key types:
 *
 * <ul>
 *     <li>{@link #EC}
 *     <li>{@link #RSA}
 *     <li>{@link #OCT}
 *     <li>{@link #OKP}
 * </ul>
 *
 * <p>Additional key types can be defined using the constructor.
 *
 * @author Vladimir Dzhuvinov
 * @author Justin Richer
 * @version 2017-08-23
 */
@Immutable
public final class KeyType implements JSONAware, Serializable {


	private static final long serialVersionUID = 1L;


	/**
	 * The key type value.
	 */
	private final String value;


	/**
	 * The implementation requirement, {@code null} if not known.
	 */
	private final Requirement requirement;


	/**
	 * Elliptic Curve (DSS) key type (recommended).
	 */
	public static final KeyType EC = new KeyType("EC", Requirement.RECOMMENDED);


	/**
	 * RSA (RFC 3447) key type (required).
	 */
	public static final KeyType RSA = new KeyType("RSA", Requirement.REQUIRED);


	/**
	 * Octet sequence key type (optional).
	 */
	public static final KeyType OCT = new KeyType("oct", Requirement.OPTIONAL);
	
	
	/**
	 * Octet key pair (optional).
	 */
	public static final KeyType OKP = new KeyType("OKP", Requirement.OPTIONAL);
	

	/**
	 * Creates a new key type with the specified value and implementation 
	 * requirement.
	 *
	 * @param value The key type value. Values are case sensitive. Must not
	 *              be {@code null}.
	 * @param req   The implementation requirement, {@code null} if not 
	 *              known.
	 */
	public KeyType(final String value, final Requirement req) {

		if (value == null) {

			throw new IllegalArgumentException("The key type value must not be null");
		}

		this.value = value;

		requirement = req;
	}


	/**
	 * Gets the value of this key type. Values are case sensitive.
	 *
	 * @return The key type.
	 */
	public String getValue() {

		return value;
	}


	/**
	 * Gets the implementation requirement of this key type.
	 *
	 * @return The implementation requirement, {@code null} if not known.
	 */
	public Requirement getRequirement() {

		return requirement;
	}


	/**
	 * Overrides {@code Object.hashCode()}.
	 *
	 * @return The object hash code.
	 */
	@Override
	public int hashCode() {

		return value.hashCode();
	}


	/**
	 * Overrides {@code Object.equals()}.
	 *
	 * @param object The object to compare to.
	 *
	 * @return {@code true} if the objects have the same value, otherwise
	 *         {@code false}.
	 */
	@Override
	public boolean equals(final Object object) {

		return object != null && 
		       object instanceof KeyType && 
		       this.toString().equals(object.toString());
	}


	/**
	 * Returns the string representation of this key type.
	 *
	 * @see #getValue
	 *
	 * @return The string representation.
	 */
	@Override
	public String toString() {

		return value;
	}


	/**
	 * Returns the JSON string representation of this key type.
	 * 
	 * @return The JSON string representation.
	 */
	@Override
	public String toJSONString() {

		return "\"" + JSONObject.escape(value) + '"';
	}


	/**
	 * Parses a key type from the specified {@code kty} parameter value.
	 *
	 * @param s The string to parse. Must not be {@code null}.
	 *
	 * @return The key type (matching standard key type constant, else a 
	 *         newly created one).
	 */
	public static KeyType parse(final String s) {

		if (s.equals(EC.getValue())) {
			return EC;
		} else if (s.equals(RSA.getValue())) {
			return RSA;
		} else if (s.equals(OCT.getValue())) {
			return OCT;
		} else if (s.equals(OKP.getValue())) {
			return OKP;
		} else {
			return new KeyType(s, null);
		}
	}


	/**
	 * Infers the key type for the specified JOSE algorithm.
	 *
	 * @param alg The JOSE algorithm. May be {@code null}.
	 *
	 * @return The key type, {@code null} if it couldn't be inferred.
	 */
	public static KeyType forAlgorithm(final Algorithm alg) {

		if (alg == null) {
			return null;
		}

		if (JWSAlgorithm.Family.RSA.contains(alg)) {
			return KeyType.RSA;
		} else if (JWSAlgorithm.Family.EC.contains(alg)) {
			return KeyType.EC;
		} else if (JWSAlgorithm.Family.HMAC_SHA.contains(alg)) {
			return KeyType.OCT;
		} else if (JWEAlgorithm.Family.RSA.contains(alg)) {
			return KeyType.RSA;
		} else if (JWEAlgorithm.Family.ECDH_ES.contains(alg)) {
			return KeyType.EC;
		} else if (JWEAlgorithm.DIR.equals(alg)) {
			return KeyType.OCT;
		} else if (JWEAlgorithm.Family.AES_GCM_KW.contains(alg)) {
			return KeyType.OCT;
		} else if (JWEAlgorithm.Family.AES_KW.contains(alg)) {
			return KeyType.OCT;
		} else if (JWEAlgorithm.Family.PBES2.contains(alg)) {
			return KeyType.OCT;
		} else if (JWSAlgorithm.Family.ED.contains(alg)) {
			return KeyType.OKP;
		} else {
			return null;
		}
	}
}
