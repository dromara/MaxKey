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


import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import net.minidev.json.JSONArray;


/**
 * X.509 certificate chain utilities.
 *
 * @author Vladimir Dzhuvinov
 * @version 2018-02-27
 */
public class X509CertChainUtils {

	
	/**
	 * Converts the specified JSON array of strings to a list of Base64
	 * encoded objects.
	 *
	 * @param jsonArray The JSON array of string, {@code null} if not
	 *                  specified.
	 *
	 * @return The Base64 list, {@code null} if not specified.
	 *
	 * @throws ParseException If parsing failed.
	 */
	public static List<Base64> toBase64List(final JSONArray jsonArray)
		throws ParseException {
		
		if (jsonArray == null)
			return null;

		List<Base64> chain = new LinkedList<>();

		for (int i=0; i < jsonArray.size(); i++) {

			Object item = jsonArray.get(i);

			if (item == null) {
				throw new ParseException("The X.509 certificate at position " + i + " must not be null", 0);
			}

			if  (! (item instanceof String)) {
				throw new ParseException("The X.509 certificate at position " + i + " must be encoded as a Base64 string", 0);
			}

			chain.add(new Base64((String)item));
		}

		return chain;
	}
	
	
	/**
	 * Parses a X.509 certificate chain from the specified Base64-encoded
	 * DER-encoded representation.
	 *
	 * @param b64List The Base64-encoded DER-encoded X.509 certificate
	 *                chain, {@code null} if not specified.
	 *
	 * @return The X.509 certificate chain, {@code null} if not specified.
	 *
	 * @throws ParseException If parsing failed.
	 */
	public static List<X509Certificate> parse(final List<Base64> b64List)
		throws ParseException {
		
		if (b64List == null)
			return null;
		
		List<X509Certificate> out = new LinkedList<>();
		
		for (int i=0; i < b64List.size(); i++) {
			
			if (b64List.get(i)== null) continue; // skip
			
			X509Certificate cert = X509CertUtils.parse(b64List.get(i).decode());
			
			if (cert == null) {
				throw new ParseException("Invalid X.509 certificate at position " + i, 0);
			}
			
			out.add(cert);
		}
		
		return out;
	}

	
	/**
	 * Prevents public instantiation.
	 */
	private X509CertChainUtils() {}
}