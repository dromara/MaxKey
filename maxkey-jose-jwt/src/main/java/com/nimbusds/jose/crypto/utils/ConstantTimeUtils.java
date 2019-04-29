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

package com.nimbusds.jose.crypto.utils;


/**
 * Array utilities.
 *
 * @author Vladimir Dzhuvinov
 * @version 2017-04-26
 */
public class ConstantTimeUtils {


	/**
	 * Checks the specified arrays for equality in constant time. Intended
	 * to mitigate timing attacks.
	 *
	 * @param a The first array. Must not be {@code null}.
	 * @param b The second array. Must not be {@code null}.
	 *
	 * @return {@code true} if the two arrays are equal, else
	 *         {@code false}.
	 */
	public static boolean areEqual(final byte[] a, final byte[] b) {

		// From http://codahale.com/a-lesson-in-timing-attacks/

		if (a.length != b.length) {
			return false;
		}

		int result = 0;
		for (int i = 0; i < a.length; i++) {
			result |= a[i] ^ b[i];
		}

		return result == 0;
	}


	/**
	 * Prevents public instantiation.
	 */
	private ConstantTimeUtils() { }
}
