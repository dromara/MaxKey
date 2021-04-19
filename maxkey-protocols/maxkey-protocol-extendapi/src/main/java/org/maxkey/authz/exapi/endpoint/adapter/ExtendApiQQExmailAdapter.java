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
 

package org.maxkey.authz.exapi.endpoint.adapter;

import java.util.HashMap;

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.client.oauth.OAuthClient;
import org.maxkey.client.oauth.model.Token;
import org.maxkey.entity.ExtraAttrs;
import org.maxkey.entity.UserInfo;
import org.maxkey.entity.apps.Apps;
import org.maxkey.util.HttpsTrusts;
import org.maxkey.util.JsonUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
/**
 * https://exmail.qq.com/qy_mng_logic/doc
 * exmail sso
 * @author shimingxy
 *
 */
public class ExtendApiQQExmailAdapter extends AbstractAuthorizeAdapter {
	final static Logger _logger = LoggerFactory.getLogger(ExtendApiQQExmailAdapter.class);
	//https://exmail.qq.com/qy_mng_logic/doc#10003
	static String TOKEN_URI="https://api.exmail.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
	//https://exmail.qq.com/qy_mng_logic/doc#10036
	static String AUTHKEY_URI="https://api.exmail.qq.com/cgi-bin/service/get_login_url?access_token=%s&userid=%s";
	
	@Override
	public String generateInfo(SigninPrincipal authentication,UserInfo userInfo,Object app) {
		return null;
	}

	@Override
	public String encrypt(String data, String algorithmKey, String algorithm) {
		return null;
	}

	@Override
	public ModelAndView authorize(UserInfo userInfo, Object app, String data,ModelAndView modelAndView) {
		HttpsTrusts.beforeConnection();
		
		Apps details=(Apps)app;
		//extraAttrs from Applications
		ExtraAttrs extraAttrs=null;
		if(details.getIsExtendAttr()==1){
			extraAttrs=new ExtraAttrs(details.getExtendAttr());
		}
		OAuthClient tokenRestClient=new OAuthClient(
				String.format(TOKEN_URI,details.getPrincipal(),details.getCredentials()));
		Token token =tokenRestClient.requestAccessToken();
		_logger.debug(""+token);
		
		OAuthClient authkeyRestClient=new OAuthClient(
				String.format(AUTHKEY_URI,token.getAccess_token(),details.getAppUser().getRelatedUsername()));
		
		HashMap<String, String> authKey=JsonUtils.gson2Object(authkeyRestClient.execute().getBody(), HashMap.class);
		_logger.debug("authKey : "+authKey);
		
		String redirec_uri=authKey.get("login_url");
		_logger.debug("redirec_uri : "+redirec_uri);
		return WebContext.redirect(redirec_uri);
	}

}
