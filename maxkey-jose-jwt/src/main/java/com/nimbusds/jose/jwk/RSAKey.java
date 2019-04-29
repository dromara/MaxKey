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


import java.io.Serializable;
import java.math.BigInteger;
import java.net.URI;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.text.ParseException;
import java.util.*;

import net.jcip.annotations.Immutable;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jose.util.*;


/**
 * Public and private {@link KeyType#RSA RSA} JSON Web Key (JWK). This class is
 * immutable.
 *
 * <p>Provides RSA JWK import from / export to the following standard Java 
 * interfaces and classes:
 *
 * <ul>
 *     <li>{@link java.security.interfaces.RSAPublicKey}
 *     <li>{@link java.security.interfaces.RSAPrivateKey}
 *         <ul>
 *             <li>{@link java.security.interfaces.RSAPrivateCrtKey}
 *             <li>{@link java.security.interfaces.RSAMultiPrimePrivateCrtKey}
 *         </ul>
 *     <li>{@link java.security.PrivateKey} for an RSA key in a PKCS#11 store
 *     <li>{@link java.security.KeyPair}
 * </ul>
 *
 * <p>Example JSON object representation of a public RSA JWK:
 *
 * <pre>
 * { 
 *   "kty" : "RSA",
 *   "n"   : "0vx7agoebGcQSuuPiLJXZptN9nndrQmbXEps2aiAFbWhM78LhWx
 *            4cbbfAAtVT86zwu1RK7aPFFxuhDR1L6tSoc_BJECPebWKRXjBZCiFV4n3oknjhMs
 *            tn64tZ_2W-5JsGY4Hc5n9yBXArwl93lqt7_RN5w6Cf0h4QyQ5v-65YGjQR0_FDW2
 *            QvzqY368QQMicAtaSqzs8KJZgnYb9c7d0zgdAZHzu6qMQvRL5hajrn1n91CbOpbI
 *            SD08qNLyrdkt-bFTWhAI4vMQFh6WeZu0fM4lFd2NcRwr3XPksINHaQ-G_xBniIqb
 *            w0Ls1jF44-csFCur-kEgU8awapJzKnqDKgw",
 *   "e"   : "AQAB",
 *   "alg" : "RS256",
 *   "kid" : "2011-04-29"
 * }
 * </pre>
 *
 * <p>Example JSON object representation of a public and private RSA JWK (with 
 * both the first and the second private key representations):
 *
 * <pre>
 * { 
 *   "kty" : "RSA",
 *   "n"   : "0vx7agoebGcQSuuPiLJXZptN9nndrQmbXEps2aiAFbWhM78LhWx
 *            4cbbfAAtVT86zwu1RK7aPFFxuhDR1L6tSoc_BJECPebWKRXjBZCiFV4n3oknjhMs
 *            tn64tZ_2W-5JsGY4Hc5n9yBXArwl93lqt7_RN5w6Cf0h4QyQ5v-65YGjQR0_FDW2
 *            QvzqY368QQMicAtaSqzs8KJZgnYb9c7d0zgdAZHzu6qMQvRL5hajrn1n91CbOpbI
 *            SD08qNLyrdkt-bFTWhAI4vMQFh6WeZu0fM4lFd2NcRwr3XPksINHaQ-G_xBniIqb
 *            w0Ls1jF44-csFCur-kEgU8awapJzKnqDKgw",
 *   "e"   : "AQAB",
 *   "d"   : "X4cTteJY_gn4FYPsXB8rdXix5vwsg1FLN5E3EaG6RJoVH-HLLKD9
 *            M7dx5oo7GURknchnrRweUkC7hT5fJLM0WbFAKNLWY2vv7B6NqXSzUvxT0_YSfqij
 *            wp3RTzlBaCxWp4doFk5N2o8Gy_nHNKroADIkJ46pRUohsXywbReAdYaMwFs9tv8d
 *            _cPVY3i07a3t8MN6TNwm0dSawm9v47UiCl3Sk5ZiG7xojPLu4sbg1U2jx4IBTNBz
 *            nbJSzFHK66jT8bgkuqsk0GjskDJk19Z4qwjwbsnn4j2WBii3RL-Us2lGVkY8fkFz
 *            me1z0HbIkfz0Y6mqnOYtqc0X4jfcKoAC8Q",
 *   "p"   : "83i-7IvMGXoMXCskv73TKr8637FiO7Z27zv8oj6pbWUQyLPQBQxtPV
 *            nwD20R-60eTDmD2ujnMt5PoqMrm8RfmNhVWDtjjMmCMjOpSXicFHj7XOuVIYQyqV
 *            WlWEh6dN36GVZYk93N8Bc9vY41xy8B9RzzOGVQzXvNEvn7O0nVbfs",
 *   "q"   : "3dfOR9cuYq-0S-mkFLzgItgMEfFzB2q3hWehMuG0oCuqnb3vobLyum
 *            qjVZQO1dIrdwgTnCdpYzBcOfW5r370AFXjiWft_NGEiovonizhKpo9VVS78TzFgx
 *            kIdrecRezsZ-1kYd_s1qDbxtkDEgfAITAG9LUnADun4vIcb6yelxk",
 *   "dp"  : "G4sPXkc6Ya9y8oJW9_ILj4xuppu0lzi_H7VTkS8xj5SdX3coE0oim
 *            YwxIi2emTAue0UOa5dpgFGyBJ4c8tQ2VF402XRugKDTP8akYhFo5tAA77Qe_Nmtu
 *            YZc3C3m3I24G2GvR5sSDxUyAN2zq8Lfn9EUms6rY3Ob8YeiKkTiBj0",
 *   "dq"  : "s9lAH9fggBsoFR8Oac2R_E2gw282rT2kGOAhvIllETE1efrA6huUU
 *            vMfBcMpn8lqeW6vzznYY5SSQF7pMdC_agI3nG8Ibp1BUb0JUiraRNqUfLhcQb_d9
 *            GF4Dh7e74WbRsobRonujTYN1xCaP6TO61jvWrX-L18txXw494Q_cgk",
 *   "qi"  : "GyM_p6JrXySiz1toFgKbWV-JdI3jQ4ypu9rbMWx3rQJBfmt0FoYzg
 *            UIZEVFEcOqwemRN81zoDAaa-Bk0KWNGDjJHZDdDmFhW3AN7lI-puxk_mHZGJ11rx
 *            yR8O55XLSe3SPmRfKwZI6yU24ZxvQKFYItdldUKGzO6Ia6zTKhAVRU",
 *   "alg" : "RS256",
 *   "kid" : "2011-04-29"
 * }
 * </pre>
 *
 * <p>Use the builder to create a new RSA JWK:
 *
 * <pre>
 * RSAKey key = new RSAKey.Builder(n, e)
 * 	.keyUse(KeyUse.SIGNATURE)
 * 	.keyID("123")
 * 	.build();
 * </pre>
 *
 * <p>See RFC 3447.
 *
 * <p>See http://en.wikipedia.org/wiki/RSA_%28algorithm%29
 *
 * @author Vladimir Dzhuvinov
 * @author Justin Richer
 * @author Cedric Staub
 * @version 2019-04-15
 */
@Immutable
public final class RSAKey extends JWK implements AsymmetricJWK {


	private static final long serialVersionUID = 1L;


	/**
	 * Other Primes Info, represents the private {@code oth} parameter of a
	 * RSA JWK. This class is immutable.
	 */
	@Immutable
	public static class OtherPrimesInfo implements Serializable {


		private static final long serialVersionUID = 1L;


		 /**
	          * The prime factor.
	          */
		private final Base64URL r;

		
		/**
		 * The factor Chinese Remainder Theorem (CRT) exponent.
		 */
    		private final Base64URL d;
    	

