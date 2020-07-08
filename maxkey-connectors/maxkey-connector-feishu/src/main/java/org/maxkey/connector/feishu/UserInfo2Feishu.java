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
 

package org.maxkey.connector.feishu;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.maxkey.connector.UserInfoConnector;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.ldap.LdapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component(value = "userInfoConnector")
public class UserInfo2Feishu  extends UserInfoConnector{
	private final static Logger logger = LoggerFactory.getLogger(UserInfo2Feishu.class);

	LdapUtils  ldapUtils;
	
	public UserInfo2Feishu() {
		
	}

	@Override
	public boolean create(UserInfo userInfo) throws Exception{
		logger.info("create");
		try {
			Attributes attributes = new BasicAttributes();
			attributes.put(new BasicAttribute("objectClass","inetOrgPerson"));
			attributes.put(new BasicAttribute("uid",userInfo.getUsername()));
			attributes.put(new BasicAttribute("userPassword",ReciprocalUtils.decoder(userInfo.getDecipherable())));
			attributes.put(new BasicAttribute("displayName",userInfo.getDisplayName()));
			attributes.put(new BasicAttribute("cn",userInfo.getDisplayName()));
			attributes.put(new BasicAttribute("givenName",userInfo.getGivenName()));
			attributes.put(new BasicAttribute("sn",userInfo.getFamilyName()));

			attributes.put(new BasicAttribute("mobile",userInfo.getWorkPhoneNumber()==null?"":userInfo.getWorkPhoneNumber()));
			attributes.put(new BasicAttribute("mail",userInfo.getWorkEmail()==null?"":userInfo.getWorkEmail()));
		
			attributes.put(new BasicAttribute("employeeNumber",userInfo.getEmployeeNumber()==null?"":userInfo.getEmployeeNumber()));
			attributes.put(new BasicAttribute("ou",userInfo.getDepartment()==null?"":userInfo.getDepartment()));
			String managerDn="uid=dummy";
			if(userInfo.getManagerId()==null||userInfo.getManagerId().equals("")){

			}else{
				UserInfo queryManager=new UserInfo();
				queryManager.setId(userInfo.getManagerId());
				UserInfo manager=loadUser(queryManager);
				managerDn="uid="+manager.getUsername()+",dc=users,"+ldapUtils.getBaseDN();
			}
			attributes.put(new BasicAttribute("manager",managerDn));
			attributes.put(new BasicAttribute("departmentNumber",userInfo.getDepartmentId()==null?"":userInfo.getDepartmentId()));
			attributes.put(new BasicAttribute("title",userInfo.getJobTitle()==null?"":userInfo.getJobTitle()));
			
			
			String dn="uid="+userInfo.getUsername()+",dc=users,"+ldapUtils.getBaseDN();
		
			ldapUtils.getCtx().createSubcontext(dn, attributes);
			ldapUtils.close();
			super.create(userInfo);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean update(UserInfo userInfo) throws Exception{
		logger.info("update");
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(&(objectClass=inetOrgPerson)(uid="+userInfo.getUsername()+"))", constraints);
			if (results == null || !results.hasMore()) {
				return create(loadUser(userInfo));
			}
			
			ModificationItem[] modificationItems = new ModificationItem[10];
			modificationItems[0]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("displayName",userInfo.getDisplayName()));
			modificationItems[1]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("cn",userInfo.getDisplayName()));
			modificationItems[2]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("givenName",userInfo.getGivenName()));
			modificationItems[3]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("sn",userInfo.getFamilyName()));
			
			modificationItems[4]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("mobile",userInfo.getWorkPhoneNumber()==null?"":userInfo.getWorkPhoneNumber()));
			modificationItems[5]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("mail",userInfo.getWorkEmail()==null?"":userInfo.getWorkEmail()));
			
			modificationItems[6]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("employeeNumber",userInfo.getEmployeeNumber()==null?"":userInfo.getEmployeeNumber()));
			modificationItems[7]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("ou",userInfo.getDepartment()==null?"":userInfo.getDepartment()));
			modificationItems[8]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("departmentNumber",userInfo.getDepartmentId()==null?"":userInfo.getDepartmentId()));
			modificationItems[9]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("title",userInfo.getJobTitle()==null?"":userInfo.getJobTitle()));
			
			String managerDn="uid=dummy";
			if(userInfo.getManagerId()==null||userInfo.getManagerId().equals("")){

			}else{
				UserInfo queryManager=new UserInfo();
				queryManager.setId(userInfo.getManagerId());
				UserInfo manager=loadUser(queryManager);
				managerDn="uid="+manager.getUsername()+",dc=users,"+ldapUtils.getBaseDN();
			}
			modificationItems[9]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("manager",managerDn));

			
			
			String dn="uid="+userInfo.getUsername()+",dc=users,"+ldapUtils.getBaseDN();
			
			ldapUtils.getCtx().modifyAttributes(dn, modificationItems);
			ldapUtils.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return true;
		
	}
	
	@Override
	public boolean delete(UserInfo userInfo) throws Exception{
		logger.info("delete");
		try {
			String dn="uid="+userInfo.getUsername()+",dc=users,"+ldapUtils.getBaseDN();
			ldapUtils.getCtx().destroySubcontext(dn);
			ldapUtils.close();
			super.delete(userInfo);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return true;
	}

	public UserInfo loadUser(UserInfo UserInfo) {
		return null;
	}
}
