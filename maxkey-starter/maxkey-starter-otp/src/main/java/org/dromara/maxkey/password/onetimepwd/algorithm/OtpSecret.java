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

import java.util.Arrays;
import java.util.Random;

public class OtpSecret {

    private static final Random rand = new Random();

    /**
     * Seed for HMAC-SHA1 - 20 bytes Generates random 20 bytes long TOTP Secret.
     * 
     * @return generated secret
     */
    public static final byte[] generate() {
        int size = 20;
        byte[] b = new byte[size];
        rand.nextBytes(b);
        return Arrays.copyOf(b, size);
    }

    /**
     * Seed by crypto.
     * 
     * @return generated secret
     */
    public static final byte[] generate(String crypto) {
        if (crypto.equalsIgnoreCase("HmacSHA1") || crypto.equalsIgnoreCase("HMAC-SHA-1")) {
            return generate();
        }
        if (crypto.equalsIgnoreCase("HmacSHA256") || crypto.equalsIgnoreCase("HMAC-SHA-256")) {
            return generate32();
        }
        if (crypto.equalsIgnoreCase("HmacSHA512") || crypto.equalsIgnoreCase("HMAC-SHA-512")) {
            return generate64();
        }
        return generate();
    }

    /**
     * Seed for HMAC-SHA256 - 32 bytes Generates random 32 bytes long TOTP Secret.
     * 
     * @return generated secret
     */
    public static final byte[] generate32() {
        int size = 32;
        byte[] b = new byte[size];
        rand.nextBytes(b);
        return Arrays.copyOf(b, size);
    }

    /**
     * Seed forHMAC-SHA512 - 64 bytes Generates random 64 bytes long TOTP Secret.
     * 
     * @return generated secret
     */
    public static final byte[] generate64() {
        int size = 64;
        byte[] b = new byte[size];
        rand.nextBytes(b);
        return Arrays.copyOf(b, size);
    }

}
