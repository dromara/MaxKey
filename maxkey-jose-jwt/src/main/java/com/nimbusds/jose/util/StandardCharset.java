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


import java.nio.charset.Charset;


/**
 * UTF-8 is the standard charset in JOSE. Works around missing
 * {@link java.nio.charset.StandardCharsets} in Android below API level 19.
 */
public final class StandardCharset {
	
	
	/**
	 * UTF-8
	 */
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	
	
	/**
	 * Prevents public instantiation.
	 */
	private StandardCharset() {}
}
