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
 

package org.dromara.maxkey.authn.support.kerberos;

public class KerberosProxy {
	
	String userdomain;
	
	String fullUserdomain;
	
	String crypto;
	
	String redirectUri;

	
	/**
	 * 
	 */
	public KerberosProxy() {
		super();
	}

	public String getUserdomain() {
		return userdomain;
	}

	public void setUserdomain(String userdomain) {
		this.userdomain = userdomain.toUpperCase();
	}

	public String getFullUserdomain() {
		return fullUserdomain;
	}

	public void setFullUserdomain(String fullUserdomain) {
		this.fullUserdomain = fullUserdomain.toUpperCase();
	}



	public String getCrypto() {
		return crypto;
	}

	public void setCrypto(String crypto) {
		this.crypto = crypto;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	@Override
	public String toString() {
		return "KerberosProxy [userdomain=" + userdomain + ", fullUserdomain="
				+ fullUserdomain + ", crypto=" + crypto
				+ ", redirectUri=" + redirectUri + "]";
	}
	
	
}
