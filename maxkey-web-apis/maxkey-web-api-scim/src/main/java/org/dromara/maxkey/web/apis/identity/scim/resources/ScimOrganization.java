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
 

package org.dromara.maxkey.web.apis.identity.scim.resources;

import java.util.HashSet;
import java.util.List;

public class ScimOrganization extends ScimResource{

    /**
     * 
     */
    private static final long serialVersionUID = -8087404240254880740L;
    public static  String SCHEMA = "urn:ietf:params:scim:schemas:core:2.0:Organization";
    
    private String code;
    
    private String name;
    
    private String fullName;
    
    private String parentId;
    
    private String parentName;
    
    private String type;
    
    private String codePath;
    
    private String namePath;
    
    private int level;
    
    private String division;
    
    private  List<ScimOrganizationAddress> addresses;
    
    private  List<ScimOrganizationEmail> emails;

    private  List<ScimOrganizationPhoneNumber> phoneNumbers;
    
    
    private String sortOrder;
    
    private String description;
    
    // T/IDAC 002—2021
    private String 	displayName;	//name
    private long   	order;			//sortOrder
    private String 	parent; 		//parentId
    private String 	parentCode;		//parent code
    
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ScimOrganizationAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<ScimOrganizationAddress> addresses) {
        this.addresses = addresses;
    }

    public List<ScimOrganizationEmail> getEmails() {
        return emails;
    }

    public void setEmails(List<ScimOrganizationEmail> emails) {
        this.emails = emails;
    }

    public List<ScimOrganizationPhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<ScimOrganizationPhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    
    public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public long getOrder() {
		return order;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public ScimOrganization() {
        schemas =new HashSet<String>();
        schemas.add(SCHEMA);
        
    }
    
    
}
