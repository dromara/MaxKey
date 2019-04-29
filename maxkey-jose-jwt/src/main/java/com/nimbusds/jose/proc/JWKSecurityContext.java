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

package com.nimbusds.jose.proc;


import java.util.List;

import com.nimbusds.jose.jwk.JWK;

/**
 * A security context that contains JSON Web Keys (JWK). Typically, this would
 * be used when the keys are evaluated outside of token validation.
 *
 * @author Rob Winch
 * @author Josh Cummings
 * @version 2019-01-10
 */
public class JWKSecurityContext implements SecurityContext {

	private final List<JWK> keys;

	/**
	 * Constructs a {@code JWKSecurityContext} with the provided
	 * parameters.
	 *
	 * @param keys The list of keys.
	 */
	public JWKSecurityContext(final List<JWK> keys) {
		this.keys = keys;

		if (keys == null) {
			throw new IllegalArgumentException("The list of keys must not be null");
		}
	}

	/**
	 * Gets the list of {@link JWK}s.
	 *
	 * @return The {@code JWK} list.
	 */
	public List<JWK> getKeys() {
		return keys;
	}
}
