/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.dromara.maxkey.authz.oauth2.common.exceptions;

import java.util.Set;

import org.dromara.maxkey.authz.oauth2.common.util.OAuth2Utils;

/**
 * Exception representing insufficient scope in a token when a request is handled by a Resource Server. It is akin to an
 * {@link AccessDeniedException} and should result in a 403 (FORBIDDEN) HTTP status.
 * 
 * @author Dave Syer
 */
@SuppressWarnings("serial")
public class InsufficientScopeException extends OAuth2Exception {

	public InsufficientScopeException(String msg, Set<String> validScope) {
		this(msg);
		addAdditionalInformation("scope", OAuth2Utils.formatParameterList(validScope));
	}

	public InsufficientScopeException(String msg) {
		super(msg);
	}

	@Override
	public int getHttpErrorCode() {
		return 403;
	}

	@Override
	public String getOAuth2ErrorCode() {
		// Not defined in the spec, so not really an OAuth2Exception
		return "insufficient_scope";
	}

}
