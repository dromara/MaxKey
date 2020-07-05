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
 

package org.maxkey.authz.desktop.endpoint.adapter;

import java.util.HashMap;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.crypto.HexUtils;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.AppsDesktopDetails;
import org.maxkey.util.JsonUtils;
import org.maxkey.util.StringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class DesktopDefaultAdapter extends AbstractAuthorizeAdapter {
	final static Logger _logger = LoggerFactory.getLogger(DesktopDefaultAdapter.class);
	@Override
	public String generateInfo(UserInfo userInfo,Object app) {
		AppsDesktopDetails details=(AppsDesktopDetails)app;
		String parameter=details.getParameter()==null?"":details.getParameter();
		
		if(details.getUsernameType().equalsIgnoreCase(AppsDesktopDetails.ParameterType.PARAMETER)){
			parameter = parameter+ " "+details.getUsernameParameter();
			parameter = parameter+ " "+ details.getAppUser().getRelatedUsername();
		}
		if(details.getPasswordType().equalsIgnoreCase(AppsDesktopDetails.ParameterType.PARAMETER)){
			parameter = parameter+ " "+details.getPasswordParameter();
			parameter = parameter+ " "+ details.getAppUser().getRelatedPassword();
		}
		
		HashMap<String,String> beanMap=new HashMap<String,String>();
		beanMap.put("randomId",(new StringGenerator()).uuidGenerate());
		beanMap.put("programPath", details.getProgramPath());
		beanMap.put("parameter", details.getParameter());
		beanMap.put("preUsername", details.getPreUsername());
		beanMap.put("usernameType", details.getUsernameType());
		beanMap.put("usernameParameter", details.getUsernameParameter());
		beanMap.put("username", details.getAppUser().getRelatedUsername());
		beanMap.put("prePassword", details.getPrePassword());
		beanMap.put("passwordType", details.getPasswordType());
		beanMap.put("passwordParameter", details.getPasswordParameter());
		beanMap.put("password", details.getAppUser().getRelatedPassword());
		beanMap.put("preSubmit", details.getPreSubmit());
		beanMap.put("submitType", details.getSubmitType());
		beanMap.put("submitKey", details.getSubmitKey());
		
		String jsonString=JsonUtils.object2Json(beanMap);
		_logger.debug("Token : "+jsonString);
		
		return jsonString;
	}

	@Override
	public String encrypt(String data, String algorithmKey, String algorithm) {
		_logger.debug("Parameter String : "+data);
		String encoderParamString=HexUtils.bytes2HexString(data.getBytes());
		return encoderParamString;
	}

	
	
	/* (non-Javadoc)
	 * @see com.connsec.web.authorize.endpoint.adapter.AbstractAuthorizeAdapter#sign(java.lang.String, com.connsec.domain.apps.Applications)
	 */
	@Override
	public String sign(String data, Apps app) {
		String signData=super.sign(data, app);
		return signData;
	}

	@Override
	public ModelAndView authorize(UserInfo userInfo, Object app, String data,ModelAndView modelAndView) {
		modelAndView.setViewName("authorize/desktop_sso_execute");
		AppsDesktopDetails details=(AppsDesktopDetails)app;
		modelAndView.addObject("username", details.getAppUser().getRelatedUsername());
		modelAndView.addObject("password", details.getAppUser().getRelatedPassword());
		modelAndView.addObject("encoderParam", data);
		return modelAndView;
	}

}
