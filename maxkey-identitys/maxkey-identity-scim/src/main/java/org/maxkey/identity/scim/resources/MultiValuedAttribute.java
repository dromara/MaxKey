package org.maxkey.identity.scim.resources;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MultiValuedAttribute implements Serializable {
    
    private static final long serialVersionUID = 6878912593878245947L;
    
    String value;
    String display;
    boolean primary;
    @JsonProperty("$ref")
    String reference;
    String type;
      
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getDisplay() {
        return display;
    }
    public void setDisplay(String display) {
        this.display = display;
    }
    public boolean isPrimary() {
        return primary;
    }
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
}
