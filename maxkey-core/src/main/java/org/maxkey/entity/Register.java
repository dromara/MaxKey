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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.mybatis.jpa.persistence.JpaBaseEntity;

@Entity
@Table(name = "MXK_REGISTER")
public class Register extends JpaBaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4021412077583763378L;
	@Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflakeid")
	private String id;
	@Column
	private String displayName;
	@Column
	private String workEmail;
	@Column
	private String workPhone;
	@Column
	private Integer employees;
	
	//机构
	private String instId;
	@Column
	private String instName;
	
	/**
	 * 
	 */
	public Register() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getWorkEmail() {
		return workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}



	public Integer getEmployees() {
		return employees;
	}

	public void setEmployees(Integer employees) {
		this.employees = employees;
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
		builder.append("Registration [id=");
		builder.append(id);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append(", workEmail=");
		builder.append(workEmail);
		builder.append(", workPhone=");
		builder.append(workPhone);
		builder.append(", employees=");
		builder.append(employees);
		builder.append(", instId=");
		builder.append(instId);
		builder.append(", instName=");
		builder.append(instName);
		builder.append("]");
		return builder.toString();
	}
	
}
