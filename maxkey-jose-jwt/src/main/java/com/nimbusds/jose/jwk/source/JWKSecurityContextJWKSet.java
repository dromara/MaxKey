/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2019, Connect2id Ltd and contributors.
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

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.proc.JWKSecurityContext;


/**
 * A {@link JWKSource} backed by keys found in the {@link JWKSecurityContext}.
 *
 * @author Rob Winch
 * @author Josh Cummings
 * @version 2019-01-10
 */
public class JWKSecurityContextJWKSet implements JWKSource<JWKSecurityContext> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<JWK> get(final JWKSelector jwkSelector, final JWKSecurityContext context) throws KeySourceException {
		if (context == null) {
			throw new IllegalArgumentException("Security Context must not be null");
		}

		return jwkSelector.select(new JWKSet(context.getKeys()));
	}
}
