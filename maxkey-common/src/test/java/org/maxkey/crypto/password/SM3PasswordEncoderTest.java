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

import org.dromara.maxkey.crypto.password.SM3PasswordEncoder;

public class SM3PasswordEncoderTest {

    public static void main(String[] args) {
        SM3PasswordEncoder sm3 = new SM3PasswordEncoder();
        System.out.println(sm3.encode("maxkeypassword"));
        
        String c="f4679d46e96d95d67db4c8c91fcf8aaaa4e1d437ffee278d2ea97f41f7f48c12";
        System.out.println(sm3.matches("maxkeypassword",c));
    }

}
