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
import org.dromara.maxkey.crypto.ReciprocalUtilsV1;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordReciprocal.
 * @author Crystal.Sea
 *
 */
public class PasswordReciprocalV1 implements PasswordEncoder {
	static final String PREFIX = "$2a";
	static final int PREFFIX_LENGTH = 7;
	static final int SALT_LENGTH = 29;
    
    public static PasswordReciprocalV1 passwordReciprocal;
    
    private PasswordReciprocalV1() {

    }

    /**
     * getInstance.
     * @return
     */
    public static PasswordReciprocalV1 getInstance() {
        if (passwordReciprocal == null) {
            passwordReciprocal = new PasswordReciprocalV1();
        }
        return passwordReciprocal;
    }
    
    public String decoder(CharSequence encodedPassword) {
    	if(StringUtils.isBlank(encodedPassword)){
    		return null;
    	}
        String salt = encodedPassword.subSequence(0, SALT_LENGTH).toString();
        encodedPassword = encodedPassword.subSequence(SALT_LENGTH, encodedPassword.length());
        String plain = ReciprocalUtilsV1.decoderHex(encodedPassword.toString(), salt.substring(PREFFIX_LENGTH));
        return plain.substring(salt.substring(PREFFIX_LENGTH).length());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
    	//空值返回false
    	if(StringUtils.isBlank(encodedPassword) || StringUtils.isBlank(rawPassword)) {
    		return false;
    	}
        String salt = encodedPassword.subSequence(0, SALT_LENGTH).toString();
        String finalPassword = encode(rawPassword,salt);
        return finalPassword.equals(encodedPassword);
    }

    /**
     * salt
     * length 29
     * @return salt
     */
    private String gensalt() {
        return BCrypt.gensalt(PREFIX, 10);
    }

    @Override
    public String encode(CharSequence plain) {
        //$2a$10$
        String salt = gensalt();
        return encode(plain, salt);
    }
    
    private String encode(CharSequence plain,String salt) {
        String password = salt.substring(PREFFIX_LENGTH) + plain ;
        return salt + ReciprocalUtilsV1.encode2Hex(password , salt.substring(PREFFIX_LENGTH));
    }
}
