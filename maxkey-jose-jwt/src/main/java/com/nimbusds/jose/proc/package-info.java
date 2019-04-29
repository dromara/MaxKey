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
 * JOSE objects (with arbitrary payloads). Provides a core
 * {@link com.nimbusds.jose.proc.JOSEProcessor interface} for processing JWS,
 * JWE and unsecured (plain) objects, with a
 * {@link com.nimbusds.jose.proc.DefaultJOSEProcessor default implementation}
 * which can be configured and extended as required.
 *
 * <p>To process JSON Web Tokens (JWT) refer to the
 * {@link com.nimbusds.jwt.proc} package.
 */
package com.nimbusds.jose.proc;