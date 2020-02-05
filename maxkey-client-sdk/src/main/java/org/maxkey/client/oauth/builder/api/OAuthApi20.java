package org.maxkey.client.oauth.builder.api;


import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.oauth.extractors.*;
import org.maxkey.client.oauth.model.*;
import org.maxkey.client.utils.HttpEncoder;

/**
 * OAuth 2.0 api. 
 */
public class OAuthApi20 extends DefaultApi20
{
	//private static final String AUTHORIZE_URL = "https://api.weibo.com/oauth2/authorize?client_id=%s&redirect_uri=%s&response_type=code";
	//private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";
	private String authorizeUrl;
	private String accessTokenUrl;
	private String grantType="authorization_code";
	private String scope="&scope=%s";
	private String accessTokenMethod="POST";
  
  
	public OAuthApi20(String authorizeUrl, String accessTokenUrl) {
		super();
		this.authorizeUrl = authorizeUrl;
		this.accessTokenUrl = accessTokenUrl;
	}
	
	public OAuthApi20(String authorizeUrl, String accessTokenUrl,String accessTokenMethod) {
		super();
		this.authorizeUrl = authorizeUrl;
		this.accessTokenUrl = accessTokenUrl;
		this.accessTokenMethod=accessTokenMethod;
	}
	
	public OAuthApi20(String authorizeUrl, String accessTokenUrl,String grantType,String accessTokenMethod) {
		super();
		this.authorizeUrl = authorizeUrl;
		this.accessTokenUrl = accessTokenUrl;
		this.grantType = grantType;
		this.accessTokenMethod=accessTokenMethod;
	}


	public OAuthApi20(String authorizeUrl, String accessTokenUrl,String grantType, String accessTokenMethod,String scope) {
		super();
		this.authorizeUrl = authorizeUrl;
		this.accessTokenUrl = accessTokenUrl;
		this.grantType = grantType;
		this.accessTokenMethod=accessTokenMethod;
		this.scope = scope;
	}


	 @Override
	  public HttpVerb getAccessTokenVerb(){
		 if(accessTokenMethod!= null &&accessTokenMethod.toUpperCase().equals("POST")){
			 return HttpVerb.POST;
		 }else{
			 return HttpVerb.GET;
		 }
	  }

	  @Override
	 public AccessTokenExtractor getAccessTokenExtractor(){
		  if(accessTokenUrl.indexOf("qq")>-1){
			  return new QQTokenExtractor();
		  }
		  return new GsonJsonTokenExtractor();
	  }

	@Override
	public String getAccessTokenEndpoint(){
		if(accessTokenUrl.indexOf("?")>0){
			return accessTokenUrl+"&grant_type="+grantType;
		}else{
			return accessTokenUrl+"?grant_type="+grantType;
		}
	}

	@Override
	public String getAuthorizationUrl(OAuthConfig config){
	    // Append scope if present
		//dingtalk
		if(authorizeUrl.indexOf("oapi.dingtalk.com")>-1) {
			if (config.hasScope()){
			      return String.format(authorizeUrl+scope, config.getApiKey(), config.getCallback(), HttpEncoder.encode(config.getScope()));
			    }
			    else{
			      return String.format(authorizeUrl, config.getApiKey(), config.getCallback());
			    }
		}else {
			if (config.hasScope()){
		      return String.format(authorizeUrl+scope, config.getApiKey(), HttpEncoder.encode(config.getCallback()), HttpEncoder.encode(config.getScope()));
		    }
		    else{
		      return String.format(authorizeUrl, config.getApiKey(), HttpEncoder.encode(config.getCallback()));
		    }
		}
	}

	public String getAuthorizeUrl() {
		return authorizeUrl;
	}
	
	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}
	
	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}
	
	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}
	
	public String getGrantType() {
		return grantType;
	}
	
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}


	@Override
	public String toString() {
		return "OAuthApi20 [authorizeUrl=" + authorizeUrl + ", accessTokenUrl="
				+ accessTokenUrl + ", grantType=" + grantType + ", scope="
				+ scope + "]";
	}
  
}
