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
 

package org.maxkey.connector.activedirectory;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.maxkey.connector.GroupConnector;
import org.maxkey.domain.GroupMember;
import org.maxkey.domain.Groups;
import org.maxkey.persistence.ldap.ActiveDirectoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "groupConnector")
public class Group2Activedirectory extends GroupConnector {
	private final static Logger logger = LoggerFactory.getLogger(Group2Activedirectory.class);

	ActiveDirectoryUtils  ldapUtils;
	
	public Group2Activedirectory() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean create(Groups group)  throws Exception{
		logger.info("create");
		try {
			Attributes attributes = new BasicAttributes();
			Attribute objectClass = new BasicAttribute("objectClass");
			objectClass.add("top");
			objectClass.add("group");
			attributes.put(objectClass);
			
			attributes.put(new BasicAttribute("cn",group.getName()));
			attributes.put(new BasicAttribute("member","CN=dummy,"+ldapUtils.getBaseDN()));
			
			String dn="cn="+group.getName()+",cn=groups,"+ldapUtils.getBaseDN();
		
			ldapUtils.getCtx().createSubcontext(dn, attributes);
			ldapUtils.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Groups group)  throws Exception{
		logger.info("update");
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(cn="+group.getName()+")", constraints);
			String oldDn="";
			String rdn="";
			if (results == null || !results.hasMore()) {
				return create(group);
			}else{
				SearchResult sr = (SearchResult) results.next();
				oldDn =sr.getNameInNamespace();
				String[] dnSplit=oldDn.split(",");
				rdn=oldDn.substring(oldDn.indexOf(","), oldDn.length());
				
				String groupName=dnSplit[0].split("=")[1];
				if(group.getName()!=groupName){
					String newDn="cn="+group.getName()+","+rdn;
					ldapUtils.getCtx().rename(oldDn, newDn);
					ModificationItem[] modificationItems = new ModificationItem[1];
					modificationItems[0]=new ModificationItem(DirContext.REMOVE_ATTRIBUTE,new BasicAttribute("cn",groupName));
					ldapUtils.getCtx().modifyAttributes(newDn, modificationItems);
				}
			}
			
			ldapUtils.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(Groups group) throws Exception {
		logger.info("delete");
		try {
			String dn="cn="+group.getName()+",cn=groups,"+ldapUtils.getBaseDN();
			ldapUtils.getCtx().destroySubcontext(dn);
			ldapUtils.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean addMember(GroupMember groupMember) throws Exception {
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(cn="+groupMember.getGroupName()+")", constraints);
			if (results == null || !results.hasMore()) {
				Groups  group =new Groups();
				group.setName(groupMember.getGroupName());
				return create(group);
			}
			
			
			String uniqueMember="";
			SearchControls memberSearchControls = new SearchControls();
			logger.debug("user Search : "+"(sAMAccountName="+groupMember.getMemberName()+")");
			memberSearchControls.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> memberResults = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(sAMAccountName="+groupMember.getMemberName()+")", memberSearchControls);
			if (memberResults == null || !memberResults.hasMore()) {
				
			}else{
				SearchResult memberSr = (SearchResult) memberResults.next();
				uniqueMember =memberSr.getNameInNamespace();
				logger.debug("uniqueMember : "+uniqueMember);
				ModificationItem[] modificationItems = new ModificationItem[1];
				modificationItems[0]=new ModificationItem(DirContext.ADD_ATTRIBUTE,new BasicAttribute("member",uniqueMember));
				
				String dn="cn="+groupMember.getGroupName()+",cn=groups,"+ldapUtils.getBaseDN();
				
				ldapUtils.getCtx().modifyAttributes(dn, modificationItems);
			}
			
			
			ldapUtils.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean deleteMember(GroupMember groupMember)  throws Exception{
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(cn="+groupMember.getGroupName()+")", constraints);
			if (results == null || !results.hasMore()) {
				return true;
			}
			
			String uniqueMember="";
			SearchControls memberSearchControls = new SearchControls();
			memberSearchControls.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> memberResults = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(sAMAccountName="+groupMember.getMemberName()+")", memberSearchControls);
			if (memberResults == null || !memberResults.hasMore()) {
				
			}else{
				SearchResult memberSr = (SearchResult) memberResults.next();
				uniqueMember =memberSr.getNameInNamespace();
				logger.debug("uniqueMember : "+uniqueMember);
				ModificationItem[] modificationItems = new ModificationItem[1];
				modificationItems[0]=new ModificationItem(DirContext.REMOVE_ATTRIBUTE,new BasicAttribute("member",uniqueMember));
				
				String dn="cn="+groupMember.getGroupName()+",cn=groups,"+ldapUtils.getBaseDN();
				
				ldapUtils.getCtx().modifyAttributes(dn, modificationItems);
			}

			ldapUtils.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
