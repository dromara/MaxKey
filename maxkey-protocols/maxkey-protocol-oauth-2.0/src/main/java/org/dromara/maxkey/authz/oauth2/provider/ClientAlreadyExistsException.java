/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dromara.maxkey.authz.oauth2.provider;

/**
 * Exception indicating that a client registration already exists (e.g. if someone tries to create a duplicate).
 * 
 * @author Dave Syer
 * 
 */
@SuppressWarnings("serial")
public class ClientAlreadyExistsException extends ClientRegistrationException {

    public ClientAlreadyExistsException(String msg) {
        super(msg);
    }

    public ClientAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
