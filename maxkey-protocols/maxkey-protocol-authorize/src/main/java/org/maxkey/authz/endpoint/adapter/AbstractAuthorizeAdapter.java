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
 

package org.maxkey.authz.endpoint.adapter;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Hex;
import org.maxkey.constants.Boolean;
import org.maxkey.crypto.Base64Utils;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.crypto.cert.CertSigner;
import org.maxkey.crypto.keystore.KeyStoreLoader;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Apps;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractAuthorizeAdapter {
	final static Logger _logger = LoggerFactory.getLogger(AbstractAuthorizeAdapter.class);
	
	public  PasswordReciprocal passwordReciprocal=PasswordReciprocal.getInstance();
	
	public abstract ModelAndView authorize(UserInfo userInfo,Object app,String data,ModelAndView modelAndView);
	
	public abstract String generateInfo(UserInfo userInfo,Object app);
	
	public String  sign(String data,Apps app){
		if(Boolean.isTrue(app.getIsSignature())){
			KeyStoreLoader keyStoreLoader=(KeyStoreLoader)WebContext.getBean("keyStoreLoader");
			try {	
				byte[] signature= CertSigner.sign(data.getBytes(), keyStoreLoader.getKeyStore(), keyStoreLoader.getEntityName(), keyStoreLoader.getKeystorePassword());
				_logger.debug("signed Token : "+data);
				_logger.debug("signature : "+signature.toString());
				
				
				data=Base64Utils.base64UrlEncode(data.getBytes("UTF-8"))+"."+Base64Utils.base64UrlEncode(signature);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_logger.debug("Token : "+data);
			
		}else{
			_logger.debug("data not need sign .");
		}
		
		return data;
	}
	
	public  String encrypt(String data,String algorithmKey,String algorithm){
		
		algorithmKey=passwordReciprocal.decoder(algorithmKey);
		_logger.debug("algorithm : "+algorithm);
		_logger.debug("algorithmKey : "+algorithmKey);
		//Chinese , encode data to HEX
		try {
			data = new String(Hex.encodeHex(data.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		}     
		byte[] encodeData=ReciprocalUtils.encode(data, algorithmKey, algorithm);
		String tokenString=Base64Utils.base64UrlEncode(encodeData);
		_logger.trace("Reciprocal then HEX  Token : "+tokenString);
		
		return tokenString;
	}
}
