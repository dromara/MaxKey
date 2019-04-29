/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2016, Connect2id Ltd and contributors.
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

package com.nimbusds.jose.crypto.impl;


import java.util.Collections;
import java.util.Set;

import com.nimbusds.jose.JWSAlgorithm;


/**
 * The base abstract class for Edwards-curve Digital Signature Algorithm 
 * (EdDSA) signers and validators of {@link com.nimbusds.jose.JWSObject JWS 
 * objects}.
 *
 * <p>Supports the following algorithm:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#EdDSA}
 * </ul>
 * 
 * @author Tim McLean
 * @version 2018-07-11
 */
public abstract class EdDSAProvider extends BaseJWSProvider {


	/**
	 * The supported JWS algorithms by the EdDSA provider class.
	 */
	public static final Set<JWSAlgorithm> SUPPORTED_ALGORITHMS;


	static {
		SUPPORTED_ALGORITHMS = Collections.singleton(JWSAlgorithm.EdDSA);
	}


	/**
	 * Creates a new Edwards-curve Digital Signature Algorithm (EdDSA) 
	 * provider.
	 */
	protected EdDSAProvider() {

		super(SUPPORTED_ALGORITHMS);
	}
}

