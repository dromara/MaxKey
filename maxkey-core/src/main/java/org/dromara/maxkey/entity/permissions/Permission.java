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
 

package org.dromara.maxkey.entity.permissions;

import java.io.Serializable;
import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.web.WebContext;

@Entity
@Table(name = "MXK_PERMISSION")
public class Permission  extends JpaEntity implements Serializable {
    private static final long serialVersionUID = -8783585691243853899L;
    
    @Id
    @Column
    @GeneratedValue
    String id;
    @Column
    String appId;
    @Column
    String groupId;
    @Column
    String resourceId;
    
    int status = ConstsStatus.ACTIVE;
	@Column
	private String instId;

	private String instName;
	
    public Permission() {
    }

    public Permission(String appId, String groupId, String instId) {
        this.appId = appId;
        this.groupId = groupId;
        this.instId = instId;
    }
    
    /**
     * .
     * @param appId String
     * @param groupId String
     * @param resourceId String
     */
    public Permission(String appId, String groupId, String resourceId , String instId) {
        this.id = WebContext.genId();
        this.appId = appId;
        this.groupId = groupId;
        this.resourceId = resourceId;
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

    public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
        return  appId + "_" + groupId + "_" + resourceId;
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
        builder.append("RolePrivileges [id=");
        builder.append(id);
        builder.append(", appId=");
        builder.append(appId);
        builder.append(", groupId=");
        builder.append(groupId);
        builder.append(", resourceId=");
        builder.append(resourceId);
        builder.append(", status=");
        builder.append(status);
        builder.append("]");
        return builder.toString();
    }
    

}
