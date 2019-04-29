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


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.util.IntegerUtils;
import com.nimbusds.jose.util.StandardCharset;


/**
 * Legacy implementation of a Concatenation Key Derivation Function (KDF) for
 * use by the deprecated {@code A128CBC+HS256} and {@code A256CBC+HS512}
 * encryption methods. Provides static methods for deriving the Content
 * Encryption Key (CEK) and the Content Integrity Key (CIK) from a Content
 * Master Key (CMKs).
 *
 * <p>See draft-ietf-jose-json-web-encryption-08, appendices A.4 and A.5.
 *
 * <p>See NIST.800-56A.
 *
 * @author Vladimir Dzhuvinov
 * @version 2018-01-04
 */
public class LegacyConcatKDF {


	/**
	 * The four byte array (32-byte) representation of 1.
	 */
	private static final byte[] ONE_BYTES = { (byte)0, (byte)0, (byte)0,  (byte)1 };


	/**
	 * The four byte array (32-bit) representation of 0.
	 */
	private static final byte[] ZERO_BYTES = { (byte)0, (byte)0, (byte)0,  (byte)0 };


	/**
	 * The byte array representation of the string "Encryption".
	 */
	private static final byte[] ENCRYPTION_BYTES = {

		(byte)69, (byte)110, (byte)99, (byte)114, (byte)121, (byte)112, (byte)116, (byte)105, (byte)111, (byte)110
	};


	/**
	 * The byte array representation of the string "Integrity".
	 */
	private static final byte[] INTEGRITY_BYTES = {

		(byte)73, (byte)110, (byte)116, (byte)101, (byte)103, (byte)114, (byte)105, (byte)116, (byte)121
	};


	/**
	 * Generates a Content Encryption Key (CEK) from the specified
	 * Content Master Key (CMK) and JOSE encryption method.
	 *
	 * @param key The Content Master Key (CMK). Must not be {@code null}.
	 * @param enc The JOSE encryption method. Must not be {@code null}.
	 * @param epu The value of the encryption PartyUInfo header parameter,
	 *            {@code null} if not specified.
	 * @param epv The value of the encryption PartyVInfo header parameter,
	 *            {@code null} if not specified.
	 *
	 * @return The generated AES CEK.
	 *
	 * @throws JOSEException If CEK generation failed.
	 */
	public static SecretKey generateCEK(final SecretKey key,
					    final EncryptionMethod enc,
					    final byte[] epu,
					    final byte[] epv)
		throws JOSEException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int hashBitLength;

		try {
			// Write [0, 0, 0, 1]
			baos.write(ONE_BYTES);

			// Append CMK
			byte[] cmkBytes = key.getEncoded();
			baos.write(cmkBytes);

			// Append [CEK-bit-length...]
			final int cmkBitLength = cmkBytes.length * 8;
			hashBitLength = cmkBitLength;
			final int cekBitLength = cmkBitLength / 2;
			byte[] cekBitLengthBytes = IntegerUtils.toBytes(cekBitLength);
			baos.write(cekBitLengthBytes);

			// Append the encryption method value, e.g. "A128CBC+HS256"
			byte[] encBytes = enc.toString().getBytes(StandardCharset.UTF_8);
			baos.write(encBytes);

			// Append encryption PartyUInfo=Datalen || Data
			if (epu != null) {

				baos.write(IntegerUtils.toBytes(epu.length));
				baos.write(epu);

			} else {
				baos.write(ZERO_BYTES);
			}

			// Append encryption PartyVInfo=Datalen || Data
			if (epv != null) {

				baos.write(IntegerUtils.toBytes(epv.length));
				baos.write(epv);

			} else {
				baos.write(ZERO_BYTES);
			}

			// Append "Encryption" label
			baos.write(ENCRYPTION_BYTES);

		} catch (IOException e) {

			throw new JOSEException(e.getMessage(), e);
		}

		// Write out
		byte[] hashInput = baos.toByteArray();

		MessageDigest md;

		try {
			// SHA-256 or SHA-512
			md = MessageDigest.getInstance("SHA-" + hashBitLength);

		} catch (NoSuchAlgorithmException e) {

			throw new JOSEException(e.getMessage(), e);
		}

		byte[] hashOutput = md.digest(hashInput);

		byte[] cekBytes = new byte[hashOutput.length / 2];
		System.arraycopy(hashOutput, 0, cekBytes, 0, cekBytes.length);

		return new SecretKeySpec(cekBytes, "AES");
	}


	/**
	 * Generates a Content Integrity Key (CIK) from the specified
	 * Content Master Key (CMK) and JOSE encryption method.
	 *
	 * @param key The Content Master Key (CMK). Must not be {@code null}.
	 * @param enc The JOSE encryption method. Must not be {@code null}.
	 * @param epu The value of the encryption PartyUInfo header parameter,
	 *            {@code null} if not specified.
	 * @param epv The value of the encryption PartyVInfo header parameter,
	 *            {@code null} if not specified.
	 *
	 * @return The generated HMAC SHA CIK.
	 *
	 * @throws JOSEException If CIK generation failed.
	 */
	public static SecretKey generateCIK(final SecretKey key,
					    final EncryptionMethod enc,
					    final byte[] epu,
					    final byte[] epv)
		throws JOSEException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int hashBitLength;
		int cikBitLength;

		try {
			// Write [0, 0, 0, 1]
			baos.write(ONE_BYTES);

			// Append CMK
			byte[] cmkBytes = key.getEncoded();
			baos.write(cmkBytes);

			// Append [CIK-bit-length...]
			final int cmkBitLength = cmkBytes.length * 8;
			hashBitLength = cmkBitLength;
			cikBitLength = cmkBitLength;
			byte[] cikBitLengthBytes = IntegerUtils.toBytes(cikBitLength);
			baos.write(cikBitLengthBytes);

			// Append the encryption method value, e.g. "A128CBC+HS256"
			byte[] encBytes = enc.toString().getBytes(StandardCharset.UTF_8);
			baos.write(encBytes);

			// Append encryption PartyUInfo=Datalen || Data
			if (epu != null) {

				baos.write(IntegerUtils.toBytes(epu.length));
				baos.write(epu);

			} else {
				baos.write(ZERO_BYTES);
			}

			// Append encryption PartyVInfo=Datalen || Data
			if (epv != null) {

				baos.write(IntegerUtils.toBytes(epv.length));
				baos.write(epv);

			} else {
				baos.write(ZERO_BYTES);
			}

			// Append "Encryption" label
			baos.write(INTEGRITY_BYTES);

		} catch (IOException e) {

			throw new JOSEException(e.getMessage(), e);
		}

		// Write out
		byte[] hashInput = baos.toByteArray();

		MessageDigest md;

		try {
			// SHA-256 or SHA-512
			md = MessageDigest.getInstance("SHA-" + hashBitLength);

		} catch (NoSuchAlgorithmException e) {

			throw new JOSEException(e.getMessage(), e);
		}

		// HMACSHA256 or HMACSHA512
		return new SecretKeySpec(md.digest(hashInput), "HMACSHA" + cikBitLength);
	}


	/**
	 * Prevents public instantiation.
	 */
	private LegacyConcatKDF() {

	}
}
