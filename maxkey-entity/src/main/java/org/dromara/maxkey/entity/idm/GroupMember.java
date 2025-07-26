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
 

package org.dromara.maxkey.entity.idm;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mxk_group_member")  
public class GroupMember extends UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8059639972590554760L;
	@Id
	@Column
	@GeneratedValue
	String id;
	@Column
	private String groupId;
	private String groupName;
	private String category;
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


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
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



	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
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
		builder.append(", category=");
		builder.append(category);
		builder.append(", memberId=");
		builder.append(memberId);
		builder.append(", memberName=");
		builder.append(memberName);
		builder.append(", type=");
		builder.append(type);
		builder.append(", instId=");
		builder.append(instId);
		builder.append(", instName=");
		builder.append(instName);
		builder.append("]");
		return builder.toString();
	}


}
