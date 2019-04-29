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


import java.io.Serializable;

import net.jcip.annotations.Immutable;
import net.minidev.json.JSONAware;
import net.minidev.json.JSONObject;


/**
 * Compression algorithm name, represents the {@code zip} header parameter in 
 * JSON Web Encryption (JWE) objects. This class is immutable.
 *
 * <p>Includes a constant for the standard DEFLATE compression algorithm:
 *
 * <ul>
 *     <li>{@link #DEF}
 * </ul> 
 *
 * <p>Additional compression algorithm names can be defined using the 
 * constructor. 
 *
 * @author Vladimir Dzhuvinov
 * @version 2013-01-15
 */
@Immutable
public final class CompressionAlgorithm implements JSONAware, Serializable {


	private static final long serialVersionUID = 1L;


	/**
	 * DEFLATE Compressed Data Format Specification version 1.3, as 
	 * described in RFC 1951.
	 */
	public static final CompressionAlgorithm DEF = new CompressionAlgorithm("DEF");


	/**
	 * The algorithm name.
	 */
	private final String name;


	/**
	 * Creates a new compression algorithm with the specified name.
	 *
	 * @param name The compression algorithm name. Must not be {@code null}.
	 */
	public CompressionAlgorithm(final String name) {

		if (name == null) {
			throw new IllegalArgumentException("The compression algorithm name must not be null");
		}

		this.name = name;
	}


	/**
	 * Gets the name of this compression algorithm.
	 *
	 * @return The compression algorithm name.
	 */
	public String getName() {

		return name;
	}


	/**
	 * Overrides {@code Object.hashCode()}.
	 *
	 * @return The object hash code.
	 */
	@Override
	public int hashCode() {

		return name.hashCode();
	}


	/**
	 * Overrides {@code Object.equals()}.
	 *
	 * @param object The object to compare to.
	 *
	 * @return {@code true} if the objects have the same value, otherwise
	 *         {@code false}.
	 */
	@Override
	public boolean equals(final Object object) {

		return object != null && 
				object instanceof CompressionAlgorithm && 
				this.toString().equals(object.toString());
	}


	/**
	 * Returns the string representation of this compression algorithm.
	 *
	 * @see #getName
	 *
	 * @return The string representation.
	 */
	@Override
	public String toString() {

		return name;
	}


	/**
	 * Returns the JSON string representation of this compression algorithm.
	 * 
	 * @return The JSON string representation.
	 */
	@Override
	public String toJSONString() {

		return "\"" + JSONObject.escape(name) + '"';
	}
}
