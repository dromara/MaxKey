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


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.KeyLengthException;
import net.jcip.annotations.Immutable;


/**
 * Composite key used in AES/CBC/PKCS5Padding/HMAC-SHA2 encryption. This class
 * is immutable.
 *
 * <p>See RFC 7518 (JWA), section 5.2.
 *
 * <p>See draft-mcgrew-aead-aes-cbc-hmac-sha2-01
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-06-29
 */
@Immutable
public final class CompositeKey {


	/**
	 * The input key.
	 */
	private final SecretKey inputKey;


	/**
	 * The extracted MAC key.
	 */
	private final SecretKey macKey;


	/**
	 * The extracted AES key.
	 */
	private final SecretKey encKey;


	/**
	 * The expected truncated MAC output length.
	 */
	private final int truncatedMacLength;


	/**
	 * Creates a new composite key from the specified secret key.
	 *
	 * @param inputKey The input key. Must be 256, 384 or 512 bits long.
	 *                 Must not be {@code null}.
	 *
	 * @throws KeyLengthException If the input key length is not supported.
	 */
	public CompositeKey(final SecretKey inputKey)
		throws KeyLengthException {

		this.inputKey = inputKey;

		byte[] secretKeyBytes = inputKey.getEncoded();

		if (secretKeyBytes.length == 32) {

			// AES_128_CBC_HMAC_SHA_256
			// 256 bit key -> 128 bit MAC key + 128 bit AES key
			macKey = new SecretKeySpec(secretKeyBytes, 0, 16, "HMACSHA256");
			encKey = new SecretKeySpec(secretKeyBytes, 16, 16, "AES");
			truncatedMacLength = 16;

		} else if (secretKeyBytes.length == 48) {

			// AES_192_CBC_HMAC_SHA_384
			// 384 bit key -> 129 bit MAC key + 192 bit AES key
			macKey = new SecretKeySpec(secretKeyBytes, 0, 24, "HMACSHA384");
			encKey = new SecretKeySpec(secretKeyBytes, 24, 24, "AES");
			truncatedMacLength = 24;


		} else if (secretKeyBytes.length == 64) {

			// AES_256_CBC_HMAC_SHA_512
			// 512 bit key -> 256 bit MAC key + 256 bit AES key
			macKey = new SecretKeySpec(secretKeyBytes, 0, 32, "HMACSHA512");
			encKey = new SecretKeySpec(secretKeyBytes, 32, 32, "AES");
			truncatedMacLength = 32;

		} else {

			throw new KeyLengthException("Unsupported AES/CBC/PKCS5Padding/HMAC-SHA2 key length, must be 256, 384 or 512 bits");
		}
	}


	/**
	 * Gets the input key.
	 *
	 * @return The input key.
	 */
	public SecretKey getInputKey() {

		return inputKey;
	}


	/**
	 * Gets the extracted MAC key.
	 *
	 * @return The extracted MAC key.
	 */
	public SecretKey getMACKey() {

		return macKey;
	}


	/**
	 * Gets the expected truncated MAC length.
	 *
	 * @return The expected truncated MAC length, in bytes.
	 */
	public int getTruncatedMACByteLength() {

		return truncatedMacLength;
	}


	/**
	 * Gets the extracted encryption key.
	 *
	 * @return The extracted encryption key.
	 */
	public SecretKey getAESKey() {

		return encKey;
	}
}