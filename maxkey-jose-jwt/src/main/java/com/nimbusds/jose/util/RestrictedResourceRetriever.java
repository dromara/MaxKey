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


/**
 * Retriever of resources specified by URL which permits setting of HTTP
 * connect and read timeouts as well as a size limit.
 */
public interface RestrictedResourceRetriever extends ResourceRetriever {
	

	/**
	 * Gets the HTTP connect timeout.
	 *
	 * @return The HTTP connect timeout, in milliseconds, zero for
	 *         infinite.
	 */
	int getConnectTimeout();


	/**
	 * Sets the HTTP connect timeout.
	 *
	 * @param connectTimeoutMs The HTTP connect timeout, in milliseconds,
	 *                         zero for infinite. Must not be negative.
	 */
	void setConnectTimeout(final int connectTimeoutMs);


	/**
	 * Gets the HTTP read timeout.
	 *
	 * @return The HTTP read timeout, in milliseconds, zero for infinite.
	 */
	int getReadTimeout();


	/**
	 * Sets the HTTP read timeout.
	 *
	 * @param readTimeoutMs The HTTP read timeout, in milliseconds, zero
	 *                      for infinite. Must not be negative.
	 */
	void setReadTimeout(final int readTimeoutMs);


	/**
	 * Gets the HTTP entity size limit.
	 *
	 * @return The HTTP entity size limit, in bytes, zero for infinite.
	 */
	int getSizeLimit();


	/**
	 * Sets the HTTP entity size limit.
	 *
	 * @param sizeLimitBytes The HTTP entity size limit, in bytes, zero for
	 *                       infinite. Must not be negative.
	 */
	void setSizeLimit(int sizeLimitBytes);
}
