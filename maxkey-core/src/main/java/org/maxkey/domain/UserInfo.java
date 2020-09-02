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
 

package org.maxkey.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.IOException;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.mybatis.jpa.persistence.JpaBaseDomain;
import org.maxkey.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * .
 * @author Crystal.Sea
 * 
 */
@Table(name = "MXK_USERINFO")
public class UserInfo extends JpaBaseDomain {
    private static final long serialVersionUID = 6402443942083382236L;
    //
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    String id;
    @Column
    protected String username;
    @Column
    protected String password;
    @Column
    protected String decipherable;
    @Column
    protected String sharedSecret;
    @Column
    protected String sharedCounter;
    /**
     * "Employee", "Supplier","Dealer","Contractor",Partner,Customer "Intern",
     * "Temp", "External", and "Unknown" .
     */
    @Column
    protected String userType;
    @Column
    protected String windowsAccount;

    // for user name
    @Column
    protected String displayName;
    @Column
    protected String nickName;
    @Column
    protected String nameZhSpell;
    @Column
    protected String nameZhShortSpell;
    @Column
    protected String givenName;
    @Column
    protected String middleName;
    @Column
    protected String familyName;
    @Column
    protected String honorificPrefix;
    @Column
    protected String honorificSuffix;
    @Column
    protected String formattedName;

    @Column
    protected int married;
    @Column
    protected int gender;
    @Column
    protected String birthDate;
    @JsonIgnore
    @Column
    protected byte[] picture;
    @JsonIgnore
    protected MultipartFile pictureFile;
    @Column
    protected int idType;
    @Column
    protected String idCardNo;
    @Column
    protected String webSite;
    @Column
    protected String startWorkDate;

    // for security
    @Column
    protected int authnType;
    @Column
    protected String email;

    protected int emailVerified;
    @Column
    protected String mobile;

    protected int mobileVerified;

    protected String passwordQuestion;

    protected String passwordAnswer;
    @Column
    // for apps login protected
    protected int appLoginAuthnType;
    protected String appLoginPassword;
    protected String protectedApps;
    @JsonIgnore
    protected HashMap<String, String> protectedAppsMap;

    protected String passwordLastSetTime;
    protected int badPasswordCount;
    protected String badPasswordTime;
    protected String unLockTime;
    protected int isLocked;
    protected String lastLoginTime;
    protected String lastLoginIp;
    protected String lastLogoffTime;
    protected int passwordSetType;
    protected Integer loginCount;

    @Column
    protected String locale;
    @Column
    protected String timeZone;
    @Column
    protected String preferredLanguage;

    // for work
    @Column
    protected String workCountry;
    @Column
    protected String workRegion;// province;
    @Column
    protected String workLocality;// city;
    @Column
    protected String workStreetAddress;
    @Column
    protected String workAddressFormatted;
    @Column
    protected String workEmail;
    @Column
    protected String workPhoneNumber;
    @Column
    protected String workPostalCode;
    @Column
    protected String workFax;
    // for home
    @Column
    protected String homeCountry;
    @Column
    protected String homeRegion;// province;
    @Column
    protected String homeLocality;// city;
    @Column
    protected String homeStreetAddress;
    @Column
    protected String homeAddressFormatted;
    @Column
    protected String homeEmail;
    @Column
    protected String homePhoneNumber;
    @Column
    protected String homePostalCode;
    @Column
    protected String homeFax;
    // for company
    @Column
    protected String employeeNumber;
    @Column
    protected String costCenter;
    @Column
    protected String organization;
    @Column
    protected String division;
    @Column
    protected String departmentId;
    @Column
    protected String department;
    @Column
    protected String jobTitle;
    @Column
    protected String jobLevel;
    @Column
    protected String managerId;
    @Column
    protected String manager;
    @Column
    protected String assistantId;
    @Column
    protected String assistant;
    @Column
    protected String entryDate;
    @Column
    protected String quitDate;

    // for social contact
    @Column
    protected String defineIm;
    protected int weixinFollow;

    protected String theme;
    /*
     * for extended Attribute from userType extraAttribute for database
     * extraAttributeName & extraAttributeValue for page submit
     */
    protected String extraAttribute;
    protected String extraAttributeName;
    protected String extraAttributeValue;
    @JsonIgnore
    protected HashMap<String, String> extraAttributeMap;

    protected int online;

    protected String ldapDn;

    protected int gridList;

    @Column
    String createdBy;
    @Column
    String createdDate;
    @Column
    String modifiedBy;
    @Column
    String modifiedDate;
    @Column
    int status;
    @Column
    String description;

