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

package com.nimbusds.jose.util;


import java.math.BigInteger;


/**
 * Big integer utilities.
 *
 * @author Vladimir Dzhuvinov
 * @version 2013-03-21
 */
public class BigIntegerUtils {


	/**
	 * Returns a byte array representation of the specified big integer 
	 * without the sign bit.
	 * 
	 * @param bigInt The big integer to be converted. Must not be
	 *               {@code null}.
	 *
	 * @return A byte array representation of the big integer, without the
	 *         sign bit.
	 */
	public static byte[] toBytesUnsigned(final BigInteger bigInt) {

		// Copied from Apache Commons Codec 1.8

		int bitlen = bigInt.bitLength();

		// round bitlen
		bitlen = ((bitlen + 7) >> 3) << 3;
		final byte[] bigBytes = bigInt.toByteArray();

		if (((bigInt.bitLength() % 8) != 0) && (((bigInt.bitLength() / 8) + 1) == (bitlen / 8))) {
		
			return bigBytes;
		
		}

		// set up params for copying everything but sign bit
		int startSrc = 0;
		int len = bigBytes.length;

		// if bigInt is exactly byte-aligned, just skip signbit in copy
		if ((bigInt.bitLength() % 8) == 0) {
			
			startSrc = 1;
			len--;
		}
		
		final int startDst = bitlen / 8 - len; // to pad w/ nulls as per spec
		final byte[] resizedBytes = new byte[bitlen / 8];
		System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, len);
		return resizedBytes;
	}


	/**
	 * Prevents public instantiation.
	 */
	private BigIntegerUtils() {

	}
}