package org.maxkey.client.oauth.oauth;


import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.http.Response;
import org.maxkey.client.oauth.builder.api.*;
import org.maxkey.client.oauth.model.*;

public class OAuth20ServiceImpl implements OAuthService
{
  private static final String VERSION = "2.0";
  
  private final DefaultApi20 api;
  private final OAuthConfig config;
  
  /**
   * Default constructor
   * 
   * @param api OAuth2.0 api information
   * @param config OAuth 2.0 configuration param object
   */
  public OAuth20ServiceImpl(DefaultApi20 api, OAuthConfig config)
  {
    this.api = api;
    this.config = config;
  }
  
  
  /**
   * Default constructor
   * 
   * @param clientId
   * @param clientSecret
   * @param redirectUri
   */
  public OAuth20ServiceImpl(String clientId, String clientSecret,String redirectUri)
  {
	  this.api=new ConnsecApi20();
	  this.config =new OAuthConfig(clientId,clientSecret,redirectUri);
	 
  }

  /**
   * {@inheritDoc}
   */
  public Token getAccessToken(Token requestToken, Verifier verifier)
  {
    OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
    if(api.getAccessTokenVerb().equals(HttpVerb.GET)){
	    request.addQuerystringParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
	    request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
	    request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
	    request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
	    if(config.hasScope()) request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope());
    }else{
    	request.getBodyParams().add(OAuthConstants.CLIENT_ID, config.getApiKey());
    	request.getBodyParams().add(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
    	request.getBodyParams().add(OAuthConstants.CODE, verifier.getValue());
    	request.getBodyParams().add(OAuthConstants.REDIRECT_URI, config.getCallback());
    	request.getBodyParams().add(OAuthConstants.GRANT_TYPE, api.getGrantType());
    	if(config.hasScope())request.getBodyParams().add(OAuthConstants.SCOPE, config.getScope());
    }
    
    Response response = request.send();
    return api.getAccessTokenExtractor().extract(response.getBody());
  }

  /**
   * {@inheritDoc}
   */
  public Token getRequestToken()
  {
    throw new UnsupportedOperationException("Unsupported operation, please use 'getAuthorizationUrl' and redirect your users there");
  }

  /**
   * {@inheritDoc}
   */
  public String getVersion()
  {
    return VERSION;
  }

  /**
   * {@inheritDoc}
   */
  public void signRequest(Token accessToken, OAuthRequest request)
  {
    request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
  }

  /**
   * {@inheritDoc}
   */
  public String getAuthorizationUrl(Token requestToken)
  {
    return api.getAuthorizationUrl(config);
  }
	@Override
	public void signAccessTokenRequest(Token accessToken, OAuthRequest request) {
		// TODO Auto-generated method stub
		 request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
	}

}
