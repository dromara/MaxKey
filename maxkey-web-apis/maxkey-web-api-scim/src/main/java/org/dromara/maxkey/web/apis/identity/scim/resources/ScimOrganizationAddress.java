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

public class ScimOrganizationAddress extends ScimMultiValuedAttribute implements Serializable {

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
    public ScimOrganizationAddress() {
    }
    
    
}