		/**
		 * The factor Chinese Remainder Theorem (CRT) coefficient.
		 */
		private final Base64URL t;


		/**
		 * Creates a new JWK Other Primes Info with the specified 
		 * parameters.
		 *
		 * @param r The prime factor. Must not be {@code null}.
		 * @param d The factor Chinese Remainder Theorem (CRT) 
		 *          exponent. Must not be {@code null}.
		 * @param t The factor Chinese Remainder Theorem (CRT) 
		 *          coefficient. Must not be {@code null}.
		 */
		public OtherPrimesInfo(final Base64URL r, final Base64URL d, final Base64URL t) {

			if (r == null) {

				throw new IllegalArgumentException("The prime factor must not be null");
			}

			this.r = r;

			if (d == null) {

				throw new IllegalArgumentException("The factor CRT exponent must not be null");
			}

			this.d = d;

			if (t == null) {

				throw new IllegalArgumentException("The factor CRT coefficient must not be null");
			}
			
			this.t = t;
		}


		/**
		 * Creates a new JWK Other Primes Info from the specified
		 * {@code java.security.spec.RSAOtherPrimeInfo} instance.
		 *
		 * @param oth The RSA Other Primes Info instance. Must not be 
		 *            {@code null}.
		 */
		public OtherPrimesInfo(final RSAOtherPrimeInfo oth) {

			r = Base64URL.encode(oth.getPrime());
			d = Base64URL.encode(oth.getExponent());
			t = Base64URL.encode(oth.getCrtCoefficient());
		}
       
    	
		/**
		 * Gets the prime factor ({@code r}).
		 *
		 * @return The prime factor.
		 */
		public Base64URL getPrimeFactor() {

			return r;
		}


		/**
		 * Gets factor Chinese Remainder Theorem (CRT) exponent
		 * ({@code d}).
		 *
		 * @return The factor Chinese Remainder Theorem (CRT) exponent.
		 */
		public Base64URL getFactorCRTExponent() {

			return d;
		}


		/**
		 * The factor Chinese Remainder Theorem (CRT) coefficient
		 * ({@code t}).
		 *
		 * @return The factor Chinese Remainder Theorem (CRT) 
		 *         coefficient.
		 */
		public Base64URL getFactorCRTCoefficient() {

			return t;
		}


		/**
		 * Converts the specified array of 
		 * {@code java.security.spec.RSAOtherPrimeInfo} instances to a
		 * list of JWK Other Prime Infos.
		 *
		 * @param othArray Array of RSA Other Primes Info instances. 
		 *                 May be be {@code null}.
		 *
		 * @return The corresponding list of JWK Other Prime Infos, or
		 *         empty list of the array was {@code null}.
		 */
		public static List<OtherPrimesInfo> toList(final RSAOtherPrimeInfo[] othArray) {

			List<OtherPrimesInfo> list = new ArrayList<>();

			if (othArray == null) {

				// Return empty list
				return list;
			}

			for (RSAOtherPrimeInfo oth: othArray) {

				list.add(new OtherPrimesInfo(oth));
			}

			return list;
		}
	}


	/**
	 * Builder for constructing RSA JWKs.
	 *
	 * <p>Example usage:
	 *
	 * <pre>
	 * RSAKey key = new RSAKey.Builder(n, e).
	 *              privateExponent(d).
	 *              algorithm(JWSAlgorithm.RS512).
	 *              keyID("456").
	 *              build();
	 * </pre>
	 */
	public static class Builder {


		// Public RSA params

		/**
		 * The modulus value for the RSA key.
		 */
		private final Base64URL n;


		/**
		 * The public exponent of the RSA key.
		 */
		private final Base64URL e;


		// Private RSA params, 1st representation	

		/**
		 * The private exponent of the RSA key.
		 */
		private Base64URL d;

		
		// Private RSA params, 2nd representation

		/**
		 * The first prime factor of the private RSA key.
		 */
		private Base64URL p;

		
		/**
		 * The second prime factor of the private RSA key.
		 */
		private Base64URL q;

		
		/**
		 * The first factor Chinese Remainder Theorem exponent of the 
		 * private RSA key.
		 */
		private Base64URL dp;

		
		/**
		 * The second factor Chinese Remainder Theorem exponent of the
		 * private RSA key.
		 */
		private Base64URL dq;

		
		/**
		 * The first Chinese Remainder Theorem coefficient of the private RSA
		 * key.
		 */
		private Base64URL qi;

		
		/**
		 * The other primes information of the private RSA key, should
		 * they exist. When only two primes have been used (the normal
		 * case), this parameter MUST be omitted. When three or more 
		 * primes have been used, the number of array elements MUST be 
		 * the number of primes used minus two.
		 */
		private List<OtherPrimesInfo> oth;
		
		
		// Private RSA key, as PKCS#11 handle
		
		/**
		 * The private RSA key, as PKCS#11 handle.
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
		 * Creates a new RSA JWK builder.
		 *
		 * @param n The the modulus value for the public RSA key. It is 
		 *          represented as the Base64URL encoding of value's 
		 *          big endian representation. Must not be 
		 *          {@code null}.
		 * @param e The exponent value for the public RSA key. It is 
		 *          represented as the Base64URL encoding of value's 
		 *          big endian representation. Must not be 
		 *          {@code null}.
		 */
		public Builder(final Base64URL n, final Base64URL e) {

			// Ensure the public params are defined

			if (n == null) {
				throw new IllegalArgumentException("The modulus value must not be null");
			}

			this.n = n;


			if (e == null) {
				throw new IllegalArgumentException("The public exponent value must not be null");
			}

			this.e = e;
		}


		/**
		 * Creates a new RSA JWK builder.
		 * 
		 * @param pub The public RSA key to represent. Must not be 
		 *            {@code null}.
		 */
		public Builder(final RSAPublicKey pub) {

			n = Base64URL.encode(pub.getModulus());
			e = Base64URL.encode(pub.getPublicExponent());
		}
		
		
		/**
		 * Creates a new RSA JWK builder.
		 *
		 * @param rsaJWK The RSA JWK to start with. Must not be
		 *               {@code null}.
		 */
		public Builder(final RSAKey rsaJWK) {
			
			n = rsaJWK.n;
			e = rsaJWK.e;
			d = rsaJWK.d;
			p = rsaJWK.p;
			q = rsaJWK.q;
			dp = rsaJWK.dp;
			dq = rsaJWK.dq;
			qi = rsaJWK.qi;
			oth = rsaJWK.oth;
			priv = rsaJWK.privateKey;
			use = rsaJWK.getKeyUse();
			ops = rsaJWK.getKeyOperations();
			alg = rsaJWK.getAlgorithm();
			kid = rsaJWK.getKeyID();
			x5u = rsaJWK.getX509CertURL();
			x5t = rsaJWK.getX509CertThumbprint();
			x5t256 = rsaJWK.getX509CertSHA256Thumbprint();
			x5c = rsaJWK.getX509CertChain();
			ks = rsaJWK.getKeyStore();
		}


		/**
		 * Sets the private exponent ({@code d}) of the RSA key.
		 *
		 * @param d The private RSA key exponent. It is represented as 
		 *          the Base64URL encoding of the value's big endian 
		 *          representation. {@code null} if not specified (for 
		 *          a public key or a private key using the second 
		 *          representation only).
		 *
		 * @return This builder.
		 */
		public Builder privateExponent(final Base64URL d) {

			this.d = d;
			return this;
		}


