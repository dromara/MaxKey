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
import java.util.*;

import net.minidev.json.JSONObject;

import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.JSONObjectUtils;


/**
 * The base abstract class for unsecured ({@code alg=none}), JSON Web Signature
 * (JWS) and JSON Web Encryption (JWE) headers.
 *
 * <p>The header may also include {@link #getCustomParams custom
 * parameters}; these will be serialised and parsed along the registered ones.
 *
 * @author Vladimir Dzhuvinov
 * @version 2014-08-21
 */
public abstract class Header implements Serializable {


	private static final long serialVersionUID = 1L;


	/**
	 * The algorithm ({@code alg}) parameter.
	 */
	private final Algorithm alg;


	/**
	 * The JOSE object type ({@code typ}) parameter.
	 */
	private final JOSEObjectType typ;


	/**
	 * The content type ({@code cty}) parameter.
	 */
	private final String cty;


	/**
	 * The critical headers ({@code crit}) parameter.
	 */
	private final Set<String> crit;


	/**
	 * Custom header parameters.
	 */
	private final Map<String,Object> customParams;


	/**
	 * Empty custom parameters constant.
	 */
	private static final Map<String,Object> EMPTY_CUSTOM_PARAMS =
		Collections.unmodifiableMap(new HashMap<String,Object>());


	/**
	 * The original parsed Base64URL, {@code null} if the header was 
	 * created from scratch.
	 */
	private final Base64URL parsedBase64URL;


	/**
	 * Creates a new abstract header.
	 *
	 * @param alg             The algorithm ({@code alg}) parameter. Must
	 *                        not be {@code null}.
	 * @param typ             The type ({@code typ}) parameter,
	 *                        {@code null} if not specified.
	 * @param cty             The content type ({@code cty}) parameter,
	 *                        {@code null} if not specified.
	 * @param crit            The names of the critical header
	 *                        ({@code crit}) parameters, empty set or
	 *                        {@code null} if none.
	 * @param customParams    The custom parameters, empty map or
	 *                        {@code null} if none.
	 * @param parsedBase64URL The parsed Base64URL, {@code null} if the
	 *                        header is created from scratch.
	 */
	protected Header(final Algorithm alg,
			 final JOSEObjectType typ,
			 final String cty, Set<String> crit,
			 final Map<String,Object> customParams,
			 final Base64URL parsedBase64URL) {

		if (alg == null) {
			throw new IllegalArgumentException("The algorithm \"alg\" header parameter must not be null");
		}

		this.alg = alg;

		this.typ = typ;
		this.cty = cty;

		if (crit != null) {
			// Copy and make unmodifiable
			this.crit = Collections.unmodifiableSet(new HashSet<>(crit));
		} else {
			this.crit = null;
		}

		if (customParams != null) {
			// Copy and make unmodifiable
			this.customParams = Collections.unmodifiableMap(new HashMap<>(customParams));
		} else {
			this.customParams = EMPTY_CUSTOM_PARAMS;
		}

		this.parsedBase64URL = parsedBase64URL;
	}


	/**
	 * Deep copy constructor.
	 *
	 * @param header The header to copy. Must not be {@code null}.
	 */
	protected Header(final Header header) {

		this(
			header.getAlgorithm(),
			header.getType(),
			header.getContentType(),
			header.getCriticalParams(),
			header.getCustomParams(),
			header.getParsedBase64URL());
	}


	/**
	 * Gets the algorithm ({@code alg}) parameter.
	 *
	 * @return The algorithm parameter.
	 */
	public Algorithm getAlgorithm() {

		return alg;
	}


	/**
	 * Gets the type ({@code typ}) parameter.
	 *
	 * @return The type parameter, {@code null} if not specified.
	 */
	public JOSEObjectType getType() {

		return typ;
	}


	/**
	 * Gets the content type ({@code cty}) parameter.
	 *
	 * @return The content type parameter, {@code null} if not specified.
	 */
	public String getContentType() {

		return cty;
	}


