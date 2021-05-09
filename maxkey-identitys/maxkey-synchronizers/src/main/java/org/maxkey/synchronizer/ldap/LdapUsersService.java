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
 

package org.maxkey.synchronizer.ldap;

import java.util.HashMap;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.maxkey.constants.ldap.InetOrgPerson;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.ldap.LdapUtils;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LdapUsersService  implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(LdapUsersService.class);

	LdapUtils ldapUtils;
	
	@Autowired
	UserInfoService userInfoService;
	
	public void sync() {
		_logger.info("Sync Users...");
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(&(objectClass=inetOrgPerson))", constraints);
			
			while (null != results && results.hasMoreElements()) {
				Object obj = results.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult si = (SearchResult) obj;
					_logger.trace("name " + si.getName());
					_logger.info("NameInNamespace " + si.getNameInNamespace());
					
					HashMap<String,Attribute> attributeMap = new HashMap<String,Attribute>();
					NamingEnumeration<? extends Attribute>  attrs = si.getAttributes().getAll();
					while (null != attrs && attrs.hasMoreElements()) {
						Attribute  objAttrs = attrs.nextElement();
						_logger.trace("attribute "+objAttrs.getID() + " , " + objAttrs.get());
						attributeMap.put(objAttrs.getID(), objAttrs);
					}
					
					UserInfo userInfo  = buildUserInfo(attributeMap,si.getName(),si.getNameInNamespace());
					_logger.info("userInfo " + userInfo);
				}
			}

			//ldapUtils.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void postSync(UserInfo userInfo) {
		
	}

	public UserInfo buildUserInfo(HashMap<String,Attribute> attributeMap,String name,String nameInNamespace) {
		UserInfo userInfo = new  UserInfo();
		userInfo.setLdapDn(nameInNamespace);
		
		try {
			userInfo.setFormattedName(LdapUtils.getAttributeStringValue(InetOrgPerson.CN,attributeMap));//閸忋劌鎮�
			//鐠愶附鍩�
			userInfo.setUsername(LdapUtils.getAttributeStringValue(InetOrgPerson.UID,attributeMap));//鐠愶箑褰�
			userInfo.setFamilyName(LdapUtils.getAttributeStringValue(InetOrgPerson.SN,attributeMap));//婵拷
			userInfo.setGivenName(LdapUtils.getAttributeStringValue(InetOrgPerson.GIVENNAME,attributeMap));//閸氾拷
			userInfo.setNickName(LdapUtils.getAttributeStringValue(InetOrgPerson.INITIALS,attributeMap));//閺勭數袨
			userInfo.setNameZhShortSpell(LdapUtils.getAttributeStringValue(InetOrgPerson.INITIALS,attributeMap));//閼昏鲸鏋冪紓鈺佸晸
			userInfo.setDisplayName(LdapUtils.getAttributeStringValue(InetOrgPerson.DISPLAYNAME,attributeMap));//閺勫墽銇氶崥宥囆�
			
			userInfo.setEmployeeNumber(LdapUtils.getAttributeStringValue(InetOrgPerson.EMPLOYEENUMBER,attributeMap));
			userInfo.setDepartment(LdapUtils.getAttributeStringValue(InetOrgPerson.OU,attributeMap));
			userInfo.setDepartmentId(LdapUtils.getAttributeStringValue(InetOrgPerson.DEPARTMENTNUMBER,attributeMap));
			userInfo.setJobTitle(LdapUtils.getAttributeStringValue(InetOrgPerson.TITLE,attributeMap));//閼卞苯濮�
			userInfo.setWorkOfficeName(LdapUtils.getAttributeStringValue(InetOrgPerson.PHYSICALDELIVERYOFFICENAME,attributeMap));//閸旂偛鍙曠�癸拷
			userInfo.setWorkEmail(LdapUtils.getAttributeStringValue(InetOrgPerson.MAIL,attributeMap));//闁喕娆�
			userInfo.setWorkRegion(LdapUtils.getAttributeStringValue(InetOrgPerson.ST,attributeMap));//閻拷
			userInfo.setWorkLocality(LdapUtils.getAttributeStringValue(InetOrgPerson.L,attributeMap));//閸橈拷
			userInfo.setWorkStreetAddress(LdapUtils.getAttributeStringValue(InetOrgPerson.STREET,attributeMap));//鐞涙浜�
			userInfo.setWorkPostalCode(LdapUtils.getAttributeStringValue(InetOrgPerson.POSTALCODE,attributeMap));//闁喚绱�
			userInfo.setWorkAddressFormatted(LdapUtils.getAttributeStringValue(InetOrgPerson.POSTOFFICEBOX,attributeMap));//闁喗鏂傞柇顔绢唸
			userInfo.setWorkFax(LdapUtils.getAttributeStringValue(InetOrgPerson.FACSIMILETELEPHONENUMBER,attributeMap));
			
			userInfo.setHomePhoneNumber(LdapUtils.getAttributeStringValue(InetOrgPerson.HOMEPHONE,attributeMap));//鐎硅泛娑甸悽浣冪樈
			userInfo.setHomeAddressFormatted(LdapUtils.getAttributeStringValue(InetOrgPerson.HOMEPOSTALADDRESS,attributeMap));//閻絻鐦芥径鍥ㄦ暈
			
			userInfo.setMobile(LdapUtils.getAttributeStringValue(InetOrgPerson.MOBILE,attributeMap));//閹靛婧�
			
			userInfo.setPreferredLanguage(LdapUtils.getAttributeStringValue(InetOrgPerson.PREFERREDLANGUAGE,attributeMap));//鐠囶叀鈻�
			
			userInfo.setDescription(LdapUtils.getAttributeStringValue(InetOrgPerson.DESCRIPTION,attributeMap));//閹诲繗鍫�
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	
	public LdapUtils getLdapUtils() {
		return ldapUtils;
	}

	public void setLdapUtils(LdapUtils ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	
}
