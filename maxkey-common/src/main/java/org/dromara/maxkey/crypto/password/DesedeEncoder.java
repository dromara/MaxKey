/*
 * Copyright [2023] [MaxKey of copyright http://www.maxkey.top]
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
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * DesedePasswordEncoder.
 * @author Crystal.Sea
 *
 */
public class DesedeEncoder implements PasswordEncoder {

	private static final String DEFAULT_SALT 	= "bWu3x8E5yS2h6l0J_qT7NvIzP9oRaG4kFc1QmD"; //
	private static final String CRYPT 			= "{crypt}";
	private static final String PLAIN 			= "{plain}";

	public static final int PREFFIX_LENGTH 		= 7;

    private static DesedeEncoder desedePasswordEncoder;

    public DesedeEncoder() {
    	//
    }

    /**
     * getInstance.
     * @return
     */
    public static DesedeEncoder getInstance() {

        if (desedePasswordEncoder == null) {
        	desedePasswordEncoder = new DesedeEncoder();
        }

        return desedePasswordEncoder;
    }

    public String decoder(CharSequence encodedPassword) {
    	if(encodedPassword == null) {
    		return null;
    	}
    	String encodedPasswordString  = encodedPassword.toString();
    	if(encodedPasswordString.startsWith(CRYPT)) {
    		return ReciprocalUtils.decoderHex(encodedPasswordString.substring(PREFFIX_LENGTH), DEFAULT_SALT);
    	}else if(encodedPasswordString.startsWith(PLAIN)) {
    		return encodedPasswordString.substring(PREFFIX_LENGTH);
    	}else {
    		return encodedPasswordString;
    	}
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
    	if(encodedPassword.startsWith(PLAIN)) {
    		encodedPassword = encode(encodedPassword.substring(PREFFIX_LENGTH));
    	}
    	String finalPassword = encode(rawPassword);
        return finalPassword.equals(encodedPassword);
    }

	@Override
	public String encode(CharSequence plain) {
		return (plain == null) ? null : (CRYPT + ReciprocalUtils.encode2Hex(plain + "", DEFAULT_SALT));
	}

	public String encode(CharSequence plain,boolean isEncode) {
		if(plain == null) {
			return null;
		}
		if(isEncode) {
			return CRYPT + ReciprocalUtils.encode2Hex(plain + "", DEFAULT_SALT);
		}else {
			return PLAIN + plain;
		}
	}

}
