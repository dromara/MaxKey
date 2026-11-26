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

import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.junit.jupiter.api.Test;

public class PasswordReciprocalTest {

    @Test
    public  void reciprocal() {
        String pass ="x8zPbCya";
        String encode = PasswordReciprocal.getInstance().encode(pass);
        System.out.println("encode "+encode);
        System.out.println("decoder "+PasswordReciprocal.getInstance().decoder(encode));
        
        PasswordReciprocal.setReciprocalKey("5da4e05b8e67049d599ea3ed02f095a8d");
        encode = "$2b$10$i0iY3dS7G7122ba7e53a3825e9c4b5965de9a092348482627c5f086ef";
        System.out.println("decoder "+PasswordReciprocal.getInstance().decoder(encode));
        
        System.out.println("matches "+PasswordReciprocal.getInstance().matches(pass,encode));
        
        System.out.println("Reciprocal "+PasswordReciprocal.getInstance().encode(pass));
        
    }
    
    @Test
    public void reciprocalV1() {
    	 String e ="$2a$10$Q/lGCwsetv/J7/LEFBmpxu6ba953da1c35a53559a85073a183e1c3f34e52af6134a54452b606d84593870a";
         System.out.println(PasswordReciprocal.getInstance().decoder(e));
         System.out.println(PasswordReciprocal.getInstance().matches("secret",e));
    }

}
