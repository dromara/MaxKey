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
 

package org.maxkey.util;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.crypto.Base64Utils;

/**
 * @author Crystal.Sea
 *
 */
public class AuthorizationHeaderUtils {

	/**
	 * first UpperCase
	 */
    public static final String HEADER_Authorization = "Authorization";
    /**
     * first LowerCase
     */
    public static final String HEADER_authorization = "authorization";

    public static String createBasic(String username, String password) {
        String authUserPass = username + ":" + password;
        String encodedAuthUserPass = Base64Utils.encode(authUserPass);
        return AuthorizationHeaderCredential.Credential.BASIC + encodedAuthUserPass;
    }

    public static AuthorizationHeaderCredential resolve(String authorization) {
        if (StringUtils.isNotBlank(authorization) && isBasic(authorization)) {
            String decodeUserPass = Base64Utils.decode(authorization.split(" ")[1]);
            String []userPass =decodeUserPass.split(":");
            return new AuthorizationHeaderCredential(userPass[0],userPass[1]);
        } else {
            return new AuthorizationHeaderCredential(resolveBearer(authorization));
        }
    }

    public static boolean isBasic(String basic) {
        if (basic.startsWith(AuthorizationHeaderCredential.Credential.BASIC)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String createBearer(String bearer) {
        return AuthorizationHeaderCredential.Credential.BEARER + bearer;
    }

    public static String resolveBearer(String bearer) {
        if (StringUtils.isNotBlank(bearer) && isBearer(bearer)) {
            return bearer.split(" ")[1];
        } else {
            return bearer;
        }
    }
    
    public static boolean isBearer(String bearer) {
        if (bearer.toLowerCase().startsWith(AuthorizationHeaderCredential.Credential.BEARER.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }
    
    public  static String resolveBearer(HttpServletRequest request) {
    	String authorization = 
    			StringUtils.isNotBlank(request.getHeader(HEADER_Authorization)) ? 
    					request.getHeader(HEADER_Authorization) : request.getHeader(HEADER_authorization);
    	if(StringUtils.isNotBlank(authorization)) {
    		return resolveBearer(authorization);
    	}
    	return null;
    }

}
