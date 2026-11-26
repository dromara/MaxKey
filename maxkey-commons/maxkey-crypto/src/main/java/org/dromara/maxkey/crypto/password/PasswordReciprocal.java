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
 

package org.dromara.maxkey.crypto.password;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.dromara.maxkey.crypto.ReciprocalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordReciprocal.
 * @author Crystal.Sea
 *
 */
public class PasswordReciprocal implements PasswordEncoder {
	static final Logger _logger = LoggerFactory.getLogger(PasswordReciprocal.class);

	static final String PREFIX_2A = "$2a";
	static final String PREFIX_2B = "$2b";
	static final String PREFIX_2Y = "$2y";
    static final int PREFFIX_LENGTH = 8;
    static final int SALT_LENGTH = PREFFIX_LENGTH + 8;
    /**
     * 
     */
    private static final String DEFAULT_KEY = "_bWIc1QmDzPyS2h6l0JqT7Nv9oRaG4kFu3x8E5"; 
    
    static String reciprocalKey = "";
    
    public static PasswordReciprocal passwordReciprocal;
    
    private PasswordReciprocal() {

    }

    public static void setReciprocalKey(String reciprocalKey) {
		PasswordReciprocal.reciprocalKey = reciprocalKey;
	}

	/**
     * getInstance.
     * @return
     */
    public static PasswordReciprocal getInstance() {
        if (passwordReciprocal == null) {
            passwordReciprocal = new PasswordReciprocal();
        }
        return passwordReciprocal;
    }
    
    public String decoder(CharSequence encodedPassword) {
    	if(StringUtils.isBlank(encodedPassword)){
    		return null;
    	}else if(Strings.CS.startsWith(encodedPassword, PREFIX_2Y)) {//自定义
	        return decoderByKey(encodedPassword,reciprocalKey);
        }else if(Strings.CS.startsWith(encodedPassword, PREFIX_2B)) {//默认
        	return  decoderByKey(encodedPassword,DEFAULT_KEY);
        }else if(Strings.CS.startsWith(encodedPassword, PREFIX_2A)) {//兼容V1
        	return PasswordReciprocalV1.getInstance().decoder(encodedPassword);
        }
    	return null;
    }
    
    private String decoderByKey(CharSequence encodedPassword,String decoderKey) {
    	String salt = encodedPassword.subSequence(0, SALT_LENGTH).toString();
    	CharSequence doEncodedPassword = encodedPassword.subSequence(SALT_LENGTH, encodedPassword.length());
    	String secretKey = salt.substring(PREFFIX_LENGTH) + decoderKey;
        String plain = ReciprocalUtils.decoderHex(doEncodedPassword.toString(), secretKey);
        if(StringUtils.isNotBlank(plain)) {
        	return plain.substring(PREFFIX_LENGTH);
        }
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
    	//空值返回false
    	if(StringUtils.isBlank(encodedPassword) || StringUtils.isBlank(rawPassword)) {
    		return false;
    	}else if(Strings.CS.startsWith(encodedPassword,PREFIX_2Y) 
    			|| Strings.CS.startsWith(encodedPassword, PREFIX_2B)) {
	        String plain = decoder(encodedPassword);
	        return Strings.CS.equals(rawPassword, plain);
    	}else if(Strings.CS.startsWith(encodedPassword, PREFIX_2A)){//兼容V1
    		return PasswordReciprocalV1.getInstance().matches(rawPassword, encodedPassword);
    	}
    	return false;
    }

    /**
     * salt
     * length 16
     * @return salt
     */
    private String gensalt() {
    	if(StringUtils.isNotBlank(reciprocalKey)) {
    		return BCrypt.gensalt(PREFIX_2Y, 10).substring(0, SALT_LENGTH);
    	}
    	return BCrypt.gensalt(PREFIX_2B, 10).substring(0, SALT_LENGTH);
    }

    @Override
    public String encode(CharSequence plain) {
        //$2y$10$
        String salt = gensalt();
        return encode(plain, salt);
    }
    
    private String encode(CharSequence plain,String salt) {
        String password = salt.substring(PREFFIX_LENGTH) + plain ;
        String secretKey = genSecretKey(salt);
        return salt + ReciprocalUtils.encode2Hex(password , secretKey);
    }
    
    private String genSecretKey(String salt) {
    	if(StringUtils.isNotBlank(reciprocalKey)) {
    		return salt.substring(PREFFIX_LENGTH) + reciprocalKey;
    	}
    	return salt.substring(PREFFIX_LENGTH) + DEFAULT_KEY;
    }
   
}
