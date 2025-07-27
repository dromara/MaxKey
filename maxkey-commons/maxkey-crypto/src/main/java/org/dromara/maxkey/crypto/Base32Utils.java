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
 

package org.dromara.maxkey.crypto;

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
