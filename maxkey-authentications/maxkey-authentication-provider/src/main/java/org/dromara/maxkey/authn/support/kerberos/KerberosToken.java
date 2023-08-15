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


public class KerberosToken {

	private String principal;
	
	private String fullPrincipal;
	
	private String notOnOrAfter;
	
	private String userDomain;

	/**
	 * 
	 */
	public KerberosToken() {
		super();
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getFullPrincipal() {
		return fullPrincipal;
	}

	public void setFullPrincipal(String fullPrincipal) {
		this.fullPrincipal = fullPrincipal;
	}
	
	public String getNotOnOrAfter() {
		return notOnOrAfter;
	}

	public void setNotOnOrAfter(String notOnOrAfter) {
		this.notOnOrAfter = notOnOrAfter;
	}

	public String getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}

	@Override
	public String toString() {
		return "KerberosToken [principal=" + principal + ", fullPrincipal="
				+ fullPrincipal + ", notOnOrAfter=" + notOnOrAfter
				+ ", userDomain=" + userDomain + "]";
	}

}
