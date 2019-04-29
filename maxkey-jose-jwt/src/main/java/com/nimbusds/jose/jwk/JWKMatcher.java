/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2019, Connect2id Ltd.
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

package com.nimbusds.jose.jwk;


import java.util.*;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.util.Base64URL;
import net.jcip.annotations.Immutable;


/**
 * JSON Web Key (JWK) matcher. May be used to ensure a JWK matches a set of
 * application-specific criteria.
 *
 * <p>Supported key matching criteria:
 *
 * <ul>
 *     <li>Any, unspecified, one or more key types (typ).
 *     <li>Any, unspecified, one or more key uses (use).
 *     <li>Any, unspecified, one or more key operations (key_ops).
 *     <li>Any, unspecified, one or more key algorithms (alg).
 *     <li>Any, unspecified, one or more key identifiers (kid).
 *     <li>Private only key.
 *     <li>Public only key.
 *     <li>Minimum, maximum or exact key sizes.
 *     <li>Any, unspecified, one or more curves for EC and OKP keys (crv).
 *     <li>X.509 certificate SHA-256 thumbprint.
 * </ul>
 *
 * <p>Matching by JWK thumbprint (RFC 7638), X.509 certificate URL and X.509
 * certificate chain is not supported.
 *
 * @author Vladimir Dzhuvinov
 * @author Josh Cummings
 * @version 2018-06-13
 */
@Immutable
public class JWKMatcher {


	/**
	 * The key types to match.
	 */
	private final Set<KeyType> types;


	/**
	 * The public key uses to match.
	 */
	private final Set<KeyUse> uses;


	/**
	 * The key operations to match.
	 */
	private final Set<KeyOperation> ops;


	/**
	 * The algorithms to match.
	 */
	private final Set<Algorithm> algs;


	/**
	 * The key IDs to match.
	 */
	private final Set<String> ids;
	
	
	/**
	 * {@code true} to match a key with a set use.
	 */
	private final boolean hasUse;
	
	
	/**
	 * {@code true} to match a key with a set ID.
	 */
	private final boolean hasID;


	/**
	 * {@code true} to match a private key.
	 */
	private final boolean privateOnly;


	/**
	 * {@code true} to match a public only key.
	 */
	private final boolean publicOnly;


	/**
	 * The minimum key size in bits, zero implies no minimum size limit.
	 */
	private final int minSizeBits;


	/**
	 * The maximum key size in bits, zero implies no maximum size limit.
	 */
	private final int maxSizeBits;
	
	
	/**
	 * The key sizes in bits.
	 */
	private final Set<Integer> sizesBits;
	
	
	/**
	 * The curves to match (for EC and OKP keys).
	 */
	private final Set<Curve> curves;

	
	/**
	 * The X.509 certificate SHA-256 thumbprints to match.
	 */
	private final Set<Base64URL> x5tS256s;

	
	/**
	 * Builder for constructing JWK matchers.
	 *
	 * <p>Example usage:
	 *
	 * <pre>
	 * JWKMatcher matcher = new JWKMatcher().keyID("123").build();
	 * </pre>
	 */
	public static class Builder {

		
		/**
		 * The key types to match.
		 */
		private Set<KeyType> types;


		/**
		 * The public key uses to match.
		 */
		private Set<KeyUse> uses;


		/**
		 * The key operations to match.
		 */
		private Set<KeyOperation> ops;


		/**
		 * The algorithms to match.
		 */
		private Set<Algorithm> algs;


		/**
		 * The key IDs to match.
		 */
		private Set<String> ids;
		
		
		/**
		 * {@code true} to match a key with a set use.
		 */
		private boolean hasUse = false;
		
		
		/**
		 * {@code true} to match a key with a set ID.
		 */
		private boolean hasID = false;


		/**
		 * {@code true} to match a private key.
		 */
		private boolean privateOnly = false;


		/**
		 * {@code true} to match a public only key.
		 */
		private boolean publicOnly = false;


		/**
		 * The minimum key size in bits, zero implies no minimum size
		 * limit.
		 */
		private int minSizeBits = 0;


