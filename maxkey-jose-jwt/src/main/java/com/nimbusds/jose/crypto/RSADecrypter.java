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


import java.security.PrivateKey;
import java.util.Set;
import javax.crypto.SecretKey;

import static com.nimbusds.jose.jwk.gen.RSAKeyGenerator.MIN_KEY_SIZE_BITS;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.impl.*;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64URL;
import net.jcip.annotations.ThreadSafe;


/**
 * RSA decrypter of {@link com.nimbusds.jose.JWEObject JWE objects}. Expects a
 * private RSA key.
 *
 * <p>Decrypts the encrypted Content Encryption Key (CEK) with the private RSA
 * key, and then uses the CEK along with the IV and authentication tag to
 * decrypt the cipher text. See RFC 7518, sections
 * <a href="https://tools.ietf.org/html/rfc7518#section-4.2">4.2</a> and
 * <a href="https://tools.ietf.org/html/rfc7518#section-4.3">4.3</a> for more
 * information.
 *
 * <p>This class is thread-safe.
 *
 * <p>Supports the following key management algorithms:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#RSA_OAEP_256}
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#RSA_OAEP} (deprecated)
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#RSA1_5} (deprecated)
 * </ul>
 *
 * <p>Supports the following content encryption algorithms:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A128CBC_HS256}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A192CBC_HS384}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A256CBC_HS512}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A128GCM}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A192GCM}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A256GCM}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A128CBC_HS256_DEPRECATED}
 *     <li>{@link com.nimbusds.jose.EncryptionMethod#A256CBC_HS512_DEPRECATED}
 * </ul>
 * 
 * @author David Ortiz
 * @author Vladimir Dzhuvinov
 * @author Dimitar A. Stoikov
 * @version 2018-10-11
 */
@ThreadSafe
public class RSADecrypter extends RSACryptoProvider implements JWEDecrypter, CriticalHeaderParamsAware {


	/**
	 * The critical header policy.
	 */
	private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();


	/**
	 * The private RSA key.
	 */
	private final PrivateKey privateKey;
	
	
	/**
	 * Stores a CEK decryption exception is one was encountered during the
	 * last {@link #decrypt} run.
	 */
	private Exception cekDecryptionException;


	/**
	 * Creates a new RSA decrypter. This constructor can also accept a
	 * private RSA key located in a PKCS#11 store that doesn't expose the
	 * private key parameters (such as a smart card or HSM).
	 *
	 * @param privateKey The private RSA key. Its algorithm must be "RSA"
	 *                   and its length at least 2048 bits. Note that the
	 *                   length of an RSA key in a PKCS#11 store cannot be
	 *                   checked. Must not be {@code null}.
	 */
	public RSADecrypter(final PrivateKey privateKey) {

		this(privateKey, null, false);
	}


	/**
	 * Creates a new RSA decrypter.
	 *
	 * @param rsaJWK The RSA JSON Web Key (JWK). Must contain or reference
	 *               a private part. Its length must be at least 2048 bits.
	 *               Note that the length of an RSA key in a PKCS#11 store
	 *               cannot be checked. Must not be {@code null}.
	 *
	 * @throws JOSEException If the RSA JWK doesn't contain a private part
	 *                       or its extraction failed.
	 */
	public RSADecrypter(final RSAKey rsaJWK)
		throws JOSEException {

		this(RSAKeyUtils.toRSAPrivateKey(rsaJWK));
	}


	/**
	 * Creates a new RSA decrypter. This constructor can also accept a
	 * private RSA key located in a PKCS#11 store that doesn't expose the
	 * private key parameters (such as a smart card or HSM).
	 *
	 * @param privateKey     The private RSA key. Its algorithm must be
	 *                       "RSA" and its length at least 2048 bits. Note
	 *                       that the length of an RSA key in a PKCS#11
	 *                       store cannot be checked. Must not be
	 *                       {@code null}.
	 * @param defCritHeaders The names of the critical header parameters
	 *                       that are deferred to the application for
	 *                       processing, empty set or {@code null} if none.
	 */
	public RSADecrypter(final PrivateKey privateKey,
			    final Set<String> defCritHeaders) {

		this(privateKey, defCritHeaders, false);
	}


