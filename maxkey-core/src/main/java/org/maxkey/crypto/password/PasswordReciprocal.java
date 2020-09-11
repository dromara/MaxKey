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
 

package org.maxkey.crypto.password;

import org.maxkey.crypto.ReciprocalUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordReciprocal.
 * @author Crystal.Sea
 *
 */
public class PasswordReciprocal implements PasswordEncoder {

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

    public String rawPassword(String username, String password) {
        return password + "@" + username;
    }

    public String encode(CharSequence rawPassword) {
        return ReciprocalUtils.encode(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return ReciprocalUtils.encode(rawPassword.toString()).equals(encodedPassword);
    }

    public String decoder(CharSequence encodedPassword) {
        if(encodedPassword == null || encodedPassword.equals("")) {
            return "";
        }
        return ReciprocalUtils.decoder(encodedPassword.toString());
    }

}
