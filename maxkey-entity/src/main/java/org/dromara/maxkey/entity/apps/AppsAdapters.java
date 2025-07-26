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
 

package org.dromara.maxkey.entity.apps;

import java.io.Serializable;
import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MXK_APPS_ADAPTERS")
public class AppsAdapters extends JpaEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6264641546959620712L;

    @Id
    @Column
    @GeneratedValue
    protected String id;
    /**
     * 
     */
    @Column
    private String name;

    @Column
    private String protocol;
    
    @Column
    private String adapter;

    @Column
    protected int sortIndex;

    @Column
    protected String createdBy;
    @Column
    protected String createdDate;
    @Column
    protected String modifiedBy;
    @Column
    protected String modifiedDate;
    @Column
    protected String description;
    
    
    public AppsAdapters() {
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


	public String getProtocol() {
		return protocol;
	}


	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}


	public String getAdapter() {
		return adapter;
	}


	public void setAdapter(String adapter) {
		this.adapter = adapter;
	}


	public int getSortIndex() {
		return sortIndex;
	}


	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public String getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppsAdapters [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", protocol=");
		builder.append(protocol);
		builder.append(", adapter=");
		builder.append(adapter);
		builder.append(", sortIndex=");
		builder.append(sortIndex);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", modifiedBy=");
		builder.append(modifiedBy);
		builder.append(", modifiedDate=");
		builder.append(modifiedDate);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

}