    public static class ONLINE {
        // 在线
        public static final int ONLINE = 1;
        // 下线
        public static final int OFFLINE = 0;
    }

    public static class MARRIED {
        // 未知
        public static final int UNKNOWN = 0;
        // 单身
        public static final int SINGLE = 1;
        // 结婚
        public static final int MARRIED = 2;
        // 离异
        public static final int DIVORCE = 3;
        // 丧偶
        public static final int WIDOWED = 4;

    }

    public static class GENDER {
        // 未知
        public static final int UNKNOWN = 0;
        // 女性
        public static final int FEMALE = 1;
        // 男性
        public static final int MALE = 2;
    }

    public static class IDTYPE {
        // 未知
        public static final int UNKNOWN = 0;
        // 身份证
        public static final int IDCARD = 1;
        // 护照
        public static final int PASSPORT = 2;
        // 学生证
        public static final int STUDENTCARD = 3;
        // 军人证
        public static final int MILITARYCARD = 4;
    }

    public static class AUTHNTYPE {
        // 用户名密码
        public static final int NORMAL = 1;
        // 手机
        public static final int MOBILE = 2;
        // 短信
        public static final int SMS = 3;
        // 邮箱
        public static final int EMAIL = 4;

        public static final int TIMEBASED_OPT = 5;

        public static final int COUNTERBASED_OPT = 6;

        public static final int HOTP_OPT = 7;

        public static final int RSA_OPT = 8;
        // 证书
        public static final int CERTIFICATE = 9;
        // usb证书
        public static final int USBKEY = 10;

    }

    public static class EMPLOYMENTSTATUS {
        // 在册人员
        public static final int ACTIVE = 1;
        // 离职人员
        public static final int WITHDRAWN = 2;
        // 停薪留职人员
        public static final int INACTIVE = 3;
        // 退休人员
        public static final int RETIREE = 4;

    }

