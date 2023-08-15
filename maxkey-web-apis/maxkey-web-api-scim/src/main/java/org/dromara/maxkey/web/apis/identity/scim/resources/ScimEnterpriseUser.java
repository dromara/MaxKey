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

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScimEnterpriseUser extends ScimUser {
    private static final long serialVersionUID = 3212312511630459427L;
    
    public static final String SCHEMA = "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User";
    
    @JsonProperty(SCHEMA)
    ScimEnterprise enterprise;
    
    public ScimEnterpriseUser() {
        schemas =new HashSet<String>();
        schemas.add(ScimUser.SCHEMA);
        schemas.add(SCHEMA);
    }

    public ScimEnterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(ScimEnterprise enterprise) {
        this.enterprise = enterprise;
    }
    
    
}
