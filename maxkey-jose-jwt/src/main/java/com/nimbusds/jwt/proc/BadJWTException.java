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


import com.nimbusds.jose.proc.BadJOSEException;


/**
 * Bad JSON Web Token (JWT) exception.
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-06-29
 */
public class BadJWTException extends BadJOSEException {


	/**
	 * Creates a new bad JWT exception.
	 *
	 * @param message The exception message.
	 */
	public BadJWTException(final String message) {

		super(message);
	}


	/**
	 * Creates a new bad JWT exception.
	 *
	 * @param message The exception message.
	 * @param cause   The exception cause.
	 */
	public BadJWTException(final String message, final Throwable cause) {

		super(message, cause);
	}
}
