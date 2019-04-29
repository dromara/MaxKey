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


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.StandardCharset;
import net.minidev.json.JSONObject;


/**
 * Thumbprint utilities.
 *
 * <p>See RFC 7638.
 *
 * @author Vladimir Dzhuvinov
 * @version 2016-07-26
 */
public final class ThumbprintUtils {


	/**
	 * Computes the SHA-256 thumbprint for the specified JWK.
	 *
	 * @param jwk The JWK. Must not be {@code null}.
	 *
	 * @return The JWK thumbprint.
	 *
	 * @throws JOSEException If the SHA-256 hash algorithm is not
	 *                       supported.
	 */
	public static Base64URL compute(final JWK jwk)
		throws JOSEException {

		return compute("SHA-256", jwk);
	}


	/**
	 * Computes the thumbprint for the specified JWK.
	 *
	 * @param hashAlg The hash algorithm. Must not be {@code null}.
	 * @param jwk     The JWK. Must not be {@code null}.
	 *
	 * @return The JWK thumbprint.
	 *
	 * @throws JOSEException If the hash algorithm is not supported.
	 */
	public static Base64URL compute(final String hashAlg, final JWK jwk)
		throws JOSEException {

		final LinkedHashMap<String,?> orderedParams = jwk.getRequiredParams();

		return compute(hashAlg, orderedParams);
	}


	/**
	 * Computes the thumbprint for the specified required JWK parameters.
	 *
	 * @param hashAlg The hash algorithm. Must not be {@code null}.
	 * @param params  The required JWK parameters, alphanumerically sorted
	 *                by parameter name and ready for JSON object
	 *                serialisation. Must not be {@code null}.
	 *
	 * @return The JWK thumbprint.
	 *
	 * @throws JOSEException If the hash algorithm is not supported.
	 */
	public static Base64URL compute(final String hashAlg, final LinkedHashMap<String,?> params)
		throws JOSEException {

		final String json = JSONObject.toJSONString(params);

		final MessageDigest md;

		try {
			md = MessageDigest.getInstance(hashAlg);

		} catch (NoSuchAlgorithmException e) {

			throw new JOSEException("Couldn't compute JWK thumbprint: Unsupported hash algorithm: " + e.getMessage(), e);
		}

		md.update(json.getBytes(StandardCharset.UTF_8));

		return Base64URL.encode(md.digest());
	}
}
