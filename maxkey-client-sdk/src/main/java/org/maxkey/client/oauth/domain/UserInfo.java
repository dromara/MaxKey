package org.maxkey.client.oauth.domain;

import java.util.Arrays;


/**
 * @author Crystal.Sea
 * 
 */
public class UserInfo extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6402443942083382236L;
	//

	protected String username;

	/**
	 * "Employee", "Supplier","Dealer","Contractor",Partner,Customer "Intern",
	 * "Temp", "External", and "Unknown"
	 */
	protected String userType;
	protected String windowsAccount;

	// for user name
	protected String displayName;
	protected String nickName;
	protected String nameZHSpell;
	protected String nameZHShortSpell;
	protected String givenName;
	protected String middleName;
	protected String familyName;
	protected String honorificPrefix;
	protected String honorificSuffix;
	protected String formattedName;

	protected int married;
	protected int gender;
	protected String birthDate;
	protected byte[] picture;
	protected int idType;
	protected String idCardNo;
	protected String webSite;
	protected String startWorkDate;

	// for security
	protected int authnType;
	protected String email;
	protected String mobile;
	protected int mobileVerified;

	protected String lastLoginTime;
	protected String lastLoginIp;
	protected String lastLogoffTime;
	protected int passwordSetType;
	protected Integer loginCount;

	protected String locale;
	protected String timeZone;
	protected String preferredLanguage;

	// for work
	protected String workCountry;
	protected String workRegion;// province;
	protected String workLocality;// city;
	protected String workStreetAddress;
	protected String workAddressFormatted;
	protected String workEmail;
	protected String workPhoneNumber;
	protected String workPostalCode;
	protected String workFax;
	// for home
	protected String homeCountry;
	protected String homeRegion;// province;
	protected String homeLocality;// city;
	protected String homeStreetAddress;
	protected String homeAddressFormatted;
	protected String homeEmail;
	protected String homePhoneNumber;
	protected String homePostalCode;
	protected String homeFax;
	// for company
	protected String employeeNumber;
	protected String costCenter;
	protected String organization;
	protected String division;
	protected String departmentId;
	protected String department;
	protected String jobTitle;
	protected String jobLevel;
	protected String managerId;
	protected String manager;
	protected String assistantId;
	protected String assistant;
	protected String entryDate;
	protected String quitDate;

	// for social contact
	/**
	 * QQ WeiXin SinaWeibo Gtalk YiXin IMessage Skype Yahoo MSN Aim ICQ Xmpp
	 */
	protected String ims;
	/*
	 * for extended Attribute from userType extraAttribute for database
	 * extraAttributeName & extraAttributeValue for page submit
	 */
	protected String extraAttribute;

	protected int online;

	public static class ONLINE {
		// 在线
		public static int ONLINE = 0;
		// 下线
		public static int OFFLINE = 1;
	}

	public static class MARRIED {
		// 未知
		public static int UNKNOWN = 0;
		// 单身
		public static int SINGLE = 1;
		// 结婚
		public static int MARRIED = 2;
		// 离异
		public static int DIVORCE = 3;
		// 丧偶
		public static int WIDOWED = 4;

	}

	public static class GENDER {
		// 未知
		public static int UNKNOWN = 0;
		// 女性
		public static int FEMALE = 1;
		// 男性
		public static int MALE = 2;
	}

	public static class IDTYPE {
		// 未知
		public static int UNKNOWN = 0;
		// 身份证
		public static int IDCARD = 1;
		// 护照
		public static int PASSPORT = 2;
		// 学生证
		public static int STUDENTCARD = 3;
		// 军人证
		public static int MILITARYCARD = 4;
	}

	public static class AUTHNTYPE {
		// 用户名密码
		public static int NORMAL = 1;
		// 手机
		public static int MOBILE = 2;
		// 短信
		public static int SMS = 3;
		// 邮箱
		public static int EMAIL = 4;

		public static int TIMEBASED_OPT = 5;

		public static int COUNTERBASED_OPT = 6;

		public static int HOTP_OPT = 7;

		public static int RSA_OPT = 8;
		// 证书
		public static int CERTIFICATE = 9;
		// usb证书
		public static int USBKEY = 10;

	}

	/**
	 * 
	 */
	public UserInfo() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getNameZHSpell() {
		return nameZHSpell;
	}

	public void setNameZHSpell(String nameZHSpell) {
		this.nameZHSpell = nameZHSpell;
	}

	public String getNameZHShortSpell() {
		return nameZHShortSpell;
	}

	public void setNameZHShortSpell(String nameZHShortSpell) {
		this.nameZHShortSpell = nameZHShortSpell;
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

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
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

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
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

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
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

	public String getIms() {
		return ims;
	}

	public void setIms(String ims) {
		this.ims = ims;
	}

	public String getExtraAttribute() {
		return extraAttribute;
	}

	public void setExtraAttribute(String extraAttribute) {
		this.extraAttribute = extraAttribute;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	@Override
	public String toString() {
		return "UserInfo [username=" + username + ", userType=" + userType
				+ ", windowsAccount=" + windowsAccount + ", displayName="
				+ displayName + ", nickName=" + nickName + ", nameZHSpell="
				+ nameZHSpell + ", nameZHShortSpell=" + nameZHShortSpell
				+ ", givenName=" + givenName + ", middleName=" + middleName
				+ ", familyName=" + familyName + ", honorificPrefix="
				+ honorificPrefix + ", honorificSuffix=" + honorificSuffix
				+ ", formattedName=" + formattedName + ", married=" + married
				+ ", gender=" + gender + ", birthDate=" + birthDate
				+ ", picture=" + Arrays.toString(picture) + ", idType="
				+ idType + ", idCardNo=" + idCardNo + ", webSite=" + webSite
				+ ", startWorkDate=" + startWorkDate + ", authnType="
				+ authnType + ", email=" + email + ", mobile=" + mobile
				+ ", mobileVerified=" + mobileVerified + ", lastLoginTime="
				+ lastLoginTime + ", lastLoginIp=" + lastLoginIp
				+ ", lastLogoffTime=" + lastLogoffTime + ", passwordSetType="
				+ passwordSetType + ", loginCount=" + loginCount + ", locale="
				+ locale + ", timeZone=" + timeZone + ", preferredLanguage="
				+ preferredLanguage + ", workCountry=" + workCountry
				+ ", workRegion=" + workRegion + ", workLocality="
				+ workLocality + ", workStreetAddress=" + workStreetAddress
				+ ", workAddressFormatted=" + workAddressFormatted
				+ ", workEmail=" + workEmail + ", workPhoneNumber="
				+ workPhoneNumber + ", workPostalCode=" + workPostalCode
				+ ", workFax=" + workFax + ", homeCountry=" + homeCountry
				+ ", homeRegion=" + homeRegion + ", homeLocality="
				+ homeLocality + ", homeStreetAddress=" + homeStreetAddress
				+ ", homeAddressFormatted=" + homeAddressFormatted
				+ ", homeEmail=" + homeEmail + ", homePhoneNumber="
				+ homePhoneNumber + ", homePostalCode=" + homePostalCode
				+ ", homeFax=" + homeFax + ", employeeNumber=" + employeeNumber
				+ ", costCenter=" + costCenter + ", organization="
				+ organization + ", division=" + division + ", departmentId="
				+ departmentId + ", department=" + department + ", jobTitle="
				+ jobTitle + ", jobLevel=" + jobLevel + ", managerId="
				+ managerId + ", manager=" + manager + ", assistantId="
				+ assistantId + ", assistant=" + assistant + ", entryDate="
				+ entryDate + ", quitDate=" + quitDate + ", ims=" + ims
				+ ", extraAttribute=" + extraAttribute + ", online=" + online
				+ "]";
	}

}
