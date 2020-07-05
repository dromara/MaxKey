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
 

package org.maxkey.client.tokenbase;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.maxkey.client.crypto.Base64Utils;
import org.maxkey.client.crypto.ReciprocalUtils;
import org.maxkey.client.utils.JsonUtils;

public class TokenUtils {

	public static String decode(String tokenString,String algorithmKey, String algorithm){
		String token=ReciprocalUtils.decoder(Base64Utils.base64UrlDecode(tokenString), algorithmKey, algorithm);
		
		
		try {
			token=new String(Hex.decodeHex(token.toCharArray()),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		
		return token;
	}
	
	public static String [] parseSimpleBasedToken(String token){
		return new String[] {token.substring(0, token.indexOf("@@")),
			token.substring(token.indexOf("@@")+2)};
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String,Object> parseJsonBasedToken(String token){
		HashMap<String,Object>  tokenMap=new HashMap<String,Object>();

		tokenMap=JsonUtils.gson2Object(token, tokenMap.getClass());
			
		return tokenMap;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String token="634d23bf64c20ae937eb9b81dbe9c30969d2d569c8c6c3b9d8089bff8c910c07722ca1f4137132fefb380fb8dd011e71e5b1df84b73088629b85f07a3559c7d9";
		//            ZpqjxUOX3QuE8rwl6etstU0z2WO%2Flpo5
		String tokenString=TokenUtils.decode(token, "x8zPbCya", ReciprocalUtils.Algorithm.DES);
		System.out.println(tokenString);
		
	}
	
}
