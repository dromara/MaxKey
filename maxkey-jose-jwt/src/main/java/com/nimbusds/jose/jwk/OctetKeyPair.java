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

package com.nimbusds.jose.jwk;


import java.net.URI;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.*;

import net.jcip.annotations.Immutable;
import net.minidev.json.JSONObject;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.ByteUtils;
import com.nimbusds.jose.util.JSONObjectUtils;


/**
 * {@link KeyType#OKP Octet key pair} JSON Web Key (JWK), used to represent
 * Edwards-curve keys. This class is immutable.
 *
 * <p>Supported curves:
 *
 * <ul>
 *     <li>{@link Curve#Ed25519 Ed25519}
 *     <li>{@link Curve#Ed448 Ed448}
 *     <li>{@link Curve#X25519 X25519}
 *     <li>{@link Curve#X448 X448}
 * </ul>
 *
 * <p>Example JSON object representation of a public OKP JWK:
 *
 * <pre>
 * {
 *   "kty" : "OKP",
 *   "crv" : "Ed25519",
 *   "x"   : "11qYAYKxCrfVS_7TyWQHOg7hcvPapiMlrwIaaPcHURo",
 *   "use" : "sig",
 *   "kid" : "1"
 * }
 * </pre>
 *
 * <p>Example JSON object representation of a private OKP JWK:
 *
 * <pre>
 * {
 *   "kty" : "OKP",
 *   "crv" : "Ed25519",
 *   "x"   : "11qYAYKxCrfVS_7TyWQHOg7hcvPapiMlrwIaaPcHURo",
 *   "d"   : "nWGxne_9WmC6hEr0kuwsxERJxWl7MmkZcDusAxyuf2A",
 *   "use" : "sig",
 *   "kid" : "1"
 * }
 * </pre>
 *
 * <p>Use the builder to create a new OKP JWK:
 *
 * <pre>
 * OctetKeyPair key = new OctetKeyPair.Builder(Curve.Ed25519, x)
 * 	.keyUse(KeyUse.SIGNATURE)
 * 	.keyID("1")
 * 	.build();
 * </pre>
 *
 * @author Vladimir Dzhuvinov
 * @version 2019-04-15
 */
