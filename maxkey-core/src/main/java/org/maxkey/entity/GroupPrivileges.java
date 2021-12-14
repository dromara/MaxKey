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

import org.maxkey.entity.apps.Apps;


/*
   ID                   varchar(40)                    not null,
   ROLEID               varchar(40)                    null,
   MENUID                varchar(40)	                   null
   constraint PK_ROLES primary key clustered (ID)
 */
@Entity
@Table(name = "MXK_GROUP_PRIVILEGES")  
public class GroupPrivileges extends Apps implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8634166407201007340L;
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO,generator="snowflakeid")
	String id;
	@Column
	private String groupId;
	@Column
	private String appId;
	
	@Column
	private String instId;

	private String instName;
	
	public GroupPrivileges(){
		super();
	}
	
	
	/**
	 * @param groupId
	 * @param appId
	 */
	public GroupPrivileges(String groupId, String appId, String instId) {
		super();
		this.groupId = groupId;
		this.appId = appId;
		this.instId = instId;
	}


	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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
        builder.append("GroupPrivileges [id=");
        builder.append(id);
        builder.append(", groupId=");
        builder.append(groupId);
        builder.append(", appId=");
        builder.append(appId);
        builder.append("]");
        return builder.toString();
    }
	

}
