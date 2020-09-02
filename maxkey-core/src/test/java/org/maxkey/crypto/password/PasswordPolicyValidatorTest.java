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

import org.maxkey.domain.PasswordPolicy;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.db.PasswordPolicyValidator;

public class PasswordPolicyValidatorTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
         PasswordPolicy passwordPolicy =new PasswordPolicy();
         passwordPolicy.setDigits(3);
         passwordPolicy.setMaxLength(16);
         passwordPolicy.setMinLength(6);
         passwordPolicy.setLowerCase(2);
         passwordPolicy.setUpperCase(2);
         passwordPolicy.setSpecialChar(1);
         passwordPolicy.setUsername(1);
         passwordPolicy.setDictionary(0);
        PasswordPolicyValidator passwordPolicyValidator =new PasswordPolicyValidator();
        
        passwordPolicyValidator.setPasswordPolicy(passwordPolicy);
        
        UserInfo u=new UserInfo();
        u.setUsername("admin");
        u.setPassword("admin无");
        passwordPolicyValidator.validator(u);
        
    }

}
