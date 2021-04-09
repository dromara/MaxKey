package org.maxkey.authz.oauth2.common;

public class OAuth2Constants {
	
	public static final class PARAMETER{
		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String CLIENT_ID = "client_id";
		
		public static final String CLIENT_SECRET = "client_secret";
		
		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String STATE = "state";

		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String SCOPE = "scope";

		public static final String CODE = "code";
		
		public static final String EXPIRES_IN = "expires_in";
		
		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String REDIRECT_URI = "redirect_uri";

		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String RESPONSE_TYPE = "response_type";

		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String USER_OAUTH_APPROVAL = "user_oauth_approval";

		/**
		 * Constant to use as a prefix for scope approval
		 */
		public static final String SCOPE_PREFIX = "scope.";

		/**
		 * Constant to use while parsing and formatting parameter maps for OAuth2 requests
		 */
		public static final String GRANT_TYPE = "grant_type";
		
		public static final String ACCESS_TOKEN = "access_token";
	}
	
	public static class ENDPOINT{
		
		public final static String ENDPOINT_BASE = "/authz/oauth/v20";
		
		public final static String ENDPOINT_AUTHORIZE = ENDPOINT_BASE + "/authorize";
		
		public final static String ENDPOINT_TOKEN = ENDPOINT_BASE + "/token";
		
		public final static String ENDPOINT_CHECK_TOKEN = ENDPOINT_BASE + "/check_token";
		
		public final static String ENDPOINT_TOKEN_KEY = ENDPOINT_BASE + "/token_key";
		
		public final static String ENDPOINT_APPROVAL_CONFIRM = ENDPOINT_BASE + "/approval_confirm";
		
		public final static String ENDPOINT_ERROR = ENDPOINT_BASE + "/error";
		
		public final static String ENDPOINT_USERINFO = "/api/oauth/v20/me";
		
		public final static String ENDPOINT_OPENID_CONNECT_USERINFO = "/api/connect/v10/userinfo";
		
	}
}