	/**
	 * Gets the critical header parameters ({@code crit}) parameter.
	 *
	 * @return The names of the critical header parameters, as a
	 *         unmodifiable set, {@code null} if not specified.
	 */
	public Set<String> getCriticalParams() {

		return crit;
	}


	/**
	 * Gets a custom (non-registered) parameter.
	 *
	 * @param name The name of the custom parameter. Must not be
	 *             {@code null}.
	 *
	 * @return The custom parameter, {@code null} if not specified.
	 */
	public Object getCustomParam(final String name) {

		return customParams.get(name);
	}


	/**
	 * Gets the custom (non-registered) parameters.
	 *
	 * @return The custom parameters, as a unmodifiable map, empty map if
	 *         none.
	 */
	public Map<String,Object> getCustomParams() {

		return customParams;
	}


	/**
	 * Gets the original Base64URL used to create this header.
	 *
	 * @return The parsed Base64URL, {@code null} if the header was created
	 *         from scratch.
	 */
	public Base64URL getParsedBase64URL() {

		return parsedBase64URL;
	}


	/**
	 * Gets the names of all included parameters (registered and custom) in
	 * the header instance.
	 *
	 * @return The included parameters.
	 */
	public Set<String> getIncludedParams() {

		Set<String> includedParameters =
			new HashSet<>(getCustomParams().keySet());

		includedParameters.add("alg");

		if (getType() != null) {
			includedParameters.add("typ");
		}

		if (getContentType() != null) {
			includedParameters.add("cty");
		}

		if (getCriticalParams() != null && ! getCriticalParams().isEmpty()) {
			includedParameters.add("crit");
		}

		return includedParameters;
	}


	/**
	 * Returns a JSON object representation of the header. All custom
	 * parameters are included if they serialise to a JSON entity and
	 * their names don't conflict with the registered ones.
	 *
	 * @return The JSON object representation of the header.
	 */
	public JSONObject toJSONObject() {

		// Include custom parameters, they will be overwritten if their
		// names match specified registered ones
		JSONObject o = new JSONObject(customParams);

		// Alg is always defined
		o.put("alg", alg.toString());

		if (typ != null) {
			o.put("typ", typ.toString());
		}

		if (cty != null) {
			o.put("cty", cty);
		}

		if (crit != null && ! crit.isEmpty()) {
			o.put("crit", new ArrayList<>(crit));
		}

		return o;
	}


	/**
	 * Returns a JSON string representation of the header. All custom
	 * parameters will be included if they serialise to a JSON entity and
	 * their names don't conflict with the registered ones.
	 *
	 * @return The JSON string representation of the header.
	 */
	public String toString() {

		return toJSONObject().toString();
	}


	/**
	 * Returns a Base64URL representation of the header. If the header was
	 * parsed always returns the original Base64URL (required for JWS
	 * validation and authenticated JWE decryption).
	 *
	 * @return The original parsed Base64URL representation of the header,
	 *         or a new Base64URL representation if the header was created
	 *         from scratch.
	 */
	public Base64URL toBase64URL() {

		if (parsedBase64URL == null) {

			// Header was created from scratch, return new Base64URL
			return Base64URL.encode(toString());

		} else {

			// Header was parsed, return original Base64URL
			return parsedBase64URL;
		}
	}


	/**
	 * Parses an algorithm ({@code alg}) parameter from the specified 
	 * header JSON object. Intended for initial parsing of unsecured
	 * (plain), JWS and JWE headers.
	 *
	 * <p>The algorithm type (none, JWS or JWE) is determined by inspecting
	 * the algorithm name for "none" and the presence of an "enc"
	 * parameter.
	 *
	 * @param json The JSON object to parse. Must not be {@code null}.
	 *
	 * @return The algorithm, an instance of {@link Algorithm#NONE},
	 *         {@link JWSAlgorithm} or {@link JWEAlgorithm}.
	 *
	 * @throws ParseException If the {@code alg} parameter couldn't be 
	 *                        parsed.
	 */
	public static Algorithm parseAlgorithm(final JSONObject json)
			throws ParseException {

		String algName = JSONObjectUtils.getString(json, "alg");

		// Infer algorithm type

		if (algName.equals(Algorithm.NONE.getName())) {
			// Plain
			return Algorithm.NONE;
		} else if (json.containsKey("enc")) {
			// JWE
			return JWEAlgorithm.parse(algName);
		} else {
			// JWS
			return JWSAlgorithm.parse(algName);
		}
	}


