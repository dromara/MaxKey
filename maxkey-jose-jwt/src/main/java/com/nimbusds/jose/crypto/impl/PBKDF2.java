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
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.util.ByteUtils;
import com.nimbusds.jose.util.IntegerUtils;
import com.nimbusds.jose.util.StandardCharset;


/**
 * Password-Based Key Derivation Function 2 (PBKDF2) utilities. Provides static
 * methods to generate Key Encryption Keys (KEK) from passwords. Adopted from
 * jose4j by Brian Campbell.
 *
 * @author Brian Campbell
 * @author Yavor Vassilev
 * @version 2016-07-26
 */
public class PBKDF2 {


	/**
	 * Zero byte array of length one.
	 */
	public static byte[] ZERO_BYTE = { 0 };


	/**
	 * Formats the specified cryptographic salt for use in PBKDF2.
	 *
	 * <pre>
	 * UTF8(JWE-alg) || 0x00 || Salt Input
	 * </pre>
	 *
	 * @param alg  The JWE algorithm. Must not be {@code null}.
	 * @param salt The cryptographic salt. Must not be empty or null.
	 *
	 * @return The formatted salt for use in PBKDF2.
	 */
	public static byte[] formatSalt(final JWEAlgorithm alg, final byte[] salt)
		throws JOSEException {

		byte[] algBytes = alg.toString().getBytes(StandardCharset.UTF_8);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			out.write(algBytes);
			out.write(ZERO_BYTE);
			out.write(salt);

		} catch (IOException e) {

			throw new JOSEException(e.getMessage(), e);
		}

		return out.toByteArray();
	}


	/**
	 * Derives a PBKDF2 key from the specified password and parameters.
	 *
	 * @param password       The password. Must not be {@code null}.
	 * @param formattedSalt  The formatted cryptographic salt. Must not be
	 *                       {@code null}.
	 * @param iterationCount The iteration count. Must be positive.
	 * @param prfParams      The Pseudo-Random Function (PRF) parameters.
	 *                       Must not be {@code null}.
	 *
	 * @return The derived secret key (with "AES" algorithm).
	 *
	 * @throws JOSEException If the key derivation failed.
	 */
	public static SecretKey deriveKey(final byte[] password,
					  final byte[] formattedSalt,
					  final int iterationCount,
					  final PRFParams prfParams)
		throws JOSEException {

		SecretKey macKey = new SecretKeySpec(password, prfParams.getMACAlgorithm());

		Mac prf = HMAC.getInitMac(macKey, prfParams.getMacProvider());

		int hLen = prf.getMacLength();

		//  1. If dkLen > (2^32 - 1) * hLen, output "derived key too long" and
		//     stop.
		long maxDerivedKeyLength = 4294967295L; // value of (long) Math.pow(2, 32) - 1;
		if (prfParams.getDerivedKeyByteLength() > maxDerivedKeyLength) {
			throw new JOSEException("derived key too long " + prfParams.getDerivedKeyByteLength());
		}

		//  2. Let l be the number of hLen-octet blocks in the derived key,
		//     rounding up, and let r be the number of octets in the last
		//     block:
		//
		//               l = CEIL (dkLen / hLen) ,
		//               r = dkLen - (l - 1) * hLen .
		//
		//     Here, CEIL (x) is the "ceiling" function, i.e. the smallest
		//     integer greater than, or equal to, x.
		int l = (int) Math.ceil((double) prfParams.getDerivedKeyByteLength() / (double) hLen);
		int r = prfParams.getDerivedKeyByteLength() - (l - 1) * hLen;

		//  3. For each block of the derived key apply the function F defined
		//     below to the password P, the salt S, the iteration count c, and
		//     the block index to compute the block:
		//
		//               T_1 = F (P, S, c, 1) ,
		//               T_2 = F (P, S, c, 2) ,
		//               ...
		//               T_l = F (P, S, c, l) ,
		//
		//     where the function F is defined as the exclusive-or sum of the
		//     first c iterates of the underlying pseudorandom function PRF
		//     applied to the password P and the concatenation of the salt S
		//     and the block index i:
		//
		//               F (P, S, c, i) = U_1 \xor U_2 \xor ... \xor U_c
		//
		//     where
		//
		//               U_1 = PRF (P, S || INT (i)) ,
		//               U_2 = PRF (P, U_1) ,
		//               ...
		//               U_c = PRF (P, U_{c-1}) .
		//
		//     Here, INT (i) is a four-octet encoding of the integer i, most
		//     significant octet first.

		//  4. Concatenate the blocks and extract the first dkLen octets to
		//     produce a derived key DK:
		//
		//               DK = T_1 || T_2 ||  ...  || T_l<0..r-1>
		//
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		for (int i = 0; i < l; i++) {
			byte[] block = extractBlock(formattedSalt, iterationCount, i + 1, prf);
			if (i == (l - 1)) {
				block = ByteUtils.subArray(block, 0, r);
			}
			byteArrayOutputStream.write(block, 0, block.length);
		}

		//  5. Output the derived key DK.
		return new SecretKeySpec(byteArrayOutputStream.toByteArray(), "AES");
	}


	/**
	 * Block extraction iteration.
	 *
	 * @param salt           The cryptographic salt. Must not be
	 *                       {@code null}.
	 * @param iterationCount The iteration count.
	 * @param blockIndex     The block index.
	 * @param prf            The pseudo-random function (HMAC). Must not be
	 *                       {@code null.
	 *
	 * @return The block.
	 */
	private static byte[] extractBlock(byte[] salt, int iterationCount, int blockIndex, Mac prf) {

		byte[] currentU;
		byte[] lastU = null;
		byte[] xorU = null;

		for (int i = 1; i <= iterationCount; i++)
		{
			byte[] inputBytes;
			if (i == 1)
			{
				inputBytes = ByteUtils.concat(salt, IntegerUtils.toBytes(blockIndex));
				currentU = prf.doFinal(inputBytes);
				xorU = currentU;
			}
			else
			{
				currentU = prf.doFinal(lastU);
				for (int j = 0; j < currentU.length; j++)
				{
					xorU[j] = (byte) (currentU[j] ^ xorU[j]);
				}
			}

			lastU = currentU;
		}
		return xorU;
	}


	/**
	 * Prevents public instantiation.
	 */
	private PBKDF2() {

	}
}
