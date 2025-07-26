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
 

package org.dromara.maxkey.constants;

/**
 * Define int for boolean 0 false 1 true.
 * 
 * @author Crystal.Sea
 *
 */
public class ConstsBoolean {

    public static final int FALSE 	= 0;

    public static  final int TRUE 	= 1;

    private int value 				= FALSE;

    public ConstsBoolean() {

    }

    public int getValue() {
        return value;
    }

    public boolean isValue() {
        return TRUE == value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static boolean isTrue(int value) {
        return TRUE == value;
    }
    
    public static boolean isYes(String value) {
        return "YES".equalsIgnoreCase(value);
    }

    public static boolean isFalse(int value) {
        return FALSE == value;
    }

}
