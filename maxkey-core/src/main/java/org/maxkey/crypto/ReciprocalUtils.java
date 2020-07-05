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
 

/**
 * 
 */
package org.maxkey.crypto;

import java.io.UnsupportedEncodingException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.LogFactory;
import org.maxkey.util.StringGenerator;

/**
 * Reciprocal cipher or Symmetric-key algorithm
 * 
 * algorithm Support DES,DESede,Blowfish and AES
 * 
 * default key value use ReciprocalUtils.defaultKey
 * 
 * generateKey is generate random key for algorithm
 * 
 * @author Crystal.Sea
 * 
 */
public final class ReciprocalUtils {

    private static final String defaultKey = "l0JqT7NvIzP9oRaG4kFc1QmD_bWu3x8E5yS2h6"; //

    public final class Algorithm {
        public static final String DES = "DES";
        public static final String DESede = "DESede";
        public static final String Blowfish = "Blowfish";
        public static final String AES = "AES";
    }

    public static byte[] encode(byte[] simpleBytes, SecretKey secretKey, String algorithm) {
        // Create the ciphers
        Cipher ecipher;
        byte[] byteFinal = null;
        try {
            ecipher = Cipher.getInstance(secretKey.getAlgorithm());
            // Encode the string into bytes using utf-8
            ecipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // Encrypt
            byteFinal = ecipher.doFinal(simpleBytes);
            return byteFinal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param simple
     * @param secretKey must length
     * @return
     * @throws Exception
     */
    public static byte[] encode(String simple, String secretKey, String algorithm) {
        if (keyLengthCheck(secretKey, algorithm)) {
            SecretKey key = generatorKey(secretKey, algorithm);
            try {
                return encode(simple.getBytes("UTF-8"), key, algorithm);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] decoder(byte[] ciphersBytes, SecretKey secretKey, String algorithm) {
        Cipher cipher;
        byte[] byteFinal = null;
        try {
            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byteFinal = cipher.doFinal(ciphersBytes);
            // String simple=new String(byteFinal, "UTF8" );
            // return simple;
            return byteFinal;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return null;
    }

    public static String decoder(byte[] ciphersBytes, String secretKey, String algorithm) {
        if (keyLengthCheck(secretKey, algorithm)) {
            SecretKey key = generatorKey(secretKey, algorithm);
            try {
                return new String(decoder(ciphersBytes, key, algorithm), "UTF8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] encodeByDefaultKey(String simple, String algorithm) {
        SecretKey key = generatorDefaultKey(algorithm);
        return encode(simple.getBytes(), key, algorithm);

    }

    public static String encode2HexByDefaultKey(String simple, String algorithm) {
        byte[] byteFinal = encodeByDefaultKey(simple, algorithm);

        String cipherHex = HexUtils.bytes2HexString(byteFinal);
        return cipherHex;
    }

    public static byte[] decoderByDefaultKey(byte[] byteCiphers, String algorithm) {
        SecretKey key = generatorDefaultKey(algorithm);
        return decoder(byteCiphers, key, algorithm);

    }

    public static String decoderHexByDefaultKey(String ciphers, String algorithm) {
        byte[] byteSimple = HexUtils.hex2Bytes(ciphers);

        byte[] byteFinal = decoderByDefaultKey(byteSimple, algorithm);

        String simple = null;
        try {
            simple = new String(byteFinal, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return simple;

    }

    public static SecretKey generatorDefaultKey(String algorithm) {
        try {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            String secretKey = defaultKey;
            if (algorithm.equals(Algorithm.DES)) {
                secretKey = defaultKey.substring(0, 8);
            } else if (algorithm.equals(Algorithm.AES) || algorithm.equals(Algorithm.Blowfish)) {
                secretKey = defaultKey.substring(0, 16);
            } else if (algorithm.equals(Algorithm.DESede)) {
                secretKey = defaultKey.substring(0, 24);
            }
            // System.out.println("defaultKey : "+secretKey);
            SecretKey key = new SecretKeySpec(secretKey.getBytes(), algorithm);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SecretKey generatorKey(String secretKey, String algorithm) {
        try {
            SecretKey key = new SecretKeySpec(secretKey.getBytes(), algorithm);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode2Hex(String simple, String secretKey, String algorithm) {
        if (keyLengthCheck(secretKey, algorithm)) {
            byte[] cipher = encode(simple, secretKey, algorithm);
            // Encode bytes to HEX to get a string
            return HexUtils.bytes2HexString(cipher);
        }
        return null;
    }

    public static String decoderHex(String ciphers, String secretKey, String algorithm) {
        if (keyLengthCheck(secretKey, algorithm)) {
            byte[] byteSimple = HexUtils.hex2Bytes(ciphers);

            return decoder(byteSimple, secretKey, algorithm);
        }
        return null;
    }

    private static boolean keyLengthCheck(String secretKey, String algorithm) {
        boolean lengthCheck = false;
        if (algorithm.equals(Algorithm.DES)) {
            if (secretKey.length() == 8) {
                lengthCheck = true;
            } else {
                LogFactory.getLog(ReciprocalUtils.class)
                        .debug("key length is " + secretKey.getBytes().length + " ,must lequal 8");
            }
        } else if (algorithm.equals(Algorithm.DESede)) {
            if (secretKey.length() == 24) {
                lengthCheck = true;
            } else {
                LogFactory.getLog(ReciprocalUtils.class)
                        .debug("key length is " + secretKey.getBytes().length + " ,must equal 24");
            }
        } else if (algorithm.equals(Algorithm.AES)) {
            if (secretKey.length() == 16) {
                lengthCheck = true;
            } else {
                LogFactory.getLog(ReciprocalUtils.class)
                        .debug("key length is " + secretKey.getBytes().length + " ,must equal 16");
            }
        } else if (algorithm.equals(Algorithm.Blowfish)) {
            if (secretKey.length() <= 16) {
                lengthCheck = true;
            } else {
                LogFactory.getLog(ReciprocalUtils.class)
                        .debug("key length is " + secretKey.getBytes().length + " ,must be less then 16");
            }
        }
        return lengthCheck;
    }

    /**
     * @param simple
     * @param secretKey must length is 16
     * @return
     */
    public static String aesEncode(String simple, String secretKey) {
        return encode2Hex(simple, secretKey, Algorithm.AES);
    }

    public static String aesDecoder(String ciphers, String secretKey) {
        return decoderHex(ciphers, secretKey, Algorithm.AES);
    }

    /**
     * encode by defaultKey with Algorithm.AES
     * 
     * @param simple
     * @return Hex
     */
    public static String encode(String simple) {
        return encode2HexByDefaultKey(simple, Algorithm.AES);
    }

    /**
     * decoder by defaultKey with Algorithm.AES
     * 
     * @param ciphers is HEX
     * 
     * @return
     */
    public static String decoder(String ciphers) {
        return decoderHexByDefaultKey(ciphers, Algorithm.AES);
    }

    public static String generateKey(String algorithm) {
        if (algorithm.equals(Algorithm.DES)) {
            return (new StringGenerator(8)).randomGenerate();
        } else if (algorithm.equals(Algorithm.AES)) {
            return (new StringGenerator(16)).randomGenerate();
        } else if (algorithm.equals(Algorithm.Blowfish)) {
            return (new StringGenerator(16)).randomGenerate();
        } else if (algorithm.equals(Algorithm.DESede)) {
            return (new StringGenerator(24)).randomGenerate();
        } else {
            return (new StringGenerator()).uniqueGenerate();
        }
    }
}
