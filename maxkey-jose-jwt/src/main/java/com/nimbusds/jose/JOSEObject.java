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


import java.io.Serializable;
import java.text.ParseException;

import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.JSONObjectUtils;
import net.minidev.json.JSONObject;


/**
 * The base abstract class for unsecured (plain / {@code alg=none}), JSON Web
 * Signature (JWS) secured and JSON Web Encryption (JWE) secured objects.
 *
 * @author Vladimir Dzhuvinov
 * @version 2017-07-11
 */
public abstract class JOSEObject implements Serializable {
	
	
	private static final long serialVersionUID = 1L;


	/**
	 * The MIME type of JOSE objects serialised to a compact form:
	 * {@code application/jose; charset=UTF-8}
	 */
	public static final String MIME_TYPE_COMPACT = "application/jose; charset=UTF-8";


	/**
	 * The MIME type of JOSE objects serialised to a JSON object form:
	 * {@code application/jose+json; charset=UTF-8}
	 */
	public static final String MIME_TYPE_JS = "application/jose+json; charset=UTF-8";


	/**
	 * The payload (message), {@code null} if not specified.
	 */
	private Payload payload;


	/**
	 * The original parsed Base64URL parts, {@code null} if the JOSE object 
	 * was created from scratch. The individual parts may be empty or 
	 * {@code null} to indicate a missing part.
	 */
	private Base64URL[] parsedParts;


	/**
	 * Creates a new JOSE object. The payload and the original parsed 
	 * Base64URL parts are not defined.
	 */
	protected JOSEObject() {

		payload = null;
		parsedParts = null;
	}


	/**
	 * Creates a new JOSE object with the specified payload.
	 *
	 * @param payload The payload, {@code null} if not available (e.g for 
	 *                an encrypted JWE object).
	 */
	protected JOSEObject(final Payload payload) {

		this.payload = payload;
	}


	/**
	 * Returns the header of this JOSE object.
	 *
	 * @return The header.
	 */
	public abstract Header getHeader();


	/**
	 * Sets the payload of this JOSE object.
	 *
	 * @param payload The payload, {@code null} if not available (e.g. for 
	 *                an encrypted JWE object).
	 */
	protected void setPayload(final Payload payload) {

		this.payload = payload;
	}


	/**
	 * Returns the payload of this JOSE object.
	 *
	 * @return The payload, {@code null} if not available (for an encrypted
	 *         JWE object that hasn't been decrypted).
	 */
	public Payload getPayload() {

		return payload;
	}


	/**
	 * Sets the original parsed Base64URL parts used to create this JOSE 
	 * object.
	 *
	 * @param parts The original Base64URL parts used to creates this JOSE
	 *              object, {@code null} if the object was created from
	 *              scratch. The individual parts may be empty or 
	 *              {@code null} to indicate a missing part.
	 */
	protected void setParsedParts(final Base64URL... parts) {

		parsedParts = parts;
	}


	/**
	 * Returns the original parsed Base64URL parts used to create this JOSE
	 * object.
	 *
	 * @return The original Base64URL parts used to creates this JOSE 
	 *         object, {@code null} if the object was created from scratch. 
	 *         The individual parts may be empty or {@code null} to 
	 *         indicate a missing part.
	 */
	public Base64URL[] getParsedParts() {

		return parsedParts;
	}


