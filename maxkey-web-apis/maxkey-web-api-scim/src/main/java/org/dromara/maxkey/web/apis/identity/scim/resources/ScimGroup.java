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

import java.util.HashSet;
import java.util.Set;

public class ScimGroup extends ScimResource{
    private static final long serialVersionUID = 404613567384513866L;

    public static final String SCHEMA = "urn:ietf:params:scim:schemas:core:2.0:Group";
    
    private  String displayName;
    private  Set<ScimMemberRef> members;
    
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public Set<ScimMemberRef> getMembers() {
        return members;
    }
    public void setMembers(Set<ScimMemberRef> members) {
        this.members = members;
    }
    
    public ScimGroup() {
        schemas =new HashSet<String>();
        schemas.add(SCHEMA);
    }
    
}
