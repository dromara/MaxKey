/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
 

 

package org.dromara.maxkey.entity.permissions;

import java.io.Serializable;

import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.web.WebContext;
import org.dromara.mybatis.jpa.entity.JpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MXK_PERMISSION_ROLE")
public class PermissionRole  extends JpaEntity implements Serializable {
    private static final long serialVersionUID = -8783585691243853899L;
    
    @Id
    @Column
    @GeneratedValue
    String id;
    @Column
    String appId;
    @Column
    String roleId;
    @Column
    String resourceId;
    @Column
    String createdBy;
    @Column
    String createdDate;
    
    int status = ConstsStatus.ACTIVE;
	@Column
	private String instId;

	private String instName;
	
    public PermissionRole() {
    }

    public PermissionRole(String appId, String roleId, String instId) {
        this.appId = appId;
        this.roleId = roleId;
        this.instId = instId;
    }
    
    /**
     * .
     * @param appId String
     * @param roleId String
     * @param resourceId String
     */
    public PermissionRole(String appId, String roleId, String resourceId , String createdBy,String instId) {
        this.id = WebContext.genId();
        this.appId = appId;
        this.roleId = roleId;
        this.resourceId = resourceId;
        this.createdBy = createdBy;
        this.instId = instId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public String  getUniqueId() {
        return  appId + "_" + roleId + "_" + resourceId;
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
		builder.append("PermissionRole [id=");
		builder.append(id);
		builder.append(", appId=");
		builder.append(appId);
		builder.append(", roleId=");
		builder.append(roleId);
		builder.append(", resourceId=");
		builder.append(resourceId);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
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
