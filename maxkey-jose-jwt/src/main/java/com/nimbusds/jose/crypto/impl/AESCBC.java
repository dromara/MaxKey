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


import java.nio.ByteBuffer;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.crypto.utils.ConstantTimeUtils;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.ByteUtils;
import com.nimbusds.jose.util.StandardCharset;
import net.jcip.annotations.ThreadSafe;


/**
 * AES/CBC/PKCS5Padding and AES/CBC/PKCS5Padding/HMAC-SHA2 encryption and 
 * decryption methods. This class is thread-safe.
 *
 * <p>Also supports the deprecated AES/CBC/HMAC encryption using a custom
 * concat KDF (JOSE draft suite 08).
 *
 * <p>See RFC 7518 (JWA), section 5.2.
 *
 * @author Vladimir Dzhuvinov
 * @author Axel Nennker
 * @version 2018-01-04
 */
@ThreadSafe
public class AESCBC {


	/**
	 * The standard Initialisation Vector (IV) length (128 bits).
	 */
	public static final int IV_BIT_LENGTH = 128;


	/**
	 * Generates a random 128 bit (16 byte) Initialisation Vector(IV) for
	 * use in AES-CBC encryption.
	 *
	 * @param randomGen The secure random generator to use. Must be 
	 *                  correctly initialised and not {@code null}.
	 *
	 * @return The random 128 bit IV, as 16 byte array.
	 */
	public static byte[] generateIV(final SecureRandom randomGen) {
		
		byte[] bytes = new byte[ByteUtils.byteLength(IV_BIT_LENGTH)];
		randomGen.nextBytes(bytes);
		return bytes;
	}


	/**
	 * Creates a new AES/CBC/PKCS5Padding cipher.
	 *
	 * @param secretKey     The AES key. Must not be {@code null}.
	 * @param forEncryption If {@code true} creates an encryption cipher, 
	 *                      else creates a decryption cipher.
	 * @param iv            The initialisation vector (IV). Must not be
	 *                      {@code null}.
	 * @param provider      The JCA provider, or {@code null} to use the
	 *                      default one.
	 *
	 * @return The AES/CBC/PKCS5Padding cipher.
	 */
	private static Cipher createAESCBCCipher(final SecretKey secretKey,
		                                 final boolean forEncryption,
		                                 final byte[] iv,
		                                 final Provider provider)
		throws JOSEException {

		Cipher cipher;

		try {
			cipher = CipherHelper.getInstance("AES/CBC/PKCS5Padding", provider);

			SecretKeySpec keyspec = new SecretKeySpec(secretKey.getEncoded(), "AES");

			IvParameterSpec ivSpec = new IvParameterSpec(iv);

			if (forEncryption) {

				cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivSpec);

			} else {

				cipher.init(Cipher.DECRYPT_MODE, keyspec, ivSpec);
			}

		} catch (Exception e) {

			throw new JOSEException(e.getMessage(), e);
		}

