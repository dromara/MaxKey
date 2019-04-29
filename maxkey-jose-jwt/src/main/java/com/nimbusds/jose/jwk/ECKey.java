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

package com.nimbusds.jose.jwk;


import java.math.BigInteger;
import java.net.URI;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import java.text.ParseException;
import java.util.*;

import net.jcip.annotations.Immutable;
import net.minidev.json.JSONObject;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.utils.ECChecks;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.BigIntegerUtils;
import com.nimbusds.jose.util.JSONObjectUtils;


/**
 * Public and private {@link KeyType#EC Elliptic Curve} JSON Web Key (JWK). 
 * This class is immutable.
 *
 * <p>Supported curves:
 *
 * <ul>
 *     <li>{@link Curve#P_256 P-256}
 *     <li>{@link Curve#P_256K P-256K}
 *     <li>{@link Curve#P_384 P-384}
 *     <li>{@link Curve#P_521 P-512}
 * </ul>
 *
 * <p>Provides EC JWK import from / export to the following standard Java
 * interfaces and classes:
 *
 * <ul>
 *     <li>{@link java.security.interfaces.ECPublicKey}
 *     <li>{@link java.security.interfaces.ECPrivateKey}
 *     <li>{@link java.security.PrivateKey} for an EC key in a PKCS#11 store
 *     <li>{@link java.security.KeyPair}
 * </ul>
 *
 * <p>Example JSON object representation of a public EC JWK:
 * 
 * <pre>
 * {
 *   "kty" : "EC",
 *   "crv" : "P-256",
 *   "x"   : "MKBCTNIcKUSDii11ySs3526iDZ8AiTo7Tu6KPAqv7D4",
 *   "y"   : "4Etl6SRW2YiLUrN5vfvVHuhp7x8PxltmWWlbbM4IFyM",
 *   "use" : "enc",
 *   "kid" : "1"
 * }
 * </pre>
 *
 * <p>Example JSON object representation of a private EC JWK:
 *
 * <pre>
 * {
 *   "kty" : "EC",
 *   "crv" : "P-256",
 *   "x"   : "MKBCTNIcKUSDii11ySs3526iDZ8AiTo7Tu6KPAqv7D4",
 *   "y"   : "4Etl6SRW2YiLUrN5vfvVHuhp7x8PxltmWWlbbM4IFyM",
 *   "d"   : "870MB6gfuTJ4HtUnUvYMyJpr5eUZNP4Bk43bVdj3eAE",
 *   "use" : "enc",
 *   "kid" : "1"
 * }
 * </pre>
 *
 * <p>Use the builder to create a new EC JWK:
 *
 * <pre>
 * ECKey key = new ECKey.Builder(Curve.P_256, x, y)
 * 	.keyUse(KeyUse.SIGNATURE)
 * 	.keyID("1")
 * 	.build();
 * </pre>
 *
 * <p>See http://en.wikipedia.org/wiki/Elliptic_curve_cryptography
 *
 * @author Vladimir Dzhuvinov
 * @author Justin Richer
 * @version 2019-04-15
 */
@Immutable
public final class ECKey extends JWK implements AsymmetricJWK, CurveBasedJWK {


	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Supported EC curves.
	 */
	public static final Set<Curve> SUPPORTED_CURVES = Collections.unmodifiableSet(
		new HashSet<>(Arrays.asList(Curve.P_256, Curve.P_256K, Curve.P_384, Curve.P_521))
	);


	/**
	 * Builder for constructing Elliptic Curve JWKs.
	 *
	 * <p>Example usage:
	 *
	 * <pre>
	 * ECKey key = new ECKey.Builder(Curve.P521, x, y)
	 *     .d(d)
	 *     .algorithm(JWSAlgorithm.ES512)
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
		 * The public 'x' EC coordinate.
		 */
		private final Base64URL x;


		/**
		 * The public 'y' EC coordinate.
		 */
		private final Base64URL y;
		

