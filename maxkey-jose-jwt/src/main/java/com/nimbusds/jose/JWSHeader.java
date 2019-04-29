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


import java.net.URI;
import java.text.ParseException;
import java.util.*;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.JSONObjectUtils;
import com.nimbusds.jose.util.X509CertChainUtils;
import net.jcip.annotations.Immutable;
import net.minidev.json.JSONObject;


/**
 * JSON Web Signature (JWS) header. This class is immutable.
 *
 * <p>Supports all {@link #getRegisteredParameterNames registered header
 * parameters} of the JWS specification:
 *
 * <ul>
 *     <li>alg
 *     <li>jku
 *     <li>jwk
 *     <li>x5u
 *     <li>x5t
 *     <li>x5t#S256
 *     <li>x5c
 *     <li>kid
 *     <li>typ
 *     <li>cty
 *     <li>crit
 * </ul>
 *
 * <p>The header may also include {@link #getCustomParams custom
 * parameters}; these will be serialised and parsed along the registered ones.
 *
 * <p>Example header of a JSON Web Signature (JWS) object using the 
 * {@link JWSAlgorithm#HS256 HMAC SHA-256 algorithm}:
 *
 * <pre>
 * {
 *   "alg" : "HS256"
 * }
 * </pre>
 *
 * @author Vladimir Dzhuvinov
 * @version 2017-04-09
 */
@Immutable
public final class JWSHeader extends CommonSEHeader {


	private static final long serialVersionUID = 1L;


	/**
	 * The registered parameter names.
	 */
	private static final Set<String> REGISTERED_PARAMETER_NAMES;


	/**
	 * Initialises the registered parameter name set.
	 */
	static {
		Set<String> p = new HashSet<>();

		p.add("alg");
		p.add("jku");
		p.add("jwk");
		p.add("x5u");
		p.add("x5t");
		p.add("x5t#S256");
		p.add("x5c");
		p.add("kid");
		p.add("typ");
		p.add("cty");
		p.add("crit");

		REGISTERED_PARAMETER_NAMES = Collections.unmodifiableSet(p);
	}


	/**
	 * Builder for constructing JSON Web Signature (JWS) headers.
	 *
	 * <p>Example usage:
	 *
	 * <pre>
	 * JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).
	 *                    contentType("text/plain").
	 *                    customParam("exp", new Date().getTime()).
	 *                    build();
	 * </pre>
	 */
	public static class Builder {


		/**
		 * The JWS algorithm.
		 */
		private final JWSAlgorithm alg;


		/**
		 * The JOSE object type.
		 */
		private JOSEObjectType typ;


		/**
		 * The content type.
		 */
		private String cty;


		/**
		 * The critical headers.
		 */
		private Set<String> crit;


		/**
		 * JWK Set URL.
		 */
		private URI jku;


		/**
		 * JWK.
		 */
		private JWK jwk;


		/**
		 * X.509 certificate URL.
		 */
		private URI x5u;


		/**
		 * X.509 certificate SHA-1 thumbprint.
		 */
		@Deprecated
		private Base64URL x5t;


		/**
		 * X.509 certificate SHA-256 thumbprint.
		 */
		private Base64URL x5t256;


		/**
		 * The X.509 certificate chain corresponding to the key used to
		 * sign the JWS object.
		 */
		private List<Base64> x5c;


		/**
		 * Key ID.
		 */
		private String kid;


		/**
		 * Custom header parameters.
		 */
		private Map<String,Object> customParams;


		/**
		 * The parsed Base64URL.
		 */
		private Base64URL parsedBase64URL;


		/**
		 * Creates a new JWS header builder.
		 *
		 * @param alg The JWS algorithm ({@code alg}) parameter. Must
		 *            not be "none" or {@code null}.
		 */
		public Builder(final JWSAlgorithm alg) {

			if (alg.getName().equals(Algorithm.NONE.getName())) {
				throw new IllegalArgumentException("The JWS algorithm \"alg\" cannot be \"none\"");
			}

			this.alg = alg;
		}


		/**
		 * Creates a new JWS header builder with the parameters from
		 * the specified header.
		 *
		 * @param jwsHeader The JWS header to use. Must not not be
		 *                  {@code null}.
		 */
		public Builder(final JWSHeader jwsHeader) {

			this(jwsHeader.getAlgorithm());

			typ = jwsHeader.getType();
			cty = jwsHeader.getContentType();
			crit = jwsHeader.getCriticalParams();

			jku = jwsHeader.getJWKURL();
			jwk = jwsHeader.getJWK();
			x5u = jwsHeader.getX509CertURL();
			x5t = jwsHeader.getX509CertThumbprint();
			x5t256 = jwsHeader.getX509CertSHA256Thumbprint();
			x5c = jwsHeader.getX509CertChain();
			kid = jwsHeader.getKeyID();
			customParams = jwsHeader.getCustomParams();
		}


