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
 

package org.dromara.maxkey.util;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public abstract class HttpEncoder {

    private static final String CHARSET = "UTF-8";
    private static final Map<String, String> ENCODING_RULES;

    static {
        final Map<String, String> rules = new HashMap<>();
        rules.put("*", "%2A");
        rules.put("+", "%20");
        rules.put("%7E", "~");
        ENCODING_RULES = Collections.unmodifiableMap(rules);
    }

    public static String encode(String plain) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(plain, CHARSET);
            for (Map.Entry<String, String> rule : ENCODING_RULES.entrySet()) {
                encoded = applyRule(encoded, rule.getKey(), rule.getValue());
            }
        } catch (UnsupportedEncodingException uee) {
        	uee.printStackTrace();
        }
        
        return encoded;
    }

    private static String applyRule(String encoded, String toReplace, String replacement) {
        return encoded.replaceAll(Pattern.quote(toReplace), replacement);
    }

    public static String decode(String encoded) throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, CHARSET);

    }
}
