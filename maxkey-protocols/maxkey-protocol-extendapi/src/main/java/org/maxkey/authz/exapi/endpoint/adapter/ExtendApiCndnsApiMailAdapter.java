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

import java.time.Instant;
import java.util.HashMap;

import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.oauth.OAuthClient;
import org.maxkey.client.oauth.model.Token;
import org.maxkey.crypto.DigestUtils;
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
public class ExtendApiCndnsApiMailAdapter extends AbstractAuthorizeAdapter {
	final static Logger _logger = LoggerFactory.getLogger(ExtendApiCndnsApiMailAdapter.class);
	//sign no  parameter
	//sign=md5(action=getDomainInfo&appid=***&time=1579736456 + md5(token))
	//sign with parameter
	//sign=md5(action=getUserInfo&appid=***&email=admin@maxkey.org&time=1579736456 + md5(token))

	static String SIGN_STRING 		="action=getDomainInfo&appid=%s%s";
	
	static String SIGN_EMAIL_STRING ="action=getUserInfo&appid=%s&email=%s&time=%s%s";
	
	static String ADMIN_AUTHKEY_URI	="https://www.cndnsapi.com/email/clientmanagement?action=getDomailUrl&appid=%s&sign=%s&time=%s";
	
	static String AUTHKEY_URI		="https://www.cndnsapi.com/email/clientmanagement?action=getWebMailUrl&appid=%s&sign=%s&time=%s";
	
	
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
		String action = "getWebMailUrl";
		String domain = null;
		if(details.getIsExtendAttr()==1){
			extraAttrs=new ExtraAttrs(details.getExtendAttr());
			if(extraAttrs.get("action")==null || extraAttrs.get("action").equalsIgnoreCase("getWebMailUrl")) {
				action = "getWebMailUrl";
			}else if(extraAttrs.get("action").equalsIgnoreCase("getDomailUrl")){
				action = "getDomailUrl";
				domain = extraAttrs.get("domain");
			}
		}
		
		String timestamp  = ""+Instant.now().getEpochSecond();
		
		String tokenMd5 =DigestUtils.md5Hex(details.getCredentials());
		HashMap<String,String > requestParamenter =new HashMap<String,String >();
		String redirec_uri = "";
		if(action.equalsIgnoreCase("getDomailUrl")) {
			String sign =DigestUtils.md5Hex
					(String.format(
							SIGN_STRING,
							details.getPrincipal(),timestamp,tokenMd5));
			requestParamenter.put("domain", domain);
			OAuthClient authkeyRestClient=new OAuthClient(
					String.format(ADMIN_AUTHKEY_URI,details.getPrincipal(),sign,timestamp),HttpVerb.POST);
			authkeyRestClient.addRestObject(requestParamenter);
			
			HashMap<String, String> authKey=JsonUtils.gson2Object(authkeyRestClient.execute().getBody(), HashMap.class);
			redirec_uri=authKey.get("adminUrl");
			
		}else {
			String sign =DigestUtils.md5Hex
					(String.format(
							SIGN_EMAIL_STRING,
							details.getPrincipal(),userInfo.getEmail(),timestamp,tokenMd5));
			requestParamenter.put("email", userInfo.getWorkEmail());
			OAuthClient authkeyRestClient=new OAuthClient(
					String.format(AUTHKEY_URI,details.getPrincipal(),sign,timestamp),HttpVerb.POST);
			authkeyRestClient.addRestObject(requestParamenter);
			
			HashMap<String, String> authKey=JsonUtils.gson2Object(authkeyRestClient.execute().getBody(), HashMap.class);
			redirec_uri=authKey.get("webmailUrl");
		}
		
		_logger.debug("redirec_uri : "+redirec_uri);
		return WebContext.redirect(redirec_uri);
	}

}
