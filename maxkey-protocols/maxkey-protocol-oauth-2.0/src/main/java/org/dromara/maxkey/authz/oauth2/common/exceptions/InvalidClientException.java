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
 * Exception thrown when a client was unable to authenticate.
 *
 * @author Ryan Heaton
 * @author Dave Syer
 */
@SuppressWarnings("serial")
public class InvalidClientException extends ClientAuthenticationException {

	public InvalidClientException(String msg) {
		super(msg);
	}

	@Override
	public int getHttpErrorCode() {
		return 401;
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_client";
	}
}