		/**
		 * Sets the type ({@code typ}) parameter.
		 *
		 * @param typ The type parameter, {@code null} if not
		 *            specified.
		 *
		 * @return This builder.
		 */
		public Builder type(final JOSEObjectType typ) {

			this.typ = typ;
			return this;
		}


		/**
		 * Sets the content type ({@code cty}) parameter.
		 *
		 * @param cty The content type parameter, {@code null} if not
		 *            specified.
		 *
		 * @return This builder.
		 */
		public Builder contentType(final String cty) {

			this.cty = cty;
			return this;
		}


		/**
		 * Sets the critical header parameters ({@code crit})
		 * parameter.
		 *
		 * @param crit The names of the critical header parameters,
		 *             empty set or {@code null} if none.
		 *
		 * @return This builder.
		 */
		public Builder criticalParams(final Set<String> crit) {

			this.crit = crit;
			return this;
		}


		/**
		 * Sets the JSON Web Key (JWK) Set URL ({@code jku}) parameter.
		 *
		 * @param jku The JSON Web Key (JWK) Set URL parameter,
		 *            {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder jwkURL(final URI jku) {

			this.jku = jku;
			return this;
		}


		/**
		 * Sets the JSON Web Key (JWK) ({@code jwk}) parameter.
		 *
		 * @param jwk The JSON Web Key (JWK) ({@code jwk}) parameter,
		 *            {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder jwk(final JWK jwk) {

			this.jwk = jwk;
			return this;
		}


		/**
		 * Sets the X.509 certificate URL ({@code x5u}) parameter.
		 *
		 * @param x5u The X.509 certificate URL parameter, {@code null}
		 *            if not specified.
		 *
		 * @return This builder.
		 */
		public Builder x509CertURL(final URI x5u) {

			this.x5u = x5u;
			return this;
		}


		/**
		 * Sets the X.509 certificate SHA-1 thumbprint ({@code x5t})
		 * parameter.
		 *
		 * @param x5t The X.509 certificate SHA-1 thumbprint parameter,
		 *            {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		@Deprecated
		public Builder x509CertThumbprint(final Base64URL x5t) {

			this.x5t = x5t;
			return this;
		}


		/**
		 * Sets the X.509 certificate SHA-256 thumbprint
		 * ({@code x5t#S256}) parameter.
		 *
		 * @param x5t256 The X.509 certificate SHA-256 thumbprint
		 *               parameter, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder x509CertSHA256Thumbprint(final Base64URL x5t256) {

			this.x5t256 = x5t256;
			return this;
		}


		/**
		 * Sets the X.509 certificate chain parameter ({@code x5c})
		 * corresponding to the key used to sign the JWS object.
		 *
		 * @param x5c The X.509 certificate chain parameter,
		 *            {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder x509CertChain(final List<Base64> x5c) {

			this.x5c = x5c;
			return this;
		}


		/**
		 * Sets the key ID ({@code kid}) parameter.
		 *
		 * @param kid The key ID parameter, {@code null} if not
		 *            specified.
		 *
		 * @return This builder.
		 */
		public Builder keyID(final String kid) {

			this.kid = kid;
			return this;
		}


		/**
		 * Sets a custom (non-registered) parameter.
		 *
		 * @param name  The name of the custom parameter. Must not
		 *              match a registered parameter name and must not
		 *              be {@code null}.
		 * @param value The value of the custom parameter, should map
		 *              to a valid JSON entity, {@code null} if not
		 *              specified.
		 *
		 * @return This builder.
		 *
		 * @throws IllegalArgumentException If the specified parameter
		 *                                  name matches a registered
		 *                                  parameter name.
		 */
		public Builder customParam(final String name, final Object value) {

			if (getRegisteredParameterNames().contains(name)) {
				throw new IllegalArgumentException("The parameter name \"" + name + "\" matches a registered name");
			}

			if (customParams == null) {
				customParams = new HashMap<>();
			}

			customParams.put(name, value);

			return this;
		}


		/**
		 * Sets the custom (non-registered) parameters. The values must
		 * be serialisable to a JSON entity, otherwise will be ignored.
		 *
		 * @param customParameters The custom parameters, empty map or
		 *                         {@code null} if none.
		 *
		 * @return This builder.
		 */
		public Builder customParams(final Map<String, Object> customParameters) {

			this.customParams = customParameters;
			return this;
		}


		/**
		 * Sets the parsed Base64URL.
		 *
		 * @param base64URL The parsed Base64URL, {@code null} if the
		 *                  header is created from scratch.
		 *
		 * @return This builder.
		 */
		public Builder parsedBase64URL(final Base64URL base64URL) {

			this.parsedBase64URL = base64URL;
			return this;
		}


