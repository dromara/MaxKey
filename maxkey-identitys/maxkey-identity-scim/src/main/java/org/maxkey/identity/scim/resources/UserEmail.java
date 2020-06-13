package org.maxkey.identity.scim.resources;

import java.io.Serializable;

public class UserEmail extends MultiValuedAttribute implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -41327146592552688L;
    
    public static class UserEmailType {
        public static final String WORK = "work";
        public static final String HOME = "home";
        public static final String OTHER = "other";

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public UserEmail() {
    }

    public UserEmail(String value, String type, boolean primary) {
        super();
        this.value = value;
        this.type = type;
        this.primary = primary;
    }
    
    
}
