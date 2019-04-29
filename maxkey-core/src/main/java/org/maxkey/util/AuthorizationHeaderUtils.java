package org.maxkey.util;

import org.maxkey.crypto.Base64Utils;

/**
 * @author Crystal.Sea
 *
 */
public class AuthorizationHeaderUtils {
	
	public static String AUTHORIZATION_HEADERNAME="Authorization";
	
	public static String BASIC="Basic ";
	
	public static String BEARER="Bearer ";

	public static String createBasic( String username, String password ){
		String authUserPass = username + ":" + password;
	    String encodedAuthUserPass = Base64Utils.encode(authUserPass );
	    return BASIC  + encodedAuthUserPass;
	}

	public static String [] resolveBasic( String basic ){
		if(isBasic(basic)){
			String[] userPass =basic.split(" ");
		    String decodeUserPass = Base64Utils.decode(userPass[1] );
		    return decodeUserPass.split(":");
		}else{
			return null;
		}
	}
	
	public static boolean isBasic( String basic ){
		if(basic.startsWith(BASIC )){
			return true;
		}else{
			return false;
		}
	}
	
	public static String resolveBearer( String bearer ){
		if(isBearer(bearer)){
			return bearer.split(" ")[1];
		}else{
			return null;
		}
	}
	
	
	public static String createBearer(String bearer){
		return BEARER +bearer;
	}
	

	
	public static boolean isBearer( String bearer ){
		if(bearer.startsWith(BEARER )){
			return true;
		}else{
			return false;
		}
	}

	
}
