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
 

package org.dromara.maxkey.authz.oauth2.common;

public class OAuth2Constants {
	
	public static final class PARAMETER{
		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String CLIENT_ID              = "client_id";
		
		public static final String CLIENT_SECRET          = "client_secret";
		
		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String STATE                  = "state";

		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String SCOPE                  = "scope";

		public static final String CODE                   = "code";
		
		
		
		public static final String TOKEN                  = "token";
		
		public static final String TOKEN_TYPE             = "token_type";
		
		public static final String EXPIRES_IN             = "expires_in";
		
		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String REDIRECT_URI           = "redirect_uri";

		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String RESPONSE_TYPE          = "response_type";

		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String USER_OAUTH_APPROVAL    = "user_oauth_approval";

		/**
		 * Constant to use as a prefix for scope approval
		 */
		public static final String SCOPE_PREFIX           = "scope.";

		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String GRANT_TYPE                     = "grant_type";
		public static final String GRANT_TYPE_CODE                = "code";
		public static final String GRANT_TYPE_PASSWORD            = "password";
		public static final String GRANT_TYPE_IMPLICIT            = "implicit";
		public static final String GRANT_TYPE_AUTHORIZATION_CODE  = "authorization_code";
		public static final String GRANT_TYPE_CLIENT_CREDENTIALS  = "client_credentials";
		public static final String GRANT_TYPE_REFRESH_TOKEN		  = "refresh_token";
		
		
		public static final String ACCESS_TOKEN           = "access_token";
		
		public static final String APPROVAL_PROMPT        = "approval_prompt";
		
		//https://datatracker.ietf.org/doc/html/rfc7636 PKCE
		//Proof Key for Code Exchange by OAuth Public Clients
		public static final String CODE_CHALLENGE         = "code_challenge" ;
		
		public static final String CODE_CHALLENGE_METHOD  = "code_challenge_method" ;
		
		public static final String CODE_VERIFIER          = "code_verifier" ;
		
		
		 
	}
	
	public static class PKCE_TYPE{
	    public static final String PKCE_TYPE_YES          = "YES" ;
        public static final String PKCE_TYPE_NO           = "NO" ; 
	}
	
	public static class CODE_CHALLENGE_METHOD_TYPE{
        public static final String PLAIN          = "plain" ;
        public static final String S256           = "S256" ; 
    }
	
	public static class ENDPOINT{
		
		public static final  String ENDPOINT_BASE                      = "/authz/oauth/v20";
		
		public static final  String ENDPOINT_AUTHORIZE                 = ENDPOINT_BASE + "/authorize";
		
		public static final  String ENDPOINT_TOKEN                     = ENDPOINT_BASE + "/token";
		
		public static final  String ENDPOINT_CHECK_TOKEN               = ENDPOINT_BASE + "/check_token";
		
		public static final  String ENDPOINT_TOKEN_KEY                 = ENDPOINT_BASE + "/token_key";
		
		public static final  String ENDPOINT_APPROVAL_CONFIRM          = ENDPOINT_BASE + "/approval_confirm";
		
		public static final  String ENDPOINT_ERROR                     = ENDPOINT_BASE + "/error";
		
		public static final  String ENDPOINT_USERINFO                  = "/api/oauth/v20/me";
		
		public static final  String ENDPOINT_OPENID_CONNECT_USERINFO   = "/api/connect/v10/userinfo";
		
		public static final  String ENDPOINT_TENCENT_IOA_AUTHORIZE	  =	"/oauth2/authorize";
		public static final  String ENDPOINT_TENCENT_IOA_TOKEN	  	  =	"/oauth2/token";
		
	}
}
