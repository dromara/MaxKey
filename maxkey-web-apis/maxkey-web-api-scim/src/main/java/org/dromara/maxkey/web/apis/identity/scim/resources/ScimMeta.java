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
import java.util.Date;
import java.util.Set;

import org.dromara.maxkey.json.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ScimMeta implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -2244662962968933591L;
    
    private  String resourceType;
    
    @JsonSerialize(using = JsonISODateSerializer.class)
    @JsonDeserialize(using = JsonISODateDeserializer.class)
    private  Date created;
    
    @JsonSerialize(using = JsonISODateSerializer.class)
    @JsonDeserialize(using = JsonISODateDeserializer.class)
    
    private  Date lastModified;
    
    private  String location;
    
    private  String version;
    
    private  Set<String> attributes;
    
    
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public Date getLastModified() {
        return lastModified;
    }
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public Set<String> getAttributes() {
        return attributes;
    }
    public void setAttributes(Set<String> attributes) {
        this.attributes = attributes;
    }
    public String getResourceType() {
        return resourceType;
    }
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    public ScimMeta() {

    }
    
    public ScimMeta(String resourceType) {
    	this.resourceType 	= 	resourceType;
    	this.version 		= 	"1.0";
    }
    
    public ScimMeta(String resourceType, Date created, Date lastModified, String location, String version,
            Set<String> attributes) {
        super();
        this.resourceType = resourceType;
        this.created = created;
        this.lastModified = lastModified;
        this.location = location;
        this.version = version;
        this.attributes = attributes;
    }
    
    
}
