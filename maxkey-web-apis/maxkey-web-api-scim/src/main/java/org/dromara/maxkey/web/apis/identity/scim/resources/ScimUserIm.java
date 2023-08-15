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
 

package org.dromara.maxkey.web.apis.identity.scim.resources;

import java.io.Serializable;

public class ScimUserIm extends ScimMultiValuedAttribute implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -529297556948872883L;

    public static class UserImType {
        public static final String AIM = "aim";
        public static final String GTALK = "gtalk";
        public static final String ICQ = "icq";
        public static final String XMPP = "xmpp";
        public static final String MSN = "msn";
        public static final String SKYPE = "skype";
        public static final String QQ = "qq";
        public static final String YAHOO = "yahoo";
    }
}
