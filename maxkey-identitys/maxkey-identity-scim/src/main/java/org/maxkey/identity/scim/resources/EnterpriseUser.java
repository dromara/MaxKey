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

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnterpriseUser extends User {
    private static final long serialVersionUID = 3212312511630459427L;
    
    public static final String SCHEMA = "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User";
    
    @JsonProperty(SCHEMA)
    Enterprise enterprise;
    
    public EnterpriseUser() {
        schemas =new HashSet<String>();
        schemas.add(User.SCHEMA);
        schemas.add(SCHEMA);
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
    
    
}
