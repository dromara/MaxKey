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
 

package org.maxkey.identity.scim.resources;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class User extends Resource{

    /**
     * 
     */
    private static final long serialVersionUID = -7478787809774041557L;

    public static  String SCHEMA = "urn:ietf:params:scim:schemas:core:2.0:User";

    private  String userName;
    private  UserName name;
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

    private  List<UserEmail> emails;

    private  List<UserPhoneNumber> phoneNumbers;

    private  List<UserIm> ims;

    private  List<UserPhoto> photos;
    // Can't really validate that one. value is not acessible
    private  List<UserAddress> addresses;

    private  List<GroupRef> groups;

    private  List<UserEntitlement> entitlements;

    private  List<UserRole> roles;

    private  List<UserX509Certificate> x509Certificates;
    private  Map<String, UserExtension> extensions;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public UserName getName() {
        return name;
    }
    public void setName(UserName name) {
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
    public List<UserEmail> getEmails() {
        return emails;
    }
    public void setEmails(List<UserEmail> emails) {
        this.emails = emails;
    }
    public List<UserPhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }
    public void setPhoneNumbers(List<UserPhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public List<UserIm> getIms() {
        return ims;
    }
    public void setIms(List<UserIm> ims) {
        this.ims = ims;
    }
    public List<UserPhoto> getPhotos() {
        return photos;
    }
    public void setPhotos(List<UserPhoto> photos) {
        this.photos = photos;
    }
    public List<UserAddress> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<UserAddress> addresses) {
        this.addresses = addresses;
    }
    public List<GroupRef> getGroups() {
        return groups;
    }
    public void setGroups(List<GroupRef> groups) {
        this.groups = groups;
    }
    public List<UserEntitlement> getEntitlements() {
        return entitlements;
    }
    public void setEntitlements(List<UserEntitlement> entitlements) {
        this.entitlements = entitlements;
    }
    public List<UserRole> getRoles() {
        return roles;
    }
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
    public List<UserX509Certificate> getX509Certificates() {
        return x509Certificates;
    }
    public void setX509Certificates(List<UserX509Certificate> x509Certificates) {
        this.x509Certificates = x509Certificates;
    }
    public Map<String, UserExtension> getExtensions() {
        return extensions;
    }
    public void setExtensions(Map<String, UserExtension> extensions) {
        this.extensions = extensions;
    }
    public User() {
        schemas =new HashSet<String>();
        schemas.add(SCHEMA);
    }

    
    
}
