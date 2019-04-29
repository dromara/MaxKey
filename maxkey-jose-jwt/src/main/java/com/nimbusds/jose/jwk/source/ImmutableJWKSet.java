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


import java.util.List;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import net.jcip.annotations.Immutable;


/**
 * JSON Web Key (JWK) source backed by an immutable JWK set.
 *
 * @author Vladimir Dzhuvinov
 * @version 2016-04-10
 */
@Immutable
public class ImmutableJWKSet<C extends SecurityContext> implements JWKSource<C> {


	/**
	 * The JWK set.
	 */
	private final JWKSet jwkSet;


	/**
	 * Creates a new JWK source backed by an immutable JWK set.
	 *
	 * @param jwkSet The JWK set. Must not be {@code null}.
	 */
	public ImmutableJWKSet(final JWKSet jwkSet) {
		if (jwkSet == null) {
			throw new IllegalArgumentException("The JWK set must not be null");
		}
		this.jwkSet = jwkSet;
	}


	/**
	 * Returns the JWK set.
	 *
	 * @return The JWK set.
	 */
	public JWKSet getJWKSet() {
		return jwkSet;
	}


	/**
	 * {@inheritDoc} The security context is ignored.
	 */
	@Override
	public List<JWK> get(final JWKSelector jwkSelector, final C context) {

		return jwkSelector.select(jwkSet);
	}
}
