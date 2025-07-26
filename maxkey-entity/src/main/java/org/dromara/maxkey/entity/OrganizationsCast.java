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
 

package org.dromara.maxkey.entity;

import java.io.Serializable;
import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MXK_ORGANIZATIONS_CAST")
public class OrganizationsCast extends JpaEntity implements Serializable {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5166920258761620856L;
	@Id
    @Column
    @GeneratedValue
    private String id;
    @Column
    private String code;
    @Column
    private String name;
    @Column
    private String fullName;
    @Column
    private String parentId;
    @Column
    private String parentName;
    @Column
    private String codePath;
    @Column
    private String namePath;
    
    @Column
    private long sortIndex;
    @Column
    private int status;
    @Column
    private String provider;
    
    @Column
    private String orgId;
    @Column
    private String orgParentId;
    
    @Column
    private String appId;
    
    @Column
    private String appName;
	@Column
	private String instId;

	private String instName;
    //重组标志
    boolean reorgNamePath;
    

    public OrganizationsCast() {
        //
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getCodePath() {
		return codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}

	public String getNamePath() {
		return namePath;
	}

	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgParentId() {
		return orgParentId;
	}

	public void setOrgParentId(String orgParentId) {
		this.orgParentId = orgParentId;
	}

	public long getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(long sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public boolean isReorgNamePath() {
		return reorgNamePath;
	}

	public void setReorgNamePath(boolean reorgNamePath) {
		this.reorgNamePath = reorgNamePath;
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
		builder.append("OrganizationsMapper [id=");
		builder.append(id);
		builder.append(", code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append(", fullName=");
		builder.append(fullName);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append(", parentName=");
		builder.append(parentName);
		builder.append(", codePath=");
		builder.append(codePath);
		builder.append(", namePath=");
		builder.append(namePath);
		builder.append(", status=");
		builder.append(status);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append(", orgParentId=");
		builder.append(orgParentId);
		builder.append("]");
		return builder.toString();
	}



}
