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
 

package org.dromara.maxkey.synchronizer.ldap;

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
import org.dromara.maxkey.crypto.DigestUtils;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.entity.history.HistorySynchronizer;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.ldap.LdapUtils;
import org.dromara.maxkey.ldap.constants.InetOrgPerson;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.entity.SyncJobConfigField;
import org.dromara.maxkey.synchronizer.service.SyncJobConfigFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.dromara.maxkey.synchronizer.utils.FieldUtil.getFieldValue;
import static org.dromara.maxkey.synchronizer.utils.FieldUtil.setFieldValue;

@Service
public class LdapUsersService extends AbstractSynchronizerService  implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(LdapUsersService.class);
	@Autowired
	public SyncJobConfigFieldService syncJobConfigFieldService;

	private static final Integer USER_TYPE = 1;
	LdapUtils ldapUtils;
	
	public void sync() {
		_logger.info("Sync Ldap Users ...");
		loadOrgsByInstId(this.synchronizer.getInstId(),Organizations.ROOT_ORG_ID);
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			String filter = StringUtils.isNotBlank(this.getSynchronizer().getUserFilters()) ? 
								getSynchronizer().getUserFilters() : "(&(objectClass=inetOrgPerson))";
			_logger.debug(" User filter {} ",filter);
			NamingEnumeration<SearchResult> results = 
					ldapUtils.getConnection().search(ldapUtils.getBaseDN(), filter, constraints);
			
			long recordCount = 0;
			while (null != results && results.hasMoreElements()) {
				Object obj = results.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult sr = (SearchResult) obj;
					_logger.debug("Sync User {} , name [{}] , NameInNamespace [{}]" , 
				    				(++recordCount),sr.getName(),sr.getNameInNamespace());
					
					HashMap<String,Attribute> attributeMap = new HashMap<String,Attribute>();
					NamingEnumeration<? extends Attribute>  attrs = sr.getAttributes().getAll();
					while (null != attrs && attrs.hasMoreElements()) {
						Attribute  objAttrs = attrs.nextElement();
						_logger.trace("attribute {} : {}" ,
											objAttrs.getID(), 
											LdapUtils.getAttrStringValue(objAttrs)
								);
						attributeMap.put(objAttrs.getID(), objAttrs);
					}
					String originId = DigestUtils.md5B64(sr.getNameInNamespace());
					UserInfo userInfo  = buildUserInfo(attributeMap,sr.getName(),sr.getNameInNamespace());
					userInfo.setPassword(userInfo.getUsername() + UserInfo.DEFAULT_PASSWORD_SUFFIX);
					userInfoService.saveOrUpdate(userInfo);
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
        userInfo.setDepartment(deptOrg.getOrgName());
        userInfo.setDepartmentId(deptOrg.getId());
        
		try {
		    userInfo.setId(userInfo.generateId());
		    String cn  = LdapUtils.getAttributeStringValue(InetOrgPerson.CN,attributeMap);
			String uid = LdapUtils.getAttributeStringValue(InetOrgPerson.UID,attributeMap);
			String sn  = LdapUtils.getAttributeStringValue(InetOrgPerson.SN,attributeMap);
			String givenName = LdapUtils.getAttributeStringValue(InetOrgPerson.GIVENNAME,attributeMap);
			String initials  = LdapUtils.getAttributeStringValue(InetOrgPerson.INITIALS,attributeMap);
			String displayName = LdapUtils.getAttributeStringValue(InetOrgPerson.DISPLAYNAME,attributeMap);
			userInfo.setFormattedName(sn + givenName);
			if(StringUtils.isBlank(uid)) {
				userInfo.setUsername(cn);
				userInfo.setWindowsAccount(cn);
			}else {
				userInfo.setUsername(uid);
				userInfo.setWindowsAccount(uid);
			}
			userInfo.setFamilyName(sn);
			userInfo.setGivenName(givenName);
			if(StringUtils.isBlank(initials)) {
				userInfo.setNickName(sn + givenName);
				userInfo.setNameZhShortSpell(sn + givenName);
			}else {
				userInfo.setNickName(initials);
				userInfo.setNameZhShortSpell(initials);
			}
			if(StringUtils.isBlank(displayName)) {
				userInfo.setDisplayName(sn + givenName);
			}else {
				userInfo.setDisplayName(displayName);
			}
			
			userInfo.setEmployeeNumber(LdapUtils.getAttributeStringValue(InetOrgPerson.EMPLOYEENUMBER,attributeMap));
			//userInfo.setDepartment(LdapUtils.getAttributeStringValue(InetOrgPerson.OU,attributeMap));
			//userInfo.setDepartmentId(LdapUtils.getAttributeStringValue(InetOrgPerson.DEPARTMENTNUMBER,attributeMap));
			userInfo.setJobTitle(LdapUtils.getAttributeStringValue(InetOrgPerson.TITLE,attributeMap));
			userInfo.setWorkOfficeName(LdapUtils.getAttributeStringValue(InetOrgPerson.PHYSICALDELIVERYOFFICENAME,attributeMap));
			userInfo.setWorkEmail(LdapUtils.getAttributeStringValue(InetOrgPerson.MAIL,attributeMap));
			userInfo.setWorkRegion(LdapUtils.getAttributeStringValue(InetOrgPerson.ST,attributeMap));
			userInfo.setWorkLocality(LdapUtils.getAttributeStringValue(InetOrgPerson.L,attributeMap));
			userInfo.setWorkStreetAddress(LdapUtils.getAttributeStringValue(InetOrgPerson.STREET,attributeMap));
			userInfo.setWorkPostalCode(LdapUtils.getAttributeStringValue(InetOrgPerson.POSTALCODE,attributeMap));
			userInfo.setWorkAddressFormatted(LdapUtils.getAttributeStringValue(InetOrgPerson.POSTOFFICEBOX,attributeMap));
			userInfo.setWorkFax(LdapUtils.getAttributeStringValue(InetOrgPerson.FACSIMILETELEPHONENUMBER,attributeMap));
			
			userInfo.setHomePhoneNumber(LdapUtils.getAttributeStringValue(InetOrgPerson.HOMEPHONE,attributeMap));
			userInfo.setHomeAddressFormatted(LdapUtils.getAttributeStringValue(InetOrgPerson.HOMEPOSTALADDRESS,attributeMap));
			
			if(LdapUtils.getAttributeStringValue(InetOrgPerson.MOBILE,attributeMap).equals("")) {
			    userInfo.setMobile(userInfo.getId());
			}else {
			    userInfo.setMobile(LdapUtils.getAttributeStringValue(InetOrgPerson.MOBILE,attributeMap));
            }
			
			userInfo.setPreferredLanguage(LdapUtils.getAttributeStringValue(InetOrgPerson.PREFERREDLANGUAGE,attributeMap));
			
			userInfo.setDescription(LdapUtils.getAttributeStringValue(InetOrgPerson.DESCRIPTION,attributeMap));
			userInfo.setUserState("RESIDENT");
			userInfo.setUserType("EMPLOYEE");
			userInfo.setTimeZone("Asia/Shanghai");
			userInfo.setStatus(1);
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
		Map<String, String> fieldMap = getFieldMap(Long.parseLong(synchronizer.getId()));
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

		userInfo.setLdapDn(nameInNamespace);
		userInfo.setId(userInfo.generateId());
		userInfo.setUserState("RESIDENT");
		userInfo.setUserType("EMPLOYEE");
		userInfo.setTimeZone("Asia/Shanghai");
		userInfo.setStatus(1);
		userInfo.setInstId(this.synchronizer.getInstId());

		for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
			String  targetAttr = entry.getKey();
			String sourceAttr = entry.getValue();
			String value = null;
			try {
				
				if(!attributeMap.keySet().contains(sourceAttr.toLowerCase())){
					value = (String) getFieldValue(deptOrg, sourceAttr);
					if(value!=null){
						setFieldValue(userInfo,targetAttr,value);
						continue;
					}
				}
				value = LdapUtils.getAttributeStringValue(sourceAttr,attributeMap);
				if(targetAttr.equals("formattedName")){
					userInfo.setFormattedName(LdapUtils.getAttributeStringValue(InetOrgPerson.SN,attributeMap)+
							LdapUtils.getAttributeStringValue(InetOrgPerson.GIVENNAME,attributeMap));
					continue;
				}
				//只配置 username 到 uid 的映射关系
				///只配置 windowsAccount 到 uid 的映射关系
				if (targetAttr.equals("username") || targetAttr.equals("windowsAccount")) {
					if (sourceAttr.equals("uid") && StringUtils.isBlank(value)) {
						value = LdapUtils.getAttributeStringValue(InetOrgPerson.CN,attributeMap);
					}else{
						value = LdapUtils.getAttributeStringValue(InetOrgPerson.UID,attributeMap);
					}
					//只配置 nickName 到 initials 的映射关系
					//只配置 nameZhShortSpell 到 initials 的映射关系
				} else if (targetAttr.equals("nickName") || targetAttr.equals("nameZhShortSpell")) {
					if (sourceAttr.equals("initials") && StringUtils.isBlank(value)) {
						value = LdapUtils.getAttributeStringValue(InetOrgPerson.SN,attributeMap) +
								LdapUtils.getAttributeStringValue(InetOrgPerson.GIVENNAME,attributeMap);
					}else{
						value = LdapUtils.getAttributeStringValue(InetOrgPerson.INITIALS,attributeMap);
					}

					//只配置 displayName 到 displayName 的映射关系
				} else if (targetAttr.equals("displayName")) {
					if (sourceAttr.equals("displayName") && StringUtils.isBlank(value)) {
						value = LdapUtils.getAttributeStringValue(InetOrgPerson.SN,attributeMap) +
								LdapUtils.getAttributeStringValue(InetOrgPerson.GIVENNAME,attributeMap);
					}else {
						value = LdapUtils.getAttributeStringValue(InetOrgPerson.DISPLAYNAME,attributeMap);
					}
				} else if (targetAttr.equals("mobile")) {
					if (sourceAttr.equals("mobile") && StringUtils.isBlank(value)) {
						value = (String) getFieldValue(userInfo,"id");
					}else {
						value = LdapUtils.getAttributeStringValue(InetOrgPerson.MOBILE,attributeMap);
					}
				}

				setFieldValue(userInfo, targetAttr,value);
			}catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}

		}
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

		return userInfo;
	}

	public Map<String,String> getFieldMap(Long jobId){
		Map<String,String> userFieldMap = new HashMap<>();
		//根据job id查询属性映射表
		List<SyncJobConfigField> syncJobConfigFieldList = syncJobConfigFieldService.findByJobId(jobId);
		//获取用户属性映射
		for(SyncJobConfigField element:syncJobConfigFieldList){
			if(Integer.parseInt(element.getObjectType()) == USER_TYPE.intValue()){
				userFieldMap.put(element.getTargetField(), element.getSourceField());
			}
		}
		return userFieldMap;
	}

	
	public LdapUtils getLdapUtils() {
		return ldapUtils;
	}

	public void setLdapUtils(LdapUtils ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

	public SyncJobConfigFieldService getSyncJobConfigFieldService() {
		return syncJobConfigFieldService;
	}

	public void setSyncJobConfigFieldService(SyncJobConfigFieldService syncJobConfigFieldService) {
		this.syncJobConfigFieldService = syncJobConfigFieldService;
	}
}
