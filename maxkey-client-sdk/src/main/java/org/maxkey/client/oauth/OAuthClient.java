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
 

package org.maxkey.client.oauth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.maxkey.client.http.AuthorizationHeader;
import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.http.Response;
import org.maxkey.client.oauth.domain.OIDCUserInfo;
import org.maxkey.client.oauth.domain.UserInfo;
import org.maxkey.client.oauth.model.OAuthConstants;
import org.maxkey.client.oauth.model.OAuthRequest;
import org.maxkey.client.oauth.model.Token;
import org.maxkey.client.utils.JsonUtils;
import org.maxkey.client.utils.Preconditions;


public class OAuthClient {
	
	private   static  Log log  =  LogFactory.getLog(OAuthClient. class );
	private static final String DEFAULT_WEB_URL = "http://sso.maxkey.org/maxkey";
	
	public static String OAUTH_V20_USERINFO_URI=DEFAULT_WEB_URL+"/api/oauth/v20/me";
	
	public static String OAUTH_V10A_USERINFO_URI=DEFAULT_WEB_URL+"/api/oauth/v10a/me";
	
	public static String OPENID_CONNECT_V10A_USERINFO_URI=DEFAULT_WEB_URL+"/api/connect/v10/userinfo";
	
	//action method
	private HttpVerb method = HttpVerb.GET;
	//request Service 
	private OAuthRequest request;
	
	
	public OAuthClient(String url) {
		Preconditions.checkEmptyString(url, "Invalid request url");
		
		request = new OAuthRequest(method, url);
	}
	
	public OAuthClient(String url,String  accessToken) {
		
		Preconditions.checkEmptyString(url, "Invalid request url");
		
		request = new OAuthRequest(method, url);
		
		addParameter(OAuthConstants.ACCESS_TOKEN, accessToken);
	}
	
	
	/**
	 * 鏋勯�犳柟娉�
	 * 
	 * @param method 璇锋眰鏂规硶
	 * @param url 璇锋眰url
	 */
	public OAuthClient(String url,HttpVerb method) {
		Preconditions.checkEmptyString(url, "Invalid request url");
		if(method != null) {
			this.method = method;
		}
		request = new OAuthRequest(method, url);
	}
	
	/**
	 * 灏哸ccess token闄勫姞鍒拌姹傚弬鏁颁腑
	 * 
	 * @param accessToken
	 */
	public void signAccessToken(Token accessToken) {
		Preconditions.checkNotNull(request, "OAuthRequest is null");
		addParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
	}
	
	/**
	 * 娣诲姞璇锋眰鍙傛暟
	 * 
	 * @param name 鍙傛暟鍚�
	 * @param value 鍙傛暟鍊�
	 */
	public void addParameter(String name,String value) {
		Preconditions.checkEmptyString(name, "parameter name is null");
		Preconditions.checkNotNull(request, "OAuthRequest is null");
		if(HttpVerb.GET == method) {
			request.addQuerystringParameter(name, value);
		} else if(HttpVerb.POST == method) {
			request.addBodyParameter(name, value);
		}
	}
	
	/**
	 * 娣诲姞璇锋眰Header
	 * 
	 * @param name 鍙傛暟鍚�
	 * @param value 鍙傛暟鍊�
	 */
	public void addHeader(String name,String value) {
		Preconditions.checkEmptyString(name, "parameter name is null");
		Preconditions.checkNotNull(request, "OAuthRequest is null");
		request.addHeader(name, value);
	}
	
	/**
	 * 鍙戦�佽姹傦紝鑾峰彇杩斿洖鐨勬暟鎹�
	 * 
	 * @return Response 鏈嶅姟绔繑鍥炵殑鏁版嵁
	 */
	public Response execute() {
		return request.send();
	}
	
	public Token requestAccessToken(){
		String tokenString=execute().getBody();
		Token token =JsonUtils.gson2Object(tokenString, Token.class);
		log.debug("Request token : "+token);
		return token;
	}
	
	public void addBasicAuthorization(String username,String password) {
		Preconditions.checkEmptyString(username, "parameter username is null");
		Preconditions.checkEmptyString(password, "parameter password is null");
		Preconditions.checkNotNull(request, "OAuthRequest is null");
		request.addHeader(AuthorizationHeader.AUTHORIZATION_HEADERNAME, AuthorizationHeader.createBasic(username, password));
	}
	
	public void addBearerAuthorization(String bearer) {
		Preconditions.checkEmptyString(bearer, "parameter bearer is null");
		Preconditions.checkNotNull(request, "OAuthRequest is null");
		request.addHeader(AuthorizationHeader.AUTHORIZATION_HEADERNAME, AuthorizationHeader.createBearer(bearer));
	}
	
	public UserInfo getUserInfo(String  accessToken){
		
		addParameter(OAuthConstants.ACCESS_TOKEN, accessToken);
		
		Response response = execute();
		
		log.debug("Request UserInfo : "+response.getBody());
		
		UserInfo userInfo  = (UserInfo) JsonUtils.gson2Object(response.getBody(), UserInfo.class);
		
		userInfo.setResponseString(response.getBody());
		
		if(userInfo.getError() != null && !"".equals(userInfo.getError().trim())) {
		}
		
		log.debug("UserInfo : "+userInfo);
		return userInfo;
	}
	
	public OIDCUserInfo getOIDCUserInfo(String  accessToken){
		
		addHeader("Authorization", accessToken);
		
		Response response = execute();
		
		log.debug("Request OIDCUserInfo : "+response.getBody());
		
		OIDCUserInfo userInfo  = (OIDCUserInfo) JsonUtils.gson2Object(response.getBody(), OIDCUserInfo.class);
		
		userInfo.setResponseString(response.getBody());
		
		if(userInfo.getError() != null && !"".equals(userInfo.getError().trim())) {
		}
		
		log.debug("UserInfo : "+userInfo);
		return userInfo;
	}
	
	  /**
	   * set REST Content
	   *
	   * @param content
	   */
	  public void addRestContent(String content)
	  {
	    this.request.addRestContent(content) ;
	  }
	  
	  /**
	   * set REST Content
	   *
	   * @param content
	   */
	  public void addRestObject(Object content)
	  {
		  this.request.addRestObject(content);
	  }
	  
	  /**
	   * set REST Content
	   *
	   * @param content
	   */
	  public void addRestContent(byte[] content)
	  {
		  this.request.addRestContent(content);
	  }
	
}
