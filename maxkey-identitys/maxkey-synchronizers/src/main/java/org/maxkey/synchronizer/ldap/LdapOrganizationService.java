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
import org.maxkey.constants.ldap.OrganizationalUnit;
import org.maxkey.entity.Organizations;
import org.maxkey.persistence.ldap.LdapUtils;
import org.maxkey.persistence.service.OrganizationsService;
import org.maxkey.synchronizer.ISynchronizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LdapOrganizationService  implements ISynchronizerService{
	final static Logger _logger = LoggerFactory.getLogger(LdapOrganizationService.class);

	LdapUtils ldapUtils;
	
	@Autowired
	OrganizationsService organizationsService;
	
	public void sync() {
		_logger.info("Sync Organizations ...");
		
		_logger.info("Sync Organizations ...");
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(&(objectClass=OrganizationalUnit))", constraints);
			
			long recordCount = 0;
			while (null != results && results.hasMoreElements()) {
				Object obj = results.nextElement();
				if (obj instanceof SearchResult) {
					recordCount ++;
					SearchResult si = (SearchResult) obj;
					_logger.info("Sync OrganizationalUnit  Record " + recordCount+" --------------------------------------------------");
					_logger.trace("name " + si.getName());
					_logger.info("NameInNamespace " + si.getNameInNamespace());
					
					HashMap<String,Attribute> attributeMap = new HashMap<String,Attribute>();
					NamingEnumeration<? extends Attribute>  attrs = si.getAttributes().getAll();
					while (null != attrs && attrs.hasMoreElements()) {
						Attribute  objAttrs = attrs.nextElement();
						_logger.trace("attribute "+objAttrs.getID() + " : " + objAttrs.get());
						attributeMap.put(objAttrs.getID().toLowerCase(), objAttrs);
					}
					
					Organizations org = buildOrganization(attributeMap,si.getName(),si.getNameInNamespace());
					
					_logger.info("Organizations " + org);
				}
			}
			
			//ldapUtils.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	public Organizations buildOrganization(HashMap<String,Attribute> attributeMap,String name,String nameInNamespace) {
		Organizations org = new Organizations();
		org.setLdapDn(nameInNamespace);
		try {
			org.setName(LdapUtils.getAttributeStringValue(OrganizationalUnit.OU,attributeMap));

			//org.setCountry(LdapUtils.getAttributeStringValue(OrganizationalUnit.CO,attributeMap));
			org.setRegion(LdapUtils.getAttributeStringValue(OrganizationalUnit.ST,attributeMap));
			org.setLocality(LdapUtils.getAttributeStringValue(OrganizationalUnit.L,attributeMap));
			org.setStreet(LdapUtils.getAttributeStringValue(OrganizationalUnit.STREET,attributeMap));
			org.setPostalCode(LdapUtils.getAttributeStringValue(OrganizationalUnit.POSTALCODE,attributeMap));
			org.setAddress(LdapUtils.getAttributeStringValue(OrganizationalUnit.POSTALADDRESS,attributeMap));
			org.setPhone(LdapUtils.getAttributeStringValue(OrganizationalUnit.TELEPHONENUMBER,attributeMap));
			org.setFax(LdapUtils.getAttributeStringValue(OrganizationalUnit.FACSIMILETELEPHONENUMBER,attributeMap));
			org.setDescription(LdapUtils.getAttributeStringValue(OrganizationalUnit.DESCRIPTION,attributeMap));
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return org;
	}
	

	public LdapUtils getLdapUtils() {
		return ldapUtils;
	}

	public void setLdapUtils(LdapUtils ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

	public OrganizationsService getOrganizationsService() {
		return organizationsService;
	}

	public void setOrganizationsService(OrganizationsService organizationsService) {
		this.organizationsService = organizationsService;
	}
	
	
}
