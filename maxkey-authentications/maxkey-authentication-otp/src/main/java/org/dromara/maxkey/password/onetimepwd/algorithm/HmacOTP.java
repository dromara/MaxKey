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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * same as HOTP,but addChecksum = false & truncationOffset = -1.
 * 
 * @author Crystal.Sea
 *
 */
public class HmacOTP {
    private static final Logger logger = LoggerFactory.getLogger(HmacOTP.class);

    /**
     * gen.
     * @param seed byte
     * @param count int
     * @param digits int
     * @return
     */
    public static String gen(byte[] seed, int count, int digits) {
        try {
            return generateOTP(seed, count, digits);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return "";
    }

    /**
     * @param keyBytes
     * @param text
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] hmac_sha1(byte[] keyBytes, byte[] text)
            throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha1;
        try {
            hmacSha1 = Mac.getInstance("HmacSHA1");
        } catch (NoSuchAlgorithmException nsae) {
            hmacSha1 = Mac.getInstance("HMAC-SHA-1");
        }
        SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
        hmacSha1.init(macKey);
        return hmacSha1.doFinal(text);
    }

    /**
     * @param secret
     * @param movingFactor
     * @param codeDigits
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    static private String generateOTP(byte[] secret, 
                long movingFactor, int codeDigits)
            throws NoSuchAlgorithmException, InvalidKeyException {
        // put movingFactor value into text byte array
        String result = null;
        byte[] text = new byte[8];

        for (int i = text.length - 1; i >= 0; i--) {
            text[i] = (byte) (movingFactor & 0xff);
            movingFactor >>= 8;
        }

        // compute hmac hash
        byte[] hash = hmac_sha1(secret, text);

        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16)
                | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

        int otp = (int) (binary % Math.pow(10, codeDigits));
        // int otp = binary % DIGITS_POWER[codeDigits];
        result = Integer.toString(otp);

        while (result.length() < codeDigits) {
            result = "0" + result;
        }
        return result;
    }

}
