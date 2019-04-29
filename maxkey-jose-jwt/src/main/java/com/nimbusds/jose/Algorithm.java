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
 * The base class for algorithm names, with optional implementation 
 * requirement. This class is immutable.
 *
 * <p>Includes constants for the following standard algorithm names:
 *
 * <ul>
 *     <li>{@link #NONE none}
 * </ul>
 *
 * @author Vladimir Dzhuvinov 
 * @version 2013-03-27
 */
@Immutable
public class Algorithm implements JSONAware, Serializable {


	private static final long serialVersionUID = 1L;


	/**
	 * No algorithm (unsecured JOSE object without signature / encryption).
	 */
	public static final Algorithm NONE = new Algorithm("none", Requirement.REQUIRED);


	/**
	 * The algorithm name.
	 */
	private final String name;


	/**
	 * The implementation requirement, {@code null} if not known.
	 */
	private final Requirement requirement;


	/**
	 * Creates a new JOSE algorithm name.
	 *
	 * @param name The algorithm name. Must not be {@code null}.
	 * @param req  The implementation requirement, {@code null} if not 
	 *             known.
	 */
	public Algorithm(final String name, final Requirement req) {

		if (name == null) {

			throw new IllegalArgumentException("The algorithm name must not be null");
		}

		this.name = name;

		requirement = req;
	}


	/**
	 * Creates a new JOSE algorithm name.
	 *
	 * @param name The algorithm name. Must not be {@code null}.
	 */
	public Algorithm(final String name) {

		this(name, null);
	}


	/**
	 * Gets the name of this algorithm.
	 *
	 * @return The algorithm name.
	 */
	public final String getName() {

		return name;
	}


	/**
	 * Gets the implementation requirement of this algorithm.
	 *
	 * @return The implementation requirement, {@code null} if not known.
	 */
	public final Requirement getRequirement() {

		return requirement;
	}


	/**
	 * Overrides {@code Object.hashCode()}.
	 *
	 * @return The object hash code.
	 */
	@Override
	public final int hashCode() {

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
		       object instanceof Algorithm && 
		       this.toString().equals(object.toString());
	}


	/**
	 * Returns the string representation of this algorithm.
	 *
	 * @see #getName
	 *
	 * @return The string representation.
	 */
	@Override
	public final String toString() {

		return name;
	}


	/**
	 * Returns the JSON string representation of this algorithm.
	 * 
	 * @return The JSON string representation.
	 */
	@Override
	public final String toJSONString() {

		return "\"" + JSONObject.escape(name) + '"';
	}
}
