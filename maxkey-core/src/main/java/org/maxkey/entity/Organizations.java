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
 

package org.maxkey.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.mybatis.jpa.persistence.JpaBaseEntity;
@Entity
@Table(name = "MXK_ORGANIZATIONS")
public class Organizations extends JpaBaseEntity implements Serializable {

    private static final long serialVersionUID = 5085413816404119803L;
    
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflakeid")
    private String id;
    @Column
    private String code;
    @Column
    private String name;
    @Column
    private String fullName;
    @Column
    private String parentId;
    private String parentCode;
    @Column
    private String parentName;
    @Column
    private String type;
    @Column
    private String codePath;
    @Column
    private String namePath;
    @Column
    private int level;
    @Column
    private String hasChild;
    @Column
    private String division;
    @Column
    private String country;
    @Column
    private String region;
    @Column
    private String locality;
    @Column
    private String street;
    @Column
    private String address;
    @Column
    private String contact;
    @Column
    private String postalCode;
    @Column
    private String phone;
    @Column
    private String fax;
    @Column
    private String email;
    @Column
    private long sortIndex;
    @Column
    private String ldapDn;
    @Column
    private String description;
    @Column
    private int status;
    @Column
    String createdBy;
    @Column
    String createdDate;
    @Column
    String modifiedBy;
    @Column
    String modifiedDate;
    
	@Column
	private String instId;

	private String instName;
    
    private int isPrimary = 0;
    
    private boolean reorgNamePath;

    public Organizations() {
        //
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public long getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(long sortIndex) {
        this.sortIndex = sortIndex;
    }
    
    

    public String getLdapDn() {
		return ldapDn;
	}

	public void setLdapDn(String ldapDn) {
		this.ldapDn = ldapDn;
	}

	public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
	public int getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(int isPrimary) {
        this.isPrimary = isPrimary;
    }

    public boolean isReorgNamePath() {
        return reorgNamePath;
    }

    public void setReorgNamePath(boolean reorgNamePath) {
        this.reorgNamePath = reorgNamePath;
    }

    public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Organizations [id=");
        builder.append(id);
        builder.append(", code=");
        builder.append(code);
        builder.append(", name=");
        builder.append(name);
        builder.append(", fullName=");
        builder.append(fullName);
        builder.append(", parentId=");
        builder.append(parentId);
        builder.append(", parentName=");
        builder.append(parentName);
        builder.append(", type=");
        builder.append(type);
        builder.append(", codePath=");
        builder.append(codePath);
        builder.append(", namePath=");
        builder.append(namePath);
        builder.append(", level=");
        builder.append(level);
        builder.append(", hasChild=");
        builder.append(hasChild);
        builder.append(", division=");
        builder.append(division);
        builder.append(", country=");
        builder.append(country);
        builder.append(", region=");
        builder.append(region);
        builder.append(", locality=");
        builder.append(locality);
        builder.append(", street=");
        builder.append(street);
        builder.append(", address=");
        builder.append(address);
        builder.append(", contact=");
        builder.append(contact);
        builder.append(", postalCode=");
        builder.append(postalCode);
        builder.append(", phone=");
        builder.append(phone);
        builder.append(", fax=");
        builder.append(fax);
        builder.append(", email=");
        builder.append(email);
        builder.append(", sortIndex=");
        builder.append(sortIndex);
        builder.append(", ldapDn=");
        builder.append(ldapDn);
        builder.append(", description=");
        builder.append(description);
        builder.append(", status=");
        builder.append(status);
        builder.append(", isPrimary=");
        builder.append(isPrimary);
        builder.append(", reorgNamePath=");
        builder.append(reorgNamePath);
        builder.append("]");
        return builder.toString();
    }



}
