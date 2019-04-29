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

package com.nimbusds.jose.proc;


import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.nimbusds.jose.*;


/**
 * JOSE object / header matcher. May be used to ensure a JOSE object / header
 * matches a set of application-specific criteria.
 *
 * <p>Supported matching criteria:
 *
 * <ul>
 *     <li>Any, one or more JOSE classes (plain, JWS, JWE).
 *     <li>Any, one or more algorithms (alg).
 *     <li>Any, one or more encryption methods (enc).
 *     <li>Any, one or more JWK URLs (jku).
 *     <li>Any, one or more JWK IDs (kid).
 * </ul>
 *
 * <p>Matching by X.509 certificate URL, thumbprint and chain is not supported.
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-04-22
 */
public class JOSEMatcher {


	/**
	 * The JOSE classes to match.
	 */
	private final Set<Class<? extends JOSEObject>> classes;


	/**
	 * The JOSE algorithms to match.
	 */
	private final Set<Algorithm> algs;


	/**
	 * The JOSE encryption methods to match (applies to JWE only).
	 */
	private final Set<EncryptionMethod> encs;


	/**
	 * The JWK URLs (jku) to match.
	 */
	private final Set<URI> jkus;


	/**
	 * The JWK IDs (kid) to match.
	 */
	private final Set<String> kids;


	/**
	 * Builder for constructing JOSE matchers.
	 *
	 * <p>Example usage:
	 *
	 * <pre>
	 * JOSEMatcher matcher = new JOSEMatcher().keyID("123").build();
	 * </pre>
	 */
	public static class Builder {


		/**
		 * The JOSE classes to match.
		 */
		private Set<Class<? extends JOSEObject>> classes;


		/**
		 * The JOSE algorithms to match.
		 */
		private Set<Algorithm> algs;


		/**
		 * The JOSE encryption methods to match (applies to JWE only).
		 */
		private Set<EncryptionMethod> encs;


		/**
		 * The JWK URLs (jku) to match.
		 */
		private Set<URI> jkus;


		/**
		 * The JWK IDs (kid) to match.
		 */
		private Set<String> kids;


		/**
		 * Sets a single JOSE class to match.
		 *
		 * @param clazz The JOSE class to match, {@code null} if not
		 *              specified.
		 *
		 * @return This builder.
		 */
		public Builder joseClass(final Class<? extends JOSEObject> clazz) {

			if (clazz == null) {
				this.classes = null;
			} else {
				this.classes = new HashSet<Class<? extends JOSEObject>>(Collections.singletonList(clazz));
			}
			return this;
		}


		/**
		 * Sets multiple JOSE classes to match.
		 *
		 * @param classes The JOSE classes to match.
		 *
		 * @return This builder.
		 */
		public Builder joseClasses(final Class<? extends JOSEObject>... classes) {

			joseClasses(new HashSet<>(Arrays.asList(classes)));
			return this;
		}


		/**
		 * Sets multiple JOSE classes to match.
		 *
		 * @param classes The JOSE classes to match, {@code null} if
		 *                not specified.
		 *
		 * @return This builder.
		 */
		public Builder joseClasses(final Set<Class<? extends JOSEObject>> classes) {

			this.classes = classes;
			return this;
		}


		/**
		 * Sets a single JOSE algorithm to match.
		 *
		 * @param alg The JOSE algorithm, {@code null} if not
		 *            specified.
		 *
		 * @return This builder.
		 */
		public Builder algorithm(final Algorithm alg) {

			if (alg == null) {
				algs = null;
			} else {
				algs = new HashSet<>(Collections.singletonList(alg));
			}
			return this;
		}


		/**
		 * Sets multiple JOSE algorithms to match.
		 *
		 * @param algs The JOSE algorithms.
		 *
		 * @return This builder.
		 */
		public Builder algorithms(final Algorithm ... algs) {

			algorithms(new HashSet<>(Arrays.asList(algs)));
			return this;
		}


		/**
		 * Sets multiple JOSE algorithms to match.
		 *
		 * @param algs The JOSE algorithms, {@code null} if not
		 *             specified.
		 *
		 * @return This builder.
		 */
		public Builder algorithms(final Set<Algorithm> algs) {

			this.algs = algs;
			return this;
		}


		/**
		 * Sets a single JOSE encryption method to match.
		 *
		 * @param enc The JOSE encryption methods, {@code null} if not
		 *            specified.
		 *
		 * @return This builder.
		 */
		public Builder encryptionMethod(final EncryptionMethod enc) {

			if (enc == null) {
				encs = null;
			} else {
				encs = new HashSet<>(Collections.singletonList(enc));
			}
			return this;
		}


		/**
		 * Sets multiple JOSE encryption methods to match.
		 *
		 * @param encs The JOSE encryption methods.
		 *
		 * @return This builder.
		 */
		public Builder encryptionMethods(final EncryptionMethod... encs) {

			encryptionMethods(new HashSet<>(Arrays.asList(encs)));
			return this;
		}


		/**
		 * Sets multiple JOSE encryption methods to match.
		 *
		 * @param encs The JOSE encryption methods, {@code null} if not
		 *             specified.
		 *
		 * @return This builder.
		 */
		public Builder encryptionMethods(final Set<EncryptionMethod> encs) {

			this.encs = encs;
			return this;
		}


