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
 

package org.maxkey.client.oauth.test;

import org.maxkey.client.http.Response;
import org.maxkey.client.oauth.builder.api.MaxkeyPasswordApi20;
import org.maxkey.client.oauth.model.OAuthConfig;
import org.maxkey.client.oauth.model.Token;
import org.maxkey.client.oauth.oauth.OAuthPasswordService;

public class MaxkeyPasswordDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String accessTokenUrl="http://localhost:8080/opensec/oauth/v20/token";
		String clientId = "e6bfadbfc1d64d0e9140a716548c35db";
		String clientSerect = "e6bfadbfc1d64d0e9140a716548c35db";
		
		String callback = "http://localhost:8080/oauth1demo/oauth20callback.jsp";
		String responseType ="token";
		String approvalprompt = "auto";
		OAuthConfig oauthServiceConfig=new OAuthConfig(clientId,clientSerect,callback);
	
		MaxkeyPasswordApi20	ConnsecPasswordApi20=new MaxkeyPasswordApi20(accessTokenUrl);
		
		OAuthPasswordService oAuthPasswordService=new OAuthPasswordService(oauthServiceConfig,ConnsecPasswordApi20);
		Token accessToken = null;
		Response response = null;
		accessToken = oAuthPasswordService.getAccessToken("6ac07a3d-b935-43f2-a693-9ce49b6695b7", "1qaz2wsx"); 

	}
	

}
