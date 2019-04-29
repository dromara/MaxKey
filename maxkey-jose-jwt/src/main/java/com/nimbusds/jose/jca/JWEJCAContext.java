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
 * Java Cryptography Architecture (JCA) context intended specifically for
 * JSON Web Encryption (JWE) providers. Allows setting of more specific JCA
 * providers for key encryption, content encryption and MAC computation.
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-06-08
 */
public final class JWEJCAContext extends JCAContext {


	/**
	 * The key encryption provider.
	 */
	private Provider keProvider;


	/**
	 * The content encryption provider.
	 */
	private Provider ceProvider;


	/**
	 * The MAC provider.
	 */
	private Provider macProvider;


	/**
	 * Creates a new default JCA context for JWE.
	 */
	public JWEJCAContext() {

		this(null, null, null, null, null);
	}


	/**
	 * Creates a new JCA context for JWE with the specified JCA providers
	 * and secure random generator.
	 *
	 * @param generalProvider The general JCA provider to be used for all
	 *                        operations where a more specific one is
	 *                        absent, {@code null} to use the default
	 *                        system provider.
	 * @param keProvider      The specific JCA provider to be used for the
	 *                        key encryption, {@code null} to fall back to
	 *                        the general one, and if that is not specified
	 *                        to the default system provider.
	 * @param ceProvider      The specific JCA provider to be used for the
	 *                        content encryption, {@code null} to fall back
	 *                        to the general one, and if that is not
	 *                        specified to the default system provider.
	 * @param macProvider     The specific JCA provider to be used for the
	 *                        MAC computation (where required by the JWE
	 *                        encryption method), {@code null} to fall back
	 *                        to the general one, and if that is not
	 *                        specified to the default system provider.
	 * @param randomGen       The specific secure random generator for the
	 *                        initialisation vector and other purposes
	 *                        requiring a random number, {@code null} to
	 *                        use the default system one.
	 */
	public JWEJCAContext(final Provider generalProvider,
			     final Provider keProvider,
			     final Provider ceProvider,
			     final Provider macProvider,
			     final SecureRandom randomGen) {

		super(generalProvider, randomGen);
		this.keProvider = keProvider;
		this.ceProvider = ceProvider;
		this.macProvider = macProvider;
	}



	/**
	 * Sets a specific JCA provider for the key encryption.
	 *
	 * @param keProvider The specific JCA provider to be used for the key
	 *                   encryption, {@code null} to fall back to the
	 *                   general one, and if that is not specified to the
	 *                   default system provider.
	 */
	public void setKeyEncryptionProvider(final Provider keProvider) {

		this.keProvider = keProvider;
	}


	/**
	 * Gets the specific JCA provider for the key encryption.
	 *
	 * @return The applicable JCA provider, {@code null} implies the
	 *         default system provider.
	 */
	public Provider getKeyEncryptionProvider() {

		return keProvider != null ? keProvider : getProvider();
	}


	/**
	 * Sets a specific JCA provider for the content encryption.
	 *
	 * @param ceProvider The specific JCA provider to be used for the
	 *                   content encryption, {@code null} to fall back to
	 *                   the general one, and if that is not specified to
	 *                   the default system provider.
	 */
	public void setContentEncryptionProvider(final Provider ceProvider) {

		this.ceProvider = ceProvider;
	}


	/**
	 * Gets the specific JCA provider for the content encryption.
	 *
	 * @return The applicable JCA provider, {@code null} implies the
	 *         default system provider.
	 */
	public Provider getContentEncryptionProvider() {

		return ceProvider != null ? ceProvider : getProvider();
	}


	/**
	 * Sets a specific JCA provider for the MAC computation (where required
	 * by the JWE encryption method).
	 *
	 * @param macProvider The specific JCA provider to be used for the MAC
	 *                    computation (where required by the JWE encryption
	 *                    method), {@code null} to fall back to the general
	 *                    one, and if that is not specified to the default
	 *                    system provider.
	 */
	public void setMACProvider(final Provider macProvider) {

		this.macProvider = macProvider;
	}


	/**
	 * Gets the specific JCA provider for the MAC computation (where
	 * required by the JWE encryption method).
	 *
	 * @return The applicable JCA provider, {@code null} implies the
	 *         default system provider.
	 */
	public Provider getMACProvider() {

		return macProvider != null ? macProvider : getProvider();
	}
}
