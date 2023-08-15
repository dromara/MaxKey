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

/**
 * Exception thrown when a user was unable to authenticate.
 * 
 * @author Dave Syer
 */
@SuppressWarnings("serial")
public class UnauthorizedUserException extends OAuth2Exception {

	public UnauthorizedUserException(String msg, Throwable t) {
		super(msg, t);
	}

	public UnauthorizedUserException(String msg) {
		super(msg);
	}

	@Override
	public int getHttpErrorCode() {
		// The spec says this can be unauthorized
		return 401;
	}

	@Override
	public String getOAuth2ErrorCode() {
		// Not in the spec
		return "unauthorized_user";
	}
}
