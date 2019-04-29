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
import java.security.interfaces.RSAPrivateKey;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;


/**
 * RSA JWK conversion utility.
 */
public class RSAKeyUtils {
	
	
	/**
	 * Returns the private RSA key of the specified RSA JWK. Supports
	 * PKCS#11 keys stores.
	 *
	 * @param rsaJWK The RSA JWK. Must not be {@code null}.
	 *
	 * @return The private RSA key.
	 *
	 * @throws JOSEException If the RSA JWK doesn't contain a private part.
	 */
	public static PrivateKey toRSAPrivateKey(final RSAKey rsaJWK)
		throws JOSEException {
		
		if (! rsaJWK.isPrivate()) {
			throw new JOSEException("The RSA JWK doesn't contain a private part");
		}
		
		return rsaJWK.toPrivateKey();
	}
	
	
	/**
	 * Returns the length in bits of the specified RSA private key.
	 *
	 * @param privateKey The RSA private key. Must not be {@code null}.
	 *
	 * @return The key length in bits, -1 if the length couldn't be
	 *         determined, e.g. for a PKCS#11 backed key which doesn't
	 *         expose an RSAPrivateKey interface or support the
	 *         {@code getModulus()} method.
	 */
	public static int keyBitLength(final PrivateKey privateKey) {
		
		if (! (privateKey instanceof RSAPrivateKey)) {
			return -1; // May be an PKCS#11 backed key
		}
		
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)privateKey;
		
		try {
			return rsaPrivateKey.getModulus().bitLength();
		} catch (Exception e) {
			// Some PKCS#11 backed keys still have the
			// RSAPrivateKey interface, but will throw an exception
			// here
			return -1;
		}
	}
}
