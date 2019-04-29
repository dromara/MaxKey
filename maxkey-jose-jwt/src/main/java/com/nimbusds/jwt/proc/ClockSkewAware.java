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


import com.nimbusds.jose.util.DateUtils;


/**
 * Clock skew aware interface.
 *
 * @see DateUtils
 */
public interface ClockSkewAware {
	

	/**
	 * Gets the maximum acceptable clock skew.
	 *
	 * @return The maximum acceptable clock skew, in seconds. Zero if none.
	 */
	int getMaxClockSkew();


	/**
	 * Sets the maximum acceptable clock skew.
	 *
	 * @param maxClockSkewSeconds The maximum acceptable clock skew, in
	 *                            seconds. Zero if none.
	 */
	void setMaxClockSkew(final int maxClockSkewSeconds);
}
