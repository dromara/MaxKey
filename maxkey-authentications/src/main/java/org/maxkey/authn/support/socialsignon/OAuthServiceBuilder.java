package org.maxkey.authn.support.socialsignon;

import org.maxkey.authn.support.socialsignon.service.SocialSignOnProvider;
import org.maxkey.client.http.SignatureType;
import org.maxkey.client.oauth.builder.ServiceBuilder;
import org.maxkey.client.oauth.builder.api.Api;
import org.maxkey.client.oauth.builder.api.OAuthApi20;
import org.maxkey.client.oauth.oauth.OAuthService;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OAuthServiceBuilder {
	private static Logger _logger = LoggerFactory.getLogger(OAuthServiceBuilder.class);
	
	private SocialSignOnProvider socialSignOnProvider;
	
	private Api api;
	
	
	/**
	 * 
	 */
	public OAuthServiceBuilder() {

	}


	/**
	 * @param socialSignOnProvider
	 */
	public OAuthServiceBuilder(SocialSignOnProvider socialSignOnProvider) {
		
		this.socialSignOnProvider = socialSignOnProvider;
		String callbackUrl=WebContext.getHttpContextPath()+ "/logon/oauth20/callback/"+socialSignOnProvider.getProvider();
		
		socialSignOnProvider.setCallBack(callbackUrl);
		
		api  = new OAuthApi20(socialSignOnProvider.getAuthorizeUrl(),
				socialSignOnProvider.getAccessTokenUrl(),
				socialSignOnProvider.getAccessTokenMethod());
		
		_logger.debug("api : "+api);
	}


	public OAuthService builderOAuthService() {
		
		if(socialSignOnProvider.getScope()==null||socialSignOnProvider.getScope().equals("")){
			return new ServiceBuilder().provider(api)
								.apiKey(socialSignOnProvider.getClientId())
							    .apiSecret(socialSignOnProvider.getClientSecret())
							    .callback(socialSignOnProvider.getCallBack())
							    .signatureType(SignatureType.QueryString)
							    .debug()
							    .build();
		}else{
			return new ServiceBuilder().provider(api)
								.apiKey(socialSignOnProvider.getClientId())
							    .apiSecret(socialSignOnProvider.getClientSecret())
							    .scope(socialSignOnProvider.getScope())
							    .callback(socialSignOnProvider.getCallBack())
							    .signatureType(SignatureType.QueryString)
							    .debug()
							    .build();
		}
	}

	

	public SocialSignOnProvider getSocialSignOnProvider() {
		return socialSignOnProvider;
	}


	public void setSocialSignOnProvider(SocialSignOnProvider socialSignOnProvider) {
		this.socialSignOnProvider = socialSignOnProvider;
	}


	public Api getApi() {
		return api;
	}


	public void setApi(Api api) {
		this.api = api;
	}
	
	
}