		/**
		 * Sets a single JWK URL to match.
		 *
		 * @param jku The JWK URL, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder jwkURL(final URI jku) {

			if (jku == null) {
				jkus = null;
			} else {
				jkus = new HashSet<>(Collections.singletonList(jku));
			}
			return this;
		}


		/**
		 * Sets multiple JWK URLs to match.
		 *
		 * @param jkus The JWK URLs.
		 *
		 * @return This builder.
		 */
		public Builder jwkURLs(final URI... jkus) {

			jwkURLs(new HashSet<>(Arrays.asList(jkus)));
			return this;
		}


		/**
		 * Sets multiple JWK URLs to match.
		 *
		 * @param jkus The JWK URLs, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder jwkURLs(final Set<URI> jkus) {

			this.jkus = jkus;
			return this;
		}


		/**
		 * Sets a single key ID to match.
		 *
		 * @param kid The key ID, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder keyID(final String kid) {

			if (kid == null) {
				kids = null;
			} else {
				kids = new HashSet<>(Collections.singletonList(kid));
			}
			return this;
		}


		/**
		 * Sets multiple key IDs to match.
		 *
		 * @param ids The key IDs.
		 *
		 * @return This builder.
		 */
		public Builder keyIDs(final String ... ids) {

			keyIDs(new HashSet<>(Arrays.asList(ids)));
			return this;
		}


		/**
		 * Sets multiple key IDs to match.
		 *
		 * @param kids The key IDs, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder keyIDs(final Set<String> kids) {

			this.kids = kids;
			return this;
		}


		/**
		 * Builds a new JOSE matcher.
		 *
		 * @return The JOSE matcher.
		 */
		public JOSEMatcher build() {

			return new JOSEMatcher(classes, algs, encs, jkus, kids);
		}
	}


	/**
	 * Creates a new JOSE matcher.
	 *
	 * @param classes The JOSE classes to match, {@code null} if not
	 *                specified.
	 * @param algs    The JOSE algorithms to match, {@code null} if not
	 *                specified.
	 * @param encs    The JOSE encryption methods to match, {@code null} if
	 *                not specified.
	 * @param jkus    The JWK URLs to match, {@code null} if not specified.
	 * @param kids    The key IDs to match, {@code null} if not specified.
	 */
	public JOSEMatcher(final Set<Class<? extends JOSEObject>> classes,
			   final Set<Algorithm> algs,
			   final Set<EncryptionMethod> encs,
			   final Set<URI> jkus,
			   final Set<String> kids) {

		this.classes = classes;
		this.algs = algs;
		this.encs = encs;
		this.jkus = jkus;
		this.kids = kids;
	}


	/**
	 * Returns the JOSE classes to match.
	 *
	 * @return The JOSE classes, {@code null} if not specified.
	 */
	public Set<Class<? extends JOSEObject>> getJOSEClasses() {

		return classes;
	}


	/**
	 * Returns the JOSE algorithms to match.
	 *
	 * @return The JOSE algorithms, {@code null} if not specified.
	 */
	public Set<Algorithm> getAlgorithms() {

		return algs;
	}


	/**
	 * Returns the JOSE encryption methods to match.
	 *
	 * @return The JOSE encryption methods, {@code null} if not specified.
	 */
	public Set<EncryptionMethod> getEncryptionMethods() {

		return encs;
	}


	/**
	 * Returns the JWK URLs to match.
	 *
	 * @return The JWK URLs, {@code null} if not specified.
	 */
	public Set<URI> getJWKURLs() {

		return jkus;
	}


	/**
	 * Returns the key IDs to match.
	 *
	 * @return The key IDs, {@code null} if not specified.
	 */
	public Set<String> getKeyIDs() {

		return kids;
	}


	/**
	 * Returns {@code true} if the specified JOSE object matches.
	 *
	 * @param joseObject The JOSE object. Must not  be {@code null}.
	 *
	 * @return {@code true} if the JOSE object matches, else {@code false}.
	 */
	public boolean matches(final JOSEObject joseObject) {

		if (classes != null) {

			boolean pass = false;
			for (Class<? extends JOSEObject> c: classes) {
				if (c != null && c.isInstance(joseObject)) {
					pass = true;
				}
			}

			if (!pass) {
				return false;
			}
		}

		if (algs != null && ! algs.contains(joseObject.getHeader().getAlgorithm()))
			return false;

		if (encs != null) {

			if (! (joseObject instanceof JWEObject))
				return false;

			JWEObject jweObject = (JWEObject)joseObject;

			if (! encs.contains(jweObject.getHeader().getEncryptionMethod()))
				return false;
		}

		if (jkus != null) {

			final URI jku;

			if (joseObject instanceof JWSObject) {
				jku = ((JWSObject) joseObject).getHeader().getJWKURL();
			} else if (joseObject instanceof JWEObject) {
				jku = ((JWEObject) joseObject).getHeader().getJWKURL();
			} else {
				// Plain object
				jku = null; // jku not supported by unsecured JOSE objects
			}

			if (! jkus.contains(jku))
				return false;
		}

		if (kids != null) {

			final String kid;

			if (joseObject instanceof JWSObject) {
				kid = ((JWSObject) joseObject).getHeader().getKeyID();
			} else if (joseObject instanceof JWEObject) {
				kid = ((JWEObject) joseObject).getHeader().getKeyID();
			} else {
				// Plain object
				kid = null; // kid not supported by unsecured JOSE objects
			}

			if (! kids.contains(kid))
				return false;
		}

		return true;
	}
}