		/**
		 * The private 'd' EC coordinate, optional.
		 */
		private Base64URL d;
		
		
		/**
		 * The private EC key, as PKCS#11 handle, optional.
		 */
		private PrivateKey priv;


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
		 * Creates a new Elliptic Curve JWK builder.
		 *
		 * @param crv The cryptographic curve. Must not be 
		 *            {@code null}.
		 * @param x   The public 'x' coordinate for the elliptic curve 
		 *            point. It is represented as the Base64URL 
		 *            encoding of the coordinate's big endian 
		 *            representation. Must not be {@code null}.
		 * @param y   The public 'y' coordinate for the elliptic curve 
		 *            point. It is represented as the Base64URL 
		 *            encoding of the coordinate's big endian 
		 *            representation. Must not be {@code null}.
		 */
		public Builder(final Curve crv, final Base64URL x, final Base64URL y) {

			if (crv == null) {
				throw new IllegalArgumentException("The curve must not be null");
			}

			this.crv = crv;

			if (x == null) {
				throw new IllegalArgumentException("The 'x' coordinate must not be null");
			}

			this.x = x;

			if (y == null) {
				throw new IllegalArgumentException("The 'y' coordinate must not be null");
			}

			this.y = y;
		}


		/**
		 * Creates a new Elliptic Curve JWK builder.
		 *
		 * @param crv The cryptographic curve. Must not be 
		 *            {@code null}.
		 * @param pub The public EC key to represent. Must not be 
		 *            {@code null}.
		 */
		public Builder(final Curve crv, final ECPublicKey pub) {

			this(crv,
			     encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()),
			     encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()));
		}
		
		
		/**
		 * Creates a new Elliptic Curve JWK builder.
		 *
		 * @param ecJWK The EC JWK to start with. Must not be
		 *              {@code null}.
		 */
		public Builder(final ECKey ecJWK) {
			
			crv = ecJWK.crv;
			x = ecJWK.x;
			y = ecJWK.y;
			d = ecJWK.d;
			priv = ecJWK.privateKey;
			use = ecJWK.getKeyUse();
			ops = ecJWK.getKeyOperations();
			alg = ecJWK.getAlgorithm();
			kid = ecJWK.getKeyID();
			x5u = ecJWK.getX509CertURL();
			x5t = ecJWK.getX509CertThumbprint();
			x5t256 = ecJWK.getX509CertSHA256Thumbprint();
			x5c = ecJWK.getX509CertChain();
			ks = ecJWK.getKeyStore();
		}


		/**
		 * Sets the private 'd' coordinate for the elliptic curve 
		 * point. The alternative method is {@link #privateKey}.
		 *
		 * @param d The private 'd' coordinate. It is represented as
		 *          the Base64URL encoding of the coordinate's big
		 *          endian representation. {@code null} if not
		 *          specified (for a public key).
		 *
		 * @return This builder.
		 */
		public Builder d(final Base64URL d) {

			this.d = d;
			return this;
		}


		/**
		 * Sets the private Elliptic Curve key. The alternative method 
		 * is {@link #d}.
		 *
		 * @param priv The private EC key, used to obtain the private
		 *             'd' coordinate for the elliptic curve point.
		 *             {@code null} if not specified (for a public 
		 *             key).
		 *
		 * @return This builder.
		 */
		public Builder privateKey(final ECPrivateKey priv) {

			if (priv != null) {
				this.d = encodeCoordinate(priv.getParams().getCurve().getField().getFieldSize(), priv.getS());
			}
			
			return this;
		}
		
		
		/**
		 * Sets the private EC key, typically for a key located in a
		 * PKCS#11 store that doesn't expose the private key parameters
		 * (such as a smart card or HSM).
		 *
		 * @param priv The private EC key reference. Its algorithm must
		 *             be "EC". Must not be {@code null}.
		 *
		 * @return This builder.
		 */
		public Builder privateKey(final PrivateKey priv) {

			if (priv instanceof ECPrivateKey) {
				return privateKey((ECPrivateKey) priv);
			}

			if (! "EC".equalsIgnoreCase(priv.getAlgorithm())) {
				throw new IllegalArgumentException("The private key algorithm must be EC");
			}
			
			this.priv = priv;
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
		public Builder keyUse(final KeyUse use) {

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
		public Builder keyOperations(final Set<KeyOperation> ops) {

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
		public Builder algorithm(final Algorithm alg) {

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
		public Builder keyID(final String kid) {

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
		public Builder keyIDFromThumbprint()
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
		public Builder keyIDFromThumbprint(final String hashAlg)
			throws JOSEException {

			// Put mandatory params in sorted order
			LinkedHashMap<String,String> requiredParams = new LinkedHashMap<>();
			requiredParams.put("crv", crv.toString());
			requiredParams.put("kty", KeyType.EC.getValue());
			requiredParams.put("x", x.toString());
			requiredParams.put("y", y.toString());
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
		public Builder x509CertURL(final URI x5u) {

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
		public Builder x509CertThumbprint(final Base64URL x5t) {

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
		public Builder x509CertSHA256Thumbprint(final Base64URL x5t256) {

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
		public Builder x509CertChain(final List<Base64> x5c) {

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
		public Builder keyStore(final KeyStore keyStore) {
			
			this.ks = keyStore;
			return this;
		}


		/**
		 * Builds a new Elliptic Curve JWK.
		 *
		 * @return The Elliptic Curve JWK.
		 *
		 * @throws IllegalStateException If the JWK parameters were
		 *                               inconsistently specified.
		 */
		public ECKey build() {

			try {
				if (d == null && priv == null) {
					// Public key
					return new ECKey(crv, x, y, use, ops, alg, kid, x5u, x5t, x5t256, x5c, ks);
				}
				
				if (priv != null) {
					// PKCS#11 reference to private key
					return new ECKey(crv, x, y, priv, use, ops, alg, kid, x5u, x5t, x5t256, x5c, ks);
				}

				// Public / private key pair with 'd'
				return new ECKey(crv, x, y, d, use, ops, alg, kid, x5u, x5t, x5t256, x5c, ks);

			} catch (IllegalArgumentException e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
		}
	}


	/**
	 * Returns the Base64URL encoding of the specified elliptic curve 'x',
	 * 'y' or 'd' coordinate, with leading zero padding up to the specified
	 * field size in bits.
	 *
	 * @param fieldSize  The field size in bits.
	 * @param coordinate The elliptic curve coordinate. Must not be
	 *                   {@code null}.
	 *
	 * @return The Base64URL-encoded coordinate, with leading zero padding
	 *         up to the curve's field size.
	 */
	public static Base64URL encodeCoordinate(final int fieldSize, final BigInteger coordinate) {

		final byte[] notPadded = BigIntegerUtils.toBytesUnsigned(coordinate);

		int bytesToOutput = (fieldSize + 7)/8;

		if (notPadded.length >= bytesToOutput) {
			// Greater-than check to prevent exception on malformed
			// key below
			return Base64URL.encode(notPadded);
		}

		final byte[] padded = new byte[bytesToOutput];

		System.arraycopy(notPadded, 0, padded, bytesToOutput - notPadded.length, notPadded.length);

		return Base64URL.encode(padded);
	}


	/**
	 * The curve name.
	 */
	private final Curve crv;


	/**
	 * The public 'x' EC coordinate.
	 */
	private final Base64URL x;


	/**
	 * The public 'y' EC coordinate.
	 */
	private final Base64URL y;
	

	/**
	 * The private 'd' EC coordinate.
	 */
	private final Base64URL d;
	
	
	/**
	 * Private PKCS#11 key handle.
	 */
	private final PrivateKey privateKey;
	
	
	/**
	 * Ensures the specified 'x' and 'y' public coordinates are on the
	 * given curve.
	 *
	 * @param crv The curve. Must not be {@code null}.
	 * @param x   The public 'x' coordinate. Must not be {@code null}.
	 * @param y   The public 'y' coordinate. Must not be {@code null}.
	 */
	private static void ensurePublicCoordinatesOnCurve(final Curve crv, final Base64URL x, final Base64URL y) {
		
		if (! SUPPORTED_CURVES.contains(crv)) {
			throw new IllegalArgumentException("Unknown / unsupported curve: " + crv);
		}
		
		if (! ECChecks.isPointOnCurve(x.decodeToBigInteger(), y.decodeToBigInteger(), crv.toECParameterSpec())) {
			throw new IllegalArgumentException("Invalid EC JWK: The 'x' and 'y' public coordinates are not on the " + crv + " curve");
		}
	}


	/**
	 * Creates a new public Elliptic Curve JSON Web Key (JWK) with the 
	 * specified parameters.
	 *
	 * @param crv    The cryptographic curve. Must not be {@code null}.
	 * @param x      The public 'x' coordinate for the elliptic curve
	 *               point. It is represented as the Base64URL encoding of
	 *               the coordinate's big endian representation. Must not
	 *               be {@code null}.
	 * @param y      The public 'y' coordinate for the elliptic curve
	 *               point. It is represented as the Base64URL encoding of
	 *               the coordinate's big endian representation. Must not
	 *               be {@code null}.
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
	public ECKey(final Curve crv, final Base64URL x, final Base64URL y, 
		     final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		     final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		     final KeyStore ks) {

		super(KeyType.EC, use, ops, alg, kid, x5u, x5t, x5t256, x5c, ks);

		if (crv == null) {
			throw new IllegalArgumentException("The curve must not be null");
		}

		this.crv = crv;

		if (x == null) {
			throw new IllegalArgumentException("The 'x' coordinate must not be null");
		}

		this.x = x;

		if (y == null) {
			throw new IllegalArgumentException("The 'y' coordinate must not be null");
		}

		this.y = y;
		
		ensurePublicCoordinatesOnCurve(crv, x, y);
		
		ensureMatches(getParsedX509CertChain());

		this.d = null;
		
		this.privateKey = null;
	}


	/**
	 * Creates a new public / private Elliptic Curve JSON Web Key (JWK) 
	 * with the specified parameters.
	 *
	 * @param crv    The cryptographic curve. Must not be {@code null}.
	 * @param x      The public 'x' coordinate for the elliptic curve
	 *               point. It is represented as the Base64URL encoding of
	 *               the coordinate's big endian representation. Must not
	 *               be {@code null}.
	 * @param y      The public 'y' coordinate for the elliptic curve
	 *               point. It is represented as the Base64URL encoding of
	 *               the coordinate's big endian representation. Must not
	 *               be {@code null}.
	 * @param d      The private 'd' coordinate for the elliptic curve
	 *               point. It is represented as the Base64URL encoding of
	 *               the coordinate's big endian representation. Must not
	 *               be {@code null}.
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
	public ECKey(final Curve crv, final Base64URL x, final Base64URL y, final Base64URL d,
		     final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		     final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		     final KeyStore ks) {

		super(KeyType.EC, use, ops, alg, kid, x5u, x5t, x5t256, x5c, ks);
		
		if (crv == null) {
			throw new IllegalArgumentException("The curve must not be null");
		}

		this.crv = crv;

		if (x == null) {
			throw new IllegalArgumentException("The 'x' coordinate must not be null");
		}

		this.x = x;

		if (y == null) {
			throw new IllegalArgumentException("The 'y' coordinate must not be null");
		}

		this.y = y;
		
		ensurePublicCoordinatesOnCurve(crv, x, y);
		
		ensureMatches(getParsedX509CertChain());
		
		if (d == null) {
			throw new IllegalArgumentException("The 'd' coordinate must not be null");
		}

		this.d = d;
		
		this.privateKey = null;
	}


	/**
	 * Creates a new public / private Elliptic Curve JSON Web Key (JWK)
	 * with the specified parameters. The private key is specified by its
	 * PKCS#11 handle.
	 *
	 * @param crv    The cryptographic curve. Must not be {@code null}.
	 * @param x      The public 'x' coordinate for the elliptic curve
	 *               point. It is represented as the Base64URL encoding of
	 *               the coordinate's big endian representation. Must not
	 *               be {@code null}.
	 * @param y      The public 'y' coordinate for the elliptic curve
	 *               point. It is represented as the Base64URL encoding of
	 *               the coordinate's big endian representation. Must not
	 *               be {@code null}.
	 * @param priv   The private key as a PKCS#11 handle, {@code null} if
	 *               not specified.
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
	 */
	public ECKey(final Curve crv, final Base64URL x, final Base64URL y, final PrivateKey priv,
		     final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		     final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		     final KeyStore ks) {

		super(KeyType.EC, use, ops, alg, kid, x5u, x5t, x5t256, x5c, ks);

		if (crv == null) {
			throw new IllegalArgumentException("The curve must not be null");
		}

		this.crv = crv;

		if (x == null) {
			throw new IllegalArgumentException("The 'x' coordinate must not be null");
		}

		this.x = x;

		if (y == null) {
			throw new IllegalArgumentException("The 'y' coordinate must not be null");
		}

		this.y = y;
		
		ensurePublicCoordinatesOnCurve(crv, x, y);
		
		ensureMatches(getParsedX509CertChain());
		
		d = null;
		
		this.privateKey = priv;
	}


	/**
	 * Creates a new public Elliptic Curve JSON Web Key (JWK) with the 
	 * specified parameters.
	 *
	 * @param crv    The cryptographic curve. Must not be {@code null}.
	 * @param pub    The public EC key to represent. Must not be
	 *               {@code null}.
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
	public ECKey(final Curve crv, final ECPublicKey pub, 
		     final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		     final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		     final KeyStore ks) {

		this(crv, 
		     encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()),
		     encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()),
		     use, ops, alg, kid,
		     x5u, x5t, x5t256, x5c,
		     ks);
	}


	/**
	 * Creates a new public / private Elliptic Curve JSON Web Key (JWK) 
	 * with the specified parameters.
	 *
	 * @param crv    The cryptographic curve. Must not be {@code null}.
	 * @param pub    The public EC key to represent. Must not be
	 *               {@code null}.
	 * @param priv   The private EC key to represent. Must not be
	 *               {@code null}.
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
	public ECKey(final Curve crv, final ECPublicKey pub, final ECPrivateKey priv, 
		     final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		     final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		     final KeyStore ks) {

		this(crv,
		     encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()),
		     encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()),
		     encodeCoordinate(priv.getParams().getCurve().getField().getFieldSize(), priv.getS()),
		     use, ops, alg, kid,
		     x5u, x5t, x5t256, x5c,
		     ks);
	}


	/**
	 * Creates a new public / private Elliptic Curve JSON Web Key (JWK)
	 * with the specified parameters. The private key is specified by its
	 * PKCS#11 handle.
	 *
	 * @param crv    The cryptographic curve. Must not be {@code null}.
	 * @param pub    The public EC key to represent. Must not be
	 *               {@code null}.
	 * @param priv   The private key as a PKCS#11 handle, {@code null} if
	 *               not specified.
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
	public ECKey(final Curve crv, final ECPublicKey pub, final PrivateKey priv,
		     final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		     final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		     final KeyStore ks) {
		
		this(
			crv,
			encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineX()),
			encodeCoordinate(pub.getParams().getCurve().getField().getFieldSize(), pub.getW().getAffineY()),
			priv,
			use, ops, alg, kid, x5u, x5t, x5t256, x5c,
			ks);
	}


	@Override
	public Curve getCurve() {

		return crv;
	}


	/**
	 * Gets the public 'x' coordinate for the elliptic curve point.
	 *
	 * @return The 'x' coordinate. It is represented as the Base64URL 
	 *         encoding of the coordinate's big endian representation.
	 */
	public Base64URL getX() {

		return x;
	}


	/**
	 * Gets the public 'y' coordinate for the elliptic curve point.
	 *
	 * @return The 'y' coordinate. It is represented as the Base64URL 
	 *         encoding of the coordinate's big endian representation.
	 */
	public Base64URL getY() {

		return y;
	}

	
	/**
	 * Gets the private 'd' coordinate for the elliptic curve point. It is 
	 * represented as the Base64URL encoding of the coordinate's big endian 
	 * representation.
	 *
	 * @return The 'd' coordinate.  It is represented as the Base64URL 
	 *         encoding of the coordinate's big endian representation. 
	 *         {@code null} if not specified (for a public key).
	 */
	public Base64URL getD() {

		return d;
	}


	/**
	 * Returns a standard {@code java.security.interfaces.ECPublicKey} 
	 * representation of this Elliptic Curve JWK. Uses the default JCA
	 * provider.
	 * 
	 * @return The public Elliptic Curve key.
	 * 
	 * @throws JOSEException If EC is not supported by the underlying Java
	 *                       Cryptography (JCA) provider or if the JWK
	 *                       parameters are invalid for a public EC key.
	 */
	public ECPublicKey toECPublicKey()
		throws JOSEException {

		return toECPublicKey(null);
	}


	/**
	 * Returns a standard {@code java.security.interfaces.ECPublicKey}
	 * representation of this Elliptic Curve JWK.
	 *
	 * @param provider The specific JCA provider to use, {@code null}
	 *                 implies the default one.
	 *
	 * @return The public Elliptic Curve key.
	 *
	 * @throws JOSEException If EC is not supported by the underlying Java
	 *                       Cryptography (JCA) provider or if the JWK
	 *                       parameters are invalid for a public EC key.
	 */
	public ECPublicKey toECPublicKey(final Provider provider)
		throws JOSEException {

		ECParameterSpec spec = crv.toECParameterSpec();

		if (spec == null) {
			throw new JOSEException("Couldn't get EC parameter spec for curve " + crv);
		}

		ECPoint w = new ECPoint(x.decodeToBigInteger(), y.decodeToBigInteger());

		ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(w, spec);

		try {
			KeyFactory keyFactory;

			if (provider == null) {
				keyFactory = KeyFactory.getInstance("EC");
			} else {
				keyFactory = KeyFactory.getInstance("EC", provider);
			}

			return (ECPublicKey) keyFactory.generatePublic(publicKeySpec);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

			throw new JOSEException(e.getMessage(), e);
		}
	}
	

	/**
	 * Returns a standard {@code java.security.interfaces.ECPrivateKey} 
	 * representation of this Elliptic Curve JWK. Uses the default JCA
	 * provider.
	 * 
	 * @return The private Elliptic Curve key, {@code null} if not 
	 *         specified by this JWK.
	 * 
	 * @throws JOSEException If EC is not supported by the underlying Java
	 *                       Cryptography (JCA) provider or if the JWK
	 *                       parameters are invalid for a private EC key.
	 */
	public ECPrivateKey toECPrivateKey()
		throws JOSEException {

		return toECPrivateKey(null);
	}


	/**
	 * Returns a standard {@code java.security.interfaces.ECPrivateKey}
	 * representation of this Elliptic Curve JWK.
	 *
	 * @param provider The specific JCA provider to use, {@code null}
	 *                 implies the default one.
	 *
	 * @return The private Elliptic Curve key, {@code null} if not
	 *         specified by this JWK.
	 *
	 * @throws JOSEException If EC is not supported by the underlying Java
	 *                       Cryptography (JCA) provider or if the JWK
	 *                       parameters are invalid for a private EC key.
	 */
	public ECPrivateKey toECPrivateKey(final Provider provider)
		throws JOSEException {

		if (d == null) {
			// No private 'd' param
			return null;
		}

		ECParameterSpec spec = crv.toECParameterSpec();

		if (spec == null) {
			throw new JOSEException("Couldn't get EC parameter spec for curve " + crv);
		}

		ECPrivateKeySpec privateKeySpec = new ECPrivateKeySpec(d.decodeToBigInteger(), spec);

		try {
			KeyFactory keyFactory;

			if (provider == null) {
				keyFactory = KeyFactory.getInstance("EC");
			} else {
				keyFactory = KeyFactory.getInstance("EC", provider);
			}

			return (ECPrivateKey) keyFactory.generatePrivate(privateKeySpec);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

			throw new JOSEException(e.getMessage(), e);
		}
	}


	@Override
	public PublicKey toPublicKey()
		throws JOSEException {

		return toECPublicKey();
	}


	@Override
	public PrivateKey toPrivateKey()
		throws JOSEException {
		
		PrivateKey prv = toECPrivateKey();
		
		if (prv != null) {
			// Return private EC key with key material
			return prv;
		}
		
		// Return private EC key as PKCS#11 handle, or null
		return privateKey;
	}
	

	/**
	 * Returns a standard {@code java.security.KeyPair} representation of 
	 * this Elliptic Curve JWK. Uses the default JCA provider.
	 * 
	 * @return The Elliptic Curve key pair. The private Elliptic Curve key 
	 *         will be {@code null} if not specified.
	 * 
	 * @throws JOSEException If EC is not supported by the underlying Java
	 *                       Cryptography (JCA) provider or if the JWK
	 *                       parameters are invalid for a public and / or
	 *                       private EC key.
	 */
	@Override
	public KeyPair toKeyPair()
		throws JOSEException {

		return toKeyPair(null);
	}


	/**
	 * Returns a standard {@code java.security.KeyPair} representation of
	 * this Elliptic Curve JWK.
	 *
	 * @param provider The specific JCA provider to use, {@code null}
	 *                 implies the default one.
	 *
	 * @return The Elliptic Curve key pair. The private Elliptic Curve key
	 *         will be {@code null} if not specified.
	 *
	 * @throws JOSEException If EC is not supported by the underlying Java
	 *                       Cryptography (JCA) provider or if the JWK
	 *                       parameters are invalid for a public and / or
	 *                       private EC key.
	 */
	public KeyPair toKeyPair(final Provider provider)
		throws JOSEException {

		if (privateKey != null) {
			// Private key as PKCS#11 handle
			return new KeyPair(toECPublicKey(provider), privateKey);
		} else {
			return new KeyPair(toECPublicKey(provider), toECPrivateKey(provider));
		}
	}
	
	
	@Override
	public boolean matches(final X509Certificate cert) {
		
		ECPublicKey certECKey;
		try {
			certECKey = (ECPublicKey) getParsedX509CertChain().get(0).getPublicKey();
		} catch (ClassCastException ex) {
			return false;
		}
		// Compare Big Ints, base64url encoding may have padding!
		// https://tools.ietf.org/html/rfc7518#section-6.2.1.2
		if (! getX().decodeToBigInteger().equals(certECKey.getW().getAffineX())) {
			return false;
		}
		if (! getY().decodeToBigInteger().equals(certECKey.getW().getAffineY())) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * Calls {@link #matches(X509Certificate)} for the first X.509
	 * certificate in the specified chain.
	 *
	 * @param chain The X.509 certificate chain, {@code null} if not
	 *              specified.
	 *
	 * @throws IllegalArgumentException If a certificate chain is specified
	 *                                  and the first certificate in it
	 *                                  doesn't match.
	 */
	private void ensureMatches(final List<X509Certificate> chain) {
		
		if (chain == null)
			return;
		
		if (! matches(chain.get(0)))
			throw new IllegalArgumentException("The public subject key info of the first X.509 certificate in the chain must match the JWK type and public parameters");
	}
	
	
	@Override
	public LinkedHashMap<String,?> getRequiredParams() {

		// Put mandatory params in sorted order
		LinkedHashMap<String,String> requiredParams = new LinkedHashMap<>();
		requiredParams.put("crv", crv.toString());
		requiredParams.put("kty", getKeyType().getValue());
		requiredParams.put("x", x.toString());
		requiredParams.put("y", y.toString());
		return requiredParams;
	}


	@Override
	public boolean isPrivate() {

		return d != null || privateKey != null;
	}


	@Override
	public int size() {

		ECParameterSpec ecParameterSpec = crv.toECParameterSpec();

		if (ecParameterSpec == null) {
			throw new UnsupportedOperationException("Couldn't determine field size for curve " + crv.getName());
		}

		return ecParameterSpec.getCurve().getField().getFieldSize();
	}

	
	/**
	 * Returns a copy of this Elliptic Curve JWK with any private values 
	 * removed.
	 *
	 * @return The copied public Elliptic Curve JWK.
	 */
	@Override
	public ECKey toPublicJWK() {

		return new ECKey(
			getCurve(), getX(), getY(),
			getKeyUse(), getKeyOperations(), getAlgorithm(), getKeyID(),
			getX509CertURL(), getX509CertThumbprint(), getX509CertSHA256Thumbprint(), getX509CertChain(),
			getKeyStore());
	}
	

	@Override
	public JSONObject toJSONObject() {

		JSONObject o = super.toJSONObject();

		// Append EC specific attributes
		o.put("crv", crv.toString());
		o.put("x", x.toString());
		o.put("y", y.toString());

		if (d != null) {
			o.put("d", d.toString());
		}
		
		return o;
	}


	/**
	 * Parses a public / private Elliptic Curve JWK from the specified JSON
	 * object string representation.
	 *
	 * @param s The JSON object string to parse. Must not be {@code null}.
	 *
	 * @return The public / private Elliptic Curve JWK.
	 *
	 * @throws ParseException If the string couldn't be parsed to an
	 *                        Elliptic Curve JWK.
	 */
	public static ECKey parse(final String s)
		throws ParseException {

		return parse(JSONObjectUtils.parse(s));
	}


	/**
	 * Parses a public / private Elliptic Curve JWK from the specified JSON
	 * object representation.
	 *
	 * @param jsonObject The JSON object to parse. Must not be 
	 *                   {@code null}.
	 *
	 * @return The public / private Elliptic Curve JWK.
	 *
	 * @throws ParseException If the JSON object couldn't be parsed to an 
	 *                        Elliptic Curve JWK.
	 */
	public static ECKey parse(final JSONObject jsonObject)
		throws ParseException {

		// Parse the mandatory parameters first
		Curve crv = Curve.parse(JSONObjectUtils.getString(jsonObject, "crv"));
		Base64URL x = new Base64URL(JSONObjectUtils.getString(jsonObject, "x"));
		Base64URL y = new Base64URL(JSONObjectUtils.getString(jsonObject, "y"));

		// Check key type
		KeyType kty = JWKMetadata.parseKeyType(jsonObject);

		if (kty != KeyType.EC) {
			throw new ParseException("The key type \"kty\" must be EC", 0);
		}

		// Get optional private key
		Base64URL d = null;
		if (jsonObject.get("d") != null) {
			d = new Base64URL(JSONObjectUtils.getString(jsonObject, "d"));
		}


		try {
			if (d == null) {
				// Public key
				return new ECKey(crv, x, y,
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
				return new ECKey(crv, x, y, d,
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
	
	
	/**
	 * Parses a public Elliptic Curve JWK from the specified X.509
	 * certificate. Requires BouncyCastle.
	 *
	 * <p><strong>Important:</strong> The X.509 certificate is not
	 * validated!
	 *
	 * <p>Sets the following JWK parameters:
	 *
	 * <ul>
	 *     <li>The curve is obtained from the subject public key info
	 *         algorithm parameters.
	 *     <li>The JWK use inferred by {@link KeyUse#from}.
	 *     <li>The JWK ID from the X.509 serial number (in base 10).
	 *     <li>The JWK X.509 certificate chain (this certificate only).
	 *     <li>The JWK X.509 certificate SHA-256 thumbprint.
	 * </ul>
	 *
	 * @param cert The X.509 certificate. Must not be {@code null}.
	 *
	 * @return The public Elliptic Curve JWK.
	 *
	 * @throws JOSEException If parsing failed.
	 */
	public static ECKey parse(final X509Certificate cert)
		throws JOSEException {
		
		if (! (cert.getPublicKey() instanceof ECPublicKey)) {
			throw new JOSEException("The public key of the X.509 certificate is not EC");
		}
		
		ECPublicKey publicKey = (ECPublicKey) cert.getPublicKey();
		
		try {
			JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder(cert);
			
			String oid = certHolder.getSubjectPublicKeyInfo().getAlgorithm().getParameters().toString();
			
			Curve crv = Curve.forOID(oid);
			
			if (crv == null) {
				throw new JOSEException("Couldn't determine EC JWK curve for OID " + oid);
			}
			
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			
			return new ECKey.Builder(crv, publicKey)
				.keyUse(KeyUse.from(cert))
				.keyID(cert.getSerialNumber().toString(10))
				.x509CertChain(Collections.singletonList(Base64.encode(cert.getEncoded())))
				.x509CertSHA256Thumbprint(Base64URL.encode(sha256.digest(cert.getEncoded())))
				.build();
		} catch (NoSuchAlgorithmException e) {
			throw new JOSEException("Couldn't encode x5t parameter: " + e.getMessage(), e);
		} catch (CertificateEncodingException e) {
			throw new JOSEException("Couldn't encode x5c parameter: " + e.getMessage(), e);
		}
	}
	
	
	/**
	 * Loads a public / private Elliptic Curve JWK from the specified JCA
	 * key store. Requires BouncyCastle.
	 *
	 * <p><strong>Important:</strong> The X.509 certificate is not
	 * validated!
	 *
	 * @param keyStore The key store. Must not be {@code null}.
	 * @param alias    The alias. Must not be {@code null}.
	 * @param pin      The pin to unlock the private key if any, empty or
	 *                 {@code null} if not required.
	 *
	 * @return The public / private Elliptic Curve JWK., {@code null} if no
	 *         key with the specified alias was found.
	 *
	 * @throws KeyStoreException On a key store exception.
	 * @throws JOSEException     If EC key loading failed.
	 */
	public static ECKey load(final KeyStore keyStore,
				 final String alias,
				 final char[] pin)
		throws KeyStoreException, JOSEException {
		
		Certificate cert = keyStore.getCertificate(alias);
		
		if (cert == null || ! (cert instanceof X509Certificate)) {
			return null;
		}
		
		X509Certificate x509Cert = (X509Certificate)cert;
		
		if (! (x509Cert.getPublicKey() instanceof ECPublicKey)) {
			throw new JOSEException("Couldn't load EC JWK: The key algorithm is not EC");
		}
			
		ECKey ecJWK = ECKey.parse(x509Cert);
		
		// Let kid=alias
		ecJWK = new ECKey.Builder(ecJWK).keyID(alias).keyStore(keyStore).build();
		
		// Check for private counterpart
		Key key;
		try {
			key = keyStore.getKey(alias, pin);
		} catch (UnrecoverableKeyException | NoSuchAlgorithmException e) {
			throw new JOSEException("Couldn't retrieve private EC key (bad pin?): " + e.getMessage(), e);
		}
			
		if (key instanceof ECPrivateKey) {
			// Simple file based key store
			return new ECKey.Builder(ecJWK)
				.privateKey((ECPrivateKey)key)
				.build();
		} else if (key instanceof PrivateKey && "EC".equalsIgnoreCase(key.getAlgorithm())) {
			// PKCS#11 store
			return new ECKey.Builder(ecJWK)
				.privateKey((PrivateKey)key)
				.build();
		} else {
			return ecJWK;
		}
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ECKey)) return false;
		if (!super.equals(o)) return false;
		ECKey ecKey = (ECKey) o;
		return Objects.equals(crv, ecKey.crv) &&
				Objects.equals(x, ecKey.x) &&
				Objects.equals(y, ecKey.y) &&
				Objects.equals(d, ecKey.d) &&
				Objects.equals(privateKey, ecKey.privateKey);
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), crv, x, y, d, privateKey);
	}
}
