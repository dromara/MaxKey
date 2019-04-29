/**
 * 
 */
package org.maxkey.authn.support.socialsignon;

import java.util.HashMap;
import java.util.Map;

import org.maxkey.authn.support.socialsignon.service.SocialSignOnProvider;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.maxkey.authn.support.socialsignon.service.SocialSignOnUserTokenService;
import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.http.Response;
import org.maxkey.client.oauth.model.OAuthRequest;
import org.maxkey.client.oauth.model.Token;
import org.maxkey.client.oauth.model.Verifier;
import org.maxkey.client.oauth.oauth.OAuthService;
import org.maxkey.util.JsonUtils;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Crystal.Sea
 *
 */
public class AbstractSocialSignOnEndpoint {
	final static Logger _logger = LoggerFactory.getLogger(AbstractSocialSignOnEndpoint.class);

	protected final static String SOCIALSIGNON_SESSION_REDIRECT_URI="socialsignon_session_redirect_uri";
	
	protected final static String SOCIALSIGNON_REDIRECT_URI="redirect_uri";
	
	public  final static String SOCIALSIGNON_TYPE_SESSION="socialsignon_type_session";
	
	public  final static String SOCIALSIGNON_OAUTH_SERVICE_SESSION="socialsignon_oauth_service_session";
	
	public  final static String SOCIALSIGNON_PROVIDER_SESSION="socialsignon_provider_session";
	
	
	public final static class SOCIALSIGNON_TYPE{
		public  final static String SOCIALSIGNON_TYPE_LOGON="socialsignon_type_logon";
		public  final static String SOCIALSIGNON_TYPE_BIND="socialsignon_type_bind";
	}
	
	protected Token accessToken;
	
	protected SocialSignOnProvider socialSignOnProvider;
	
	protected OAuthService oauthService;
	
	protected String accountJsonString;
	
	protected String accountId;
	
	protected String provider;
	
	@Autowired
	protected SocialSignOnProviderService socialSignOnProviderService;
	
	@Autowired
	protected SocialSignOnUserTokenService socialSignOnUserTokenService;
	
	
 	
  	protected OAuthService buildOAuthService(String provider){
  		
		SocialSignOnProvider socialSignOnProvider = socialSignOnProviderService.get(provider);
		_logger.debug("socialSignOn Provider : "+socialSignOnProvider);
		
		if(socialSignOnProvider!=null){
			OAuthServiceBuilder oAuthServiceBuilder=new OAuthServiceBuilder(socialSignOnProvider);
			oauthService=oAuthServiceBuilder.builderOAuthService();
			WebContext.setAttribute(SOCIALSIGNON_OAUTH_SERVICE_SESSION, socialSignOnProvider);
			WebContext.setAttribute(SOCIALSIGNON_PROVIDER_SESSION, oauthService);
			return oauthService;
		}
		return null;
	}
  	
  	/**
  	 * get accessToken
  	 * @param service
  	 * @return
  	 */
  	protected Token getAccessToken() {
  		
  		socialSignOnProvider=(SocialSignOnProvider)WebContext.getAttribute(SOCIALSIGNON_OAUTH_SERVICE_SESSION);
  		oauthService=(OAuthService)WebContext.getAttribute(SOCIALSIGNON_PROVIDER_SESSION);
  		String oauthVerifier = WebContext.getRequest().getParameter(socialSignOnProvider.getVerifierCode());
  		WebContext.removeAttribute(SOCIALSIGNON_OAUTH_SERVICE_SESSION);
  		WebContext.removeAttribute(SOCIALSIGNON_PROVIDER_SESSION);
		if(StringUtils.isNullOrBlank(socialSignOnProvider.getVerifierCode()))
			return null;
		// getting access token
		Verifier verifier = new Verifier(oauthVerifier);
		this.accessToken=oauthService.getAccessToken(null, verifier);
		
		return accessToken;
  	}
  	
  	protected String requestAccountJson() {
  		OAuthRequest oauthRequest = new OAuthRequest(HttpVerb.GET, this.convertAccountUrl(socialSignOnProvider.getAccountUrl(),socialSignOnProvider.getProvider(), accessToken));
  		oauthService.signRequest(accessToken, oauthRequest);
		Response oauthResponse = oauthRequest.send();
		accountJsonString=oauthResponse.getBody();
		_logger.debug("requestAccountJson : "+accountJsonString);
		return accountJsonString;
  	}
  	
  	

 	@SuppressWarnings("unchecked")
	protected String  getAccountId() {
 		//if(StringUtils.isNullOrBlank(accountJsonString)) {
 			requestAccountJson();
 		//}
 			
		if(this.provider.equals("qq")){
 			accountJsonString=accountJsonString.substring(accountJsonString.indexOf("{"), accountJsonString.indexOf("}")+1);
 		}
 		Map<String,Object> map = new HashMap<String,Object>();
 		
 		map=(HashMap<String,Object>)JsonUtils.json2Object(accountJsonString, map);
 		if(this.provider.equals("qqweibo")){
 			if(accessToken.getResponseObject().get(socialSignOnProvider.getAccountId())!=null){
 	 			accountId=accessToken.getResponseObject().get(socialSignOnProvider.getAccountId()).toString();
 	 		}
 		}else if(this.provider.equals("qq")){
 			accountId=map.get(socialSignOnProvider.getAccountId()).toString();

 		}else{
	 		if(map.get(socialSignOnProvider.getAccountId())!=null){
	 			accountId=map.get(socialSignOnProvider.getAccountId()).toString();
	 		}
 		}
 		
 		
 		_logger.debug("getAccountId : "+accountId);
 		return accountId;
 	}
  	
  	private String convertAccountUrl(String accountUrl,String provider,Token accessToken) {
  		if("sinaweibo".equals(provider)) {
  			if(null!=accessToken.getResponseObject()) {
  				Object uid = accessToken.getResponseObject().get("uid");
  				accountUrl = this.convertUrl(accountUrl, "uid", uid == null  ? "" : uid.toString());
  			}
  		}
  		return accountUrl;
  	}
  	
  	private String convertUrl(String url,String paramName,String paramVal) {
		StringBuilder sb = new StringBuilder(url);
		if (url.indexOf('?') < 0) {
			sb.append('?');
		}
		else {
			sb.append('&');
		}
		sb.append(paramName+"=").append(paramVal);
		return sb.toString();
  	}

}