@Immutable
public class OctetKeyPair extends JWK implements AsymmetricJWK, CurveBasedJWK {
	
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Supported Edwards curves.
	 */
	public static final Set<Curve> SUPPORTED_CURVES = Collections.unmodifiableSet(
		new HashSet<>(Arrays.asList(Curve.Ed25519, Curve.Ed448, Curve.X25519, Curve.X448))
	);
	
	
	/**
	 * Builder for constructing Octet Key Pair JWKs.
	 *
	 * <p>Example usage:
	 *
	 * <pre>
	 * OctetKeyPair key = new OctetKeyPair.Builder(Curve.Ed25519, x)
	 *     .d(d)
	 *     .algorithm(JWSAlgorithm.EdDSA)
	 *     .keyID("1")
	 *     .build();
	 * </pre>
	 */
	public static class Builder {
		
		
		/**
		 * The curve name.
		 */
		private final Curve crv;
		
		
		/**
		 * The public 'x' parameter.
		 */
		private final Base64URL x;
		
		
		/**
		 * The private 'd' parameter, optional.
		 */
		private Base64URL d;
		
		
		/**
		 * The key use, optional.
		 */
		private KeyUse use;
		
		
		/**
		 * The key operations, optional.
		 */
		private Set<KeyOperation> ops;
		
		
		/**
		 * The intended JOSE algorithm for the key, optional.
		 */
		private Algorithm alg;
		
		
		/**
		 * The key ID, optional.
		 */
		private String kid;
		
		
		/**
		 * X.509 certificate URL, optional.
		 */
		private URI x5u;
		
		
		/**
		 * X.509 certificate SHA-1 thumbprint, optional.
		 */
		@Deprecated
		private Base64URL x5t;
		
		
		/**
		 * X.509 certificate SHA-256 thumbprint, optional.
		 */
		private Base64URL x5t256;
		
		
		/**
		 * The X.509 certificate chain, optional.
		 */
		private List<Base64> x5c;
		
		
		/**
		 * Reference to the underlying key store, {@code null} if none.
		 */
		private KeyStore ks;
		
		
		/**
		 * Creates a new Octet Key Pair JWK builder.
		 *
		 * @param crv The cryptographic curve. Must not be
		 *            {@code null}.
		 * @param x   The public 'x' parameter. Must not be 
		 *            {@code null}.
		 */
		public Builder(final Curve crv, final Base64URL x) {
			
			if (crv == null) {
				throw new IllegalArgumentException("The curve must not be null");
			}
			
			this.crv = crv;
			
			if (x == null) {
				throw new IllegalArgumentException("The 'x' coordinate must not be null");
			}
			
			this.x = x;
		}
		
		
		/**
		 * Creates a new Octet Key Pair JWK builder.
		 *
		 * @param okpJWK The Octet Key Pair to start with. Must not be
		 *              {@code null}.
		 */
		public Builder(final OctetKeyPair okpJWK) {
			
			crv = okpJWK.crv;
			x = okpJWK.x;
			d = okpJWK.d;
			use = okpJWK.getKeyUse();
			ops = okpJWK.getKeyOperations();
			alg = okpJWK.getAlgorithm();
			kid = okpJWK.getKeyID();
			x5u = okpJWK.getX509CertURL();
			x5t = okpJWK.getX509CertThumbprint();
			x5t256 = okpJWK.getX509CertSHA256Thumbprint();
			x5c = okpJWK.getX509CertChain();
			ks = okpJWK.getKeyStore();
		}
		
		
		/**
		 * Sets the private 'd' parameter.
		 *
		 * @param d The private 'd' parameter, {@code null} if not 
		 *          specified (for a public key).
		 *
		 * @return This builder.
		 */
		public OctetKeyPair.Builder d(final Base64URL d) {
			
			this.d = d;
			return this;
		}
		
		
		/**
		 * Sets the use ({@code use}) of the JWK.
		 *
		 * @param use The key use, {@code null} if not specified or if
		 *            the key is intended for signing as well as
		 *            encryption.
		 *
		 * @return This builder.
		 */
		public OctetKeyPair.Builder keyUse(final KeyUse use) {
			
			this.use = use;
			return this;
		}
		
		
		/**
		 * Sets the operations ({@code key_ops}) of the JWK.
		 *
		 * @param ops The key operations, {@code null} if not
		 *            specified.
		 *
		 * @return This builder.
		 */
		public OctetKeyPair.Builder keyOperations(final Set<KeyOperation> ops) {
			
			this.ops = ops;
			return this;
		}
		
		
		/**
		 * Sets the intended JOSE algorithm ({@code alg}) for the JWK.
		 *
		 * @param alg The intended JOSE algorithm, {@code null} if not
		 *            specified.
		 *
		 * @return This builder.
		 */
		public OctetKeyPair.Builder algorithm(final Algorithm alg) {
			
			this.alg = alg;
			return this;
		}
		
