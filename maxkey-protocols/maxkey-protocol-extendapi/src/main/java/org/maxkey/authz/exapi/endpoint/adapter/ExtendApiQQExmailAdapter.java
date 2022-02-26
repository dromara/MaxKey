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

import java.io.Serializable;

import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.entity.Accounts;
import org.maxkey.entity.ExtraAttrs;
import org.maxkey.entity.apps.Apps;
import org.maxkey.util.HttpsTrusts;
import org.maxkey.util.JsonUtils;
import org.maxkey.web.HttpRequestAdapter;
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
	static String TOKEN_URI		= "https://api.exmail.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
	//https://exmail.qq.com/qy_mng_logic/doc#10036
	static String AUTHKEY_URI 	= "https://api.exmail.qq.com/cgi-bin/service/get_login_url?access_token=%s&userid=%s";
	
	Accounts account;
	
	@Override
	public Object generateInfo() {
		return null;
	}

    @Override
	public ModelAndView authorize(ModelAndView modelAndView) {
		HttpsTrusts.beforeConnection();
		
		Apps details=(Apps)app;
		//extraAttrs from Applications
		ExtraAttrs extraAttrs=null;
		if(details.getIsExtendAttr()==1){
			extraAttrs=new ExtraAttrs(details.getExtendAttr());
		}
		_logger.debug("Extra Attrs "+extraAttrs);
		String responseBody = new HttpRequestAdapter().get(
				String.format(TOKEN_URI,details.getPrincipal(),details.getCredentials()),null);
		Token token =JsonUtils.gson2Object(responseBody,Token.class);
		_logger.debug("token {}" , token);
		
		String authKeyBody = new HttpRequestAdapter().get(
				String.format(AUTHKEY_URI,token.getAccess_token(),userInfo.getUsername()),null);
		
		LoginUrl loginUrl=JsonUtils.gson2Object(authKeyBody, LoginUrl.class);
		_logger.debug("LoginUrl {} " , loginUrl);
		
		
        modelAndView.addObject("redirect_uri", loginUrl.getLogin_url());
        
        return modelAndView;
	}
    
	class ExMailMsg{
		
		protected long expires_in;
		    
		protected String errmsg;
		
		protected long errcode;

		public ExMailMsg() {
		}

		public long getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(long expires_in) {
			this.expires_in = expires_in;
		}

		public String getErrmsg() {
			return errmsg;
		}

		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}

		public long getErrcode() {
			return errcode;
		}

		public void setErrcode(long errcode) {
			this.errcode = errcode;
		}
		
		
	}
	
	class Token extends ExMailMsg implements Serializable {
		private static final long serialVersionUID = 275756585220635542L;

	    /**
	     * access_token
	     */
	    private String access_token;
	    
		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Token [access_token=");
			builder.append(access_token);
			builder.append("]");
			return builder.toString();
		}

	}
	
	class LoginUrl extends ExMailMsg  implements Serializable {
		private static final long serialVersionUID = 3033047757268214198L;
		private String login_url;
		 
		public String getLogin_url() {
			return login_url;
		}

		public void setLogin_url(String login_url) {
			this.login_url = login_url;
		}
		
		public LoginUrl() {
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("LoginUrl [login_url=");
			builder.append(login_url);
			builder.append("]");
			return builder.toString();
		}
	}

}
