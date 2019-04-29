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
 * Secure framework for application-specific verification and decryption of
 * JSON Web Tokens (JWTs). Provides a core
 * {@link com.nimbusds.jwt.proc.JWTProcessor interface} for processing signed,
 * encrypted and unsecured (plain) JWTs, with a
 * {@link com.nimbusds.jwt.proc.DefaultJWTProcessor default implementation}
 * which can be configured and extended as required.
 *
 * <p>To process generic JOSE objects refer to the
 * {@link com.nimbusds.jose.proc} package.
 *
 * <p>References:
 *
 * <ul>
 *     <li><a href="http://tools.ietf.org/html/rfc7519">RFC 7519 (JWT)</a>
 * </ul>
 */
package com.nimbusds.jwt.proc;