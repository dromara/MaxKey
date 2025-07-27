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

import org.dromara.maxkey.crypto.password.StandardPasswordEncoder;

public class StandardPasswordEncoderTest {

    public static void main(String[] args) {
    	StandardPasswordEncoder spe = new StandardPasswordEncoder();
        System.out.println(spe.encode("maxkeypassword"));
        
        String c="4b60c81ad4c31d97fbe8c87952f8de7a329ceb004261c8bd22254cfa8aa096bede6efbafcc84bade";
        System.out.println(spe.matches("maxkeypassword",c));
    }

}
