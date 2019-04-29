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

package com.nimbusds.jose.proc;


/**
 * Security context. Provides additional information necessary for processing
 * a JOSE object.
 *
 * <p>Example context information:
 *
 * <ul>
 *     <li>Identifier of the message producer (e.g. OpenID Connect issuer) to
 *         retrieve its public key to verify the JWS signature.
 *     <li>Indicator whether the message was received over a secure channel
 *         (e.g. TLS/SSL) which is essential for processing unsecured (plain)
 *         JOSE objects.
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 * @version 2015-06-10
 */
public interface SecurityContext {


}