	/**
	 * Returns the original parsed string used to create this JOSE object.
	 *
	 * @see #getParsedParts
	 * 
	 * @return The parsed string used to create this JOSE object, 
	 *         {@code null} if the object was creates from scratch.
	 */
	public String getParsedString() {

		if (parsedParts == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (Base64URL part: parsedParts) {

			if (sb.length() > 0) {
				sb.append('.');
			}

			if (part != null) {
				sb.append(part.toString());
			}
		}

		return sb.toString();
	}


	/**
	 * Serialises this JOSE object to its compact format consisting of 
	 * Base64URL-encoded parts delimited by period ('.') characters.
	 *
	 * @return The serialised JOSE object.
	 *
	 * @throws IllegalStateException If the JOSE object is not in a state 
	 *                               that permits serialisation.
	 */
	public abstract String serialize();


	/**
	 * Splits a compact serialised JOSE object into its Base64URL-encoded
	 * parts.
	 *
	 * @param s The compact serialised JOSE object to split. Must not be
	 *          {@code null}.
	 *
	 * @return The JOSE Base64URL-encoded parts (three for unsecured and
	 *         JWS objects, five for JWE objects).
	 *
	 * @throws ParseException If the specified string couldn't be split 
	 *                        into three or five Base64URL-encoded parts.
	 */
	public static Base64URL[] split(final String s)
		throws ParseException {

		final String t = s.trim();
		
		// We must have 2 (JWS) or 4 dots (JWE)

		// String.split() cannot handle empty parts
		final int dot1 = t.indexOf(".");

		if (dot1 == -1) {
			throw new ParseException("Invalid serialized unsecured/JWS/JWE object: Missing part delimiters", 0);
		}

		final int dot2 = t.indexOf(".", dot1 + 1);

		if (dot2 == -1) {
			throw new ParseException("Invalid serialized unsecured/JWS/JWE object: Missing second delimiter", 0);
		}

		// Third dot for JWE only
		final int dot3 = t.indexOf(".", dot2 + 1);

		if (dot3 == -1) {

			// Two dots only? -> We have a JWS
			Base64URL[] parts = new Base64URL[3];
			parts[0] = new Base64URL(t.substring(0, dot1));
			parts[1] = new Base64URL(t.substring(dot1 + 1, dot2));
			parts[2] = new Base64URL(t.substring(dot2 + 1));
			return parts;
		}

		// Fourth final dot for JWE
		final int dot4 = t.indexOf(".", dot3 + 1);

		if (dot4 == -1) {
			throw new ParseException("Invalid serialized JWE object: Missing fourth delimiter", 0);
		}

		if (dot4 != -1 && t.indexOf(".", dot4 + 1) != -1) {
			throw new ParseException("Invalid serialized unsecured/JWS/JWE object: Too many part delimiters", 0);
		}

		// Four dots -> five parts
		Base64URL[] parts = new Base64URL[5];
		parts[0] = new Base64URL(t.substring(0, dot1));
		parts[1] = new Base64URL(t.substring(dot1 + 1, dot2));
		parts[2] = new Base64URL(t.substring(dot2 + 1, dot3));
		parts[3] = new Base64URL(t.substring(dot3 + 1, dot4));
		parts[4] = new Base64URL(t.substring(dot4 + 1));
		return parts;
	}


	/**
	 * Parses a JOSE object from the specified string in compact format.
	 *
	 * @param s The string to parse. Must not be {@code null}.
	 *
	 * @return The corresponding {@link PlainObject}, {@link JWSObject} or
	 *         {@link JWEObject} instance.
	 *
	 * @throws ParseException If the string couldn't be parsed to a valid 
	 *                        unsecured, JWS or JWE object.
	 */
	public static JOSEObject parse(final String s) 
		throws ParseException {

		Base64URL[] parts = split(s);

		JSONObject jsonObject;

		try {
			jsonObject = JSONObjectUtils.parse(parts[0].decodeToString());

		} catch (ParseException e) {

			throw new ParseException("Invalid unsecured/JWS/JWE header: " + e.getMessage(), 0);
		}

		Algorithm alg = Header.parseAlgorithm(jsonObject);

		if (alg.equals(Algorithm.NONE)) {
			return PlainObject.parse(s);
		} else if (alg instanceof JWSAlgorithm) {
			return JWSObject.parse(s);
		} else if (alg instanceof JWEAlgorithm) {
			return JWEObject.parse(s);
		} else {
			throw new AssertionError("Unexpected algorithm type: " + alg);
		}
	}
}
