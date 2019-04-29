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


/**
 * Integer utilities.
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-05-12
 */
public class IntegerUtils {


	/**
	 * Returns a four byte array representation of the specified integer.
	 *
	 * @param intValue The integer to be converted.
	 *
	 * @return The byte array representation of the integer.
	 */
	public static byte[] toBytes(int intValue) {

		byte[] res = new byte[4];
		res[0] = (byte) (intValue >>> 24);
		res[1] = (byte) ((intValue >>> 16) & 0xFF);
		res[2] = (byte) ((intValue >>> 8) & 0xFF);
		res[3] = (byte) (intValue & 0xFF);
		return res;
	}


	/**
	 * Prevents public instantiation.
	 */
	private IntegerUtils() {

	}
}
