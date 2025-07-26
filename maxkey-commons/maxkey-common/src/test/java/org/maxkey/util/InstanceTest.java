/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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

import java.security.Provider;
import java.security.Security;

import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.util.Instance;


public class InstanceTest {

    public static void main(String[] args) {
        if(System.getProperty("java.version").startsWith("1.8")) {
            System.out.println("1.8");
            Security.addProvider((Provider)Instance.newInstance("com.sun.crypto.provider.SunJCE"));
            System.out.println(PasswordReciprocal.getInstance().encode("ddddd"));
            
            System.out.println(PasswordReciprocal.getInstance().encode("ddfs"));
        }else {
            System.out.println("other");
        }
        
    }

}
