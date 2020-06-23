package org.maxkey.client.oauth.builder.api;

import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.oauth.extractors.AccessTokenExtractor;
import org.maxkey.client.oauth.extractors.GsonJsonTokenExtractor;
import org.maxkey.client.oauth.model.OAuthConfig;
import org.maxkey.client.utils.HttpEncoder;
import org.maxkey.client.utils.Preconditions;

public class MaxkeyApi20 extends DefaultApi20 {
	//approval_prompt:force or auto
	private static final String DEFAULT_WEB_URL = "https://sso.maxkey.top/maxkey";
	
	private static final String AUTHORIZATION_URL = "%s/oauth/v20/authorize?client_id=%s&response_type=code&redirect_uri=%s&approval_prompt=auto";
    
	private static final String SCOPED_AUTHORIZE_URL = String.format("%s&scope=%%s", AUTHORIZATION_URL);
    
    public MaxkeyApi20() {
   
	}


	@Override
    public String getAccessTokenEndpoint() {
    	return getWebUrl() + "/oauth/v20/token?grant_type=authorization_code";
    }

    
    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
    	Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Secure does not support OOB");
    	if(config.hasScope()) {
    		return String.format(SCOPED_AUTHORIZE_URL, getWebUrl(),config.getApiKey(), HttpEncoder.encode(config.getCallback()), HttpEncoder.encode(config.getScope()));
    	} else {
    		return String.format(AUTHORIZATION_URL, getWebUrl(),config.getApiKey(), HttpEncoder.encode(config.getCallback()));
    	}
    }
    
    

    @Override
	public HttpVerb getAccessTokenVerb() {
		return HttpVerb.POST;
	}


	@Override
    public AccessTokenExtractor getAccessTokenExtractor() {
    	return new GsonJsonTokenExtractor();
    }
    
    private String getWebUrl() {
    	String webUrl = null;
    	if(webUrl == null || "".equals(webUrl)) {
    		webUrl = DEFAULT_WEB_URL;
    	}
    	return webUrl;
    }


	@Override
	public String getGrantType() {
		// TODO Auto-generated method stub
		return "authorization_code";
	}
}