    /**
     * 
     */
    public UserInfo() {
        super();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param username
     */
    public UserInfo(String username) {
        super();
        this.username = username;
    }

    /**
     * @param username
     * @param password
     */
    public UserInfo(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public byte[] getPicture() {
        if (pictureFile != null && !pictureFile.isEmpty()) {
            try {
                picture = pictureFile.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return picture;
    }

    /**
     * @return the protectedAppsMap
     */
    public HashMap<String, String> getProtectedAppsMap() {
        if (protectedAppsMap == null) {
            protectedAppsMap = new HashMap<String, String>();
        }
        if (StringUtils.isNotEmpty(protectedApps)) {
            String[] apps = protectedApps.split(",");

            for (String appid : apps) {
                protectedAppsMap.put(appid, appid);
            }
        }
        return protectedAppsMap;
    }

    // auto gen setter and getter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDecipherable() {
        return decipherable;
    }

    public void setDecipherable(String decipherable) {
        this.decipherable = decipherable;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    public String getSharedCounter() {
        return sharedCounter;
    }

    public void setSharedCounter(String sharedCounter) {
        this.sharedCounter = sharedCounter;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getWindowsAccount() {
        return windowsAccount;
    }

    public void setWindowsAccount(String windowsAccount) {
        this.windowsAccount = windowsAccount;
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

   

    public String getNameZhSpell() {
        return nameZhSpell;
    }

    public void setNameZhSpell(String nameZhSpell) {
        this.nameZhSpell = nameZhSpell;
    }

    public String getNameZhShortSpell() {
        return nameZhShortSpell;
    }

    public void setNameZhShortSpell(String nameZhShortSpell) {
        this.nameZhShortSpell = nameZhShortSpell;
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

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
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

    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }

    public int getMarried() {
        return married;
    }

    public void setMarried(int married) {
        this.married = married;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public MultipartFile getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(MultipartFile pictureFile) {
        this.pictureFile = pictureFile;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(String startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public int getAuthnType() {
        return authnType;
    }

    public void setAuthnType(int authnType) {
        this.authnType = authnType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(int emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(int mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public String getPasswordQuestion() {
        return passwordQuestion;
    }

    public void setPasswordQuestion(String passwordQuestion) {
        this.passwordQuestion = passwordQuestion;
    }

    public String getPasswordAnswer() {
        return passwordAnswer;
    }

    public void setPasswordAnswer(String passwordAnswer) {
        this.passwordAnswer = passwordAnswer;
    }

    public int getAppLoginAuthnType() {
        return appLoginAuthnType;
    }

    public void setAppLoginAuthnType(int appLoginAuthnType) {
        this.appLoginAuthnType = appLoginAuthnType;
    }

    public String getAppLoginPassword() {
        return appLoginPassword;
    }

    public void setAppLoginPassword(String appLoginPassword) {
        this.appLoginPassword = appLoginPassword;
    }

    public String getProtectedApps() {
        return protectedApps;
    }

    public void setProtectedApps(String protectedApps) {
        this.protectedApps = protectedApps;
    }

    public String getPasswordLastSetTime() {
        return passwordLastSetTime;
    }

    public void setPasswordLastSetTime(String passwordLastSetTime) {
        this.passwordLastSetTime = passwordLastSetTime;
    }

    public int getBadPasswordCount() {
        return badPasswordCount;
    }

    public void setBadPasswordCount(int badPasswordCount) {
        this.badPasswordCount = badPasswordCount;
    }

    public String getBadPasswordTime() {
        return badPasswordTime;
    }

    public void setBadPasswordTime(String badPasswordTime) {
        this.badPasswordTime = badPasswordTime;
    }

    public String getUnLockTime() {
        return unLockTime;
    }

    public void setUnLockTime(String unLockTime) {
        this.unLockTime = unLockTime;
    }

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLogoffTime() {
        return lastLogoffTime;
    }

    public void setLastLogoffTime(String lastLogoffTime) {
        this.lastLogoffTime = lastLogoffTime;
    }

    public int getPasswordSetType() {
        return passwordSetType;
    }

    public void setPasswordSetType(int passwordSetType) {
        this.passwordSetType = passwordSetType;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getWorkCountry() {
        return workCountry;
    }

    public void setWorkCountry(String workCountry) {
        this.workCountry = workCountry;
    }

    public String getWorkRegion() {
        return workRegion;
    }

    public void setWorkRegion(String workRegion) {
        this.workRegion = workRegion;
    }

    public String getWorkLocality() {
        return workLocality;
    }

    public void setWorkLocality(String workLocality) {
        this.workLocality = workLocality;
    }

    public String getWorkStreetAddress() {
        return workStreetAddress;
    }

    public void setWorkStreetAddress(String workStreetAddress) {
        this.workStreetAddress = workStreetAddress;
    }

    public String getWorkAddressFormatted() {
        return workAddressFormatted;
    }

    public void setWorkAddressFormatted(String workAddressFormatted) {
        this.workAddressFormatted = workAddressFormatted;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getWorkPostalCode() {
        return workPostalCode;
    }

    public void setWorkPostalCode(String workPostalCode) {
        this.workPostalCode = workPostalCode;
    }

    public String getWorkFax() {
        return workFax;
    }

    public void setWorkFax(String workFax) {
        this.workFax = workFax;
    }

    public String getHomeCountry() {
        return homeCountry;
    }

    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }

    public String getHomeRegion() {
        return homeRegion;
    }

    public void setHomeRegion(String homeRegion) {
        this.homeRegion = homeRegion;
    }

    public String getHomeLocality() {
        return homeLocality;
    }

    public void setHomeLocality(String homeLocality) {
        this.homeLocality = homeLocality;
    }

    public String getHomeStreetAddress() {
        return homeStreetAddress;
    }

    public void setHomeStreetAddress(String homeStreetAddress) {
        this.homeStreetAddress = homeStreetAddress;
    }

    public String getHomeAddressFormatted() {
        return homeAddressFormatted;
    }

    public void setHomeAddressFormatted(String homeAddressFormatted) {
        this.homeAddressFormatted = homeAddressFormatted;
    }

    public String getHomeEmail() {
        return homeEmail;
    }

    public void setHomeEmail(String homeEmail) {
        this.homeEmail = homeEmail;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getHomePostalCode() {
        return homePostalCode;
    }

    public void setHomePostalCode(String homePostalCode) {
        this.homePostalCode = homePostalCode;
    }

    public String getHomeFax() {
        return homeFax;
    }

    public void setHomeFax(String homeFax) {
        this.homeFax = homeFax;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(String quitDate) {
        this.quitDate = quitDate;
    }

    public String getExtraAttribute() {
        return extraAttribute;
    }

    public void setExtraAttribute(String extraAttribute) {
        this.extraAttribute = extraAttribute;
    }

    public String getExtraAttributeName() {
        return extraAttributeName;
    }

    public void setExtraAttributeName(String extraAttributeName) {
        this.extraAttributeName = extraAttributeName;
    }

    public String getExtraAttributeValue() {
        return extraAttributeValue;
    }

    public void setExtraAttributeValue(String extraAttributeValue) {
        this.extraAttributeValue = extraAttributeValue;
    }

    public HashMap<String, String> getExtraAttributeMap() {
        return extraAttributeMap;
    }

    public void setExtraAttributeMap(HashMap<String, String> extraAttributeMap) {
        this.extraAttributeMap = extraAttributeMap;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getLdapDn() {
        return ldapDn;
    }

    public void setLdapDn(String ldapDn) {
        this.ldapDn = ldapDn;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public void setProtectedAppsMap(HashMap<String, String> protectedAppsMap) {
        this.protectedAppsMap = protectedAppsMap;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public int getGridList() {
        return gridList;
    }

    public void setGridList(int gridList) {
        this.gridList = gridList;
    }

    public String getDefineIm() {
        return defineIm;
    }

    public void setDefineIm(String defineIm) {
        this.defineIm = defineIm;
    }

    public int getWeixinFollow() {
        return weixinFollow;
    }

    public void setWeixinFollow(int weixinFollow) {
        this.weixinFollow = weixinFollow;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserInfo [username=" + username 
                + ", password=" + password + ", decipherable=" + decipherable
                + ", sharedSecret=" + sharedSecret 
                + ", sharedCounter=" + sharedCounter + ", userType=" + userType
                + ", windowsAccount=" + windowsAccount 
                + ", displayName=" + displayName + ", nickName=" + nickName
                + ", nameZHSpell=" + nameZhSpell 
                + ", nameZHShortSpell=" + nameZhShortSpell 
                + ", givenName=" + givenName
                + ", middleName=" + middleName + ", familyName=" + familyName 
                + ", honorificPrefix=" + honorificPrefix
                + ", honorificSuffix=" + honorificSuffix 
                + ", formattedName=" + formattedName + ", married=" + married
                + ", gender=" + gender + ", birthDate=" + birthDate 
                + ", pictureFile=" + pictureFile + ", idType="
                + idType + ", idCardNo=" + idCardNo + ", webSite=" + webSite 
                + ", startWorkDate=" + startWorkDate
                + ", authnType=" + authnType + ", email=" + email 
                + ", emailVerified=" + emailVerified + ", mobile="
                + mobile + ", mobileVerified=" + mobileVerified 
                + ", passwordQuestion=" + passwordQuestion
                + ", passwordAnswer=" + passwordAnswer + ", appLoginAuthnType=" + appLoginAuthnType
                + ", appLoginPassword=" + appLoginPassword 
                + ", protectedApps=" + protectedApps + ", protectedAppsMap="
                + protectedAppsMap + ", passwordLastSetTime=" + passwordLastSetTime 
                + ", badPasswordCount="
                + badPasswordCount + ", unLockTime=" + unLockTime 
                + ", isLocked=" + isLocked + ", lastLoginTime="
                + lastLoginTime + ", lastLoginIp=" + lastLoginIp 
                + ", lastLogoffTime=" + lastLogoffTime
                + ", passwordSetType=" + passwordSetType 
                + ", loginCount=" + loginCount + ", locale=" + locale
                + ", timeZone=" + timeZone + ", preferredLanguage=" + preferredLanguage 
                + ", workCountry=" + workCountry
                + ", workRegion=" + workRegion + ", workLocality=" + workLocality 
                + ", workStreetAddress="
                + workStreetAddress + ", workAddressFormatted=" + workAddressFormatted 
                + ", workEmail=" + workEmail
                + ", workPhoneNumber=" + workPhoneNumber + ", workPostalCode=" + workPostalCode 
                + ", workFax=" + workFax
                + ", homeCountry=" + homeCountry + ", homeRegion=" + homeRegion 
                + ", homeLocality=" + homeLocality
                + ", homeStreetAddress=" + homeStreetAddress 
                + ", homeAddressFormatted=" + homeAddressFormatted
                + ", homeEmail=" + homeEmail 
                + ", homePhoneNumber=" + homePhoneNumber + ", homePostalCode="
                + homePostalCode + ", homeFax=" + homeFax 
                + ", employeeNumber=" + employeeNumber + ", costCenter="
                + costCenter + ", organization=" + organization 
                + ", division=" + division + ", departmentId="
                + departmentId + ", department=" + department 
                + ", jobTitle=" + jobTitle + ", jobLevel=" + jobLevel
                + ", managerId=" + managerId + ", manager=" + manager 
                + ", assistantId=" + assistantId + ", assistant="
                + assistant + ", entryDate=" + entryDate 
                + ", quitDate=" + quitDate + ", extraAttribute=" + extraAttribute 
                + ", extraAttributeName=" + extraAttributeName + ", extraAttributeValue="
                + extraAttributeValue + ", extraAttributeMap=" + extraAttributeMap 
                + ", online=" + online + ", ldapDn="
                + ldapDn + "]";
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the modifiedDate
     */
    public String getModifiedDate() {
        return modifiedDate;
    }

    /**
     * @param modifiedDate the modifiedDate to set
     */
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

}
