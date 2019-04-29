package org.maxkey.persistence.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.maxkey.domain.UserInfo;
import org.springframework.jdbc.core.RowMapper;

public class UserInfoRowMapper  implements RowMapper<UserInfo> {

	@Override
	public UserInfo mapRow(ResultSet rs, int rowNum)throws SQLException {
		
		UserInfo userInfo=new UserInfo();
		userInfo.setId(rs.getString("ID"));
		userInfo.setUsername(rs.getString("USERNAME"));
		userInfo.setPassword(rs.getString("PASSWORD"));
		userInfo.setSharedSecret(rs.getString("SHAREDSECRET"));
		userInfo.setSharedCounter(rs.getString("SHAREDCOUNTER"));
		userInfo.setDecipherable(rs.getString("DECIPHERABLE"));
		userInfo.setWindowsAccount(rs.getString("WINDOWSACCOUNT"));
		userInfo.setUserType(rs.getString("USERTYPE"));
		
		userInfo.setDisplayName(rs.getString("DISPLAYNAME"));
		userInfo.setNickName(rs.getString("NICKNAME"));
		userInfo.setNameZHSpell(rs.getString("NAMEZHSPELL"));//nameZHSpell
		userInfo.setNameZHShortSpell(rs.getString("NAMEZHSHORTSPELL"));//nameZHSpell
		userInfo.setGivenName(rs.getString("GIVENNAME"));
		userInfo.setMiddleName(rs.getString("MIDDLENAME"));
		userInfo.setFamilyName(rs.getString("FAMILYNAME"));
		userInfo.setHonorificPrefix(rs.getString("HONORIFICPREFIX"));
		userInfo.setHonorificSuffix(rs.getString("HONORIFICSUFFIX"));
		userInfo.setFormattedName(rs.getString("FORMATTEDNAME"));
		
		userInfo.setGender(rs.getInt("GENDER"));
		userInfo.setBirthDate(rs.getString("BIRTHDATE"));
		userInfo.setPicture(rs.getBytes("PICTURE"));
		userInfo.setMarried(rs.getInt("MARRIED"));
		userInfo.setIdType(rs.getInt("IDTYPE"));
		userInfo.setIdCardNo(rs.getString("IDCARDNO"));
		userInfo.setWebSite(rs.getString("WEBSITE"));
		
		userInfo.setAuthnType(rs.getInt("AUTHNTYPE"));
		userInfo.setMobile(rs.getString("MOBILE"));
		userInfo.setMobileVerified(rs.getInt("MOBILEVERIFIED"));
		userInfo.setEmail(rs.getString("EMAIL"));
		userInfo.setEmailVerified(rs.getInt("EMAILVERIFIED"));
		userInfo.setPasswordQuestion(rs.getString("PASSWORDQUESTION"));
		userInfo.setPasswordAnswer(rs.getString("PASSWORDANSWER"));
		
		userInfo.setAppLoginAuthnType(rs.getInt("APPLOGINAUTHNTYPE"));
		userInfo.setAppLoginPassword(rs.getString("APPLOGINPASSWORD"));
		userInfo.setProtectedApps(rs.getString("PROTECTEDAPPS"));			
		
		userInfo.setPasswordLastSetTime(rs.getString("PASSWORDLASTSETTIME"));
		userInfo.setPasswordSetType(rs.getInt("PASSWORDSETTYPE"));
		userInfo.setBadPasswordCount(rs.getInt("BADPASSWORDCOUNT"));
		userInfo.setUnLockTime(rs.getString("UNLOCKTIME"));
		userInfo.setIsLocked(rs.getInt("ISLOCKED"));
		userInfo.setLastLoginTime(rs.getString("LASTLOGINTIME"));
		userInfo.setLastLoginIp(rs.getString("LASTLOGINIP"));
		userInfo.setLastLogoffTime(rs.getString("LASTLOGOFFTIME"));
		userInfo.setLoginCount(rs.getInt("LOGINCOUNT"));
		
		userInfo.setTimeZone(rs.getString("TIMEZONE"));
		userInfo.setLocale(rs.getString("LOCALE"));
		userInfo.setPreferredLanguage(rs.getString("PREFERREDLANGUAGE"));			
		

		userInfo.setWorkEmail(rs.getString("WORKEMAIL"));
		userInfo.setWorkPhoneNumber(rs.getString("WORKPHONENUMBER"));
		userInfo.setWorkCountry(rs.getString("WORKCOUNTRY"));
		userInfo.setWorkRegion(rs.getString("WORKREGION"));
		userInfo.setWorkLocality(rs.getString("WORKLOCALITY"));
		userInfo.setWorkStreetAddress(rs.getString("WORKSTREETADDRESS"));
		userInfo.setWorkAddressFormatted(rs.getString("WORKADDRESSFORMATTED"));
		userInfo.setWorkPostalCode(rs.getString("WORKPOSTALCODE"));
		userInfo.setWorkFax(rs.getString("WORKFAX"));
		
		userInfo.setHomeEmail(rs.getString("HOMEEMAIL"));
		userInfo.setHomePhoneNumber(rs.getString("HOMEPHONENUMBER"));
		userInfo.setHomeCountry(rs.getString("HOMECOUNTRY"));
		userInfo.setHomeRegion(rs.getString("HOMEREGION"));
		userInfo.setHomeLocality(rs.getString("HOMELOCALITY"));
		userInfo.setHomeStreetAddress(rs.getString("HOMESTREETADDRESS"));
		userInfo.setHomeAddressFormatted(rs.getString("HOMEADDRESSFORMATTED"));
		userInfo.setHomePostalCode(rs.getString("HOMEPOSTALCODE"));
		userInfo.setHomeFax(rs.getString("HOMEFAX"));
		
		userInfo.setEmployeeNumber(rs.getString("EMPLOYEENUMBER"));
		userInfo.setDivision(rs.getString("DIVISION"));
		userInfo.setCostCenter(rs.getString("COSTCENTER"));
		userInfo.setOrganization(rs.getString("ORGANIZATION"));
		userInfo.setDepartmentId(rs.getString("DEPARTMENTID"));
		userInfo.setDepartment(rs.getString("DEPARTMENT"));
		userInfo.setJobTitle(rs.getString("JOBTITLE"));
		userInfo.setJobLevel(rs.getString("JOBLEVEL"));
		userInfo.setManagerId(rs.getString("MANAGERID"));
		userInfo.setManager(rs.getString("MANAGER"));
		userInfo.setAssistantId(rs.getString("ASSISTANTID"));
		userInfo.setAssistant(rs.getString("ASSISTANT"));
		userInfo.setEntryDate(rs.getString("ENTRYDATE"));//
		userInfo.setQuitDate(rs.getString("QUITDATE"));
		userInfo.setStartWorkDate(rs.getString("STARTWORKDATE"));//STARTWORKDATE
		
		userInfo.setExtraAttribute(rs.getString("EXTRAATTRIBUTE"));
		
		userInfo.setCreatedBy(rs.getString("CREATEDBY"));
		userInfo.setCreatedDate(rs.getString("CREATEDDATE"));
		userInfo.setModifiedBy(rs.getString("MODIFIEDBY"));
		userInfo.setModifiedDate(rs.getString("MODIFIEDDATE"));
		
		userInfo.setStatus(rs.getInt("STATUS"));
		userInfo.setGridList(rs.getInt("GRIDLIST"));
		userInfo.setDescription(rs.getString("DESCRIPTION"));
		
		return userInfo;
	}	
}