		/**
		 * Builds a new JWS header.
		 *
		 * @return The JWS header.
		 */
		public JWSHeader build() {

			return new JWSHeader(
				alg, typ, cty, crit,
				jku, jwk, x5u, x5t, x5t256, x5c, kid,
				customParams, parsedBase64URL);
		}
	}


	/**
	 * Creates a new minimal JSON Web Signature (JWS) header.
	 *
	 * <p>Note: Use {@link PlainHeader} to create a header with algorithm
	 * {@link Algorithm#NONE none}.
	 *
	 * @param alg The JWS algorithm ({@code alg}) parameter. Must not be
	 *            "none" or {@code null}.
	 */
	public JWSHeader(final JWSAlgorithm alg) {

		this(alg, null, null, null, null, null, null, null, null, null, null, null, null);
	}


	/**
	 * Creates a new JSON Web Signature (JWS) header.
	 *
	 * <p>Note: Use {@link PlainHeader} to create a header with algorithm
	 * {@link Algorithm#NONE none}.
	 *
	 * @param alg             The JWS algorithm ({@code alg}) parameter.
	 *                        Must not be "none" or {@code null}.
	 * @param typ             The type ({@code typ}) parameter,
	 *                        {@code null} if not specified.
	 * @param cty             The content type ({@code cty}) parameter,
	 *                        {@code null} if not specified.
	 * @param crit            The names of the critical header
	 *                        ({@code crit}) parameters, empty set or
	 *                        {@code null} if none.
	 * @param jku             The JSON Web Key (JWK) Set URL ({@code jku})
	 *                        parameter, {@code null} if not specified.
	 * @param jwk             The X.509 certificate URL ({@code jwk})
	 *                        parameter, {@code null} if not specified.
	 * @param x5u             The X.509 certificate URL parameter
	 *                        ({@code x5u}), {@code null} if not specified.
	 * @param x5t             The X.509 certificate SHA-1 thumbprint
	 *                        ({@code x5t}) parameter, {@code null} if not
	 *                        specified.
	 * @param x5t256          The X.509 certificate SHA-256 thumbprint
	 *                        ({@code x5t#S256}) parameter, {@code null} if
	 *                        not specified.
	 * @param x5c             The X.509 certificate chain ({@code x5c})
	 *                        parameter, {@code null} if not specified.
	 * @param kid             The key ID ({@code kid}) parameter,
	 *                        {@code null} if not specified.
	 * @param customParams    The custom parameters, empty map or
	 *                        {@code null} if none.
	 * @param parsedBase64URL The parsed Base64URL, {@code null} if the
	 *                        header is created from scratch.
	 */
	public JWSHeader(final JWSAlgorithm alg,
			 final JOSEObjectType typ,
			 final String cty,
			 final Set<String> crit,
			 final URI jku,
			 final JWK jwk,
			 final URI x5u,
			 final Base64URL x5t,
			 final Base64URL x5t256,
			 final List<Base64> x5c,
			 final String kid,
			 final Map<String,Object> customParams,
			 final Base64URL parsedBase64URL) {

		super(alg, typ, cty, crit, jku, jwk, x5u, x5t, x5t256, x5c, kid, customParams, parsedBase64URL);

		if (alg.getName().equals(Algorithm.NONE.getName())) {
			throw new IllegalArgumentException("The JWS algorithm \"alg\" cannot be \"none\"");
		}
	}


	/**
	 * Deep copy constructor.
	 *
	 * @param jwsHeader The JWS header to copy. Must not be {@code null}.
	 */
	public JWSHeader(final JWSHeader jwsHeader) {

		this(
			jwsHeader.getAlgorithm(),
			jwsHeader.getType(),
			jwsHeader.getContentType(),
			jwsHeader.getCriticalParams(),
			jwsHeader.getJWKURL(),
			jwsHeader.getJWK(),
			jwsHeader.getX509CertURL(),
			jwsHeader.getX509CertThumbprint(),
			jwsHeader.getX509CertSHA256Thumbprint(),
			jwsHeader.getX509CertChain(),
			jwsHeader.getKeyID(),
			jwsHeader.getCustomParams(),
			jwsHeader.getParsedBase64URL()
		);
	}


	/**
	 * Gets the registered parameter names for JWS headers.
	 *
	 * @return The registered parameter names, as an unmodifiable set.
	 */
	public static Set<String> getRegisteredParameterNames() {

		return REGISTERED_PARAMETER_NAMES;
	}


	/**
	 * Gets the algorithm ({@code alg}) parameter.
	 *
	 * @return The algorithm parameter.
	 */
	@Override
	public JWSAlgorithm getAlgorithm() {

		return (JWSAlgorithm)super.getAlgorithm();
	}


