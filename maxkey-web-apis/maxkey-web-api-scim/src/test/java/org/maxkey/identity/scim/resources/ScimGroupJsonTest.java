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
 

package org.maxkey.identity.scim.resources;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.dromara.maxkey.pretty.impl.JsonPretty;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimGroup;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimMemberRef;
import org.dromara.maxkey.web.apis.identity.scim.resources.ScimMeta;

public class ScimGroupJsonTest {
    public static void main(String[] args) {
        ScimGroup g= new ScimGroup();
        
        ScimMeta meta = new ScimMeta();
        meta.setVersion("W\\/\"f250dd84f0671c3\"");
        meta.setCreated(new Date());
        meta.setLocation("https://example.com/v2/Users/2819c223...");
        meta.setResourceType("User");
        meta.setLastModified(new Date());
        g.setMeta(meta);
              
        g.setDisplayName("Tour Guides");
        
        Set<ScimMemberRef> mrSet =new HashSet<ScimMemberRef>();
        ScimMemberRef mr1 =new ScimMemberRef();
        mr1.setReference("https://example.com/v2/Users/2819c223-7f76-453a-919d-413861904646");
        mr1.setValue("2819c223-7f76-453a-919d-413861904646");
        mr1.setDisplay("Babs Jensen");
        ScimMemberRef mr2 =new ScimMemberRef();
        mr2.setReference("https://example.com/v2/Users/2819c223-7f76-453a-919d-413861904646");
        mr2.setValue("2819c223-7f76-453a-919d-413861904646");
        mr2.setDisplay("Babs Jensen");
        ScimMemberRef mr3 =new ScimMemberRef();
        mr3.setReference("https://example.com/v2/Users/2819c223-7f76-453a-919d-413861904646");
        mr3.setValue("2819c223-7f76-453a-919d-413861904646");
        mr3.setDisplay("Babs Jensen");
        mrSet.add(mr1);
        mrSet.add(mr2);
        mrSet.add(mr3);
        
        g.setMembers(mrSet);
        
        System.out.println(
                (new JsonPretty()).format(JsonUtils.toString(g)));
    }
}
