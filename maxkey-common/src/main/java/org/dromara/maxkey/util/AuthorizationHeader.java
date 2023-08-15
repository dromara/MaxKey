/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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

package org.dromara.maxkey.util;

public class AuthorizationHeader {

	public static class Credential {

		public static final String BASIC = "Basic ";

		public static final String BEARER = "Bearer ";
	}

	String credentialType = Credential.BASIC;
	String username;
	String credential;
	String authorization;

	public AuthorizationHeader(String bearer) {
		super();
		this.credential = bearer;
		this.credentialType = Credential.BEARER;
	}

	public AuthorizationHeader(String username, String credential) {
		super();
		this.username = username;
		this.credential = credential;
	}

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public String transform() {
		if (credentialType.equalsIgnoreCase(Credential.BASIC)) {
			return AuthorizationHeaderUtils.createBasic(username, credential);
		} else {
			return AuthorizationHeaderUtils.createBearer(credential);
		}
	}

	public boolean isBasic() {
		return credentialType.equals(Credential.BASIC) ? true : false;
	}

	@Override
	public String toString() {
		return "AuthorizationHeaderCredential [credentialType=" + credentialType + ", username=" + username
				+ ", credential=" + credential + "]";
	}

}
