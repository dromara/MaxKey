package org.maxkey.identity.scim.resources;

public class Manager {

    private  String managerId;
    private  String displayName;
    public String getManagerId() {
        return managerId;
    }
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public Manager() {
    }
    public Manager(String managerId, String displayName) {
        super();
        this.managerId = managerId;
        this.displayName = displayName;
    }

}
