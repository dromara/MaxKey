package org.maxkey.identity.scim.resources;

import java.io.Serializable;

public class UserName  implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2482724471550531523L;
    private  String formatted;
    private  String familyName;
    private  String givenName;
    private  String middleName;
    private  String honorificPrefix;
    private  String honorificSuffix;
    public String getFormatted() {
        return formatted;
    }
    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }
    public String getFamilyName() {
        return familyName;
    }
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    public String getGivenName() {
        return givenName;
    }
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getHonorificPrefix() {
        return honorificPrefix;
    }
    public void setHonorificPrefix(String honorificPrefix) {
        this.honorificPrefix = honorificPrefix;
    }
    public String getHonorificSuffix() {
        return honorificSuffix;
    }
    public void setHonorificSuffix(String honorificSuffix) {
        this.honorificSuffix = honorificSuffix;
    }
    public UserName(String formatted, String familyName, String givenName, String middleName, String honorificPrefix,
            String honorificSuffix) {
        super();
        this.formatted = formatted;
        this.familyName = familyName;
        this.givenName = givenName;
        this.middleName = middleName;
        this.honorificPrefix = honorificPrefix;
        this.honorificSuffix = honorificSuffix;
    }
    public UserName() {
    }

    
}
