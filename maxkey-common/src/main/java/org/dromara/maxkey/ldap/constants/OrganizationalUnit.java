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
 * OrganizationalUnit objectclass attribute
 * top
 * @author shimingxy
 *
 */
public class OrganizationalUnit {
	public static final List<String> OBJECTCLASS = new ArrayList<>(Arrays.asList("top", "OrganizationalUnit"));
	public static final String objectClass				 	 = "OrganizationalUnit";
	public static final String DISTINGUISHEDNAME 			 = "distinguishedname";
	/**OrganizationalUnit ou*/
	public static final String OU                         	 = "ou";
	/**OrganizationalUnit userPassword*/
	public static final String USERPASSWORD                  = "userPassword";
	/**OrganizationalUnit searchGuide*/
	public static final String SEARCHGUIDE                   = "searchGuide";
	/**OrganizationalUnit seeAlso*/
	public static final String SEEALSO                       = "seeAlso";
	/**OrganizationalUnit description*/
	public static final String DESCRIPTION                   = "description";
	/**OrganizationalUnit businessCategory*/
	public static final String BUSINESSCATEGORY              = "businessCategory";
	/**OrganizationalUnit x121Address*/
	public static final String X121ADDRESS                   = "x121Address";
	/**OrganizationalUnit registeredAddress*/
	public static final String REGISTEREDADDRESS             = "registeredAddress";
	/**OrganizationalUnit destinationIndicator*/
	public static final String DESTINATIONINDICATOR          = "destinationIndicator";
	/**OrganizationalUnit preferredDeliveryMethod*/
	public static final String PREFERREDDELIVERYMETHOD       = "preferredDeliveryMethod";
	/**OrganizationalUnit telexNumber*/
	public static final String TELEXNUMBER                   = "telexNumber";
	/**OrganizationalUnit teletexTerminalIdentifier*/
	public static final String TELETEXTERMINALIDENTIFIER     = "teletexTerminalIdentifier";
	/**OrganizationalUnit telephoneNumber*/
	public static final String TELEPHONENUMBER     		     = "telephoneNumber";
	/**OrganizationalUnit internationaliSDNNumber*/
	public static final String INTERNATIONALISDNNUMBER       = "internationaliSDNNumber";
	/**OrganizationalUnit facsimileTelephoneNumber*/
	public static final String FACSIMILETELEPHONENUMBER      = "facsimileTelephoneNumber";
	/**OrganizationalUnit street*/
	public static final String STREET                        = "street";
	/**OrganizationalUnit postOfficeBox*/
	public static final String POSTOFFICEBOX                 = "postOfficeBox";
	/**OrganizationalUnit postalCode*/
	public static final String POSTALCODE                    = "postalCode";
	/**OrganizationalUnit postalAddress*/
	public static final String POSTALADDRESS                 = "postalAddress";
	/**OrganizationalUnit physicalDeliveryOfficeName*/
	public static final String PHYSICALDELIVERYOFFICENAME    = "physicalDeliveryOfficeName";
	/**OrganizationalUnit st*/
	public static final String ST                            = "st";//省/州
	/**OrganizationalUnit l*/
	public static final String L                             = "l";//县市

	public static final String CO                            = "co"; //中国
	public static final String C                             = "c"; //CN
	public static final String COUNTRYCODE                   = "countryCode";//156
	public static final String NAME                   		 = "name";
	
	//for id
	public static final String CN                   		 = "cn";
	
}
