package org.maxkey.domain;

import java.io.Serializable;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;
import org.hibernate.validator.constraints.Length;



public class Groups extends JpaBaseDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4660258495864814777L;

	String id;
	
	@Length(max=60)
	private String name;
	private int isdefault;
	
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Groups [name=" + name + ", isdefault=" + isdefault + "]";
	}
	

	
}
