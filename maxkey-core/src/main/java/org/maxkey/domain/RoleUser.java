package org.maxkey.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;


/*
   ID                   varchar(40)                    not null,
   ROLEID               varchar(40)                    null,
   UID	                varchar(40)	                   null
   constraint PK_ROLES primary key clustered (ID)
 */
/**
 * @author Crystal.Sea
 *
 */ 
public class RoleUser extends UserInfo implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3840528281795495533L;
	/**
	 * 
	 */
	@NotEmpty
	private String roleId;
	@NotEmpty
	private String uid;
	
	public RoleUser(){
		super();
	}

	

	public RoleUser(String roleId, String uid) {
		super();
		this.roleId = roleId;
		this.uid = uid;
	}



	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RoleUser [roleId=" + roleId + ", uid=" + uid + "]";
	}
	

}