	/**
	 * Creates a new RSA decrypter. This constructor can also accept a
	 * private RSA key located in a PKCS#11 store that doesn't expose the
	 * private key parameters (such as a smart card or HSM).
	 *
	 * @param privateKey     The private RSA key. Its algorithm must be
	 *                       "RSA" and its length at least 2048 bits. Note
	 *                       that the length of an RSA key in a PKCS#11
	 *                       store cannot be checked. Must not be
	 *                       {@code null}.
	 * @param defCritHeaders The names of the critical header parameters
	 *                       that are deferred to the application for
	 *                       processing, empty set or {@code null} if none.
	 * @param allowWeakKey   {@code true} to allow an RSA key shorter than
	 * 	                 2048 bits.
	 */
	public RSADecrypter(final PrivateKey privateKey,
			    final Set<String> defCritHeaders,
			    final boolean allowWeakKey) {

		if (! privateKey.getAlgorithm().equalsIgnoreCase("RSA")) {
			throw new IllegalArgumentException("The private key algorithm must be RSA");
		}
		
		if (! allowWeakKey) {
			
			int keyBitLength = RSAKeyUtils.keyBitLength(privateKey);
			
			if (keyBitLength > 0 && keyBitLength < MIN_KEY_SIZE_BITS) {
				throw new IllegalArgumentException("The RSA key size must be at least " + MIN_KEY_SIZE_BITS + " bits");
			}
		}

		this.privateKey = privateKey;

		critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
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
	public Set<String> getProcessedCriticalHeaderParams() {

		return critPolicy.getProcessedCriticalHeaderParams();
	}


	@Override
	public Set<String> getDeferredCriticalHeaderParams() {

		return critPolicy.getProcessedCriticalHeaderParams();
	}


	@Override
	public byte[] decrypt(final JWEHeader header,
		              final Base64URL encryptedKey,
		              final Base64URL iv,
		              final Base64URL cipherText,
		              final Base64URL authTag) 
		throws JOSEException {

		// Validate required JWE parts
		if (encryptedKey == null) {
			throw new JOSEException("Missing JWE encrypted key");
		}	

		if (iv == null) {
			throw new JOSEException("Missing JWE initialization vector (IV)");
		}

		if (authTag == null) {
			throw new JOSEException("Missing JWE authentication tag");
		}

		critPolicy.ensureHeaderPasses(header);
		

		// Derive the content encryption key
		JWEAlgorithm alg = header.getAlgorithm();

		SecretKey cek;

		if (alg.equals(JWEAlgorithm.RSA1_5)) {

			int keyLength = header.getEncryptionMethod().cekBitLength();

			// Protect against MMA attack by generating random CEK to be used on decryption failure,
			// see http://www.ietf.org/mail-archive/web/jose/current/msg01832.html
			final SecretKey randomCEK = ContentCryptoProvider.generateCEK(header.getEncryptionMethod(), getJCAContext().getSecureRandom());

			try {
				cek = RSA1_5.decryptCEK(privateKey, encryptedKey.decode(), keyLength, getJCAContext().getKeyEncryptionProvider());

				if (cek == null) {
					// CEK length mismatch, signalled by null instead of
					// exception to prevent MMA attack
					cek = randomCEK;
				}

			} catch (Exception e) {
				// continue
				cekDecryptionException = e;
				cek = randomCEK;
			}
			
			cekDecryptionException = null;
		
		} else if (alg.equals(JWEAlgorithm.RSA_OAEP)) {

			cek = RSA_OAEP.decryptCEK(privateKey, encryptedKey.decode(), getJCAContext().getKeyEncryptionProvider());

		} else if (alg.equals(JWEAlgorithm.RSA_OAEP_256)) {
			
			cek = RSA_OAEP_256.decryptCEK(privateKey, encryptedKey.decode(), getJCAContext().getKeyEncryptionProvider());
			
		} else {
		
			throw new JOSEException(AlgorithmSupportMessage.unsupportedJWEAlgorithm(alg, SUPPORTED_ALGORITHMS));
		}

		return ContentCryptoProvider.decrypt(header, encryptedKey, iv, cipherText, authTag, cek, getJCAContext());
	}
	
	
	/**
	 * Returns the Content Encryption Key (CEK) decryption exception if one
	 * was encountered during the last {@link #decrypt} run. Intended for
	 * logging and debugging purposes.
	 *
	 * @return The recorded exception, {@code null} if none.
	 */
	public Exception getCEKDecryptionException() {
		
		return cekDecryptionException;
	}
}

