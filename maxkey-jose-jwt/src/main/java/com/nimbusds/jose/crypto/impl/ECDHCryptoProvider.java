/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2019, Connect2id Ltd and contributors.
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
import java.util.LinkedHashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWECryptoParts;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.util.Base64URL;


/**
 * The base abstract class for Elliptic Curve Diffie-Hellman encrypters and
 * decrypters of {@link com.nimbusds.jose.JWEObject JWE objects}.
 *
 * <p>Supports the following key management algorithms:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#ECDH_ES}
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#ECDH_ES_A128KW}
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#ECDH_ES_A192KW}
 *     <li>{@link com.nimbusds.jose.JWEAlgorithm#ECDH_ES_A256KW}
 * </ul>
 *
 * <p>Supports the following elliptic curves:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.jwk.Curve#P_256}
 *     <li>{@link com.nimbusds.jose.jwk.Curve#P_384}
 *     <li>{@link com.nimbusds.jose.jwk.Curve#P_521}
 *     <li>{@link com.nimbusds.jose.jwk.Curve#X25519}
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
 * @author Tim McLean
 * @author Vladimir Dzhuvinov
 * @author Fernando González Callejas
 * @version 2019-01-24
 */
public abstract class ECDHCryptoProvider extends BaseJWEProvider {


	/**
	 * The supported JWE algorithms by the ECDH crypto provider class.
	 */
	public static final Set<JWEAlgorithm> SUPPORTED_ALGORITHMS;


	/**
	 * The supported encryption methods by the ECDH crypto provider class.
	 */
	public static final Set<EncryptionMethod> SUPPORTED_ENCRYPTION_METHODS = ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS;


	static {
		Set<JWEAlgorithm> algs = new LinkedHashSet<>();
		algs.add(JWEAlgorithm.ECDH_ES);
		algs.add(JWEAlgorithm.ECDH_ES_A128KW);
		algs.add(JWEAlgorithm.ECDH_ES_A192KW);
		algs.add(JWEAlgorithm.ECDH_ES_A256KW);
		SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
	}


	/**
	 * The elliptic curve.
	 */
	private final Curve curve;


	/**
	 * The Concatenation Key Derivation Function (KDF).
	 */
	private final ConcatKDF concatKDF;


	/**
	 * Creates a new Elliptic Curve Diffie-Hellman encryption /decryption
	 * provider.
	 *
	 * @param curve The elliptic curve. Must be supported and not
	 *              {@code null}.
	 *
	 * @throws JOSEException If the elliptic curve is not supported.
	 */
	protected ECDHCryptoProvider(final Curve curve)
		throws JOSEException {

		super(SUPPORTED_ALGORITHMS, ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS);

		Curve definedCurve = curve != null ? curve : new Curve("unknown");

		if (! supportedEllipticCurves().contains(curve)) {
			throw new JOSEException(AlgorithmSupportMessage.unsupportedEllipticCurve(
				definedCurve, supportedEllipticCurves()));
		}

		this.curve = curve;

		concatKDF = new ConcatKDF("SHA-256");
	}


	/**
	 * Returns the Concatenation Key Derivation Function (KDF).
	 *
	 * @return The concat KDF.
	 */
	protected ConcatKDF getConcatKDF() {

		return concatKDF;
	}


	/**
	 * Returns the names of the supported elliptic curves. These correspond
	 * to the {@code crv} EC JWK parameter.
	 *
	 * @return The supported elliptic curves.
	 */
	public abstract Set<Curve> supportedEllipticCurves();


	/**
	 * Returns the elliptic curve of the key (JWK designation).
	 *
	 * @return The elliptic curve.
	 */
	public Curve getCurve() {

		return curve;
	}

	/**
	 * Encrypts the specified plaintext using the specified shared secret
	 * ("Z").
	 */
	protected JWECryptoParts encryptWithZ(final JWEHeader header, final SecretKey Z, final byte[] clearText)
		throws JOSEException {
		
		return this.encryptWithZ(header, Z, clearText, null);
	}

	/**
	 * Encrypts the specified plaintext using the specified shared secret
	 * ("Z") and, if provided, the content encryption key (CEK).
	 */
	protected JWECryptoParts encryptWithZ(final JWEHeader header,
					      final SecretKey Z,
					      final byte[] clearText,
					      final SecretKey contentEncryptionKey)
		throws JOSEException {

		final JWEAlgorithm alg = header.getAlgorithm();
		final ECDH.AlgorithmMode algMode = ECDH.resolveAlgorithmMode(alg);
		final EncryptionMethod enc = header.getEncryptionMethod();

		// Derive shared key via concat KDF
		getConcatKDF().getJCAContext().setProvider(getJCAContext().getMACProvider()); // update before concat
		SecretKey sharedKey = ECDH.deriveSharedKey(header, Z, getConcatKDF());

		final SecretKey cek;
		final Base64URL encryptedKey; // The CEK encrypted (second JWE part)

		if (algMode.equals(ECDH.AlgorithmMode.DIRECT)) {
			cek = sharedKey;
			encryptedKey = null;
		} else if (algMode.equals(ECDH.AlgorithmMode.KW)) {
			if(contentEncryptionKey != null) { // Use externally supplied CEK
				cek = contentEncryptionKey;
			} else { // Generate the CEK according to the enc method
				cek = ContentCryptoProvider.generateCEK(enc, getJCAContext().getSecureRandom());
			}
			encryptedKey = Base64URL.encode(AESKW.wrapCEK(cek, sharedKey, getJCAContext().getKeyEncryptionProvider()));
		} else {
			throw new JOSEException("Unexpected JWE ECDH algorithm mode: " + algMode);
		}

		return ContentCryptoProvider.encrypt(header, clearText, cek, encryptedKey, getJCAContext());
	}


	/**
	 * Decrypts the encrypted JWE parts using the specified shared secret ("Z").
	 */
	protected byte[] decryptWithZ(final JWEHeader header,
				      final SecretKey Z,
				      final Base64URL encryptedKey,
				      final Base64URL iv,
				      final Base64URL cipherText,
				      final Base64URL authTag)
		throws JOSEException {

		final JWEAlgorithm alg = header.getAlgorithm();
		final ECDH.AlgorithmMode algMode = ECDH.resolveAlgorithmMode(alg);

		// Derive shared key via concat KDF
		getConcatKDF().getJCAContext().setProvider(getJCAContext().getMACProvider()); // update before concat
		SecretKey sharedKey = ECDH.deriveSharedKey(header, Z, getConcatKDF());

		final SecretKey cek;

		if (algMode.equals(ECDH.AlgorithmMode.DIRECT)) {
			cek = sharedKey;
		} else if (algMode.equals(ECDH.AlgorithmMode.KW)) {
			if (encryptedKey == null) {
				throw new JOSEException("Missing JWE encrypted key");
			}
			cek = AESKW.unwrapCEK(sharedKey, encryptedKey.decode(), getJCAContext().getKeyEncryptionProvider());
		} else {
			throw new JOSEException("Unexpected JWE ECDH algorithm mode: " + algMode);
		}

		return ContentCryptoProvider.decrypt(header, encryptedKey, iv, cipherText, authTag, cek, getJCAContext());
	}


}
