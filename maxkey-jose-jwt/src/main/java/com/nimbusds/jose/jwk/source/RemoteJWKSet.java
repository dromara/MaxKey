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


import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.nimbusds.jose.RemoteKeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.Resource;
import com.nimbusds.jose.util.ResourceRetriever;
import net.jcip.annotations.ThreadSafe;


/**
 * Remote JSON Web Key (JWK) source specified by a JWK set URL. The retrieved
 * JWK set is cached to minimise network calls. The cache is updated whenever
 * the key selector tries to get a key with an unknown ID.
 *
 * @author Vladimir Dzhuvinov
 * @version 2018-10-28
 */
@ThreadSafe
public class RemoteJWKSet<C extends SecurityContext> implements JWKSource<C> {


	/**
	 * The default HTTP connect timeout for JWK set retrieval, in
	 * milliseconds. Set to 250 milliseconds.
	 */
	public static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 250;


	/**
	 * The default HTTP read timeout for JWK set retrieval, in
	 * milliseconds. Set to 250 milliseconds.
	 */
	public static final int DEFAULT_HTTP_READ_TIMEOUT = 250;


	/**
	 * The default HTTP entity size limit for JWK set retrieval, in bytes.
	 * Set to 50 KBytes.
	 */
	public static final int DEFAULT_HTTP_SIZE_LIMIT = 50 * 1024;


	/**
	 * The JWK set URL.
	 */
	private final URL jwkSetURL;
	

	/**
	 * The JWK set cache.
	 */
	private final JWKSetCache jwkSetCache;


	/**
	 * The JWK set retriever.
	 */
	private final ResourceRetriever jwkSetRetriever;


	/**
	 * Creates a new remote JWK set using the
	 * {@link DefaultResourceRetriever default HTTP resource retriever},
	 * with a HTTP connect timeout set to 250 ms, HTTP read timeout set to
	 * 250 ms and a 50 KByte size limit.
	 *
	 * @param jwkSetURL The JWK set URL. Must not be {@code null}.
	 */
	public RemoteJWKSet(final URL jwkSetURL) {
		this(jwkSetURL, null);
	}


	/**
	 * Creates a new remote JWK set.
	 *
	 * @param jwkSetURL         The JWK set URL. Must not be {@code null}.
	 * @param resourceRetriever The HTTP resource retriever to use,
	 *                          {@code null} to use the
	 *                          {@link DefaultResourceRetriever default
	 *                          one}.
	 */
	public RemoteJWKSet(final URL jwkSetURL,
			    final ResourceRetriever resourceRetriever) {
		
		this(jwkSetURL, resourceRetriever, null);
	}


	/**
	 * Creates a new remote JWK set.
	 *
	 * @param jwkSetURL         The JWK set URL. Must not be {@code null}.
	 * @param resourceRetriever The HTTP resource retriever to use,
	 *                          {@code null} to use the
	 *                          {@link DefaultResourceRetriever default
	 *                          one}.
	 * @param jwkSetCache       The JWK set cache to use, {@code null} to
	 *                          use the {@link DefaultJWKSetCache default
	 *                          one}.
	 */
	public RemoteJWKSet(final URL jwkSetURL,
			    final ResourceRetriever resourceRetriever,
			    final JWKSetCache jwkSetCache) {
		
		if (jwkSetURL == null) {
			throw new IllegalArgumentException("The JWK set URL must not be null");
		}
		this.jwkSetURL = jwkSetURL;

		if (resourceRetriever != null) {
			jwkSetRetriever = resourceRetriever;
		} else {
			jwkSetRetriever = new DefaultResourceRetriever(DEFAULT_HTTP_CONNECT_TIMEOUT, DEFAULT_HTTP_READ_TIMEOUT, DEFAULT_HTTP_SIZE_LIMIT);
		}
		
		if (jwkSetCache != null) {
			this.jwkSetCache = jwkSetCache;
		} else {
			this.jwkSetCache = new DefaultJWKSetCache();
		}
	}


	/**
	 * Updates the cached JWK set from the configured URL.
	 *
	 * @return The updated JWK set.
	 *
	 * @throws RemoteKeySourceException If JWK retrieval failed.
	 */
	private JWKSet updateJWKSetFromURL()
		throws RemoteKeySourceException {
		Resource res;
		try {
			res = jwkSetRetriever.retrieveResource(jwkSetURL);
		} catch (IOException e) {
			throw new RemoteKeySourceException("Couldn't retrieve remote JWK set: " + e.getMessage(), e);
		}
		JWKSet jwkSet;
		try {
			jwkSet = JWKSet.parse(res.getContent());
		} catch (java.text.ParseException e) {
			throw new RemoteKeySourceException("Couldn't parse remote JWK set: " + e.getMessage(), e);
		}
		jwkSetCache.put(jwkSet);
		return jwkSet;
	}


	/**
	 * Returns the JWK set URL.
	 *
	 * @return The JWK set URL.
	 */
	public URL getJWKSetURL() {
		
		return jwkSetURL;
	}


	/**
	 * Returns the HTTP resource retriever.
	 *
	 * @return The HTTP resource retriever.
	 */
	public ResourceRetriever getResourceRetriever() {

		return jwkSetRetriever;
	}
	
	
	/**
	 * Returns the configured JWK set cache.
	 *
	 * @return The JWK set cache.
	 */
	public JWKSetCache getJWKSetCache() {
		
		return jwkSetCache;
	}
	
	
	/**
	 * Returns the cached JWK set.
	 *
	 * @return The cached JWK set, {@code null} if none or expired.
	 */
	public JWKSet getCachedJWKSet() {
		
		return jwkSetCache.get();
	}


	/**
	 * Returns the first specified key ID (kid) for a JWK matcher.
	 *
	 * @param jwkMatcher The JWK matcher. Must not be {@code null}.
	 *
	 * @return The first key ID, {@code null} if none.
	 */
	protected static String getFirstSpecifiedKeyID(final JWKMatcher jwkMatcher) {

		Set<String> keyIDs = jwkMatcher.getKeyIDs();

		if (keyIDs == null || keyIDs.isEmpty()) {
			return null;
		}

		for (String id: keyIDs) {
			if (id != null) {
				return id;
			}
		}
		return null; // No kid in matcher
	}


	/**
	 * {@inheritDoc} The security context is ignored.
	 */
	@Override
	public List<JWK> get(final JWKSelector jwkSelector, final C context)
		throws RemoteKeySourceException {

		// Get the JWK set, may necessitate a cache update
		JWKSet jwkSet = jwkSetCache.get();
		if (jwkSet == null) {
			jwkSet = updateJWKSetFromURL();
		}

		// Run the selector on the JWK set
		List<JWK> matches = jwkSelector.select(jwkSet);

		if (! matches.isEmpty()) {
			// Success
			return matches;
		}

		// Refresh the JWK set if the sought key ID is not in the cached JWK set

		// Looking for JWK with specific ID?
		String soughtKeyID = getFirstSpecifiedKeyID(jwkSelector.getMatcher());
		if (soughtKeyID == null) {
			// No key ID specified, return no matches
			return Collections.emptyList();
		}

		if (jwkSet.getKeyByKeyId(soughtKeyID) != null) {
			// The key ID exists in the cached JWK set, matching
			// failed for some other reason, return no matches
			return Collections.emptyList();
		}

		// Make new HTTP GET to the JWK set URL
		jwkSet = updateJWKSetFromURL();
		if (jwkSet == null) {
			// Retrieval has failed
			return Collections.emptyList();
		}

		// Repeat select, return final result (success or no matches)
		return jwkSelector.select(jwkSet);
	}
}
