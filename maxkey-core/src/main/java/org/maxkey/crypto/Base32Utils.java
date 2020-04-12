package org.maxkey.crypto;

import org.apache.commons.codec.binary.Base32;

public class Base32Utils {

    static Base32 base32 = new Base32();

    public static String encode(String simple) {
        return base32.encodeToString(simple.getBytes());
    }

    public static String encode(byte[] simple) {
        return base32.encodeToString(simple);
    }

    public static byte[] decode(String cipher) {
        return base32.decode(cipher);
    }

}