		/**
		 * Sets the private RSA key, using the first representation.
		 * 
		 * @param priv The private RSA key, used to obtain the private
		 *             exponent ({@code d}). Must not be {@code null}.
		 *
		 * @return This builder.
		 */
		public Builder privateKey(final RSAPrivateKey priv) {

			if (priv instanceof RSAPrivateCrtKey) {
				return this.privateKey((RSAPrivateCrtKey) priv);
			} else if (priv instanceof RSAMultiPrimePrivateCrtKey) {
				return this.privateKey((RSAMultiPrimePrivateCrtKey) priv);
			} else {
				this.d = Base64URL.encode(priv.getPrivateExponent());
				return this;
			}
		}
		
		
		/**
		 * Sets the private RSA key, typically for a key located in a
		 * PKCS#11 store that doesn't expose the private key parameters
		 * (such as a smart card or HSM).
		 *
		 * @param priv The private RSA key reference. Its algorithm
		 *             must be "RSA". Must not be {@code null}.
		 *
		 * @return This builder.
		 */
		public Builder privateKey(final PrivateKey priv) {
			if (priv instanceof RSAPrivateKey) {
				return privateKey((RSAPrivateKey) priv);
			}

			if (! "RSA".equalsIgnoreCase(priv.getAlgorithm())) {
				throw new IllegalArgumentException("The private key algorithm must be RSA");
			}
			
			this.priv = priv;
			return this;
		}


		/**
		 * Sets the first prime factor ({@code p}) of the private RSA
		 * key. 
		 *
		 * @param p The RSA first prime factor. It is represented as 
		 *          the Base64URL encoding of the value's big endian 
		 *          representation. {@code null} if not specified (for 
		 *          a public key or a private key using the first 
		 *          representation only).
		 *
		 * @return This builder.
		 */
		public Builder firstPrimeFactor(final Base64URL p) {

			this.p = p;
			return this;
		}


		/**
		 * Sets the second prime factor ({@code q}) of the private RSA 
		 * key.
		 *
		 * @param q The RSA second prime factor. It is represented as 
		 *          the Base64URL encoding of the value's big endian 
		 *          representation. {@code null} if not specified (for 
		 *          a public key or a private key using the first 
		 *          representation only).
		 *
		 * @return This builder.
		 */
		public Builder secondPrimeFactor(final Base64URL q) {

			this.q = q;
			return this;
		}


		/**
		 * Sets the first factor Chinese Remainder Theorem (CRT) 
		 * exponent ({@code dp}) of the private RSA key.
		 *
		 * @param dp The RSA first factor CRT exponent. It is 
		 *           represented as the Base64URL encoding of the 
		 *           value's big endian representation. {@code null} 
		 *           if not specified (for a public key or a private
		 *           key using the first representation only).
		 *
		 * @return This builder.
		 */
		public Builder firstFactorCRTExponent(final Base64URL dp) {

			this.dp = dp;
			return this;
		}


		/**
		 * Sets the second factor Chinese Remainder Theorem (CRT) 
		 * exponent ({@code dq}) of the private RSA key.
		 *
		 * @param dq The RSA second factor CRT exponent. It is 
		 *           represented as the Base64URL encoding of the 
		 *           value's big endian representation. {@code null} if 
		 *           not specified (for a public key or a private key 
		 *           using the first representation only).
		 *
		 * @return This builder.
		 */
		public Builder secondFactorCRTExponent(final Base64URL dq) {

			this.dq = dq;
			return this;
		}


		/**
		 * Sets the first Chinese Remainder Theorem (CRT) coefficient
		 * ({@code qi}) of the private RSA key.
		 *
		 * @param qi The RSA first CRT coefficient. It is represented 
		 *           as the Base64URL encoding of the value's big 
		 *           endian representation. {@code null} if not 
		 *           specified (for a public key or a private key using 
		 *           the first representation only).
		 *
		 * @return This builder.
		 */
		public Builder firstCRTCoefficient(final Base64URL qi) {

			this.qi = qi;
			return this;
		}


		/**
		 * Sets the other primes information ({@code oth}) for the 
		 * private RSA key, should they exist.
		 *
		 * @param oth The RSA other primes information, {@code null} or 
		 *            empty list if not specified.
		 *
		 * @return This builder.
		 */
		public Builder otherPrimes(final List<OtherPrimesInfo> oth) {

			this.oth = oth;
			return this;
		}


		/**
		 * Sets the private RSA key, using the second representation 
		 * (see RFC 3447, section 3.2).
		 * 
		 * @param priv The private RSA key, used to obtain the private
		 *             exponent ({@code d}), the first prime factor
		 *             ({@code p}), the second prime factor 
		 *             ({@code q}), the first factor CRT exponent 
		 *             ({@code dp}), the second factor CRT exponent
		 *             ({@code dq}) and the first CRT coefficient 
		 *             ({@code qi}). Must not be {@code null}.
		 *
		 * @return This builder.
		 */
		public Builder privateKey(final RSAPrivateCrtKey priv) {

			d = Base64URL.encode(priv.getPrivateExponent());
			p = Base64URL.encode(priv.getPrimeP());
			q = Base64URL.encode(priv.getPrimeQ());
			dp = Base64URL.encode(priv.getPrimeExponentP());
			dq = Base64URL.encode(priv.getPrimeExponentQ());
			qi = Base64URL.encode(priv.getCrtCoefficient());

			return this;
		}


		/**
		 * Sets the private RSA key, using the second representation, 
		 * with optional other primes info (see RFC 3447, section 3.2).
		 * 
		 * @param priv The private RSA key, used to obtain the private
		 *             exponent ({@code d}), the first prime factor
		 *             ({@code p}), the second prime factor 
		 *             ({@code q}), the first factor CRT exponent 
		 *             ({@code dp}), the second factor CRT exponent
		 *             ({@code dq}), the first CRT coefficient 
		 *             ({@code qi}) and the other primes info
		 *             ({@code oth}). Must not be {@code null}.
		 *
		 * @return This builder.
		 */
		public Builder privateKey(final RSAMultiPrimePrivateCrtKey priv) {
			
			d = Base64URL.encode(priv.getPrivateExponent());
			p = Base64URL.encode(priv.getPrimeP());
			q = Base64URL.encode(priv.getPrimeQ());
			dp = Base64URL.encode(priv.getPrimeExponentP());
			dq = Base64URL.encode(priv.getPrimeExponentQ());
			qi = Base64URL.encode(priv.getCrtCoefficient());
			oth = OtherPrimesInfo.toList(priv.getOtherPrimeInfo());

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
		 * Sets the operations ({@code key_ops}) of the JWK (for a
		 * non-public key).
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
			requiredParams.put("e", e.toString());
			requiredParams.put("kty", KeyType.RSA.getValue());
			requiredParams.put("n", n.toString());
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
		 * Builds a new RSA JWK.
		 *
		 * @return The RSA JWK.
		 *
		 * @throws IllegalStateException If the JWK parameters were
		 *                               inconsistently specified.
		 */
		public RSAKey build() {

			try {
				// The full constructor
				return new RSAKey(n, e, d, p, q, dp, dq, qi, oth,
					          priv,
					          use, ops, alg, kid, x5u, x5t, x5t256, x5c,
					          ks);

			} catch (IllegalArgumentException e) {

				throw new IllegalStateException(e.getMessage(), e);
			}
		}
	}


	// Public RSA params

	/**
	 * The modulus value of the RSA key.
	 */
	private final Base64URL n;


	/**
	 * The public exponent of the RSA key.
	 */
	private final Base64URL e;


	// Private RSA params, 1st representation	

	/**
	 * The private exponent of the RSA key.
	 */
	private final Base64URL d;

	
	// Private RSA params, 2nd representation

