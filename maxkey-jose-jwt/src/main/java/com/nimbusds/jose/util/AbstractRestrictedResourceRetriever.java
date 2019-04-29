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

package com.nimbusds.jose.util;


import net.jcip.annotations.ThreadSafe;


/**
 * Abstract retrieval of resources by URL with HTTP timeout and entity size
 * restrictions.
 */
@ThreadSafe
public abstract class AbstractRestrictedResourceRetriever implements RestrictedResourceRetriever {
	

	/**
	 * The HTTP connect timeout, in milliseconds.
	 */
	private int connectTimeout;


	/**
	 * The HTTP read timeout, in milliseconds.
	 */
	private int readTimeout;


	/**
	 * The HTTP entity size limit, in bytes.
	 */
	private int sizeLimit;


	/**
	 * Creates a new abstract restricted resource retriever.
	 *
	 * @param connectTimeout The HTTP connects timeout, in milliseconds,
	 *                       zero for infinite. Must not be negative.
	 * @param readTimeout    The HTTP read timeout, in milliseconds, zero
	 *                       for infinite. Must not be negative.
	 * @param sizeLimit      The HTTP entity size limit, in bytes, zero for
	 *                       infinite. Must not be negative.
	 */
	public AbstractRestrictedResourceRetriever(int connectTimeout, int readTimeout, int sizeLimit) {
		setConnectTimeout(connectTimeout);
		setReadTimeout(readTimeout);
		setSizeLimit(sizeLimit);
	}


	@Override
	public int getConnectTimeout() {

		return connectTimeout;
	}


	@Override
	public void setConnectTimeout(final int connectTimeoutMs) {

		if (connectTimeoutMs < 0) {
			throw new IllegalArgumentException("The connect timeout must not be negative");
		}

		this.connectTimeout = connectTimeoutMs;
	}


	@Override
	public int getReadTimeout() {

		return readTimeout;
	}


	@Override
	public void setReadTimeout(final int readTimeoutMs) {

		if (readTimeoutMs < 0) {
			throw new IllegalArgumentException("The read timeout must not be negative");
		}

		this.readTimeout = readTimeoutMs;
	}


	@Override
	public int getSizeLimit() {

		return sizeLimit;
	}


	@Override
	public void setSizeLimit(int sizeLimitBytes) {

		if (sizeLimitBytes < 0) {
			throw new IllegalArgumentException("The size limit must not be negative");
		}

		this.sizeLimit = sizeLimitBytes;
	}
}
