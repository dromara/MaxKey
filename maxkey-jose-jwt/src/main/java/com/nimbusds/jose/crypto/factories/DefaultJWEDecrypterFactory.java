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

package com.nimbusds.jose.crypto.factories;


import java.security.Key;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.crypto.SecretKey;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jca.JWEJCAContext;
import com.nimbusds.jose.proc.JWEDecrypterFactory;
import net.jcip.annotations.ThreadSafe;


/**
 * Default JSON Web Encryption (JWE) decrypter factory.
 *
 * <p>Supports all standard JWE algorithms implemented in the
 * {@link com.nimbusds.jose.crypto} package.
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-11-16
 */
@ThreadSafe
public class DefaultJWEDecrypterFactory implements JWEDecrypterFactory {


	/**
	 * The supported JWE algorithms.
	 */
	public static final Set<JWEAlgorithm> SUPPORTED_ALGORITHMS;


	/**
	 * The supported encryption methods.
	 */
	public static final Set<EncryptionMethod> SUPPORTED_ENCRYPTION_METHODS;


	static {
		Set<JWEAlgorithm> algs = new LinkedHashSet<>();
		algs.addAll(RSADecrypter.SUPPORTED_ALGORITHMS);
		algs.addAll(ECDHDecrypter.SUPPORTED_ALGORITHMS);
		algs.addAll(DirectDecrypter.SUPPORTED_ALGORITHMS);
		algs.addAll(AESDecrypter.SUPPORTED_ALGORITHMS);
		algs.addAll(PasswordBasedDecrypter.SUPPORTED_ALGORITHMS);
		SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);

		Set<EncryptionMethod> encs = new LinkedHashSet<>();
		encs.addAll(RSADecrypter.SUPPORTED_ENCRYPTION_METHODS);
		encs.addAll(ECDHDecrypter.SUPPORTED_ENCRYPTION_METHODS);
		encs.addAll(DirectDecrypter.SUPPORTED_ENCRYPTION_METHODS);
		encs.addAll(AESDecrypter.SUPPORTED_ENCRYPTION_METHODS);
		encs.addAll(PasswordBasedDecrypter.SUPPORTED_ENCRYPTION_METHODS);
		SUPPORTED_ENCRYPTION_METHODS = Collections.unmodifiableSet(encs);
	}


	/**
	 * The JWE JCA context.
	 */
	private final JWEJCAContext jcaContext = new JWEJCAContext();


	@Override
	public Set<JWEAlgorithm> supportedJWEAlgorithms() {

		return SUPPORTED_ALGORITHMS;
	}


	@Override
	public Set<EncryptionMethod> supportedEncryptionMethods() {

		return SUPPORTED_ENCRYPTION_METHODS;
	}


	@Override
	public JWEJCAContext getJCAContext() {

		return jcaContext;
	}


	@Override
	public JWEDecrypter createJWEDecrypter(final JWEHeader header, final Key key)
		throws JOSEException {

		final JWEDecrypter decrypter;

		if (RSADecrypter.SUPPORTED_ALGORITHMS.contains(header.getAlgorithm()) &&
			RSADecrypter.SUPPORTED_ENCRYPTION_METHODS.contains(header.getEncryptionMethod())) {

			if (!(key instanceof RSAPrivateKey)) {
				throw new KeyTypeException(RSAPrivateKey.class);
			}

			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)key;

			decrypter = new RSADecrypter(rsaPrivateKey);

		} else if (ECDHDecrypter.SUPPORTED_ALGORITHMS.contains(header.getAlgorithm()) &&
			ECDHDecrypter.SUPPORTED_ENCRYPTION_METHODS.contains(header.getEncryptionMethod())) {

			if (!(key instanceof ECPrivateKey)) {
				throw new KeyTypeException(ECPrivateKey.class);
			}

			ECPrivateKey ecPrivateKey = (ECPrivateKey)key;
			decrypter = new ECDHDecrypter(ecPrivateKey);

		} else if (DirectDecrypter.SUPPORTED_ALGORITHMS.contains(header.getAlgorithm()) &&
			DirectDecrypter.SUPPORTED_ENCRYPTION_METHODS.contains(header.getEncryptionMethod())) {

			if (!(key instanceof SecretKey)) {
				throw new KeyTypeException(SecretKey.class);
			}

			SecretKey aesKey = (SecretKey)key;
			DirectDecrypter directDecrypter =  new DirectDecrypter(aesKey);

			if (! directDecrypter.supportedEncryptionMethods().contains(header.getEncryptionMethod())) {
				throw new KeyLengthException(header.getEncryptionMethod().cekBitLength(), header.getEncryptionMethod());
			}

			decrypter = directDecrypter;

		} else if (AESDecrypter.SUPPORTED_ALGORITHMS.contains(header.getAlgorithm()) &&
			AESDecrypter.SUPPORTED_ENCRYPTION_METHODS.contains(header.getEncryptionMethod())) {

			if (!(key instanceof SecretKey)) {
				throw new KeyTypeException(SecretKey.class);
			}

			SecretKey aesKey = (SecretKey)key;
			AESDecrypter aesDecrypter = new AESDecrypter(aesKey);

			if (! aesDecrypter.supportedJWEAlgorithms().contains(header.getAlgorithm())) {
				throw new KeyLengthException(header.getAlgorithm());
			}

			decrypter = aesDecrypter;

		} else if (PasswordBasedDecrypter.SUPPORTED_ALGORITHMS.contains(header.getAlgorithm()) &&
			PasswordBasedDecrypter.SUPPORTED_ENCRYPTION_METHODS.contains(header.getEncryptionMethod())) {

			if (!(key instanceof SecretKey)) {
				throw new KeyTypeException(SecretKey.class);
			}

			byte[] password = key.getEncoded();
			decrypter = new PasswordBasedDecrypter(password);

		} else {

			throw new JOSEException("Unsupported JWE algorithm or encryption method");
		}

		// Apply JCA context
		decrypter.getJCAContext().setSecureRandom(jcaContext.getSecureRandom());
		decrypter.getJCAContext().setProvider(jcaContext.getProvider());
		decrypter.getJCAContext().setKeyEncryptionProvider(jcaContext.getKeyEncryptionProvider());
		decrypter.getJCAContext().setMACProvider(jcaContext.getMACProvider());
		decrypter.getJCAContext().setContentEncryptionProvider(jcaContext.getContentEncryptionProvider());

		return decrypter;
	}
}
