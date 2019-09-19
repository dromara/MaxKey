package org.maxkey.domain;

import java.io.Serializable;

import javax.persistence.Table;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;
import org.hibernate.validator.constraints.Length;


@Table(name = "GROUPS")  
public class Groups extends JpaBaseDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4660258495864814777L;

	String id;
	
	@Length(max=60)
	private String name;
	private int isdefault;
	String description;
	String createdBy;
	String createdDate;
	String modifiedBy;
	String modifiedDate;
	String status;
	
	public Groups() {}
	
	public Groups(String id) {
		this.id = id;
	}

	public Groups(String id, String name, int isdefault) {
		super();
		this.id = id;
		this.name = name;
		this.isdefault = isdefault;
	}

	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIsdefault(){
		return isdefault;
	}
	
	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Groups [name=" + name + ", isdefault=" + isdefault + "]";
	}
	

	
}
