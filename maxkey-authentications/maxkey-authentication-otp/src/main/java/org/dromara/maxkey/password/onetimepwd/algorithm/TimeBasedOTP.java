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
 

package org.dromara.maxkey.password.onetimepwd.algorithm;

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TimeBasedOTP {
    // 0 1 2 3 4 5 6 7 8
    private static final int[] DIGITS_POWER
            = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };

    static String seed64 = "";

    static String steps = "0";

    static String steps2 = "0";

    TimeBasedOTP() {

    }

    /**
     * This method uses the JCE to provide the crypto algorithm. HMAC computes a
     * Hashed Message Authentication Code with the crypto hash algorithm as a
     * parameter.
     *
     * @param crypto the crypto algorithm (HmacSHA1, HmacSHA256, HmacSHA512)
     * @param keyBytes the bytes to use for the HMAC key
     * @param text    the message or text to be authenticated
     */

    private static byte[] hmac_sha(String crypto, byte[] keyBytes, byte[] text) {
        try {
            Mac hmac;
            hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }

    /**
     * This method converts a HEX string to Byte[].
     *
     * @param hex  the HEX string
     *
     * @return: a byte array
     */
    private static byte[] hexStr2Bytes(String hex) {
        // Adding one byte to get the right conversion
        // Values starting with "0" can be converted
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();

        // Copy all the REAL bytes, not the "first"
        byte[] ret = new byte[bArray.length - 1];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = bArray[i + 1];
        }
        return ret;
    }

    /**
     * This method generates a OTP value for the given set of parameters. Default
     * Crypto HmacSHA512
     * 
     * @param key          the shared secret, HEX encoded
     * @param time          a value that reflects a time
     * @param returnDigits  number of digits to return
     *
     * @return: a numeric String in base 10 that includes {@link truncationDigits}
     *          digits
     */
    public static String genOTP(String key, String time, String returnDigits) {

        return generateOTP(key, time, returnDigits, "HmacSHA1");
    }

    /*
     * * This method generates a OTP value for the given set of parameters. Crypto
     * HmacSHA1
     * 
     * @param key
     * 
     * @param time
     * 
     * @param returnDigits
     * 
     * @return
     */
    public static String generateTOTPHmacSHA1(String key, String time, String returnDigits) {
        return generateOTP(key, time, returnDigits, "HmacSHA1");
    }

    /*
     * * This method generates a OTP value for the given set of parameters. Crypto
     * HmacSHA256
     * 
     * @param key: the shared secret, HEX encoded
     * 
     * @param time: a value that reflects a time
     * 
     * @param returnDigits: number of digits to return
     *
     * @return: a numeric String in base 10 that includes {@link truncationDigits}
     * digits
     */

    public static String genOTPHmacSHA256(String key, String time, String returnDigits) {
        return generateOTP(key, time, returnDigits, "HmacSHA256");
    }

    /*
     * * This method generates a OTP value for the given set of parameters. Crypto
     * HmacSHA256
     * 
     * @param key: the shared secret, HEX encoded
     * 
     * @param time: a value that reflects a time
     * 
     * @param returnDigits: number of digits to return
     *
     * @return: a numeric String in base 10 that includes {@link truncationDigits}
     * digits
     */
    public static String genOTPHmacSHA512(String key, String time, String returnDigits) {
        return generateOTP(key, time, returnDigits, "HmacSHA512");
    }

    /**
     * This method generates a TOTP value for the given set of parameters.
     */
    public static void actualiseTime() {
        // double random = Math.random();
        long T0 = 0;
        long X = 30;
        Date d = new Date();
        Long k = d.getTime() / 1000;

        long testTime[] = { d.getTime() };
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        for (int i = 0; i < testTime.length; i++) {
            long T = (k - T0) / X;
            long T2 = ((k - T0) / X) - 1;
            // System.out.println(T);
            steps = Long.toHexString(T).toUpperCase();
            steps2 = Long.toHexString(T2).toUpperCase();
            while (steps.length() < 16) {
                steps = "0" + steps;
            }
            //String fmtTime = String.format("%1$-11s", k/1000);
            //String utcTime = df.format(new Date(testTime[i]));
        }
    }

    /**
     * generateOTP.
     * @param key String
     * @param time String
     * @param returnDigits String
     * @param crypto String
     * @return
     */
    public static String generateOTP(String key, String time, String returnDigits, String crypto) {
        int codeDigits = Integer.decode(returnDigits).intValue();

        String result = null;

        // Using the counter
        // First 8 bytes are for the movingFactor
        // Compliant with base RFC 4226 (HOTP)
        while (time.length() < 16) {
            time = "0" + time;
        }
        // Get the HEX in a Byte[]
        byte[] msg = hexStr2Bytes(time);
        byte[] k = hexStr2Bytes(key);

        byte[] hash = hmac_sha(crypto, k, msg);

        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16)
                | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[codeDigits];

        result = Integer.toString(otp);
        while (result.length() < codeDigits) {
            result = "0" + result;
        }
        return result;
    }

}
