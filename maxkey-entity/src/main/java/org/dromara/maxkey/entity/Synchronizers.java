/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
import java.util.Date;

import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.validator.constraints.Length;


@Entity
@Table(name = "MXK_SYNCHRONIZERS")
public class Synchronizers extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 4660258495864814777L;
	
	@Id
	@Column
	@GeneratedValue
	String id;

	@Length(max = 60)
	@Column
	String name;
	@Column
	String sourceType;
	@Column
	String resumeTime;
	@Column
	String suspendTime;
	@Column
	String scheduler;

	// 同步时间范围（单位天）
	@Column
	Integer syncStartTime;

	@Column
	String providerUrl;
	@Column
	String driverClass;
	@Column
	String principal;
	@Column
	String credentials;
	@Column
	String userBasedn;
	@Column
	String userFilters;
	@Column
	String orgBasedn;
	@Column
	String orgFilters;
	@Column
	String msadDomain;
	@Column
	String sslSwitch;
	@Column
	String trustStore;
	@Column
	String trustStorePassword;
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
	String status;
	@Column
	String service;

	@Column
	private String instId;

	private String instName;

	public Synchronizers() {
	}

	public Synchronizers(String id) {
		this.id = id;
	}

	public Integer getSyncStartTime() {
		return syncStartTime;
	}

	public void setSyncStartTime(Integer syncStartTime) {
		this.syncStartTime = syncStartTime;
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

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getResumeTime() {
		return resumeTime;
	}

	public void setResumeTime(String resumeTime) {
		this.resumeTime = resumeTime;
	}

	public String getSuspendTime() {
		return suspendTime;
	}

	public void setSuspendTime(String suspendTime) {
		this.suspendTime = suspendTime;
	}

	public String getScheduler() {
		return scheduler;
	}

	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public String getUserBasedn() {
		return userBasedn;
	}

	public void setUserBasedn(String userBasedn) {
		this.userBasedn = userBasedn;
	}

	public String getUserFilters() {
		return userFilters;
	}

	public void setUserFilters(String userFilters) {
		this.userFilters = userFilters;
	}

	public String getOrgBasedn() {
		return orgBasedn;
	}

	public void setOrgBasedn(String orgBasedn) {
		this.orgBasedn = orgBasedn;
	}

	public String getOrgFilters() {
		return orgFilters;
	}

	public void setOrgFilters(String orgFilters) {
		this.orgFilters = orgFilters;
	}

	public String getMsadDomain() {
		return msadDomain;
	}

	public void setMsadDomain(String msadDomain) {
		this.msadDomain = msadDomain;
	}

	public String getSslSwitch() {
		return sslSwitch;
	}

	public void setSslSwitch(String sslSwitch) {
		this.sslSwitch = sslSwitch;
	}

	public String getTrustStore() {
		return trustStore;
	}

	public void setTrustStore(String trustStore) {
		this.trustStore = trustStore;
	}

	public String getTrustStorePassword() {
		return trustStorePassword;
	}

	public void setTrustStorePassword(String trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
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
		builder.append("Synchronizers [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", sourceType=");
		builder.append(sourceType);
		builder.append(", resumeTime=");
		builder.append(resumeTime);
		builder.append(", suspendTime=");
		builder.append(suspendTime);
		builder.append(", scheduler=");
		builder.append(scheduler);
		builder.append(", syncStartTime=");
		builder.append(syncStartTime);
		builder.append(", providerUrl=");
		builder.append(providerUrl);
		builder.append(", driverClass=");
		builder.append(driverClass);
		builder.append(", principal=");
		builder.append(principal);
		builder.append(", credentials=");
		builder.append(credentials);
		builder.append(", userBasedn=");
		builder.append(userBasedn);
		builder.append(", userFilters=");
		builder.append(userFilters);
		builder.append(", orgBasedn=");
		builder.append(orgBasedn);
		builder.append(", orgFilters=");
		builder.append(orgFilters);
		builder.append(", msadDomain=");
		builder.append(msadDomain);
		builder.append(", sslSwitch=");
		builder.append(sslSwitch);
		builder.append(", trustStore=");
		builder.append(trustStore);
		builder.append(", trustStorePassword=");
		builder.append(trustStorePassword);
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
		builder.append(", service=");
		builder.append(service);
		builder.append(", instId=");
		builder.append(instId);
		builder.append(", instName=");
		builder.append(instName);
		builder.append("]");
		return builder.toString();
	}

}
