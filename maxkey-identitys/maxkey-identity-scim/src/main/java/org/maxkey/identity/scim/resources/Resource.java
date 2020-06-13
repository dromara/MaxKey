package org.maxkey.identity.scim.resources;

import java.io.Serializable;
import java.util.Set;

public class Resource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5159743553948621024L;
    private  String id;
    private  String externalId;
    private  Meta meta;
    private  Set<String> schemas;
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
