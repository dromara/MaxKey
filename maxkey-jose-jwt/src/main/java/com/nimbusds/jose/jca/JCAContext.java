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

package com.nimbusds.jose.jca;


import java.security.Provider;
import java.security.SecureRandom;


/**
 * Java Cryptography Architecture (JCA) context, consisting of a JCA
 * {@link java.security.Provider provider} and
 * {@link java.security.SecureRandom secure random generator}.
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-06-08
 */
public class JCAContext {


	/**
	 * The JCA provider.
	 */
	private Provider provider;


	/**
	 * The secure random generator.
	 */
	private SecureRandom randomGen;


	/**
	 * Creates a new default JCA context.
	 */
	public JCAContext() {

		this(null, null);
	}


	/**
	 * Creates a new JCA context.
	 *
	 * @param provider  The JCA provider, {@code null} to use the default
	 *                  system one.
	 * @param randomGen The specific secure random generator, {@code null}
	 *                  to use the default system one.
	 */
	public JCAContext(final Provider provider, final SecureRandom randomGen) {

		this.provider = provider;
		this.randomGen = randomGen;
	}


	/**
	 * Gets the JCA provider to be used for all operations.
	 *
	 * @return The JCA provider to be used for all operations where a more
	 *         specific one is absent, {@code null} implies the default
	 *         system provider.
	 */
	public Provider getProvider() {

		return provider;
	}


	/**
	 * Sets the JCA provider to be used for all operations.
	 *
	 * @param provider The JCA provider to be used for all operations where
	 *                 a more specific one is absent, {@code null} to use
	 *                 the default system provider.
	 */
	public void setProvider(final Provider provider) {

		this.provider = provider;
	}


	/**
	 * Gets the secure random generator. Intended for generation of
	 * initialisation vectors and other purposes that require a secure
	 * random generator.
	 *
	 * @return The specific secure random generator (if available), else
	 *         the default system one.
	 */
	public SecureRandom getSecureRandom() {

		return randomGen != null ? randomGen : new SecureRandom();
	}


	/**
	 * Sets a specific secure random generator for the initialisation
	 * vector and other purposes requiring a random number.
	 *
	 * @param randomGen The secure random generator, {@code null} to use
	 *                  the default system one.
	 */
	public void setSecureRandom(final SecureRandom randomGen) {

		this.randomGen = randomGen;
	}
}
