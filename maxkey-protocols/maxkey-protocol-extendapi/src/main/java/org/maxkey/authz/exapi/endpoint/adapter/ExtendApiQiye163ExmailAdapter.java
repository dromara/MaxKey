/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

import java.io.UnsupportedEncodingException;
import java.security.interfaces.RSAPrivateKey;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.crypto.HexUtils;
import org.maxkey.crypto.RSAUtils;
import org.maxkey.entity.Accounts;
import org.maxkey.entity.ExtraAttrs;
import org.maxkey.entity.apps.Apps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
/**
 * qiye.163.com
 * @author shimingxy
 *
 */
public class ExtendApiQiye163ExmailAdapter extends AbstractAuthorizeAdapter {
	final static Logger _logger = LoggerFactory.getLogger(ExtendApiQiye163ExmailAdapter.class);
	//https://entryhz.qiye.163.com
	static String REDIRECT_URI	
			= "https://entryhz.qiye.163.com/domain/oa/Entry?domain=%s&account_name=%s&time=%s&enc=%s";

	Accounts account;
	
	@Override
	public Object generateInfo() {
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
		String time = System.currentTimeMillis() + "";
		//域名，请使用企业自己的域名
		String domain = details.getPrincipal();
		
		String account_name = this.userInfo.getEmail();
		
		//String lang = "0";
		String src = account_name + domain + time;
		
		String privateKey = details.getCredentials();
		String enc = null;
		try {
			enc = HexUtils.bytes2HexString(
						RSAUtils.sign(
								src.getBytes("UTF-8"), 
								(RSAPrivateKey)RSAUtils.privateKey(HexUtils.hex2Bytes(privateKey)), 
								null)
					);
			String loginUrl = String.format(REDIRECT_URI, domain,account_name,time,enc);
			_logger.debug("LoginUrl {} " , loginUrl);
			modelAndView.addObject("redirect_uri", loginUrl);
		} catch (UnsupportedEncodingException e) {
			_logger.error("UnsupportedEncodingException ", e);
		} catch (Exception e) {
			_logger.error("Exception ", e);
		}
        
        return modelAndView;
	}
}
