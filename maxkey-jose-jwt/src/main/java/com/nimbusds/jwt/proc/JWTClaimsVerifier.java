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


import com.nimbusds.jwt.JWTClaimsSet;


/**
 * @see JWTClaimsSetVerifier
 */
@Deprecated
public interface JWTClaimsVerifier {


	/**
	 * Performs verification of selected or all claims in the specified JWT
	 * claims set.
	 *
	 * @param claimsSet The JWT claims set. Not {@code null}.
	 *
	 * @throws BadJWTException If the JWT claims set is rejected.
	 */
	@Deprecated
	void verify(final JWTClaimsSet claimsSet)
		throws BadJWTException;
}
