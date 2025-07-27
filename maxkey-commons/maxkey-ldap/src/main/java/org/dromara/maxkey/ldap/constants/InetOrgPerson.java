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
 

package org.dromara.maxkey.ldap.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * InetOrgPerson objectclass attribute
 * top ->  person -> organizationalPerson -> inetOrgPerson
 * @author shimingxy
 *
 */
public class InetOrgPerson {
	public static final List<String> OBJECTCLASS = new ArrayList<>(Arrays.asList("top", "person","organizationalPerson","inetOrgPerson"));
	
	public static final String objectClass				 	 = "inetOrgPerson";
	public static final String DISTINGUISHEDNAME 			 = "distinguishedname";
	//person sup top
	/**person sn MUST*/
	public static final String SN                            = "sn";
	/**person cn MUST*/
	public static final String CN                            = "cn";
	/**person userPassword*/
	public static final String USERPASSWORD                  = "userPassword";
	/**person userPassword*/
	public static final String TELEPHONENUMBER               = "telephoneNumber";
	/**person seeAlso*/
	public static final String SEEALSO                       = "seeAlso";
	/**person description*/
	public static final String DESCRIPTION                   = "description";
	
	//organizationalPerson sup  person
	/**organizationalPerson title*/
	public static final String TITLE                         = "title";
	/**organizationalPerson x121Address*/
	public static final String X121ADDRESS                   = "x121Address";
	/**organizationalPerson registeredAddress*/
	public static final String REGISTEREDADDRESS             = "registeredAddress";
	/**organizationalPerson destinationIndicator*/
	public static final String DESTINATIONINDICATOR          = "destinationIndicator";
	/**organizationalPerson preferredDeliveryMethod*/
	public static final String PREFERREDDELIVERYMETHOD       = "preferredDeliveryMethod";
	/**organizationalPerson telexNumber*/
	public static final String TELEXNUMBER                   = "telexNumber";
	/**organizationalPerson teletexTerminalIdentifier*/
	public static final String TELETEXTERMINALIDENTIFIER     = "teletexTerminalIdentifier";
	/**organizationalPerson internationaliSDNNumber*/
	public static final String INTERNATIONALISDNNUMBER       = "internationaliSDNNumber";
	/**organizationalPerson facsimileTelephoneNumber*/
	public static final String FACSIMILETELEPHONENUMBER      = "facsimileTelephoneNumber";
	/**organizationalPerson street*/
	public static final String STREET                        = "street";
	/**organizationalPerson postOfficeBox*/
	public static final String POSTOFFICEBOX                 = "postOfficeBox";
	/**organizationalPerson postalCode*/
	public static final String POSTALCODE                    = "postalCode";
	/**organizationalPerson postalAddress*/
	public static final String POSTALADDRESS                 = "postalAddress";
	/**organizationalPerson physicalDeliveryOfficeName*/
	public static final String PHYSICALDELIVERYOFFICENAME    = "physicalDeliveryOfficeName";
	/**organizationalPerson ou*/
	public static final String OU                            = "ou";
	/**organizationalPerson st*/
	public static final String ST                            = "st";
	/**organizationalPerson l*/
	public static final String L                             = "l";
	
	//inetOrgPerson sup organizationalPerson
	/**inetOrgPerson carLicense*/
	public static final String CARLICENSE       			 = "carLicense";
	/**inetOrgPerson departmentNumber*/
	public static final String DEPARTMENTNUMBER              = "departmentNumber";
	/**inetOrgPerson displayName*/
	public static final String DISPLAYNAME                   = "displayName";
	/**inetOrgPerson employeeNumber*/
	public static final String EMPLOYEENUMBER                = "employeeNumber";
	/**inetOrgPerson employeeType*/
	public static final String EMPLOYEETYPE                  = "employeeType";
	/**inetOrgPerson jpegPhoto*/
	public static final String JPEGPHOTO                     = "jpegPhoto";
	/**inetOrgPerson preferredLanguage*/
	public static final String PREFERREDLANGUAGE             = "preferredLanguage";
	/**inetOrgPerson userSMIMECertificate*/
	public static final String USERSMIMECERTIFICATE          = "userSMIMECertificate";
	/**inetOrgPerson userPKCS12*/
	public static final String USERPKCS12                    = "userPKCS12";
	/**inetOrgPerson audio*/
	public static final String AUDIO                         = "audio";
	/**inetOrgPerson businessCategory*/
	public static final String BUSINESSCATEGORY              = "businessCategory";
	/**inetOrgPerson givenName*/
	public static final String GIVENNAME                     = "givenName";
	/**inetOrgPerson homePhone*/
	public static final String HOMEPHONE                     = "homePhone";
	/**inetOrgPerson homePostalAddress*/
	public static final String HOMEPOSTALADDRESS             = "homePostalAddress";
	/**inetOrgPerson initials*/
	public static final String INITIALS                      = "initials";
	/**inetOrgPerson photo*/
	public static final String PHOTO                         = "photo";
	/**inetOrgPerson roomNumber*/
	public static final String ROOMNUMBER                    = "roomNumber";
	/**inetOrgPerson secretary*/
	public static final String SECRETARY                     = "secretary";
	/**inetOrgPerson uid*/
	public static final String UID                           = "uid";
	/**inetOrgPerson userCertificate*/
	public static final String USERCERTIFICATE               = "userCertificate";
	/**inetOrgPerson x500uniqueIdentifier*/
	public static final String X500UNIQUEIDENTIFIER          = "x500uniqueIdentifier";
	
	public static final String MAIL 						 = "mail";
	
	public static final String MOBILE 						 = "mobile";
	
	public static final String MANAGER 				    	 = "manager";
	
	//MaxKey EXTEND
	/**
	 * EXTEND department
	 */
    public static final String DEPARTMENT 				     = "department";
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

}
