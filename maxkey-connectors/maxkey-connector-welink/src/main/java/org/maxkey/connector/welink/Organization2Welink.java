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
 

package org.maxkey.connector.welink;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.maxkey.connector.OrganizationConnector;
import org.maxkey.domain.Organizations;
import org.maxkey.persistence.ldap.LdapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "organizationConnector")
public class Organization2Welink  extends OrganizationConnector{
	private final static Logger logger = LoggerFactory.getLogger(Organization2Welink.class);

	LdapUtils  ldapUtils;
	public Organization2Welink() {
		
	}

	@Override
	public boolean create(Organizations organization) throws Exception {
		logger.info("create");
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(&(objectClass=organizationalUnit)(description="+organization.getParentId()+"))", constraints);
			String rdn="";
			if (results == null || !results.hasMore()) {
				rdn=ldapUtils.getBaseDN();
			}else{
				SearchResult sr = (SearchResult) results.next();
				rdn =sr.getNameInNamespace();
			}
			
			Attributes attributes = new BasicAttributes();
			attributes.put(new BasicAttribute("objectClass","organizationalUnit"));
			attributes.put(new BasicAttribute("ou",organization.getName()));
			//attributes.put(new BasicAttribute("name",organization.getName()));
			//attributes.put(new BasicAttribute("id",organization.getId()));
			//attributes.put(new BasicAttribute("porgname",organization.getpName()));
			//attributes.put(new BasicAttribute("porgid",organization.getpId()));
			attributes.put(new BasicAttribute("description",organization.getId()));
			
			String dn="ou="+organization.getName()+","+rdn;
			
			ldapUtils.getCtx().createSubcontext(dn, attributes);
			ldapUtils.close();
			
		return super.create(organization);
	}

	@Override
	public boolean update(Organizations organization)  throws Exception{
		logger.info("update");
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(&(objectClass=organizationalUnit)(description="+organization.getId()+"))", constraints);
			String oldDn="";
			String rdn="";
			if (results == null || !results.hasMore()) {
				return create(organization);
			}else{
				SearchResult sr = (SearchResult) results.next();
				oldDn =sr.getNameInNamespace();
				String[] dnSplit=oldDn.split(",");
				rdn=oldDn.substring(oldDn.indexOf(",")+1, oldDn.length());
				
				String ouName=dnSplit[0].split("=")[1];
				if(organization.getName()!=ouName){
					String newDn="ou="+organization.getName()+","+rdn;
					logger.debug("oldDn : "+oldDn);
					logger.debug("newDn : "+newDn);
					ldapUtils.getCtx().rename(oldDn, newDn);
					ModificationItem[] modificationItems = new ModificationItem[1];
					modificationItems[0]=new ModificationItem(DirContext.REMOVE_ATTRIBUTE,new BasicAttribute("ou",ouName));
					//modificationItems[1]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("name",organization.getName()));
					//modificationItems[2]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("id",organization.getId()));
					//modificationItems[3]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("porgname",organization.getpName()));
					//modificationItems[4]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("porgid",organization.getpId()));
					ldapUtils.getCtx().modifyAttributes(newDn, modificationItems);
				}
			}
			
			ldapUtils.close();
		
		return super.update(organization);
	}

	@Override
	public boolean delete(Organizations organization)  throws Exception{
		logger.info("delete");
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(&(objectClass=organizationalUnit)(description="+organization.getId()+"))", constraints);
			String dn="";
			if (results == null || !results.hasMore()) {
				
			}else{
				SearchResult sr = (SearchResult) results.next();
				dn =sr.getNameInNamespace();
				ldapUtils.getCtx().destroySubcontext(dn);
			}
			
			ldapUtils.close();
		
		return super.delete(organization);
	}

}
