package org.maxkey.crypto.password;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.maxkey.crypto.*;

public class SM3PasswordEncoder  implements PasswordEncoder {
  
    @Override
    public String encode(CharSequence rawPassword) {
        String  cipher = new String(Hex.encode(SM3.encode(rawPassword.toString().getBytes())));
        return cipher;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String  cipher = encode(rawPassword);
        return encodedPassword.equals(cipher);
    }

}
