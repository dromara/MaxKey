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

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSProvider;
import com.nimbusds.jose.JWSVerifier;


/**
 * JSON Web Signature (JWS) verifier factory.
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-11-16
 */
public interface JWSVerifierFactory extends JWSProvider {


	/**
	 * Creates a new JWS verifier for the specified header and key.
	 *
	 * @param header The JWS header. Not {@code null}.
	 * @param key    The key intended to verify the JWS message. Not
	 *               {@code null}.
	 *
	 * @return The JWS verifier.
	 *
	 * @throws JOSEException If the JWS algorithm is not supported or the
	 *                       key type or length doesn't match the expected
	 *                       for the JWS algorithm.
	 */
	JWSVerifier createJWSVerifier(final JWSHeader header, final Key key)
		throws JOSEException;
}
