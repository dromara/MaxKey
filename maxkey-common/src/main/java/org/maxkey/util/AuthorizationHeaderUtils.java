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

import org.maxkey.crypto.Base64Utils;

/**
 * @author Crystal.Sea
 *
 */
public class AuthorizationHeaderUtils {

    public static final String AUTHORIZATION_HEADERNAME = "Authorization";

    public static final String BASIC = "Basic ";

    public static final String BEARER = "Bearer ";

    public static String createBasic(String username, String password) {
        String authUserPass = username + ":" + password;
        String encodedAuthUserPass = Base64Utils.encode(authUserPass);
        return BASIC + encodedAuthUserPass;
    }

    public static String[] resolveBasic(String basic) {
        if (isBasic(basic)) {
            String[] userPass = basic.split(" ");
            String decodeUserPass = Base64Utils.decode(userPass[1]);
            return decodeUserPass.split(":");
        } else {
            return null;
        }
    }

    public static boolean isBasic(String basic) {
        if (basic.startsWith(BASIC)) {
            return true;
        } else {
            return false;
        }
    }

    public static String resolveBearer(String bearer) {
        if (isBearer(bearer)) {
            return bearer.split(" ")[1];
        } else {
            return null;
        }
    }

    public static String createBearer(String bearer) {
        return BEARER + bearer;
    }

    public static boolean isBearer(String bearer) {
        if (bearer.startsWith(BEARER)) {
            return true;
        } else {
            return false;
        }
    }

}
