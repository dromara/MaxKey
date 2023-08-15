/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

/*
 * StringUtil.java
 */

package org.dromara.maxkey.crypto.cert;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Locale;

import org.bouncycastle.asn1.ASN1Integer;

/**
 * String utilities.
 * 
 * @author 
 */
public class StringUtil
{
	/**
	 * Convert the supplied object to hex characters sub-divided by spaces every given number of characters,
	 * and left-padded with zeros to fill group size.
	 * 
	 * @param obj Object (byte array, BigInteger, DERInteger)
	 * @param groupSize number of characters to group hex characters by
	 * @param separator grouping separator
	 * @return Hex string
	 * @throws IllegalArgumentException if obj is not a BigInteger, byte array, or a DERInteger, or groupSize
	 *             &lt; 0
	 */
	public static StringBuilder toHex(Object obj, int groupSize, String separator)
	{
		if (groupSize < 0)
		{
			throw new IllegalArgumentException("Group size must be >= 0");
		}
		BigInteger bigInt;
		if (obj instanceof BigInteger)
		{
			bigInt = (BigInteger) obj;
		}
		else if (obj instanceof byte[])
		{
			bigInt = new BigInteger(1, (byte[]) obj);
		}
		else if (obj instanceof ASN1Integer)
		{
			bigInt = ((ASN1Integer) obj).getValue();
		}
		else
		{
			throw new IllegalArgumentException("Don't know how to convert " + obj.getClass().getName() +
			    " to a hex string");
		}

		// Convert to hex

		StringBuilder sb = new StringBuilder(bigInt.toString(16).toUpperCase(Locale.ENGLISH));

		// Left-pad if asked and necessary

		if (groupSize != 0)
		{
			int len = groupSize - (sb.length() % groupSize);
			if (len != groupSize)
			{
				for (int i = 0; i < len; i++)
				{
					sb.insert(0, '0');
				}
			}
		}

		// Place separator at every groupSize characters

		if (sb.length() > groupSize && !separator.isEmpty())
		{
			for (int i = groupSize; i < sb.length(); i += groupSize + separator.length())
			{
				sb.insert(i, separator);
			}
		}

		return sb;
	}
	
	// 1. String --> InputStream
	public static  InputStream String2InputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}

	// 2. InputStream --> String
	public static String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}
	
}
