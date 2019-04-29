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

package com.nimbusds.jose.jwk.source;


import com.nimbusds.jose.jwk.JWKSet;


/**
 * JSON Web Key (JWK) set cache.
 *
 * @author Vladimir Dzhuvinov
 * @version 2018-10-28
 */
public interface JWKSetCache {
	
	
	/**
	 * Puts the specified JWK set into the cache or clears the cache.
	 *
	 * @param jwkSet The JWK set to cache, {@code null} to clear the cache.
	 */
	void put(final JWKSet jwkSet);
	
	
	/**
	 * Gets the cached JWK set.
	 *
	 * @return The cached JWK set, {@code null} if none or expired.
	 */
	JWKSet get();
}
