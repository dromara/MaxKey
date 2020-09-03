package org.maxkey.crypto.password;

 
import org.springframework.security.crypto.codec.Utf8;

import java.security.MessageDigest;

/**
 * Utility for constant time comparison to prevent against timing attacks.
 *
 * @author Rob Winch
 */
public class PasswordEncoderUtils {

    /**
     * Constant time comparison to prevent against timing attacks.
     * @param expected
     * @param actual
     * @return
     */
    static boolean equals(String expected, String actual) {
        byte[] expectedBytes = bytesUtf8(expected);
        byte[] actualBytes = bytesUtf8(actual);

        return MessageDigest.isEqual(expectedBytes, actualBytes);
    }

    private static byte[] bytesUtf8(String s) {
        if (s == null) {
            return null;
        }

        return Utf8.encode(s); // need to check if Utf8.encode() runs in constant time (probably not). This may leak length of string.
    }

    private PasswordEncoderUtils() {
    }
}
