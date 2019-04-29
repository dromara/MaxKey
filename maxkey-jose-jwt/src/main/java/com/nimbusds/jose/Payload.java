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
import com.nimbusds.jose.util.StandardCharset;
import com.nimbusds.jwt.SignedJWT;
import net.jcip.annotations.Immutable;
import net.minidev.json.JSONObject;


/**
 * Payload of an unsecured (plain), JSON Web Signature (JWS) or JSON Web
 * Encryption (JWE) object. Supports JSON object, string, byte array,
 * Base64URL, JWS object and signed JWT payload representations. This class is
 * immutable.
 *
 * <p>UTF-8 is the character set for all conversions between strings and byte
 * arrays.
 *
 * <p>Conversion relations:
 *
 * <pre>
 * JSONObject &lt;=&gt; String &lt;=&gt; Base64URL
 *                       &lt;=&gt; byte[]
 *                       &lt;=&gt; JWSObject
 *                       &lt;=&gt; SignedJWT
 * </pre>
 *
 * @author Vladimir Dzhuvinov
 * @version 2016-07-26
 */
@Immutable
public final class Payload implements Serializable {


	/**
	 * Enumeration of the original data types used to create a 
	 * {@link Payload}.
	 */
	public enum Origin {


		/**
		 * The payload was created from a JSON object.
		 */
		JSON,


		/**
		 * The payload was created from a string.
		 */
		STRING,


		/**
		 * The payload was created from a byte array.
		 */
		BYTE_ARRAY,


		/**
		 * The payload was created from a Base64URL-encoded object.
		 */
		BASE64URL,


		/**
		 * The payload was created from a JWS object.
		 */
		JWS_OBJECT,


		/**
		 * The payload was created from a signed JSON Web Token (JWT).
		 */
		SIGNED_JWT
	}


	private static final long serialVersionUID = 1L;


	/**
	 * The original payload data type.
	 */
	private final Origin origin;


	/**
	 * The JSON object representation.
	 */
	private final JSONObject jsonObject;


	/**
	 * The string representation.
	 */
	private final String string;


	/**
	 * The byte array representation.
	 */
	private final byte[] bytes;


	/**
	 * The Base64URL representation.
	 */
	private final Base64URL base64URL;


	/**
	 * The JWS object representation.
	 */
	private final JWSObject jwsObject;


	/**
	 * The signed JWT representation.
	 */
	private final SignedJWT signedJWT;


	/**
	 * Converts a byte array to a string using {@code UTF-8}.
	 *
	 * @param bytes The byte array to convert. May be {@code null}.
	 *
	 * @return The resulting string, {@code null} if conversion failed.
	 */
	private static String byteArrayToString(final byte[] bytes) {

		return bytes != null ? new String(bytes, StandardCharset.UTF_8) : null;
	}


	/**
	 * Converts a string to a byte array using {@code UTF-8}.
	 *
	 * @param string The string to convert. May be {@code null}.
	 *
	 * @return The resulting byte array, {@code null} if conversion failed.
	 */
	private static byte[] stringToByteArray(final String string) {

		return string != null ? string.getBytes(StandardCharset.UTF_8) : null;
	}


	/**
	 * Creates a new payload from the specified JSON object.
	 *
	 * @param jsonObject The JSON object representing the payload. Must not
	 *                   be {@code null}.
	 */
	public Payload(final JSONObject jsonObject) {

		if (jsonObject == null) {
			throw new IllegalArgumentException("The JSON object must not be null");
		}

		this.jsonObject = jsonObject;
		string = null;
		bytes = null;
		base64URL = null;
		jwsObject = null;
		signedJWT = null;

		origin = Origin.JSON;
	}


	/**
	 * Creates a new payload from the specified string.
	 *
	 * @param string The string representing the payload. Must not be 
	 *               {@code null}.
	 */
	public Payload(final String string) {

		if (string == null) {
			throw new IllegalArgumentException("The string must not be null");
		}

		jsonObject = null;
		this.string = string;
		bytes = null;
		base64URL = null;
		jwsObject = null;
		signedJWT = null;

		origin = Origin.STRING;
	}


	/**
	 * Creates a new payload from the specified byte array.
	 *
	 * @param bytes The byte array representing the payload. Must not be 
	 *              {@code null}.
	 */
	public Payload(final byte[] bytes) {

		if (bytes == null) {
			throw new IllegalArgumentException("The byte array must not be null");
		}

		jsonObject = null;
		string = null;
		this.bytes = bytes;
		base64URL = null;
		jwsObject = null;
		signedJWT = null;

		origin = Origin.BYTE_ARRAY;
	}


	/**
	 * Creates a new payload from the specified Base64URL-encoded object.
	 *
	 * @param base64URL The Base64URL-encoded object representing the 
	 *                  payload. Must not be {@code null}.
	 */
	public Payload(final Base64URL base64URL) {

		if (base64URL == null) {
			throw new IllegalArgumentException("The Base64URL-encoded object must not be null");
		}

		jsonObject = null;
		string = null;
		bytes = null;
		this.base64URL = base64URL;
		jwsObject = null;
		signedJWT = null;

		origin = Origin.BASE64URL;
	}


