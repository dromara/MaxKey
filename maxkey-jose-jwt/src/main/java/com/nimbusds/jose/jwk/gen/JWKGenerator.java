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

package com.nimbusds.jose.jwk.gen;


import java.security.KeyStore;
import java.util.Set;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyOperation;
import com.nimbusds.jose.jwk.KeyUse;


/**
 * Abstract JWK generator.
 *
 * @author Vladimir Dzhuvinov
 * @version 2018-09-07
 */
public abstract class JWKGenerator<T extends JWK> {
	
	
	/**
	 * The key use, optional.
	 */
	protected KeyUse use;
	
	
	/**
	 * The key operations, optional.
	 */
	protected Set<KeyOperation> ops;
	
	
	/**
	 * The intended JOSE algorithm for the key, optional.
	 */
	protected Algorithm alg;
	
	
	/**
	 * The key ID, optional.
	 */
	protected String kid;
	
	
	/**
	 * If {@code true} sets the ID of the JWK to the SHA-256 thumbprint of
	 * the JWK.
	 */
	protected boolean x5tKid;
	
	
	/**
	 * Reference to the underlying key store, {@code null} if none.
	 */
	protected KeyStore keyStore;
	
	
	/**
	 * Sets the use ({@code use}) of the JWK.
	 *
	 * @param use The key use, {@code null} if not specified or if 
	 *            the key is intended for signing as well as 
	 *            encryption.
	 *
	 * @return This generator.
	 */
	public JWKGenerator<T> keyUse(final KeyUse use) {
		
		this.use = use;
		return this;
	}
	
	
	/**
	 * Sets the operations ({@code key_ops}) of the JWK.
	 *
	 * @param ops The key operations, {@code null} if not
	 *            specified.
	 *
	 * @return This generator.
	 */
	public JWKGenerator<T> keyOperations(final Set<KeyOperation> ops) {
		
		this.ops = ops;
		return this;
	}
	
	
	/**
	 * Sets the intended JOSE algorithm ({@code alg}) for the JWK.
	 *
	 * @param alg The intended JOSE algorithm, {@code null} if not 
	 *            specified.
	 *
	 * @return This generator.
	 */
	public JWKGenerator<T> algorithm(final Algorithm alg) {
		
		this.alg = alg;
		return this;
	}
	
	/**
	 * Sets the ID ({@code kid}) of the JWK. The key ID can be used 
	 * to match a specific key. This can be used, for instance, to 
	 * choose a key within a {@link JWKSet} during key rollover. 
	 * The key ID may also correspond to a JWS/JWE {@code kid}
	 * header parameter value.
	 *
	 * @param kid The key ID, {@code null} if not specified.
	 *
	 * @return This generator.
	 */
	public JWKGenerator<T> keyID(final String kid) {
		
		this.kid = kid;
		return this;
	}
	
	
	/**
	 * Sets the ID ({@code kid}) of the JWK to its SHA-256 JWK
	 * thumbprint (RFC 7638). The key ID can be used to match a
	 * specific key. This can be used, for instance, to choose a
	 * key within a {@link JWKSet} during key rollover. The key ID
	 * may also correspond to a JWS/JWE {@code kid} header
	 * parameter value.
	 *
	 * @param x5tKid If {@code true} sets the ID of the JWK to the SHA-256
	 *               JWK thumbprint.
	 *
	 * @return This generator.
	 */
	public JWKGenerator<T> keyIDFromThumbprint(final boolean x5tKid) {
		
		this.x5tKid = x5tKid;
		return this;
	}
	
	
	/**
	 * Sets the underlying key store.
	 *
	 * @param keyStore Reference to the underlying key store,
	 *                 {@code null} if none.
	 *
	 * @return This generator.
	 */
	public JWKGenerator<T> keyStore(final KeyStore keyStore) {
		
		this.keyStore = keyStore;
		return this;
	}
	
	
	/**
	 * Generates the JWK according to the set parameters.
	 *
	 * @return The generated JWK.
	 *
	 * @throws JOSEException If the key generation failed.
	 */
	public abstract T generate() throws JOSEException;
}
