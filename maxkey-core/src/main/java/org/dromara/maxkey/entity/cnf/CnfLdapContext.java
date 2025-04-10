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
 

package org.dromara.maxkey.entity.cnf;

import java.io.Serializable;
import java.util.Date;

import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MXK_CNF_LDAP_CONTEXT")
public class CnfLdapContext extends JpaEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4595539647817265938L;
	@Id
    @Column
    @GeneratedValue
    String id;
    @Column
    String product;
    @Column
    String providerUrl;
    @Column
    String principal;
    @Column
    String credentials;
    @Column
    String filters;
    @Column
    String basedn;
    @Column
    String msadDomain;
    @Column
    String accountMapping;
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
    int status;
    
	@Column
	private String instId;

	private String instName;

    public CnfLdapContext() {
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
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

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getBasedn() {
		return basedn;
	}

	public void setBasedn(String basedn) {
		this.basedn = basedn;
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

	public String getAccountMapping() {
		return accountMapping;
	}

	public void setAccountMapping(String accountMapping) {
		this.accountMapping = accountMapping;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
		builder.append("LdapContext [id=");
		builder.append(id);
		builder.append(", product=");
		builder.append(product);
		builder.append(", providerUrl=");
		builder.append(providerUrl);
		builder.append(", principal=");
		builder.append(principal);
		builder.append(", credentials=");
		builder.append(credentials);
		builder.append(", filters=");
		builder.append(filters);
		builder.append(", basedn=");
		builder.append(basedn);
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
		builder.append(", instId=");
		builder.append(instId);
		builder.append(", instName=");
		builder.append(instName);
		builder.append("]");
		return builder.toString();
	}

}
