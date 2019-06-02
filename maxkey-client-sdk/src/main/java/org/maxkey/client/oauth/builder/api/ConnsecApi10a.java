package org.maxkey.client.oauth.builder.api;

import org.maxkey.client.oauth.model.Token;

public class ConnsecApi10a extends DefaultApi10a
{
	private static final String DEFAULT_WEB_URL = "http://sso.maxkey.org/maxkey";
	private static final String AUTHORIZATION_URL = DEFAULT_WEB_URL+"/oauth/v10a/authz?oauth_token=%s";
  
  public ConnsecApi10a() {
	  
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
