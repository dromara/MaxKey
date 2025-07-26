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
 

package org.dromara.maxkey.entity;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;

import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "MXK_ACCOUNTS_STRATEGY")
public class AccountsStrategy extends JpaEntity implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8743329570694948718L;
    @Id
    @Column
    @GeneratedValue
    private String id;
    @Column
    private String name;
    @Column
    private String appId;
    private byte[] appIcon;
    private String appIconBase64;
    @Column
    private String appName;
    @Column
    private String mapping;
    @Column
    String filters ;
    @Column
    String orgIdsList;
    @Column
    String suffixes;
    @Column
    String createType;
    @Column
    String status;
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
    private String instId;
    
    private String instName;

    public AccountsStrategy() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public byte[] getAppIcon() {
		return appIcon;
	}

	public String getAppIconBase64() {
		return appIconBase64;
	}

	public void setAppIconBase64(String appIconBase64) {
		this.appIconBase64 = appIconBase64;
	}

	public void setAppIcon(byte[] appIcon) {
		this.appIcon = appIcon;
	}

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public String getSuffixes() {
		return suffixes;
	}

	public void setSuffixes(String suffixes) {
		this.suffixes = suffixes;
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

	
	public void transIconBase64() {
		if(this.appIcon !=null) {
			this.appIconBase64 = "data:image/png;base64," + 
					Base64.getEncoder().encodeToString(appIcon);
		}
	}
	
	
	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AccountsStrategy [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", appId=");
        builder.append(appId);
        builder.append(", appName=");
        builder.append(appName);
        builder.append(", mapping=");
        builder.append(mapping);
        builder.append(", filters=");
        builder.append(filters);
        builder.append(", orgIdsList=");
        builder.append(orgIdsList);
        builder.append(", status=");
        builder.append(status);
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
        builder.append("]");
        return builder.toString();
    }
   
    

}
