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


import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;


/**
 * JWT claims set verifier. Ensures the claims set of a JWT that is being
 * {@link JWTProcessor processed} complies with an application's requirements.
 *
 * <p>An application may implement JWT claims checks such as:
 *
 * <ul>
 *     <li>The JWT is within the required validity time window;
 *     <li>has a specific issuer;
 *     <li>has a specific audience;
 *     <li>has a specific subject;
 *     <li>etc.
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 * @version 2016-07-25
 * @since 4.23
 */
public interface JWTClaimsSetVerifier <C extends SecurityContext> {
	
	
	/**
	 * Verifies selected or all claims from the specified JWT claims set.
	 *
	 * @param claimsSet The JWT claims set. Not {@code null}.
	 * @param context   Optional context, {@code null} if not required.
	 *
	 * @throws BadJWTException If the JWT claims set is rejected.
	 */
	void verify(final JWTClaimsSet claimsSet, final C context)
		throws BadJWTException;
}
