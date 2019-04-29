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


import java.text.ParseException;

import com.nimbusds.jose.*;


/**
 * Interface for parsing and processing {@link com.nimbusds.jose.PlainObject
 * unsecured} (plain), {@link com.nimbusds.jose.JWSObject JWS} and
 * {@link com.nimbusds.jose.JWEObject JWE} objects. An optional context
 * parameter is available to facilitate passing of additional data between the
 * caller and the underlying JOSE processor (in both directions).
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-08-20
 */
public interface JOSEProcessor<C extends SecurityContext> {


	/**
	 * Parses and processes the specified JOSE object (unsecured, JWS or
	 * JWE).
	 *
	 * @param compactEncodedJOSE The JOSE object, compact-encoded to a
	 *                           URL-safe string. Must not be {@code null}.
	 * @param context            Optional context, {@code null} if not
	 *                           required.
	 *
	 * @return The payload on success.
	 *
	 * @throws ParseException   If the string couldn't be parsed to a valid
	 *                          JOSE object.
	 * @throws BadJOSEException If the JOSE object is rejected.
	 * @throws JOSEException    If an internal processing exception is
	 *                          encountered.
	 */
	Payload process(final String compactEncodedJOSE, final C context)
		throws ParseException, BadJOSEException, JOSEException;


	/**
	 * Processes the specified JOSE object (unsecured, JWS or JWE).
	 *
	 * @param joseObject The JOSE object. Must not be {@code null}.
	 * @param context    Optional context, {@code null} if not required.
	 *
	 * @return The payload on success.
	 *
	 * @throws BadJOSEException If the JOSE object is rejected.
	 * @throws JOSEException    If an internal processing exception is
	 *                          encountered.
	 */
	Payload process(final JOSEObject joseObject, final C context)
		throws BadJOSEException, JOSEException;


	/**
	 * Processes the specified unsecured (plain) JOSE object, typically by
	 * checking its context.
	 *
	 * @param plainObject The unsecured (plain) JOSE object. Not
	 *                    {@code null}.
	 * @param context     Optional context, {@code null} if not required.
	 *
	 * @return The payload on success.
	 *
	 * @throws BadJOSEException If the unsecured (plain) JOSE object is
	 *                          rejected.
	 * @throws JOSEException    If an internal processing exception is
	 *                          encountered.
	 */
	Payload process(final PlainObject plainObject, final C context)
		throws BadJOSEException, JOSEException;


	/**
	 * Processes the specified JWS object by verifying its signature. The
	 * key candidate(s) are selected by examining the JWS header and / or
	 * the message context.
	 *
	 * @param jwsObject The JWS object. Not {@code null}.
	 * @param context   Optional context, {@code null} if not required.
	 *
	 * @return The payload on success.
	 *
	 * @throws BadJOSEException If the JWS object is rejected, typically
	 *                          due to a bad signature.
	 * @throws JOSEException    If an internal processing exception is
	 *                          encountered.
	 */
	Payload process(final JWSObject jwsObject, final C context)
		throws BadJOSEException, JOSEException;


	/**
	 * Processes the specified JWE object by decrypting it. The key
	 * candidate(s) are selected by examining the JWS header and / or the
	 * message context.
	 *
	 * @param jweObject The JWE object. Not {@code null}.
	 * @param context   Optional context of the JWE object, {@code null} if
	 *                  not required.
	 *
	 * @return The payload on success.
	 *
	 * @throws BadJOSEException If the JWE object is rejected, typically
	 *                          due to failed decryption.
	 * @throws JOSEException    If an internal processing exception is
	 *                          encountered.
	 */
	Payload process(final JWEObject jweObject, final C context)
		throws BadJOSEException, JOSEException;
}

