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
 * Bad JSON Web Encryption (JWE) exception. Used to indicate a JWE-protected
 * object that couldn't be successfully decrypted or its integrity has been
 * compromised.
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-06-11
 */
public class BadJWEException extends BadJOSEException {


	/**
	 * Creates a new bad JWE exception.
	 *
	 * @param message The exception message.
	 */
	public BadJWEException(final String message) {

		super(message);
	}


	/**
	 * Creates a new bad JWE exception.
	 *
	 * @param message The exception message.
	 * @param cause   The exception cause.
	 */
	public BadJWEException(final String message, final Throwable cause) {

		super(message, cause);
	}
}
