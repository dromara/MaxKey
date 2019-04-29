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

package com.nimbusds.jose.jwk.source;


import javax.crypto.SecretKey;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.proc.SecurityContext;
import net.jcip.annotations.Immutable;


/**
 * JSON Web Key (JWK) source backed by an immutable secret.
 *
 * @author Vladimir Dzhuvinov
 * @version 2016-04-10
 */
@Immutable
public class ImmutableSecret<C extends SecurityContext> extends ImmutableJWKSet<C> {
	

	/**
	 * Creates a new JSON Web Key (JWK) source backed by an immutable
	 * secret.
	 *
	 * @param secret The secret. Must not be empty or {@code null}.
	 */
	public ImmutableSecret(final byte[] secret) {

		super(new JWKSet(new OctetSequenceKey.Builder(secret).build()));
	}


	/**
	 * Creates a new JSON Web Key (JWK) source backed by an immutable
	 * secret key.
	 *
	 * @param secretKey The secret key. Must not be {@code null}.
	 */
	public ImmutableSecret(final SecretKey secretKey) {

		super(new JWKSet(new OctetSequenceKey.Builder(secretKey).build()));
	}


	/**
	 * Returns the secret.
	 *
	 * @return The secret.
	 */
	public byte[] getSecret() {

		return ((OctetSequenceKey) getJWKSet().getKeys().get(0)).toByteArray();
	}


	/**
	 * Returns the secret key.
	 *
	 * @return The secret key.
	 */
	public SecretKey getSecretKey() {

		return ((OctetSequenceKey) getJWKSet().getKeys().get(0)).toSecretKey();
	}
}
