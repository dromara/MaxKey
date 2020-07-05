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

import java.io.Serializable;
import java.util.Set;

public class Resource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5159743553948621024L;
    protected  Set<String> schemas;
    private  String id;
    private  String externalId;
    private  Meta meta;
    
    public Resource() {

    }
    public Resource(String id, String externalId, Meta meta, Set<String> schemas) {
        super();
        this.id = id;
        this.externalId = externalId;
        this.meta = meta;
        this.schemas = schemas;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getExternalId() {
        return externalId;
    }
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    public Set<String> getSchemas() {
        return schemas;
    }
    public void setSchemas(Set<String> schemas) {
        this.schemas = schemas;
    }
    
    
}
