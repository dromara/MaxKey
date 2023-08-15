/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.constants.ldap;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ActiveDirectoryUser objectclass attribute
 * top ->  person -> organizationalPerson -> user
 * @author shimingxy
 *
 */

public class ActiveDirectoryUser {
	public static ArrayList<String> OBJECTCLASS = new ArrayList<>(Arrays.asList("top", "person", "organizationalPerson", "user"));
	
	public static String	   objectClass				 	 = "user";
	/** userAccountControl值得说明
	 * http://support.microsoft.com/zh-cn/kb/305144
	 * https://docs.microsoft.com/en-us/troubleshoot/windows-server/identity/useraccountcontrol-manipulate-account-properties
	 */
	public static class userAccountControl{
							//Property flag				Value in hexadecimal	Value in decimal
		public static final int	SCRIPT							=0x0001			;//		1
		public static final int	ACCOUNTDISABLE					=0x0002			;//		2
		public static final int	HOMEDIR_REQUIRED				=0x0008			;//		8
		public static final int	LOCKOUT							=0x0010			;//		16
		public static final int	PASSWD_NOTREQD					=0x0020			;//		32
		public static final int	PASSWD_CANT_CHANGE				=0x0040			;//		64    		You can't assign this permission by directly modifying the UserAccountControl attribute. For information about how to set the permission programmatically, see the Property flag descriptions section.
		public static final int	ENCRYPTED_TEXT_PWD_ALLOWED		=0x0080			;//		128
		public static final int	TEMP_DUPLICATE_ACCOUNT			=0x0100			;//		256
		public static final int	NORMAL_ACCOUNT					=0x0200			;//		512
		public static final int	INTERDOMAIN_TRUST_ACCOUNT		=0x0800			;//		2048
		public static final int	WORKSTATION_TRUST_ACCOUNT		=0x1000			;//		4096
		public static final int	SERVER_TRUST_ACCOUNT			=0x2000			;//		8192
		public static final int	DONT_EXPIRE_PASSWORD			=0x10000		;//		65536
		public static final int	MNS_LOGON_ACCOUNT				=0x20000		;//		131072
		public static final int	SMARTCARD_REQUIRED				=0x40000		;//		262144
		public static final int	TRUSTED_FOR_DELEGATION			=0x80000		;//		524288
		public static final int	NOT_DELEGATED					=0x100000		;//		1048576
		public static final int	USE_DES_KEY_ONLY				=0x200000		;//		2097152
		public static final int	DONT_REQ_PREAUTH				=0x400000		;//		4194304
		public static final int	PASSWORD_EXPIRED				=0x800000		;//		8388608
		public static final int	TRUSTED_TO_AUTH_FOR_DELEGATION	=0x1000000		;//		16777216
		public static final int	PARTIAL_SECRETS_ACCOUNT			=0x04000000		;//		67108864
	}
	
    /*
	 *常规
	 *  名				First Name			givenName
	 *	姓				Last Name/SurName 	sn 
	 *	英文缩写			Initials			initials
	 *	描述				Description			description
	 *	办公室			Office				physicalDeliveryOfficeName
	 *	电话号码			Telephone Number	telephoneNumber
	 *	电话号码			Telephone: Other	otherTelephone
	 *	电子邮件			E-Mail				mail
	 *	网页				Web Page			wwwHomePage
	 *	Web Page: 			Other	url
	 * 
	 *	家庭电话			Home				telephoneNumber
	 *					Home: Other			otherTelephone
	 *	寻呼机			Pager				pager
	 *					Pager: Other		pagerOther
	 *	移动电话			Mobile				mobile
	 *					Mobile: Other		otherMobile
	 *	传真				Fax					facsimileTelephoneNumber
	 *					Fax: Other			otherFacsimileTelephoneNumber
	 *	IP电话			IP phone			ipPhone
	 *					IP phone: Other		otherIpPhone
	 *	注释				Notes				info
	 *帐号
	 *	用户登录名 		UserLogon Name		userPrincipalName
	 *	用户登录名（以前版本）User logon name (pre-Windows 2000)	sAMAccountname
	 *	登录时间			Logon Hours			logonHours
	 *	登录到 			Log On To			logonWorkstation
	 *	用户帐户控制 	Account is locked out	userAccountControl   (启用：512，禁用：514， 密码永不过期：66048)  
	 *				Other Account Options	userAccountControl
	 *	User must change password at next logon	pwdLastSet
	 *	User cannot change password	N/A
	 *	帐户过期  			Account Expires		accountExpires
	 *
	 *地址
	 *	街道				Street				streetAddress
	 *	邮政信箱			P.O.Box				postOfficeBox
	 *	邮政编码			Zip/Postal Code		postalCode
	 *	市/县				City				l
	 *	省/自治区			State/Province		st
	 *	国家/地区 			Country/Region		c,co, and countryCode
	 *
	 *单位
	 *	职务 				Title				title
	 *	部门				Department			department
	 *	公司			 	Company				company
	 *	经理 				Manager:Name		manager
	 *	直接汇报人			Direct Reports		directReports
	 *
     *成员
     *	成员组			Member of			memberOf
     *	主要组			Set Primary Group 	primaryGroupID
	 */

