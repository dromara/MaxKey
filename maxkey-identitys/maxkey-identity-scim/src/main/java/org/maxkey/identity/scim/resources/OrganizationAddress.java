package org.maxkey.identity.scim.resources;

import java.io.Serializable;

public class OrganizationAddress extends MultiValuedAttribute implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7401597570364338298L;
    private  String formatted;
    private  String streetAddress;
    private  String locality;
    private  String region;
    private  String postalCode;
    private  String country;
    
    public static class UserAddressType {
        public static final String WORK = "work";
        public static final String HOME = "home";
        public static final String OTHER = "other";

    }
    
    public String getFormatted() {
        return formatted;
    }
    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }
    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getLocality() {
        return locality;
    }
    public void setLocality(String locality) {
        this.locality = locality;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public OrganizationAddress() {
    }
    
    
}
