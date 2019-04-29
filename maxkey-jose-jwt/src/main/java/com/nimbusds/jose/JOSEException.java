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


/**
 * Javascript Object Signing and Encryption (JOSE) exception.
 *
 * @author Vladimir Dzhuvinov
 * @version 2012-09-15
 */
public class JOSEException extends Exception {


	private static final long serialVersionUID = 1L;


	/**
	 * Creates a new JOSE exception with the specified message.
	 *
	 * @param message The exception message.
	 */
	public JOSEException(final String message) {

		super(message);
	}


	/**
	 * Creates a new JOSE exception with the specified message and cause.
	 *
	 * @param message The exception message.
	 * @param cause   The exception cause.
	 */
	public JOSEException(final String message, final Throwable cause) {

		super(message, cause);
	}
}
