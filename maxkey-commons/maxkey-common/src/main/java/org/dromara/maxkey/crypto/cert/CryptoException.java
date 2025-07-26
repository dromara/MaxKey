/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

/*
 * CryptoException.java
 */

package org.dromara.maxkey.crypto.cert;

/**
 * Represents a cryptographic exception.
 */
public class CryptoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1879905141025077248L;

	/**
	 * Creates a new CryptoException.
	 */
	public CryptoException() {
		super();
	}

	/**
	 * Creates a new CryptoException with the specified message.
	 * 
	 * @param sMessage
	 *            Exception message
	 */
	public CryptoException(String sMessage) {
		super(sMessage);
	}

	/**
	 * Creates a new CryptoException with the specified message and cause
	 * throwable.
	 * 
	 * @param causeThrowable
	 *            The throwable that caused this exception to be thrown
	 * @param sMessage
	 *            Exception message
	 */
	public CryptoException(String sMessage, Throwable causeThrowable) {
		super(sMessage, causeThrowable);
	}

	/**
	 * Creates a new CryptoException with the specified cause throwable.
	 * 
	 * @param causeThrowable
	 *            The throwable that caused this exception to be thrown
	 */
	public CryptoException(Throwable causeThrowable) {
		super(causeThrowable);
	}
}