		/**
		 * Sets the ID ({@code kid}) of the JWK. The key ID can be used
		 * to match a specific key. This can be used, for instance, to
		 * choose a key within a {@link JWKSet} during key rollover.
		 * The key ID may also correspond to a JWS/JWE {@code kid}
		 * header parameter value.
		 *
		 * @param kid The key ID, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public OctetKeyPair.Builder keyID(final String kid) {
			
			this.kid = kid;
			return this;
		}
		
		
		/**
		 * Sets the ID ({@code kid}) of the JWK to its SHA-256 JWK
		 * thumbprint (RFC 7638). The key ID can be used to match a
		 * specific key. This can be used, for instance, to choose a
		 * key within a {@link JWKSet} during key rollover. The key ID
		 * may also correspond to a JWS/JWE {@code kid} header
		 * parameter value.
		 *
		 * @return This builder.
		 *
		 * @throws JOSEException If the SHA-256 hash algorithm is not
		 *                       supported.
		 */
		public OctetKeyPair.Builder keyIDFromThumbprint()
			throws JOSEException {
			
			return keyIDFromThumbprint("SHA-256");
		}
		
		
		/**
		 * Sets the ID ({@code kid}) of the JWK to its JWK thumbprint
		 * (RFC 7638). The key ID can be used to match a specific key.
		 * This can be used, for instance, to choose a key within a
		 * {@link JWKSet} during key rollover. The key ID may also
		 * correspond to a JWS/JWE {@code kid} header parameter value.
		 *
		 * @param hashAlg The hash algorithm for the JWK thumbprint
		 *                computation. Must not be {@code null}.
		 *
		 * @return This builder.
		 *
		 * @throws JOSEException If the hash algorithm is not
		 *                       supported.
		 */
		public OctetKeyPair.Builder keyIDFromThumbprint(final String hashAlg)
			throws JOSEException {
			
			// Put mandatory params in sorted order
			LinkedHashMap<String,String> requiredParams = new LinkedHashMap<>();
			requiredParams.put("crv", crv.toString());
			requiredParams.put("kty", KeyType.OKP.getValue());
			requiredParams.put("x", x.toString());
			this.kid = ThumbprintUtils.compute(hashAlg, requiredParams).toString();
			return this;
		}
		
		
		/**
		 * Sets the X.509 certificate URL ({@code x5u}) of the JWK.
		 *
		 * @param x5u The X.509 certificate URL, {@code null} if not
		 *            specified.
		 *
		 * @return This builder.
		 */
		public OctetKeyPair.Builder x509CertURL(final URI x5u) {
			
			this.x5u = x5u;
			return this;
		}
		
		
		/**
		 * Sets the X.509 certificate SHA-1 thumbprint ({@code x5t}) of
		 * the JWK.
		 *
		 * @param x5t The X.509 certificate SHA-1 thumbprint,
		 *            {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		@Deprecated
		public OctetKeyPair.Builder x509CertThumbprint(final Base64URL x5t) {
			
			this.x5t = x5t;
			return this;
		}
		
		
		/**
		 * Sets the X.509 certificate SHA-256 thumbprint
		 * ({@code x5t#S256}) of the JWK.
		 *
		 * @param x5t256 The X.509 certificate SHA-256 thumbprint,
		 *               {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public OctetKeyPair.Builder x509CertSHA256Thumbprint(final Base64URL x5t256) {
			
			this.x5t256 = x5t256;
			return this;
		}
		
		
		/**
		 * Sets the X.509 certificate chain ({@code x5c}) of the JWK.
		 *
		 * @param x5c The X.509 certificate chain as a unmodifiable
		 *            list, {@code null} if not specified.
		 *
		 * @return This builder.
		 */
		public OctetKeyPair.Builder x509CertChain(final List<Base64> x5c) {
			
			this.x5c = x5c;
			return this;
		}
		
		
		/**
		 * Sets the underlying key store.
		 *
		 * @param keyStore Reference to the underlying key store,
		 *                 {@code null} if none.
		 *
		 * @return This builder.
		 */
		public OctetKeyPair.Builder keyStore(final KeyStore keyStore) {
			
			this.ks = keyStore;
			return this;
		}
		
		
		/**
		 * Builds a new Octet Key Pair JWK.
		 *
		 * @return The Octet Key Pair JWK.
		 *
		 * @throws IllegalStateException If the JWK parameters were
		 *                               inconsistently specified.
		 */
		public OctetKeyPair build() {
			
			try {
				if (d == null) {
					// Public key
					return new OctetKeyPair(crv, x, use, ops, alg, kid, x5u, x5t, x5t256, x5c, ks);
				}
				
				// Public / private key pair with 'd'
				return new OctetKeyPair(crv, x, d, use, ops, alg, kid, x5u, x5t, x5t256, x5c, ks);
				
			} catch (IllegalArgumentException e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
		}
	}
	
	
	/**
	 * The curve name.
	 */
	private final Curve crv;
	
	
	/**
	 * The public 'x' parameter.
	 */
	private final Base64URL x;


	/**
	 * The public 'x' parameter, decoded from Base64.
	 * Cached for performance and to reduce the risk of side channel attacks
	 * against the Base64 decoding procedure.
	 */
	private final byte[] decodedX;


	/**
	 * The private 'd' parameter.
	 */
	private final Base64URL d;


	/**
	 * The private 'd' parameter, decoded from Base64.
	 * Cached for performance and to reduce the risk of side channel attacks
	 * against the Base64 decoding procedure.
	 */
	private final byte[] decodedD;


