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


import java.security.Provider;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.util.ByteUtils;
import com.nimbusds.jose.util.Container;
import net.jcip.annotations.ThreadSafe;


/**
 * AES GCM methods for Content Encryption Key (CEK) encryption and
 * decryption. This class is thread-safe.
 *
 * <p>See RFC 7518 (JWA), section 4.7.
 *
 * @author Melisa Halsband
 * @author Vladimir Dzhuvinov
 * @version 2017-06-01
 */
@ThreadSafe
public class AESGCMKW {


	/**
	 * Encrypts the specified Content Encryption Key (CEK).
	 *
	 * @param cek	   The Content Encryption Key (CEK) to encrypt. Must
	 *		   not be {@code null}.
	 * @param iv	   The initialisation vector (IV). Must not be
	 *		   {@code null}. The contained IV must not be
	 *		   {@code null} either.
	 * @param kek	   The AES Key Encryption Key (KEK). Must not be
	 *		   {@code null}.
	 * @param provider The specific JCA provider to use, {@code null}
	 *                 implies the default system one.
	 *
	 * @return The encrypted Content Encryption Key (CEK).
	 *
	 * @throws JOSEException If encryption failed.
	 */
	public static AuthenticatedCipherText encryptCEK(final SecretKey cek,
							 final Container<byte[]> iv,
							 final SecretKey kek,
							 Provider provider)
		throws JOSEException {

		return AESGCM.encrypt(kek, iv, cek.getEncoded(), new byte[0], provider);
	}


	/**
	 * Decrypts the specified encrypted Content Encryption Key (CEK).
	 *
	 * @param kek	       The AES Key Encription Key. Must not be
	 *                     {@code null}.
	 * @param iv	       The initialisation vector (IV). Must not be
	 *		       {@code null}.
	 * @param authEncrCEK  The encrypted Content Encryption Key (CEK) to
	 *		       decrypt and authentication tag. Must not be
	 *		       {@code null}.
	 * @param provider     The JCA provider, or {@code null} to use the
	 *		       default one.
	 *
	 * @return The decrypted Content Encryption Key (CEK).
	 *
	 * @throws JOSEException If decryption failed.
	 */
	public static SecretKey decryptCEK(final SecretKey kek,
					   final byte[] iv,
					   final AuthenticatedCipherText authEncrCEK,
					   final int keyLength,
					   final Provider provider)
		throws JOSEException {

		byte[] keyBytes = AESGCM.decrypt(kek, iv, authEncrCEK.getCipherText(), new byte[0], authEncrCEK.getAuthenticationTag(), provider);

		if (ByteUtils.safeBitLength(keyBytes) != keyLength) {

			throw new KeyLengthException("CEK key length mismatch: " + ByteUtils.safeBitLength(keyBytes) + " != " + keyLength);
		}

		return new SecretKeySpec(keyBytes, "AES");
	}


	/**
	 * Prevents public instantiation.
	 */
	private AESGCMKW() { }
}