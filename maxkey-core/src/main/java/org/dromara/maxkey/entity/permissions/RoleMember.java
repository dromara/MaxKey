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

import org.dromara.maxkey.entity.idm.UserInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MXK_ROLE_MEMBER")  
public class RoleMember extends UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8059639972590554760L;
	@Id
	@Column
	@GeneratedValue
	String id;
	@Column
	private String roleId;
	private String roleName;
	private String category;
	@Column
	private String memberId;
	private String memberName;
	@Column
	private String type;//User or Group
	
	private String createdBy;

	@Column
	private String instId;

	private String instName;
	
	public RoleMember(){
		super();
	}

	
	/**
	 * @param groupId
	 * @param memberId
	 * @param type
	 */
	public RoleMember(String roleId, String memberId, String type , String instId) {
		super();
		this.roleId = roleId;
		this.memberId = memberId;
		this.type = type;
		this.instId = instId;
	}


	public RoleMember(String roleId, String roleName, String memberId,String memberName, String type , String createdBy, String instId) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.memberId = memberId;
		this.memberName = memberName;
		this.type = type;
		this.createdBy = createdBy;
		this.instId = instId;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}



	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
		builder.append("RoleMember [id=");
		builder.append(id);
		builder.append(", roleId=");
		builder.append(roleId);
		builder.append(", roleName=");
		builder.append(roleName);
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