	/**
	 * Creates a new public Octet Key Pair JSON Web Key (JWK) with the
	 * specified parameters.
	 *
	 * @param crv    The cryptographic curve. Must not be {@code null}.
	 * @param x      The public 'x' parameter. Must not be {@code null}.
	 * @param use    The key use, {@code null} if not specified or if the
	 *               key is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null}
	 *               if not specified.
	 * @param kid    The key ID, {@code null} if not specified.
	 * @param x5u    The X.509 certificate URL, {@code null} if not
	 *               specified.
	 * @param x5t    The X.509 certificate SHA-1 thumbprint, {@code null}
	 *               if not specified.
	 * @param x5t256 The X.509 certificate SHA-256 thumbprint, {@code null}
	 *               if not specified.
	 * @param x5c    The X.509 certificate chain, {@code null} if not
	 *               specified.
	 * @param ks     Reference to the underlying key store, {@code null} if
	 *               not specified.
	 */
	public OctetKeyPair(final Curve crv, final Base64URL x,
			    final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
			    final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
			    final KeyStore ks) {
		
		super(KeyType.OKP, use, ops, alg, kid, x5u, x5t, x5t256, x5c, ks);
		
		if (crv == null) {
			throw new IllegalArgumentException("The curve must not be null");
		}
		
		if (! SUPPORTED_CURVES.contains(crv)) {
			throw new IllegalArgumentException("Unknown / unsupported curve: " + crv);
		}
		
		this.crv = crv;
		
		if (x == null) {
			throw new IllegalArgumentException("The 'x' parameter must not be null");
		}
		
		this.x = x;
		decodedX = x.decode();
		
		d = null;
		decodedD = null;
	}
	
	
	/**
	 * Creates a new public / private Octet Key Pair JSON Web Key (JWK)
	 * with the specified parameters.
	 *
	 * @param crv    The cryptographic curve. Must not be {@code null}.
	 * @param x      The public 'x' parameter. Must not be {@code null}.
	 * @param d      The private 'd' parameter. Must not be {@code null}.
	 * @param use    The key use, {@code null} if not specified or if the
	 *               key is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null}
	 *               if not specified.
	 * @param kid    The key ID, {@code null} if not specified.
	 * @param x5u    The X.509 certificate URL, {@code null} if not
	 *               specified.
	 * @param x5t    The X.509 certificate SHA-1 thumbprint, {@code null}
	 *               if not specified.
	 * @param x5t256 The X.509 certificate SHA-256 thumbprint, {@code null}
	 *               if not specified.
	 * @param x5c    The X.509 certificate chain, {@code null} if not
	 *               specified.
	 * @param ks     Reference to the underlying key store, {@code null} if
	 *               not specified.
	 */
	public OctetKeyPair(final Curve crv, final Base64URL x, final Base64URL d,
			    final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
			    final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
			    final KeyStore ks) {
		
		super(KeyType.OKP, use, ops, alg, kid, x5u, x5t, x5t256, x5c, ks);
		
		if (crv == null) {
			throw new IllegalArgumentException("The curve must not be null");
		}
		
		if (! SUPPORTED_CURVES.contains(crv)) {
			throw new IllegalArgumentException("Unknown / unsupported curve: " + crv);
		}
		
		this.crv = crv;
		
		if (x == null) {
			throw new IllegalArgumentException("The 'x' parameter must not be null");
		}
		
		this.x = x;
		decodedX = x.decode();
		
		if (d == null) {
			throw new IllegalArgumentException("The 'd' parameter must not be null");
		}
		
		this.d = d;
		decodedD = d.decode();
	}
	
	
	@Override
	public Curve getCurve() {
		
		return crv;
	}
	
	
	/**
	 * Gets the public 'x' parameter.
	 *
	 * @return The public 'x' parameter.
	 */
	public Base64URL getX() {
		
		return x;
	}


	/**
	 * Gets the public 'x' parameter, decoded from Base64.
	 *
	 * @return The public 'x' parameter in bytes.
	 */
	public byte[] getDecodedX() {

		return decodedX.clone();
	}


	/**
	 * Gets the private 'd' parameter.
	 *
	 * @return The private 'd' coordinate, {@code null} if not specified
	 *         (for a public key).
	 */
	public Base64URL getD() {
		
		return d;
	}


	/**
	 * Gets the private 'd' parameter, decoded from Base64.
	 *
	 * @return The private 'd' coordinate in bytes, {@code null} if not specified
	 *         (for a public key).
	 */
	public byte[] getDecodedD() {

		return decodedD == null ? null : decodedD.clone();
	}


