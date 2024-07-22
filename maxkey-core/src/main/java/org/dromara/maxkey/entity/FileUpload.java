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


import java.util.Date;

import org.dromara.mybatis.jpa.entity.JpaEntity;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MXK_FILE_UPLOAD")
public class FileUpload extends JpaEntity {
	
	@Id
    @Column
    @GeneratedValue
    String id;
	
	@Column
    byte[] uploaded;
    
    @JsonIgnore
     MultipartFile uploadFile;
    
    @Column
    String fileName;
    
    @Column
    String contentType;
    
    @Column
    long contentSize;
    
    @Column
    String createdBy;
    
    Date createdDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getUploaded() {
		return uploaded;
	}

	public void setUploaded(byte[] uploaded) {
		this.uploaded = uploaded;
	}

	public MultipartFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getContentSize() {
		return contentSize;
	}

	public void setContentSize(long contentSize) {
		this.contentSize = contentSize;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileUpload [id=");
		builder.append(id);
		builder.append(", uploadFile=");
		builder.append(uploadFile);
		builder.append(", createdBy=");
		builder.append(this.createdBy);
		builder.append("]");
		return builder.toString();
	}
    
}
