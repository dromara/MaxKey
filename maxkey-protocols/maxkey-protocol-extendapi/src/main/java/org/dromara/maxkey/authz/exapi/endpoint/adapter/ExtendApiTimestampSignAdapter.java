/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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

import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.crypto.DigestUtils;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.ExtraAttrs;
import org.dromara.maxkey.entity.apps.Apps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
/**
 * 
 * http://target.maxkey.org/demo/login?code=maxkey&time=timestamp&token=token
 * login url http://target.maxkey.org/demo/login?code=%s&timestamp=%s&token=%s
 * 
 * $code  		= 'maxkey';
 * $key   		= 'a5246932b0f371263c252384076cd3f0';
 * $timestamp  	= '1557034496';
 * $token 		= md5($code . $key . $time);
 * 
 * @author shimingxy
 *
 */
public class ExtendApiTimestampSignAdapter extends AbstractAuthorizeAdapter {
	static final  Logger _logger = LoggerFactory.getLogger(ExtendApiTimestampSignAdapter.class);
	
	Accounts account;
	
	@Override
	public Object generateInfo() {
		return null;
	}

	@Override
	public Object encrypt(Object data, String algorithmKey, String algorithm) {
		return null;
	}

	@Override
	public ModelAndView authorize(ModelAndView modelAndView) {
		Apps details=(Apps)app;
		
		String code = details.getPrincipal();
		String key   = details.getCredentials();
		String timestamp  = ""+Instant.now().getEpochSecond();
		String token =DigestUtils.md5Hex(code+key+timestamp);
		
		//extraAttrs from Applications
		ExtraAttrs extraAttrs=null;
		if(details.getIsExtendAttr()==1){
			extraAttrs=new ExtraAttrs(details.getExtendAttr());
			if(extraAttrs.get("sign") == null || extraAttrs.get("sign").equalsIgnoreCase("md5")) {
				
			}else if(extraAttrs.get("sign").equalsIgnoreCase("sha") || extraAttrs.get("sign").equalsIgnoreCase("sha1")) {
				token =DigestUtils.shaHex(code+key+timestamp);
			}else if(extraAttrs.get("sign").equalsIgnoreCase("sha256")) {
				token =DigestUtils.sha256Hex(code+key+timestamp);
			}else if(extraAttrs.get("sign").equalsIgnoreCase("sha384")) {
				token =DigestUtils.sha384Hex(code+key+timestamp);
			}else if(extraAttrs.get("sign").equalsIgnoreCase("sha512")) {
				token =DigestUtils.sha512Hex(code+key+timestamp);
			}
		}
		
		_logger.debug(""+token);
		String account = userInfo.getUsername();
		
		String redirect_uri = String.format(details.getLoginUrl(),account,code,timestamp,token);

		_logger.debug("redirect_uri : "+redirect_uri);
		
        modelAndView.addObject("redirect_uri", redirect_uri);
        
        return modelAndView;
        
	}

}