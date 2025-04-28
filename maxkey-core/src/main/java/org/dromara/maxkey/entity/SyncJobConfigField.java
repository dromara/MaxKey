/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.dromara.mybatis.jpa.entity.JpaEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  
 * @Author  Hunter
 * @Date 2024-07-16 
 */

@Entity
@Table(name = "SYNC_JOB_CONFIG_FIELD")
public class SyncJobConfigField extends JpaEntity implements Serializable {

	private static final long serialVersionUID =  6784822536779144306L;

	/**
	 *
	 * ID
	 */
	@Id
	@Column
	private Long id;

	/**
	 * 同步任务ID
	 */
	@Column
	private Long jobId;

	/**
	 * 规则名
	 */
   	@Column
	private String name;

	/**
	 * 类型
	 */
   	@Column
	private String objectType;

	/**
	 * 目标字段
	 */
   	@Column
	private String targetField;

	/**
	 * 目标字段描述
	 */
   	@Column
	private String targetFieldName;

	/**
	 * 来源字段
	 */
   	@Column
	private String sourceField;

	/**
	 * 来源字段描述
	 */
   	@Column
	private String sourceFieldName;

	/**
	 * 描述
	 */
   	@Column
	private String description;

	/**
	 * 创建人
	 */
   	@Column
	private Long createUser;

	/**
	 * 创建时间
	 */
   	@Column
	private Date createTime;

	/**
	 * 修改人
	 */
   	@Column
	private Long updateUser;

	/**
	 * 修改时间
	 */
   	@Column
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getTargetField() {
		return targetField;
	}

	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}

	public String getTargetFieldName() {
		return targetFieldName;
	}

	public void setTargetFieldName(String targetFieldName) {
		this.targetFieldName = targetFieldName;
	}

	public String getSourceField() {
		return sourceField;
	}

	public void setSourceField(String sourceField) {
		this.sourceField = sourceField;
	}

	public String getSourceFieldName() {
		return sourceFieldName;
	}

	public void setSourceFieldName(String sourceFieldName) {
		this.sourceFieldName = sourceFieldName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