	/**
	 * The first prime factor of the private RSA key.
	 */
	private final Base64URL p;

	
	/**
	 * The second prime factor of the private RSA key.
	 */
	private final Base64URL q;

	
	/**
	 * The first factor Chinese Remainder Theorem exponent of the private 
	 * RSA key.
	 */
	private final Base64URL dp;

	
	/**
	 * The second factor Chinese Remainder Theorem exponent of the private
	 * RSA key.
	 */
	private final Base64URL dq;

	
	/**
	 * The first Chinese Remainder Theorem coefficient of the private RSA
	 * key.
	 */
	private final Base64URL qi;

	
	/**
	 * The other primes information of the private RSA key, should they
	 * exist. When only two primes have been used (the normal case), this 
	 * parameter MUST be omitted. When three or more primes have been used,
	 * the number of array elements MUST be the number of primes used minus
	 * two.
	 */
	private final List<OtherPrimesInfo> oth;
	
	
	// Private RSA PKCS#11 key handle
	
	/**
	 * Private PKCS#11 key handle.
	 */
	private final PrivateKey privateKey;


	/**
	 * Creates a new public RSA JSON Web Key (JWK) with the specified 
	 * parameters.
	 *
	 * @param n      The the modulus value for the public RSA key. It is
	 *               represented as the Base64URL encoding of value's big
	 *               endian representation. Must not be {@code null}.
	 * @param e      The exponent value for the public RSA key. It is
	 *               represented as the Base64URL encoding of value's big
	 *               endian representation. Must not be {@code null}.
	 * @param use    The key use, {@code null} if not specified or if the
	 *               key is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null}
	 *               if not specified.
	 * @param kid    The key ID. {@code null} if not specified.
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
	public RSAKey(final Base64URL n, final Base64URL e,
		      final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		      final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		      final KeyStore ks) {

		// Call the full constructor, all private key parameters are null
		this(n, e, null, null, null, null, null, null, null, null, use, ops, alg, kid,
		     x5u, x5t, x5t256, x5c,
		     ks);
	}


	/**
	 * Creates a new public / private RSA JSON Web Key (JWK) with the 
	 * specified parameters. The private RSA key is specified by its first
	 * representation (see RFC 3447, section 3.2).
	 * 
	 * @param n      The the modulus value for the public RSA key. It is
	 *               represented as the Base64URL encoding of value's big
	 *               endian representation. Must not be {@code null}.
	 * @param e      The exponent value for the public RSA key. It is
	 *               represented as the Base64URL encoding of value's big
	 *               endian representation. Must not be {@code null}.
	 * @param d      The private exponent. It is represented as the
	 *               Base64URL encoding of the value's big endian
	 *               representation. Must not be {@code null}.
	 * @param use    The key use, {@code null} if not specified or if the
	 *               key is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null}
	 *               if not specified.
	 * @param kid    The key ID. {@code null} if not specified.
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
	public RSAKey(final Base64URL n, final Base64URL e, final Base64URL d,
		      final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		      final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		      final KeyStore ks) {
	    
		// Call the full constructor, the second private representation 
		// parameters are all null
		this(n, e, d, null, null, null, null, null, null, null, use, ops, alg, kid,
		     x5u, x5t, x5t256, x5c, ks);

		if (d == null) {
			throw new IllegalArgumentException("The private exponent must not be null");
		}
	}


	/**
	 * Creates a new public / private RSA JSON Web Key (JWK) with the 
	 * specified parameters. The private RSA key is specified by its
	 * second representation (see RFC 3447, section 3.2).
	 * 
	 * @param n      The the modulus value for the public RSA key. It is
	 *               represented as the Base64URL encoding of value's big
	 *               endian representation. Must not be {@code null}.
	 * @param e      The exponent value for the public RSA key. It is
	 *               represented as the Base64URL encoding of value's big
	 *               endian representation. Must not be {@code null}.
	 * @param p      The first prime factor. It is represented as the
	 *               Base64URL encoding of the value's big endian
	 *               representation. Must not be {@code null}.
	 * @param q      The second prime factor. It is represented as the
	 *               Base64URL encoding of the value's big endian
	 *               representation. Must not be {@code null}.
	 * @param dp     The first factor Chinese Remainder Theorem exponent.
	 *               It is represented as the Base64URL encoding of the
	 *               value's big endian representation. Must not be
	 *               {@code null}.
	 * @param dq     The second factor Chinese Remainder Theorem exponent.
	 *               It is represented as the Base64URL encoding of the
	 *               value's big endian representation. Must not be
	 *               {@code null}.
	 * @param qi     The first Chinese Remainder Theorem coefficient. It is
	 *               represented as the Base64URL encoding of the value's
	 *               big endian representation. Must not be {@code null}.
	 * @param oth    The other primes information, should they exist,
	 *               {@code null} or an empty list if not specified.
	 * @param use    The key use, {@code null} if not specified or if the
	 *               key is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null}
	 *               if not specified.
	 * @param kid    The key ID. {@code null} if not specified.
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
	public RSAKey(final Base64URL n, final Base64URL e, 
		      final Base64URL p, final Base64URL q, 
		      final Base64URL dp, final Base64URL dq, final Base64URL qi, 
		      final List<OtherPrimesInfo> oth,
		      final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		      final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		      final KeyStore ks) {
	    
		// Call the full constructor, the first private representation 
		// d param is null
		this(n, e, null, p, q, dp, dq, qi, oth, null, use, ops, alg, kid,
		     x5u, x5t, x5t256, x5c,
		     ks);

		if (p == null) {
			throw new IllegalArgumentException("The first prime factor must not be null");
		}

		if (q == null) {
			throw new IllegalArgumentException("The second prime factor must not be null");
		}

		if (dp == null) {
			throw new IllegalArgumentException("The first factor CRT exponent must not be null");
		}

		if (dq == null) {
			throw new IllegalArgumentException("The second factor CRT exponent must not be null");
		}

		if (qi == null) {
			throw new IllegalArgumentException("The first CRT coefficient must not be null");
		}
	}


	/**
	 * Creates a new public / private RSA JSON Web Key (JWK) with the 
	 * specified parameters. The private RSA key is specified by both its
	 * first and second representations (see RFC 3447, section 3.2).
	 *
	 * <p>A valid first private RSA key representation must specify the
	 * {@code d} parameter.
	 *
	 * <p>A valid second private RSA key representation must specify all 
	 * required Chinese Remainder Theorem (CRT) parameters - {@code p},
	 * {@code q}, {@code dp}, {@code dq} and {@code qi}, else an
	 * {@link java.lang.IllegalArgumentException} will be thrown.
	 * 
	 * @param n      The the modulus value for the public RSA key. It is
	 *               represented as the Base64URL encoding of value's big
	 *               endian representation. Must not be {@code null}.
	 * @param e      The exponent value for the public RSA key. It is
	 *               represented as the Base64URL encoding of value's big
	 *               endian representation. Must not be {@code null}.
	 * @param d      The private exponent. It is represented as the Base64URL
	 *               encoding of the value's big endian representation. May
	 *               be {@code null}.
	 * @param p      The first prime factor. It is represented as the
	 *               Base64URL encoding of the value's big endian
	 *               representation. May be {@code null}.
	 * @param q      The second prime factor. It is represented as the
	 *               Base64URL encoding of the value's big endian
	 *               representation. May be {@code null}.
	 * @param dp     The first factor Chinese Remainder Theorem exponent. It
	 *               is represented as the Base64URL encoding of the value's
	 *               big endian representation. May be {@code null}.
	 * @param dq     The second factor Chinese Remainder Theorem exponent. It
	 *               is represented as the Base64URL encoding of the value's
	 *               big endian representation. May be {@code null}.
	 * @param qi     The first Chinese Remainder Theorem coefficient. It is
	 *               represented as the Base64URL encoding of the value's big
	 *               endian representation. May be {@code null}.
	 * @param oth    The other primes information, should they exist,
	 *               {@code null} or an empty list if not specified.
	 * @param use    The key use, {@code null} if not specified or if the key
	 *               is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null} if
	 *               not specified.
	 * @param kid    The key ID. {@code null} if not specified.
	 * @param x5u    The X.509 certificate URL, {@code null} if not specified.
	 * @param x5t    The X.509 certificate SHA-1 thumbprint, {@code null}
	 *               if not specified.
	 * @param x5t256 The X.509 certificate SHA-256 thumbprint, {@code null}
	 *               if not specified.
	 * @param x5c    The X.509 certificate chain, {@code null} if not
	 *               specified.
	 */
	@Deprecated
	public RSAKey(final Base64URL n, final Base64URL e,
		      final Base64URL d, 
		      final Base64URL p, final Base64URL q, 
		      final Base64URL dp, final Base64URL dq, final Base64URL qi, 
		      final List<OtherPrimesInfo> oth,
		      final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		      final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c) {
	    
		this(n, e, d, p, q, dp, dq, qi, oth, null, use, ops, alg, kid, x5u, x5t, x5t256, x5c, null);
	}


