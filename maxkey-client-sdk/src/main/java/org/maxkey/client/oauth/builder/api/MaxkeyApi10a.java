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
 

package org.maxkey.client.oauth.builder.api;

import org.maxkey.client.oauth.model.Token;

public class MaxkeyApi10a extends DefaultApi10a
{
	private static final String DEFAULT_WEB_URL = "https://sso.maxkey.top/maxkey";
	private static final String AUTHORIZATION_URL = DEFAULT_WEB_URL+"/oauth/v10a/authz?oauth_token=%s";
  
  public MaxkeyApi10a() {
	  
  }

@Override
  public String getAccessTokenEndpoint()
  {
    return DEFAULT_WEB_URL+"/oauth/v10a/access_token";
  }

  @Override
  public String getRequestTokenEndpoint()
  {
    return DEFAULT_WEB_URL+"/oauth/v10a/request_token";
  }
  
  @Override
  public String getAuthorizationUrl(Token requestToken)
  {
    return String.format(AUTHORIZATION_URL, requestToken.getToken());
  }
  
}