	/**
	 * Parses a JWS header from the specified JSON object.
	 *
	 * @param jsonObject The JSON object to parse. Must not be
	 *                   {@code null}.
	 *
	 * @return The JWS header.
	 *
	 * @throws ParseException If the specified JSON object doesn't
	 *                        represent a valid JWS header.
	 */
	public static JWSHeader parse(final JSONObject jsonObject)
		throws ParseException {

		return parse(jsonObject, null);
	}


	/**
	 * Parses a JWS header from the specified JSON object.
	 *
	 * @param jsonObject      The JSON object to parse. Must not be
	 *                        {@code null}.
	 * @param parsedBase64URL The original parsed Base64URL, {@code null}
	 *                        if not applicable.
	 *
	 * @return The JWS header.
	 *
	 * @throws ParseException If the specified JSON object doesn't 
	 *                        represent a valid JWS header.
	 */
	public static JWSHeader parse(final JSONObject jsonObject,
				      final Base64URL parsedBase64URL)
		throws ParseException {

		// Get the "alg" parameter
		Algorithm alg = Header.parseAlgorithm(jsonObject);

		if (! (alg instanceof JWSAlgorithm)) {
			throw new ParseException("The algorithm \"alg\" header parameter must be for signatures", 0);
		}

		JWSHeader.Builder header = new Builder((JWSAlgorithm)alg).parsedBase64URL(parsedBase64URL);

		// Parse optional + custom parameters
		for (final String name: jsonObject.keySet()) {

			if("alg".equals(name)) {
				// skip
			} else if("typ".equals(name)) {
				header = header.type(new JOSEObjectType(JSONObjectUtils.getString(jsonObject, name)));
			} else if("cty".equals(name)) {
				header = header.contentType(JSONObjectUtils.getString(jsonObject, name));
			} else if("crit".equals(name)) {
				header = header.criticalParams(new HashSet<>(JSONObjectUtils.getStringList(jsonObject, name)));
			} else if("jku".equals(name)) {
				header = header.jwkURL(JSONObjectUtils.getURI(jsonObject, name));
			} else if("jwk".equals(name)) {
				header = header.jwk(JWK.parse(JSONObjectUtils.getJSONObject(jsonObject, name)));
			} else if("x5u".equals(name)) {
				header = header.x509CertURL(JSONObjectUtils.getURI(jsonObject, name));
			} else if("x5t".equals(name)) {
				header = header.x509CertThumbprint(new Base64URL(JSONObjectUtils.getString(jsonObject, name)));
			} else if("x5t#S256".equals(name)) {
				header = header.x509CertSHA256Thumbprint(new Base64URL(JSONObjectUtils.getString(jsonObject, name)));
			} else if("x5c".equals(name)) {
				header = header.x509CertChain(X509CertChainUtils.toBase64List(JSONObjectUtils.getJSONArray(jsonObject, name)));
			} else if("kid".equals(name)) {
				header = header.keyID(JSONObjectUtils.getString(jsonObject, name));
			} else {
				header = header.customParam(name, jsonObject.get(name));
			}
		}

		return header.build();
	}


	/**
	 * Parses a JWS header from the specified JSON object string.
	 *
	 * @param jsonString The JSON string to parse. Must not be
	 *                   {@code null}.
	 *
	 * @return The JWS header.
	 *
	 * @throws ParseException If the specified JSON object string doesn't
	 *                        represent a valid JWS header.
	 */
	public static JWSHeader parse(final String jsonString)
		throws ParseException {

		return parse(jsonString, null);
	}


	/**
	 * Parses a JWS header from the specified JSON object string.
	 *
	 * @param jsonString      The JSON string to parse. Must not be
	 *                        {@code null}.
	 * @param parsedBase64URL The original parsed Base64URL, {@code null}
	 *                        if not applicable.
	 *
	 * @return The JWS header.
	 *
	 * @throws ParseException If the specified JSON object string doesn't 
	 *                        represent a valid JWS header.
	 */
	public static JWSHeader parse(final String jsonString,
				      final Base64URL parsedBase64URL)
		throws ParseException {

		return parse(JSONObjectUtils.parse(jsonString), parsedBase64URL);
	}


	/**
	 * Parses a JWS header from the specified Base64URL.
	 *
	 * @param base64URL The Base64URL to parse. Must not be {@code null}.
	 *
	 * @return The JWS header.
	 *
	 * @throws ParseException If the specified Base64URL doesn't represent
	 *                        a valid JWS header.
	 */
	public static JWSHeader parse(final Base64URL base64URL)
		throws ParseException {

		return parse(base64URL.decodeToString(), base64URL);
	}
}
