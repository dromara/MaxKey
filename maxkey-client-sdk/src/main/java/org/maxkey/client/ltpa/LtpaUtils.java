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
 

package org.maxkey.client.ltpa;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.maxkey.client.crypto.Base64Utils;
import org.maxkey.client.crypto.ReciprocalUtils;
import org.maxkey.client.utils.JsonUtils;

public class LtpaUtils {

	public static String readLtpa(HttpServletRequest request,String ltpaDomain,String ltpaName){
		Cookie[] cookies = request.getCookies(); 
		if(cookies!=null) {
			 for (int i = 0; i < cookies.length; i++) {
				 if(cookies[i].getName().equalsIgnoreCase(ltpaName)){
					 return cookies[i].getValue();
				 }
			 }
		}
		return null;
	}
	
	public static String decode(String ltpaString,String algorithmKey, String algorithm){
		
		String token=ReciprocalUtils.decoder(Base64Utils.base64UrlDecode(ltpaString), algorithmKey, algorithm);
		
		try {
			token=new String(Hex.decodeHex(token.toCharArray()),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		
		return token;
	}
	
	
	@SuppressWarnings("unchecked")
	public static HashMap<String,Object> parseLtpaJson(String token){
		HashMap<String,Object>  tokenMap=new HashMap<String,Object>();

		tokenMap=JsonUtils.gson2Object(token, tokenMap.getClass());
			
		return tokenMap;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String token="ICHQ%2F1Tdzw214UNw9fKEjRNFbvOlGdXyxVjF9I7kwEo%3D";
		String tokenString=LtpaUtils.decode(token, "x8zPbCya", ReciprocalUtils.Algorithm.DES);
		System.out.println(tokenString);
		
	}
	
}
