package org.maxkey.identity.scim.resources;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Meta implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -2244662962968933591L;
    
    private  String resourceType;
    private  Date created;
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
    public Meta() {

    }
    public Meta(String resourceType, Date created, Date lastModified, String location, String version,
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
