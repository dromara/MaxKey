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
 

package org.maxkey.client.oauth.model;

import java.io.*;
import java.util.Map;

import org.maxkey.client.utils.Preconditions;

/**
 * Represents an OAuth 2 Access token.
 * <p>
 * http://tools.ietf.org/html/rfc6749#section-5.1
 *
 * @see <a href="https://tools.ietf.org/html/rfc6749#section-4.1.4">OAuth 2 Access Token Specification</a></p>
 */
public class Token implements Serializable {
	private static final long serialVersionUID = 715000866082812683L;

	/*
	 * for return from Provider
	 */
	private  String rawResponse;
	private  Map<String, Object> responseObject;

	/*
	 * for OAuth 1.0a & OAuth 2.0
	 */
	/**
     * access_token
     * <p>
     * REQUIRED. The access token issued by the authorization server.</p>
     */
	private String access_token;
	
	private String token;
	private String secret;
	/**
     * refresh_token
     * <p>
     * OPTIONAL. The refresh token, which can be used to obtain new access tokens using the same authorization grant as
     * described in http://tools.ietf.org/html/rfc6749#section-6</p>
     */
	private String refresh_token;
	/**
     * expires_in
     * <p>
     * RECOMMENDED. The lifetime in seconds of the access token. For example, the value "3600" denotes that the access
     * token will expire in one hour from the time the response was generated. If omitted, the authorization server
     * SHOULD provide the expiration time via other means or document the default value.</p>
     */
	private String expires_in;

	 /**
     * scope
     * <p>
     * OPTIONAL, if identical to the scope requested by the client; otherwise, REQUIRED. The scope of the access token
     * as described by http://tools.ietf.org/html/rfc6749#section-3.3</p>
     */
    private String scope;
    
	private String signature;
	/*
	 * for OpenID Connect
	 */
	 /**
     * token_type
     * <p>
     * REQUIRED. The type of the token issued as described in http://tools.ietf.org/html/rfc6749#section-7.1 Value is
     * case insensitive.</p>
     */
	private String token_type;
	
	private String id_token;
	
	/**
	 * https://self-issued.me
	 */
	private String sub_jwk;
	/*
	 * for error
	 */
	private String error;
	private String error_description;

	
	/**
	 * 
	 */
	public Token() {
		rawResponse = null;
	}

	/**
	 * Default constructor
	 * 
	 * @param token
	 *            token value. Can't be null.
	 * @param secret
	 *            token secret. Can't be null.
	 */
	public Token(String token, String secret) {
		this(token, secret, null);
	}

	public Token(String token, String secret, String rawResponse) {
		Preconditions.checkNotNull(token, "Token can't be null");
		Preconditions.checkNotNull(secret, "Secret can't be null");

		this.token = token;
		this.secret = secret;
		this.rawResponse = rawResponse;
		this.responseObject = null;
	}

	public Token(String token, String secret, String rawResponse,
			Map<String, Object> responseObject) {
		this.token = token;
		this.secret = secret;
		this.rawResponse = rawResponse;
		this.responseObject = responseObject;
	}

	public String getAccess_token() {
		if(access_token==null||access_token.equals("")){
			access_token=this.token;
		}
		
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Map<String, Object> getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Map<String, Object> responseObject) {
		this.responseObject = responseObject;
	}

	public String getToken() {
		if(token==null){
			token=this.access_token;
		}
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getId_token() {
		return id_token;
	}

	public void setId_token(String id_token) {
		this.id_token = id_token;
	}

	public String getSub_jwk() {
		return sub_jwk;
	}

	public void setSub_jwk(String sub_jwk) {
		this.sub_jwk = sub_jwk;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}

	public void setRawResponse(String rawResponse) {
		this.rawResponse = rawResponse;
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRawResponse() {
		if (rawResponse == null) {
			throw new IllegalStateException(
					"This token object was not constructed by scribe and does not have a rawResponse");
		}
		return rawResponse;
	}

	@Override
	public String toString() {
		return "Token [rawResponse=" + rawResponse + ", responseObject="
				+ responseObject + ", access_token=" + access_token
				+ ", token=" + token + ", secret=" + secret
				+ ", refresh_token=" + refresh_token + ", expires_in="
				+ expires_in + ", signature=" + signature + ", token_type="
				+ token_type + ", id_token=" + id_token + ", sub_jwk="
				+ sub_jwk + ", error=" + error + ", error_description="
				+ error_description + "]";
	}

	/**
	 * Returns true if the token is empty (token = "", secret = "")
	 */
	public boolean isEmpty() {
		return "".equals(this.token) && "".equals(this.secret);
	}

	/**
	 * Factory method that returns an empty token (token = "", secret = "").
	 * 
	 * Useful for two legged OAuth.
	 */
	public static Token empty() {
		return new Token("", "");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Token that = (Token) o;
		return token.equals(that.token) && secret.equals(that.secret);
	}

	@Override
	public int hashCode() {
		return 31 * token.hashCode() + secret.hashCode();
	}
}
