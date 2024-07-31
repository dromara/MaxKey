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
 

package org.dromara.maxkey.authz.exapi.endpoint.adapter;

import java.time.Instant;
import java.util.HashMap;

import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.crypto.DigestUtils;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.ExtraAttrs;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.util.HttpsTrusts;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.web.HttpRequestAdapter;
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
	static final  Logger _logger = LoggerFactory.getLogger(ExtendApiCndnsApiMailAdapter.class);
	//sign no  parameter
	//sign=md5(action=getDomainInfo&appid=***&time=1579736456 + md5(token))
	//sign with parameter
	//sign=md5(action=getUserInfo&appid=***&email=admin@maxkey.org&time=1579736456 + md5(token))

	Accounts account;
	
	static String SIGN_STRING 		="action=getDomainInfo&appid=%s%s";
	
	static String SIGN_EMAIL_STRING ="action=getUserInfo&appid=%s&email=%s&time=%s%s";
	
	static String ADMIN_AUTHKEY_URI	="https://www.cndnsapi.com/email/clientmanagement?action=getDomailUrl&appid=%s&sign=%s&time=%s";
	
	static String AUTHKEY_URI		="https://www.cndnsapi.com/email/clientmanagement?action=getWebMailUrl&appid=%s&sign=%s&time=%s";
	
	
	@Override
	public Object generateInfo() {
		return null;
	}

	@Override
	public Object encrypt(Object data, String algorithmKey, String algorithm) {
		return null;
	}

	@SuppressWarnings("unchecked")
    @Override
	public ModelAndView authorize(ModelAndView modelAndView) {
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
		HashMap<String,Object > requestParamenter =new HashMap<String,Object >();
		String redirect_uri = "";
		if(action.equalsIgnoreCase("getDomailUrl")) {
			String sign =DigestUtils.md5Hex
					(String.format(
							SIGN_STRING,
							details.getPrincipal(),timestamp,tokenMd5));
			requestParamenter.put("domain", domain);
			String responseBody = new HttpRequestAdapter().post(
					String.format(ADMIN_AUTHKEY_URI,details.getPrincipal(),sign,timestamp),requestParamenter);
			
			HashMap<String, String> authKey=JsonUtils.gsonStringToObject(responseBody, HashMap.class);
			redirect_uri = authKey.get("adminUrl");
			
		}else {
			String sign =DigestUtils.md5Hex
					(String.format(
							SIGN_EMAIL_STRING,
							details.getPrincipal(),userInfo.getEmail(),timestamp,tokenMd5));
			requestParamenter.put("email", userInfo.getWorkEmail());
			String responseBody = new HttpRequestAdapter().post(
					String.format(AUTHKEY_URI,details.getPrincipal(),sign,timestamp),requestParamenter);
			
			HashMap<String, String> authKey=JsonUtils.gsonStringToObject(responseBody, HashMap.class);
			redirect_uri=authKey.get("webmailUrl");
		}
		
		_logger.debug("redirect_uri : "+redirect_uri);
		
        modelAndView.addObject("redirect_uri", redirect_uri);
        
        return modelAndView;
	}

}
