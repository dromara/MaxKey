/*******************************************************************************
 * Copyright 2014 The MITRE Corporation
 *   and the MIT Kerberos and Internet Trust Consortium
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/**
 * 
 */
package org.dromara.maxkey.crypto.jose;

import com.google.common.base.Strings;
import com.nimbusds.jose.JWEAlgorithm;

/**
 * 
 * Wrapper class for Nimbus JOSE objects to fit into JPA
 * 
 * @author jricher
 *
 */

public class JWEAlgorithmEmbed {

	public static final JWEAlgorithmEmbed NONE = getForAlgorithmName("none");

	private JWEAlgorithm algorithm;

	public JWEAlgorithmEmbed() {

	}

	public JWEAlgorithmEmbed(JWEAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public static JWEAlgorithmEmbed getForAlgorithmName (String algorithmName) {
		JWEAlgorithmEmbed ent = new JWEAlgorithmEmbed();
		ent.setAlgorithmName(algorithmName);
		if (ent.getAlgorithm() == null) {
			return null;
		} else {
			return ent;
		}
	}

	/**
	 * Get the name of this algorithm, return null if no algorithm set.
	 * @return
	 */

	public String getAlgorithmName() {
		if (algorithm != null) {
			return algorithm.getName();
		} else {
			return null;
		}
	}

	/**
	 * Set the name of this algorithm.
	 * Calls JWEAlgorithm.parse()
	 * @param algorithmName
	 */
	public void setAlgorithmName(String algorithmName) {
		if (!Strings.isNullOrEmpty(algorithmName)) {
			algorithm = JWEAlgorithm.parse(algorithmName);
		} else {
			algorithm = null;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JWEAlgorithmEmbed [algorithm=" + algorithm + "]";
	}

	/**
	 * @return the algorithm
	 */
	public JWEAlgorithm getAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm the algorithm to set
	 */
	public void setAlgorithm(JWEAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

}
