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
         passwordPolicy.setSimplePasswords("admin,1qaz,2wsx,123456,12345678,1234567890");
        PasswordPolicyValidator passwordPolicyValidator =new PasswordPolicyValidator();
        
        passwordPolicyValidator.setPasswordPolicy(passwordPolicy);
        
        UserInfo u=new UserInfo();
        u.setUsername("admin");
        u.setPassword("admin无");
        passwordPolicyValidator.validator(u);
        
    }

}