		/**
		 * The maximum key size in bits, zero implies no maximum size
		 * limit.
		 */
		private int maxSizeBits = 0;
		
		
		/**
		 * The key sizes in bits.
		 */
		private Set<Integer> sizesBits;
		
		
		/**
		 * The curves to match (for EC and OKP keys).
		 */
		private Set<Curve> curves;

		
		/**
		 * The X.509 certificate SHA-256 thumbprints to match.
		 */
		private Set<Base64URL> x5tS256s;

		
		/**
		 * Sets a single key type to match.
		 *
		 * @param kty The key type, {@code null} if not specified.
		 *            
		 * @return This builder.            
		 */
		public Builder keyType(final KeyType kty) {

			if (kty == null) {
				types = null;
			} else {
				types = new HashSet<>(Collections.singletonList(kty));
			}
			
			return this;
		}


		/**
		 * Sets multiple key types to match.
		 *
		 * @param types The key types.
		 *
		 * @return This builder.
		 */
		public Builder keyTypes(final KeyType ... types) {

			keyTypes(new LinkedHashSet<>(Arrays.asList(types)));
			return this;
		}


		/**
		 * Sets multiple key types to match.
		 *
		 * @param types The key types, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder keyTypes(final Set<KeyType> types) {

			this.types = types;
			return this;
		}


		/**
		 * Sets a single public key use to match.
		 *
		 * @param use The public key use, {@code null} if not 
		 *            specified.
		 *
		 * @return This builder.
		 */
		public Builder keyUse(final KeyUse use) {

			if (use == null) {
				uses = null;
			} else {
				uses = new HashSet<>(Collections.singletonList(use));
			}
			return this;
		}


		/**
		 * Sets multiple public key uses to match.
		 *
		 * @param uses The public key uses.
		 *
		 * @return This builder.
		 */
		public Builder keyUses(final KeyUse... uses) {

			keyUses(new LinkedHashSet<>(Arrays.asList(uses)));
			return this;
		}


		/**
		 * Sets multiple public key uses to match.
		 *
		 * @param uses The public key uses, {@code null} if not
		 *             specified.
		 *
		 * @return This builder.
		 */
		public Builder keyUses(final Set<KeyUse> uses) {

			this.uses = uses;
			return this;
		}


		/**
		 * Sets a single key operation to match.
		 *
		 * @param op The key operation, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder keyOperation(final KeyOperation op) {

			if (op == null) {
				ops = null;
			} else {
				ops = new HashSet<>(Collections.singletonList(op));
			}
			return this;
		}


		/**
		 * Sets multiple key operations to match.
		 *
		 * @param ops The key operations.
		 *
		 * @return This builder.
		 */
		public Builder keyOperations(final KeyOperation... ops) {

			keyOperations(new LinkedHashSet<>(Arrays.asList(ops)));
			return this;
		}


