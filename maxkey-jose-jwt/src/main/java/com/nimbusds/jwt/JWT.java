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

package com.nimbusds.jwt;


import java.io.Serializable;
import java.text.ParseException;

import com.nimbusds.jose.Header;
import com.nimbusds.jose.util.Base64URL;


/**
 * JSON Web Token (JWT) interface.
 *
 * @author Vladimir Dzhuvinov
 * @version 2014-08-19
 */
public interface JWT extends Serializable {


	/**
	 * Gets the JOSE header of the JSON Web Token (JWT).
	 *
	 * @return The header.
	 */
	Header getHeader();


	/**
	 * Gets the claims set of the JSON Web Token (JWT).
	 *
	 * @return The claims set, {@code null} if not available (for an 
	 *         encrypted JWT that isn't decrypted).
	 *
	 * @throws ParseException If the payload of the JWT doesn't represent a
	 *                        valid JSON object and a JWT claims set.
	 */
	JWTClaimsSet getJWTClaimsSet()
		throws ParseException;


	/**
	 * Gets the original parsed Base64URL parts used to create the JSON Web
	 * Token (JWT).
	 *
	 * @return The original Base64URL parts used to creates the JWT,
	 *         {@code null} if the JWT was created from scratch. The 
	 *         individual parts may be empty or {@code null} to indicate a 
	 *         missing part.
	 */
	Base64URL[] getParsedParts();


	/**
	 * Gets the original parsed string used to create the JSON Web Token 
	 * (JWT).
	 *
	 * @see #getParsedParts
	 * 
	 * @return The parsed string used to create the JWT, {@code null} if 
	 *         the JWT was created from scratch.
	 */
	String getParsedString();


	/**
	 * Serialises the JSON Web Token (JWT) to its compact format consisting 
	 * of Base64URL-encoded parts delimited by period ('.') characters.
	 *
	 * @return The serialised JWT.
	 *
	 * @throws IllegalStateException If the JWT is not in a state that 
	 *                               permits serialisation.
	 */
	String serialize();
}
