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
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.util.KeyUtils;
import net.jcip.annotations.ThreadSafe;


/**
 * AES key Wrapping methods for Content Encryption Key (CEK) encryption and
 * decryption. This class is thread-safe.
 *
 * <p>See RFC 7518 (JWA), section 4.4.
 *
 * @author Melisa Halsband
 * @author Vladimir Dzhuvinov
 * @version 2018-03-09
 */
@ThreadSafe
public class AESKW {


	/**
	 * Wraps the specified Content Encryption Key (CEK).
	 *
	 * @param cek      The Content Encryption Key (CEK) to wrap. Must not
	 *                 be {@code null}.
	 * @param kek      The AES Key Encryption Key (KEK) (wrapping key).
	 *                 Must not be {@code null}.
	 * @param provider The specific JCA provider to use, {@code null}
	 *                 implies the default system one.
	 *
	 * @return The wrapped Content Encryption Key (CEK).
	 *
	 * @throws JOSEException If wrapping failed.
	 */
	public static byte[] wrapCEK(final SecretKey cek,
				     final SecretKey kek,
				     final Provider provider)
		throws JOSEException {

		try {
			Cipher cipher;

			if (provider != null) {
				cipher = Cipher.getInstance("AESWrap", provider);
			} else {
				cipher = Cipher.getInstance("AESWrap");
			}

			cipher.init(Cipher.WRAP_MODE, kek);
			return cipher.wrap(cek);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
			throw new JOSEException("Couldn't wrap AES key: " + e.getMessage(), e);
		}
	}


	/**
	 * Unwraps the specified encrypted Content Encryption Key (CEK).
	 *
	 * @param kek          The AES Key Encryption Key (KEK) (wrapping key).
	 *                     Must not be {@code null}.
	 * @param encryptedCEK The wrapped Content Encryption Key (CEK) with
	 *                     authentication tag. Must not be {@code null}.
	 * @param provider     The specific JCA provider to use, {@code null}
	 *                     implies the default system one.
	 *
	 * @return The unwrapped Content Encryption Key (CEK).
	 *
	 * @throws JOSEException If unwrapping failed.
	 */
	public static SecretKey unwrapCEK(final SecretKey kek,
					  final byte[] encryptedCEK,
					  final Provider provider)
		throws JOSEException {

		try {
			Cipher cipher;

			if (provider != null) {
				cipher = Cipher.getInstance("AESWrap", provider);
			} else {
				cipher = Cipher.getInstance("AESWrap");
			}

			cipher.init(Cipher.UNWRAP_MODE, KeyUtils.toAESKey(kek)); // Make sure key alg is "AES"
			return (SecretKey)cipher.unwrap(encryptedCEK, "AES", Cipher.SECRET_KEY);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {

			throw new JOSEException("Couldn't unwrap AES key: " + e.getMessage(), e);
		}
	}


	/**
	 * Prevents public instantiation.
	 */
	private AESKW() {
	}
}