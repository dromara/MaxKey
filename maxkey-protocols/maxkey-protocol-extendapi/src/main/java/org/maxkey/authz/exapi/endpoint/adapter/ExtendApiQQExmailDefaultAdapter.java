package org.maxkey.authz.exapi.endpoint.adapter;

import java.util.HashMap;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.client.oauth.OAuthClient;
import org.maxkey.client.oauth.model.Token;
import org.maxkey.client.utils.JsonUtils;
import org.maxkey.domain.ExtraAttrs;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Apps;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class ExtendApiQQExmailDefaultAdapter extends AbstractAuthorizeAdapter {
	final static Logger _logger = LoggerFactory.getLogger(ExtendApiQQExmailDefaultAdapter.class);
	static String login_url_template="https://exmail.qq.com/cgi-bin/login?fun=bizopenssologin&method=bizauth&agent=%s&user=%s&ticket=%s";
	static String token_uri="https://exmail.qq.com/cgi-bin/token";
	static String authkey_uri="http://openapi.exmail.qq.com:12211/openapi/mail/authkey";
	
	@Override
	public String generateInfo(UserInfo userInfo,Object app) {
		return null;
	}

	@Override
	public String encrypt(String data, String algorithmKey, String algorithm) {
		return null;
	}

	@Override
	public ModelAndView authorize(UserInfo userInfo, Object app, String data,ModelAndView modelAndView) {
		Apps details=(Apps)app;
		//extraAttrs from Applications
		ExtraAttrs extraAttrs=null;
		if(details.getIsExtendAttr()==1){
			extraAttrs=new ExtraAttrs(details.getExtendAttr());
		}
		OAuthClient tokenRestClient=new OAuthClient(token_uri);
		tokenRestClient.addParameter("grant_type", "client_credentials");
		tokenRestClient.addBasicAuthorization(details.getPrincipal(), details.getCredentials());
		Token token =tokenRestClient.requestAccessToken();
		_logger.debug(""+token);
		
		OAuthClient authkeyRestClient=new OAuthClient(authkey_uri);
		authkeyRestClient.addBearerAuthorization(token.getAccess_token());
		authkeyRestClient.addParameter("Alias", details.getAppUser().getRelatedUsername());
		
		HashMap<String, String> authKey=JsonUtils.gson2Object(authkeyRestClient.execute().getBody(), HashMap.class);
		_logger.debug("authKey : "+authKey);
		
		String redirec_uri=String.format(login_url_template,details.getPrincipal(),details.getAppUser().getRelatedUsername(),authKey.get("auth_key"));
		_logger.debug("redirec_uri : "+redirec_uri);
		return WebContext.redirect(redirec_uri);
	}

}
