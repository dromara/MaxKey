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

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScimUser extends ScimResource{

    /**
     * 
     */
    private static final long serialVersionUID = -7478787809774041557L;

    public static final String SCHEMA = "urn:ietf:params:scim:schemas:core:2.0:User";
    
    public static final String SCHEMA_ENTERPRISE = "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User";

    private  String userName;
    private  ScimFormattedName name;
    private  String displayName;
    private  String nickName;
    private  String profileUrl;
    private  String title;
    private  String userType;
    private  String preferredLanguage;
    private  String locale;
    private  String timezone;
    private  Boolean active;
    private  String password;

    private  List<ScimUserEmail> emails;

    private  List<ScimUserPhoneNumber> phoneNumbers;
    
    @JsonProperty(SCHEMA_ENTERPRISE)
    ScimEnterprise enterprise;
    
    private  List<ScimUserIm> ims;

    private  List<ScimUserPhoto> photos;
    // Can't really validate that one. value is not acessible
    private  List<ScimUserAddress> addresses;

    private  List<ScimGroupRef> groups;

    private  List<ScimUserEntitlement> entitlements;

    private  List<ScimUserRole> roles;

    private  List<ScimUserX509Certificate> x509Certificates;
    private  Map<String, ScimUserExtension> extensions;
    
    // T/IDAC 002—2021
    private  List<String> organization;
    private  List<String> group;
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public ScimFormattedName getName() {
        return name;
    }
    public void setName(ScimFormattedName name) {
        this.name = name;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getProfileUrl() {
        return profileUrl;
    }
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getPreferredLanguage() {
        return preferredLanguage;
    }
    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
    public String getTimezone() {
        return timezone;
    }
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<ScimUserEmail> getEmails() {
        return emails;
    }
    public void setEmails(List<ScimUserEmail> emails) {
        this.emails = emails;
    }
    public List<ScimUserPhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }
    public void setPhoneNumbers(List<ScimUserPhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public List<ScimUserIm> getIms() {
        return ims;
    }
    public void setIms(List<ScimUserIm> ims) {
        this.ims = ims;
    }
    public List<ScimUserPhoto> getPhotos() {
        return photos;
    }
    public void setPhotos(List<ScimUserPhoto> photos) {
        this.photos = photos;
    }
    public List<ScimUserAddress> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<ScimUserAddress> addresses) {
        this.addresses = addresses;
    }
    public List<ScimGroupRef> getGroups() {
        return groups;
    }
    public void setGroups(List<ScimGroupRef> groups) {
        this.groups = groups;
    }
    public List<ScimUserEntitlement> getEntitlements() {
        return entitlements;
    }
    public void setEntitlements(List<ScimUserEntitlement> entitlements) {
        this.entitlements = entitlements;
    }
    public List<ScimUserRole> getRoles() {
        return roles;
    }
    public void setRoles(List<ScimUserRole> roles) {
        this.roles = roles;
    }
    public List<ScimUserX509Certificate> getX509Certificates() {
        return x509Certificates;
    }
    public void setX509Certificates(List<ScimUserX509Certificate> x509Certificates) {
        this.x509Certificates = x509Certificates;
    }
    public Map<String, ScimUserExtension> getExtensions() {
        return extensions;
    }
    public void setExtensions(Map<String, ScimUserExtension> extensions) {
        this.extensions = extensions;
    }
    
    
    public ScimEnterprise getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(ScimEnterprise enterprise) {
		this.enterprise = enterprise;
	}

	public List<String> getOrganization() {
		return organization;
	}
	public void setOrganization(List<String> organization) {
		this.organization = organization;
	}
	public List<String> getGroup() {
		return group;
	}
	public void setGroup(List<String> group) {
		this.group = group;
	}
	public ScimUser() {
        schemas =new HashSet<String>();
        schemas.add(SCHEMA);
    }

    
    
}
