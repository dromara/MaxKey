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

package com.nimbusds.jose.crypto;


import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import static com.nimbusds.jose.jwk.gen.RSAKeyGenerator.MIN_KEY_SIZE_BITS;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.impl.RSAKeyUtils;
import com.nimbusds.jose.crypto.impl.RSASSA;
import com.nimbusds.jose.crypto.impl.RSASSAProvider;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64URL;
import net.jcip.annotations.ThreadSafe;



/**
 * RSA Signature-Scheme-with-Appendix (RSASSA) signer of 
 * {@link com.nimbusds.jose.JWSObject JWS objects}. Expects a private RSA key.
 *
 * <p>See RFC 7518, sections
 * <a href="https://tools.ietf.org/html/rfc7518#section-3.3">3.3</a> and
 * <a href="https://tools.ietf.org/html/rfc7518#section-3.5">3.5</a> for more
 * information.
 *
 * <p>This class is thread-safe.
 *
 * <p>Supports the following algorithms:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#RS256}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#RS384}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#RS512}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#PS256}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#PS384}
 *     <li>{@link com.nimbusds.jose.JWSAlgorithm#PS512}
 * </ul>
 * 
 * @author Vladimir Dzhuvinov
 * @author Omer Levi Hevroni
 * @version 2018-10-11
 */
@ThreadSafe
public class RSASSASigner extends RSASSAProvider implements JWSSigner {


	/**
	 * The private RSA key. Represented by generic private key interface to
	 * support key stores that prevent exposure of the private key
	 * parameters via the {@link java.security.interfaces.RSAPrivateKey}
	 * API.
	 *
	 * See https://bitbucket.org/connect2id/nimbus-jose-jwt/issues/169
	 */
	private final PrivateKey privateKey;


	/**
	 * Creates a new RSA Signature-Scheme-with-Appendix (RSASSA) signer.
	 * This constructor can also accept a private RSA key located in a
	 * PKCS#11 store that doesn't expose the private key parameters (such
	 * as a smart card or HSM).
	 *
	 * @param privateKey The private RSA key. Its algorithm must be "RSA"
	 *                   and its length at least 2048 bits. Note that the
	 *                   length of an RSA key in a PKCS#11 store cannot be
	 *                   checked. Must not be {@code null}.
	 */
	public RSASSASigner(final PrivateKey privateKey) {

		this(privateKey, false);
	}


	/**
	 * Creates a new RSA Signature-Scheme-with-Appendix (RSASSA) signer.
	 * This constructor can also accept a private RSA key located in a
	 * PKCS#11 store that doesn't expose the private key parameters (such
	 * as a smart card or HSM).
	 *
	 * @param privateKey   The private RSA key. Its algorithm must be
	 *                     "RSA" and its length at least 2048 bits. Note
	 *                     that the length of an RSA key in a PKCS#11 store
	 *                     cannot be checked. Must not be {@code null}.
	 * @param allowWeakKey {@code true} to allow an RSA key shorter than
	 *                     2048 bits.
	 */
	public RSASSASigner(final PrivateKey privateKey, final boolean allowWeakKey) {

		if (! "RSA".equalsIgnoreCase(privateKey.getAlgorithm())) {
			throw new IllegalArgumentException("The private key algorithm must be RSA");
		}
		
		if (! allowWeakKey) {
			
			int keyBitLength = RSAKeyUtils.keyBitLength(privateKey);
			
			if (keyBitLength > 0 && keyBitLength < MIN_KEY_SIZE_BITS) {
				throw new IllegalArgumentException("The RSA key size must be at least " + MIN_KEY_SIZE_BITS + " bits");
			}
		}

		this.privateKey = privateKey;
	}


	/**
	 * Creates a new RSA Signature-Scheme-with-Appendix (RSASSA) signer.
	 *
	 * @param rsaJWK The RSA JSON Web Key (JWK). Must contain or reference
	 *               a private part. Its length must be at least 2048 bits.
	 *               Note that the length of an RSA key in a PKCS#11 store
	 *               cannot be checked. Must not be {@code null}.
	 *
	 * @throws JOSEException If the RSA JWK doesn't contain a private part
	 *                       or its extraction failed.
	 */
	public RSASSASigner(final RSAKey rsaJWK)
		throws JOSEException {

		this(rsaJWK, false);
	}


	/**
	 * Creates a new RSA Signature-Scheme-with-Appendix (RSASSA) signer.
	 *
	 * @param rsaJWK       The RSA JSON Web Key (JWK). Must contain or
	 *                     reference a private part. Its length must be at
	 *                     least 2048 bits. Note that the length of an RSA
	 *                     key in a PKCS#11 store cannot be checked. Must
	 *                     not be {@code null}.
	 * @param allowWeakKey {@code true} to allow an RSA key shorter than
	 * 	               2048 bits.
	 *
	 * @throws JOSEException If the RSA JWK doesn't contain a private part
	 *                       or its extraction failed.
	 */
	public RSASSASigner(final RSAKey rsaJWK, final boolean allowWeakKey)
		throws JOSEException {

		this(RSAKeyUtils.toRSAPrivateKey(rsaJWK), allowWeakKey);
	}


	/**
	 * Gets the private RSA key.
	 *
	 * @return The private RSA key. Casting to
	 *         {@link java.security.interfaces.RSAPrivateKey} may not be
	 *         possible if the key is located in a PKCS#11 store that
	 *         doesn't expose the private key parameters.
	 */
	public PrivateKey getPrivateKey() {

		return privateKey;
	}


	@Override
	public Base64URL sign(final JWSHeader header, final byte[] signingInput)
		throws JOSEException {

		Signature signer = RSASSA.getSignerAndVerifier(header.getAlgorithm(), getJCAContext().getProvider());

		try {
			signer.initSign(privateKey);
			signer.update(signingInput);
			return Base64URL.encode(signer.sign());

		} catch (InvalidKeyException e) {
			throw new JOSEException("Invalid private RSA key: " + e.getMessage(), e);

		} catch (SignatureException e) {
			throw new JOSEException("RSA signature exception: " + e.getMessage(), e);
		}
	}
}
