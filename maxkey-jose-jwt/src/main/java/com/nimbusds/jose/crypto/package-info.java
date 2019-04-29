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

/**
 * Implementations of all standard Javascript Object Signing and Encryption
 * (JOSE) algorithms.
 *
 * <p>Provides {@link com.nimbusds.jose.JWSSigner signers} and 
 * {@link com.nimbusds.jose.JWSVerifier verifiers} for the following JSON Web
 * Signature (JWS) algorithms:
 *
 * <ul>
 *     <li>For HMAC algorithms HS256, HS384 and HS512:
 *         <ul>
 *             <li>{@link com.nimbusds.jose.crypto.MACSigner}
 *             <li>{@link com.nimbusds.jose.crypto.MACVerifier}
 *         </ul>
 *     <li>For RSA-SSA signatures RS256, RS384, RS512, PS256, PS384 and PS512:
 *         <ul>
 *             <li>{@link com.nimbusds.jose.crypto.RSASSASigner}
 *             <li>{@link com.nimbusds.jose.crypto.RSASSAVerifier}
 *         </ul>
 *      <li>For ECDSA signatures ES256, ES384 and ES512:
 *         <ul>
 *             <li>{@link com.nimbusds.jose.crypto.ECDSASigner}
 *             <li>{@link com.nimbusds.jose.crypto.ECDSAVerifier}
 *         </ul>
 *      <li>For EdDSA signatures Ed25519:
 *         <ul>
 *             <li>{@link com.nimbusds.jose.crypto.Ed25519Signer}
 *             <li>{@link com.nimbusds.jose.crypto.Ed25519Verifier}
 *         </ul>
 * </ul>
 *
 * <p>Provides {@link com.nimbusds.jose.JWEEncrypter encrypters} and 
 * {@link com.nimbusds.jose.JWEDecrypter decrypters} for the following JSON
 * Web Encryption (JWE) algorithms:
 *
 * <ul>
 *     <li>For RSA PKCS#1 v1.5 and RSA OAEP:
 *         <ul>
 *             <li>{@link com.nimbusds.jose.crypto.RSAEncrypter}
 *             <li>{@link com.nimbusds.jose.crypto.RSADecrypter}
 *         </ul>
 *     <li>For AES key wrap and AES GCM key encryption:
 *         <ul>
 *             <li>{@link com.nimbusds.jose.crypto.AESEncrypter}
 *             <li>{@link com.nimbusds.jose.crypto.AESDecrypter}
 *         </ul>
 *     <li>For direct encryption (using a shared symmetric key):
 *         <ul>
 *             <li>{@link com.nimbusds.jose.crypto.DirectEncrypter}
 *             <li>{@link com.nimbusds.jose.crypto.DirectDecrypter}
 *         </ul>
 *     <li>For Elliptic Curve Diffie-Hellman (ECDH) encryption:
 *         <ul>
 *             <li>{@link com.nimbusds.jose.crypto.ECDHEncrypter}
 *             <li>{@link com.nimbusds.jose.crypto.ECDHDecrypter}
 *             <li>{@link com.nimbusds.jose.crypto.X25519Encrypter} (for Curve25519 only)
 *             <li>{@link com.nimbusds.jose.crypto.X25519Decrypter} (for Curve25519 only)
 *         </ul>
 *     <li>For password-based (PBKDF2) encryption:
 *         <ul>
 *             <li>{@link com.nimbusds.jose.crypto.PasswordBasedEncrypter}
 *             <li>{@link com.nimbusds.jose.crypto.PasswordBasedDecrypter}
 *         </ul>
 * </ul>
 *
 * <p>References:
 *
 * <ul>
 *     <li><a href="http://tools.ietf.org/html/rfc7518">RFC 7518 (JWA)</a>
 * </ul>
 */
package com.nimbusds.jose.crypto;
