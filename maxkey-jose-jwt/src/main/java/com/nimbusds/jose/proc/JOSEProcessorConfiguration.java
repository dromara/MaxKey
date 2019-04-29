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

package com.nimbusds.jose.proc;


/**
 * JOSE processor configuration.
 *
 * <p>Specifies the required components to process secured JOSE objects:
 *
 * <ul>
 *     <li>To verify JWS objects:
 *         <ul>
 *             <li>Key selector to determine key candidate(s) for JWS
 *                 verification based on the JWS header and application-
 *                 specific context information.
 *             <li>Factory to construct a JWS verifier for a given key
 *                 candidate and JWS header information.
 *         </ul>
 *     <li>To decrypt JWT objects:
 *         <ul>
 *             <li>Key selector to determine key candidate(s) for JWE
 *                 decryption based on the JWS header and application-specific
 *                 context information.
 *             <li>Factory to construct a JWE decrypter for a given key
 *                 candidate and JWE header information.
 *         </ul>
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-08-22
 */
public interface JOSEProcessorConfiguration <C extends SecurityContext> {


	/**
	 * Gets the JWS key selector.
	 *
	 * @return The JWS key selector, {@code null} if not specified.
	 */
	JWSKeySelector<C> getJWSKeySelector();


	/**
	 * Sets the JWS key selector.
	 *
	 * @param jwsKeySelector The JWS key selector, {@code null} if not
	 *                       specified.
	 */
	void setJWSKeySelector(final JWSKeySelector<C> jwsKeySelector);


	/**
	 * Gets the JWE key selector.
	 *
	 * @return The JWE key selector, {@code null} if not specified.
	 */
	JWEKeySelector<C> getJWEKeySelector();


	/**
	 * Sets the JWE key selector.
	 *
	 * @param jweKeySelector The JWE key selector, {@code null} if not
	 *                       specified.
	 */
	void setJWEKeySelector(final JWEKeySelector<C> jweKeySelector);


	/**
	 * Gets the factory for creating JWS verifier instances.
	 *
	 * @return The JWS verifier factory, {@code null} if not specified.
	 */
	JWSVerifierFactory getJWSVerifierFactory();


	/**
	 * Sets the factory for creating JWS verifier instances.
	 *
	 * @param factory The JWS verifier factory, {@code null} if not
	 *                specified.
	 */
	void setJWSVerifierFactory(final JWSVerifierFactory factory);


	/**
	 * Gets the factory for creating JWE decrypter instances.
	 *
	 * @return The JWE decrypter factory, {@code null} if not specified.
	 */
	JWEDecrypterFactory getJWEDecrypterFactory();


	/**
	 * Sets the factory for creating JWE decrypter instances.
	 *
	 * @param factory The JWE decrypter factory, {@code null} if not
	 *                specified.
	 */
	void setJWEDecrypterFactory(final JWEDecrypterFactory factory);
}
