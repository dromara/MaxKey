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


/*
   ID                   varchar(40)                    not null,
   APPROLEID            varchar(40)                    null,
   USERID	                varchar(40)	                   null
   constraint PK_ROLES primary key clustered (ID)
 */
@Entity
@Table(name = "MXK_GROUP_MEMBER")  
public class GroupMember extends UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8059639972590554760L;
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO,generator="snowflakeid")
	String id;
	@Column
	private String groupId;
	private String groupName;
	@Column
	private String memberId;
	private String memberName;
	@Column
	private String type;//User or Group

	@Column
	private String instId;

	private String instName;
	
	public GroupMember(){
		super();
	}

	
	/**
	 * @param groupId
	 * @param memberId
	 * @param type
	 */
	public GroupMember(String groupId, String memberId, String type , String instId) {
		super();
		this.groupId = groupId;
		this.memberId = memberId;
		this.type = type;
		this.instId = instId;
	}


	public GroupMember(String groupId, String groupName, String memberId,
			String memberName, String type , String instId) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.memberId = memberId;
		this.memberName = memberName;
		this.type = type;
		this.instId = instId;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}


	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
        builder.append("GroupMember [id=");
        builder.append(id);
        builder.append(", groupId=");
        builder.append(groupId);
        builder.append(", groupName=");
        builder.append(groupName);
        builder.append(", memberId=");
        builder.append(memberId);
        builder.append(", memberName=");
        builder.append(memberName);
        builder.append(", type=");
        builder.append(type);
        builder.append("]");
        return builder.toString();
    }


}
