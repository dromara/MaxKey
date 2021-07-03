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
 

package org.maxkey.synchronizer.activedirectory;

import java.util.HashMap;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.maxkey.constants.ldap.ActiveDirectoryUser;
import org.maxkey.entity.HistorySynchronizer;
import org.maxkey.entity.Organizations;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.ldap.ActiveDirectoryUtils;
import org.maxkey.persistence.ldap.LdapUtils;
import org.maxkey.synchronizer.AbstractSynchronizerService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ActiveDirectoryUsersService extends AbstractSynchronizerService    implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(ActiveDirectoryUsersService.class);

	ActiveDirectoryUtils ldapUtils;
	
	public void sync() {
		_logger.info("Sync Users...");
		loadOrgsById("1");
		genSessionId();
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(&(objectClass=User))", constraints);
			
			long recordCount = 0;
			while (null != results && results.hasMoreElements()) {
				Object obj = results.nextElement();
				if (obj instanceof SearchResult) {
					recordCount ++;
					SearchResult si = (SearchResult) obj;
					_logger.info("Sync Users  Record " + recordCount+" --------------------------------------------------");
					_logger.trace("name " + si.getName());
					_logger.info("NameInNamespace " + si.getNameInNamespace());
					
					HashMap<String,Attribute> attributeMap = new HashMap<String,Attribute>();
					NamingEnumeration<? extends Attribute>  attrs = si.getAttributes().getAll();
					while (null != attrs && attrs.hasMoreElements()) {
						Attribute  objAttrs = attrs.nextElement();
						_logger.trace("attribute "+objAttrs.getID() + " : " + objAttrs.get());
						attributeMap.put(objAttrs.getID().toLowerCase(), objAttrs);
					}
					
					UserInfo userInfo =buildUserInfo(attributeMap,si.getName(),si.getNameInNamespace());
					
					
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
		if(nameInNamespace.indexOf("CN=Users,DC=maxkey,DC=top")>-1
		        ||nameInNamespace.indexOf("OU=Domain Controllers,DC=maxkey,DC=top")>-1) {
		    _logger.info("to skip.");
		    return null;
		}
	    UserInfo userInfo = new  UserInfo();
		userInfo.setLdapDn(nameInNamespace);
		nameInNamespace = nameInNamespace.replaceAll(",OU=", "/").replaceAll("OU=", "/").replaceAll("CN=", "/");
        nameInNamespace = nameInNamespace.substring(0, nameInNamespace.length() - ldapUtils.getBaseDN().length() - 1);
        _logger.info("nameInNamespace  " + nameInNamespace);
        String []namePaths = nameInNamespace.split("/");
        String namePah= "/"+rootOrganization.getName();
        for(int i = namePaths.length -1 ; i>=1 ;i--) {
            namePah = namePah + "/"+namePaths[i];
        }
        //namePah = namePah.substring(0, namePah.length());
        String deptNamePath= namePah.substring(0, namePah.lastIndexOf("/"));
        _logger.info("deptNamePath  " + deptNamePath);
        Organizations  deptOrg = orgsNamePathMap.get(deptNamePath);
        userInfo.setDepartment(deptOrg.getName());
        userInfo.setDepartmentId(deptOrg.getId());
		try {
		    userInfo.setId(userInfo.generateId());
			userInfo.setFormattedName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.CN,attributeMap));//閸忋劌鎮�
			//鐠愶附鍩�
			userInfo.setUsername(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.SAMACCOUNTNAME,attributeMap));//鐠愶箑褰�
			userInfo.setWindowsAccount(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.USERPRINCIPALNAME,attributeMap));//閻ц缍�
			
			//鐢瓕顫�
			userInfo.setFamilyName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.SN,attributeMap));//婵拷
			userInfo.setGivenName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.GIVENNAME,attributeMap));//閸氾拷
			userInfo.setNickName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.INITIALS,attributeMap));//閺勭數袨
			userInfo.setNameZhShortSpell(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.INITIALS,attributeMap));//閼昏鲸鏋冪紓鈺佸晸
			userInfo.setDisplayName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.DISPLAYNAME,attributeMap));//閺勫墽銇氶崥宥囆�
			userInfo.setDescription(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.DESCRIPTION,attributeMap));//閹诲繗鍫�
			userInfo.setWorkPhoneNumber(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.TELEPHONENUMBER,attributeMap));//閻絻鐦介崣椋庣垳
			userInfo.setWorkOfficeName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.PHYSICALDELIVERYOFFICENAME,attributeMap));//閸旂偛鍙曠�癸拷
			userInfo.setWorkEmail(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.MAIL,attributeMap));//闁喕娆�
			userInfo.setWebSite(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.WWWHOMEPAGE,attributeMap));//缂冩垿銆�
			//閸︽澘娼�
			userInfo.setWorkCountry(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.CO,attributeMap));//閸ヨ棄顔�
			userInfo.setWorkRegion(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.ST,attributeMap));//閻拷
			userInfo.setWorkLocality(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.L,attributeMap));//閸橈拷
			userInfo.setWorkStreetAddress(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.STREETADDRESS,attributeMap));//鐞涙浜�
			userInfo.setWorkPostalCode(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.POSTALCODE,attributeMap));//闁喚绱�
			userInfo.setWorkAddressFormatted(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.POSTOFFICEBOX,attributeMap));//闁喗鏂傞柇顔绢唸
			
			if(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.MOBILE,attributeMap).equals("")) {
			    userInfo.setMobile(userInfo.getId());
			}else {
			    userInfo.setMobile(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.MOBILE,attributeMap));//閹靛婧�
			}
			userInfo.setHomePhoneNumber(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.HOMEPHONE,attributeMap));//鐎硅泛娑甸悽浣冪樈
			userInfo.setWorkFax(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.FACSIMILETELEPHONENUMBER,attributeMap));//娴肩姷婀�
			userInfo.setHomeAddressFormatted(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.INFO,attributeMap));//閻絻鐦芥径鍥ㄦ暈
			
			userInfo.setDivision(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.COMPANY,attributeMap)); //閸忣剙寰�
			//userInfo.setDepartment(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.DEPARTMENT,attributeMap)); //闁劑妫�
			//userInfo.setDepartmentId(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.DEPARTMENT,attributeMap)); //闁劑妫紓鏍у娇
			userInfo.setJobTitle(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.TITLE,attributeMap));//閼卞苯濮�
			userInfo.setUserState("RESIDENT");
			userInfo.setUserType("EMPLOYEE");
			userInfo.setTimeZone("Asia/Shanghai");
			userInfo.setStatus(1);
			UserInfo quser=new UserInfo();
			quser.setUsername(userInfo.getUsername());
			UserInfo loadedUser=userInfoService.load(quser);
			if(loadedUser == null) {
			    
			    userInfo.setPassword(userInfo.generateId());
			    userInfoService.insert(userInfo);
			    HistorySynchronizer historySynchronizer =new HistorySynchronizer();
	            historySynchronizer.setId(historySynchronizer.generateId());
	            historySynchronizer.setSessionId(this.getSessionId());
	            historySynchronizer.setSyncId(this.synchronizer.getId());
	            historySynchronizer.setSyncName(this.synchronizer.getName());
	            historySynchronizer.setObjectId(userInfo.getId());
	            historySynchronizer.setObjectName(userInfo.getUsername());
	            historySynchronizer.setObjectType(Organizations.class.getSimpleName());
	            historySynchronizer.setResult("success");
	            this.historySynchronizerService.insert(historySynchronizer);
			}else {
			    _logger.info("username  " + userInfo.getUsername()+" exists.");
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	public ActiveDirectoryUtils getLdapUtils() {
		return ldapUtils;
	}

	public void setLdapUtils(ActiveDirectoryUtils ldapUtils) {
		this.ldapUtils = ldapUtils;
	}
	
}