	/**
	 * Parses a {@link PlainHeader}, {@link JWSHeader} or {@link JWEHeader}
	 * from the specified JSON object.
	 *
	 * @param jsonObject      The JSON object to parse. Must not be
	 *                        {@code null}.
	 *
	 * @return The header.
	 *
	 * @throws ParseException If the specified JSON object doesn't
	 *                        represent a valid header.
	 */
	public static Header parse(final JSONObject jsonObject)
		throws ParseException {

		return parse(jsonObject, null);
	}


	/**
	 * Parses a {@link PlainHeader}, {@link JWSHeader} or {@link JWEHeader} 
	 * from the specified JSON object.
	 *
	 * @param jsonObject      The JSON object to parse. Must not be
	 *                        {@code null}.
	 * @param parsedBase64URL The original parsed Base64URL, {@code null}
	 *                        if not applicable.
	 *
	 * @return The header.
	 *
	 * @throws ParseException If the specified JSON object doesn't 
	 *                        represent a valid header.
	 */
	public static Header parse(final JSONObject jsonObject,
				   final Base64URL parsedBase64URL)
		throws ParseException {

		Algorithm alg = parseAlgorithm(jsonObject);

		if (alg.equals(Algorithm.NONE)) {

			return PlainHeader.parse(jsonObject, parsedBase64URL);

		} else if (alg instanceof JWSAlgorithm) {

			return JWSHeader.parse(jsonObject, parsedBase64URL);

		} else if (alg instanceof JWEAlgorithm) {

			return JWEHeader.parse(jsonObject, parsedBase64URL);

		} else {

			throw new AssertionError("Unexpected algorithm type: " + alg);
		}
	}


	/**
	 * Parses a {@link PlainHeader}, {@link JWSHeader} or {@link JWEHeader}
	 * from the specified JSON object string.
	 *
	 * @param jsonString      The JSON object string to parse. Must not be
	 *                        {@code null}.
	 *
	 * @return The header.
	 *
	 * @throws ParseException If the specified JSON object string doesn't
	 *                        represent a valid header.
	 */
	public static Header parse(final String jsonString)
		throws ParseException {

		return parse(jsonString, null);
	}


	/**
	 * Parses a {@link PlainHeader}, {@link JWSHeader} or {@link JWEHeader}
	 * from the specified JSON object string.
	 *
	 * @param jsonString      The JSON object string to parse. Must not be
	 *                        {@code null}.
	 * @param parsedBase64URL The original parsed Base64URL, {@code null}
	 *                        if not applicable.
	 *
	 * @return The header.
	 *
	 * @throws ParseException If the specified JSON object string doesn't
	 *                        represent a valid header.
	 */
	public static Header parse(final String jsonString,
				   final Base64URL parsedBase64URL)
		throws ParseException {

		JSONObject jsonObject = JSONObjectUtils.parse(jsonString);

		return parse(jsonObject, parsedBase64URL);
	}


	/**
	 * Parses a {@link PlainHeader}, {@link JWSHeader} or {@link JWEHeader}
	 * from the specified Base64URL.
	 *
	 * @param base64URL The Base64URL to parse. Must not be {@code null}.
	 *
	 * @return The header.
	 *
	 * @throws ParseException If the specified Base64URL doesn't represent
	 *                        a valid header.
	 */
	public static Header parse(final Base64URL base64URL)
		throws ParseException {

		return parse(base64URL.decodeToString(), base64URL);
	}
}