		return cipher;
	}


	/**
	 * Encrypts the specified plain text using AES/CBC/PKCS5Padding.
	 *
	 * @param secretKey The AES key. Must not be {@code null}.
	 * @param iv        The initialisation vector (IV). Must not be
	 *                  {@code null}.
	 * @param plainText The plain text. Must not be {@code null}.
	 * @param provider  The JCA provider, or {@code null} to use the
	 *                  default one.
	 *
	 * @return The cipher text.
	 *
	 * @throws JOSEException If encryption failed.
	 */
	public static byte[] encrypt(final SecretKey secretKey, 
		                     final byte[] iv,
		                     final byte[] plainText,
		                     final Provider provider)
		throws JOSEException {

		Cipher cipher = createAESCBCCipher(secretKey, true, iv, provider);

		try {
			return cipher.doFinal(plainText);	
		
		} catch (Exception e) {

			throw new JOSEException(e.getMessage(), e);
		}
	}


	/**
	 * Encrypts the specified plain text using AES/CBC/PKCS5Padding/
	 * HMAC-SHA2.
	 * 
	 * <p>See RFC 7518 (JWA), section 5.2.2.1
	 *
	 * <p>See draft-mcgrew-aead-aes-cbc-hmac-sha2-01
	 *
	 * @param secretKey   The secret key. Must be 256 or 512 bits long.
	 *                    Must not be {@code null}.
	 * @param iv          The initialisation vector (IV). Must not be
	 *                    {@code null}.
	 * @param plainText   The plain text. Must not be {@code null}.
	 * @param aad         The additional authenticated data. Must not be
	 *                    {@code null}.
	 * @param ceProvider  The JCA provider for the content encryption, or
	 *                    {@code null} to use the default one.
	 * @param macProvider The JCA provider for the MAC computation, or
	 *                    {@code null} to use the default one.
	 *
	 * @return The authenticated cipher text.
	 *
	 * @throws JOSEException If encryption failed.
	 */
	public static AuthenticatedCipherText encryptAuthenticated(final SecretKey secretKey,
								   final byte[] iv,
								   final byte[] plainText,
								   final byte[] aad,
								   final Provider ceProvider,
								   final Provider macProvider)
		throws JOSEException {

		// Extract MAC + AES/CBC keys from input secret key
		CompositeKey compositeKey = new CompositeKey(secretKey);

		// Encrypt plain text
		byte[] cipherText = encrypt(compositeKey.getAESKey(), iv, plainText, ceProvider);

		// AAD length to 8 byte array
		byte[] al = AAD.computeLength(aad);

		// Do MAC
		int hmacInputLength = aad.length + iv.length + cipherText.length + al.length;
		byte[] hmacInput = ByteBuffer.allocate(hmacInputLength).put(aad).put(iv).put(cipherText).put(al).array();
		byte[] hmac = HMAC.compute(compositeKey.getMACKey(), hmacInput, macProvider);
		byte[] authTag = Arrays.copyOf(hmac, compositeKey.getTruncatedMACByteLength());

		return new AuthenticatedCipherText(cipherText, authTag);
	}


	/**
	 * Encrypts the specified plain text using the deprecated concat KDF
	 * from JOSE draft suite 09.
	 *
	 * @param header       The JWE header. Must not be {@code null}.
	 * @param secretKey    The secret key. Must be 256 or 512 bits long.
	 *                     Must not be {@code null}.
	 * @param encryptedKey The encrypted key. Must not be {@code null}.
	 * @param iv           The initialisation vector (IV). Must not be
	 *                     {@code null}.
	 * @param plainText    The plain text. Must not be {@code null}.
	 * @param ceProvider   The JCA provider for the content encryption, or
	 *                     {@code null} to use the default one.
	 * @param macProvider  The JCA provider for the MAC computation, or
	 *                     {@code null} to use the default one.
	 *
	 * @return The authenticated cipher text.
	 *
	 * @throws JOSEException If encryption failed.
	 */
	public static AuthenticatedCipherText encryptWithConcatKDF(final JWEHeader header,
		                                                   final SecretKey secretKey,
								   final Base64URL encryptedKey,
								   final byte[] iv,
								   final byte[] plainText,
								   final Provider ceProvider,
								   final Provider macProvider)
		throws JOSEException {

		byte[] epu = null;

		if (header.getCustomParam("epu") instanceof String) {

			epu = new Base64URL((String)header.getCustomParam("epu")).decode();
		}

		byte[] epv = null;

		if (header.getCustomParam("epv") instanceof String) {

			epv = new Base64URL((String)header.getCustomParam("epv")).decode();
		}

		// Generate alternative CEK using concat-KDF
		SecretKey altCEK = LegacyConcatKDF.generateCEK(secretKey, header.getEncryptionMethod(), epu, epv);

		byte[] cipherText = AESCBC.encrypt(altCEK, iv, plainText, ceProvider);

		// Generate content integrity key for HMAC
		SecretKey cik = LegacyConcatKDF.generateCIK(secretKey, header.getEncryptionMethod(), epu, epv);

		String macInput = header.toBase64URL().toString() + "." +
			encryptedKey.toString() + "." +
			Base64URL.encode(iv).toString() + "." +
			Base64URL.encode(cipherText);

		byte[] mac = HMAC.compute(cik, macInput.getBytes(StandardCharset.UTF_8), macProvider);

		return new AuthenticatedCipherText(cipherText, mac);
	}


	/**
	 * Decrypts the specified cipher text using AES/CBC/PKCS5Padding.
	 *
	 * @param secretKey  The AES key. Must not be {@code null}.
	 * @param iv         The initialisation vector (IV). Must not be
	 *                   {@code null}.
	 * @param cipherText The cipher text. Must not be {@code null}.
	 * @param provider   The JCA provider, or {@code null} to use the
	 *                   default one.
	 *
	 * @return The decrypted plain text.
	 *
	 * @throws JOSEException If decryption failed.
	 */
	public static byte[] decrypt(final SecretKey secretKey, 
		                     final byte[] iv,
		                     final byte[] cipherText,
		                     final Provider provider)
		throws JOSEException {

		Cipher cipher = createAESCBCCipher(secretKey, false, iv, provider);

		try {
			return cipher.doFinal(cipherText);

		} catch (Exception e) {

			throw new JOSEException(e.getMessage(), e);
		}
	}


	/**
	 * Decrypts the specified cipher text using AES/CBC/PKCS5Padding/
	 * HMAC-SHA2.
	 * 
	 * <p>See RFC 7518 (JWA), section 5.2.2.2
	 *
	 * <p>See draft-mcgrew-aead-aes-cbc-hmac-sha2-01
	 *
	 * @param secretKey   The secret key. Must be 256 or 512 bits long.
	 *                    Must not be {@code null}.
	 * @param iv          The initialisation vector (IV). Must not be
	 *                    {@code null}.
	 * @param cipherText  The cipher text. Must not be {@code null}.
	 * @param aad         The additional authenticated data. Must not be
	 *                    {@code null}.
	 * @param authTag     The authentication tag. Must not be {@code null}.
	 * @param ceProvider  The JCA provider for the content encryption, or
	 *                    {@code null} to use the default one.
	 * @param macProvider The JCA provider for the MAC computation, or
	 *                    {@code null} to use the default one.
	 *
	 * @return The decrypted plain text.
	 *
	 * @throws JOSEException If decryption failed.
	 */
	public static byte[] decryptAuthenticated(final SecretKey secretKey,
		                                  final byte[] iv,
		                                  final byte[] cipherText,
		                                  final byte[] aad,
		                                  final byte[] authTag,
		                                  final Provider ceProvider,
						  final Provider macProvider)
		throws JOSEException {


		// Extract MAC + AES/CBC keys from input secret key
		CompositeKey compositeKey = new CompositeKey(secretKey);

		// AAD length to 8 byte array
		byte[] al = AAD.computeLength(aad);

		// Check MAC
		int hmacInputLength = aad.length + iv.length + cipherText.length + al.length;
		byte[] hmacInput = ByteBuffer.allocate(hmacInputLength).
			put(aad).
			put(iv).
			put(cipherText).
			put(al).
			array();
		byte[] hmac = HMAC.compute(compositeKey.getMACKey(), hmacInput, macProvider);

		byte[] expectedAuthTag = Arrays.copyOf(hmac, compositeKey.getTruncatedMACByteLength());

		if (! ConstantTimeUtils.areEqual(expectedAuthTag, authTag)) {
			throw new JOSEException("MAC check failed");
		}

		return decrypt(compositeKey.getAESKey(), iv, cipherText, ceProvider);
	}


	/**
	 * Decrypts the specified cipher text using the deprecated concat KDF
	 * from JOSE draft suite 09.
	 *
	 * @param header       The JWE header. Must not be {@code null}.
	 * @param secretKey    The secret key. Must be 256 or 512 bits long.
	 *                     Must not be {@code null}.
	 * @param encryptedKey The encrypted key. Must not be {@code null}.
	 * @param iv           The initialisation vector (IV). Must not be
	 *                     {@code null}.
	 * @param cipherText   The cipher text. Must not be {@code null}.
	 * @param authTag      The authentication tag. Must not be {@code null}.
	 * @param ceProvider   The JCA provider for the content encryption, or
	 *                    {@code null} to use the default one.
	 * @param macProvider The JCA provider for the MAC computation, or
	 *                    {@code null} to use the default one.
	 *
	 * @return The decrypted plain text.
	 *
	 * @throws JOSEException If decryption failed.
	 */
	public static byte[] decryptWithConcatKDF(final JWEHeader header,
						  final SecretKey secretKey,
						  final Base64URL encryptedKey,
						  final Base64URL iv,
						  final Base64URL cipherText,
						  final Base64URL authTag,
						  final Provider ceProvider,
						  final Provider macProvider)
		throws JOSEException {

		byte[] epu = null;

		if (header.getCustomParam("epu") instanceof String) {

			epu = new Base64URL((String)header.getCustomParam("epu")).decode();
		}

		byte[] epv = null;

		if (header.getCustomParam("epv") instanceof String) {

			epv = new Base64URL((String)header.getCustomParam("epv")).decode();
		}
		
		SecretKey cik = LegacyConcatKDF.generateCIK(secretKey, header.getEncryptionMethod(), epu, epv);
		
		String macInput = header.toBase64URL().toString() + "." +
			encryptedKey.toString() + "." +
			iv.toString() + "." +
			cipherText.toString();
		
		byte[] mac = HMAC.compute(cik, macInput.getBytes(StandardCharset.UTF_8), macProvider);
		
		if (! ConstantTimeUtils.areEqual(authTag.decode(), mac)) {
			throw new JOSEException("MAC check failed");
		}

		SecretKey cekAlt = LegacyConcatKDF.generateCEK(secretKey, header.getEncryptionMethod(), epu, epv);

		return AESCBC.decrypt(cekAlt, iv.decode(), cipherText.decode(), ceProvider);
	}


	/**
	 * Prevents public instantiation.
	 */
	private AESCBC() { }
}