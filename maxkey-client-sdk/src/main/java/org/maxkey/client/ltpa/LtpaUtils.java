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
