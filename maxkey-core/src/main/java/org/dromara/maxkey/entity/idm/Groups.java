/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.entity.idm;

import java.io.Serializable;
import java.util.Date;

import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "MXK_GROUPS")
public class Groups extends JpaEntity implements Serializable {

    private static final long serialVersionUID = 4660258495864814777L;
    
    public class Category{
    	public static final String DYNAMIC = "dynamic";
    	
    	public static final String STATIC  = "static";
    	
    	public static final String APP     = "app";
    }
    
    
    @Id
    @Column
    @GeneratedValue
    String id;

    @Length(max = 60)
    @Column
    String groupCode;
    
    @Length(max = 60)
    @Column
    String groupName;
    
    @Column
    String category;

    @Column
    String filters ;
    
    @Column
    String orgIdsList;
    
    @Column
    int isdefault;
    @Column
    String description;
    @Column
    String createdBy;
    @Column
    Date createdDate;
    @Column
    String modifiedBy;
    @Column
    Date modifiedDate;
    @Column
    int status;
    
	@Column
	private String instId;

	private String instName;

    public Groups() {
    }

    public Groups(String id) {
        this.id = id;
    }

    /**
     * Groups.
     * @param id String
     * @param name String
     * @param isdefault int
     */
    public Groups(String id,String groupCode, String groupName, int isdefault) {
        super();
        this.id = id;
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.isdefault = isdefault;
    }

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	
    public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * ROLE_ALL_USER must be 
     * 		1, dynamic 
     * 		2, all orgIdsList 
	 *		3, not filters
     */
    public void setDefaultAllUser() {
    	this.category = "dynamic";
    	this.orgIdsList ="";
		this.filters ="";
    }

    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getOrgIdsList() {
        return orgIdsList;
    }

    public void setOrgIdsList(String orgIdsList) {
        this.orgIdsList = orgIdsList;
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
		builder.append("Groups [id=");
		builder.append(id);
		builder.append(", groupCode=");
		builder.append(groupCode);
		builder.append(", groupName=");
		builder.append(groupName);
		builder.append(", category=");
		builder.append(category);
		builder.append(", filters=");
		builder.append(filters);
		builder.append(", orgIdsList=");
		builder.append(orgIdsList);
		builder.append(", isdefault=");
		builder.append(isdefault);
		builder.append(", description=");
		builder.append(description);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", modifiedBy=");
		builder.append(modifiedBy);
		builder.append(", modifiedDate=");
		builder.append(modifiedDate);
		builder.append(", status=");
		builder.append(status);
		builder.append(", instId=");
		builder.append(instId);
		builder.append(", instName=");
		builder.append(instName);
		builder.append("]");
		return builder.toString();
	}

}
