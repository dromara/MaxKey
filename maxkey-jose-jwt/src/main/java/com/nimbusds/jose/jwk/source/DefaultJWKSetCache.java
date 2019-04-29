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


import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.nimbusds.jose.jwk.JWKSet;


/**
 * JSON Web Key (JWK) set cache implementation.
 *
 * @author Vladimir Dzhuvinov
 * @version 2018-12-01
 */
public class DefaultJWKSetCache implements JWKSetCache {
	
	
	/**
	 * The default lifespan for cached JWK sets (5 minutes).
	 */
	public static final long DEFAULT_LIFESPAN_MINUTES = 5;
	
	
	/**
	 * The lifespan the cached JWK set, in {@link #timeUnit}s, negative
	 * means no expiration
	 */
	private final long lifespan;
	
	
	/**
	 * The lifespan time unit, may be {@code null} if no expiration.
	 */
	private final TimeUnit timeUnit;
	
	
	/**
	 * The cache put timestamp, negative if not specified.
	 */
	private long putTimestamp = -1;
	
	
	/**
	 * Creates a new JWK set, the default lifespan of the cached JWK set is
	 * set to 5 minutes.
	 */
	public DefaultJWKSetCache() {
		
		this(DEFAULT_LIFESPAN_MINUTES, TimeUnit.MINUTES);
	}
	
	
	/**
	 * Creates a new JWK set cache.
	 *
	 * @param lifespan The lifespan of the cached JWK set before it
	 *                 expires, negative means no expiration.
	 * @param timeUnit The lifespan time unit, may be {@code null} if no
	 *                 expiration.
	 */
	public DefaultJWKSetCache(final long lifespan, final TimeUnit timeUnit) {
		
		this.lifespan = lifespan;
		
		if (lifespan > -1 && timeUnit == null) {
			throw new IllegalArgumentException("A time unit must be specified for non-negative lifespans");
		}
		
		this.timeUnit = timeUnit;
	}
	
	
	/**
	 * The cached JWK set, {@code null} if none.
	 */
	private JWKSet jwkSet;
	
	
	@Override
	public void put(final JWKSet jwkSet) {
		
		this.jwkSet = jwkSet;
		
		if (jwkSet != null) {
			putTimestamp = new Date().getTime();
		} else {
			// cache cleared
			putTimestamp = -1;
		}
	}
	
	
	@Override
	public JWKSet get() {
		
		if (isExpired()) {
			jwkSet = null; // clear
		}
		
		return jwkSet;
	}
	
	
	/**
	 * Returns the cache put timestamp.
	 *
	 * @return The cache put timestamp, negative if not specified.
	 */
	public long getPutTimestamp() {
		
		return putTimestamp;
	}
	
	
	/**
	 * Returns {@code true} if the cached JWK set is expired.
	 *
	 * @return {@code true} if expired.
	 */
	public boolean isExpired() {
	
		return putTimestamp > -1 &&
			lifespan > -1 &&
			new Date().getTime() > putTimestamp + TimeUnit.MILLISECONDS.convert(lifespan, timeUnit);
	}
	
	
	/**
	 * Returns the configured lifespan of the cached JWK.
	 *
	 * @param timeUnit The time unit to use.
	 *
	 * @return The configured lifespan, negative means no expiration.
	 */
	public long getLifespan(final TimeUnit timeUnit) {
		
		if (lifespan < 0) {
			return lifespan;
		}
		
		return timeUnit.convert(lifespan, timeUnit);
	}
}