	/**
	 * Creates a new public / private RSA JSON Web Key (JWK) with the
	 * specified parameters. The private RSA key can be specified by its
	 * first representation, its second representation (see RFC 3447,
	 * section 3.2), or by a PKCS#11 handle as {@link PrivateKey}.
	 *
	 * <p>A valid first private RSA key representation must specify the
	 * {@code d} parameter.
	 *
	 * <p>A valid second private RSA key representation must specify all
	 * required Chinese Remainder Theorem (CRT) parameters - {@code p},
	 * {@code q}, {@code dp}, {@code dq} and {@code qi}, else an
	 * {@link java.lang.IllegalArgumentException} will be thrown.
	 *
	 * @param n      The the modulus value for the public RSA key. It is
	 *               represented as the Base64URL encoding of value's big
	 *               endian representation. Must not be {@code null}.
	 * @param e      The exponent value for the public RSA key. It is
	 *               represented as the Base64URL encoding of value's big
	 *               endian representation. Must not be {@code null}.
	 * @param d      The private exponent. It is represented as the Base64URL
	 *               encoding of the value's big endian representation. May
	 *               be {@code null}.
	 * @param p      The first prime factor. It is represented as the
	 *               Base64URL encoding of the value's big endian
	 *               representation. May be {@code null}.
	 * @param q      The second prime factor. It is represented as the
	 *               Base64URL encoding of the value's big endian
	 *               representation. May be {@code null}.
	 * @param dp     The first factor Chinese Remainder Theorem exponent. It
	 *               is represented as the Base64URL encoding of the value's
	 *               big endian representation. May be {@code null}.
	 * @param dq     The second factor Chinese Remainder Theorem exponent. It
	 *               is represented as the Base64URL encoding of the value's
	 *               big endian representation. May be {@code null}.
	 * @param qi     The first Chinese Remainder Theorem coefficient. It is
	 *               represented as the Base64URL encoding of the value's big
	 *               endian representation. May be {@code null}.
	 * @param oth    The other primes information, should they exist,
	 *               {@code null} or an empty list if not specified.
	 * @param use    The key use, {@code null} if not specified or if the key
	 *               is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null} if
	 *               not specified.
	 * @param kid    The key ID. {@code null} if not specified.
	 * @param x5u    The X.509 certificate URL, {@code null} if not specified.
	 * @param x5t    The X.509 certificate SHA-1 thumbprint, {@code null}
	 *               if not specified.
	 * @param x5t256 The X.509 certificate SHA-256 thumbprint, {@code null}
	 *               if not specified.
	 * @param x5c    The X.509 certificate chain, {@code null} if not
	 *               specified.
	 * @param ks     Reference to the underlying key store, {@code null} if
	 *               not specified.
	 */
	public RSAKey(final Base64URL n, final Base64URL e,
		      final Base64URL d,
		      final Base64URL p, final Base64URL q,
		      final Base64URL dp, final Base64URL dq, final Base64URL qi,
		      final List<OtherPrimesInfo> oth,
		      final PrivateKey prv,
		      final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		      final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		      final KeyStore ks) {
	    
		super(KeyType.RSA, use, ops, alg, kid, x5u, x5t, x5t256, x5c, ks);


		// Ensure the public params are defined

		if (n == null) {
			throw new IllegalArgumentException("The modulus value must not be null");
		}
		this.n = n;


		if (e == null) {
			throw new IllegalArgumentException("The public exponent value must not be null");
		}
		this.e = e;

		if (getParsedX509CertChain() != null) {
			if (! matches(getParsedX509CertChain().get(0)))
				throw new IllegalArgumentException("The public subject key info of the first X.509 certificate in the chain must match the JWK type and public parameters");
		}

		// Private params, 1st representation

		this.d = d;


		// Private params, 2nd representation, check for consistency

		if (p != null && q != null && dp != null && dq != null && qi != null) {

			// CRT params fully specified
			this.p = p;
			this.q = q;
			this.dp = dp;
			this.dq = dq;
			this.qi = qi;

			// Other RSA primes info optional, default to empty list
			if (oth != null) {
				this.oth = Collections.unmodifiableList(oth);
			} else {
				this.oth = Collections.emptyList();
			}

		} else if (p == null && q == null && dp == null && dq == null && qi == null && oth == null) {

			// No CRT params
			this.p = null;
			this.q = null;
			this.dp = null;
			this.dq = null;
			this.qi = null;

			this.oth = Collections.emptyList();

		} else if (p != null || q != null || dp != null || dq != null || qi != null) {

			if (p == null) {
				throw new IllegalArgumentException("Incomplete second private (CRT) representation: The first prime factor must not be null");
			} else if (q == null) {
				throw new IllegalArgumentException("Incomplete second private (CRT) representation: The second prime factor must not be null");
			} else if (dp == null) {
				throw new IllegalArgumentException("Incomplete second private (CRT) representation: The first factor CRT exponent must not be null");
			} else if (dq == null) {
				throw new IllegalArgumentException("Incomplete second private (CRT) representation: The second factor CRT exponent must not be null");
			} else {
				throw new IllegalArgumentException("Incomplete second private (CRT) representation: The first CRT coefficient must not be null");
			}
		} else {
			// No CRT params
			this.p = null;
			this.q = null;
			this.dp = null;
			this.dq = null;
			this.qi = null;
			this.oth = Collections.emptyList();
		}
		
		this.privateKey = prv; // PKCS#11 handle
	}


