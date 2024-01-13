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

import org.dromara.maxkey.crypto.ReciprocalUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordReciprocal.
 * @author Crystal.Sea
 *
 */
public class PasswordReciprocal implements PasswordEncoder {

	public static int PREFFIX_LENGTH = 7;
	
    public static PasswordReciprocal passwordReciprocal;
    
    public PasswordReciprocal() {

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
    	String salt = encodedPassword.subSequence(0, 29).toString();
    	encodedPassword = encodedPassword.subSequence(29, encodedPassword.length());
    	String plain = ReciprocalUtils.decoderHex(encodedPassword.toString(), salt.substring(PREFFIX_LENGTH));
        return plain.substring(salt.substring(PREFFIX_LENGTH).length());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
    	String salt = encodedPassword.subSequence(0, 29).toString();
    	String finalPassword = encode(rawPassword,salt);
        return finalPassword.equals(encodedPassword);//ReciprocalUtils.encode(rawPassword.toString()).equals(encodedPassword);
    }

    /**
     * salt
     * length 29
     * @return salt
     */
    public String gensalt() {
    	return BCrypt.gensalt("$2a", 10);
    }

	@Override
	public String encode(CharSequence plain) {
		//$2a$10$
    	String salt = gensalt();
        return encode(plain, salt);
	}
	
	private String encode(CharSequence plain,String salt) {
    	String password = salt.substring(PREFFIX_LENGTH) + plain ;
        return salt + ReciprocalUtils.encode2Hex(password , salt.substring(PREFFIX_LENGTH));
	}
}