	public static final String CN 							= "cn";
	public static final String NAME 						= "name";
	public static final String UID 							= "uid";
	
	/**
	 * First Name
	 */
	public static final String GIVENNAME 					= "givenName";
	/**
	 * Last Name/SurName
	 */
	public static final String SN 							= "sn";
	public static final String INITIALS 					= "initials";
	public static final String DESCRIPTION 					= "description";
	public static final String PHYSICALDELIVERYOFFICENAME 	= "physicalDeliveryOfficeName";
	public static final String MAIL 						= "mail";
	public static final String WWWHOMEPAGE 					= "wwwHomePage";
	public static final String DISPLAYNAME 					= "displayName";

	public static final String TELEPHONENUMBER 				= "telephoneNumber";
	public static final String OTHERTELEPHONE 				= "otherTelephone";
	public static final String PAGER 						= "pager";
	public static final String PAGEROTHER 					= "pagerOther";
	public static final String MOBILE 						= "mobile";
	public static final String OTHERMOBILE 					= "otherMobile";
	public static final String FACSIMILETELEPHONENUMBER 	= "facsimileTelephoneNumber";
	public static final String OTHERFACSIMILETELEPHONENUMBER = "otherFacsimileTelephoneNumber";
	public static final String IPPHONE 						= "ipPhone";
	public static final String OTHERIPPHONE 				= "otherIpPhone";
	public static final String INFO 						= "info";
	public static final String HOMEPHONE 					= "homePhone";
	
	/**
	 * admin@maxkey.top
	 */
	public static final String USERPRINCIPALNAME 			= "userPrincipalName";
	/**
	 * maxkey\admin
	 */
	public static final String SAMACCOUNTNAME 				= "sAMAccountname";
	public static final String LOGONHOURS 					= "logonHours";
	public static final String LOGONWORKSTATION 			= "logonWorkstation";
	public static final String USERACCOUNTCONTROL		 	= "userAccountControl";
	public static final String PWDLASTSET 					= "pwdLastSet";
	public static final String ACCOUNTEXPIRES 				= "accountExpires";
	
	public static final String CO 							= "co";
	public static final String C 							= "c";
	public static final String COUNTRYCODE 					= "countryCode";
	public static final String ST 							= "st";
	public static final String L 							= "l";
	public static final String STREETADDRESS 				= "streetAddress";
	public static final String POSTOFFICEBOX 				= "postOfficeBox";
	public static final String POSTALCODE 					= "postalCode";

	public static final String TITLE 						= "title";
	public static final String COMPANY 						= "company";
	public static final String DEPARTMENT 					= "department";
	public static final String EMPLOYEENUMBER 				= "employeeNumber";
	public static final String OU 							= "ou";
	public static final String DEPARTMENTNUMBER 			= "departmentNumber";
	
	public static final String MANAGER 						= "manager";
	public static final String DIRECTREPORTS 				= "directReports";

	public static final String MEMBER   					= "member";
	public static final String MEMBEROF 					= "memberOf";
	public static final String PRIMARYGROUPID 				= "primaryGroupID";
	
	public static final String UNICODEPWD 					= "unicodePwd";
	public static final String DISTINGUISHEDNAME 			= "distinguishedname";
	
	//MaxKey EXTEND
	/**
	 * EXTEND managerName
	 */
	public static final String MANAGERNAME                  = "managerName";
	/**
	 * EXTEND username
	 */
	public static final String USERNAME                     = "username";
	/**
	 * EXTEND userType
	 */
	public static final String USERTYPE                     = "userType";
	/**
	 * EXTEND gender
	 */
	public static final String GENDER                       = "gender";
	/**
	 * EXTEND status
	 */
	public static final String USERSTATUS                   = "status";
    /**
	 * EXTEND firstName
	 */
    public static final String FIRSTNAME 				     = "firstName";
    /**
	 * EXTEND lastName
	 */
    public static final String LASTNAME 				     = "lastName";
    /**
	 * EXTEND email
	 */
    public static final String EMAIL 				     	 = "email";
	
	/**
	 * encodePassword for ActiveDirectory
	 * @param password
	 * @return 
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] encodePassword(String password) throws UnsupportedEncodingException {
	        return ("\"" + password + "\"").getBytes("UTF-16LE");
	}
	
	
}