		/**
		 * Sets multiple key operations to match.
		 *
		 * @param ops The key operations, {@code null} if not
		 *            specified.
		 *
		 * @return This builder.
		 */
		public Builder keyOperations(final Set<KeyOperation> ops) {

			this.ops = ops;
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

			algorithms(new LinkedHashSet<>(Arrays.asList(algs)));
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
		 * Sets a single key ID to match.
		 *
		 * @param id The key ID, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder keyID(final String id) {

			if (id == null) {
				ids = null;
			} else {
				ids = new HashSet<>(Collections.singletonList(id));
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

			keyIDs(new LinkedHashSet<>(Arrays.asList(ids)));
			return this;
		}


		/**
		 * Sets multiple key IDs to match.
		 *
		 * @param ids The key IDs, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder keyIDs(final Set<String> ids) {

			this.ids = ids;
			return this;
		}
		
		
		/**
		 * Sets key use presence matching.
		 *
		 * @param hasUse {@code true} to match a key with a set use.
		 *
		 * @return This builder.
		 */
		public Builder hasKeyUse(final boolean hasUse) {
			
			this.hasUse = hasUse;
			return this;
		}
		
		
		/**
		 * Sets key ID presence matching.
		 *
		 * @param hasID {@code true} to match a key with a set ID.
		 *
		 * @return This builder.
		 */
		public Builder hasKeyID(final boolean hasID) {
			
			this.hasID = hasID;
			return this;
		}


		/**
		 * Sets the private key matching policy.
		 *
		 * @param privateOnly {@code true} to match a private key.
		 *
		 * @return This builder.
		 */
		public Builder privateOnly(final boolean privateOnly) {

			this.privateOnly = privateOnly;
			return this;
		}


		/**
		 * Sets the public key matching policy.
		 *
		 * @param publicOnly {@code true} to match a public only key.
		 *
		 * @return This builder.
		 */
		public Builder publicOnly(final boolean publicOnly) {

			this.publicOnly = publicOnly;
			return this;
		}


		/**
		 * Sets the minimal key size.
		 *
		 * @param minSizeBits The minimum key size in bits, zero
		 *                    implies no minimum key size limit.
		 *
		 * @return This builder.
		 */
		public Builder minKeySize(final int minSizeBits) {

			this.minSizeBits = minSizeBits;
			return this;
		}


		/**
		 * Sets the maximum key size.
		 *
		 * @param maxSizeBits The maximum key size in bits, zero
		 *                    implies no maximum key size limit.
		 *
		 * @return This builder.
		 */
		public Builder maxKeySize(final int maxSizeBits) {

			this.maxSizeBits = maxSizeBits;
			return this;
		}
		
		
		/**
		 * Sets the key size.
		 *
		 * @param keySizeBits The key size in bits, zero if not
		 *                    specified.
		 *
		 * @return This builder.
		 */
		public Builder keySize(final int keySizeBits) {
			if (keySizeBits <= 0) {
				sizesBits = null;
			} else {
				sizesBits = Collections.singleton(keySizeBits);
			}
			return this;
		}
		
		
		/**
		 * Sets the key sizes.
		 *
		 * @param keySizesBits The key sizes in bits.
		 *
		 * @return This builder.
		 */
		public Builder keySizes(final int... keySizesBits) {
			Set<Integer> sizesSet = new LinkedHashSet<>();
			for (int keySize: keySizesBits) {
				sizesSet.add(keySize);
			}
			keySizes(sizesSet);
			return this;
		}
		
		
		/**
		 * Sets the key sizes.
		 *
		 * @param keySizesBits The key sizes in bits.
		 *
		 * @return This builder.
		 */
		public Builder keySizes(final Set<Integer> keySizesBits) {
			
			this.sizesBits = keySizesBits;
			return this;
		}
		
		
		/**
		 * Sets a single curve to match (for EC and OKP keys).
		 *
		 * @param curve The curve, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder curve(final Curve curve) {
			
			if (curve == null) {
				curves = null;
			} else {
				curves = new HashSet<>(Collections.singletonList(curve));
			}
			return this;
		}
		
		
		/**
		 * Sets multiple curves to match (for EC and OKP keys).
		 *
		 * @param curves The curves.
		 *
		 * @return This builder.
		 */
		public Builder curves(final Curve... curves) {
			
			curves(new LinkedHashSet<>(Arrays.asList(curves)));
			return this;
		}
		
		
		/**
		 * Sets multiple curves to match (for EC and OKP keys).
		 *
		 * @param curves The curves, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public Builder curves(final Set<Curve> curves) {
			
			this.curves = curves;
			return this;
		}

		
		/**
		 * Sets a single X.509 certificate SHA-256 thumbprint to match.
		 *
		 * @param x5tS256 The thumbprint, {@code null} if not
		 *                specified.
		 *
		 * @return This builder.
		 */
		public Builder x509CertSHA256Thumbprint(final Base64URL x5tS256) {

			if (x5tS256 == null) {
				x5tS256s = null;
			} else {
				x5tS256s = Collections.singleton(x5tS256);
			}
			return this;
		}

		/**
		 * Sets multiple X.509 certificate SHA-256 thumbprints to
		 * match.
		 *
		 * @param x5tS256s The thumbprints.
		 *
		 * @return This builder.
		 */
		public Builder x509CertSHA256Thumbprints(final Base64URL... x5tS256s) {
			return x509CertSHA256Thumbprints(new LinkedHashSet<>(Arrays.asList(x5tS256s)));
		}

		
		/**
		 * Sets multiple X.509 certificate SHA-256 thumbprints to
		 * match.
		 *
		 * @param x5tS256s The thumbprints, {@code null} if not
		 *                 specified.
		 *
		 * @return This builder.
		 */
		public Builder x509CertSHA256Thumbprints(final Set<Base64URL> x5tS256s) {
			this.x5tS256s = x5tS256s;
			return this;
		}

		/**
		 * Builds a new JWK matcher.
		 *
		 * @return The JWK matcher.
		 */
		public JWKMatcher build() {

			return new JWKMatcher(types, uses, ops, algs, ids, hasUse, hasID, privateOnly, publicOnly, minSizeBits, maxSizeBits, sizesBits, curves, x5tS256s);
		}
	}


	/**
	 * Creates a new JSON Web Key (JWK) matcher.
	 *
	 * @param types       The key types to match, {@code null} if not
	 *                    specified.
	 * @param uses        The public key uses to match, {@code null} if not
	 *                    specified.
	 * @param ops         The key operations to match, {@code null} if not
	 *                    specified.
	 * @param algs        The JOSE algorithms to match, {@code null} if not
	 *                    specified.
	 * @param ids         The key IDs to match, {@code null} if not
	 *                    specified.
	 * @param privateOnly {@code true} to match a private key.
	 * @param publicOnly  {@code true} to match a public only key.
	 */
	@Deprecated
	public JWKMatcher(final Set<KeyType> types,
			  final Set<KeyUse> uses,
			  final Set<KeyOperation> ops,
			  final Set<Algorithm> algs,
			  final Set<String> ids,
			  final boolean privateOnly,
			  final boolean publicOnly) {

		this(types, uses, ops, algs, ids, privateOnly, publicOnly, 0, 0);
	}


	/**
	 * Creates a new JSON Web Key (JWK) matcher.
	 *
	 * @param types       The key types to match, {@code null} if not
	 *                    specified.
	 * @param uses        The public key uses to match, {@code null} if not
	 *                    specified.
	 * @param ops         The key operations to match, {@code null} if not
	 *                    specified.
	 * @param algs        The JOSE algorithms to match, {@code null} if not
	 *                    specified.
	 * @param ids         The key IDs to match, {@code null} if not
	 *                    specified.
	 * @param privateOnly {@code true} to match a private key.
	 * @param publicOnly  {@code true} to match a public only key.
	 * @param minSizeBits The minimum key size in bits, zero implies no
	 *                    minimum size limit.
	 * @param maxSizeBits The maximum key size in bits, zero implies no
	 *                    maximum size limit.
	 */
	@Deprecated
	public JWKMatcher(final Set<KeyType> types,
			  final Set<KeyUse> uses,
			  final Set<KeyOperation> ops,
			  final Set<Algorithm> algs,
			  final Set<String> ids,
			  final boolean privateOnly,
			  final boolean publicOnly,
			  final int minSizeBits,
			  final int maxSizeBits) {
		
		this(types, uses, ops, algs, ids, privateOnly, publicOnly, minSizeBits, maxSizeBits, null);
	}


	/**
	 * Creates a new JSON Web Key (JWK) matcher.
	 *
	 * @param types       The key types to match, {@code null} if not
	 *                    specified.
	 * @param uses        The public key uses to match, {@code null} if not
	 *                    specified.
	 * @param ops         The key operations to match, {@code null} if not
	 *                    specified.
	 * @param algs        The JOSE algorithms to match, {@code null} if not
	 *                    specified.
	 * @param ids         The key IDs to match, {@code null} if not
	 *                    specified.
	 * @param privateOnly {@code true} to match a private key.
	 * @param publicOnly  {@code true} to match a public only key.
	 * @param minSizeBits The minimum key size in bits, zero implies no
	 *                    minimum size limit.
	 * @param maxSizeBits The maximum key size in bits, zero implies no
	 *                    maximum size limit.
	 * @param curves      The curves to match (for EC keys), {@code null}
	 *                    if not specified.
	 */
	@Deprecated
	public JWKMatcher(final Set<KeyType> types,
			  final Set<KeyUse> uses,
			  final Set<KeyOperation> ops,
			  final Set<Algorithm> algs,
			  final Set<String> ids,
			  final boolean privateOnly,
			  final boolean publicOnly,
			  final int minSizeBits,
			  final int maxSizeBits,
			  final Set<Curve> curves) {
		
		this(types, uses, ops, algs, ids, privateOnly, publicOnly, minSizeBits, maxSizeBits, null, curves);
	}


	/**
	 * Creates a new JSON Web Key (JWK) matcher.
	 *
	 * @param types       The key types to match, {@code null} if not
	 *                    specified.
	 * @param uses        The public key uses to match, {@code null} if not
	 *                    specified.
	 * @param ops         The key operations to match, {@code null} if not
	 *                    specified.
	 * @param algs        The JOSE algorithms to match, {@code null} if not
	 *                    specified.
	 * @param ids         The key IDs to match, {@code null} if not
	 *                    specified.
	 * @param privateOnly {@code true} to match a private key.
	 * @param publicOnly  {@code true} to match a public only key.
	 * @param minSizeBits The minimum key size in bits, zero implies no
	 *                    minimum size limit.
	 * @param maxSizeBits The maximum key size in bits, zero implies no
	 *                    maximum size limit.
	 * @param sizesBits   The key sizes in bits, {@code null} if not
	 *                    specified.
	 * @param curves      The curves to match (for EC and OKP keys),
	 *                    {@code null} if not specified.
	 */
	@Deprecated
	public JWKMatcher(final Set<KeyType> types,
			  final Set<KeyUse> uses,
			  final Set<KeyOperation> ops,
			  final Set<Algorithm> algs,
			  final Set<String> ids,
			  final boolean privateOnly,
			  final boolean publicOnly,
			  final int minSizeBits,
			  final int maxSizeBits,
			  final Set<Integer> sizesBits,
			  final Set<Curve> curves) {
		
		this(types, uses, ops, algs, ids, false, false, privateOnly, publicOnly, minSizeBits, maxSizeBits, sizesBits, curves);
	}


	/**
	 * Creates a new JSON Web Key (JWK) matcher.
	 *
	 * @param types       The key types to match, {@code null} if not
	 *                    specified.
	 * @param uses        The public key uses to match, {@code null} if not
	 *                    specified.
	 * @param ops         The key operations to match, {@code null} if not
	 *                    specified.
	 * @param algs        The JOSE algorithms to match, {@code null} if not
	 *                    specified.
	 * @param ids         The key IDs to match, {@code null} if not
	 *                    specified.
	 * @param hasUse      {@code true} to match a key with a set use.
	 * @param hasID       {@code true} to match a key with a set ID.
	 * @param privateOnly {@code true} to match a private key.
	 * @param publicOnly  {@code true} to match a public only key.
	 * @param minSizeBits The minimum key size in bits, zero implies no
	 *                    minimum size limit.
	 * @param maxSizeBits The maximum key size in bits, zero implies no
	 *                    maximum size limit.
	 * @param sizesBits   The key sizes in bits, {@code null} if not
	 *                    specified.
	 * @param curves      The curves to match (for EC and OKP keys),
	 *                    {@code null} if not specified.
	 */
	@Deprecated
	public JWKMatcher(final Set<KeyType> types,
			  final Set<KeyUse> uses,
			  final Set<KeyOperation> ops,
			  final Set<Algorithm> algs,
			  final Set<String> ids,
			  final boolean hasUse,
			  final boolean hasID,
			  final boolean privateOnly,
			  final boolean publicOnly,
			  final int minSizeBits,
			  final int maxSizeBits,
			  final Set<Integer> sizesBits,
			  final Set<Curve> curves) {

		this(types, uses, ops, algs, ids, hasUse, hasID, privateOnly, publicOnly, minSizeBits, maxSizeBits, sizesBits, curves, null);
	}

	/**
	 * Creates a new JSON Web Key (JWK) matcher.
	 *
	 * @param types       The key types to match, {@code null} if not
	 *                    specified.
	 * @param uses        The public key uses to match, {@code null} if not
	 *                    specified.
	 * @param ops         The key operations to match, {@code null} if not
	 *                    specified.
	 * @param algs        The JOSE algorithms to match, {@code null} if not
	 *                    specified.
	 * @param ids         The key IDs to match, {@code null} if not
	 *                    specified.
	 * @param hasUse      {@code true} to match a key with a set use.
	 * @param hasID       {@code true} to match a key with a set ID.
	 * @param privateOnly {@code true} to match a private key.
	 * @param publicOnly  {@code true} to match a public only key.
	 * @param minSizeBits The minimum key size in bits, zero implies no
	 *                    minimum size limit.
	 * @param maxSizeBits The maximum key size in bits, zero implies no
	 *                    maximum size limit.
	 * @param sizesBits   The key sizes in bits, {@code null} if not
	 *                    specified.
	 * @param curves      The curves to match (for EC and OKP keys),
	 *                    {@code null} if not specified.
	 * @param x5tS256s    The X.509 certificate thumbprints to match,
	 *                    {@code null} if not specified.
	 */
	public JWKMatcher(final Set<KeyType> types,
					  final Set<KeyUse> uses,
					  final Set<KeyOperation> ops,
					  final Set<Algorithm> algs,
					  final Set<String> ids,
					  final boolean hasUse,
					  final boolean hasID,
					  final boolean privateOnly,
					  final boolean publicOnly,
					  final int minSizeBits,
					  final int maxSizeBits,
					  final Set<Integer> sizesBits,
					  final Set<Curve> curves,
					  final Set<Base64URL> x5tS256s) {

		this.types = types;
		this.uses = uses;
		this.ops = ops;
		this.algs = algs;
		this.ids = ids;
		this.hasUse = hasUse;
		this.hasID = hasID;
		this.privateOnly = privateOnly;
		this.publicOnly = publicOnly;
		this.minSizeBits = minSizeBits;
		this.maxSizeBits = maxSizeBits;
		this.sizesBits = sizesBits;
		this.curves = curves;
		this.x5tS256s = x5tS256s;
	}

	/**
	 * Returns a {@link JWKMatcher} based on the given {@link JWEHeader}.
	 *
	 * <p>The {@link JWKMatcher} is configured as follows:
	 *
	 * <ul>
	 *     <li>The key type to match is determined by the JWE algorithm
	 *         (alg).
	 *     <li>The key ID to match is set by the JWE header key ID (kid)
	 *         parameter (if set).
	 *     <li>The key uses to match are set to encryption or not
	 *         specified.
	 *     <li>The key algorithm to match is set to the JWE algorithm (alg)
	 *         or not specified.
	 * </ul>
	 *
	 * <p>Other JWE header parameters are not taken into account.
	 *
	 * @param jweHeader The header to use.
	 *
	 * @return A {@code JWKMatcher} based on the given header.
	 */
	public static JWKMatcher forJWEHeader(final JWEHeader jweHeader) {

		return new JWKMatcher.Builder()
			.keyType(KeyType.forAlgorithm(jweHeader.getAlgorithm()))
			.keyID(jweHeader.getKeyID())
			.keyUses(KeyUse.ENCRYPTION, null)
			.algorithms(jweHeader.getAlgorithm(), null)
			.build();
	}

	/**
	 * Returns a {@link JWKMatcher} based on the given {@link JWSHeader}.
	 *
	 * <p>The {@link JWKMatcher} is configured as follows:
	 *
	 * <ul>
	 *     <li>The key type to match is determined by the JWS algorithm
	 *         (alg).
	 *     <li>The key ID to match is set by the JWS header key ID (kid)
	 *         parameter (if set).
	 *     <li>The key uses to match are set to signature or not specified.
	 *     <li>The key algorithm to match is set to the JWS algorithm (alg)
	 *         or not specified.
	 *     <li>The X.509 certificate SHA-256 thumbprint to match is set to
	 *         the x5t#S256 parameter (if set).
	 * </ul>
	 *
	 * <p>Other JWS header parameters are not taken into account.
	 *
	 * @param jwsHeader The header to use.
	 *
	 * @return A {@code JWKMatcher} based on the given header, {@code null}
	 *         if the JWS algorithm is not supported.
	 */
	public static JWKMatcher forJWSHeader(final JWSHeader jwsHeader) {

		JWSAlgorithm algorithm = jwsHeader.getAlgorithm();
		if (JWSAlgorithm.Family.RSA.contains(algorithm) || JWSAlgorithm.Family.EC.contains(algorithm)) {
			// RSA or EC key matcher
			return new JWKMatcher.Builder()
				.keyType(KeyType.forAlgorithm(algorithm))
				.keyID(jwsHeader.getKeyID())
				.keyUses(KeyUse.SIGNATURE, null)
				.algorithms(algorithm, null)
				.x509CertSHA256Thumbprint(jwsHeader.getX509CertSHA256Thumbprint())
				.build();
		} else if (JWSAlgorithm.Family.HMAC_SHA.contains(algorithm)) {
			// HMAC secret matcher
			return new JWKMatcher.Builder()
				.keyType(KeyType.forAlgorithm(algorithm))
				.keyID(jwsHeader.getKeyID())
				.privateOnly(true)
				.algorithms(algorithm, null)
				.build();
		} else {
			return null; // Unsupported algorithm
		}
	}

	/**
	 * Returns the key types to match.
	 *
	 * @return The key types, {@code null} if not specified.
	 */
	public Set<KeyType> getKeyTypes() {

		return types;
	}


	/**
	 * Returns the public key uses to match.
	 *
	 * @return The public key uses, {@code null} if not specified.
	 */
	public Set<KeyUse> getKeyUses() {

		return uses;
	}


	/**
	 * Returns the key operations to match.
	 *
	 * @return The key operations, {@code null} if not specified.
	 */
	public Set<KeyOperation> getKeyOperations() {

		return ops;
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
	 * Returns the key IDs to match.
	 *
	 * @return The key IDs, {@code null} if not specified.
	 */
	public Set<String> getKeyIDs() {

		return ids;
	}
	
	
	/**
	 * Returns {@code true} if keys with a set use are matched.
	 *
	 * @return {@code true} if keys with a set use are matched, else
	 *         {@code false}.
	 */
	public boolean hasKeyUse() {
		
		return hasUse;
	}
	
	
	/**
	 * Returns {@code true} if keys with a set use are matched.
	 *
	 * @return {@code true} if keys with a set ID are matched, else
	 *         {@code false}.
	 */
	public boolean hasKeyID() {
		
		return hasID;
	}


	/**
	 * Returns {@code true} if only private keys are matched.
	 *
	 * @return {@code true} if only private keys are matched, else 
	 *         {@code false}.
	 */
	public boolean isPrivateOnly() {

		return privateOnly;
	}


	/**
	 * Returns {@code true} if only public keys are matched.
	 *
	 * @return {@code true} if only public keys are selected, else
	 *         {@code false}.
	 */
	public boolean isPublicOnly() {

		return publicOnly;
	}


	/**
	 * Returns the minimum key size. Use {@link #getMinKeySize()} instead.
	 *
	 * @return The minimum key size in bits, zero implies no minimum size
	 *         limit.
	 */
	@Deprecated
	public int getMinSize() {

		return getMinKeySize();
	}


	/**
	 * Returns the minimum key size.
	 *
	 * @return The minimum key size in bits, zero implies no minimum size
	 *         limit.
	 */
	public int getMinKeySize() {

		return minSizeBits;
	}


	/**
	 * Returns the maximum key size. Use {@link #getMaxKeySize()} instead.
	 *
	 * @return The maximum key size in bits, zero implies no maximum size
	 *         limit.
	 */
	@Deprecated
	public int getMaxSize() {

		return getMaxKeySize();
	}


	/**
	 * Returns the maximum key size.
	 *
	 * @return The maximum key size in bits, zero implies no maximum size
	 *         limit.
	 */
	public int getMaxKeySize() {

		return maxSizeBits;
	}
	
	
	/**
	 * Returns the key sizes.
	 *
	 * @return The key sizes in bits, {@code null} if not specified.
	 */
	public Set<Integer> getKeySizes() {
		
		return sizesBits;
	}
	
	
	/**
	 * Returns the curves to match (for EC and OKP keys).
	 *
	 * @return The curves, {@code null} if not specified.
	 */
	public Set<Curve> getCurves() {
		
		return curves;
	}

	/**
	 * Returns the X.509 certificate SHA-256 thumbprints to match.
	 *
	 * @return The thumbprints, {@code null} if not specified.
	 */
	public Set<Base64URL> getX509CertSHA256Thumbprints() {
		
		return x5tS256s;
	}

	/**
	 * Returns {@code true} if the specified JWK matches.
	 *
	 * @param key The JSON Web Key (JWK). Must not  be {@code null}.
	 *
	 * @return {@code true} if the JWK matches, else {@code false}.
	 */
	public boolean matches(final JWK key) {
		
		if (hasUse && key.getKeyUse() == null)
			return false;
		
		if (hasID && (key.getKeyID() == null || key.getKeyID().trim().isEmpty()))
			return false;

		if (privateOnly && ! key.isPrivate())
			return false;

		if (publicOnly && key.isPrivate())
			return false;

		if (types != null && ! types.contains(key.getKeyType()))
			return false;

		if (uses != null && ! uses.contains(key.getKeyUse()))
			return false;

		if (ops != null) {

			if (ops.contains(null) && key.getKeyOperations() == null) {
				// pass
			} else if (key.getKeyOperations() != null && ops.containsAll(key.getKeyOperations())) {
				// pass
			} else {
				return false;
			}
		}

		if (algs != null && ! algs.contains(key.getAlgorithm()))
			return false;

		if (ids != null && ! ids.contains(key.getKeyID()))
			return false;

		if (minSizeBits > 0) {

			if (key.size() < minSizeBits)
				return false;
		}

		if (maxSizeBits > 0) {

			if (key.size() > maxSizeBits)
				return false;
		}
		
		if (sizesBits != null) {
			if (! sizesBits.contains(key.size()))
				return false;
		}
		
		if (curves != null) {
			
			if (! (key instanceof CurveBasedJWK))
				return false;
			
			CurveBasedJWK curveBasedJWK = (CurveBasedJWK) key;
			
			if (! curves.contains(curveBasedJWK.getCurve()))
				return false;
		}

		if (x5tS256s != null) {
			if (! x5tS256s.contains(key.getX509CertSHA256Thumbprint()) )
				return false;
		}

		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		append(sb, "kty", types);
		append(sb, "use", uses);
		append(sb, "key_ops", ops);
		append(sb, "alg", algs);
		append(sb, "kid", ids);
		
		if (hasUse) {
			sb.append("has_use=true ");
		}
		
		if (hasID) {
			sb.append("has_id=true ");
		}
		
		if (privateOnly) {
			sb.append("private_only=true ");
		}
		
		if (publicOnly) {
			sb.append("public_only=true ");
		}
		
		if (minSizeBits > 0) {
			sb.append("min_size=" + minSizeBits + " ");
		}
		
		if (maxSizeBits > 0) {
			sb.append("max_size=" + maxSizeBits + " ");
		}
		
		append(sb, "size", sizesBits);
		append(sb, "crv", curves);
		append(sb, "x5t#S256", x5tS256s);
			
		return sb.toString().trim();
	}
	
	
	/**
	 * Appends the specified JWK matcher parameter to a string builder.
	 *
	 * @param sb     The string builder. Must not be {@code null}.
	 * @param key    The parameter key. Must not be {@code null}.
	 * @param values The parameter value, {@code null} if not specified.
	 */
	private static void append(final StringBuilder sb, final String key, final Set<?> values) {
		
		if (values != null) {
			
			sb.append(key);
			sb.append('=');
			if (values.size() == 1) {
				Object value = values.iterator().next();
				if (value == null) {
					sb.append("ANY");
				} else {
					sb.append(value.toString().trim());
				}
			} else {
				sb.append(values.toString().trim());
			}
			
			sb.append(' ');
		}
	}
}
