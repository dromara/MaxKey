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

import com.nimbusds.jose.JOSEException;
import net.jcip.annotations.ThreadSafe;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;


/**
 * Legacy AES/GSM/NoPadding encryption and decryption methods. Uses the
 * BouncyCastle.org API. This class is thread-safe.
 *
 * @author Vladimir Dzhuvinov
 * @author Axel Nennker
 * @version 2015-11-15
 */
@ThreadSafe
public class LegacyAESGCM {


	/**
	 * The standard authentication tag length (128 bits).
	 */
	public static final int AUTH_TAG_BIT_LENGTH = 128;


	/**
	 * Creates a new AES cipher.
	 *
	 * @param secretKey     The AES key. Must not be {@code null}.
	 * @param forEncryption If {@code true} creates an AES encryption
	 *                      cipher, else creates an AES decryption
	 *                      cipher.
	 *
	 * @return The AES cipher.
	 */
	public static AESEngine createAESCipher(final SecretKey secretKey,
						final boolean forEncryption) {

		AESEngine cipher = new AESEngine();

		CipherParameters cipherParams = new KeyParameter(secretKey.getEncoded());

		cipher.init(forEncryption, cipherParams);

		return cipher;
	}


	/**
	 * Creates a new AES/GCM/NoPadding cipher.
	 *
	 * @param secretKey     The AES key. Must not be {@code null}.
	 * @param forEncryption If {@code true} creates an encryption cipher,
	 *                      else creates a decryption cipher.
	 * @param iv            The initialisation vector (IV). Must not be
	 *                      {@code null}.
	 * @param authData      The authenticated data. Must not be
	 *                      {@code null}.
	 *
	 * @return The AES/GCM/NoPadding cipher.
	 */
	private static GCMBlockCipher createAESGCMCipher(final SecretKey secretKey,
							 final boolean forEncryption,
							 final byte[] iv,
							 final byte[] authData) {

		// Initialise AES cipher
		BlockCipher cipher = createAESCipher(secretKey, forEncryption);

		// Create GCM cipher with AES
		GCMBlockCipher gcm = new GCMBlockCipher(cipher);

		AEADParameters aeadParams = new AEADParameters(new KeyParameter(secretKey.getEncoded()),
			AUTH_TAG_BIT_LENGTH,
			iv,
			authData);
		gcm.init(forEncryption, aeadParams);

		return gcm;
	}


	/**
	 * Encrypts the specified plain text using AES/GCM/NoPadding.
	 *
	 * @param secretKey The AES key. Must not be {@code null}.
	 * @param plainText The plain text. Must not be {@code null}.
	 * @param iv        The initialisation vector (IV). Must not be
	 *                  {@code null}.
	 * @param authData  The authenticated data. Must not be {@code null}.
	 *
	 * @return The authenticated cipher text.
	 *
	 * @throws JOSEException If encryption failed.
	 */
	public static AuthenticatedCipherText encrypt(final SecretKey secretKey,
						      final byte[] iv,
						      final byte[] plainText,
						      final byte[] authData)
		throws JOSEException {

		// Initialise AES/GCM cipher for encryption
		GCMBlockCipher cipher = createAESGCMCipher(secretKey, true, iv, authData);


		// Prepare output buffer
		int outputLength = cipher.getOutputSize(plainText.length);
		byte[] output = new byte[outputLength];


		// Produce cipher text
		int outputOffset = cipher.processBytes(plainText, 0, plainText.length, output, 0);


		// Produce authentication tag
		try {
			outputOffset += cipher.doFinal(output, outputOffset);

		} catch (InvalidCipherTextException e) {

			throw new JOSEException("Couldn't generate GCM authentication tag: " + e.getMessage(), e);
		}

		// Split output into cipher text and authentication tag
		int authTagLength = AUTH_TAG_BIT_LENGTH / 8;

		byte[] cipherText = new byte[outputOffset - authTagLength];
		byte[] authTag = new byte[authTagLength];

		System.arraycopy(output, 0, cipherText, 0, cipherText.length);
		System.arraycopy(output, outputOffset - authTagLength, authTag, 0, authTag.length);

		return new AuthenticatedCipherText(cipherText, authTag);
	}


	/**
	 * Decrypts the specified cipher text using AES/GCM/NoPadding.
	 *
	 * @param secretKey  The AES key. Must not be {@code null}.
	 * @param iv         The initialisation vector (IV). Must not be
	 *                   {@code null}.
	 * @param cipherText The cipher text. Must not be {@code null}.
	 * @param authData   The authenticated data. Must not be {@code null}.
	 * @param authTag    The authentication tag. Must not be {@code null}.
	 *
	 * @return The decrypted plain text.
	 *
	 * @throws JOSEException If decryption failed.
	 */
	public static byte[] decrypt(final SecretKey secretKey,
				     final byte[] iv,
				     final byte[] cipherText,
				     final byte[] authData,
				     final byte[] authTag)
		throws JOSEException {

		// Initialise AES/GCM cipher for decryption
		GCMBlockCipher cipher = createAESGCMCipher(secretKey, false, iv, authData);


		// Join cipher text and authentication tag to produce cipher input
		byte[] input = new byte[cipherText.length + authTag.length];

		System.arraycopy(cipherText, 0, input, 0, cipherText.length);
		System.arraycopy(authTag, 0, input, cipherText.length, authTag.length);

		int outputLength = cipher.getOutputSize(input.length);

		byte[] output = new byte[outputLength];


		// Decrypt
		int outputOffset = cipher.processBytes(input, 0, input.length, output, 0);

		// Validate authentication tag
		try {
			outputOffset += cipher.doFinal(output, outputOffset);

		} catch (InvalidCipherTextException e) {

			throw new JOSEException("Couldn't validate GCM authentication tag: " + e.getMessage(), e);
		}

		return output;
	}


	/**
	 * Prevents public instantiation.
	 */
	private LegacyAESGCM() { }
}