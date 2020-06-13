package org.maxkey.identity.scim.resources;

public class EnterpriseUser extends User {
    /**
     * 
     */
    private static final long serialVersionUID = 3212312511630459427L;
    
    public static final String SCHEMA = "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User";
    
    Enterprise enterprise;
    
    public EnterpriseUser() {
    }
    
    
}
