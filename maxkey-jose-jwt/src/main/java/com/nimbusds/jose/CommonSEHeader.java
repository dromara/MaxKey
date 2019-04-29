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
import java.util.*;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jose.util.Base64URL;
import net.minidev.json.JSONObject;


/**
 * Common class for JWS and JWE headers.
 *
 * <p>Supports all registered header parameters shared by the JWS and JWE
 * specifications:
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
 * @author Vladimir Dzhuvinov
 * @version 2017-04-09
 */
abstract class CommonSEHeader extends Header {


	private static final long serialVersionUID = 1L;


	/**
	 * JWK Set URL, {@code null} if not specified.
	 */
	private final URI jku;


	/**
	 * JWK, {@code null} if not specified.
	 */
	private final JWK jwk;


	/**
	 * X.509 certificate URL, {@code null} if not specified.
	 */
	private final URI x5u;


	/**
	 * X.509 certificate SHA-1 thumbprint, {@code null} if not specified.
	 */
	private final Base64URL x5t;


	/**
	 * X.509 certificate SHA-256 thumbprint, {@code null} if not specified.
	 */
	private final Base64URL x5t256;


	/**
	 * The X.509 certificate chain corresponding to the key used to sign or 
	 * encrypt the JWS / JWE object, {@code null} if not specified.
	 */
	private final List<Base64> x5c;


	/**
	 * Key ID, {@code null} if not specified.
	 */
	private final String kid;


	/**
	 * Creates a new common JWS and JWE header.
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
	protected CommonSEHeader(final Algorithm alg,
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

		super(alg, typ, cty, crit, customParams, parsedBase64URL);

		this.jku = jku;
		this.jwk = jwk;
		this.x5u = x5u;
		this.x5t = x5t;
		this.x5t256 = x5t256;

		if (x5c != null) {
			// Copy and make unmodifiable
			this.x5c = Collections.unmodifiableList(new ArrayList<>(x5c));
		} else {
			this.x5c = null;
		}

		this.kid = kid;
	}


	/**
	 * Gets the JSON Web Key (JWK) Set URL ({@code jku}) parameter.
	 *
	 * @return The JSON Web Key (JWK) Set URL parameter, {@code null} if
	 *         not specified.
	 */
	public URI getJWKURL() {

		return jku;
	}


	/**
	 * Gets the JSON Web Key (JWK) ({@code jwk}) parameter.
	 *
	 * @return The JSON Web Key (JWK) parameter, {@code null} if not
	 *         specified.
	 */
	public JWK getJWK() {

		return jwk;
	}


	/**
	 * Gets the X.509 certificate URL ({@code x5u}) parameter.
	 *
	 * @return The X.509 certificate URL parameter, {@code null} if not
	 *         specified.
	 */
	public URI getX509CertURL() {

		return x5u;
	}


	/**
	 * Gets the X.509 certificate SHA-1 thumbprint ({@code x5t}) parameter.
	 *
	 * @return The X.509 certificate SHA-1 thumbprint parameter,
	 *         {@code null} if not specified.
	 */
	@Deprecated
	public Base64URL getX509CertThumbprint() {

		return x5t;
	}


	/**
	 * Gets the X.509 certificate SHA-256 thumbprint ({@code x5t#S256})
	 * parameter.
	 *
	 * @return The X.509 certificate SHA-256 thumbprint parameter,
	 *         {@code null} if not specified.
	 */
	public Base64URL getX509CertSHA256Thumbprint() {

		return x5t256;
	}


	/**
	 * Gets the X.509 certificate chain ({@code x5c}) parameter
	 * corresponding to the key used to sign or encrypt the JWS / JWE
	 * object.
	 *
	 * @return The X.509 certificate chain parameter as a unmodifiable
	 *         list, {@code null} if not specified.
	 */
	public List<Base64> getX509CertChain() {

		return x5c;
	}


	/**
	 * Gets the key ID ({@code kid}) parameter.
	 *
	 * @return The key ID parameter, {@code null} if not specified.
	 */
	public String getKeyID() {

		return kid;
	}


	@Override
	public Set<String> getIncludedParams() {

		Set<String> includedParameters = super.getIncludedParams();

		if (jku != null) {
			includedParameters.add("jku");
		}

		if (jwk != null) {
			includedParameters.add("jwk");
		}

		if (x5u != null) {
			includedParameters.add("x5u");
		}

		if (x5t != null) {
			includedParameters.add("x5t");
		}

		if (x5t256 != null) {
			includedParameters.add("x5t#S256");
		}

		if (x5c != null && ! x5c.isEmpty()) {
			includedParameters.add("x5c");
		}

		if (kid != null) {
			includedParameters.add("kid");
		}

		return includedParameters;
	}


	@Override
	public JSONObject toJSONObject() {

		JSONObject o = super.toJSONObject();

		if (jku != null) {
			o.put("jku", jku.toString());
		}

		if (jwk != null) {
			o.put("jwk", jwk.toJSONObject());
		}

		if (x5u != null) {
			o.put("x5u", x5u.toString());
		}

		if (x5t != null) {
			o.put("x5t", x5t.toString());
		}

		if (x5t256 != null) {
			o.put("x5t#S256", x5t256.toString());
		}

		if (x5c != null && ! x5c.isEmpty()) {
			o.put("x5c", x5c);
		}

		if (kid != null) {
			o.put("kid", kid);
		}

		return o;
	}
}
