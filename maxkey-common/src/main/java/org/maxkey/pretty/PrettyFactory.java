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
 

package org.maxkey.pretty;

import org.maxkey.pretty.impl.JsonPretty;
import org.maxkey.pretty.impl.SqlPretty;
import org.maxkey.pretty.impl.XmlPretty;

public class PrettyFactory {
    
    static final Pretty jsonPretty  = new JsonPretty();
    
    static final Pretty sqlPretty   = new SqlPretty();
    
    static final Pretty xmlPretty   = new XmlPretty();
    
    public static Pretty getJsonPretty() {
        return jsonPretty;
    }
    
    public static Pretty getXmlPretty() {
        return xmlPretty;
    }
    
    public static Pretty getSqlPretty() {
        return sqlPretty;
    }
    
}
