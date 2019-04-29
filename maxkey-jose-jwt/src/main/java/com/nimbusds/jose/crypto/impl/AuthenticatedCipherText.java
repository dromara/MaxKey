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


import net.jcip.annotations.Immutable;


/**
 * Authenticated cipher text. This class is immutable.
 *
 * @author Vladimir Dzhuvinov
 * @version 2013-05-06
 */
@Immutable
public final class AuthenticatedCipherText {


	/**
	 * The cipher text.
	 */
	private final byte[] cipherText;


	/**
	 * The authentication tag.
	 */
	private final byte[] authenticationTag;


	/**
	 * Creates a new authenticated cipher text.
	 *
	 * @param cipherText        The cipher text. Must not be {@code null}.
	 * @param authenticationTag The authentication tag. Must not be
	 *                          {@code null}.
	 */
	public AuthenticatedCipherText(final byte[] cipherText, final byte[] authenticationTag) {

		if (cipherText == null)
			throw new IllegalArgumentException("The cipher text must not be null");

		this.cipherText = cipherText;


		if (authenticationTag == null)
			throw new IllegalArgumentException("The authentication tag must not be null");

		this.authenticationTag = authenticationTag;
	}


	/**
	 * Gets the cipher text.
	 *
	 * @return The cipher text.
	 */
	public byte[] getCipherText() {

		return cipherText;
	}


	/**
	 * Gets the authentication tag.
	 *
	 * @return The authentication tag.
	 */
	public byte[] getAuthenticationTag() {

		return authenticationTag;
	}
}