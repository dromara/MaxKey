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

package com.nimbusds.jwt.proc;


import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.*;


/**
 * Interface for parsing and processing {@link com.nimbusds.jwt.PlainJWT
 * unsecured} (plain), {@link com.nimbusds.jwt.SignedJWT signed} and
 * {@link com.nimbusds.jwt.EncryptedJWT encrypted} JSON Web Tokens (JWTs).
 * An optional context parameter is available to facilitate passing of
 * additional data between the caller and the underlying JOSE processor (in
 * both directions).
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-08-20
 */
public interface JWTProcessor<C extends SecurityContext> {


	/**
	 * Parses and processes the specified JWT (unsecured, signed or
	 * encrypted).
	 *
	 * @param jwtString The JWT, compact-encoded to a URL-safe string. Must
	 *                  not be {@code null}.
	 * @param context   Optional context, {@code null} if not required.
	 *
	 * @return The JWT claims set on success.
	 *
	 * @throws ParseException   If the string couldn't be parsed to a valid
	 *                          JWT.
	 * @throws BadJOSEException If the JWT is rejected.
	 * @throws JOSEException    If an internal processing exception is
	 *                          encountered.
	 */
	JWTClaimsSet process(final String jwtString, final C context)
		throws ParseException, BadJOSEException, JOSEException;


	/**
	 * Processes the specified JWT (unsecured, signed or encrypted).
	 *
	 * @param jwt     The JWT. Must not be {@code null}.
	 * @param context Optional context, {@code null} if not required.
	 *
	 * @return The JWT claims set on success.
	 *
	 * @throws BadJOSEException If the JWT is rejected.
	 * @throws JOSEException    If an internal processing exception is
	 *                          encountered.
	 */
	JWTClaimsSet process(final JWT jwt, final C context)
		throws BadJOSEException, JOSEException;


	/**
	 * Processes the specified unsecured (plain) JWT, typically by checking
	 * its context.
	 *
	 * @param plainJWT The unsecured (plain) JWT. Not {@code null}.
	 * @param context  Optional context, {@code null} if not required.
	 *
	 * @return The JWT claims set on success.
	 *
	 * @throws BadJOSEException If the unsecured (plain) JWT is rejected,
	 *                          after examining the context or due to the
	 *                          payload not being a JSON object.
	 * @throws JOSEException    If an internal processing exception is
	 *                          encountered.
	 */
	JWTClaimsSet process(final PlainJWT plainJWT, final C context)
		throws BadJOSEException, JOSEException;


	/**
	 * Processes the specified signed JWT by verifying its signature. The
	 * key candidate(s) are selected by examining the JWS header and / or
	 * the message context.
	 *
	 * @param signedJWT The signed JWT. Not {@code null}.
	 * @param context   Optional context, {@code null} if not required.
	 *
	 * @return The JWT claims set on success.
	 *
	 * @throws BadJOSEException If the signed JWT is rejected, typically
	 *                          due to a bad signature or the payload not
	 *                          being a JSON object.
	 * @throws JOSEException    If an internal processing exception is
	 *                          encountered.
	 */
	JWTClaimsSet process(final SignedJWT signedJWT, final C context)
		throws BadJOSEException, JOSEException;


	/**
	 * Processes the specified encrypted JWT by decrypting it. The key
	 * candidate(s) are selected by examining the JWS header and / or the
	 * message context.
	 *
	 * @param encryptedJWT The encrypted JWT. Not {@code null}.
	 * @param context      Optional context, {@code null} if not required.
	 *
	 * @return The JWT claims set on success.
	 *
	 * @throws BadJOSEException If the encrypted JWT is rejected, typically
	 *                          due to failed decryption or the payload not
	 *                          being a JSON object.
	 * @throws JOSEException    If an internal processing exception is
	 *                          encountered.
	 */
	JWTClaimsSet process(final EncryptedJWT encryptedJWT, final C context)
		throws BadJOSEException, JOSEException;
}
