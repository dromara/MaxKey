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


import java.io.Reader;
import java.io.StringReader;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.List;

import com.nimbusds.jose.JOSEException;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;


/**
 * PEM-encoded public / private key parser. Requires Bouncy Castle.
 *
 * @author Stefan Larsson
 */
class PEMEncodedKeyParser {
	
	
	// JcaPEMKeyConverter looks threadsafe
	private static final JcaPEMKeyConverter pemConverter = new JcaPEMKeyConverter();
	
	
	private PEMEncodedKeyParser() {
		// prevent construction of utility class
	}
	
	
	/**
	 * Parses one or more PEM-encoded certificates, public and / or private
	 * keys. The input is assumed to be not password-protected.
	 *
	 * @param pemEncodedKeys String of one or more PEM-encoded keys.
	 *
	 * @return The found keys.
	 * 
	 * @throws JOSEException If parsing failed.
	 */
	static List<KeyPair> parseKeys(final String pemEncodedKeys)
		throws JOSEException {
		
		// Strips the "---- {BEGIN,END} {CERTIFICATE,PUBLIC/PRIVATE KEY} -----"-like header and footer lines,
		// base64-decodes the body,
		// then uses the proper key specification format to turn it into a JCA Key instance
		final Reader pemReader = new StringReader(pemEncodedKeys);
		final PEMParser parser = new PEMParser(pemReader);
		final List<KeyPair> keys = new ArrayList<>();
		
		try {
			Object pemObj;
			do {
				pemObj = parser.readObject();
				
				// if public key, use as-is
				if (pemObj instanceof SubjectPublicKeyInfo) {
					keys.add(toKeyPair((SubjectPublicKeyInfo) pemObj));
					continue;
				}
				
				// if certificate, use the public key which is signed
				if (pemObj instanceof X509CertificateHolder) {
					keys.add(toKeyPair((X509CertificateHolder) pemObj));
					continue;
				}
				
				// if EC private key given, it arrives here as a keypair
				if (pemObj instanceof PEMKeyPair) {
					keys.add(toKeyPair((PEMKeyPair) pemObj));
					continue;
				}
				
				// if (RSA) private key given, return it
				if (pemObj instanceof PrivateKeyInfo) {
					keys.add(toKeyPair((PrivateKeyInfo) pemObj));
					// continue implicitly
				}
			} while (pemObj != null);
			
			return keys;
		} catch (Exception e) {
			throw new JOSEException(e.getMessage(), e);
		}
	}
	
	
	private static KeyPair toKeyPair(final SubjectPublicKeyInfo spki) 
		throws PEMException {
		
		return new KeyPair(pemConverter.getPublicKey(spki), null);
	}
	
	
	private static KeyPair toKeyPair(final X509CertificateHolder pemObj) 
		throws PEMException {
		
		final SubjectPublicKeyInfo spki = pemObj.getSubjectPublicKeyInfo();
		return new KeyPair(pemConverter.getPublicKey(spki), null);
	}
	
	
	private static KeyPair toKeyPair(final PEMKeyPair pair) 
		throws PEMException {
		
		return pemConverter.getKeyPair(pair);
	}
	
	
	private static KeyPair toKeyPair(final PrivateKeyInfo pki)
		throws PEMException, NoSuchAlgorithmException, InvalidKeySpecException {
		
		final PrivateKey privateKey = pemConverter.getPrivateKey(pki);
		
		// If it's RSA, we can use the modulus and public exponents as BigIntegers to create a public key
		if (privateKey instanceof RSAPrivateCrtKey) {
			final RSAPublicKeySpec publicKeySpec =
				new RSAPublicKeySpec(((RSAPrivateCrtKey) privateKey).getModulus(),
					((RSAPrivateCrtKey) privateKey).getPublicExponent());
			
			final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			final PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			return new KeyPair(publicKey, privateKey);
		}
		
		// If was a private EC key, it would already have been received as a PEMKeyPair
		return new KeyPair(null, privateKey);
	}
}
