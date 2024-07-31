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

import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.crypto.DigestUtils;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.ExtraAttrs;
import org.dromara.maxkey.entity.apps.Apps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
/**
 * https://www.zentao.net/book/zentaopmshelp/344.html
 * http://www.zentao.net/api.php?m=user&f=apilogin&account=account&code=test&time=timestamp&token=token
 * 
 * $code  = 'test';
 * $key   = 'a5246932b0f371263c252384076cd3f0';
 * $time  = '1557034496';
 * $token = md5($code . $key . $time);
 * 
 * @author shimi
 *
 */
public class ExtendApiZentaoAdapter extends AbstractAuthorizeAdapter {
	static final  Logger _logger = LoggerFactory.getLogger(ExtendApiZentaoAdapter.class);
	static String login_url_template="api.php?m=user&f=apilogin&account=%s&code=%s&time=%s&token=%s";
	static String login_url_m_template="account=%s&code=%s&time=%s&token=%s";
	
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
		//extraAttrs from Applications
		ExtraAttrs extraAttrs=null;
		if(details.getIsExtendAttr()==1){
			extraAttrs=new ExtraAttrs(details.getExtendAttr());
		}
		_logger.trace("Extra Attrs " + extraAttrs);
		String code = details.getPrincipal();
		String key   = details.getCredentials();
		String time  = ""+Instant.now().getEpochSecond();

		String token =DigestUtils.md5Hex(code+key+time);
		
		_logger.debug(""+token);
		String account = userInfo.getUsername();
		
		String redirect_uri = details.getLoginUrl();
		if(redirect_uri.indexOf("api.php?")<0) {
			if(redirect_uri.endsWith("/")) {
			    redirect_uri += String.format(login_url_template,account,code,time,token);
			}else {
			    redirect_uri +="/" + String.format(login_url_template,account,code,time,token);
			}
		}else if(redirect_uri.endsWith("&")){
		    redirect_uri += String.format(login_url_m_template,account,code,time,token);
		}else {
		    redirect_uri += "&" +String.format(login_url_m_template,account,code,time,token);
		}
		
		_logger.debug("redirect_uri : "+redirect_uri);
		modelAndView=new ModelAndView("authorize/redirect_sso_submit");
        modelAndView.addObject("redirect_uri", redirect_uri);
		
		return modelAndView;
	}

}

