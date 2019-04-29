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


import java.security.Key;
import java.util.List;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeySourceException;


/**
 * Interface for selecting key candidates for verifying a JSON Web Signature
 * (JWS) object. Applications should utilise this interface or a similar
 * framework to determine whether a received JWS object (or signed JWT) is
 * eligible for {@link com.nimbusds.jose.JWSVerifier verification} and further
 * processing.
 *
 * <p>The key selection should be based on application specific criteria, such
 * as recognised header parameters referencing the key (e.g. {@code kid},
 * {@code x5t}) and / or the JWS object {@link SecurityContext}.
 *
 * <p>See JSON Web Signature (JWS), Appendix D. Notes on Key Selection for
 * suggestions.
 *
 * <p>Possible key types:
 *
 * <ul>
 *     <li>{@link javax.crypto.SecretKey} for HMAC keys.
 *     <li>{@link java.security.interfaces.RSAPublicKey} public RSA keys.
 *     <li>{@link java.security.interfaces.ECPublicKey} public EC keys.
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 * @version 2016-06-21
 */
public interface JWSKeySelector<C extends SecurityContext>  {


	/**
	 * Selects key candidates for verifying a JWS object.
	 *
	 * @param header  The header of the JWS object. Must not be
	 *                {@code null}.
	 * @param context Optional context of the JWS object, {@code null} if
	 *                not required.
	 *
	 * @return The key candidates in trial order, empty list if none.
	 *
	 * @throws KeySourceException If a key sourcing exception is
	 *                            encountered, e.g. on remote JWK
	 *                            retrieval.
	 */
	List<? extends Key> selectJWSKeys(final JWSHeader header, final C context)
		throws KeySourceException;
}
