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
 * Javascript Object Signing and Encryption (JOSE) classes.
 *
 * <p>This package provides representation, compact serialisation and parsing 
 * for the following JOSE objects:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.PlainObject Unsecured ({@code alg=none})
 *         JOSE objects}.
 *     <li>{@link com.nimbusds.jose.JWSObject JSON Web Signature (JWS) 
 *         objects}.
 *     <li>{@link com.nimbusds.jose.JWEObject JSON Web Encryption (JWE) 
 *         objects}.
 * </ul>
 *
 * <p>References:
 *
 * <ul>
 *     <li><a href="http://tools.ietf.org/html/rfc7515">RFC 7515 (JWS)</a>
 *     <li><a href="http://tools.ietf.org/html/rfc7516">RFC 7516 (JWE)</a>
 * </ul>
 */
package com.nimbusds.jose;