	/**
	 * Creates a new public RSA JSON Web Key (JWK) with the specified
	 * parameters.
	 * 
	 * @param pub    The public RSA key to represent. Must not be
	 *               {@code null}.
	 * @param use    The key use, {@code null} if not specified or if the
	 *               key is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null}
	 *               if not specified.
	 * @param kid    The key ID. {@code null} if not specified.
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
	public RSAKey(final RSAPublicKey pub,
		      final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		      final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		      final KeyStore ks) {

		this(Base64URL.encode(pub.getModulus()),
			Base64URL.encode(pub.getPublicExponent()),
			use, ops, alg, kid,
			x5u, x5t, x5t256, x5c,
			ks);
	}


	/**
	 * Creates a new public / private RSA JSON Web Key (JWK) with the 
	 * specified parameters. The private RSA key is specified by its first
	 * representation (see RFC 3447, section 3.2).
	 * 
	 * @param pub    The public RSA key to represent. Must not be
	 *               {@code null}.
	 * @param priv   The private RSA key to represent. Must not be
	 *               {@code null}.
	 * @param use    The key use, {@code null} if not specified or if the
	 *               key is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null}
	 *               if not specified.
	 * @param kid    The key ID. {@code null} if not specified.
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
	public RSAKey(final RSAPublicKey pub, final RSAPrivateKey priv,
		      final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		      final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		      final KeyStore ks) {
		
		this(Base64URL.encode(pub.getModulus()), 
		     Base64URL.encode(pub.getPublicExponent()), 
		     Base64URL.encode(priv.getPrivateExponent()),
		     use, ops, alg, kid,
		     x5u, x5t, x5t256, x5c,
		     ks);
	}


	/**
	 * Creates a new public / private RSA JSON Web Key (JWK) with the 
	 * specified parameters. The private RSA key is specified by its second
	 * representation (see RFC 3447, section 3.2).
	 * 
	 * @param pub    The public RSA key to represent. Must not be
	 *               {@code null}.
	 * @param priv   The private RSA key to represent. Must not be
	 *               {@code null}.
	 * @param use    The key use, {@code null} if not specified or if the
	 *               key is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null}
	 *               if not specified.
	 * @param kid    The key ID. {@code null} if not specified.
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
	public RSAKey(final RSAPublicKey pub, final RSAPrivateCrtKey priv,
		      final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		      final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		      final KeyStore ks) {
		
		this(Base64URL.encode(pub.getModulus()), 
		     Base64URL.encode(pub.getPublicExponent()), 
		     Base64URL.encode(priv.getPrivateExponent()),
		     Base64URL.encode(priv.getPrimeP()),
		     Base64URL.encode(priv.getPrimeQ()),
		     Base64URL.encode(priv.getPrimeExponentP()),
		     Base64URL.encode(priv.getPrimeExponentQ()),
		     Base64URL.encode(priv.getCrtCoefficient()),
		     null,
		     null,
		     use, ops, alg, kid,
		     x5u, x5t, x5t256, x5c,
		     ks);
	}


	/**
	 * Creates a new public / private RSA JSON Web Key (JWK) with the 
	 * specified parameters. The private RSA key is specified by its second
	 * representation, with optional other primes info (see RFC 3447, 
	 * section 3.2).
	 * 
	 * @param pub    The public RSA key to represent. Must not be
	 *               {@code null}.
	 * @param priv   The private RSA key to represent. Must not be
	 *               {@code null}.
	 * @param use    The key use, {@code null} if not specified or if the
	 *               key is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null}
	 *               if not specified.
	 * @param kid    The key ID. {@code null} if not specified.
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
	public RSAKey(final RSAPublicKey pub, final RSAMultiPrimePrivateCrtKey priv,
		      final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		      final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		      final KeyStore ks) {
		
		this(Base64URL.encode(pub.getModulus()), 
		     Base64URL.encode(pub.getPublicExponent()), 
		     Base64URL.encode(priv.getPrivateExponent()),
		     Base64URL.encode(priv.getPrimeP()),
		     Base64URL.encode(priv.getPrimeQ()),
		     Base64URL.encode(priv.getPrimeExponentP()),
		     Base64URL.encode(priv.getPrimeExponentQ()),
		     Base64URL.encode(priv.getCrtCoefficient()),
		     OtherPrimesInfo.toList(priv.getOtherPrimeInfo()),
		     null,
		     use, ops, alg, kid,
		     x5u, x5t, x5t256, x5c,
		     ks);
	}


	/**
	 * Creates a new public / private RSA JSON Web Key (JWK) with the
	 * specified parameters. The private RSA key is specified by a PKCS#11
	 * handle.
	 *
	 * @param pub    The public RSA key to represent. Must not be
	 *               {@code null}.
	 * @param priv   The private RSA key as PKCS#11 handle, {@code null} if
	 *               not specified.
	 * @param use    The key use, {@code null} if not specified or if the
	 *               key is intended for signing as well as encryption.
	 * @param ops    The key operations, {@code null} if not specified.
	 * @param alg    The intended JOSE algorithm for the key, {@code null}
	 *               if not specified.
	 * @param kid    The key ID. {@code null} if not specified.
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
	public RSAKey(final RSAPublicKey pub, final PrivateKey priv,
		      final KeyUse use, final Set<KeyOperation> ops, final Algorithm alg, final String kid,
		      final URI x5u, final Base64URL x5t, final Base64URL x5t256, final List<Base64> x5c,
		      final KeyStore ks) {
		
		this(Base64URL.encode(pub.getModulus()),
			Base64URL.encode(pub.getPublicExponent()),
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			priv,
			use, ops, alg, kid,
			x5u, x5t, x5t256, x5c,
			ks);
	}


	/**
	 * Gets the modulus value ({@code n}) of the RSA key.
	 *
	 * @return The RSA key modulus. It is represented as the Base64URL 
	 *         encoding of the value's big endian representation.
	 */
	public Base64URL getModulus() {

		return n;
	}


	/**
	 * Gets the public exponent ({@code e}) of the RSA key.
	 *
	 * @return The public RSA key exponent. It is represented as the 
	 *         Base64URL encoding of the value's big endian representation.
	 */
	public Base64URL getPublicExponent() {

		return e;
	}


	/**
	 * Gets the private exponent ({@code d}) of the RSA key.
	 *
	 * @return The private RSA key exponent. It is represented as the 
	 *         Base64URL encoding of the value's big endian representation. 
	 *         {@code null} if not specified (for a public key or a private
	 *         key using the second representation only).
	 */
	public Base64URL getPrivateExponent() {

		return d;
	}


	/**
	 * Gets the first prime factor ({@code p}) of the private RSA key. 
	 *
	 * @return The RSA first prime factor. It is represented as the 
	 *         Base64URL encoding of the value's big endian representation. 
	 *         {@code null} if not specified (for a public key or a private
	 *         key using the first representation only).
	 */
	public Base64URL getFirstPrimeFactor() {

		return p;
	}


	/**
	 * Gets the second prime factor ({@code q}) of the private RSA key.
	 *
	 * @return The RSA second prime factor. It is represented as the 
	 *         Base64URL encoding of the value's big endian representation. 
	 *         {@code null} if not specified (for a public key or a private
	 *         key using the first representation only).
	 */
	public Base64URL getSecondPrimeFactor() {

		return q;
	}


	/**
	 * Gets the first factor Chinese Remainder Theorem (CRT) exponent
	 * ({@code dp}) of the private RSA key.
	 *
	 * @return The RSA first factor CRT exponent. It is represented as the 
	 *         Base64URL encoding of the value's big endian representation. 
	 *         {@code null} if not specified (for a public key or a private
	 *         key using the first representation only).
	 */
	public Base64URL getFirstFactorCRTExponent() {

		return dp;
	}


	/**
	 * Gets the second factor Chinese Remainder Theorem (CRT) exponent 
	 * ({@code dq}) of the private RSA key.
	 *
	 * @return The RSA second factor CRT exponent. It is represented as the 
	 *         Base64URL encoding of the value's big endian representation. 
	 *         {@code null} if not specified (for a public key or a private
	 *         key using the first representation only).
	 */
	public Base64URL getSecondFactorCRTExponent() {

		return dq;
	}


	/**
	 * Gets the first Chinese Remainder Theorem (CRT) coefficient
	 * ({@code qi})} of the private RSA key.
	 *
	 * @return The RSA first CRT coefficient. It is represented as the 
	 *         Base64URL encoding of the value's big endian representation. 
	 *         {@code null} if not specified (for a public key or a private
	 *         key using the first representation only).
	 */
	public Base64URL getFirstCRTCoefficient() {

		return qi;
	}


	/**
	 * Gets the other primes information ({@code oth}) for the private RSA
	 * key, should they exist.
	 *
	 * @return The RSA other primes information, {@code null} or empty list
	 *         if not specified.
	 */
	public List<OtherPrimesInfo> getOtherPrimes() {

		return oth;
	}

	
	/**
	 * Returns a standard {@code java.security.interfaces.RSAPublicKey} 
	 * representation of this RSA JWK.
	 * 
	 * @return The public RSA key.
	 * 
	 * @throws JOSEException If RSA is not supported by the underlying Java
	 *                       Cryptography (JCA) provider or if the JWK
	 *                       parameters are invalid for a public RSA key.
	 */
	public RSAPublicKey toRSAPublicKey() 
		throws JOSEException {

		BigInteger modulus = n.decodeToBigInteger();
		BigInteger exponent = e.decodeToBigInteger();
				
		RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);

