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
