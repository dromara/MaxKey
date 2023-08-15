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
 

package org.dromara.maxkey.synchronizer.entity;

public class AccessToken {

	int errcode;
	String errmsg;
	String access_token;
	//feishu access_token
	String tenant_access_token;
	String expires_in;

	public AccessToken() {
		super();
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getTenant_access_token() {
		return tenant_access_token;
	}

	public void setTenant_access_token(String tenant_access_token) {
		this.tenant_access_token = tenant_access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccessToken [errcode=");
		builder.append(errcode);
		builder.append(", errmsg=");
		builder.append(errmsg);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", expires_in=");
		builder.append(expires_in);
		builder.append("]");
		return builder.toString();
	}

}
