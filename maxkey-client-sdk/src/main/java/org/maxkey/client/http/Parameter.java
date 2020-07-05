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
 

package org.maxkey.client.http;

import org.maxkey.client.utils.HttpEncoder;

public class Parameter implements Comparable<Parameter> {

    private final String key;
    private final String value;

    public Parameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String asUrlEncodedPair() {
        return HttpEncoder.encode(key).concat("=").concat(HttpEncoder.encode(value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Parameter)) {
            return false;
        }

        final Parameter otherParam = (Parameter) other;
        return otherParam.getKey().equals(key) && otherParam.getValue().equals(value);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return key.hashCode() + value.hashCode();
    }

    @Override
    public int compareTo(Parameter parameter) {
        final int keyDiff = key.compareTo(parameter.getKey());

        return keyDiff == 0 ? value.compareTo(parameter.getValue()) : keyDiff;
    }
}
