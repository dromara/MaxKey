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
 

package org.maxkey.client.utils;

import java.util.Map;

public abstract class MapUtils {

    public static <K, V> String toString(Map<K, V> map) {
        if (map == null) {
            return "";
        }
        if (map.isEmpty()) {
            return "{}";
        }

        final StringBuilder result = new StringBuilder();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            result.append(", ")
                    .append(entry.getKey().toString())
                    .append(" -> ")
                    .append(entry.getValue().toString())
                    .append(' ');
        }
        return "{" + result.append('}').substring(1);
    }
}
