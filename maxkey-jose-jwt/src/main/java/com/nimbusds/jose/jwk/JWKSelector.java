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

package com.nimbusds.jose.jwk;


import java.util.*;

import net.jcip.annotations.Immutable;


/**
 * Selects (filters) one or more JSON Web Keys (JWKs) from a JWK set.
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-04-15
 */
@Immutable
public final class JWKSelector {


	/**
	 * The JWK matcher.
	 */
	private final JWKMatcher matcher;


	/**
	 * Creates a new JWK selector (filter).
	 *
	 * @param matcher Specifies the JWK matching criteria. Must not be
	 *                {@code null}.
	 */
	public JWKSelector(final JWKMatcher matcher) {

		if (matcher == null) {
			throw new IllegalArgumentException("The JWK matcher must not be null");
		}

		this.matcher = matcher;
	}


	/**
	 * Returns the JWK matcher.
	 *
	 * @return The JWK matcher.
	 */
	public JWKMatcher getMatcher() {

		return matcher;
	}


	/**
	 * Selects the keys from the specified JWK set according to the
	 * matcher's criteria.
	 *
	 * @param jwkSet The JWK set. May be {@code null}.
	 *
	 * @return The selected keys, ordered by their position in the JWK set,
	 *         empty list if none were matched or the JWK is {@code null}.
	 */
	public List<JWK> select(final JWKSet jwkSet) {

		List<JWK> selectedKeys = new LinkedList<>();

		if (jwkSet == null)
			return selectedKeys;

		for (JWK key: jwkSet.getKeys()) {

			if (matcher.matches(key)) {
				selectedKeys.add(key);
			}
		}

		return selectedKeys;
	}
}