	@Override
	public PublicKey toPublicKey()
		throws JOSEException {
		
		throw new JOSEException("Export to java.security.PublicKey not supported");
	}
	
	
	@Override
	public PrivateKey toPrivateKey()
		throws JOSEException {
		
		throw new JOSEException("Export to java.security.PrivateKey not supported");
	}
	
	
	@Override
	public KeyPair toKeyPair()
		throws JOSEException {
		
		throw new JOSEException("Export to java.security.KeyPair not supported");
	}
	
	
	@Override
	public boolean matches(final X509Certificate cert) {
		// X.509 certs don't support OKP yet
		return false;
	}
	
	
	@Override
	public LinkedHashMap<String,?> getRequiredParams() {
		
		// Put mandatory params in sorted order
		LinkedHashMap<String,String> requiredParams = new LinkedHashMap<>();
		requiredParams.put("crv", crv.toString());
		requiredParams.put("kty", getKeyType().getValue());
		requiredParams.put("x", x.toString());
		return requiredParams;
	}
	
	
	@Override
	public boolean isPrivate() {
		
		return d != null;
	}
	
	
	/**
	 * Returns a copy of this Octet Key Pair JWK with any private values
	 * removed.
	 *
	 * @return The copied public Octet Key Pair JWK.
	 */
	@Override
	public OctetKeyPair toPublicJWK() {
		
		return new OctetKeyPair(
			getCurve(), getX(),
			getKeyUse(), getKeyOperations(), getAlgorithm(), getKeyID(),
			getX509CertURL(), getX509CertThumbprint(), getX509CertSHA256Thumbprint(), getX509CertChain(),
			getKeyStore());
	}
	
	
	@Override
	public JSONObject toJSONObject() {
		
		JSONObject o = super.toJSONObject();
		
		// Append OKP specific attributes
		o.put("crv", crv.toString());
		o.put("x", x.toString());
		
		if (d != null) {
			o.put("d", d.toString());
		}
		
		return o;
	}
	
	
	@Override
	public int size() {
		
		return ByteUtils.bitLength(x.decode());
	}
	
	
	/**
	 * Parses a public / private Octet Key Pair JWK from the specified JSON
	 * object string representation.
	 *
	 * @param s The JSON object string to parse. Must not be {@code null}.
	 *
	 * @return The public / private Octet Key Pair JWK.
	 *
	 * @throws ParseException If the string couldn't be parsed to an Octet
	 *                        Key Pair JWK.
	 */
	public static OctetKeyPair parse(final String s)
		throws ParseException {
		
		return parse(JSONObjectUtils.parse(s));
	}
	
	
	/**
	 * Parses a public / private Octet Key Pair JWK from the specified JSON
	 * object representation.
	 *
	 * @param jsonObject The JSON object to parse. Must not be
	 *                   {@code null}.
	 *
	 * @return The public / private Octet Key Pair JWK.
	 *
	 * @throws ParseException If the JSON object couldn't be parsed to an
	 *                        Octet Key Pair JWK.
	 */
	public static OctetKeyPair parse(final JSONObject jsonObject)
		throws ParseException {
		
		// Parse the mandatory parameters first
		Curve crv = Curve.parse(JSONObjectUtils.getString(jsonObject, "crv"));
		Base64URL x = new Base64URL(JSONObjectUtils.getString(jsonObject, "x"));
		
		// Check key type
		KeyType kty = JWKMetadata.parseKeyType(jsonObject);
		
		if (kty != KeyType.OKP) {
			throw new ParseException("The key type \"kty\" must be OKP", 0);
		}
		
		// Get optional private key
		Base64URL d = null;
		if (jsonObject.get("d") != null) {
			d = new Base64URL(JSONObjectUtils.getString(jsonObject, "d"));
		}
		
		
		try {
			if (d == null) {
				// Public key
				return new OctetKeyPair(crv, x,
					JWKMetadata.parseKeyUse(jsonObject),
					JWKMetadata.parseKeyOperations(jsonObject),
					JWKMetadata.parseAlgorithm(jsonObject),
					JWKMetadata.parseKeyID(jsonObject),
					JWKMetadata.parseX509CertURL(jsonObject),
					JWKMetadata.parseX509CertThumbprint(jsonObject),
					JWKMetadata.parseX509CertSHA256Thumbprint(jsonObject),
					JWKMetadata.parseX509CertChain(jsonObject),
					null);
				
			} else {
				// Key pair
				return new OctetKeyPair(crv, x, d,
					JWKMetadata.parseKeyUse(jsonObject),
					JWKMetadata.parseKeyOperations(jsonObject),
					JWKMetadata.parseAlgorithm(jsonObject),
					JWKMetadata.parseKeyID(jsonObject),
					JWKMetadata.parseX509CertURL(jsonObject),
					JWKMetadata.parseX509CertThumbprint(jsonObject),
					JWKMetadata.parseX509CertSHA256Thumbprint(jsonObject),
					JWKMetadata.parseX509CertChain(jsonObject),
					null);
			}
			
		} catch (IllegalArgumentException ex) {
			
			// Conflicting 'use' and 'key_ops'
			throw new ParseException(ex.getMessage(), 0);
		}
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OctetKeyPair)) return false;
		if (!super.equals(o)) return false;
		OctetKeyPair that = (OctetKeyPair) o;
		return Objects.equals(crv, that.crv) &&
				Objects.equals(x, that.x) &&
				Arrays.equals(decodedX, that.decodedX) &&
				Objects.equals(d, that.d) &&
				Arrays.equals(decodedD, that.decodedD);
	}

	
	@Override
	public int hashCode() {
		int result = Objects.hash(super.hashCode(), crv, x, d);
		result = 31 * result + Arrays.hashCode(decodedX);
		result = 31 * result + Arrays.hashCode(decodedD);
		return result;
	}
}
