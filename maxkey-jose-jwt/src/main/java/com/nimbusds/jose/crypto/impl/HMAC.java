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

package com.nimbusds.jose.crypto.impl;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.JOSEException;
import net.jcip.annotations.ThreadSafe;


/**
 * Static methods for Hash-based Message Authentication Codes (HMAC). This
 * class is thread-safe.
 *
 * @author Axel Nennker
 * @author Vladimir Dzhuvinov
 * @version 2015-04-23
 */
@ThreadSafe
public class HMAC {


	public static Mac getInitMac(final SecretKey secretKey,
				     final Provider provider)
		throws JOSEException {

		Mac mac;

		try {
			if (provider != null) {
				mac = Mac.getInstance(secretKey.getAlgorithm(), provider);
			} else {
				mac = Mac.getInstance(secretKey.getAlgorithm());
			}

			mac.init(secretKey);

		} catch (NoSuchAlgorithmException e) {

			throw new JOSEException("Unsupported HMAC algorithm: " + e.getMessage(), e);

		} catch (InvalidKeyException e) {

			throw new JOSEException("Invalid HMAC key: " + e.getMessage(), e);
		}

		return mac;
	}


	/**
	 * Computes a Hash-based Message Authentication Code (HMAC) for the
	 * specified secret and message.
	 *
	 * @param alg      The Java Cryptography Architecture (JCA) HMAC
	 *                 algorithm name. Must not be {@code null}.
	 * @param secret   The secret. Must not be {@code null}.
	 * @param message  The message. Must not be {@code null}.
	 * @param provider The JCA provider, or {@code null} to use the default
	 *                 one.
	 *
	 * @return A MAC service instance.
	 *
	 * @throws JOSEException If the algorithm is not supported or the
	 *                       MAC secret key is invalid.
	 */
	public static byte[] compute(final String alg,
				     final byte[] secret,
				     final byte[] message,
				     final Provider provider)
		throws JOSEException {

		return compute(new SecretKeySpec(secret, alg), message, provider);
	}


	/**
	 * Computes a Hash-based Message Authentication Code (HMAC) for the
	 * specified secret key and message.
	 *
	 * @param secretKey The secret key, with the appropriate HMAC
	 *                  algorithm. Must not be {@code null}.
	 * @param message   The message. Must not be {@code null}.
	 * @param provider  The JCA provider, or {@code null} to use the
	 *                  default one.
	 *
	 * @return A MAC service instance.
	 *
	 * @throws JOSEException If the algorithm is not supported or the MAC 
	 *                       secret key is invalid.
	 */
	public static byte[] compute(final SecretKey secretKey,
				     final byte[] message,
				     final Provider provider)
		throws JOSEException {

		Mac mac = getInitMac(secretKey, provider);
		mac.update(message);
		return mac.doFinal();
	}
}