		try {
			KeyFactory factory = KeyFactory.getInstance("RSA");

			return (RSAPublicKey) factory.generatePublic(spec);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

			throw new JOSEException(e.getMessage(), e);
		}
	}
	

	/**
	 * Returns a standard {@code java.security.interfaces.RSAPrivateKey} 
	 * representation of this RSA JWK.
	 * 
	 * @return The private RSA key, {@code null} if not specified by this
	 *         JWK.
	 * 
	 * @throws JOSEException If RSA is not supported by the underlying Java
	 *                       Cryptography (JCA) provider or if the JWK
	 *                       parameters are invalid for a private RSA key.
	 */
	public RSAPrivateKey toRSAPrivateKey() 
		throws JOSEException {
		
		if (d == null) {
			// no private key
			return null;
		}
		
		BigInteger modulus = n.decodeToBigInteger();
		BigInteger privateExponent = d.decodeToBigInteger();
		
		RSAPrivateKeySpec spec;

		if (p == null) {
			// Use 1st representation
			spec = new RSAPrivateKeySpec(modulus, privateExponent);

		} else {
			// Use 2nd (CRT) representation
			BigInteger publicExponent = e.decodeToBigInteger();
			BigInteger primeP = p.decodeToBigInteger();
			BigInteger primeQ = q.decodeToBigInteger();
			BigInteger primeExponentP = dp.decodeToBigInteger();
			BigInteger primeExponentQ = dq.decodeToBigInteger();
			BigInteger crtCoefficient = qi.decodeToBigInteger();

			if (oth != null && ! oth.isEmpty()) {
				// Construct other info spec
				RSAOtherPrimeInfo[] otherInfo = new RSAOtherPrimeInfo[oth.size()];

				for (int i=0; i < oth.size(); i++) {

					OtherPrimesInfo opi = oth.get(i);

					BigInteger otherPrime = opi.getPrimeFactor().decodeToBigInteger();
					BigInteger otherPrimeExponent = opi.getFactorCRTExponent().decodeToBigInteger();
					BigInteger otherCrtCoefficient = opi.getFactorCRTCoefficient().decodeToBigInteger();

					otherInfo[i] = new RSAOtherPrimeInfo(otherPrime,
						                             otherPrimeExponent,
						                             otherCrtCoefficient);
				}

				spec = new RSAMultiPrimePrivateCrtKeySpec(modulus,
	                        	                                  publicExponent,
	                        	                                  privateExponent,
	                        	                                  primeP,
	                        	                                  primeQ,
	                        	                                  primeExponentP,
	                        	                                  primeExponentQ,
	                        	                                  crtCoefficient,
	                        	                                  otherInfo);
			} else {
				// Construct spec with no other info
				spec = new RSAPrivateCrtKeySpec(modulus,
	                        	                        publicExponent,
	                        	                        privateExponent,
	                        	                        primeP,
	                        	                        primeQ,
	                        	                        primeExponentP,
	                        	                        primeExponentQ,
	                        	                        crtCoefficient);	
			} 
		}

		try {
			KeyFactory factory = KeyFactory.getInstance("RSA");

			return (RSAPrivateKey) factory.generatePrivate(spec);

		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {

			throw new JOSEException(e.getMessage(), e);
		}
	}


	@Override
	public PublicKey toPublicKey()
		throws JOSEException {

		return toRSAPublicKey();
	}


	@Override
	public PrivateKey toPrivateKey()
		throws JOSEException {
		
		PrivateKey prv = toRSAPrivateKey();
		
		if (prv != null) {
			// Return private RSA key with key material
			return prv;
		}
		
		// Return private RSA key as PKCS#11 handle, or null
		return privateKey;
	}


	/**
	 * Returns a standard {@code java.security.KeyPair} representation of 
	 * this RSA JWK.
	 * 
	 * @return The RSA key pair. The private RSA key will be {@code null} 
	 *         if not specified.
	 * 
	 * @throws JOSEException If RSA is not supported by the underlying Java
	 *                       Cryptography (JCA) provider or if the JWK
	 *                       parameters are invalid for a public and / or
	 *                       private RSA key.
	 */
	@Override
	public KeyPair toKeyPair() 
		throws JOSEException {
		
		return new KeyPair(toRSAPublicKey(), toPrivateKey());
	}
	
	
	@Override
	public boolean matches(final X509Certificate cert) {
		
		RSAPublicKey certRSAKey;
		try {
			certRSAKey = (RSAPublicKey) getParsedX509CertChain().get(0).getPublicKey();
		} catch (ClassCastException ex) {
			return false;
		}
		if (! e.decodeToBigInteger().equals(certRSAKey.getPublicExponent())) {
			return false;
		}
		if (! n.decodeToBigInteger().equals(certRSAKey.getModulus())) {
			return false;
		}
		return true;
	}
	
	
	@Override
	public LinkedHashMap<String,?> getRequiredParams() {

		// Put mandatory params in sorted order
		LinkedHashMap<String,String> requiredParams = new LinkedHashMap<>();
		requiredParams.put("e", e.toString());
		requiredParams.put("kty", getKeyType().getValue());
		requiredParams.put("n", n.toString());
		return requiredParams;
	}


	@Override
	public boolean isPrivate() {

		// Check if 1st or 2nd form params are specified, or PKCS#11 handle
		return d != null || p != null || privateKey != null;
	}


	@Override
	public int size() {

		try {
			return ByteUtils.safeBitLength(n.decode());
		} catch (IntegerOverflowException e) {
			throw new ArithmeticException(e.getMessage());
		}
	}


	/**
	 * Returns a copy of this RSA JWK with any private values removed.
	 *
	 * @return The copied public RSA JWK.
	 */
	@Override
	public RSAKey toPublicJWK() {

		return new RSAKey(
			getModulus(), getPublicExponent(),
			getKeyUse(), getKeyOperations(), getAlgorithm(), getKeyID(),
			getX509CertURL(), getX509CertThumbprint(), getX509CertSHA256Thumbprint(), getX509CertChain(),
			getKeyStore());
	}
	
	
	@Override
	public JSONObject toJSONObject() {

		JSONObject o = super.toJSONObject();

		// Append public RSA key specific attributes
		o.put("n", n.toString());
		o.put("e", e.toString());
		if (d != null) {
			o.put("d", d.toString());
		}
		if (p != null) {
			o.put("p", p.toString());
		}
		if (q != null) {
			o.put("q", q.toString());
		}
		if (dp != null) {
			o.put("dp", dp.toString());
		}
		if (dq != null) {
			o.put("dq", dq.toString());
		}
		if (qi != null) {
			o.put("qi", qi.toString());
		}
		if (oth != null && !oth.isEmpty()) {

			JSONArray a = new JSONArray();

			for (OtherPrimesInfo other : oth) {

				JSONObject oo = new JSONObject();
				oo.put("r", other.r.toString());
				oo.put("d", other.d.toString());
				oo.put("t", other.t.toString());

				a.add(oo);
			}

			o.put("oth", a);
		}

		return o;
	}


	/**
	 * Parses a public / private RSA JWK from the specified JSON object
	 * string representation.
	 *
	 * @param s The JSON object string to parse. Must not be {@code null}.
	 *
	 * @return The public / private RSA JWK.
	 *
	 * @throws ParseException If the string couldn't be parsed to an RSA
	 *                        JWK.
	 */
	public static RSAKey parse(final String s)
		throws ParseException {

		return parse(JSONObjectUtils.parse(s));
	}


	/**
	 * Parses a public / private RSA JWK from the specified JSON object 
	 * representation.
	 *
	 * @param jsonObject The JSON object to parse. Must not be 
	 *                   {@code null}.
	 *
	 * @return The public / private RSA Key.
	 *
	 * @throws ParseException If the JSON object couldn't be parsed to an
	 *                        RSA JWK.
	 */
	public static RSAKey parse(final JSONObject jsonObject)
		throws ParseException {

		// Parse the mandatory public key parameters first
		Base64URL n = new Base64URL(JSONObjectUtils.getString(jsonObject, "n"));
		Base64URL e = new Base64URL(JSONObjectUtils.getString(jsonObject, "e"));

		// Check key type
		KeyType kty = KeyType.parse(JSONObjectUtils.getString(jsonObject, "kty"));
		if (kty != KeyType.RSA) {
			throw new ParseException("The key type \"kty\" must be RSA", 0);
		}
		
		// Parse the optional private key parameters

		// 1st private representation
		Base64URL d = null;
		if (jsonObject.containsKey("d")) {
			d = new Base64URL(JSONObjectUtils.getString(jsonObject, "d"));
		}

		// 2nd private (CRT) representation
		Base64URL p = null;
		if (jsonObject.containsKey("p")) {
			p = new Base64URL(JSONObjectUtils.getString(jsonObject, "p"));
		}
		Base64URL q = null;
		if (jsonObject.containsKey("q")) {
			q = new Base64URL(JSONObjectUtils.getString(jsonObject, "q"));
		}
		Base64URL dp = null;
		if (jsonObject.containsKey("dp")) {
			dp = new Base64URL(JSONObjectUtils.getString(jsonObject, "dp"));
		}
		Base64URL dq= null;
		if (jsonObject.containsKey("dq")) {
			dq = new Base64URL(JSONObjectUtils.getString(jsonObject, "dq"));
		}
		Base64URL qi = null;
		if (jsonObject.containsKey("qi")) {
			qi = new Base64URL(JSONObjectUtils.getString(jsonObject, "qi"));
		}
		
		List<OtherPrimesInfo> oth = null;
		if (jsonObject.containsKey("oth")) {

			JSONArray arr = JSONObjectUtils.getJSONArray(jsonObject, "oth");
			oth = new ArrayList<>(arr.size());
			
			for (Object o : arr) {

				if (o instanceof JSONObject) {
					JSONObject otherJson = (JSONObject)o;

					Base64URL r = new Base64URL(JSONObjectUtils.getString(otherJson, "r"));
					Base64URL odq = new Base64URL(JSONObjectUtils.getString(otherJson, "dq"));
					Base64URL t = new Base64URL(JSONObjectUtils.getString(otherJson, "t"));

					OtherPrimesInfo prime = new OtherPrimesInfo(r, odq, t);
					oth.add(prime);
				}
			}
		}

		try {
			return new RSAKey(n, e, d, p, q, dp, dq, qi, oth, null,
				JWKMetadata.parseKeyUse(jsonObject),
				JWKMetadata.parseKeyOperations(jsonObject),
				JWKMetadata.parseAlgorithm(jsonObject),
				JWKMetadata.parseKeyID(jsonObject),
				JWKMetadata.parseX509CertURL(jsonObject),
				JWKMetadata.parseX509CertThumbprint(jsonObject),
				JWKMetadata.parseX509CertSHA256Thumbprint(jsonObject),
				JWKMetadata.parseX509CertChain(jsonObject),
				null);
		
		} catch (IllegalArgumentException ex) {
			// Inconsistent 2nd spec, conflicting 'use' and 'key_ops', etc.
			throw new ParseException(ex.getMessage(), 0);
		}
	}
	
	
	/**
	 * Parses a public RSA JWK from the specified X.509 certificate.
	 *
	 * <p><strong>Important:</strong> The X.509 certificate is not
	 * validated!
	 *
	 * <p>Sets the following JWK parameters:
	 *
	 * <ul>
	 *     <li>The JWK use inferred by {@link KeyUse#from}.
	 *     <li>The JWK ID from the X.509 serial number (in base 10).
	 *     <li>The JWK X.509 certificate chain (this certificate only).
	 *     <li>The JWK X.509 certificate SHA-256 thumbprint.
	 * </ul>
	 *
	 * @param cert The X.509 certificate. Must not be {@code null}.
	 *
	 * @return The public RSA key.
	 *
	 * @throws JOSEException If parsing failed.
	 */
	public static RSAKey parse(final X509Certificate cert)
		throws JOSEException {
		
		if (! (cert.getPublicKey() instanceof RSAPublicKey)) {
			throw new JOSEException("The public key of the X.509 certificate is not RSA");
		}
		
		RSAPublicKey publicKey = (RSAPublicKey)cert.getPublicKey();
		
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			
			return new RSAKey.Builder(publicKey)
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
	 * Loads a public / private RSA JWK from the specified JCA key store.
	 *
	 * <p><strong>Important:</strong> The X.509 certificate is not
	 * validated!
	 *
	 * @param keyStore The key store. Must not be {@code null}.
	 * @param alias    The alias. Must not be {@code null}.
	 * @param pin      The pin to unlock the private key if any, empty or
	 *                 {@code null} if not required.
	 *
	 * @return The public / private RSA key, {@code null} if no key with
	 *         the specified alias was found.
	 *
	 * @throws KeyStoreException On a key store exception.
	 * @throws JOSEException     If RSA key loading failed.
	 */
	public static RSAKey load(final KeyStore keyStore,
				  final String alias,
				  final char[] pin)
		throws KeyStoreException, JOSEException {
		
		java.security.cert.Certificate cert = keyStore.getCertificate(alias);
		
		if (cert == null || ! (cert instanceof X509Certificate)) {
			return null;
		}
		
		X509Certificate x509Cert = (X509Certificate)cert;
		
		if (! (x509Cert.getPublicKey() instanceof RSAPublicKey)) {
			throw new JOSEException("Couldn't load RSA JWK: The key algorithm is not RSA");
		}
		
		RSAKey rsaJWK = RSAKey.parse(x509Cert);
		
		// Let kid=alias
		rsaJWK = new RSAKey.Builder(rsaJWK).keyID(alias).keyStore(keyStore).build();
		
		// Check for private counterpart
		Key key;
		try {
			key = keyStore.getKey(alias, pin);
		} catch (UnrecoverableKeyException | NoSuchAlgorithmException e) {
			throw new JOSEException("Couldn't retrieve private RSA key (bad pin?): " + e.getMessage(), e);
		}
		
		if (key instanceof RSAPrivateKey) {
			// Simple file based key store
			return new RSAKey.Builder(rsaJWK)
				.privateKey((RSAPrivateKey)key)
				.build();
		} else if (key instanceof PrivateKey && "RSA".equalsIgnoreCase(key.getAlgorithm())) {
			// PKCS#11 store
			return new RSAKey.Builder(rsaJWK)
				.privateKey((PrivateKey)key)
				.build();
		} else {
			return rsaJWK;
		}
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RSAKey)) return false;
		if (!super.equals(o)) return false;
		RSAKey rsaKey = (RSAKey) o;
		return Objects.equals(n, rsaKey.n) &&
				Objects.equals(e, rsaKey.e) &&
				Objects.equals(d, rsaKey.d) &&
				Objects.equals(p, rsaKey.p) &&
				Objects.equals(q, rsaKey.q) &&
				Objects.equals(dp, rsaKey.dp) &&
				Objects.equals(dq, rsaKey.dq) &&
				Objects.equals(qi, rsaKey.qi) &&
				Objects.equals(oth, rsaKey.oth) &&
				Objects.equals(privateKey, rsaKey.privateKey);
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), n, e, d, p, q, dp, dq, qi, oth, privateKey);
	}
}
