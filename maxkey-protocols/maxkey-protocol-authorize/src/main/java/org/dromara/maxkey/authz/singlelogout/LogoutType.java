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
 

package org.dromara.maxkey.authz.singlelogout;

public class LogoutType {

    /**
     * For no SLO.
     */
    public static int NONE              = 0;
    /**
     * For back channel SLO.
     */
    public static  int BACK_CHANNEL     = 1;
    /**
     * For front channel SLO.
     */
    public static  int FRONT_CHANNEL    = 2;

}