	/**
	 * Creates a new payload from the specified JWS object. Intended for
	 * signed then encrypted JOSE objects.
	 *
	 * @param jwsObject The JWS object representing the payload. Must be in
	 *                  a signed state and not {@code null}.
	 */
	public Payload(final JWSObject jwsObject) {

		if (jwsObject == null) {
			throw new IllegalArgumentException("The JWS object must not be null");
		}

		if (jwsObject.getState() == JWSObject.State.UNSIGNED) {
			throw new IllegalArgumentException("The JWS object must be signed");
		}

		jsonObject = null;
		string = null;
		bytes = null;
		base64URL = null;
		this.jwsObject = jwsObject;
		signedJWT = null;

		origin = Origin.JWS_OBJECT;
	}


	/**
	 * Creates a new payload from the specified signed JSON Web Token
	 * (JWT). Intended for signed then encrypted JWTs.
	 *
	 * @param signedJWT The signed JWT representing the payload. Must be in
	 *                  a signed state and not {@code null}.
	 */
	public Payload(final SignedJWT signedJWT) {

		if (signedJWT == null) {
			throw new IllegalArgumentException("The signed JWT must not be null");
		}

		if (signedJWT.getState() == JWSObject.State.UNSIGNED) {
			throw new IllegalArgumentException("The JWT must be signed");
		}

		jsonObject = null;
		string = null;
		bytes = null;
		base64URL = null;
		this.signedJWT = signedJWT;
		jwsObject = signedJWT; // The signed JWT is also a JWS

		origin = Origin.SIGNED_JWT;
	}


	/**
	 * Gets the original data type used to create this payload.
	 *
	 * @return The payload origin.
	 */
	public Origin getOrigin() {

		return origin;
	}


	/**
	 * Returns a JSON object representation of this payload.
	 *
	 * @return The JSON object representation, {@code null} if the payload
	 *         couldn't be converted to a JSON object.
	 */
	public JSONObject toJSONObject() {

		if (jsonObject != null) {
			return jsonObject;
		}

		// Convert

		String s = toString();

		if (s == null) {
			// to string conversion failed
			return null;
		}

		try {
			return JSONObjectUtils.parse(s);

		} catch (ParseException e) {
			// Payload not a JSON object
			return null;
		}
	}


	/**
	 * Returns a string representation of this payload.
	 *
	 * @return The string representation.
	 */
	@Override
	public String toString() {

		if (string != null) {

			return string;
		}

		// Convert
		if (jwsObject != null) {

			if (jwsObject.getParsedString() != null) {
				return jwsObject.getParsedString();
			} else {
				return jwsObject.serialize();
			}

		} else if (jsonObject != null) {

			return jsonObject.toString();

		} else if (bytes != null) {

			return byteArrayToString(bytes);

		} else if (base64URL != null) {

			return base64URL.decodeToString();
		} else {
			return null; // should never happen
		}
	}


	/**
	 * Returns a byte array representation of this payload.
	 *
	 * @return The byte array representation.
	 */
	public byte[] toBytes() {

		if (bytes != null) {
			return bytes;
		}

		// Convert
		if (base64URL != null) {
			return base64URL.decode();

		}

		return stringToByteArray(toString());
	}


	/**
	 * Returns a Base64URL representation of this payload.
	 *
	 * @return The Base64URL representation.
	 */
	public Base64URL toBase64URL() {

		if (base64URL != null) {
			return base64URL;
		}

		// Convert
		return Base64URL.encode(toBytes());
	}


	/**
	 * Returns a JWS object representation of this payload. Intended for
	 * signed then encrypted JOSE objects.
	 *
	 * @return The JWS object representation, {@code null} if the payload
	 *         couldn't be converted to a JWS object.
	 */
	public JWSObject toJWSObject() {

		if (jwsObject != null) {
			return jwsObject;
		}

		try {
			return JWSObject.parse(toString());

		} catch (ParseException e) {

			return null;
		}
	}


	/**
	 * Returns a signed JSON Web Token (JWT) representation of this
	 * payload. Intended for signed then encrypted JWTs.
	 *
	 * @return The signed JWT representation, {@code null} if the payload
	 *         couldn't be converted to a signed JWT.
	 */
	public SignedJWT toSignedJWT() {

		if (signedJWT != null) {
			return signedJWT;
		}

		try {
			return SignedJWT.parse(toString());

		} catch (ParseException e) {

			return null;
		}
	}


	/**
	 * Returns a transformation of this payload.
	 *
	 * @param <T> Type of the result.
	 * @param transformer The payload transformer. Must not be
	 *                    {@code null}.
	 *
	 * @return The transformed payload.
	 */
	public <T> T toType(final PayloadTransformer<T> transformer) {

		return transformer.transform(this);
	}
}
