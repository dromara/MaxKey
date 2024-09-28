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
 

package org.dromara.maxkey.authz.endpoint.adapter;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.constants.ConstsBoolean;
import org.dromara.maxkey.crypto.Base64Utils;
import org.dromara.maxkey.crypto.ReciprocalUtils;
import org.dromara.maxkey.crypto.cert.CertSigner;
import org.dromara.maxkey.crypto.keystore.KeyStoreLoader;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractAuthorizeAdapter {
	static final  Logger _logger = LoggerFactory.getLogger(AbstractAuthorizeAdapter.class);
	
	protected Apps app;
	
	protected UserInfo userInfo;
	
	protected Accounts account;
	
	protected SignPrincipal principal;
	
	public abstract Object generateInfo();
	
	public  ModelAndView authorize(ModelAndView modelAndView) {
		return modelAndView;
	}
	
	public Object  sign(Object data,String signatureKey,String signature){
		if(ConstsBoolean.isTrue(app.getIsSignature())){
			KeyStoreLoader keyStoreLoader = WebContext.getBean("keyStoreLoader",KeyStoreLoader.class);
			try {	
				byte[] signData= CertSigner.sign(data.toString().getBytes(), keyStoreLoader.getKeyStore(), keyStoreLoader.getEntityName(), keyStoreLoader.getKeystorePassword());
				_logger.debug("signed Token : {}",data);
				_logger.debug("signature : {}",signData.toString());
				
				return Base64Utils.base64UrlEncode(data.toString().getBytes("UTF-8"))+"."+Base64Utils.base64UrlEncode(signData);
			} catch (UnsupportedEncodingException e) {
				_logger.error("UnsupportedEncodingException " , e);
			} catch (Exception e) {
				_logger.error("Exception " , e);
			}
			_logger.debug("Token {}" , data);
			
		}else{
			_logger.debug("data not need sign .");
			return data;
		}
		
		return null;
	}
	
	public  Object encrypt(Object data,String algorithmKey,String algorithm){
		
		algorithmKey = PasswordReciprocal.getInstance().decoder(algorithmKey);
		_logger.debug("algorithm : {}",algorithm);
		_logger.debug("algorithmKey : {}",algorithmKey);
		//Chinese , encode data to HEX
		try {
			data = new String(Hex.encodeHex(data.toString().getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		}     
		byte[] encodeData = ReciprocalUtils.encode(data.toString(), algorithmKey, algorithm);
		String tokenString = Base64Utils.base64UrlEncode(encodeData);
		_logger.trace("Reciprocal then HEX  Token : {}",tokenString);
		
		return tokenString;
	}
	
	public static String getValueByUserAttr(UserInfo userInfo,String userAttr) {
		String value = "";
		if(StringUtils.isBlank(userAttr)) {
			value = userInfo.getUsername();
		}else if(userAttr.equalsIgnoreCase("username")){
			value = userInfo.getUsername();
		}else if(userAttr.equalsIgnoreCase("userId")){
			value = userInfo.getId();
		}else if(userAttr.equalsIgnoreCase("email")){
			value = userInfo.getEmail();
		}else if(userAttr.equalsIgnoreCase("mobile")){
			value = userInfo.getMobile();
		}else if(userAttr.equalsIgnoreCase("workEmail")) {
			value = userInfo.getWorkEmail();
		}else if(userAttr.equalsIgnoreCase("windowsAccount")){
			value = userInfo.getWindowsAccount();
		}else if(userAttr.equalsIgnoreCase("employeeNumber")){
			value = userInfo.getEmployeeNumber();
		}else {
			value = userInfo.getId();
		}
		
		if(StringUtils.isBlank(value)) {
			value = userInfo.getUsername();
		}
		
		return value;
	}
	
	public  String serialize() {
		return "";
	};

	public void setPrincipal(SignPrincipal principal) {
		this.principal = principal;
		this.userInfo = principal.getUserInfo();
	}

	public void setApp(Apps app) {
		this.app = app;
	}

	public void setAccount(Accounts account) {
		this.account = account;
	}	
	
}
