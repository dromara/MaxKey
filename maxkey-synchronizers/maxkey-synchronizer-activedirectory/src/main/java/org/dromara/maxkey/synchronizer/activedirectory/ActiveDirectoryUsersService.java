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
 

package org.dromara.maxkey.synchronizer.activedirectory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.crypto.DigestUtils;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.entity.history.HistorySynchronizer;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.ldap.LdapUtils;
import org.dromara.maxkey.ldap.activedirectory.ActiveDirectoryUtils;
import org.dromara.maxkey.ldap.activedirectory.constants.ActiveDirectoryUser;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.entity.SyncJobConfigField;
import org.dromara.maxkey.synchronizer.service.SyncJobConfigFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.dromara.maxkey.synchronizer.utils.FieldUtil.setFieldValue;

@Service
public class ActiveDirectoryUsersService extends AbstractSynchronizerService    implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(ActiveDirectoryUsersService.class);
	@Autowired
	private SyncJobConfigFieldService syncJobConfigFieldService;

	private static final Integer USER_TYPE = 1;
	ActiveDirectoryUtils ldapUtils;
	
	public void sync() {
		_logger.info("Sync ActiveDirectory Users...");
		loadOrgsByInstId(this.synchronizer.getInstId(),Organizations.ROOT_ORG_ID);
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			String filter = StringUtils.isNotBlank(this.getSynchronizer().getUserFilters())?
								getSynchronizer().getUserFilters() : "(&(objectClass=User))";
			NamingEnumeration<SearchResult> results = 
					ldapUtils.getConnection().search(ldapUtils.getBaseDN(), filter, constraints);
			
			long recordCount = 0;
			while (null != results && results.hasMoreElements()) {
				Object obj = results.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult sr = (SearchResult) obj;
					if(sr.getNameInNamespace().contains("CN=Users,")
					        ||sr.getNameInNamespace().contains("OU=Domain Controllers,")) {
					    _logger.trace("Skip 'CN=Users' or 'OU=Domain Controllers' . ");
					    continue;
					}
					_logger.debug("Sync User {} , name [{}] , NameInNamespace [{}]" , 
						    		(++recordCount),sr.getName(),sr.getNameInNamespace());
					
					HashMap<String,Attribute> attributeMap = new HashMap<String,Attribute>();
					NamingEnumeration<? extends Attribute>  attrs = sr.getAttributes().getAll();
					while (null != attrs && attrs.hasMoreElements()) {
						Attribute  objAttrs = attrs.nextElement();
						_logger.trace("attribute {} : {}" ,
												objAttrs.getID(), 
												ActiveDirectoryUtils.getAttrStringValue(objAttrs)
									);
						attributeMap.put(objAttrs.getID().toLowerCase(), objAttrs);
					}
					
					String originId = DigestUtils.md5B64(sr.getNameInNamespace());
					
					UserInfo userInfo =buildUserInfo(attributeMap,sr.getName(),sr.getNameInNamespace());
					if(userInfo != null) {
						userInfo.setPassword(userInfo.getUsername() + UserInfo.DEFAULT_PASSWORD_SUFFIX);
						userInfoService.saveOrUpdate(userInfo);
						_logger.info("userInfo " + userInfo);
						
						SynchroRelated synchroRelated = new SynchroRelated(
								userInfo.getId(),
								userInfo.getUsername(),
								userInfo.getDisplayName(),
								UserInfo.CLASS_TYPE,
								synchronizer.getId(),
								synchronizer.getName(),
								originId,
								userInfo.getDisplayName(),
								"",
								"",
								synchronizer.getInstId());
						
						synchroRelatedService.updateSynchroRelated(
								this.synchronizer,synchroRelated,UserInfo.CLASS_TYPE);
					}
				}
			}

			//ldapUtils.close();
		} catch (NamingException e) {
			_logger.error("NamingException " , e);
		}
		
	}

	public UserInfo buildUserInfo(HashMap<String,Attribute> attributeMap,String name,String nameInNamespace) {
		
	    UserInfo userInfo = new  UserInfo();
		userInfo.setLdapDn(nameInNamespace);
		String []namePaths = name.replaceAll(",OU=" , "/")
								 .replaceAll("OU="  , "/")
								 .replaceAll(",ou=" , "/")
								 .replaceAll("ou="  , "/")
								 .split("/");
		
		String namePah= "/"+rootOrganization.getOrgName();
		for(int i = namePaths.length -1 ; i >= 0 ; i --) {
			namePah = namePah + "/" + namePaths[i];
		}
			
        namePah = namePah.substring(0, namePah.length());
        String deptNamePath= namePah.substring(0, namePah.lastIndexOf("/"));
        _logger.info("deptNamePath  " + deptNamePath);
		
        Organizations  deptOrg = orgsNamePathMap.get(deptNamePath);
        if(deptOrg == null ) {
        	deptOrg = rootOrganization;
		}

        userInfo.setDepartment(deptOrg.getOrgName());
        userInfo.setDepartmentId(deptOrg.getId());
		try {
		    userInfo.setId(userInfo.generateId());
			userInfo.setFormattedName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.CN,attributeMap));//cn
			//
			userInfo.setUsername(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.SAMACCOUNTNAME,attributeMap));//WindowsAccount
			userInfo.setWindowsAccount(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.SAMACCOUNTNAME,attributeMap));
			//userInfo.setWindowsAccount(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.USERPRINCIPALNAME,attributeMap));//
			
			//
			userInfo.setFamilyName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.SN,attributeMap));//Last Name/SurName
			userInfo.setGivenName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.GIVENNAME,attributeMap));//First Name
			userInfo.setNickName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.INITIALS,attributeMap));//Initials
			userInfo.setNameZhShortSpell(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.INITIALS,attributeMap));//Initials
			userInfo.setDisplayName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.DISPLAYNAME,attributeMap));//
			userInfo.setDescription(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.DESCRIPTION,attributeMap));//
			userInfo.setWorkPhoneNumber(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.TELEPHONENUMBER,attributeMap));//
			userInfo.setWorkOfficeName(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.PHYSICALDELIVERYOFFICENAME,attributeMap));//
			userInfo.setWorkEmail(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.MAIL,attributeMap));//
			userInfo.setWebSite(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.WWWHOMEPAGE,attributeMap));//
			//
			userInfo.setWorkCountry(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.CO,attributeMap));//
			userInfo.setWorkRegion(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.ST,attributeMap));//
			userInfo.setWorkLocality(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.L,attributeMap));//
			userInfo.setWorkStreetAddress(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.STREETADDRESS,attributeMap));//
			userInfo.setWorkPostalCode(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.POSTALCODE,attributeMap));//
			userInfo.setWorkAddressFormatted(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.POSTOFFICEBOX,attributeMap));//
			
			if(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.MOBILE,attributeMap).equals("")) {
			    userInfo.setMobile(userInfo.getId());
			}else {
			    userInfo.setMobile(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.MOBILE,attributeMap));//
			}
			userInfo.setHomePhoneNumber(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.HOMEPHONE,attributeMap));//
			userInfo.setWorkFax(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.FACSIMILETELEPHONENUMBER,attributeMap));//
			userInfo.setHomeAddressFormatted(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.INFO,attributeMap));//
			
			userInfo.setDivision(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.COMPANY,attributeMap)); //
			//userInfo.setDepartment(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.DEPARTMENT,attributeMap)); //
			//userInfo.setDepartmentId(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.DEPARTMENT,attributeMap)); //
			userInfo.setJobTitle(LdapUtils.getAttributeStringValue(ActiveDirectoryUser.TITLE,attributeMap));//
			userInfo.setUserState("RESIDENT");
			userInfo.setUserType("EMPLOYEE");
			userInfo.setTimeZone("Asia/Shanghai");
			userInfo.setStatus(ConstsStatus.ACTIVE);
			userInfo.setInstId(this.synchronizer.getInstId());
			
		    HistorySynchronizer historySynchronizer =new HistorySynchronizer();
            historySynchronizer.setId(historySynchronizer.generateId());
            historySynchronizer.setSyncId(this.synchronizer.getId());
            historySynchronizer.setSyncName(this.synchronizer.getName());
            historySynchronizer.setObjectId(userInfo.getId());
            historySynchronizer.setObjectName(userInfo.getUsername());
            historySynchronizer.setObjectType(Organizations.class.getSimpleName());
            historySynchronizer.setInstId(synchronizer.getInstId());
            historySynchronizer.setResult("success");
            this.historySynchronizerService.insert(historySynchronizer);

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	public UserInfo buildUserInfoByFieldMap(HashMap<String,Attribute> attributeMap,String name,String nameInNamespace){
		UserInfo userInfo = new  UserInfo();
		userInfo.setLdapDn(nameInNamespace);
		userInfo.setId(userInfo.generateId());
		String []namePaths = name.replaceAll(",OU=" , "/")
				.replaceAll("OU="  , "/")
				.replaceAll(",ou=" , "/")
				.replaceAll("ou="  , "/")
				.split("/");

		String namePah= "/"+rootOrganization.getOrgName();
		for(int i = namePaths.length -1 ; i >= 0 ; i --) {
			namePah = namePah + "/" + namePaths[i];
		}

		namePah = namePah.substring(0, namePah.length());
		String deptNamePath= namePah.substring(0, namePah.lastIndexOf("/"));
		_logger.info("deptNamePath  " + deptNamePath);
		
		Organizations  deptOrg = orgsNamePathMap.get(deptNamePath);
		if(deptOrg == null ) {
			deptOrg = rootOrganization;
		}
		Map<String, String> fieldMap = getFieldMap(Long.parseLong(synchronizer.getId()));
		for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
			String userInfoProperty = entry.getKey();
			String sourceProperty = entry.getValue();
			try {
				
				if(sourceProperty.equals("orgName")){
					userInfo.setDepartment(deptOrg.getOrgName());
					continue;
				}
				if(sourceProperty.equals("id")){
					userInfo.setDepartmentId(deptOrg.getId());
					continue;
				}
				if(sourceProperty.equals("mobile")){
					userInfo.setMobile(LdapUtils.getAttributeStringValue(sourceProperty, attributeMap).equals("")?
							userInfo.getId():LdapUtils.getAttributeStringValue(sourceProperty,attributeMap));
					continue;
				}
				// 获取源属性的值
				Object sourceValue = LdapUtils.getAttributeStringValue(sourceProperty, attributeMap);
				// 设置到 UserInfo 对象
				if (sourceValue != null) {
					setFieldValue(userInfo, userInfoProperty, sourceValue);
				}
			} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}

		try {
			userInfo.setLdapDn(nameInNamespace);
			userInfo.setUserState("RESIDENT");
			userInfo.setUserType("EMPLOYEE");
			userInfo.setTimeZone("Asia/Shanghai");
			userInfo.setStatus(ConstsStatus.ACTIVE);
			userInfo.setInstId(this.synchronizer.getInstId());

			
			HistorySynchronizer historySynchronizer =new HistorySynchronizer();
			historySynchronizer.setId(historySynchronizer.generateId());
			historySynchronizer.setSyncId(this.synchronizer.getId());
			historySynchronizer.setSyncName(this.synchronizer.getName());
			historySynchronizer.setObjectId(userInfo.getId());
			historySynchronizer.setObjectName(userInfo.getUsername());
			historySynchronizer.setObjectType(Organizations.class.getSimpleName());
			historySynchronizer.setInstId(synchronizer.getInstId());
			historySynchronizer.setResult("success");
			this.historySynchronizerService.insert(historySynchronizer);
		} catch (Exception e) {
			e.printStackTrace();
		}



		return userInfo;
	}

	public Map<String,String> getFieldMap(Long jobId){
		Map<String,String> fieldMap = new HashMap<>();
		//根据job id查询属性映射表
		List<SyncJobConfigField> syncJobConfigFieldList = syncJobConfigFieldService.findByJobId(jobId);
		//获取用户属性映射
		for(SyncJobConfigField element:syncJobConfigFieldList){
			if(Integer.parseInt(element.getObjectType()) == USER_TYPE.intValue()){
				fieldMap.put(element.getTargetField(), element.getSourceField());
			}
		}
		return fieldMap;
	}

	public ActiveDirectoryUtils getLdapUtils() {
		return ldapUtils;
	}

	public void setLdapUtils(ActiveDirectoryUtils ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

	public SyncJobConfigFieldService getSyncJobConfigFieldService() {
		return syncJobConfigFieldService;
	}

	public void setSyncJobConfigFieldService(SyncJobConfigFieldService syncJobConfigFieldService) {
		this.syncJobConfigFieldService = syncJobConfigFieldService;
	}
}
