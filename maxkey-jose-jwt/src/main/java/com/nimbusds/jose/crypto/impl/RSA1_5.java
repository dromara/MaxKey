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


import java.security.PrivateKey;
import java.security.Provider;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.util.ByteUtils;
import net.jcip.annotations.ThreadSafe;


/**
 * RSAES-PKCS1-V1_5 methods for Content Encryption Key (CEK) encryption and
 * decryption. This class is thread-safe.
 *
 * @author Vladimir Dzhuvinov
 * @version 2017-06-01
 */
@ThreadSafe
public class RSA1_5 {


	/**
	 * Encrypts the specified Content Encryption Key (CEK).
	 *
	 * @param pub      The public RSA key. Must not be {@code null}.
	 * @param cek      The Content Encryption Key (CEK) to encrypt. Must
	 *                 not be {@code null}.
	 * @param provider The JCA provider, or {@code null} to use the default
	 *                 one.
	 *
	 * @return The encrypted Content Encryption Key (CEK).
	 *
	 * @throws JOSEException If encryption failed.
	 */
	public static byte[] encryptCEK(final RSAPublicKey pub, final SecretKey cek, Provider provider)
		throws JOSEException {

		try {
			Cipher cipher = CipherHelper.getInstance("RSA/ECB/PKCS1Padding", provider);
			cipher.init(Cipher.ENCRYPT_MODE, pub);
			return cipher.doFinal(cek.getEncoded());
			
		} catch (IllegalBlockSizeException e) {
			throw new JOSEException("RSA block size exception: The RSA key is too short, try a longer one", e);
		} catch (Exception e) {
			// java.security.NoSuchAlgorithmException
			// java.security.InvalidKeyException
			throw new JOSEException("Couldn't encrypt Content Encryption Key (CEK): " + e.getMessage(), e);
		}
	}


	/**
	 * Decrypts the specified encrypted Content Encryption Key (CEK).
	 *
	 * @param priv         The private RSA key. Must not be {@code null}.
	 * @param encryptedCEK The encrypted Content Encryption Key (CEK) to
	 *                     decrypt. Must not be {@code null}.
	 * @param provider     The JCA provider, or {@code null} to use the
	 *                     default one.
	 *
	 * @return The decrypted Content Encryption Key (CEK), {@code null} if
	 *         there was a CEK key length mismatch.
	 *
	 * @throws JOSEException If decryption failed.
	 */
	public static SecretKey decryptCEK(final PrivateKey priv,
		                           final byte[] encryptedCEK,
		                           final int keyLength,
		                           final Provider provider)
		throws JOSEException {

		try {
			Cipher cipher = CipherHelper.getInstance("RSA/ECB/PKCS1Padding", provider);
			cipher.init(Cipher.DECRYPT_MODE, priv);
			byte[] secretKeyBytes = cipher.doFinal(encryptedCEK);

			if (ByteUtils.safeBitLength(secretKeyBytes) != keyLength) {
				// CEK key length mismatch
				return null;
			}

			return new SecretKeySpec(secretKeyBytes, "AES");

		} catch (Exception e) {

			// java.security.NoSuchAlgorithmException
			// java.security.InvalidKeyException
			// javax.crypto.IllegalBlockSizeException
			// javax.crypto.BadPaddingException
			throw new JOSEException("Couldn't decrypt Content Encryption Key (CEK): " + e.getMessage(), e);
		}
	}


	/**
	 * Prevents public instantiation.
	 */
	private RSA1_5() { }
}