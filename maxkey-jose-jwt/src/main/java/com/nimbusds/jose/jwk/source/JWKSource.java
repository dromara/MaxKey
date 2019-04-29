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


import java.util.List;

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.proc.SecurityContext;


/**
 * JSON Web Key (JWK) source. Exposes a method for retrieving JWKs matching a
 * specified selector. An optional context parameter is available to facilitate
 * passing of additional data between the caller and the underlying JWK source
 * (in both directions). Implementations must be thread-safe.
 *
 * @author Vladimir Dzhuvinov
 * @version 2016-06-21
 */
public interface JWKSource <C extends SecurityContext> {
	

	/**
	 * Retrieves a list of JWKs matching the specified selector.
	 *
	 * @param jwkSelector A JWK selector. Must not be {@code null}.
	 * @param context     Optional context, {@code null} if not required.
	 *
	 * @return The matching JWKs, empty list if no matches were found.
	 *
	 * @throws KeySourceException If key sourcing failed.
	 */
	List<JWK> get(final JWKSelector jwkSelector, final C context)
		throws KeySourceException;
}
