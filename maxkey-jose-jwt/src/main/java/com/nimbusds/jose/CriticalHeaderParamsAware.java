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

package com.nimbusds.jose;


import java.util.Set;


/**
 * JSON Web Signature (JWS) verifier or JSON Web Encryption (JWE) decrypter
 * that supports processing and / or deferral of critical ({@code crit}) header
 * parameters.
 *
 * <p>JWS verification / JWE decryption will fail with a {@link JOSEException}
 * if a critical header is encountered that is neither processed by the
 * verifier / decrypter nor deferred to the application.
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-04-21
 */
public interface CriticalHeaderParamsAware {


	/**
	 * Returns the names of the critical ({@code crit}) header parameters
	 * that are understood and processed by the JWS verifier / JWE
	 * decrypter.
	 *
	 * @return The names of the critical header parameters that are
	 *         understood and processed, empty set if none.
	 */
	Set<String> getProcessedCriticalHeaderParams();


	/**
	 * Returns the names of the critical ({@code crit}) header parameters
	 * that are deferred to the application for processing and will be
	 * ignored by the JWS verifier / JWE decrypter.
	 *
	 * @return The names of the critical header parameters that are
	 *         deferred to the application for processing, empty set if
	 *         none.
	 */
	Set<String> getDeferredCriticalHeaderParams();
}
