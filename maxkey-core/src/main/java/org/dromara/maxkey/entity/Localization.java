/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
@Table(name = "MXK_LOCALIZATION")
public class Localization  extends JpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -142504964446659847L;
	@Id
	@Column
	@GeneratedValue
	private String id;
	
	@Column
	private String property;

	@Column
	private String langZh;
	
	@Column
	private String langEn;
	
	@Column
	private String description;
	
	@Column
	private int status;
	
	@Column
	private String instId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}



	public String getLangZh() {
		return langZh;
	}

	public void setLangZh(String langZh) {
		this.langZh = langZh;
	}

	public String getLangEn() {
		return langEn;
	}

	public void setLangEn(String langEn) {
		this.langEn = langEn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Localization [id=");
		builder.append(id);
		builder.append(", property=");
		builder.append(property);
		builder.append(", langZh=");
		builder.append(langZh);
		builder.append(", langEn=");
		builder.append(langEn);
		builder.append(", description=");
		builder.append(description);
		builder.append(", status=");
		builder.append(status);
		builder.append(", instId=");
		builder.append(instId);
		builder.append("]");
		return builder.toString();
	}

}
