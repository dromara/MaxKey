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

import org.dromara.maxkey.crypto.password.Md4PasswordEncoder;

public class SM4PasswordEncoderTest {

    public static void main(String[] args) {
    	Md4PasswordEncoder sm4 = new Md4PasswordEncoder();
        System.out.println(sm4.encode("maxkeypassword"));
        
        String c="{BQWoTG+C4jL8d8QNIu0jL1WkMWezxNAZtliNoJOke5k=}8cfc46546a5996e74442183bd122f370";
        System.out.println(sm4.matches("maxkeypassword",c));
    }

}
